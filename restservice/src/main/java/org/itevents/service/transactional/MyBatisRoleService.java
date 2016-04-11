package org.itevents.service.transactional;

import org.itevents.dao.RoleDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Role;
import org.itevents.service.RoleService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("roleService")
@Transactional
public class MyBatisRoleService implements RoleService {

    @Inject
    private RoleDao roleDao;

    @Override
    public Role getRole(int id) {
        try {
            return roleDao.getRole(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
