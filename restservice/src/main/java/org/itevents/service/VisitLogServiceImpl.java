package org.itevents.service;

import org.itevents.mapper.VisitLogMapper;
import org.itevents.model.VisitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Alexander Vlasov
 */

@Transactional
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;


    @Override
    public void addVisit(int eventId, int userId) {
        try{
            visitLogMapper.addVisit(eventId, userId);
        }catch(DuplicateKeyException e){
            System.out.println(" Repeat events/" + eventId + "/users/" + userId);
        }
    }

    @Override
    public Collection<VisitLog> getVisits(int eventId) {
        return visitLogMapper.selectVisitsByEvent(eventId);
    }


}
