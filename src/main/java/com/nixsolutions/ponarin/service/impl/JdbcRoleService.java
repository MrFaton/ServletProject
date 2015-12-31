package com.nixsolutions.ponarin.service.impl;

import com.nixsolutions.ponarin.dao.RoleDao;
import com.nixsolutions.ponarin.dao.impl.JdbcRoleDao;
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.service.RoleService;

public class JdbcRoleService implements RoleService {
    private RoleDao roleDao = new JdbcRoleDao();

    @Override
    public void create(Role role) {
        roleDao.create(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void remove(Role role) {
        roleDao.remove(role);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Role findById(int id) {
        JdbcRoleDao jdbcRoleDao = (JdbcRoleDao) roleDao;
        return jdbcRoleDao.findByRoleId(id);
    }

}
