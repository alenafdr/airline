package com.airline.model.db;

import com.airline.model.Price;

import java.util.Objects;

public class PriceDB extends Price {

    public PriceDB(Price p) {
        super(p.getFlight(), p.getClassType(), p.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(flight, price1.getFlight()) &&
                Objects.equals(classType, price1.getClassType());
    }
}
