package org.itevents.service;

import org.itevents.model.Technology;

import java.util.Set;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechnologyService {

    Technology getTechnology(int id);

    Set<Technology> getAllTechnologies();

    Set<Technology> getTechnologiesByNames(String[] names);

    void addTechnology(Technology technology);

    void updateTechnology(Technology technology);

    Technology removeTechnology(Technology technology);

}
