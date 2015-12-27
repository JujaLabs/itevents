package org.itevents.service.sendmail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.*;
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
    public static final Integer FILTER_RANGE_IN_DAYS = 7;
    public static final Integer COUNT_OF_EVENTS_IN_EMAIL = 10;

    @Inject
    private EventService eventService;

    @Inject
    private FilterService filterService;

    @Inject
    private MailService mailService;

    @Inject
    private UserService userService;

    @Inject
    private MailBuilderUtil mailBuilderUtil;

    @Override
    public void performNotify()  {
        List<User> users = userService.getSubscribedUsers();
        for (User user : users) {
            Filter filter = filterService.getLastFilterByUser(user);
            List<Event> events = eventService.getFilteredEventsInDateRangeWithRating(filter);
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
