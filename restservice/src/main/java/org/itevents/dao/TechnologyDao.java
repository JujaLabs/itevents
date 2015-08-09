package org.itevents.dao;

import org.itevents.model.Technology;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechnologyDao {

    Technology getTechTag(int id);

    List<Technology> getAllTechTags();

    List<Technology> getSeveralTechTags(String[] names);

    void addTechTag(Technology technology);

    void updateTechTag(Technology technology);

    void removeTechTag(Technology technology);

}
