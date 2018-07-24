package com.airline.model;

import com.airline.model.db.DepartureDB;
import com.airline.model.db.PriceDB;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Flight {
    private Long id;
    private String flightName;
    private String fromTown;
    private String toTown;
    private Time startTime;
    private Time duration;
    private Date fromDate;
    private Date toDate;
    private boolean approved;
    private Plane plane;
    private List<Period> periods;
    private List<Departure> departures;
    private List<Price> prices;

    public Flight() {
    }

    public List<PriceDB> getPricesDB(){
        List<PriceDB> priceDBS = new ArrayList<>();
        prices.forEach(price -> priceDBS.add((new PriceDB(price))));
        return priceDBS;
    }

    public List<DepartureDB> getDeparturesDB(){
        List<DepartureDB> departureDBS = new ArrayList<>();
        departures.forEach(departure -> departureDBS.add(new DepartureDB(departure)));
        return departureDBS;
    }

    public Price getPriceByClassTypeName (String name){
        return prices.stream().filter(price -> price.getClassType().getName().equals(name)).findFirst().get();
    }

    public Flight(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public String getToTown() {
        return toTown;
    }

    public void setToTown(String toTown) {
        this.toTown = toTown;
    }

    public Time getStart() {
        return startTime;
    }

    public void setStart(Time start) {
        this.startTime = start;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "\nFlight{" +
                "\nid=" + id +
                ", \nname='" + flightName + '\'' +
                ", \nplane=" + plane +
                ", \nfromTown='" + fromTown + '\'' +
                ", \ntoTown='" + toTown + '\'' +
                ", \nstart=" + startTime +
                ", \nduration=" + duration +
                ", \nfromDate=" + fromDate +
                ", \ntoDate=" + toDate +
                ", \napproved=" + approved +
                ", \nperiods=" + periods +
                ", \ndepartures=" + departures +
                ", \nprices=" + prices +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return approved == flight.approved &&
                Objects.equals(id, flight.id) &&
                Objects.equals(flightName, flight.flightName) &&
                Objects.equals(fromTown, flight.fromTown) &&
                Objects.equals(toTown, flight.toTown) &&
                Objects.equals(startTime, flight.startTime) &&
                Objects.equals(duration, flight.duration) &&
                Objects.equals(fromDate, flight.fromDate) &&
                Objects.equals(toDate, flight.toDate) &&
                Objects.equals(plane, flight.plane) &&
                Objects.equals(periods, flight.periods) &&
                Objects.equals(departures, flight.departures) &&
                Objects.equals(prices, flight.prices);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id,
                flightName,
                fromTown,
                toTown,
                startTime,
                duration,
                fromDate,
                toDate,
                approved,
                plane,
                periods,
                departures,
                prices);
    }
}
