package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;

import java.util.Set;

public interface VisitLogDao {

    VisitLog getVisitLog(int id);

    Set<VisitLog> getAllVisitLogs();

    Set<VisitLog> getVisitLogsByEvent(Event event);

    void addVisitLog(VisitLog visitLog);

    void removeVisitLog(VisitLog visitLog);

}