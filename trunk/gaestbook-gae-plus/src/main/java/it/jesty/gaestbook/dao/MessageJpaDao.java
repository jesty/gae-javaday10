package it.jesty.gaestbook.dao;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class MessageJpaDao extends JpaDaoSupport implements MessageDao {

	@Transactional
	public void createMessage(Message message) {
		getJpaTemplate().persist(message);
	}

	public Collection<Message> list() {
		return getJpaTemplate().find("select m from Message m");
	}

}
