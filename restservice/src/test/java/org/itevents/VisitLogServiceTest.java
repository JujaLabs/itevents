package org.itevents;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.service.EventService;
import org.itevents.service.VisitLogService;
import org.itevents.service.VisitLogServiceImpl;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

public class VisitLogServiceTest {

    private static VisitLogService visitLogService;
    private static EventService eventService;

    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        visitLogService = context.getBean("visitLogService", VisitLogServiceImpl.class);
        eventService = context.getBean("eventService", EventService.class);
    }

    @AfterClass
    public static void teardown() {
        visitLogService = null;
    }

    @Test
    @Ignore
    public void testGetVisitLogByEvent() {
        Event event = eventService.getEvent(1);
        Collection<VisitLog> visitLogs = visitLogService.getVisitsByEvent(event);

        Assert.assertNotNull(visitLogs);
    }
}
