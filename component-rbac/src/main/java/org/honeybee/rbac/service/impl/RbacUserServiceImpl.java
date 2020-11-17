package org.honeybee.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.db.sql.Direction;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.util.CollectionUtil;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.AttachUserRoleDTO;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.entity.RbacUserRole;
import org.honeybee.rbac.mapper.RbacRoleMapper;
import org.honeybee.rbac.mapper.RbacUserMapper;
import org.honeybee.rbac.mapper.RbacUserRoleMapper;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.service.RbacUserService;
import org.honeybee.rbac.util.JwtUtil;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class RbacUserServiceImpl extends ServiceImpl<RbacUserMapper, RbacUser> implements RbacUserService {

    @Autowired
    private RbacUserMapper rbacUserMapper;

    @Autowired
    private RbacRoleMapper rbacRoleMapper;

    @Autowired
    private RbacUserRoleMapper rbacUserRoleMapper;

    @Override
    public UserVO getByAccount(String account) {
        UserVO userVO = new UserVO();
        RbacUser rbacUser = rbacUserMapper.getByAccount(account);
        if(userVO == null) {
            throw new BussinessException("用户不存在");
        }

        BeanUtil.copyProperties(rbacUser, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> findAll() {
        List<RbacUser> users = rbacUserMapper.selectList(null);
        return CollectionUtil.copyList(users, UserVO.class);
    }

    @Override
    public IPage<UserVO> find(RbacUserSearchDTO rbacUserSearchDTO) {
        QueryWrapper<RbacUser> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(rbacUserSearchDTO.getAccount())) {
            wrapper.eq("account", rbacUserSearchDTO.getAccount());
        }
        if(StringUtils.isNotBlank(rbacUserSearchDTO.getName())) {
            wrapper.like("name", rbacUserSearchDTO.getName());
        }
        if(rbacUserSearchDTO.getOrders() != null) {
            for(Order order : rbacUserSearchDTO.getOrders()) {
                boolean asc = true;
                if(order.getDirection() != null && order.getDirection().equals(Direction.DESC)) {
                    asc = false;
                }
                wrapper.orderBy(true, asc, order.getField());
            }
        }

        Page page = new Page(rbacUserSearchDTO.getPageNumber(), rbacUserSearchDTO.getPageSize());
        IPage<UserVO> rbacUserPage = rbacUserMapper.selectPage(page, wrapper);
        List<UserVO> userVOList = CollectionUtil.copyList(rbacUserPage.getRecords(), UserVO.class);
        rbacUserPage.setRecords(userVOList);
        return rbacUserPage;
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

        //保存至数据库
        RbacUser user = new RbacUser();
        BeanUtils.copyProperties(userDTO, user, "id");

        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setLastPasswordResetDate(new Date());
        rbacUserMapper.insert(user);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    @Transactional
    public int update(RbacUserDTO userDTO) {
        RbacUser rbacUser = rbacUserMapper.selectById(userDTO.getId());
        if(rbacUser == null) {
            throw new BussinessException("用户不存在");
        }

        //拷贝属性值, 忽略空值和特定属性值
        BeanUtil.copyProperties(userDTO, rbacUser, CopyOptions.create().ignoreNullValue()
                .setIgnoreProperties("id", "account", "password"));
        int result = rbacUserMapper.updateById(rbacUser);
        if(result != 1) {
            throw new BussinessException("更新用户失败");
        }
        return result;
    }

    @Override
    @Transactional
    public int delete(Long userId) {
        //删除用户
        int result = rbacUserMapper.deleteById(userId);
        //删除用户相关角色
        Map deleteMap = new HashMap() {{
            put("user_id", userId);
        }};
        rbacUserRoleMapper.deleteByMap(deleteMap);
        if(result != 1) {
            throw new BussinessException("删除用户失败");
        }
        return result;
    }

    @Override
    public UserVO getById(Long userId) {
        RbacUser rbacUser = rbacUserMapper.selectById(userId);
        if(rbacUser == null) {
            throw new BussinessException("用户不存在");
        }

        UserVO vo = new UserVO();
        BeanUtils.copyProperties(rbacUser, vo);
        return vo;
    }

    @Override
    public JwtUser getCurrent() {
        return JwtUtil.getCurrentUserInfo();
    }

    @Override
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
}
