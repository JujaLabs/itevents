package org.itevents.dao.mybatis.session;

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
import org.itevents.utils.BuilderUtil;
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
@ContextConfiguration({"classpath:crawlerApplicationContext.xml",
    "classpath:dbUnitDatabaseConnection.xml"})
@TestPropertySource("classpath:test-crawler-local.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class IntegrationEventMyBatisDaoTest extends SqlSessionDaoSupport {
    private static final String PATH =
        "file:src/test/resources/dbunit/integrationEventMapperTest/";
    @Inject
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Inject
    private IntegrationEventMyBatisDao integrationEventDao;

    @PostConstruct
    public void init() throws Exception {
        setSqlSessionFactory(sqlSessionFactoryBean.getObject());
    }

    @Test
    @DatabaseSetup(value = IntegrationEventMyBatisDaoTest.PATH +
        "shouldAddIntegrationEvent_initial.xml",
        type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = IntegrationEventMyBatisDaoTest.PATH +
        "shouldAddIntegrationEvent_expected.xml",
        type = DatabaseOperation.DELETE_ALL)
    @ExpectedDatabase(value = IntegrationEventMyBatisDaoTest.PATH +
        "shouldAddIntegrationEvent_expected.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddIntegrationEvent() throws Exception {
        final IntegrationEvent integrationEvent = new IntegrationEvent();
        integrationEvent.setId(-1);
        integrationEvent.setIntegrationName("SampleIntegration");
        integrationEvent.setIntegrationEventUrl("www");
        integrationEvent.setEvent(BuilderUtil.buildEventJava());

        final int added = integrationEventDao
            .addIntegrationEvent(integrationEvent);

        assertEquals("addIntegrationEvent fails", 1, added);
    }

    @Test
    @DatabaseSetup(value = IntegrationEventMyBatisDaoTest.PATH +
        "shouldGetIntegrationEventsByIntegrationName_initial.xml",
        type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = IntegrationEventMyBatisDaoTest.PATH +
        "shouldGetIntegrationEventsByIntegrationName_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
    public void shouldGetIntegrationEventsByIntegrationName() throws Exception {
        final IntegrationEvent integrationEvent = new IntegrationEvent();
        integrationEvent.setId(-1);
        final String integrationName = "SampleIntegration";
        integrationEvent.setIntegrationName(integrationName);
        integrationEvent.setIntegrationEventUrl("www");
        integrationEvent.setEvent(BuilderUtil.buildEventJava());
        final List<IntegrationEvent> expectedList = new ArrayList<>();
        expectedList.add(integrationEvent);

        final List<IntegrationEvent> returnedList =
            integrationEventDao
                .getIntegrationEventsByIntegrationName(integrationName);

        assertEquals("IntegrationEventMyBatisDao." +
                "getIntegrationEventsByIntegrationName() fails",
            expectedList, returnedList);
    }
}
