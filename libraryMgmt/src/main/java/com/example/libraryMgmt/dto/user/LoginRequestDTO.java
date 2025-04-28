package com.example.libraryMgmt.dto.user;

public class LoginRequestDTO {
    private String phoneNumber;
    private String password;

    public LoginRequestDTO() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
