package com.sh3h.datautil.data.entity;

/**
 * Created by LiMeng on 2017/10/30.
 */

public class DULgld {
    private String cid;
    private int shangCiCM;
    private int benCiCM;
    private int chaoJianSL;
    private String zhuangTaiMC;
    private int chaoBiaoBZ;
    private int ceNeiXH;

    public DULgld() {
    }

    public DULgld(String cid, int shangCiCM, int benCiCM, int chaoJianSL, String zhuangTaiMC, int chaoBiaoBZ, int ceNeiXH) {
        this.cid = cid;
        this.shangCiCM = shangCiCM;
        this.benCiCM = benCiCM;
        this.chaoJianSL = chaoJianSL;
        this.zhuangTaiMC = zhuangTaiMC;
        this.chaoBiaoBZ = chaoBiaoBZ;
        this.ceNeiXH = ceNeiXH;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getShangCiCM() {
        return shangCiCM;
    }

    public void setShangCiCM(int shangCiCM) {
        this.shangCiCM = shangCiCM;
    }

    public int getBenCiCM() {
        return benCiCM;
    }

    public void setBenCiCM(int benCiCM) {
        this.benCiCM = benCiCM;
    }

    public int getChaoJianSL() {
        return chaoJianSL;
    }

    public void setChaoJianSL(int chaoJianSL) {
        this.chaoJianSL = chaoJianSL;
    }

    public String getZhuangTaiMC() {
        return zhuangTaiMC;
    }

    public void setZhuangTaiMC(String zhuangTaiMC) {
        this.zhuangTaiMC = zhuangTaiMC;
    }

    public int getChaoBiaoBZ() {
        return chaoBiaoBZ;
    }

    public void setChaoBiaoBZ(int chaoBiaoBZ) {
        this.chaoBiaoBZ = chaoBiaoBZ;
    }

    public int getCeNeiXH() {
        return ceNeiXH;
    }

    public void setCeNeiXH(int ceNeiXH) {
        this.ceNeiXH = ceNeiXH;
    }
}
