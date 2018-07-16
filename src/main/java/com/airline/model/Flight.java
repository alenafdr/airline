package com.airline.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Flight {
    private Long id;
    private String flightName;
    private String fromTown;
    private String toTown;
    private Time start;
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
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
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
        return "Flight{" +
                "id=" + id +
                ", name='" + flightName + '\'' +
                ", plane=" + plane +
                ", fromTown='" + fromTown + '\'' +
                ", toTown='" + toTown + '\'' +
                ", start=" + start +
                ", duration=" + duration +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", approved=" + approved +
                ", periods=" + periods +
                ", departures=" + departures +
                ", prices=" + prices +
                '}';
    }
}
