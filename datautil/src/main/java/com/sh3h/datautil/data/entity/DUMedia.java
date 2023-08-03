package com.sh3h.datautil.data.entity;


public class DUMedia implements IDUEntity {
    public static final int WENJIANLX_PIC = 0;
    public static final int WENJIANLX_DELAY = 1;
    public static final int WENJIANLX_VIDEO = 2;

    public static final int MEDIA_TYPE_CHAOBIAO = 0;
    public static final int MEDIA_TYPE_WAIFU = 1;
    public static final int MEDIA_TYPE_SAMPLING = 2;
    public static final int MEDIA_TYPE_RUSH_PAY = 4;
    public static final int MEDIA_TYPE_DELAY = 5;

    public static final int SHANGCHUANBZ_WEISHANGC = 0; // 未上传
    public static final int SHANGCHUANBZ_ZHENGZAISC = 1;  // 正在上传
    public static final int SHANGCHUANBZ_YISHANGC = 2;  // 已上传

    private int id;
    private String cid;
    private int wenjianlx; // 0图片 1语音 2视频
    private String wenjianlj;
    private String wenjiannr;
    private String wenjianmc;
    private int chaobiaoid;
    private int x;
    private int y;
    private String beizhu;
    private int shangchuanbz; // 0; 未上传, 1: 正在上传, 2: 已上传
    private int type;
    private String ch;
    private int renwubh;
    private String account;
    private long createrq;
    private String url;
    private String fileHash;

    public DUMedia() {

    }

    public DUMedia(String account,
                   int renwubh,
                   String ch,
                   String cid) {
        this.account = account;
        this.renwubh = renwubh;
        this.ch = ch;
        this.cid = cid;
    }

    public DUMedia(String account,
                   int renwubh,
                   String cid) {
        this.account = account;
        this.renwubh = renwubh;
        this.cid = cid;
    }

    public DUMedia(int id,
                   String cid,
                   int wenjianlx,
                   String wenjianlj,
                   String wenjiannr,
                   String wenjianmc,
                   int chaobiaoid,
                   int x,
                   int y,
                   String beizhu,
                   int shangchuanbz,
                   int type,
                   String ch,
                   int renwubh,
                   String account,
                   long createrq,
                   String url,
                   String fileHash) {
        this.id = id;
        this.cid = cid;
        this.wenjianlx = wenjianlx;
        this.wenjianlj = wenjianlj;
        this.wenjiannr = wenjiannr;
        this.wenjianmc = wenjianmc;
        this.chaobiaoid = chaobiaoid;
        this.x = x;
        this.y = y;
        this.beizhu = beizhu;
        this.shangchuanbz = shangchuanbz;
        this.type = type;
        this.renwubh = renwubh;
        this.ch = ch;
        this.account = account;
        this.createrq = createrq;
        this.url = url;
        this.fileHash = fileHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getWenjianlx() {
        return wenjianlx;
    }

    public void setWenjianlx(int wenjianlx) {
        this.wenjianlx = wenjianlx;
    }

    public String getWenjianlj() {
        return wenjianlj;
    }

    public void setWenjianlj(String wenjianlj) {
        this.wenjianlj = wenjianlj;
    }

    public String getWenjiannr() {
        return wenjiannr;
    }

    public void setWenjiannr(String wenjiannr) {
        this.wenjiannr = wenjiannr;
    }

    public String getWenjianmc() {
        return wenjianmc;
    }

    public void setWenjianmc(String wenjianmc) {
        this.wenjianmc = wenjianmc;
    }

    public int getChaobiaoid() {
        return chaobiaoid;
    }

    public void setChaobiaoid(int chaobiaoid) {
        this.chaobiaoid = chaobiaoid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public int getShangchuanbz() {
        return shangchuanbz;
    }

    public void setShangchuanbz(int shangchuanbz) {
        this.shangchuanbz = shangchuanbz;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getRenwubh() {
        return renwubh;
    }

    public void setRenwubh(int renwubh) {
        this.renwubh = renwubh;
    }

    public long getCreaterq() {
        return createrq;
    }

    public void setCreaterq(long createrq) {
        this.createrq = createrq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}
