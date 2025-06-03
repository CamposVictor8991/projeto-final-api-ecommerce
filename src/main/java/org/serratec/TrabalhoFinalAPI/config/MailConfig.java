package org.serratec.TrabalhoFinalAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmail(String para, String assunto, String texto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("laryssa.peixot@gmail.com");
        message.setTo(para);
        message.setSubject(assunto);
        message.setText(texto);
        javaMailSender.send(message);
    }
}
