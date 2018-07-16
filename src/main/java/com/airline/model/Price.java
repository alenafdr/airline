package com.airline.model;

import java.math.BigDecimal;

public class Price {
    private Flight flight;
    private ClassType classType;
    private BigDecimal price;

    public Price(BigDecimal price) {
        this.price = price;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nPrice{" +
                "ClassType=" + classType +
                ", price=" + price +
                '}';
    }
}
