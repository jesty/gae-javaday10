package it.jesty.gaestbook.service;

public interface EmailService {
	
	void sendMail(String from, String to, String Subject, String text);

}
