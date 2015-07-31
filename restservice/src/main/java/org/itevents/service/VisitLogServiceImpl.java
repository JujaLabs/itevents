package org.itevents.service;

import org.itevents.mapper.VisitLogMapper;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Vlasov
 */
@Service
@Transactional
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Override
    public void addVisitLog(VisitLog visitLog) {
        visitLogMapper.addVisitLog(visitLog);
    }

    @Override
    public List<VisitLog> getVisitLogsByEvent(Event event) {
        return visitLogMapper.getVisitLogsByEvent(event);
    }

    @Override
    public VisitLog getVisitLog(int id) {
        return visitLogMapper.getVisitLog(id);
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return visitLogMapper.getAllVisitLogs();
    }

    @Override
    public VisitLog removeVisitLog(VisitLog visitLog) {
        VisitLog deletingVisitLog = visitLogMapper.getVisitLog(visitLog.getId());
        if (deletingVisitLog != null) {
            visitLogMapper.removeVisitLog(visitLog);
        }
        return deletingVisitLog;
    }
}
