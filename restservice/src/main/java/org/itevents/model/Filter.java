package org.itevents.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by vaa25 on 28.07.2015.
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.NONE)
public class Filter {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private int id;
    @XmlElement
    private City city;
    @XmlElement
    private Boolean free;
    @XmlElement
    private Double longitude;
    @XmlElement
    private Double latitude;
    @XmlElement
    private Integer radius;
    @XmlElement
    private List<Technology> technologies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean payed) {
        this.free = payed;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }
}
