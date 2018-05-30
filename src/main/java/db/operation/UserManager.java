package db.operation;

import config.Configuration;
import db.models.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static utility.Tools.handleException;


public class UserManager {

    private EntityManagerFactory emFactory;
    private EntityManager em;

    private UserManager() {
        emFactory = Persistence.createEntityManagerFactory(Configuration.PERSISTENCE_UNIT_NAME);
    }

    /**
     * Creates a new EntityManager instance referenced by the em variable.
     */
    private void createEM() {
        em = emFactory.createEntityManager();
    }

    /**
     * Closes the EntityManager after it is not needed any longer.
     */
    private void closeEM() {
        em.close();
    }

    /**
     * Creates a new instance of UserManager.
     *
     * @return new UserManager instance
     */
    public static UserManager start() {
        return new UserManager();
    }

    /**
     * Closes the EntityManagerFactory. Should be executed
     * when the UserManager is not needed any longer.
     */
    public void stop() {
        emFactory.close();
    }

    /**
     * Reads all user records from the database and returns
     * them as a list.
     *
     * @return all stored users
     */
    public List<UserEntity> getAllUsers() {

        createEM();

        List<UserEntity> userList = em.createQuery(
                "select new UserEntity(name, password, admin) from UserEntity",
                UserEntity.class)
                .getResultList();

        closeEM();

        return userList;
    }

    /**
     * Reads data of a specific user from the database.
     *
     * @param username name of the user whose data should be returned
     * @return result of the search
     */
    public UserEntity getUser(String username) {

        UserEntity user;

        try {
            createEM();

            user = em.find(UserEntity.class, username);

            closeEM();

        } catch (Exception e) {
            handleException(e);
            user = null;
        }
        return user;
    }

    /**
     * Writes a given user entity into the database.
     *
     * @param user user element to be stored
     * @return status of the operation
     */
    public boolean addUser(UserEntity user) {

        if (userExists(user.getName())) {
            return false;
        }

        try {
            createEM();

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            closeEM();
        } catch (Exception e) {
            handleException(e);
            return false;
        }
        return true;
    }

    /**
     * Deletes user with the given name from the database.
     *
     * @param username name of the user
     * @return success of the operation
     */
    public boolean deleteUser(String username) {

        if (!userExists(username)) {
            return false;
        }

        createEM();

        UserEntity user = em.find(UserEntity.class, username);

        if (user == null) return false;

        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        closeEM();

        return true;
    }

    /**
     * Checks if a user with the given name already exists
     * in the database.
     *
     * @param username name of the user
     * @return whether or not user exists
     */
    public boolean userExists(String username) {

        long usrCount;

        createEM();

        usrCount = em.createQuery(
                "select count(*) from UserEntity where name = :param",
                Long.class)
                .setParameter("param", username)
                .getSingleResult();

        closeEM();

        return usrCount != 0;
    }

}
