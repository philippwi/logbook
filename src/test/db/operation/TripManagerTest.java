package db.operation;

import config.Configuration;
import db.models.TripEntity;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class TripManagerTest {

    private static EntityManagerFactory emFactory;
    private EntityManager em;
    private static TripManager tm;

    @BeforeClass
    public static void initBeforeClass() {
        emFactory = Persistence.createEntityManagerFactory(Configuration.PERSISTENCE_UNIT_NAME);
        tm = TripManager.start();
    }

    @Before
    public void initBeforeTest() {
        em = emFactory.createEntityManager();
    }

    @After
    public void closeAfterTest() {
        em.close();
    }

    @AfterClass
    public static void closeAfterClass() {
        emFactory.close();
        tm.stop();
    }

    @Test
    public void start() {
        UserManager um = UserManager.start();
        assertNotNull(um);
    }

    @Test
    public void stop() {

        UserManager um = UserManager.start();
        um.stop();

        try {
            um.userExists("Whatever");
        } catch (IllegalStateException e) {
            return;
        }

        fail();
    }

    @Test
    public void getAllTrips() {
        List<TripEntity> expectedList = em.createQuery(
                "select new TripEntity(tripId, user, origin, destination, distance, date) " +
                        "from TripEntity", TripEntity.class)
                .getResultList();

        assertEquals(tm.getAllTrips(), expectedList);
    }

    @Test
    public void getUserTrips() {

        //can be replaced with any active user in your database
        String username = "TestUser";

        List<TripEntity> expectedList = em.createQuery(
                "select new TripEntity(tripId, user, origin, destination, distance, date) " +
                        "from TripEntity where user = :param", TripEntity.class)
                .setParameter("param", username)
                .getResultList();

        assertEquals(tm.getUserTrips(username), expectedList);
    }

    @Test
    public void addTrip() {
        boolean success = false;
        TripEntity testTrip = new TripEntity(1234567890, "TestUser", "Ort1", "Ort2", 100f, LocalDate.now());

        tm.addTrip(testTrip);

        List<TripEntity> tripList = tm.getAllTrips();

        if (tripList.contains(testTrip)) {
            tm.deleteTrip(1234567890);
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void deleteTrip() {
        TripEntity testTrip = new TripEntity(1234567890, "TestUser", "Ort1", "Ort2", 100f, LocalDate.now());

        tm.addTrip(testTrip);

        tm.deleteTrip(1234567890);

        List<TripEntity> tripList = tm.getAllTrips();

        assertFalse(tripList.contains(testTrip));
    }
}