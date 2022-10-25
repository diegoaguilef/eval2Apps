package com.diego.eval2apps.models;

public class Student extends Person {
    private String email;
    private String course;


    public Student() {
    }

    public Student(String rut, String name, String lastname, int age, String address, String email, String course) {
        super(rut, name, lastname, age, address);
        this.email = email;
        this.course = course;
    }

    public Student(String rut, String name, String email, String course) {
        super(rut, name);
        this.email = email;
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}