package db.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "trip", schema = "logbook", catalog = "")
public class TripEntity implements Serializable {
    private int tripId;
    private String user;
    private String origin;
    private String destination;
    private float distance;
    private LocalDate date;


    public TripEntity(){}

    public TripEntity(String usr, String org, String dest, float dist, LocalDate dt){
        setUser(usr);
        setOrigin(org);
        setDestination(dest);
        setDistance(dist);
        setDate(dt);
    }

    public TripEntity(int id, String usr, String org, String dest, float dist, LocalDate dt){
        setTripId(id);
        setUser(usr);
        setOrigin(org);
        setDestination(dest);
        setDistance(dist);
        setDate(dt);
    }
    @Id
    @Column(name = "TripID", nullable = false)
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    @Basic
    @Column(name = "User", nullable = false, length = 45)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "Origin", nullable = false, length = 45)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "Destination", nullable = false, length = 45)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "Distance", nullable = false)
    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "Date", nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripEntity that = (TripEntity) o;
        return tripId == that.tripId &&
                distance == that.distance &&
                Objects.equals(user, that.user) &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tripId, user, origin, destination, distance, date);
    }

    @Override
    public String toString(){
        return "(" + tripId + ", " + user + ", " + origin + ", " +
                destination+ ", " + distance + ", " + date + ")";
    }
}
