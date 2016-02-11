package org.itevents.service;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.VisitLog;

import java.util.Date;
import java.util.List;

public interface VisitLogService {

    void addVisitLog(VisitLog visitLog);

    List<VisitLog> getVisitLogsByEvent(Event event);

    List<VisitLog> getVisitLogsByDate(Date date);

    VisitLog getVisitLog(int id);

    List<VisitLog> getAllVisitLogs();
}
