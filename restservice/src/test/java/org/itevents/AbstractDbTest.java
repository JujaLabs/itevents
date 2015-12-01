package org.itevents;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContextTestAddon.xml"})
@TestPropertySource("classpath:test-local.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
public abstract class AbstractDbTest {
    //protected final static String PATH = "../../../../../dbunit/";
    protected final static String PATH = "file:src/test/resources/dbunit/";
    protected final static int ABSENT_ID = 0;
    protected final static int ID_1 = -1;

}
