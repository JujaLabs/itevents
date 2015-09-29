package org.itevents;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContextTestAddon.xml"})
@TestPropertySource("classpath:test-local.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
@DbUnitConfiguration(
        databaseConnection = "dbUnitDatabaseConnection"
//        ,dataSetLoader = SpatialAwareFlatXmlDataSetLoader.class
)
@Transactional
public abstract class AbstractDbTest {
    protected final String PATH = "file:src/test/resources/dbunit/";
//    @Inject
//    private DataSource dataSource;

//    @PostConstruct
//    @Bean(name = "dbUnitDatabaseConnection")
//    public IDatabaseConnection set() throws SQLException, DatabaseUnitException {
//        IDatabaseConnection databaseConnection=new DatabaseConnection(dataSource.getConnection());
//        databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
//        return databaseConnection;
//    }


}
