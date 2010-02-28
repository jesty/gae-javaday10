package it.jesty.gaestbook.controller;

import it.jesty.gaestbook.bean.Message;
import it.jesty.gaestbook.facade.MessageFacade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GaestbookController {
	
	private final MessageFacade messageFacade;

	@Autowired
	public GaestbookController(MessageFacade messageFacade) {
		this.messageFacade = messageFacade;
	}

	@RequestMapping(value="/messages", method= RequestMethod.GET)
	public ModelAndView showMessages(){
		ModelAndView modelAndView = new ModelAndView("messages-list");
		modelAndView.addObject("messages", this.messageFacade.listMessages());
		return modelAndView;
	}
	
	@RequestMapping(value="/messagePosted", method= RequestMethod.POST)
	public ModelAndView addMessages(@RequestParam(required=true) String name, @RequestParam String email, @RequestParam(value="message") String text){
		ModelAndView modelAndView = new ModelAndView("message-page");
		if(text.isEmpty() || email.isEmpty() || name.isEmpty()){
			modelAndView.addObject("error", "You must fill all fields to send a message!");
		} else {
			Message message = new Message(text, email, name, new Date());
			modelAndView.addObject("status", "Message sent successfully. Now you receive an email to confirm this operation.");
			modelAndView.addObject("message", message);
			this.messageFacade.sendMessage(message);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/confirm/{uuid}", method= RequestMethod.GET)
	public ModelAndView confirmMessage(@PathVariable String uuid){
		ModelAndView modelAndView = new ModelAndView("message-page");
		Message message = this.messageFacade.conifrmMessage(uuid);
		if(message == null){
			modelAndView.addObject("error", "Message not found.");
		} else {
			modelAndView.addObject("status", "Message confirmed.");
			modelAndView.addObject("message", message);
		}
		return modelAndView;
	}
	
}
