package org.itevents.service.transactional;

import org.itevents.dao.VisitLogDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.VisitLog;
import org.itevents.service.VisitLogService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Alexander Vlasov
 */
@Service("visitLogService")
@Transactional
public class MyBatisVisitLogService implements VisitLogService {

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
        try {
            return visitLogDao.getVisitLog(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return visitLogDao.getAllVisitLogs();
    }
}
