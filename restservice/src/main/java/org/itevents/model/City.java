package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "City")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "name", "location"})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private Location location;

    public City() {
    }

    public City(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (!name.equals(city.name)) return false;
        return location.equals(city.location);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}
