package org.itevents.service.transactional;

import org.itevents.dao.TechnologyDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Technology;
import org.itevents.service.TechnologyService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
@Service("technologyService")
@Transactional
public class MyBatisTechnologyService implements TechnologyService {

    @Inject
    private TechnologyDao technologyDao;

    @Override
    public Technology getTechnology(int id) {
        try {
            return technologyDao.getTechnology(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Technology> getAllTechnologies() {
        return technologyDao.getAllTechnologies();
    }

    @Override
    public List<Technology> getTechnologiesByNames(String[] names) {
        return technologyDao.getTechnologiesByNames(names);
    }

    @Override
    public void addTechnology(Technology technology) {
        technologyDao.addTechnology(technology);
    }
}
