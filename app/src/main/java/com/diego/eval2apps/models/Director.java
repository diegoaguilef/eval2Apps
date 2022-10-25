package com.diego.eval2apps.models;

import java.util.Date;

public class Director extends Person {
    private String role;
    private String institution;
    private Date ingressDate;

    public Director(String rut, String name, String lastname, int age, String address, String role, String institution, Date ingressDate) {
        super(rut, name, lastname, age, address);
        this.role = role;
        this.institution = institution;
        this.ingressDate = ingressDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Date getIngressDate() {
        return ingressDate;
    }

    public void setIngressDate(Date ingressDate) {
        this.ingressDate = ingressDate;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Director{" +
                "role='" + role + '\'' +
                ", institution='" + institution + '\'' +
                ", ingressDate=" + ingressDate +
                '}';
    }
}
