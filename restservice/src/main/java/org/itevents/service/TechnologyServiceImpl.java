package org.itevents.service;

import org.itevents.dao.TechnologyDao;
import org.itevents.model.Technology;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
@Service("techTagService")
@Transactional
public class TechnologyServiceImpl implements TechnologyService {

    @Inject
    private TechnologyDao technologyDao;

    @Override
    public Technology getTechTag(int id) {
        return technologyDao.getTechTag(id);
    }

    @Override
    public List<Technology> getAllTechTags() {
        return technologyDao.getAllTechTags();
    }

    @Override
    public List<Technology> getSeveralTechnologiesByName(String[] names) {
        return technologyDao.getSeveralTechTags(names);
    }

    @Override
    public void addTechTag(Technology technology) {
        technologyDao.addTechTag(technology);
    }

    @Override
    public void updateTechTag(Technology technology) {
        technologyDao.updateTechTag(technology);
    }

    @Override
    public Technology removeTechTag(Technology technology) {
        Technology deletingTechnology = technologyDao.getTechTag(technology.getId());
        if (deletingTechnology != null) {
            technologyDao.removeTechTag(technology);
        }
        return deletingTechnology;
    }
}
