package com.sh3h.datautil.data.entity;


public class DUSampling implements IDUEntity {
    public static final int CHAOBIAOBZ_WEICHAO = 0;     // 未抄
    public static final int CHAOBIAOBZ_YICHAO = 1;      // 已抄
    public static final int CHAOBIAOBZ_KAIZHANG = 2;    // 开账
    public static final int CHAOBIAOBZ_YANCHI = 3;      // 延迟
    public static final int CHAOBIAOBZ_WAIFUYC = 4;     // 外复延迟

    public static final int SHANGCHUANBZ_WEISHANGC = 0; // 未上传
    public static final int SHANGCHUANBZ_ZHENGZAISC = 1;  // 正在上传
    public static final int SHANGCHUANBZ_YISHANGC = 2;  // 已上传

    private int id;
    private int renwubh;
    private String ch;
    private int ceneixh;
    private String cid;
    private String st;
    private int chaobiaon;
    private int ichaobiaoy;
    private int cc;
    private long chaobiaorq;
    private int shangcicm;
    private int bencicm;
    private int chaojiansl;
    private int zhuangtaibm;
    private String zhuangtaimc;
    private long shangcicbrq;
    private int shangciztbm;
    private String shangciztmc;
    private int shangcicjsl;
    private int shangciztlxs;
    private int pingjunl1;
    private int pingjunl2;
    private int pingjunl3;
    private double je;
    private String zongbiaocid;
    private String schaobiaoy;
    private int ichaobiaobz; // 0：未抄，1：已抄，2：开账，3：延迟，4：外复延迟
    private int jiubiaocm;
    private int xinbiaodm;
    private long huanbiaorq;
    private int fangshibm;
    private int lianggaoldyybm;
    private int chaobiaoid;
    private int zhuangtailxs;
    private int shuibiaobl;
    private double yongshuizkl;
    private double paishuizkl;
    private int tiaojiah;
    private String jianhao;
    private long xiazaisj;
    private int lingyongslsm;
    private int lianggaosl;
    private int liangdisl;
    private String x1;
    private String y1;
    private String x;
    private String y;
    private String schaobiaobz;
    private int ceneipx;
    private int xiazaics;
    private long zuihouycxzsj;
    private long zuihouycscsj;
    private int shangchuanbz; // 0：未上传；1: 正在上传, 2：已上传
    private int shenhebz;
    private int kaizhangbz;
    private int diaodongbz;
    private int waifuyybh;
    private String jietits;
    private String yanciyy;
    private int lastReadingChild;
    private int readingChild;
    private boolean isAdjustingSequence; // only existing in memory
    private boolean isChecked;

    private int jiChaSL;
    private int jiChaCM;
    private int jiChaZTBM;
    private long jiChaRQ;
    private String jiChaZTMC;

    public DUSampling() {

    }

    public DUSampling(int id,
                      int renwubh,
                      String ch,
                      int ceneixh,
                      String cid,
                      String st,
                      int chaobiaon,
                      int ichaobiaoy,
                      int cc,
                      long chaobiaorq,
                      int shangcicm,
                      int bencicm,
                      int chaojiansl,
                      int zhuangtaibm,
                      String zhuangtaimc,
                      long shangcicbrq,
                      int shangciztbm,
                      String shangciztmc,
                      int shangcicjsl,
                      int shangciztlxs,
                      int pingjunl1,
                      int pingjunl2,
                      int pingjunl3,
                      double je,
                      String zongbiaocid,
                      String schaobiaoy,
                      int ichaobiaobz,
                      int jiubiaocm,
                      int xinbiaodm,
                      long huanbiaorq,
                      int fangshibm,
                      int lianggaoldyybm,
                      int chaobiaoid,
                      int zhuangtailxs,
                      int shuibiaobl,
                      double yongshuizkl,
                      double paishuizkl,
                      int tiaojiah,
                      String jianhao,
                      long xiazaisj,
                      int lingyongslsm,
                      int lianggaosl,
                      int liangdisl,
                      String x1,
                      String y1,
                      String x,
                      String y,
                      String schaobiaobz,
                      int ceneipx,
                      int xiazaics,
                      long zuihouycxzsj,
                      long zuihouycscsj,
                      int shangchuanbz,
                      int shenhebz,
                      int kaizhangbz,
                      int diaodongbz,
                      int waifuyybh,
                      String jietits,
                      String yanciyy,
                      int lastReadingChild,
                      int readingChild,
                      int jiChaCM,
                      int jiChaSL,
                      int jiChaZTBM,
                      long jiChaRQ,
                      String jiChaZTMC


    ) {
        this.id = id;
        this.renwubh = renwubh;
        this.ch = ch;
        this.ceneixh = ceneixh;
        this.cid = cid;
        this.st = st;
        this.chaobiaon = chaobiaon;
        this.ichaobiaoy = ichaobiaoy;
        this.cc = cc;
        this.chaobiaorq = chaobiaorq;
        this.shangcicm = shangcicm;
        this.bencicm = bencicm;
        this.chaojiansl = chaojiansl;
        this.zhuangtaibm = zhuangtaibm;
        this.zhuangtaimc = zhuangtaimc;
        this.shangcicbrq = shangcicbrq;
        this.shangciztbm = shangciztbm;
        this.shangciztmc = shangciztmc;
        this.shangcicjsl = shangcicjsl;
        this.shangciztlxs = shangciztlxs;
        this.pingjunl1 = pingjunl1;
        this.pingjunl2 = pingjunl2;
        this.pingjunl3 = pingjunl3;
        this.je = je;
        this.zongbiaocid = zongbiaocid;
        this.schaobiaoy = schaobiaoy;
        this.ichaobiaobz = ichaobiaobz;
        this.jiubiaocm = jiubiaocm;
        this.xinbiaodm = xinbiaodm;
        this.huanbiaorq = huanbiaorq;
        this.fangshibm = fangshibm;
        this.lianggaoldyybm = lianggaoldyybm;
        this.chaobiaoid = chaobiaoid;
        this.zhuangtailxs = zhuangtailxs;
        this.shuibiaobl = shuibiaobl;
        this.yongshuizkl = yongshuizkl;
        this.paishuizkl = paishuizkl;
        this.tiaojiah = tiaojiah;
        this.jianhao = jianhao;
        this.xiazaisj = xiazaisj;
        this.lingyongslsm = lingyongslsm;
        this.lianggaosl = lianggaosl;
        this.liangdisl = liangdisl;
        this.x1 = x1;
        this.y1 = y1;
        this.x = x;
        this.y = y;
        this.schaobiaobz = schaobiaobz;
        this.ceneipx = ceneipx;
        this.xiazaics = xiazaics;
        this.zuihouycxzsj = zuihouycxzsj;
        this.zuihouycscsj = zuihouycscsj;
        this.shangchuanbz = shangchuanbz;
        this.shenhebz = shenhebz;
        this.kaizhangbz = kaizhangbz;
        this.diaodongbz = diaodongbz;
        this.waifuyybh = waifuyybh;
        this.jietits = jietits;
        this.yanciyy = yanciyy;
        this.lastReadingChild = lastReadingChild;
        this.readingChild = readingChild;
        this.isAdjustingSequence = false;
        this.isChecked = false;
        this.jiChaCM=jiChaCM;
        this.jiChaRQ=jiChaRQ;
        this.jiChaSL=jiChaSL;
        this.jiChaZTBM=jiChaZTBM;
        this.jiChaZTMC=jiChaZTMC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRenwubh() {
        return renwubh;
    }

    public void setRenwubh(int renwubh) {
        this.renwubh = renwubh;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public int getCeneixh() {
        return ceneixh;
    }

    public void setCeneixh(int ceneixh) {
        this.ceneixh = ceneixh;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
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

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
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

    public int getZhuangtaibm() {
        return zhuangtaibm;
    }

    public void setZhuangtaibm(int zhuangtaibm) {
        this.zhuangtaibm = zhuangtaibm;
    }

    public String getZhuangtaimc() {
        return zhuangtaimc;
    }

    public void setZhuangtaimc(String zhuangtaimc) {
        this.zhuangtaimc = zhuangtaimc;
    }

    public long getShangcicbrq() {
        return shangcicbrq;
    }

    public void setShangcicbrq(long shangcicbrq) {
        this.shangcicbrq = shangcicbrq;
    }

    public int getShangciztbm() {
        return shangciztbm;
    }

    public void setShangciztbm(int shangciztbm) {
        this.shangciztbm = shangciztbm;
    }

    public String getShangciztmc() {
        return shangciztmc;
    }

    public void setShangciztmc(String shangciztmc) {
        this.shangciztmc = shangciztmc;
    }

    public int getShangcicjsl() {
        return shangcicjsl;
    }

    public void setShangcicjsl(int shangcicjsl) {
        this.shangcicjsl = shangcicjsl;
    }

    public int getShangciztlxs() {
        return shangciztlxs;
    }

    public String getJiChaZTMC() {
        return jiChaZTMC;
    }

    public void setJiChaZTMC(String jiChaZTMC) {
        this.jiChaZTMC = jiChaZTMC;
    }

    public long getJiChaRQ() {
        return jiChaRQ;
    }

    public void setJiChaRQ(long jiChaRQ) {
        this.jiChaRQ = jiChaRQ;
    }

    public int getJiChaZTBM() {
        return jiChaZTBM;
    }

    public void setJiChaZTBM(int jiChaZTBM) {
        this.jiChaZTBM = jiChaZTBM;
    }

    public int getJiChaCM() {
        return jiChaCM;
    }

    public void setJiChaCM(int jiChaCM) {
        this.jiChaCM = jiChaCM;
    }

    public int getJiChaSL() {
        return jiChaSL;
    }

    public void setJiChaSL(int jiChaSL) {
        this.jiChaSL = jiChaSL;
    }

    public void setShangciztlxs(int shangciztlxs) {
        this.shangciztlxs = shangciztlxs;
    }

    public int getPingjunl1() {
        return pingjunl1;
    }

    public void setPingjunl1(int pingjunl1) {
        this.pingjunl1 = pingjunl1;
    }

    public int getPingjunl2() {
        return pingjunl2;
    }

    public void setPingjunl2(int pingjunl2) {
        this.pingjunl2 = pingjunl2;
    }

    public int getPingjunl3() {
        return pingjunl3;
    }

    public void setPingjunl3(int pingjunl3) {
        this.pingjunl3 = pingjunl3;
    }

    public double getJe() {
        return je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    public String getZongbiaocid() {
        return zongbiaocid;
    }

    public void setZongbiaocid(String zongbiaocid) {
        this.zongbiaocid = zongbiaocid;
    }

    public String getSchaobiaoy() {
        return schaobiaoy;
    }

    public void setSchaobiaoy(String schaobiaoy) {
        this.schaobiaoy = schaobiaoy;
    }

    public int getIchaobiaobz() {
        return ichaobiaobz;
    }

    public void setIchaobiaobz(int ichaobiaobz) {
        this.ichaobiaobz = ichaobiaobz;
    }

    public int getJiubiaocm() {
        return jiubiaocm;
    }

    public void setJiubiaocm(int jiubiaocm) {
        this.jiubiaocm = jiubiaocm;
    }

    public int getXinbiaodm() {
        return xinbiaodm;
    }

    public void setXinbiaodm(int xinbiaodm) {
        this.xinbiaodm = xinbiaodm;
    }

    public long getHuanbiaorq() {
        return huanbiaorq;
    }

    public void setHuanbiaorq(long huanbiaorq) {
        this.huanbiaorq = huanbiaorq;
    }

    public int getFangshibm() {
        return fangshibm;
    }

    public void setFangshibm(int fangshibm) {
        this.fangshibm = fangshibm;
    }

    public int getLianggaoldyybm() {
        return lianggaoldyybm;
    }

    public void setLianggaoldyybm(int lianggaoldyybm) {
        this.lianggaoldyybm = lianggaoldyybm;
    }

    public int getChaobiaoid() {
        return chaobiaoid;
    }

    public void setChaobiaoid(int chaobiaoid) {
        this.chaobiaoid = chaobiaoid;
    }

    public int getZhuangtailxs() {
        return zhuangtailxs;
    }

    public void setZhuangtailxs(int zhuangtailxs) {
        this.zhuangtailxs = zhuangtailxs;
    }

    public int getShuibiaobl() {
        return shuibiaobl;
    }

    public void setShuibiaobl(int shuibiaobl) {
        this.shuibiaobl = shuibiaobl;
    }

    public double getYongshuizkl() {
        return yongshuizkl;
    }

    public void setYongshuizkl(double yongshuizkl) {
        this.yongshuizkl = yongshuizkl;
    }

    public double getPaishuizkl() {
        return paishuizkl;
    }

    public void setPaishuizkl(double paishuizkl) {
        this.paishuizkl = paishuizkl;
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

    public long getXiazaisj() {
        return xiazaisj;
    }

    public void setXiazaisj(long xiazaisj) {
        this.xiazaisj = xiazaisj;
    }

    public int getLingyongslsm() {
        return lingyongslsm;
    }

    public void setLingyongslsm(int lingyongslsm) {
        this.lingyongslsm = lingyongslsm;
    }

    public int getLianggaosl() {
        return lianggaosl;
    }

    public void setLianggaosl(int lianggaosl) {
        this.lianggaosl = lianggaosl;
    }

    public int getLiangdisl() {
        return liangdisl;
    }

    public void setLiangdisl(int liangdisl) {
        this.liangdisl = liangdisl;
    }

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getSchaobiaobz() {
        return schaobiaobz;
    }

    public void setSchaobiaobz(String schaobiaobz) {
        this.schaobiaobz = schaobiaobz;
    }

    public int getCeneipx() {
        return ceneipx;
    }

    public void setCeneipx(int ceneipx) {
        this.ceneipx = ceneipx;
    }

    public int getXiazaics() {
        return xiazaics;
    }

    public void setXiazaics(int xiazaics) {
        this.xiazaics = xiazaics;
    }

    public long getZuihouycxzsj() {
        return zuihouycxzsj;
    }

    public void setZuihouycxzsj(long zuihouycxzsj) {
        this.zuihouycxzsj = zuihouycxzsj;
    }

    public long getZuihouycscsj() {
        return zuihouycscsj;
    }

    public void setZuihouycscsj(long zuihouycscsj) {
        this.zuihouycscsj = zuihouycscsj;
    }

    public int getShangchuanbz() {
        return shangchuanbz;
    }

    public void setShangchuanbz(int shangchuanbz) {
        this.shangchuanbz = shangchuanbz;
    }

    public int getShenhebz() {
        return shenhebz;
    }

    public void setShenhebz(int shenhebz) {
        this.shenhebz = shenhebz;
    }

    public int getKaizhangbz() {
        return kaizhangbz;
    }

    public void setKaizhangbz(int kaizhangbz) {
        this.kaizhangbz = kaizhangbz;
    }

    public int getDiaodongbz() {
        return diaodongbz;
    }

    public void setDiaodongbz(int diaodongbz) {
        this.diaodongbz = diaodongbz;
    }

    public int getWaifuyybh() {
        return waifuyybh;
    }

    public void setWaifuyybh(int waifuyybh) {
        this.waifuyybh = waifuyybh;
    }

    public String getJietits() {
        return jietits;
    }

    public void setJietits(String jietits) {
        this.jietits = jietits;
    }

    public String getYanciyy() {
        return yanciyy;
    }

    public void setYanciyy(String yanciyy) {
        this.yanciyy = yanciyy;
    }

    public int getLastReadingChild() {
        return lastReadingChild;
    }

    public void setLastReadingChild(int lastReadingChild) {
        this.lastReadingChild = lastReadingChild;
    }

    public int getReadingChild() {
        return readingChild;
    }

    public void setReadingChild(int readingChild) {
        this.readingChild = readingChild;
    }

    public boolean isAdjustingSequence() {
        return isAdjustingSequence;
    }

    public void setIsAdjustingSequence(boolean isAdjustingSequence) {
        this.isAdjustingSequence = isAdjustingSequence;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
