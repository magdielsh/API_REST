package com.control.visitas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(String toEmail, String token) {

        String verificationLink = baseUrl + "/api/auth/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Verifica tu cuenta");
        message.setText(
                "Hola,\n\n" +
                        "Gracias por registrarte. Haz clic en el siguiente enlace para activar tu cuenta:\n\n" +
                        verificationLink + "\n\n" +
                        "Este enlace expira en 24 horas.\n\n" +
                        "Si no creaste esta cuenta, ignora este mensaje."
        );

        mailSender.send(message);
        // ↑ Envía el email — si falla lanza MailException
        //   que GlobalExceptionHandler captura como Exception genérica
    }
}
