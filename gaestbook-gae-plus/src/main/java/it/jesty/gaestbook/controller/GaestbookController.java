package it.jesty.gaestbook.controller;

import it.jesty.gaestbook.bean.Message;
import it.jesty.gaestbook.facade.MessageFacade;
import it.jesty.gaestbook.security.GaestbookUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;

@Controller
public class GaestbookController {
	
	private final MessageFacade messageFacade;
	private final GaestbookUserService userService;

	@Autowired
	public GaestbookController(MessageFacade messageFacade, GaestbookUserService userService) {
		this.messageFacade = messageFacade;
		this.userService = userService;
	}
	
	@ModelAttribute("userService")
	public GaestbookUserService getUserService(){
		return this.userService;
	}
	
	@RequestMapping(value="/messages", method= RequestMethod.GET)
	public ModelAndView showMessages(){
		ModelAndView modelAndView = new ModelAndView("messages-list");
		modelAndView.addObject("messages", this.messageFacade.listMessages());
		return modelAndView;
	}
	
	@RequestMapping(value="/messages/of/{userid}", method= RequestMethod.GET)
	public ModelAndView showMessagesOf(@PathVariable String userid){
		ModelAndView modelAndView = new ModelAndView("messages-list");
		modelAndView.addObject("messages", this.messageFacade.listMessages(userid));
		return modelAndView;
	}
	
	@RequestMapping(value="/messagePosted", method= RequestMethod.POST)
	public ModelAndView addMessages(@ModelAttribute Message message){
		ModelAndView modelAndView = new ModelAndView("message-page");
		Map<String, Object> result; 
		if(this.userService.isLogged()){
			result = messageFromLoggedUser(message);
		} else {
			result = messageFromAnonymusUser(message);
		}
		modelAndView.addAllObjects(result);
		return modelAndView;
	}
	
	private Map<String, Object> messageFromAnonymusUser(Message message) {
		Map<String, Object> result = new HashMap<String, Object>();
		String error = validateAnonymus(message);
		if(!error.isEmpty()){
			result.put("error", error);
		}else {
			message.setConfirmed(false);
			message.setAnonymous(true);
			this.messageFacade.sendMessage(message);
			this.messageFacade.queueMail(message);
			result.put("status", "Message sent successfully. Now you receive an email to confirm this operation.");
			result.put("message", message);
		}
		return result;
	}

	private Map<String, Object> messageFromLoggedUser(Message message) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(message.getText().isEmpty()){
			result.put("error", "You must write a message!");
		} else {
			User user = this.userService.getUser();
			message.setName(user.getNickname());
			message.setEmail(user.getEmail());
			message.setConfirmed(true);
			message.setAnonymous(false);
			message.setUser(user);
			result.put("status", "Message sent successfully.");
			result.put("message", message);
			this.messageFacade.sendMessage(message);
		}
		return result;
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
	
	private String validateAnonymus(Message message){
		String error = "";
		if(message.getText().isEmpty() || message.getEmail().isEmpty() || message.getName().isEmpty()){
			error = "You must fill all fields to send a message!";
		} else if(!validateMail(message.getEmail())){
			error = "E-mail field is invalid!";
		}
		return error;
	}
	
	private boolean validateMail(String email){
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		return p.matcher(email).matches();
	}
	
}
