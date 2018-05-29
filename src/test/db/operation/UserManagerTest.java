package db.operation;

import config.Configuration;
import db.models.UserEntity;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class UserManagerTest {

    private static EntityManagerFactory emFactory;
    private EntityManager em;
    private static UserManager um;

    @BeforeClass
    public static void initBeforeClass() {
        emFactory = Persistence.createEntityManagerFactory(Configuration.PERSISTENCE_UNIT_NAME);
        um = UserManager.start();
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
        um.stop();
    }

    @Test
    public void getAllUsers() {
        List<UserEntity> expectedList = em.createQuery(
                "select new UserEntity(name, password, admin) from UserEntity",
                UserEntity.class)
                .getResultList();

        assertEquals(um.getAllUsers(), expectedList);
    }

    @Test
    public void getUser() {

        //can be replaced with any active user in your database
        String username = "TestUser";

        UserEntity user = null;
        try {
            user = em.createQuery(
                    "select new UserEntity(name, password, admin) from UserEntity where name = :param",
                    UserEntity.class)
                    .setParameter("param", username)
                    .getSingleResult();
        } catch (Exception ignored) {
        }

        assertEquals(um.getUser(username), user);
    }

    @Test
    public void addUser() {
        boolean success = false;

        UserEntity user = new UserEntity("Test-User", "12345", (byte) 0);

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        if (um.userExists(user.getName())) {
            um.deleteUser(user.getName());
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void deleteUser() {

        UserEntity user = new UserEntity("Test-User", "12345", (byte) 0);

        um.addUser(user);

        um.deleteUser(user.getName());

        assertFalse(um.userExists(user.getName()));
    }

    @Test
    public void userExists() {
        boolean success = false;
        UserEntity user = new UserEntity("Test-User", "12345", (byte) 0);

        um.addUser(user);
        if (um.userExists(user.getName())) {
            um.deleteUser(user.getName());
            success = true;
        }
        assertTrue(success);
    }
}