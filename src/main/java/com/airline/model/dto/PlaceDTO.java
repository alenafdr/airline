package com.airline.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PlaceDTO {
    @NotNull
    private Long orderId;

    @NotNull
    private Long ticket;

    private String lastName;

    private String firstName;

    @NotNull
    @Pattern(regexp = ".*\\S.*", message = "Must be not empty")
    private String place;

    public PlaceDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
