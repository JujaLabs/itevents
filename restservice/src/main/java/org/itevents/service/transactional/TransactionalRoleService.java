package org.itevents.service.transactional;

import org.itevents.dao.RoleDao;
import org.itevents.model.Role;
import org.itevents.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("roleService")
@Transactional
public class TransactionalRoleService implements RoleService {

    @Inject
    private RoleDao roleDao;

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public Role getRole(int id) {
        return roleDao.getRole(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role removeRole(Role role) {
        Role deletingRole = roleDao.getRole(role.getId());
        if (deletingRole != null) {
            roleDao.removeRole(role);
        }
        return deletingRole;
    }
}
