/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.utilities;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

/**
 *
 * @author Mrqts
 */
public class IBTEmail {
    public static void sendEmail(String toEmail) {
        Email email = EmailBuilder.startingBlank()
                .from("Carol's Boutique", "azyeki01@outlook.com")
                .to(toEmail)
                .withSubject("IBT")
                .withPlainText("Dear User,\n\nYou can collect your IBT.\n\nRegards,\nCarol's Boutique")
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.office365.com", 587, "azyeki01@outlook.com", "Lukhanyo1")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        System.out.println("Email sent successfully to " + toEmail);
    }
}
