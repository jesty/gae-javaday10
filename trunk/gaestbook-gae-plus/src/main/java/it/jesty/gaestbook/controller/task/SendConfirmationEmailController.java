package it.jesty.gaestbook.controller.task;

import it.jesty.gaestbook.service.EmailService;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SendConfirmationEmailController {
	
	private final EmailService emailService;
	
	private Logger log = Logger.getLogger(SendConfirmationEmailController.class.getName());
	
	@Autowired
	public SendConfirmationEmailController(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@RequestMapping(value="/tasks/sendconfirmation")
	public void sendConfirmation(@RequestParam String email, @RequestParam String text, @RequestParam String uuid, @RequestParam String name){
		log.info("start to send email to " + email);
		this.emailService.sendMail("davidecerbo@gmail.com", email, "G(a)estbook", buildText(name, text, uuid));
	}
	
	private String buildText(String name, String text, String uuid) {
		return new StringBuilder("Hi, ")
		.append(name)
		.append(" you have posted this message: \n")
		.append("\"")
		.append(text)
		.append("\"\n")
		.append("Please confirm it visiting this url: ")
		.append("http://g-a-estbook.appspot.com/app/confirm/")
		.append(uuid)
		.append("\nBest regardes,\n G(a)estbook")
		.toString();
	}

}
