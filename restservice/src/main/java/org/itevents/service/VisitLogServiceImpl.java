package org.itevents.service;

import org.itevents.mapper.VisitLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

/**
 * @author Alexander Vlasov
 */
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    public VisitLogMapper getVisitLogMapper() {
        return visitLogMapper;
    }

    public void setVisitLogMapper(VisitLogMapper visitLogMapper) {
        this.visitLogMapper = visitLogMapper;
    }

    @Override
    public void addVisit(int eventId, int userId) {
        try{
            visitLogMapper.addVisit(eventId, userId);
        }catch(DuplicateKeyException e){

        }
    }
}
