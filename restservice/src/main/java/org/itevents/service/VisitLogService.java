package org.itevents.service;

import org.itevents.model.VisitLog;

import java.util.Collection;

public interface VisitLogService {

    void addVisit(int eventId, int userId);

    Collection<VisitLog> getVisits(int eventId);
}
