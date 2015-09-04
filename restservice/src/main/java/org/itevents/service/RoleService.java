package org.itevents.service;

import org.itevents.model.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);

    Role getRole(int id);

    List<Role> getAllRoles();

    Role removeRole(Role role);
}
