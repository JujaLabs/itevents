package org.itevents.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "currency")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "name"})

public class Currency {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private String name;

    public Currency() {
    }

    public Currency(String name) {
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
        final StringBuilder sb = new StringBuilder("Currency ");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency role = (Currency) o;

        if (id != role.id) return false;
        return name.equals(role.name);

    }

    @Override
    public int hashCode() {
        int result = id ^ (id >>> 32);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
