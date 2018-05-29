package beans;

import calculation.DistanceProvider;
import db.models.TripEntity;
import db.operation.TripManager;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static utility.Tools.*;

@Named
@SessionScoped
public class HomeBean extends BaseBean {

    private String origin;
    private String destination;
    private float distance;
    private Date date;

    private boolean validTrip;

    private ArrayList<TripEntity> tripList;

    private ArrayList<TripEntity> selectedTrips;


    //setter & getters
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isValidTrip() {
        return validTrip;
    }

    public ArrayList<TripEntity> getTripList() {
        if(tripList == null ||
                tripList.isEmpty() ||
                !tripList.get(0).getUser().equals(getActiveUser())) {
            updateTripList();
        }
        return tripList;
    }

    public void setTripList(ArrayList<TripEntity> tripList) {
        this.tripList = tripList;
    }

    public ArrayList<TripEntity> getSelectedTrips() {
        return selectedTrips;
    }

    public void setSelectedTrips(ArrayList<TripEntity> selectedTrips) {
        this.selectedTrips = selectedTrips;
    }


    //methods
    private void resetValues(){
        origin =null;
        destination=null;
        distance=0;
        date=null;
        validTrip = false;
    }

    public void updateTripList() {
        TripManager tm = TripManager.start();

        tripList = new ArrayList<>(
                tm.getUserTrips(getActiveUser()));

        tm.stop();
    }

    public void calculateDistance() {

        if(isBlankOrNull(origin) || isBlankOrNull(destination)){
            provideMessage("Info", "Bitte Start- und Zielort eingeben!");
            return;
        }

        try {
            DistanceProvider dp = new DistanceProvider();

            int distanceInMeters = (int) dp.getDistance(origin, destination);

            distance = precisionRound(
                    distanceInMeters / (float) 1000.0,
                    1);

            validTrip = true;
        }catch (Exception ignored){
            provideMessage("Info", "Fehler bei der Berechnung. Bitte eingegebene Orte auf Korrektheit prüfen!");
        }
    }

    public void saveIntoDB() {

        if(distance == 0 || isBlankOrNull(origin) || isBlankOrNull(destination)){
            provideMessage("Info", "Bitte erst alle Felder ausfüllen und Distanz berechnen lassen");
            return;
        }

        if(origin.length() > 45 || destination.length() > 45){
            provideMessage("Info", "Ortseingaben dürfen maximal 45 Zeichen lang sein!");
            return;
        }
        if(!isValidDate(date)){
            provideMessage("Info", "Datum ungültig");
            return;
        }

        LocalDate dt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        TripEntity trip = new TripEntity(getActiveUser(), origin, destination, distance, dt);

        TripManager tm = TripManager.start();
        tm.addTrip(trip);
        tm.stop();

        resetValues();

        updateTripList();
    }

    public void deleteSelectedTrips(){

        if (selectedTrips == null || selectedTrips.isEmpty()) {
            provideMessage("Info", "Bitte zu löschende Fahrten auswählen");
            return;
        }

        TripManager tm = TripManager.start();

        for(TripEntity trip: selectedTrips){
            tm.deleteTrip(trip.getTripId());
        }

        tm.stop();

        updateTripList();

        selectedTrips = null;
    }

}
