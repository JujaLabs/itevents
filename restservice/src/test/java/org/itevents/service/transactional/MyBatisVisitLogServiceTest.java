package org.itevents.service.transactional;

import org.itevents.dao.VisitLogDao;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.service.VisitLogService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MyBatisVisitLogServiceTest {

    @InjectMocks
    @Inject
    private VisitLogService visitLogService;
    @Mock
    private VisitLogDao visitLogDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindVisitlogById() throws Exception {
        VisitLog expectedVisitlog = BuilderUtil.buildVisitLogFirst();

        when(visitLogDao.getVisitLog(expectedVisitlog.getId())).thenReturn(expectedVisitlog);

        VisitLog returnedVisitlog = visitLogService.getVisitLog(expectedVisitlog.getId());

        verify(visitLogDao).getVisitLog(expectedVisitlog.getId());
        assertEquals(expectedVisitlog, returnedVisitlog);
    }

    @Test
    public void shouldGetAllVisitlogs() {
        visitLogService.getAllVisitLogs();

        verify(visitLogDao).getAllVisitLogs();
    }

    @Test
    public void shouldAddVisitlog() throws Exception {
        VisitLog testVisitLog = BuilderUtil.buildVisitLogTest();

        visitLogService.addVisitLog(testVisitLog);

        verify(visitLogDao).addVisitLog(testVisitLog);
    }

    @Test
    public void shouldRemoveVisitlog() throws ParseException {
        VisitLog expectedVisitlog = BuilderUtil.buildVisitLogTest();

        when(visitLogDao.getVisitLog(expectedVisitlog.getId())).thenReturn(expectedVisitlog);
        doNothing().when(visitLogDao).removeVisitLog(expectedVisitlog);

        VisitLog returnedVisitlog = visitLogService.removeVisitLog(expectedVisitlog);

        assertEquals(expectedVisitlog, returnedVisitlog);
    }

    @Test
    public void shouldNotRemoveVisitlogWhenItIsNotExisting() throws ParseException {
        VisitLog testVisitlog = BuilderUtil.buildVisitLogTest();

        when(visitLogDao.getVisitLog(testVisitlog.getId())).thenReturn(null);
        doNothing().when(visitLogDao).removeVisitLog(testVisitlog);

        VisitLog returnedVisitlog = visitLogService.removeVisitLog(testVisitlog);

        assertNull(returnedVisitlog);
    }

    @Test
    public void shouldFindVisitLogsByEvent() throws Exception {
        List<VisitLog> expectedVisitlogs = BuilderUtil.buildListVisitLogJava();
        Event eventJava = BuilderUtil.buildEventJava();

        when(visitLogDao.getVisitLogsByEvent(eventJava)).thenReturn(expectedVisitlogs);

        List<VisitLog> returnedVisitlogs = visitLogService.getVisitLogsByEvent(eventJava);

        verify(visitLogDao).getVisitLogsByEvent(eventJava);
        assertEquals(expectedVisitlogs, returnedVisitlogs);
    }
}
