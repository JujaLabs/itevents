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
        OneTimePassword otp = new OneTimePassword();
        Date creationDate = new Date();
        otp.setExpirationDate(new Date(creationDate.getTime() + lifetimeInHours * 60 * 60 * 1000));
        otp.setPassword(UUID.randomUUID().toString());
        return otp;
    }
}