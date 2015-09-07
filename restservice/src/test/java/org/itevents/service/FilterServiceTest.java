package org.itevents.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.itevents.model.City;
import org.itevents.model.Filter;
import org.itevents.model.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
public class FilterServiceTest {

    @Inject
    FilterService filterService;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testGetFilterByUser(){
        Filter expectedFilter = new Filter();
        City expectedCity = new City("Kyiv", null, new Location(50.4505, 30.523));
        expectedCity.setId(1);
        expectedFilter.setCity(expectedCity);
        expectedFilter.setFree(true);
        Filter actualFilter = filterService.getFilterById(1);
        assertEquals(expectedFilter, actualFilter);
    }
}
