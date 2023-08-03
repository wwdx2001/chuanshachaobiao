package com.sh3h.datautil.data.entity;


public class DUJianHaoMX implements IDUEntity {
    private int id;
    private int tiaojiah;
    private String jianhao;
    private int feiyongdlid;
    private int feiyongid;
    private int qishiy;
    private int jieshuy;
    private int kaishisl;
    private int jieshusl;
    private int jietijb;
    private double zhekoul;
    private int zhekoulx;
    private double jiage;
    private String feiyongmc;
    private double xiShu;
    private int jietis;
    private String daLei;
    private String zhongLei;

    public DUJianHaoMX() {
        this.id = 0;
        this.tiaojiah = 0;
        this.jianhao = null;
        this.feiyongdlid = 0;
        this.feiyongid = 0;
        this.qishiy = 0;
        this.jieshuy = 0;
        this.kaishisl = 0;
        this.jieshusl = 0;
        this.jietijb = 0;
        this.zhekoul = 0;
        this.zhekoulx = 0;
        this.jiage = 0;
        this.feiyongmc = null;
        this.xiShu = 0;
        this.jietis = 0;
        this.daLei = null;
        this.zhongLei = null;
    }

    public DUJianHaoMX(int id,
                       int tiaojiah,
                       String jianhao,
                       int feiyongdlid,
                       int feiyongid,
                       int qishiy,
                       int jieshuy,
                       int kaishisl,
                       int jieshusl,
                       int jietijb,
                       double zhekoul,
                       int zhekoulx,
                       double jiage,
                       String feiyongmc,
                       double xiShu,
                       int jietis,
                       String daLei,
                       String zhongLei) {
        this.id = id;
        this.tiaojiah = tiaojiah;
        this.jianhao = jianhao;
        this.feiyongdlid = feiyongdlid;
        this.feiyongid = feiyongid;
        this.qishiy = qishiy;
        this.jieshuy = jieshuy;
        this.kaishisl = kaishisl;
        this.jieshusl = jieshusl;
        this.jietijb = jietijb;
        this.zhekoul = zhekoul;
        this.zhekoulx = zhekoulx;
        this.jiage = jiage;
        this.feiyongmc = feiyongmc;
        this.xiShu = xiShu;
        this.jietis = jietis;
        this.daLei = daLei;
        this.zhongLei = zhongLei;
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

    public String getJianhao() {
        return jianhao;
    }

    public void setJianhao(String jianhao) {
        this.jianhao = jianhao;
    }

    public int getFeiyongdlid() {
        return feiyongdlid;
    }

    public void setFeiyongdlid(int feiyongdlid) {
        this.feiyongdlid = feiyongdlid;
    }

    public int getFeiyongid() {
        return feiyongid;
    }

    public void setFeiyongid(int feiyongid) {
        this.feiyongid = feiyongid;
    }

    public int getQishiy() {
        return qishiy;
    }

    public void setQishiy(int qishiy) {
        this.qishiy = qishiy;
    }

    public int getJieshuy() {
        return jieshuy;
    }

    public void setJieshuy(int jieshuy) {
        this.jieshuy = jieshuy;
    }

    public int getKaishisl() {
        return kaishisl;
    }

    public void setKaishisl(int kaishisl) {
        this.kaishisl = kaishisl;
    }

    public int getJieshusl() {
        return jieshusl;
    }

    public void setJieshusl(int jieshusl) {
        this.jieshusl = jieshusl;
    }

    public int getJietijb() {
        return jietijb;
    }

    public void setJietijb(int jietijb) {
        this.jietijb = jietijb;
    }

    public double getZhekoul() {
        return zhekoul;
    }

    public void setZhekoul(double zhekoul) {
        this.zhekoul = zhekoul;
    }

    public int getZhekoulx() {
        return zhekoulx;
    }

    public void setZhekoulx(int zhekoulx) {
        this.zhekoulx = zhekoulx;
    }

    public double getJiage() {
        return jiage;
    }

    public void setJiage(double jiage) {
        this.jiage = jiage;
    }

    public String getFeiyongmc() {
        return feiyongmc;
    }

    public void setFeiyongmc(String feiyongmc) {
        this.feiyongmc = feiyongmc;
    }

    public double getXiShu() {
        return xiShu;
    }

    public void setXiShu(double xiShu) {
        this.xiShu = xiShu;
    }

    public int getJietis() {
        return jietis;
    }

    public void setJietis(int jietis) {
        this.jietis = jietis;
    }

    public String getDaLei() {
        return daLei;
    }

    public void setDaLei(String daLei) {
        this.daLei = daLei;
    }

    public String getZhongLei() {
        return zhongLei;
    }

    public void setZhongLei(String zhongLei) {
        this.zhongLei = zhongLei;
    }
}
