package org.itevents.service.crawler;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vaa25 on 06.04.2016.
 */
public class EngineTest {
    private Engine engine;

    @Before
    public void setUp() throws Exception {
        this.engine = new Engine();
    }

    @Test
    public void run() throws Exception {
        String expected = "Result is: Integrations 1, Entities: 1\n" +
            "Sample Integration\n";
        String returned = this.engine.run();
        assertEquals(expected, returned);
    }

}