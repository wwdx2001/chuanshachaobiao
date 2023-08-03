package com.sh3h.datautil.data.entity;


public class DUFeiYongZKL implements IDUEntity {
    private String cid;
    private String jianhao;
    private int feiyongdlbh;
    private double zhekoul;

    public DUFeiYongZKL() {

    }

    public DUFeiYongZKL(String cid,
                        String jianhao,
                        int feiyongdlbh,
                        double zhekoul) {
        this.cid = cid;
        this.jianhao = jianhao;
        this.feiyongdlbh = feiyongdlbh;
        this.zhekoul = zhekoul;
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

    public int getFeiyongdlbh() {
        return feiyongdlbh;
    }

    public void setFeiyongdlbh(int feiyongdlbh) {
        this.feiyongdlbh = feiyongdlbh;
    }

    public double getZhekoul() {
        return zhekoul;
    }

    public void setZhekoul(double zhekoul) {
        this.zhekoul = zhekoul;
    }
}
