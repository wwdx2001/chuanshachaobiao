package com.sh3h.datautil.data.entity;


public class DUShuiLiangFTXX implements IDUEntity {
    private String cid;
    private String jianhao;
    private int fentanfs;
    private double fentanl;
    private int paixu;

    public DUShuiLiangFTXX() {

    }

    public DUShuiLiangFTXX(String cid,
                           String jianhao,
                           int fentanfs,
                           double fentanl,
                           int paixu) {
        this.cid = cid;
        this.jianhao = jianhao;
        this.fentanfs = fentanfs;
        this.fentanl = fentanl;
        this.paixu = paixu;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getJianhao() {
        return jianhao;
    }

    public void setJianhao(String jianhao) {
        this.jianhao = jianhao;
    }

    public int getFentanfs() {
        return fentanfs;
    }

    public void setFentanfs(int fentanfs) {
        this.fentanfs = fentanfs;
    }

    public double getFentanl() {
        return fentanl;
    }

    public void setFentanl(double fentanl) {
        this.fentanl = fentanl;
    }

    public int getPaixu() {
        return paixu;
    }

    public void setPaixu(int paixu) {
        this.paixu = paixu;
    }
}
