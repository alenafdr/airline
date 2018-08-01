package com.airline.model.dto;

import com.airline.dto.validation.New;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class FlightDTO {

    @Null
    private Long id;

    @NotNull
    private String flightName;

    @NotNull
    private String planeName;

    @NotNull
    private String fromTown;

    @NotNull
    private String toTown;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(dataType = "java.lang.String", example = "hh:mm:ss")
    private Time start;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(dataType = "java.lang.String", example = "hh:mm:ss")
    private Time duration;

    @NotNull
    private BigDecimal priceBusiness;

    @NotNull
    private BigDecimal priceEconomy;

    private Schedule schedule;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<Date> dates;

    @AssertFalse(groups = New.class)
    private boolean approved;

    @AssertTrue
    private boolean isOk() {
        return schedule == null && !dates.isEmpty() ||
                schedule != null && dates.isEmpty();
    }

    public FlightDTO() {
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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public BigDecimal getPriceBusiness() {
        return priceBusiness;
    }

    public void setPriceBusiness(BigDecimal priceBusiness) {
        this.priceBusiness = priceBusiness;
    }

    public BigDecimal getPriceEconomy() {
        return priceEconomy;
    }

    public void setPriceEconomy(BigDecimal priceEconomy) {
        this.priceEconomy = priceEconomy;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
                "\nid=" + id +
                ", \nflightName='" + flightName + '\'' +
                ", \nplaneName='" + planeName + '\'' +
                ", \nfromTown='" + fromTown + '\'' +
                ", \ntoTown='" + toTown + '\'' +
                ", \nstart=" + start +
                ", \nduration=" + duration +
                ", \npriceBusiness=" + priceBusiness +
                ", \npriceEconomy=" + priceEconomy +
                ", \nschedule=" + schedule +
                ", \ndates=" + dates +
                ", \napproved=" + approved +
                '}';
    }
}
