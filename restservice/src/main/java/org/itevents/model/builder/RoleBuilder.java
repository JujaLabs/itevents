package org.itevents.model.builder;

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

    public RoleBuilder id(int id) {
        this.id = id;
        return this;
    }

    public RoleBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoleBuilder but() {
        return aRole().id(id).name(name);
    }

    public Role build() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }
}
