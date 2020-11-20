package org.honeybee.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.mybatisplus.util.PageUtil;
import org.honeybee.rbac.dto.AttachUserRoleDTO;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.entity.RbacDepartment;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.entity.RbacUserRole;
import org.honeybee.rbac.enums.UserEnableEnum;
import org.honeybee.rbac.mapper.RbacDepartmentMapper;
import org.honeybee.rbac.mapper.RbacUserMapper;
import org.honeybee.rbac.mapper.RbacUserRoleMapper;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.service.RbacUserService;
import org.honeybee.rbac.util.JwtUtil;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RbacUserServiceImpl extends ServiceImpl<RbacUserMapper, RbacUser> implements RbacUserService {

    @Autowired
    private RbacUserMapper rbacUserMapper;

    @Autowired
    private RbacUserRoleMapper rbacUserRoleMapper;

    @Autowired
    private RbacDepartmentMapper rbacDepartmentMapper;

    @Value("${custom.user.init-password}")
    private String initPassword;

    @Override
    public IPage<UserVO> find(RbacUserSearchDTO rbacUserSearchDTO) {
        Page page = PageUtil.buildPage(rbacUserSearchDTO);
        IPage<UserVO> result = rbacUserMapper.listUserByPage(page, rbacUserSearchDTO);
        return result;
    }

    @Override
    @Transactional
    public UserVO register(RbacUserDTO userDTO) {

        return null;
    }

    @Override
    @Transactional
    public UserVO create(RbacUserDTO userDTO) {
        //校验账号是否重复
        QueryWrapper queryWrapper = new QueryWrapper<RbacUser>()
                .eq("account", userDTO.getAccount());
        List<RbacUser> accountUserList = rbacUserMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(accountUserList)) {
            throw new BussinessException("账号[" + userDTO.getAccount() + "]已存在");
        }
        //校验部门是否存在
        QueryWrapper deptWrapper = new QueryWrapper<RbacDepartment>()
                .eq("delete_status", 0)
                .eq("id", userDTO.getDepartmentId());
        RbacDepartment department = rbacDepartmentMapper.selectOne(deptWrapper);
        if(department == null) {
            throw new BussinessException("部门不存在");
        }

        //保存至数据库
        RbacUser user = new RbacUser();
        BeanUtils.copyProperties(userDTO, user, "id");

        if(StringUtils.isBlank(initPassword)) {
            throw new BussinessException("初始密码配置[custom.user.init-password]为空");
        }
        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(initPassword));
        user.setLastPasswordResetDate(new Date());
        rbacUserMapper.insert(user);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    @Transactional
    public ResultVO update(RbacUserDTO userDTO) {
        RbacUser rbacUser = rbacUserMapper.selectById(userDTO.getId());
        if(rbacUser == null) {
            return new ResultVO(false, "用户不存在");
        }

        //拷贝属性值, 忽略空值和特定属性值
        BeanUtil.copyProperties(userDTO, rbacUser, CopyOptions.create().ignoreNullValue()
                .setIgnoreProperties("id", "account", "password"));
        int result = rbacUserMapper.updateById(rbacUser);
        if(result != 1) {
            throw new BussinessException("更新用户失败");
        }
        return new ResultVO(true, "更新成功");
    }

    @Override
    @Transactional
    public ResultVO delete(List<Long> userIds) {
        //逻辑删除用户
        if(CollectionUtils.isEmpty(userIds)) {
            return new ResultVO(false, "删除的用户为空");
        }
        rbacUserMapper.deleteByUserIds(userIds);
        return new ResultVO(true, "删除成功");
    }

    @Override
    public UserVO getById(Long userId) {
        UserVO user = rbacUserMapper.findById(userId);
        if(user == null) {
            throw new BussinessException("用户不存在");
        }
        return user;
    }

    @Override
    public JwtUser getCurrent() {
        return JwtUtil.getCurrentUserInfo();
    }

    @Override
    @Transactional
    public ResultVO attachUserRoles(AttachUserRoleDTO attachUserRoleDTO) {
        //删除原来的用户角色关联
        rbacUserRoleMapper.deleteByUserIds(Arrays.asList(attachUserRoleDTO.getUserId()));
        //新增绑定的角色
        for(Long roleId : attachUserRoleDTO.getRoleIds()) {
            RbacUserRole userRole = new RbacUserRole(attachUserRoleDTO.getUserId(), roleId);
            rbacUserRoleMapper.insert(userRole);
        }

        return new ResultVO(true, "成功");
    }

    @Override
    @Transactional
    public ResultVO updateUsersEnableStatus(List<Long> userIds, UserEnableEnum enableEnum) {
        if(CollectionUtils.isEmpty(userIds)) {
            return new ResultVO(false, enableEnum.getDescription() + "的用户为空");
        }

        rbacUserMapper.updateEnableByUserIds(userIds, enableEnum.getValue());
        return new ResultVO(true,  enableEnum.getDescription() + "成功");
    }

    @Override
    public ResultVO resetPassword(List<Long> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return new ResultVO(false, "重置密码的用户为空");
        }
        if(StringUtils.isBlank(initPassword)) {
            throw new BussinessException("初始密码配置[custom.user.init-password]为空");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        rbacUserMapper.updatePassword(passwordEncoder.encode(initPassword), userIds);
        return new ResultVO(true, "重置成功");
    }

}
