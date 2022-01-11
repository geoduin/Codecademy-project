package domain;

import java.time.LocalDate;

public class Student {
    private String studentName;
    private Gender gender;
    private String email;
    private LocalDate dateOfBirth;
    private String street;
    private int houseNumber;
    private String postalCode;
    private String country;
    private String city;

    public Student(String name, Gender gender, String email, LocalDate dateOfBirth, String street, int houseNumber,
            String postalCode,
            String country,
            String city) {
        this.studentName = name;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getstreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setstreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return this.studentName +  ", Email: " + this.email;
    }


}