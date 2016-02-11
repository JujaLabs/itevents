package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.VisitLogDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.VisitLog;
import org.itevents.service.VisitLogService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexander Vlasov
 */
@Service("visitLogService")
@Transactional
public class MyBatisVisitLogService implements VisitLogService {

    private static final Logger LOGGER = LogManager.getLogger();

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
    public List<VisitLog> getVisitLogsByDate(Date date) {
        List<VisitLog> allVisitLog = visitLogDao.getAllVisitLogs();
        List<VisitLog> resultVisitLog = new LinkedList<>();
        for (VisitLog v: allVisitLog) {
            if (v.getDate().equals( date ))
                resultVisitLog.add(v);
        }
        return resultVisitLog;
    }

    @Override
    public VisitLog getVisitLog(int id) {
        try {
            return visitLogDao.getVisitLog(id);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return visitLogDao.getAllVisitLogs();
    }
}
