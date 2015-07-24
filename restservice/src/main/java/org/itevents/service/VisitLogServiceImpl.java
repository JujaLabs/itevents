package org.itevents.service;

import org.itevents.mapper.VisitLogMapper;
import org.itevents.model.Event;
import org.itevents.model.User;
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
    public void addVisit(Event event, User user) {
        try{
            visitLogMapper.addVisit(event, user);
        }catch(DuplicateKeyException e){
            System.out.println(" Repeat events/" + event.getId() + "/users/" + user.getId());
        }
    }

    @Override
    public List<VisitLog> getVisitsByEvent(Event event) {
        return visitLogMapper.getVisitsByEvent(event);
    }

    @Override
    public VisitLog getVisit(int id) {
        return visitLogMapper.getVisit(id);
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return visitLogMapper.getAllVisits();
    }

    @Override
    public VisitLog removeVisitLog(VisitLog visitLog) {
        VisitLog was = visitLogMapper.getVisit(visitLog.getId());
        return visitLogMapper.removeVisitLog(visitLog);
    }
}
