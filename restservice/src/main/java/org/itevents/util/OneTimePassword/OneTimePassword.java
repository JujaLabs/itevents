package org.itevents.util.OneTimePassword;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Scope("prototype")
 public class OneTimePassword {

    private String password;
    private Date expirationDate;

    public OneTimePassword() {
        setPassword(UUID.randomUUID().toString());
    }

    public OneTimePassword(long lifetimeInHours) {
        Date creationDate = new Date();
        setExpirationDate(new Date(creationDate.getTime() + lifetimeInHours * 60 * 60 * 1000));
        setPassword(UUID.randomUUID().toString());
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

    public OneTimePassword generateOtp(long lifetimeInHours) {
       return new OneTimePassword(lifetimeInHours);
    }
}