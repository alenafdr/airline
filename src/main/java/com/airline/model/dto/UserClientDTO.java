package com.airline.model.dto;

import com.airline.dto.validation.Exist;
import com.airline.dto.validation.New;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserClientDTO extends UserEntityDTO {

    @Pattern(groups = {New.class, Exist.class}, regexp = "^[-A-Za-z0-9!#$%&\\'*+\\/=?^_`{|}~]+(\\.[-A-Za-z0-9!#$%&\\'*+\\/=?^_`{|}~]+)*@([A-Za-z0-9]([-A-Za-z0-9]{0,61}[A-Za-z0-9])?\\.)+([A-Za-z0-9][-A-Za-z0-9]{0,28}[A-Za-z0-9])$", message = "Email not valid")
    @NotNull(groups = {New.class, Exist.class})
    private String email;

    @Pattern(regexp = "^(\\+7|8)-?9(-?[0-9]){9}?$", message = "The phone number can contains only the numbers of " +
            "mobile operators in Russia, can start with +7 or 8, should not contains symbol()", groups = {New.class, Exist.class})
    @NotNull(groups = {New.class, Exist.class})
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
