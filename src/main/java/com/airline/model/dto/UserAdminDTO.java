package com.airline.model.dto;

import javax.validation.constraints.NotNull;

public class UserAdminDTO extends UserEntityDTO {

    @NotNull
    private String position;

    public UserAdminDTO() {
        userType = "admin";
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "UserAdminDTO{" +
                "position='" + position + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", userType='" + userType + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
