package org.itevents.mapper;

import org.itevents.model.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CurrencyMapperTest {
    @Autowired
    private CurrencyMapper currencyMapper;

    @Test
    public void testGetCurrency1() throws Exception {
        assertEquals(new Currency(1, "FREE"), currencyMapper.getCurrency(1));
    }

    @Test
    public void testGetCurrency0() throws Exception {
        assertNull(currencyMapper.getCurrency(0));
    }
}
