package it.jesty.gaestbook.dao;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class MessageJpaDao extends JpaDaoSupport implements MessageDao {
	
	Logger log = Logger.getLogger(MessageJpaDao.class.getName());

	@Transactional
	public void createMessage(Message message) {
		getJpaTemplate().persist(message);
	}

	public Collection<Message> list() {
		return getJpaTemplate().find("select m from Message m where m.confirmed = true order by m.created desc");
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
	
	@Transactional
	public int deleteUnconfirmedMessage(final Date before) {
		return getJpaTemplate().execute(new JpaCallback<Integer>() {
			@Override
			public Integer doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery("delete from Message m where m.created <= ?1 and m.confirmed = false");
				query.setParameter(1, before);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public Collection<Message> list(String userid) {
		return getJpaTemplate().find("select m from Message m where m.confirmed = true and m.name = ?1 order by m.created desc", userid);
	}

}
