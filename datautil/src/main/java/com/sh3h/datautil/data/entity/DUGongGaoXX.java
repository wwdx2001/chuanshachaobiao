package com.sh3h.datautil.data.entity;


public class DUGongGaoXX implements IDUEntity {
    private int gonggaobh;
    private String gonggaobt;
    private String gonggaonr;
    private String fabur;
    private long fabusj;
    private String jieshour;
    private String beizhu;
    private int sifouyd;

    public DUGongGaoXX(int gonggaobh,
                       String gonggaobt,
                       String gonggaonr,
                       String fabur,
                       long fabusj,
                       String jieshour,
                       String beizhu,
                       int sifouyd) {
        this.gonggaobh = gonggaobh;
        this.gonggaobt = gonggaobt;
        this.gonggaonr = gonggaonr;
        this.fabur = fabur;
        this.fabusj = fabusj;
        this.jieshour = jieshour;
        this.beizhu = beizhu;
        this.sifouyd = sifouyd;
    }

    public int getGonggaobh() {
        return gonggaobh;
    }

    public void setGonggaobh(int gonggaobh) {
        this.gonggaobh = gonggaobh;
    }

    public String getGonggaobt() {
        return gonggaobt;
    }

    public void setGonggaobt(String gonggaobt) {
        this.gonggaobt = gonggaobt;
    }

    public String getGonggaonr() {
        return gonggaonr;
    }

    public void setGonggaonr(String gonggaonr) {
        this.gonggaonr = gonggaonr;
    }

    public String getFabur() {
        return fabur;
    }

    public void setFabur(String fabur) {
        this.fabur = fabur;
    }

    public long getFabusj() {
        return fabusj;
    }

    public void setFabusj(long fabusj) {
        this.fabusj = fabusj;
    }

    public String getJieshour() {
        return jieshour;
    }

    public void setJieshour(String jieshour) {
        this.jieshour = jieshour;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public int getSifouyd() {
        return sifouyd;
    }

    public void setSifouyd(int sifouyd) {
        this.sifouyd = sifouyd;
    }
}
