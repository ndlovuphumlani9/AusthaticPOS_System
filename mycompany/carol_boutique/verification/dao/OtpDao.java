package com.mycompany.carol_boutique.verification.dao;

import com.mycompany.carol_boutique.verification.model.Otp;

public interface OtpDao {
    boolean saveOtp(Otp otp);
    Otp getOtpByEmail(String email);
    boolean deleteOtpByEmail(String email);
}
