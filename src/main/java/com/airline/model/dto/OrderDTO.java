package com.airline.model.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long orderId;
    private Long flightId;
    private String fromTown;
    private String toTown;
    private String flightName;
    private String planeName;
    private Date date;
    private Time start;
    private Time duration;
    private List<TicketDTO> passengers;
    private BigDecimal totalPrice;

    public OrderDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<TicketDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<TicketDTO> passengers) {
        this.passengers = passengers;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "\norderId=" + orderId +
                ", \nflightId=" + flightId +
                ", \nfromTown='" + fromTown + '\'' +
                ", \ntoTown='" + toTown + '\'' +
                ", \nflightName='" + flightName + '\'' +
                ", \ndate=" + date +
                ", \nstart=" + start +
                ", \nduration=" + duration +
                ", \npassengers=" + passengers +
                ", \ntotalPrice=" + totalPrice +
                '}';
    }
}
