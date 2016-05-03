package org.itevents.service.crawler;

import static org.junit.Assert.assertEquals;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


/**
 * Created by vaa25 on 06.04.2016.
 */
@SuppressWarnings("PMD.AvoidFinalLocalVariable")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:crawlerApplicationContext.xml",
    "classpath:dbUnitDatabaseConnection.xml"})
@TestPropertySource(locations = "classpath:test-crawler-local.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
public final class CrawlerServiceTest {
    @Inject
    private CrawlerService crawlerService;

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dbunit/eventServiceTest/shouldReceiveResult_initial.xml",
        type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = "file:src/test/resources/dbunit/eventServiceTest/shouldReceiveResult_expected.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = "file:src/test/resources/dbunit/eventServiceTest/shouldReceiveResult_expected.xml",
        type = DatabaseOperation.DELETE_ALL)
    public void shouldReceiveResult() throws InterruptedException, ExecutionException {
        final String expected = String.format(
            "Result is: Integrations 1, Events: 1%nSample Integration%n");
        final String returned = crawlerService.startCrawling();
        assertEquals("Crawler fails", expected, returned);
    }

}
