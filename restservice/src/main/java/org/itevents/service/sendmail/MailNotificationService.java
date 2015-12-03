package org.itevents.service.sendmail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.FilterService;
import org.itevents.service.MailFilterService;
import org.itevents.service.NotificationService;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ramax on 11/5/15.
 */
@Service("notificationEventService")
public class MailNotificationService implements NotificationService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private MailFilterService mailFilterService;

    @Inject
    private FilterService filterService;

    @Inject
    private MailService mailService;

    @Inject
    private UserDao userDao;

    @Inject
    private MailBuilderUtil mailBuilderUtil;

    @Override
    public void performNotify()  {
        List<User> users = userDao.getSubscribedUsers();
        for (User user : users) {
            Filter filter = filterService.getLastFilterByUser(user);
            List<Event> events = mailFilterService.getFilteredEventsInDateRangeWithRating(filter);
            if (!events.isEmpty()) {
                String htmlLetter = buildMail(events);
                mailService.sendMail(htmlLetter, user.getLogin());
            }
        }
    }

    private String buildMail(List<Event> events) {
        try {
            return mailBuilderUtil.buildHtmlFromEventsList(events);
        } catch (Exception e) {
            LOGGER.error("Error build mail for user");
            throw new BuildMailException("Build mail for user error:", e);
        }
    }

}
