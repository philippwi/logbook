package db.operation;

import config.Configuration;
import db.models.UserEntity;

import javax.persistence.*;
import java.util.List;


public class UserManager {

    private EntityManagerFactory emFactory;
    private EntityManager em;

    private UserManager(){
        emFactory = Persistence.createEntityManagerFactory(Configuration.PERSISTENCE_UNIT_NAME);
    }

    private void createEM(){
        em = emFactory.createEntityManager();
    }

    private void closeEM(){
       em.close();
    }

    public static UserManager start(){
        return new UserManager();
    }

    public void stop(){
        emFactory.close();
    }

    public List<UserEntity> getAllUsers(){

        createEM();

        List<UserEntity> userList = em.createQuery(
                "select new UserEntity(name, password, admin) from UserEntity",
                UserEntity.class)
                .getResultList();

        closeEM();

        return userList;
    }

    public UserEntity getUser(String username){

        UserEntity user;

        createEM();

        user = em.find(UserEntity.class, username);

        closeEM();

        return user;
    }

    public boolean addUser(UserEntity user) {

        if (userExists(user.getName())) {
            return false;
        }

        createEM();

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        closeEM();

        return true;
    }

    public boolean deleteUser(String username) {

        if (!userExists(username)) {
            return false;
        }

        createEM();

        UserEntity user = em.find(UserEntity.class, username);

        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        closeEM();

        return true;
    }

    public boolean userExists(String username){

        long usrCount;

        createEM();

        usrCount = em.createQuery(
                "select count(*) from UserEntity where name = :param",
                Long.class)
                .setParameter("param", username)
                .getSingleResult();

        closeEM();

        if(usrCount == 0) {return false;}
        else {return true;}
    }

}
