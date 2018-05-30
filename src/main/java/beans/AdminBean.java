package beans;

import db.models.TripEntity;
import db.models.UserEntity;
import db.operation.TripManager;
import db.operation.UserManager;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;

@Named
@SessionScoped
public class AdminBean extends BaseBean {

    //----- variables ------

    private ArrayList<UserEntity> userList;
    private ArrayList<TripEntity> tripList;

    private ArrayList<UserEntity> selectedUsers;
    private ArrayList<TripEntity> selectedTrips;


    //----- getters & setters ------

    public ArrayList<UserEntity> getUserList() {
        if (userList == null || userList.isEmpty()) updateUserList();
        return userList;
    }

    public ArrayList<TripEntity> getTripList() {
        if (tripList == null || tripList.isEmpty()) updateTripList();
        return tripList;
    }

    public ArrayList<UserEntity> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(ArrayList<UserEntity> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public ArrayList<TripEntity> getSelectedTrips() {
        return selectedTrips;
    }

    public void setSelectedTrips(ArrayList<TripEntity> selectedTrips) {
        this.selectedTrips = selectedTrips;
    }


    //----- methods ------

    /**
     * Updates the userList variable with records from the database.
     */
    public void updateUserList() {
        UserManager um = UserManager.start();

        userList = new ArrayList<>(
                um.getAllUsers());

        um.stop();
    }

    /**
     * Updates the tripList variable with records from the database.
     */
    public void updateTripList() {
        TripManager tm = TripManager.start();

        tripList = new ArrayList<>(
                tm.getAllTrips());

        tm.stop();
    }

    /**
     * Deletes the user entities contained in the selectedUsers variable
     * from the database.
     */
    public void deleteSelectedUsers() {

        if (selectedUsers == null || selectedUsers.isEmpty()) {
            provideMessage("Info", "Bitte zu löschende Nutzer auswählen");
            return;
        }

        UserManager um = UserManager.start();

        for (UserEntity user : selectedUsers) {
            um.deleteUser(user.getName());
        }

        um.stop();

        updateUserList();

        selectedUsers = null;
    }

    /**
     * Deletes the trip entities contained in the selectedTrips variable
     * from the database.
     */
    public void deleteSelectedTrips() {

        if (selectedTrips == null || selectedTrips.isEmpty()) {
            provideMessage("Info", "Bitte zu löschende Fahrten auswählen");
            return;
        }

        TripManager tm = TripManager.start();

        for (TripEntity trip : selectedTrips) {
            tm.deleteTrip(trip.getTripId());
        }

        tm.stop();

        updateTripList();

        selectedTrips = null;
    }

}
