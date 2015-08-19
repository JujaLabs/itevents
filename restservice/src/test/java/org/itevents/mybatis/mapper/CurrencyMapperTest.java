package org.itevents.mybatis.mapper;

import org.itevents.model.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class CurrencyMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;

    @Inject
    private CurrencyMapper currencyMapper;

    @Test
    public void testGetCurrency1() throws Exception {
        Currency expectedCurrency = new Currency("USD");
        expectedCurrency.setId(ID_1);
        Currency returnedCurrency = currencyMapper.getCurrency(ID_1);
        assertEquals(expectedCurrency, returnedCurrency);
    }

    @Test
    public void testGetCurrency0() throws Exception {
        Currency returnedCurrency = currencyMapper.getCurrency(ID_0);
        assertNull(returnedCurrency);
    }
}
