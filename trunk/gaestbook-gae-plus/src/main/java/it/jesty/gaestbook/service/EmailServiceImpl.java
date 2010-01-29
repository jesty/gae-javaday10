package it.jesty.gaestbook.service;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
	
	private static final Logger log = Logger.getLogger(EmailServiceImpl.class.getName());

	@Override
	public void sendMail(String from, String to, String subject, String text) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
        	
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);

        } catch (Exception e) {
            log.severe("Error sending mail to " + to + ". Error: " + e.getMessage());
        }
		
	}

}



