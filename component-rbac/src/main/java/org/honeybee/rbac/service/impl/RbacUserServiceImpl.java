package org.honeybee.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import org.honeybee.rbac.service.RbacUserService;
import org.honeybee.rbac.util.JwtUtil;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RbacUserServiceImpl extends ServiceImpl<RbacUserMapper, RbacUser> implements RbacUserService {

    @Autowired
    private RbacUserMapper rbacUserMapper;

    @Autowired
    private RbacRoleMapper rbacRoleMapper;

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
    public IPage<UserVO> find(RbacUserSearchDTO rbacUserSearchDTO, Page page) {
        QueryWrapper<RbacUser> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(rbacUserSearchDTO.getAccount())) {
            wrapper.eq("account", rbacUserSearchDTO.getAccount());
        }
        if(StringUtils.isNotBlank(rbacUserSearchDTO.getName())) {
            wrapper.like("name", rbacUserSearchDTO.getName());
        }

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
    public UserVO create(RbacUserDTO userDTO) {
        RbacUser user = new RbacUser();
        BeanUtils.copyProperties(userDTO, user);

        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        rbacUserMapper.insert(user);
        return null;
    }

    @Override
    public int update(RbacUserDTO userDTO) {
        return 0;
    }

    @Override
    public int delete(Long userId) {
        return 0;
    }

    @Override
    public UserVO getById(Long userId) {
        return null;
    }

    @Override
    public JwtUtil getCurrent() {
        return null;
    }

}
