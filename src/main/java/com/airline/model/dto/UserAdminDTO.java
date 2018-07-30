package com.airline.model.dto;

public class UserAdminDTO extends BasedUserDTO {
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
