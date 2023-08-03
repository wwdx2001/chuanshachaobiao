package com.sh3h.datautil.data.entity;


public class DUFeiYongZC implements IDUEntity {
    private int id;
    private int tiaojiah;
    private int feiyongid;
    private String feiyongmc;
    private double jiage;
    private int feiyongdlid;
    private double xishu;

    public DUFeiYongZC() {
        this.id = 0;
        this.tiaojiah = 0;
        this.feiyongid = 0;
        this.feiyongmc = null;
        this.jiage = 0;
        this.feiyongdlid = 0;
        this.xishu = 0;
    }

    public DUFeiYongZC(int id,
                       int tiaojiah,
                       int feiyongid,
                       String feiyongmc,
                       double jiage,
                       int feiyongdlid,
                       double xishu) {
        this.id = id;
        this.tiaojiah = tiaojiah;
        this.feiyongid = feiyongid;
        this.feiyongmc = feiyongmc;
        this.jiage = jiage;
        this.feiyongdlid = feiyongdlid;
        this.xishu = xishu;
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

    public int getFeiyongid() {
        return feiyongid;
    }

    public void setFeiyongid(int feiyongid) {
        this.feiyongid = feiyongid;
    }

    public String getFeiyongmc() {
        return feiyongmc;
    }

    public void setFeiyongmc(String feiyongmc) {
        this.feiyongmc = feiyongmc;
    }

    public double getJiage() {
        return jiage;
    }

    public void setJiage(double jiage) {
        this.jiage = jiage;
    }

    public int getFeiyongdlid() {
        return feiyongdlid;
    }

    public void setFeiyongdlid(int feiyongdlid) {
        this.feiyongdlid = feiyongdlid;
    }

    public double getXishu() {
        return xishu;
    }

    public void setXishu(double xishu) {
        this.xishu = xishu;
    }
}
