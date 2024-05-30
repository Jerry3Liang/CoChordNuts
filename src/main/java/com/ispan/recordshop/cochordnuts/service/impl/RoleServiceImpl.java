package com.ispan.recordshop.cochordnuts.service.impl;

import com.ispan.recordshop.cochordnuts.dao.RoleDao;
import com.ispan.recordshop.cochordnuts.model.Role;
import com.ispan.recordshop.cochordnuts.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }
}
