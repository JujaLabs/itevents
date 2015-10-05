package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.itevents.AbstractDbTest;
import org.itevents.model.Currency;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */

public class CurrencyMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "CurrencyMapperTest/";
    @Inject
    private CurrencyMapper currencyMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetCurrencySuccess() throws Exception {
        Currency expectedCurrency = new Currency("USD");
        Currency returnedCurrency = currencyMapper.getCurrency(ID_1);
        assertEquals(expectedCurrency, returnedCurrency);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetCurrencyFail() throws Exception {
        Currency returnedCurrency = currencyMapper.getCurrency(ID_0);
        assertNull(returnedCurrency);
    }
}
