package com.airline.model;

public class UserClient extends UserEntity {
    private String email;
    private String phone;

    public UserClient() {
    }

    public UserClient(Long id) {
        super(id);
    }

    public UserClient(Long id, String login) {
        super(id, login);
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
        return "UserClient{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
