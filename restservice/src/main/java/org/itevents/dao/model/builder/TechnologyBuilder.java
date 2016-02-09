package org.itevents.dao.model.builder;

import org.itevents.dao.model.Technology;

/**
 * Created by vaa25 on 03.10.2015.
 */
public class TechnologyBuilder {
    private int id;
    private String name;

    private TechnologyBuilder() {
    }

    public static TechnologyBuilder aTechnology() {
        return new TechnologyBuilder();
    }

    public TechnologyBuilder id(int id) {
        this.id = id;
        return this;
    }

    public TechnologyBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TechnologyBuilder but() {
        return aTechnology().id(id).name(name);
    }

    public Technology build() {
        Technology technology = new Technology();
        technology.setId(id);
        technology.setName(name);
        return technology;
    }
}
