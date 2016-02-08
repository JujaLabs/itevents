package org.itevents.dao;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.VisitLog;

import java.util.List;

public interface VisitLogDao {

    VisitLog getVisitLog(int id);

    List<VisitLog> getAllVisitLogs();

    List<VisitLog> getVisitLogsByEvent(Event event);

    void addVisitLog(VisitLog visitLog);
}