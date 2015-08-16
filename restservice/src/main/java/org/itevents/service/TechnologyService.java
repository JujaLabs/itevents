package org.itevents.service;

import org.itevents.model.Technology;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechnologyService {

    Technology getTechTag(int id);

    List<Technology> getAllTechTags();

    List<Technology> getSeveralTechnologiesByName(String[] names);

    void addTechTag(Technology technology);

    void updateTechTag(Technology technology);

    Technology removeTechTag(Technology technology);

}
