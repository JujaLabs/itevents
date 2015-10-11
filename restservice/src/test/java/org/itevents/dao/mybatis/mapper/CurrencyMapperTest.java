package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Currency;
import org.itevents_utils.BuilderUtil;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

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
        Currency expectedCurrency = BuilderUtil.buildCurrencyUsd();
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

    @Test
    @DatabaseSetup(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddCurrency_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddCurrency() throws Exception {
        Currency testCurrency = BuilderUtil.buildCurrencyTest();
        currencyMapper.addCurrency(testCurrency);
    }

    @Test(expected = DuplicateKeyException.class)
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCurrency_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddCurrency_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testAddExistingCurrency() throws Exception {
        Currency testCurrency = BuilderUtil.buildCurrencyTest();
        currencyMapper.addCurrency(testCurrency);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetAllCurrencies() {
        int expectedSize = SIZE_3;
        int returnedSize = currencyMapper.getAllCurrencies().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testAddExistingCurrency_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "CurrencyMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveCurrencySuccess() {
        Currency testCurrency = BuilderUtil.buildCurrencyTest();
        currencyMapper.removeCurrency(testCurrency);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "CurrencyMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "CurrencyMapperTest_initial.xml", type = DatabaseOperation.DELETE_ALL)
    public void testRemoveCurrencyFail() {
        Currency testCurrency = BuilderUtil.buildCurrencyTest();
        currencyMapper.removeCurrency(testCurrency);
    }
}
