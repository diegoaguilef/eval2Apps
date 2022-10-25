package com.diego.eval2apps.models;

import java.text.DateFormat;
import java.util.Date;

public class Score {
    private int id;
    private Date evalDate;
    private float score;
    private String rut;

    public Score() {
    }

    public Score(Date evalDate, float score, String rut) {
        this.evalDate = evalDate;
        this.score = score;
        this.rut = rut;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEvalDate() {
        return evalDate;
    }
    public String getEvalDateFormatted(){

       return "";
    }

    public void setEvalDate(Date evalDate) {
        this.evalDate = evalDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", evalDate=" + evalDate +
                ", score=" + score +
                ", rut='" + rut + '\'' +
                '}';
    }
}
