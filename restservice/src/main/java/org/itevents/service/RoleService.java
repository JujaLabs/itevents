package org.itevents.service;

import org.itevents.dao.model.Role;

public interface RoleService {

    Role getRole(int id);

    Role getRoleByName(String name);
}
