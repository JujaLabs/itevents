package org.itevents.service;

import org.itevents.model.VisitLog;

import java.util.List;

public interface VisitLogService {

    void addVisit(int eventId, int userId);

    List<VisitLog> getVisits(int eventId);
}
