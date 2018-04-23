package beans;

import calculation.DistanceProvider;
import db.models.TripEntity;
import db.operation.TripManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import static utility.CookieManagement.getCurrentUser;
import static utility.Tools.precisionRound;

@Named
@RequestScoped
public class HomeBean {

    private String origin;
    private String destination;
    private int distanceInMeters;
    private double distanceInKilometers;
    private LocalDate date;

    private ArrayList<TripEntity> tripList;

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

    public int getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(int distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public double getDistanceInKilometers() {
        return distanceInKilometers;
    }

    public void setDistanceInKilometers(double distanceInKilometers) {
        this.distanceInKilometers = distanceInKilometers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<TripEntity> getTripList() {

        TripManager tm = TripManager.start();

        tripList = new ArrayList<>(
                tm.getUserTrips("Hans"));

        tm.stop();

        return tripList;
    }
    public void setTripList(ArrayList<TripEntity> tripList) {
        this.tripList = tripList;
    }

    //methods
    public void calculateDistance() {

        DistanceProvider dp = new DistanceProvider();

        distanceInMeters = (int) dp.getDistance(origin, destination);

        distanceInKilometers = precisionRound(
                distanceInMeters / 1000.0,
                2);
    }

    public void saveIntoDB() {

        TripEntity trip = new TripEntity(getCurrentUser(), origin, destination, distanceInMeters, date);

        TripManager tm = TripManager.start();
        tm.addTrip(trip);
        tm.stop();
    }

}
