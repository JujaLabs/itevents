package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.itevents.AbstractDbTest;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.mybatis.sql_session_dao.RoleMyBatisDao;
import org.itevents.model.Role;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 21.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/RoleMapperTest/RoleMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/RoleMapperTest/RoleMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class RoleMyBatisDaoDbTest extends AbstractDbTest {

    @Inject
    private RoleMyBatisDao roleMyBatisDao;

    @Test
    public void shouldFindRoleById() throws Exception {
        Role expectedRole = BuilderUtil.buildRoleGuest();
        Role returnedRole = roleMyBatisDao.getRole(ID_1);
        assertEquals(expectedRole, returnedRole);
    }

    @Test(expected = EntityNotFoundDaoException.class)
    public void shouldThrowEntityNotFoundDaoExceptionWhenRoleIsAbsent() throws Exception {
        roleMyBatisDao.getRole(ABSENT_ID);
    }
}
