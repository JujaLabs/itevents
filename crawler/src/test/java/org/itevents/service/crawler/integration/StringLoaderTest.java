package org.itevents.service.crawler.integration;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vaa25 on 20.03.2016.
 */
@SuppressWarnings("PMD.AvoidFinalLocalVariable")
public final class StringLoaderTest {
    private StringLoader stringLoader;

    @Before
    public void setUp() {
        this.stringLoader = new StringLoader();
    }

    @Test
    public void testLoad() {
        final String expected = "Test string";
        final String returned = this.stringLoader.load("file_for_test.txt");
        assertEquals("can't load 'file_for_test.txt'", expected, returned);
    }
}
