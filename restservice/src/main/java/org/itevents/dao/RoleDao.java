package org.itevents.dao;

import org.itevents.model.Role;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface RoleDao {

    Role getRole(int id);

    Role getRoleByName(String name);
}
