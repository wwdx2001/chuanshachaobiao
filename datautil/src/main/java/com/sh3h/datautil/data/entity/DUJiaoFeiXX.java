package com.sh3h.datautil.data.entity;


public class DUJiaoFeiXX implements IDUEntity {
    private String cid;
    private int zhangwuny;
    private int feeid;
    private long chaobiaorq;
    private long kaizhangrq;
    private long shoufeirq;
    private double je;
    private double shishouwyj;
    private double shishouzje;
    private String shoufeitj;

    public DUJiaoFeiXX(String cid,
                       int zhangwuny,
                       int feeid,
                       long chaobiaorq,
                       long kaizhangrq,
                       long shoufeirq,
                       double je,
                       double shishouwyj,
                       double shishouzje,
                       String shoufeitj) {
        this.cid = cid;
        this.zhangwuny = zhangwuny;
        this.feeid = feeid;
        this.chaobiaorq = chaobiaorq;
        this.kaizhangrq = kaizhangrq;
        this.shoufeirq = shoufeirq;
        this.je = je;
        this.shishouwyj = shishouwyj;
        this.shishouzje = shishouzje;
        this.shoufeitj = shoufeitj;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getZhangwuny() {
        return zhangwuny;
    }

    public void setZhangwuny(int zhangwuny) {
        this.zhangwuny = zhangwuny;
    }

    public int getFeeid() {
        return feeid;
    }

    public void setFeeid(int feeid) {
        this.feeid = feeid;
    }

    public long getChaobiaorq() {
        return chaobiaorq;
    }

    public void setChaobiaorq(long chaobiaorq) {
        this.chaobiaorq = chaobiaorq;
    }

    public long getKaizhangrq() {
        return kaizhangrq;
    }

    public void setKaizhangrq(long kaizhangrq) {
        this.kaizhangrq = kaizhangrq;
    }

    public long getShoufeirq() {
        return shoufeirq;
    }

    public void setShoufeirq(long shoufeirq) {
        this.shoufeirq = shoufeirq;
    }

    public double getJe() {
        return je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    public double getShishouwyj() {
        return shishouwyj;
    }

    public void setShishouwyj(double shishouwyj) {
        this.shishouwyj = shishouwyj;
    }

    public double getShishouzje() {
        return shishouzje;
    }

    public void setShishouzje(double shishouzje) {
        this.shishouzje = shishouzje;
    }

    public String getShoufeitj() {
        return shoufeitj;
    }

    public void setShoufeitj(String shoufeitj) {
        this.shoufeitj = shoufeitj;
    }
}
