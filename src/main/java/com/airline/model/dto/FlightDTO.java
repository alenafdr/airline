package com.airline.model.dto;

import com.airline.dto.validation.CrossFieldValidation;
import com.airline.dto.validation.Exist;
import com.airline.dto.validation.New;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossFieldValidation(groups = New.class)
public class FlightDTO {

    Logger logger = LoggerFactory.getLogger(FlightDTO.class);

    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    private Long id;

    @NotNull(groups = {New.class, Exist.class})
    private String flightName;

    @NotNull(groups = {New.class, Exist.class})
    private String planeName;

    @NotNull(groups = {New.class, Exist.class})
    private String fromTown;

    @NotNull(groups = {New.class, Exist.class})
    private String toTown;

    @NotNull(groups = {New.class, Exist.class})
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(dataType = "java.lang.String", example = "hh:mm:ss")
    private Time start;

    @NotNull(groups = {New.class, Exist.class})
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(dataType = "java.lang.String", example = "hh:mm:ss")
    private Time duration;

    @NotNull(groups = {New.class, Exist.class})
    private BigDecimal priceBusiness;

    @NotNull(groups = {New.class, Exist.class})
    private BigDecimal priceEconomy;

    private Schedule schedule;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<Date> dates;

    @AssertFalse(groups = New.class)
    private boolean approved;

    public FlightDTO() {
        dates = new ArrayList<>();
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
