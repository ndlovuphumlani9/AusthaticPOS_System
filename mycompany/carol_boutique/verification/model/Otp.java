package com.mycompany.carol_boutique.verification.model;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Otp {
    private String email;
    private String otpCode;
    private Timestamp expiryTime;

    public Otp() {
    }

    public Otp(String email, String otpCode, Timestamp expiryTime) {
        this.email = email;
        this.otpCode = otpCode;
        this.expiryTime = expiryTime;
    }
}
