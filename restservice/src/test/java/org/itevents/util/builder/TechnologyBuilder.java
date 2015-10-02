package org.itevents.util.builder;

import org.itevents.model.Technology;

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

    public TechnologyBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TechnologyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TechnologyBuilder but() {
        return aTechnology().setId(id).setName(name);
    }

    public Technology build() {
        Technology technology = new Technology();
        technology.setId(id);
        technology.setName(name);
        return technology;
    }
}
