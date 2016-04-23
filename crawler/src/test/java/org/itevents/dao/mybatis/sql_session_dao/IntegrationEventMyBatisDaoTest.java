package org.itevents.dao.mybatis.sql_session_dao;

import static org.junit.Assert.assertEquals;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.itevents.dao.model.IntegrationEvent;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Created by vaa25 on 22.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml",
    "classpath:dbUnitDatabaseConnection.xml"})
@TestPropertySource("classpath:test-crawler-local.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class IntegrationEventMyBatisDaoTest extends SqlSessionDaoSupport {
    @Inject
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Inject
    private IntegrationEventMyBatisDao integrationEventMyBatisDao;

    @PostConstruct
    public void init() throws Exception {
        setSqlSessionFactory(sqlSessionFactoryBean.getObject());
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dbunit/IntegrationEventMapperTest/shouldAddIntegrationEvent_initial.xml",
        type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = "file:src/test/resources/dbunit/IntegrationEventMapperTest/shouldAddIntegrationEvent_expected.xml",
        type = DatabaseOperation.DELETE_ALL)
    @ExpectedDatabase(value = "file:src/test/resources/dbunit/IntegrationEventMapperTest/shouldAddIntegrationEvent_expected.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddIntegrationEvent() throws Exception {
        IntegrationEvent integrationEvent = new IntegrationEvent();
        integrationEvent.setId(-1);
        integrationEvent.setIntegrationName("SampleIntegration");
        integrationEvent.setIntegrationEventUrl("www");
        integrationEvent.setEvent(BuilderUtil.buildEventJava());
        integrationEventMyBatisDao.addIntegrationEvent(integrationEvent);
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dbunit/IntegrationEventMapperTest/shouldGetIntegrationEventsByIntegrationName_initial.xml",
        type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = "file:src/test/resources/dbunit/IntegrationEventMapperTest/shouldGetIntegrationEventsByIntegrationName_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
    public void shouldGetIntegrationEventsByIntegrationName() throws Exception {
        String integrationName = "SampleIntegration";
        IntegrationEvent integrationEvent = new IntegrationEvent();
        integrationEvent.setId(-1);
        integrationEvent.setIntegrationName(integrationName);
        integrationEvent.setIntegrationEventUrl("www");
        integrationEvent.setEvent(BuilderUtil.buildEventJava());
        List<IntegrationEvent> expectedList = new ArrayList<>();
        expectedList.add(integrationEvent);

        List<IntegrationEvent> returnedList = integrationEventMyBatisDao.getIntegrationEventsByIntegrationName(integrationName);
        assertEquals(expectedList, returnedList);
    }

}