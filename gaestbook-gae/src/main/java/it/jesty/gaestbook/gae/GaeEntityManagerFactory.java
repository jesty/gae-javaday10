package it.jesty.gaestbook.gae;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GaeEntityManagerFactory implements EntityManagerFactory{

	private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

    public GaeEntityManagerFactory() {}

    public EntityManager entityManager() {
        return emfInstance.createEntityManager();
    }

	@Override
	public void close() {
		emfInstance.close();
	}

	@Override
	public EntityManager createEntityManager() {
		return emfInstance.createEntityManager();
	}

	@Override
	public EntityManager createEntityManager(Map map) {
		return emfInstance.createEntityManager(map);
	}

	@Override
	public boolean isOpen() {
		return emfInstance.isOpen();
	}
}




