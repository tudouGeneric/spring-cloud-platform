package org.honeybee.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.util.CollectionUtil;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.entity.RbacUser;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        RbacUser user = new RbacUser();
        BeanUtils.copyProperties(userDTO, user);

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
                .setIgnoreProperties("id", "account"));
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

}
