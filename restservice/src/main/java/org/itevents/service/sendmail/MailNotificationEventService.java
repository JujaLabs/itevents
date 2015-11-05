package org.itevents.service.sendmail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.MailFilterService;
import org.itevents.service.NotificationEventService;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by ramax on 11/5/15.
 */
@Service("notificationEventService")
public class MailNotificationEventService implements NotificationEventService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private MailFilterService mailFilterService;

    @Inject
    private MailService mailService;

    @Inject
    private UserDao userDao;

    @Inject
    private MailBuilderUtil mailBuilderUtil;

    @Override
    public void execute()  {
        List<User> users = userDao.getAllUsers();
        for (User u: users) {
            List<Event> events = mailFilterService.getFilteredEventsInDateRangeWithRating(u.getFilter());
            String htmlLater = buildMail(events);
            mailService.sendMail(htmlLater, u.getLogin());
        }
    }

    private String buildMail(List<Event> events) {
        try {
            return mailBuilderUtil.buildHtmlFromEventsList(events);
        } catch (Exception e) {
            LOGGER.error("Error build mail for user");
            throw new RuntimeException("Build mail for user error: ",e);
        }
    }

}
