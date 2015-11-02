package org.itevents.otp;

import java.util.UUID;

public class GenerateOTP {
    public static final void main(String... Args) {
        //generate random OTP
        UUID otp = UUID.randomUUID();
        log("OTP: " + otp);
    }

    private static void log(Object Object) {
        System.out.println(String.valueOf(Object));
    }
}