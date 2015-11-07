package org.itevents.service.transactional;

import org.itevents.model.VisitLog;
import org.itevents.service.VisitLogService;
import org.itevents.service.mail.MailReminderAboutEventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ramax on 11/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class MailReminderAboutEventServiceTest {

    @InjectMocks
    @Inject
    private MailReminderAboutEventService mailReminderAboutEventService;

    @Mock
    private VisitLogService visitLogService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void executeTest() {
        List<VisitLog> visitLogs = Arrays.asList(new VisitLog(), new VisitLog());
        when(visitLogService.getVisitLogsByDate(any())).thenReturn(visitLogs);

        // TODO
    }

}
