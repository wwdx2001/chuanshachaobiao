package com.sh3h.datautil.data.entity;


public class DUChaoBiaoZT implements IDUEntity {
    private int zhuangtaibm;
    private int shuiliangsfbm;
    private int zhuangtaiflbm;
    private String zhuangtaimc;
    private String kuaijiejpc;

    public DUChaoBiaoZT() {
        this.zhuangtaibm = 0;
        this.shuiliangsfbm = 0;
        this.zhuangtaiflbm = 0;
        this.zhuangtaimc = null;
        this.kuaijiejpc = null;
    }

    public DUChaoBiaoZT(int zhuangtaibm,
                        int shuiliangsfbm,
                        int zhuangtaiflbm,
                        String zhuangtaimc,
                        String kuaijiejpc) {
        this.zhuangtaibm = zhuangtaibm;
        this.shuiliangsfbm = shuiliangsfbm;
        this.zhuangtaiflbm = zhuangtaiflbm;
        this.zhuangtaimc = zhuangtaimc;
        this.kuaijiejpc = kuaijiejpc;
    }

    public int getZhuangtaibm() {
        return zhuangtaibm;
    }

    public void setZhuangtaibm(int zhuangtaibm) {
        this.zhuangtaibm = zhuangtaibm;
    }

    public int getShuiliangsfbm() {
        return shuiliangsfbm;
    }

    public void setShuiliangsfbm(int shuiliangsfbm) {
        this.shuiliangsfbm = shuiliangsfbm;
    }

    public int getZhuangtaiflbm() {
        return zhuangtaiflbm;
    }

    public void setZhuangtaiflbm(int zhuangtaiflbm) {
        this.zhuangtaiflbm = zhuangtaiflbm;
    }

    public String getZhuangtaimc() {
        return zhuangtaimc;
    }

    public void setZhuangtaimc(String zhuangtaimc) {
        this.zhuangtaimc = zhuangtaimc;
    }

    public String getKuaijiejpc() {
        return kuaijiejpc;
    }

    public void setKuaijiejpc(String kuaijiejpc) {
        this.kuaijiejpc = kuaijiejpc;
    }
}
