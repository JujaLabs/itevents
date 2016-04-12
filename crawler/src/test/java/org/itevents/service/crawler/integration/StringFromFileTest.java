package org.itevents.service.crawler.integration;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vaa25 on 20.03.2016.
 */
@SuppressWarnings("PMD.AvoidFinalLocalVariable")
public final class StringFromFileTest {
    private StringFromFile stringFromFile;

    @Before
    public void setUp() {
        this.stringFromFile = new StringFromFile("file_for_test.txt");
    }

    @Test
    public void testLoad() {
        final String expected = "Test string";
        final String returned = this.stringFromFile.getValue();
        assertEquals("can't load 'file_for_test.txt'", expected, returned);
    }
}
