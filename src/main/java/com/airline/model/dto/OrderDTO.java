package com.airline.model.dto;

import com.airline.rest.json.CustomDateDeserializer;
import com.airline.rest.json.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    @Null
    private Long orderId;

    @NotNull
    private Long flightId;

    private String fromTown;

    private String toTown;

    private String flightName;

    private String planeName;

    @NotNull
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(dataType = "java.lang.String", example = "YYYY-MM-DD")
    private Date date;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(dataType = "java.lang.String", example = "hh:mm:ss")
    private Time start;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(dataType = "java.lang.String", example = "hh:mm:ss")
    private Time duration;

    @Size(min = 1)
    @NotNull
    private List<TicketDTO> passengers;

    @Null
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

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
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
