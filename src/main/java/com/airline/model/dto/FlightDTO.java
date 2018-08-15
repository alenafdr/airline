package com.airline.model.dto;

import com.airline.dto.validation.CrossFieldValidation;
import com.airline.rest.json.CustomListDateDeserealize;
import com.airline.rest.json.CustomListDateSerealize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossFieldValidation
public class FlightDTO {

    @Null
    private Long id;

    @NotNull
    @Pattern(regexp = ".*\\S.*", message = "Must be not empty")
    private String flightName;

    @NotNull
    @Pattern(regexp = ".*\\S.*", message = "Must be not empty")
    private String planeName;

    @NotNull
    @Pattern(regexp = ".*\\S.*", message = "Must be not empty")
    private String fromTown;

    @NotNull
    @Pattern(regexp = ".*\\S.*", message = "Must be not empty")
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
    @DecimalMin("1.00")
    private BigDecimal priceBusiness;

    @NotNull
    @DecimalMin("1.00")
    private BigDecimal priceEconomy;

    @NotNull
    private Schedule schedule;

    @JsonSerialize(using = CustomListDateSerealize.class)
    @JsonDeserialize(using = CustomListDateDeserealize.class)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<Date> dates;

    @AssertFalse
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
