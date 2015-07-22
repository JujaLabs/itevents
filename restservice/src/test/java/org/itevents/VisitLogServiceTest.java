package org.itevents;

import org.itevents.model.VisitLog;
import org.itevents.service.VisitLogService;
import org.itevents.service.VisitLogServiceImpl;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

public class VisitLogServiceTest {

    private static VisitLogService service;

    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = context.getBean("visitLogService", VisitLogServiceImpl.class);
    }

    @AfterClass
    public static void teardown() {
        service = null;
    }

    @Test
    @Ignore
    public void testGetVisitLogByEventId() {
        Collection<VisitLog> events = service.getVisits(1);
        Assert.assertNotNull(events);
    }
}
