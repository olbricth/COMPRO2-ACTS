package com.midterms.form;

public class StudentForm {
    private String name;
    private String password;
    private String email;
    private int age;
    private String gender;
    private String[] interests;
    private String country;
    private String message;
    private String dob;

    // Getters
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String[] getInterests() {
        return interests;
    }

    public String getCountry() {
        return country;
    }

    public String getMessage() {
        return message;
    }

    public String getDob() {
        return dob;
    }

    // Setters (optional)
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
