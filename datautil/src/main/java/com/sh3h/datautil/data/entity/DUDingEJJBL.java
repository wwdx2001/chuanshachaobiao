package com.sh3h.datautil.data.entity;


public class DUDingEJJBL implements IDUEntity {
    private int id;
    private double beil;
    private int kaishifloor;
    private int jieshufloor;

    public DUDingEJJBL() {

    }

    public DUDingEJJBL(int id, double beil, int kaishifloor, int jieshufloor) {
        this.id = id;
        this.beil = beil;
        this.kaishifloor = kaishifloor;
        this.jieshufloor = jieshufloor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBeil() {
        return beil;
    }

    public void setBeil(double beil) {
        this.beil = beil;
    }

    public int getKaishifloor() {
        return kaishifloor;
    }

    public void setKaishifloor(int kaishifloor) {
        this.kaishifloor = kaishifloor;
    }

    public int getJieshufloor() {
        return jieshufloor;
    }

    public void setJieshufloor(int jieshufloor) {
        this.jieshufloor = jieshufloor;
    }
}
