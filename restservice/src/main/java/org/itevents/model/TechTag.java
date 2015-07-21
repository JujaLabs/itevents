package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "TechTag")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "name"})
public class TechTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private String name;

    public TechTag() {
    }

    public TechTag(int id, String name) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechTag techTag = (TechTag) o;

        if (id != techTag.id) return false;
        return name.equals(techTag.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TechTag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
