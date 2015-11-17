package org.itevents.service.feature;

import org.itevents.model.User;

/**
 * Created by alex-anakin on 18.11.2015.
 */
public interface MailService {
    String sendEmail(User user, String email);
}
