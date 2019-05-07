package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class UserDAOImpl implements UserDAO {

    private static UserDAOImpl instance = null;
    private EntityManager entityManager;

    public static UserDAOImpl getInstance(){
        if(instance == null){
            instance = new UserDAOImpl();
        }
        return instance;
    }

    private UserDAOImpl(){
        this.entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ShowArt-persistence-unit");
        if (entityManager == null)
            entityManager = factory.createEntityManager();
        return entityManager;
    }

    @Override
    public boolean addUser(User user) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public User retrieveUser(String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public boolean removeUser(User user) {
        try{
            user = entityManager.find(User.class, user.getUsername());
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try{
            user = entityManager.find(User.class, user.getUsername());
            entityManager.getTransaction().begin();
            entityManager.refresh(user);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }
}
