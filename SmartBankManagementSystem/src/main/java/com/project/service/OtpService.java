package com.project.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender javaMailSender;

    public int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public void sendOtp(String toEmail,int otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Smart Bank OTP Verification");
        message.setText("Your OTP is : " + otp);
        javaMailSender.send(message);
    }

}