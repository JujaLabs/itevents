package org.itevents.service.crawler;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.inject.Inject;

/**
 * Created by vaa25 on 06.04.2016.
 */
@SuppressWarnings("PMD.AvoidFinalLocalVariable")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:crawlerApplicationContext.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public final class CrawlerServiceTest {
    @Inject
    private CrawlerService crawlerService;
    @Test
    public void shouldReceiveResult() throws InterruptedException, ExecutionException {
        final String expected = String.format(
            "Result is: Integrations 1, Entities: 1%nSample Integration%n");
        final String returned = crawlerService.startCrawling();
        assertEquals("Crawler fails", expected, returned);
    }

}
