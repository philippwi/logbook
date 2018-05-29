package beans;

import db.models.TripEntity;
import db.models.UserEntity;
import db.operation.TripManager;
import db.operation.UserManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AdminBeanTest {

    private static TripManager tm;
    private static UserManager um;

    //test-data
    private static UserEntity admin;
    private static UserEntity user;
    private static TripEntity trip1;
    private static TripEntity trip2;

    @BeforeClass
    public static void initBeforeClass() {
        tm = TripManager.start();
        um = UserManager.start();

        admin = new UserEntity("Test-Admin", "12345", (byte) 1);
        user = new UserEntity("Test-User", "12345", (byte) 0);
        trip1 = new TripEntity(1234567890, admin.getName(), "Ort1", "Ort2", 100f, LocalDate.now());
        trip2 = new TripEntity(1234567891, user.getName(), "Ort3", "Ort4", 1000f, LocalDate.now());
    }

    @After
    public void clearTestDataFromDB() {
        tm.deleteTrip(trip1.getTripId());
        tm.deleteTrip(trip2.getTripId());
        um.deleteUser(admin.getName());
        um.deleteUser(user.getName());
    }

    @AfterClass
    public static void closeAfterClass() {
        tm.stop();
        um.stop();
    }


    @Test
    public void updateUserList() {
        AdminBean ab = new AdminBean();

        List<UserEntity> userList = ab.getUserList();

        assertNotNull(userList);

        um.addUser(admin);
        um.addUser(user);

        ab.updateUserList();

        List<UserEntity> updatedUserList = ab.getUserList();

        assertEquals(userList.size() + 2, updatedUserList.size());
    }

    @Test
    public void updateTripList() {
        AdminBean ab = new AdminBean();

        List<TripEntity> tripList = ab.getTripList();

        assertNotNull(tripList);

        tm.addTrip(trip1);
        tm.addTrip(trip2);

        ab.updateTripList();

        List<TripEntity> updatedTipList = ab.getTripList();

        assertEquals(tripList.size() + 2, updatedTipList.size());
    }

    @Test
    public void deleteSelectedUsers() {
        AdminBean ab = new AdminBean();

        um.addUser(admin);
        um.addUser(user);

        List<UserEntity> userList = ab.getUserList();

        ab.setSelectedUsers(new ArrayList<UserEntity>() {{
            add(admin);
            add(user);
        }});

        ab.deleteSelectedUsers();

        List<UserEntity> updatedUserList = ab.getUserList();

        assertEquals(userList.size() - 2, updatedUserList.size());
    }

    @Test
    public void deleteSelectedTrips() {
        AdminBean ab = new AdminBean();

        tm.addTrip(trip1);
        tm.addTrip(trip2);

        List<TripEntity> tripList = ab.getTripList();

        ab.setSelectedTrips(new ArrayList<TripEntity>() {{
            add(trip1);
            add(trip2);
        }});

        ab.deleteSelectedTrips();

        List<TripEntity> updatedTripList = ab.getTripList();

        assertEquals(tripList.size() - 2, updatedTripList.size());
    }
}