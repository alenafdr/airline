package com.airline.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

public class TicketDTO {
    @Null
    private Long ticket;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
    private String nationality;

    @NotNull
    private String passport;

    @JsonProperty("class")
    private String classType;

    @Null
    private BigDecimal price;

    public TicketDTO() {
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @JsonGetter("class")
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classT) {
        this.classType = classT;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "ticket=" + ticket +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", passport='" + passport + '\'' +
                ", classType='" + classType + '\'' +
                ", price=" + price +
                '}';
    }
}
