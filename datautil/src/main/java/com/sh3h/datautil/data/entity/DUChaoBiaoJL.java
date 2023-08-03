package com.sh3h.datautil.data.entity;


public class DUChaoBiaoJL implements IDUEntity {
    private long id;
    private String cid;
    private int chaobiaon;
    private int ichaobiaoy;
    private int chaoci;
    private long chaobiaorq;
    private int shangcicm;
    private int bencicm;
    private int chaojiansl;
    private String chaobiaozt;
    private String schaobiaoy;
    private String chaobiaobz;
    private int chaobiaoztbm;
    private int lianggaoldyybm;

    public DUChaoBiaoJL() {

    }

    public DUChaoBiaoJL(long id,
                        String cid,
                        int chaobiaon,
                        int ichaobiaoy,
                        int chaoci,
                        long chaobiaorq,
                        int shangcicm,
                        int bencicm,
                        int chaojiansl,
                        String chaobiaozt,
                        String schaobiaoy,
                        String chaobiaobz,
                        int chaobiaoztbm,
                        int lianggaoldyybm) {
        this.id = id;
        this.cid = cid;
        this.chaobiaon = chaobiaon;
        this.ichaobiaoy = ichaobiaoy;
        this.chaoci = chaoci;
        this.chaobiaorq = chaobiaorq;
        this.shangcicm = shangcicm;
        this.bencicm = bencicm;
        this.chaojiansl = chaojiansl;
        this.chaobiaozt = chaobiaozt;
        this.schaobiaoy = schaobiaoy;
        this.chaobiaobz = chaobiaobz;
        this.chaobiaoztbm = chaobiaoztbm;
        this.lianggaoldyybm = lianggaoldyybm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getIchaobiaoy() {
        return ichaobiaoy;
    }

    public void setIchaobiaoy(int ichaobiaoy) {
        this.ichaobiaoy = ichaobiaoy;
    }

    public int getChaoci() {
        return chaoci;
    }

    public void setChaoci(int chaoci) {
        this.chaoci = chaoci;
    }

    public long getChaobiaorq() {
        return chaobiaorq;
    }

    public void setChaobiaorq(long chaobiaorq) {
        this.chaobiaorq = chaobiaorq;
    }

    public int getShangcicm() {
        return shangcicm;
    }

    public void setShangcicm(int shangcicm) {
        this.shangcicm = shangcicm;
    }

    public int getBencicm() {
        return bencicm;
    }

    public void setBencicm(int bencicm) {
        this.bencicm = bencicm;
    }

    public int getChaojiansl() {
        return chaojiansl;
    }

    public void setChaojiansl(int chaojiansl) {
        this.chaojiansl = chaojiansl;
    }

    public String getChaobiaozt() {
        return chaobiaozt;
    }

    public void setChaobiaozt(String chaobiaozt) {
        this.chaobiaozt = chaobiaozt;
    }

    public String getSchaobiaoy() {
        return schaobiaoy;
    }

    public void setSchaobiaoy(String schaobiaoy) {
        this.schaobiaoy = schaobiaoy;
    }

    public String getChaobiaobz() {
        return chaobiaobz;
    }

    public void setChaobiaobz(String chaobiaobz) {
        this.chaobiaobz = chaobiaobz;
    }

    public int getChaobiaoztbm() {
        return chaobiaoztbm;
    }

    public void setChaobiaoztbm(int chaobiaoztbm) {
        this.chaobiaoztbm = chaobiaoztbm;
    }

    public int getLianggaoldyybm() {
        return lianggaoldyybm;
    }

    public void setLianggaoldyybm(int lianggaoldyybm) {
        this.lianggaoldyybm = lianggaoldyybm;
    }
}
