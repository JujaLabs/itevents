package org.itevents.util.OneTimePassword;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
 public class OneTimePassword {

    private String password;
    private Date expirationDate;

    public OneTimePassword() {
    }

    public OneTimePassword(long lifetimeInHours) {
        Date creationDate = new Date();
        if (lifetimeInHours>0) {
            setExpirationDate(new Date(creationDate.getTime() + lifetimeInHours * 60 * 60 * 1000));
            setPassword(UUID.randomUUID().toString());
        } else {
            setPassword(UUID.randomUUID().toString());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public OneTimePassword generateOtp(long lifetimeInMinutes) {
       return new OneTimePassword(lifetimeInMinutes);
    }
}