package com.airline.model.dto;

import com.airline.dto.validation.Exist;
import com.airline.dto.validation.New;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class UserEntityDTO {
    @Null(groups = {New.class, Exist.class})
    protected Long id;

    @NotNull(groups = {New.class, Exist.class})
    protected String firstName;

    @NotNull(groups = {New.class, Exist.class})
    protected String lastName;

    protected String patronymic;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String userType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(groups = New.class)
    @Null(groups = Exist.class)
    protected String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(groups = New.class)
    @Null(groups = Exist.class)
    protected String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    protected String oldPassword;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    protected String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @JsonIgnore
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @JsonIgnore
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserEntityDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", userType='" + userType + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
