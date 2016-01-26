package org.itevents.service.sendmail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.*;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by ramax on 11/5/15.
 */
@Service("notificationEventService")
public class MailNotificationService implements NotificationService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${event_filter_range_in_days}")
    private Integer FILTER_RANGE_IN_DAYS;

    @Value("${event_count_of_events_in_email}")
    private Integer COUNT_OF_EVENTS_IN_EMAIL;

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
            List<Event> events = getFilteredEventsByUser(user);
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

    private List<Event> getFilteredEventsByUser(User user) {
        Filter filter = filterService.getLastFilterByUser(user);

        filter.setLimit(COUNT_OF_EVENTS_IN_EMAIL);
        filter.setRangeInDays(FILTER_RANGE_IN_DAYS);

        return eventService.getFilteredEventsWithRating(filter);
    }

}
