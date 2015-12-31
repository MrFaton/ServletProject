package com.nixsolutions.ponarin.service;

import com.nixsolutions.ponarin.entity.Role;

public interface RoleService {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);
    
    Role findById(int id);
}
