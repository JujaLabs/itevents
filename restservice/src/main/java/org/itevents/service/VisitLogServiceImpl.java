package org.itevents.service;

import org.itevents.mapper.VisitLogMapper;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Vlasov
 */

@Transactional
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Override
    public void addVisitLog(VisitLog visitLog) {
        try{
            visitLogMapper.addVisitLog(visitLog);
        }catch(DuplicateKeyException e){
            System.out.println(" Repeat events/" + visitLog.getEvent().getId() + "/users/" + visitLog.getUser().getId());
        }
    }

    @Override
    public List<VisitLog> getVisitsByEvent(Event event) {
        return visitLogMapper.getVisitsByEvent(event);
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
        VisitLog was = visitLogMapper.getVisitLog(visitLog.getId());
        if (was != null) {
            visitLogMapper.removeVisitLog(visitLog);
        }
        return was;
    }
}
