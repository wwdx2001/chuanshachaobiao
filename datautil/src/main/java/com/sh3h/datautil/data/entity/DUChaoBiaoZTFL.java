package com.sh3h.datautil.data.entity;


public class DUChaoBiaoZTFL implements IDUEntity {
    private int fenleibm;
    private String fenleimc;

    public DUChaoBiaoZTFL() {
        this.fenleibm = 0;
        this.fenleimc = null;
    }

    public DUChaoBiaoZTFL(int fenleibm, String fenleimc) {
        this.fenleibm = fenleibm;
        this.fenleimc = fenleimc;
    }

    public int getFenleibm() {
        return fenleibm;
    }

    public void setFenleibm(int fenleibm) {
        this.fenleibm = fenleibm;
    }

    public String getFenleimc() {
        return fenleimc;
    }

    public void setFenleimc(String fenleimc) {
        this.fenleimc = fenleimc;
    }

    @Override
    public String toString() {
        return this.fenleimc;
    }
}
