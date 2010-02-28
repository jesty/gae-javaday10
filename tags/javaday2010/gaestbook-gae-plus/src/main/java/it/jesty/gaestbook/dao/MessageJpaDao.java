package it.jesty.gaestbook.dao;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class MessageJpaDao extends JpaDaoSupport implements MessageDao {
	
	Logger log = Logger.getLogger(MessageJpaDao.class.getName());

	@Transactional
	public void createMessage(Message message) {
		getJpaTemplate().persist(message);
	}

	public Collection<Message> list() {
		return getJpaTemplate().find("select m from Message m where m.confirmed = true");
	}
	
	@Transactional
	public Message findByUUID(String uuid) {
		Message message = null;
		log.info("Try to confirm message with uuid: " + uuid);
		List<Message> find = getJpaTemplate().find("select m from Message m where m.uuid = ?1", uuid);
		if(!find.isEmpty()){
			message = find.get(0);
		}
		return message;
	}

	@Transactional
	public Message confirmMessage(String uuid) {
		Message message = findByUUID(uuid);
		if(message != null){
			message.setConfirmed(true);
			getJpaTemplate().merge(message);
			log.info("Message with uuid " + uuid + " confirmed.");
		}
		return message;
	}

}
