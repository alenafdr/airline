package com.airline.model;

public class PeriodFlight {
    private Long periodId;
    private Long flightId;

    public PeriodFlight(Long periodId, Long flightId) {
        this.periodId = periodId;
        this.flightId = flightId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
