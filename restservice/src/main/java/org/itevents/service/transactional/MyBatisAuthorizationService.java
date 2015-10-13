package org.itevents.service.transactional;

import org.itevents.dao.AuthorizationDao;
import org.itevents.model.Authorization;
import org.itevents.service.AuthorizationService;

import javax.inject.Inject;
import java.util.List;

public class MyBatisAuthorizationService implements AuthorizationService {

    @Inject
    private AuthorizationDao authorizationDao;

    @Override
    public void addAuthorization(Authorization authorization) {
        authorizationDao.addAuthorization(authorization);
    }

    @Override
    public Authorization getAuthorization(int id) {
        return authorizationDao.getAuthorization(id);
    }

    @Override
    public List<Authorization> getAllAuthorization() {
        return authorizationDao.getAllAuthorization();
    }

    @Override
    public Authorization removeAuthorization(Authorization authorization) {
        Authorization deletingAuthorization = authorizationDao.getAuthorization(authorization.getId());
        if (deletingAuthorization != null) {
            authorizationDao.removeAuthorization(authorization);
        }
        return deletingAuthorization;
    }
}