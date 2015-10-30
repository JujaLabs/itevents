package org.itevents.service.transactional;

import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.UserService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class MyBatisUserServiceTest {

    @InjectMocks
    @Inject
    private UserService userService;
    @Mock
    private UserDao userDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnUserEvents() throws Exception{
//        TODO: Õ≈œ–¿¬»À‹ÕŒ, œ≈–≈ƒ≈À¿“‹!
        User user = BuilderUtil.buildUserAnakin();
        Event event = BuilderUtil.buildEventJs();
        event.addVisitor(user);
        List expectedEvents = event.getVisitors();
        List returnedEvents = new ArrayList<>();
        when(userDao.getUserEvents(user)).thenReturn(returnedEvents);
        returnedEvents = userDao.getUserEvents(user);
        verify(userDao).getUserEvents(user);
        assertEquals(expectedEvents,returnedEvents);
    }
}
