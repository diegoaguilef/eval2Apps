package com.diego.eval2apps.models;

public class Person {
    private String rut;
    private String name;
    private String lastname;
    private int age;
    private String address;

    public Person() {
    }

    public Person(String rut, String name) {
        this.rut = rut;
        this.name = name;
    }

    public Person(String rut, String name, String lastname, int age, String address) {
        this.rut = rut;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
