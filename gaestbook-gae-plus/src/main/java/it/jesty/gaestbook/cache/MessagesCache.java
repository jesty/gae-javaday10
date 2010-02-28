package it.jesty.gaestbook.cache;

import it.jesty.gaestbook.bean.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessagesCache implements Serializable {
	
	private static final long serialVersionUID = 5552013414819286628L;
	
	private final List<Message> entries;

	private MessagesCache(List<Message> entries){
		this.entries = entries;
	}
	
	public Collection<Message> getAll(){
		return this.entries;
	}
	
	public void add(Message message){
		this.entries.add(0, message);
	}
	
	public static MessagesCache build(Collection<Message> entries){
		//to be sure that is serializable
		return new MessagesCache(new ArrayList<Message>(entries));
	}
	
}
