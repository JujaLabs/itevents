package org.itevents.service.sendmail;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.service.EventService;
import org.itevents.service.FilterService;
import org.itevents.service.NotificationService;
import org.itevents.service.UserService;
import org.itevents.util.mail.MailBuilderUtil;
import org.itevents.util.mail.MailBuilderUtilException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by ramax on 11/5/15.
 */
@Service("notificationEventService")
public class MailNotificationService implements NotificationService {

    @Value("${event.filter.range.in.days}")
    private Integer FILTER_RANGE_IN_DAYS;

    @Value("${event.count.of.events.in.email}")
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
        } catch (MailBuilderUtilException e) {
            throw new NotificationServiceException(e.getMessage(), e);
        }
    }

    private List<Event> getFilteredEventsByUser(User user) {
        Filter filter = filterService.getLastFilterByUser(user);

        filter.setLimit(COUNT_OF_EVENTS_IN_EMAIL);
        filter.setRangeInDays(FILTER_RANGE_IN_DAYS);

        return eventService.getFilteredEventsWithRating(filter);
    }

}
