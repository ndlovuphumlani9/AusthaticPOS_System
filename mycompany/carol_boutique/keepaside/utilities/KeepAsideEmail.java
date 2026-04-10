/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.utilities;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

/**
 *
 * @author Mrqts
 */
public class KeepAsideEmail {
    public static void sendEmail(String toEmail) {
        Email email = EmailBuilder.startingBlank()
                .from("Carol's Boutique", "motaungtau10@gmail.com")
                .to(toEmail)
                .withSubject("IBT")
                .withPlainText("Dear Customer,\n\nYou have 24 hours to collect your items.\n\nRegards,\nCarol's Boutique")
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "motaungtau10@gmail.com", "drpltzzujywqkjlt")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        System.out.println("Email sent successfully to " + toEmail);
    }
    
}
