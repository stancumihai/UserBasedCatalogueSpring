package com.example.db_project.model.users;


public class Address {
    private int id;
    private int userId;
    private String street;
    private int number;
    private String phone;
    private String email;
    private String IBAN;
    private int contractNr;

    public Address() {

    }

    public Address(int id, int userId, String street, int number, String phone, String email, String BAN, int contractNr) {
        this.id = id;
        this.userId = userId;
        this.street = street;
        this.number = number;
        this.phone = phone;
        this.email = email;
        this.IBAN = BAN;
        this.contractNr = contractNr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int getContractNr() {
        return contractNr;
    }

    public void setContractNr(int contractNr) {
        this.contractNr = contractNr;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", contractNr=" + contractNr +
                '}';
    }
}
