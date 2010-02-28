package it.jesty.gaestbook.controller.cron;

import it.jesty.gaestbook.facade.MessageFacade;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteUnconfirmedController {
	
	private final MessageFacade messageFacade;

	@Autowired
	public DeleteUnconfirmedController(MessageFacade messageFacade) {
		this.messageFacade = messageFacade;
	}
	
	@RequestMapping(value="/cron/deleteunconfirmed")
	public void deleteunconfirmed(){
		this.messageFacade.deleteUnconfirmed(DateUtils.addDays(new Date(), -3));
	}

}
