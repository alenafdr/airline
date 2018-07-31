package com.airline.model.dto;

public class UserClientDTO extends UserEntityDTO {

    private String email;
    private String phone;


    public UserClientDTO() {
        super.userType = "client";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserClientDTO{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
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
