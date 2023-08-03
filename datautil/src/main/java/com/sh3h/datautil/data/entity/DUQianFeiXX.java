package com.sh3h.datautil.data.entity;


public class DUQianFeiXX implements IDUEntity {
    private String ch;
    private String cid;
    private int chaobiaon;
    private int chaobiaoy;
    private int zhangwuny;
    private long chaobiaorq;
    private int chaoci;
    private int kaizhangsl;
    private double je;
    private int feeid;
    private double yingshouwyj;
    private double shuifei;
    private double paishuif;

    public double getShuifei() {
        return shuifei;
    }

    public void setShuifei(double shuifei) {
        this.shuifei = shuifei;
    }

    public double getPaishuif() {
        return paishuif;
    }

    public void setPaishuif(double paishuif) {
        this.paishuif = paishuif;
    }

    public DUQianFeiXX(String ch,
                       String cid,
                       int chaobiaon,
                       int chaobiaoy,
                       int zhangwuny,
                       long chaobiaorq,
                       int chaoci,
                       int kaizhangsl,
                       double je,
                       int feeid,
                       double yingshouwyj,
                       double shuifei,
                       double paishuif) {
        this.ch = ch;
        this.cid = cid;
        this.chaobiaon = chaobiaon;
        this.chaobiaoy = chaobiaoy;
        this.zhangwuny = zhangwuny;
        this.chaobiaorq = chaobiaorq;
        this.chaoci = chaoci;
        this.kaizhangsl = kaizhangsl;
        this.je = je;
        this.feeid = feeid;
        this.yingshouwyj = yingshouwyj;
        this.shuifei = shuifei;
        this.paishuif = paishuif;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getChaobiaon() {
        return chaobiaon;
    }

    public void setChaobiaon(int chaobiaon) {
        this.chaobiaon = chaobiaon;
    }

    public int getChaobiaoy() {
        return chaobiaoy;
    }

    public void setChaobiaoy(int chaobiaoy) {
        this.chaobiaoy = chaobiaoy;
    }

    public int getZhangwuny() {
        return zhangwuny;
    }

    public void setZhangwuny(int zhangwuny) {
        this.zhangwuny = zhangwuny;
    }

    public long getChaobiaorq() {
        return chaobiaorq;
    }

    public void setChaobiaorq(long chaobiaorq) {
        this.chaobiaorq = chaobiaorq;
    }

    public int getChaoci() {
        return chaoci;
    }

    public void setChaoci(int chaoci) {
        this.chaoci = chaoci;
    }

    public int getKaizhangsl() {
        return kaizhangsl;
    }

    public void setKaizhangsl(int kaizhangsl) {
        this.kaizhangsl = kaizhangsl;
    }

    public double getJe() {
        return je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    public int getFeeid() {
        return feeid;
    }

    public void setFeeid(int feeid) {
        this.feeid = feeid;
    }

    public double getYingshouwyj() {
        return yingshouwyj;
    }

    public void setYingshouwyj(double yingshouwyj) {
        this.yingshouwyj = yingshouwyj;
    }
}
