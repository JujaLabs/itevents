package org.itevents.service.crawler.integration;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vaa25 on 20.03.2016.
 */
public class StringLoaderTest {
    private StringLoader stringLoader;

    @Before
    public void setUp() throws Exception {
        this.stringLoader = new StringLoader();
    }

    @Test
    public void testLoad() throws Exception {
        String expectedString = "Test string";
        String returnedString = this.stringLoader.load("file_for_test.txt");
        assertEquals(expectedString, returnedString);
    }
}
