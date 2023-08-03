package com.sh3h.datautil.data.entity;


public class DUJianHao implements IDUEntity {
    private int id;
    private int tiaojiah;
    private String dalei;
    private String zhonglei;
    private String xiaolei;
    private String jianhao;
    private int jietis;
    private String beizhu;

    public DUJianHao() {
        this.id = 0;
        this.tiaojiah = 0;
        this.dalei = null;
        this.zhonglei = null;
        this.xiaolei = null;
        this.jianhao = null;
        this.jietis = 0;
        this.beizhu = null;
    }

    public DUJianHao(int id,
                     int tiaojiah,
                     String dalei,
                     String zhonglei,
                     String xiaolei,
                     String jianhao,
                     int jietis,
                     String beizhu) {
        this.id = id;
        this.tiaojiah = tiaojiah;
        this.dalei = dalei;
        this.zhonglei = zhonglei;
        this.xiaolei = xiaolei;
        this.jianhao = jianhao;
        this.jietis = jietis;
        this.beizhu = beizhu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiaojiah() {
        return tiaojiah;
    }

    public void setTiaojiah(int tiaojiah) {
        this.tiaojiah = tiaojiah;
    }

    public String getDalei() {
        return dalei;
    }

    public void setDalei(String dalei) {
        this.dalei = dalei;
    }

    public String getZhonglei() {
        return zhonglei;
    }

    public void setZhonglei(String zhonglei) {
        this.zhonglei = zhonglei;
    }

    public String getXiaolei() {
        return xiaolei;
    }

    public void setXiaolei(String xiaolei) {
        this.xiaolei = xiaolei;
    }

    public String getJianhao() {
        return jianhao;
    }

    public void setJianhao(String jianhao) {
        this.jianhao = jianhao;
    }

    public int getJietis() {
        return jietis;
    }

    public void setJietis(int jietis) {
        this.jietis = jietis;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
