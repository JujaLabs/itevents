package org.itevents.service;

import org.itevents.dao.VisitLogDao;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Alexander Vlasov
 */
@Service("visitLogService")
@Transactional
public class VisitLogServiceImpl implements VisitLogService {

    @Inject
    private VisitLogDao visitLogDao;

    @Override
    public void addVisitLog(VisitLog visitLog) {
        visitLogDao.addVisitLog(visitLog);
    }

    @Override
    public List<VisitLog> getVisitLogsByEvent(Event event) {
        return visitLogDao.getVisitLogsByEvent(event);
    }

    @Override
    public VisitLog getVisitLog(int id) {
        return visitLogDao.getVisitLog(id);
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return visitLogDao.getAllVisitLogs();
    }

    @Override
    public VisitLog removeVisitLog(VisitLog visitLog) {
        VisitLog deletingVisitLog = visitLogDao.getVisitLog(visitLog.getId());
        if (deletingVisitLog != null) {
            visitLogDao.removeVisitLog(visitLog);
        }
        return deletingVisitLog;
    }
}
