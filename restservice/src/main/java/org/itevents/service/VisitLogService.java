package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogService {

    void addVisitLog(Event event, User user);

    List<VisitLog> getVisitsByEvent(Event event);


    VisitLog getVisitLog(int id);

    List<VisitLog> getAllVisitLogs();

    VisitLog removeVisitLog(VisitLog visitLog);
}
