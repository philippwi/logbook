package db.operation;

import config.Configuration;
import db.models.TripEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static utility.Tools.handleException;

public class TripManager {

    private EntityManagerFactory emFactory;
    private EntityManager em;

    private TripManager() {
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
     * Creates a new instance of TripManager.
     *
     * @return new TripManager instance
     */
    public static TripManager start() {
        return new TripManager();
    }

    /**
     * Closes the EntityManagerFactory. Should be executed
     * when the TripManager is not needed any longer.
     */
    public void stop() {
        emFactory.close();
    }

    /**
     * Reads all trip records from the database and returns
     * them as a list.
     *
     * @return all stored trips
     */
    public List<TripEntity> getAllTrips() {

        createEM();

        List<TripEntity> tripList = em.createQuery(
                "select new TripEntity(tripId, user, origin, destination, distance, date) " +
                        "from TripEntity", TripEntity.class)
                .getResultList();

        closeEM();

        return tripList;
    }

    /**
     * Reads all trip records created by a specific user from the
     * database and returns them as a list.
     *
     * @param username name of the user whose trips should be returned
     * @return all stored trips of a specific user
     */
    public List<TripEntity> getUserTrips(String username) {

        createEM();

        List<TripEntity> tripList = em.createQuery(
                "select new TripEntity(tripId, user, origin, destination, distance, date) " +
                        "from TripEntity where user = :param", TripEntity.class)
                .setParameter("param", username)
                .getResultList();

        closeEM();

        return tripList;
    }

    /**
     * Writes a given trip entity into the database.
     *
     * @param trip trip element to be stored
     * @return status of the operation
     */
    public boolean addTrip(TripEntity trip) {
        try {
            createEM();

            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();

            closeEM();
        } catch (Exception e) {
            handleException(e);
            return false;
        }
        return true;
    }

    /**
     * Deletes trip with the given ID from the database.
     *
     * @param id identifier of the trip
     * @return success of the operation
     */
    public boolean deleteTrip(int id) {
        try {
            createEM();

            TripEntity trip = em.find(TripEntity.class, id);

            if (trip == null) return false;

            em.getTransaction().begin();
            em.remove(trip);
            em.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
            return false;
        }
        return true;
    }
}
