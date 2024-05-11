package br.com.ecommerce.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        // Configurações do servidor SMTP do Gmail
        String host = "smtp.gmail.com";
        int port = 587;
        String username = "teste@gmail.com"; // Seu endereço de e-mail do Gmail
        String password = "teste"; // Sua senha do Gmail

        // Configurações adicionais
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Autenticação
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        // Cria a mensagem de e-mail
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        // Cria a parte do corpo do e-mail
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);

        // Adiciona a parte do corpo ao multipart
        multipart.addBodyPart(messageBodyPart);

        // Define o conteúdo do e-mail
        message.setContent(multipart);

        // Envia o e-mail
        Transport.send(message);
    }

}
