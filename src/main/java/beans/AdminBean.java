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

    private ArrayList<UserEntity> userList;
    private ArrayList<TripEntity> tripList;

    private ArrayList<UserEntity> selectedUsers;

    private ArrayList<TripEntity> selectedTrips;

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

    public void updateUserList() {
        UserManager um = UserManager.start();

        userList = new ArrayList<>(
                um.getAllUsers());

        um.stop();
    }

    public void updateTripList() {
        TripManager tm = TripManager.start();

        tripList = new ArrayList<>(
                tm.getAllTrips());

        tm.stop();
    }

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
