package db.operation;

import config.Configuration;
import db.models.TripEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TripManager {

    private EntityManagerFactory emFactory;
    private EntityManager em;

    private TripManager() {
        emFactory = Persistence.createEntityManagerFactory(Configuration.PERSISTENCE_UNIT_NAME);
    }

    private void createEM() {
        em = emFactory.createEntityManager();
    }

    private void closeEM() {
        em.close();
    }

    public static TripManager start() {
        return new TripManager();
    }

    public void stop() {
        emFactory.close();
    }

    public List<TripEntity> getAllTrips() {

        createEM();

        List<TripEntity> tripList = em.createQuery(
                "select new TripEntity(tripId, user, origin, destination, distance, date) " +
                        "from TripEntity", TripEntity.class)
                .getResultList();

        closeEM();

        return tripList;
    }

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

    public boolean addTrip(TripEntity trip) {

        createEM();

        em.getTransaction().begin();
        em.persist(trip);
        em.getTransaction().commit();

        closeEM();

        return true;
    }

    public boolean deleteTrip(int id) {

        createEM();

        TripEntity trip = em.find(TripEntity.class, id);

        if (trip == null) return false;

        em.getTransaction().begin();
        em.remove(trip);
        em.getTransaction().commit();

        return true;
    }
}
