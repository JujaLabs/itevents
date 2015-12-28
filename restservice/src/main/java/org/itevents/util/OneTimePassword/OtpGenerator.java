package org.itevents.util.OneTimePassword;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class OtpGenerator implements Serializable {

    private String password;
    private Date creationDate;
    private Date expirationDate;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void generateOtp(long lifetimeInMinutes) {
        setCreationDate(new Date());
        if (lifetimeInMinutes>0){
            setExpirationDate(new Date(creationDate.getTime() + lifetimeInMinutes * 60 * 1000));
            setPassword(UUID.randomUUID().toString());
        }else
            setPassword(UUID.randomUUID().toString());
    }

    public OtpGenerator() {
    }

    private static Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }
}
