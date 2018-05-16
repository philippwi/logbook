package beans;

import calculation.DistanceProvider;
import db.models.TripEntity;
import db.models.UserEntity;
import db.operation.TripManager;
import db.operation.UserManager;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static utility.CookieManagement.deleteUserCookie;
import static utility.Tools.*;

@Named
@SessionScoped
public class AdminBean extends BaseBean {

    private boolean admin;

    private ArrayList<UserEntity> userList;
    private ArrayList<TripEntity> tripList;

    private ArrayList<UserEntity> selectedUsers;

    private ArrayList<TripEntity> selectedTrips;

    public boolean isAdmin() {

        String username = getActiveUser();

        if(isBlankOrNull(username)){
            admin = false;
            return admin;
        }

        UserManager um = UserManager.start();

        UserEntity user = um.getUser(username);

        um.stop();

        admin = (user.getAdmin() == 1);

        return admin;
    }

    public ArrayList<UserEntity> getUserList() {

        UserManager um = UserManager.start();

        userList = new ArrayList<>(
                um.getAllUsers());

        um.stop();

        return userList;
    }

    public ArrayList<TripEntity> getTripList() {

        TripManager tm = TripManager.start();

        tripList = new ArrayList<>(
                tm.getAllTrips());

        tm.stop();

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


    public void deleteSelectedUsers(){

        UserManager um = UserManager.start();

        for(UserEntity user: selectedUsers){
            um.deleteUser(user.getName());
        }

        um.stop();
    }

    public void deleteSelectedTrips(){

        TripManager tm = TripManager.start();

        for(TripEntity trip: selectedTrips){
            tm.deleteTrip(trip.getTripId());
        }

        tm.stop();
    }

}
