package com.airline.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    protected Flight flight;
    protected ClassType classType;
    protected BigDecimal price;

    public Price() {
    }

    public Price(ClassType classType, BigDecimal price) {
        this.classType = classType;
        this.price = price;
    }

    public Price(Flight flight, ClassType classType, BigDecimal price) {
        this.flight = flight;
        this.classType = classType;
        this.price = price;
    }

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
                ", flight=" + flight.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(flight.getId(), price1.flight.getId()) &&
                Objects.equals(classType, price1.classType) &&
                Objects.equals(price, price1.price);
    }


    @Override
    public int hashCode() {

        return Objects.hash(flight, classType, price);
    }
}
