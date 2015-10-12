package org.itevents.dao;

import org.itevents.model.Technology;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechnologyDao {

    Technology getTechnology(int id);

    List<Technology> getAllTechnologies();

    List<Technology> getTechnologiesByNames(String[] names);

    void addTechnology(Technology technology);

    void updateTechnology(Technology technology);

    void removeTechnology(Technology technology);

}
