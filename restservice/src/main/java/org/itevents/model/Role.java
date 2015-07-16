package org.itevents.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "roles")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "name"})

public class Role {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private String name;

    public Role() {
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event ");
        sb.append("id=").append(id);
        sb.append(", name='").append(name);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role roles = (Role) o;

        if (id != roles.id) return false;
        return name.equals(roles.name);

    }

    @Override
    public int hashCode() {
        int result = id ^ (id >>> 32);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
