package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;

import java.util.Set;

public interface VisitLogService {

    void addVisitLog(VisitLog visitLog);

    Set<VisitLog> getVisitLogsByEvent(Event event);

    VisitLog getVisitLog(int id);

    Set<VisitLog> getAllVisitLogs();

    VisitLog removeVisitLog(VisitLog visitLog);
}
