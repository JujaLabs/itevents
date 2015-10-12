package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogDao {

    VisitLog getVisitLog(int id);

    List<VisitLog> getAllVisitLogs();

    List<VisitLog> getVisitLogsByEvent(Event event);

    void addVisitLog(VisitLog visitLog);

    void removeVisitLog(VisitLog visitLog);

}