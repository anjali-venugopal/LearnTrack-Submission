package com.airtribe.learntrack.entity;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String batch;
    private boolean active;

    public Student() {
        this.active = true;
    }

    public Student(int id, String firstName, String lastName, String batch) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.batch = batch;
        this.active = true;
    }

    public Student(int id, String firstName, String lastName, String email, String batch) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.batch = batch;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDisplayName() {
        return firstName + " " + lastName + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return "Student{id=" + id
                + ", name=" + getDisplayName()
                + ", email=" + (email != null ? email : "N/A")
                + ", batch=" + batch
                + ", active=" + active + "}";
    }
}
