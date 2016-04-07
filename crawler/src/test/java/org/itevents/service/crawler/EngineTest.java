package org.itevents.service.crawler;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vaa25 on 06.04.2016.
 */
@SuppressWarnings("PMD.AvoidFinalLocalVariable")
public final class EngineTest {
    private Engine engine;

    @Before
    public void setUp() {
        this.engine = new Engine();
    }

    @Test
    public void run() throws InterruptedException, ExecutionException {
        final String expected =
            "Result is: Integrations 1, Entities: 1\nSample Integration\n";
        final String returned = this.engine.run();
        assertEquals("Crawler fails", expected, returned);
    }

}