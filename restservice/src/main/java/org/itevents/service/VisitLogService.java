package org.itevents.service;

import org.itevents.model.VisitLog;

public interface VisitLogService {

    void addVisit(int eventId, int userId);

    VisitLog getVisits(int eventId);
}
