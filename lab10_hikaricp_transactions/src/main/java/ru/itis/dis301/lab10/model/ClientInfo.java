package ru.itis.dis301.lab10.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ClientInfo {
    private String phone;
    private String address;
    private String passport;

    public ClientInfo() {}

    public ClientInfo(String phone, String address, String passport) {
        this.phone = phone;
        this.address = address;
        this.passport = passport;
    }
}
