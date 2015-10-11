package org.itevents_utils.builder;

import org.itevents.model.Role;

/**
 * Created by vaa25 on 03.10.2015.
 */
public class RoleBuilder {
    private int id;
    private String name;

    private RoleBuilder() {
    }

    public static RoleBuilder aRole() {
        return new RoleBuilder();
    }

    public RoleBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public RoleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RoleBuilder but() {
        return aRole().setId(id).setName(name);
    }

    public Role build() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }
}
