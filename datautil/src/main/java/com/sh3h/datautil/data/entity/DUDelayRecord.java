package com.sh3h.datautil.data.entity;


public class DUDelayRecord implements IDUEntity {
    public static final int CHAOBIAOBZ_WEICHAO = 0;     // 未抄
    public static final int CHAOBIAOBZ_YICHAO = 1;      // 已抄
    public static final int CHAOBIAOBZ_KAIZHANG = 2;    // 开账
    public static final int CHAOBIAOBZ_YANCHI = 3;      // 延迟
    public static final int CHAOBIAOBZ_WAIFUYC = 4;     // 外复延迟

    public static final int SHANGCHUANBZ_WEISHANGC = 0; // 未上传
    public static final int SHANGCHUANBZ_ZHENGZAISC = 1;  // 正在上传
    public static final int SHANGCHUANBZ_YISHANGC = 2;  // 已上传

    public static final int CHAOBIAOSJ = 0;  // 抄表数据
    public static final int SAMPLINGSJ = 1;  // 抽查数据
    public static final int NEWCHAOBIAOSJ = 2;  // 新卡入册数据

    private Long ID;
    private int I_RENWUBH;
    private int I_CHAOBIAOID;
    private String S_CID;
    private int I_CHAOJIANSL;
    private int I_SHANGCICM;
    private int I_CHAOHUICM;
    private int I_ZHUANGTAIBM;
    private String S_ZHUANGTAIMC;
    private int I_LIANGGAOLDBM;
    private long D_CHAOBIAORQ;
    private int I_CHAOBIAON;
    private int I_CHAOBIAOY;
    private String S_CHAOBIAOY;
    private int I_FANGSHIBM;
    private int I_CHAOBIAOZT;
    private long D_SHANGCICBRQ;
    private String S_ST;
    private String S_CH;
    private int I_CENEIXH;
    private int I_JIUBIAOCM;
    private int I_XINBIAODM;
    private long D_HUANBIAORQ;
    private long D_HUANBIAOHTSJ;
    private long D_DENGJISJ;
    private int I_ZHUANGTAI;
    private String S_YANCHIYBH;
    private String S_HUITIANYBH;
    private long D_HUITIANSJ;
    private String S_CHULIQK;
    private String S_CAOZUOR;
    private long D_CAOZUOSJ;
    private int I_HUANBIAOFS;
    private String S_CHAOBIAOBZ;
    private String S_SHUIBIAOTXM;
    private String S_YANCHIYY;
    private int I_ZHENSHICM;
    private int I_LINYONGSLSM;
    private String S_X;
    private String S_Y;
    private int I_YANCHILX;
    private String S_YANCHIBH;
    private int I_CHAOBIAOBZ;
    private int I_ShangChuanBZ;
    private int I_KaiZhangBZ;
    private String S_JH;
    private int I_LIANGGAOSL;
    private int I_LIANGDISL;
    private int I_PINGJUNL1;
    private int I_SHANGCISL;
    private int N_JE;
    private String S_JIETITS;
    private String S_ShangCiZTBM;
    private int I_SHANGGEDBZQTS;
    private long D_SHANGSHANGGYCBRQ;

    public DUDelayRecord() {
    }

    public DUDelayRecord(Long ID, int I_RENWUBH, int I_CHAOBIAOID, String S_CID,
                      int I_CHAOJIANSL, int I_SHANGCICM, int I_CHAOHUICM,
                      int I_ZHUANGTAIBM, String S_ZHUANGTAIMC, int I_LIANGGAOLDBM,
                      long D_CHAOBIAORQ, int I_CHAOBIAON, int I_CHAOBIAOY,
                      String S_CHAOBIAOY, int I_FANGSHIBM, int I_CHAOBIAOZT,
                      long D_SHANGCICBRQ, String S_ST, String S_CH, int I_CENEIXH,
                      int I_JIUBIAOCM, int I_XINBIAODM, long D_HUANBIAORQ,
                      long D_HUANBIAOHTSJ, long D_DENGJISJ, int I_ZHUANGTAI,
                      String S_YANCHIYBH, String S_HUITIANYBH, long D_HUITIANSJ,
                      String S_CHULIQK, String S_CAOZUOR, long D_CAOZUOSJ, int I_HUANBIAOFS,
                      String S_CHAOBIAOBZ, String S_SHUIBIAOTXM, String S_YANCHIYY,
                      int I_ZHENSHICM, int I_LINYONGSLSM, String S_X, String S_Y,
                      int I_YANCHILX, String S_YANCHIBH, int I_CHAOBIAOBZ,
                      int I_ShangChuanBZ, int I_KaiZhangBZ, String S_JH,
                      int I_LIANGGAOSL, int I_LIANGDISL, int I_PINGJUNL1,
                      int I_SHANGCISL, int N_JE, String S_JIETITS, String S_ShangCiZTBM,
                      int I_SHANGGEDBZQTS, long D_SHANGSHANGGYCBRQ) {
        this.ID = ID;
        this.I_RENWUBH = I_RENWUBH;
        this.I_CHAOBIAOID = I_CHAOBIAOID;
        this.S_CID = S_CID;
        this.I_CHAOJIANSL = I_CHAOJIANSL;
        this.I_SHANGCICM = I_SHANGCICM;
        this.I_CHAOHUICM = I_CHAOHUICM;
        this.I_ZHUANGTAIBM = I_ZHUANGTAIBM;
        this.S_ZHUANGTAIMC = S_ZHUANGTAIMC;
        this.I_LIANGGAOLDBM = I_LIANGGAOLDBM;
        this.D_CHAOBIAORQ = D_CHAOBIAORQ;
        this.I_CHAOBIAON = I_CHAOBIAON;
        this.I_CHAOBIAOY = I_CHAOBIAOY;
        this.S_CHAOBIAOY = S_CHAOBIAOY;
        this.I_FANGSHIBM = I_FANGSHIBM;
        this.I_CHAOBIAOZT = I_CHAOBIAOZT;
        this.D_SHANGCICBRQ = D_SHANGCICBRQ;
        this.S_ST = S_ST;
        this.S_CH = S_CH;
        this.I_CENEIXH = I_CENEIXH;
        this.I_JIUBIAOCM = I_JIUBIAOCM;
        this.I_XINBIAODM = I_XINBIAODM;
        this.D_HUANBIAORQ = D_HUANBIAORQ;
        this.D_HUANBIAOHTSJ = D_HUANBIAOHTSJ;
        this.D_DENGJISJ = D_DENGJISJ;
        this.I_ZHUANGTAI = I_ZHUANGTAI;
        this.S_YANCHIYBH = S_YANCHIYBH;
        this.S_HUITIANYBH = S_HUITIANYBH;
        this.D_HUITIANSJ = D_HUITIANSJ;
        this.S_CHULIQK = S_CHULIQK;
        this.S_CAOZUOR = S_CAOZUOR;
        this.D_CAOZUOSJ = D_CAOZUOSJ;
        this.I_HUANBIAOFS = I_HUANBIAOFS;
        this.S_CHAOBIAOBZ = S_CHAOBIAOBZ;
        this.S_SHUIBIAOTXM = S_SHUIBIAOTXM;
        this.S_YANCHIYY = S_YANCHIYY;
        this.I_ZHENSHICM = I_ZHENSHICM;
        this.I_LINYONGSLSM = I_LINYONGSLSM;
        this.S_X = S_X;
        this.S_Y = S_Y;
        this.I_YANCHILX = I_YANCHILX;
        this.S_YANCHIBH = S_YANCHIBH;
        this.I_CHAOBIAOBZ = I_CHAOBIAOBZ;
        this.I_ShangChuanBZ = I_ShangChuanBZ;
        this.I_KaiZhangBZ = I_KaiZhangBZ;
        this.S_JH = S_JH;
        this.I_LIANGGAOSL = I_LIANGGAOSL;
        this.I_LIANGDISL = I_LIANGDISL;
        this.I_PINGJUNL1 = I_PINGJUNL1;
        this.I_SHANGCISL = I_SHANGCISL;
        this.N_JE = N_JE;
        this.S_JIETITS = S_JIETITS;
        this.S_ShangCiZTBM = S_ShangCiZTBM;
        this.I_SHANGGEDBZQTS = I_SHANGGEDBZQTS;
        this.D_SHANGSHANGGYCBRQ = D_SHANGSHANGGYCBRQ;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Integer getI_RENWUBH() {
        return I_RENWUBH;
    }

    public void setI_RENWUBH(Integer i_RENWUBH) {
        I_RENWUBH = i_RENWUBH;
    }

    public Integer getI_CHAOBIAOID() {
        return I_CHAOBIAOID;
    }

    public void setI_CHAOBIAOID(Integer i_CHAOBIAOID) {
        I_CHAOBIAOID = i_CHAOBIAOID;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public int getI_CHAOJIANSL() {
        return I_CHAOJIANSL;
    }

    public void setI_CHAOJIANSL(int i_CHAOJIANSL) {
        I_CHAOJIANSL = i_CHAOJIANSL;
    }

    public Integer getI_SHANGCICM() {
        return I_SHANGCICM;
    }

    public void setI_SHANGCICM(Integer i_SHANGCICM) {
        I_SHANGCICM = i_SHANGCICM;
    }

    public Integer getI_CHAOHUICM() {
        return I_CHAOHUICM;
    }

    public void setI_CHAOHUICM(Integer i_CHAOHUICM) {
        I_CHAOHUICM = i_CHAOHUICM;
    }

    public Integer getI_ZHUANGTAIBM() {
        return I_ZHUANGTAIBM;
    }

    public void setI_ZHUANGTAIBM(Integer i_ZHUANGTAIBM) {
        I_ZHUANGTAIBM = i_ZHUANGTAIBM;
    }

    public String getS_ZHUANGTAIMC() {
        return S_ZHUANGTAIMC;
    }

    public void setS_ZHUANGTAIMC(String s_ZHUANGTAIMC) {
        S_ZHUANGTAIMC = s_ZHUANGTAIMC;
    }

    public Integer getI_LIANGGAOLDBM() {
        return I_LIANGGAOLDBM;
    }

    public void setI_LIANGGAOLDBM(Integer i_LIANGGAOLDBM) {
        I_LIANGGAOLDBM = i_LIANGGAOLDBM;
    }

    public long getD_CHAOBIAORQ() {
        return D_CHAOBIAORQ;
    }

    public void setD_CHAOBIAORQ(long d_CHAOBIAORQ) {
        D_CHAOBIAORQ = d_CHAOBIAORQ;
    }

    public Integer getI_CHAOBIAON() {
        return I_CHAOBIAON;
    }

    public void setI_CHAOBIAON(Integer i_CHAOBIAON) {
        I_CHAOBIAON = i_CHAOBIAON;
    }

    public Integer getI_CHAOBIAOY() {
        return I_CHAOBIAOY;
    }

    public void setI_CHAOBIAOY(Integer i_CHAOBIAOY) {
        I_CHAOBIAOY = i_CHAOBIAOY;
    }

    public String getS_CHAOBIAOY() {
        return S_CHAOBIAOY;
    }

    public void setS_CHAOBIAOY(String s_CHAOBIAOY) {
        S_CHAOBIAOY = s_CHAOBIAOY;
    }

    public Integer getI_FANGSHIBM() {
        return I_FANGSHIBM;
    }

    public void setI_FANGSHIBM(Integer i_FANGSHIBM) {
        I_FANGSHIBM = i_FANGSHIBM;
    }

    public Integer getI_CHAOBIAOZT() {
        return I_CHAOBIAOZT;
    }

    public void setI_CHAOBIAOZT(Integer i_CHAOBIAOZT) {
        I_CHAOBIAOZT = i_CHAOBIAOZT;
    }

    public long getD_SHANGCICBRQ() {
        return D_SHANGCICBRQ;
    }

    public void setD_SHANGCICBRQ(long d_SHANGCICBRQ) {
        D_SHANGCICBRQ = d_SHANGCICBRQ;
    }

    public String getS_ST() {
        return S_ST;
    }

    public void setS_ST(String s_ST) {
        S_ST = s_ST;
    }

    public String getS_CH() {
        return S_CH;
    }

    public void setS_CH(String s_CH) {
        S_CH = s_CH;
    }

    public Integer getI_CENEIXH() {
        return I_CENEIXH;
    }

    public void setI_CENEIXH(Integer i_CENEIXH) {
        I_CENEIXH = i_CENEIXH;
    }

    public Integer getI_JIUBIAOCM() {
        return I_JIUBIAOCM;
    }

    public void setI_JIUBIAOCM(Integer i_JIUBIAOCM) {
        I_JIUBIAOCM = i_JIUBIAOCM;
    }

    public Integer getI_XINBIAODM() {
        return I_XINBIAODM;
    }

    public void setI_XINBIAODM(Integer i_XINBIAODM) {
        I_XINBIAODM = i_XINBIAODM;
    }

    public long getD_HUANBIAORQ() {
        return D_HUANBIAORQ;
    }

    public void setD_HUANBIAORQ(long d_HUANBIAORQ) {
        D_HUANBIAORQ = d_HUANBIAORQ;
    }

    public long getD_HUANBIAOHTSJ() {
        return D_HUANBIAOHTSJ;
    }

    public void setD_HUANBIAOHTSJ(long d_HUANBIAOHTSJ) {
        D_HUANBIAOHTSJ = d_HUANBIAOHTSJ;
    }

    public long getD_DENGJISJ() {
        return D_DENGJISJ;
    }

    public void setD_DENGJISJ(long d_DENGJISJ) {
        D_DENGJISJ = d_DENGJISJ;
    }

    public Integer getI_ZHUANGTAI() {
        return I_ZHUANGTAI;
    }

    public void setI_ZHUANGTAI(Integer i_ZHUANGTAI) {
        I_ZHUANGTAI = i_ZHUANGTAI;
    }

    public String getS_YANCHIYBH() {
        return S_YANCHIYBH;
    }

    public void setS_YANCHIYBH(String s_YANCHIYBH) {
        S_YANCHIYBH = s_YANCHIYBH;
    }

    public String getS_HUITIANYBH() {
        return S_HUITIANYBH;
    }

    public void setS_HUITIANYBH(String s_HUITIANYBH) {
        S_HUITIANYBH = s_HUITIANYBH;
    }

    public long getD_HUITIANSJ() {
        return D_HUITIANSJ;
    }

    public void setD_HUITIANSJ(long d_HUITIANSJ) {
        D_HUITIANSJ = d_HUITIANSJ;
    }

    public String getS_CHULIQK() {
        return S_CHULIQK;
    }

    public void setS_CHULIQK(String s_CHULIQK) {
        S_CHULIQK = s_CHULIQK;
    }

    public String getS_CAOZUOR() {
        return S_CAOZUOR;
    }

    public void setS_CAOZUOR(String s_CAOZUOR) {
        S_CAOZUOR = s_CAOZUOR;
    }

    public long getD_CAOZUOSJ() {
        return D_CAOZUOSJ;
    }

    public void setD_CAOZUOSJ(long d_CAOZUOSJ) {
        D_CAOZUOSJ = d_CAOZUOSJ;
    }

    public Integer getI_HUANBIAOFS() {
        return I_HUANBIAOFS;
    }

    public void setI_HUANBIAOFS(Integer i_HUANBIAOFS) {
        I_HUANBIAOFS = i_HUANBIAOFS;
    }

    public String getS_CHAOBIAOBZ() {
        return S_CHAOBIAOBZ;
    }

    public void setS_CHAOBIAOBZ(String s_CHAOBIAOBZ) {
        S_CHAOBIAOBZ = s_CHAOBIAOBZ;
    }

    public String getS_SHUIBIAOTXM() {
        return S_SHUIBIAOTXM;
    }

    public void setS_SHUIBIAOTXM(String s_SHUIBIAOTXM) {
        S_SHUIBIAOTXM = s_SHUIBIAOTXM;
    }

    public String getS_YANCHIYY() {
        return S_YANCHIYY;
    }

    public void setS_YANCHIYY(String s_YANCHIYY) {
        S_YANCHIYY = s_YANCHIYY;
    }

    public Integer getI_ZHENSHICM() {
        return I_ZHENSHICM;
    }

    public void setI_ZHENSHICM(Integer i_ZHENSHICM) {
        I_ZHENSHICM = i_ZHENSHICM;
    }

    public Integer getI_LINYONGSLSM() {
        return I_LINYONGSLSM;
    }

    public void setI_LINYONGSLSM(Integer i_LINYONGSLSM) {
        I_LINYONGSLSM = i_LINYONGSLSM;
    }

    public String getS_X() {
        return S_X;
    }

    public void setS_X(String s_X) {
        S_X = s_X;
    }

    public String getS_Y() {
        return S_Y;
    }

    public void setS_Y(String s_Y) {
        S_Y = s_Y;
    }

    public Integer getI_YANCHILX() {
        return I_YANCHILX;
    }

    public void setI_YANCHILX(Integer i_YANCHILX) {
        I_YANCHILX = i_YANCHILX;
    }

    public String getS_YANCHIBH() {
        return S_YANCHIBH;
    }

    public void setS_YANCHIBH(String s_YANCHIBH) {
        S_YANCHIBH = s_YANCHIBH;
    }

    public Integer getI_CHAOBIAOBZ() {
        return I_CHAOBIAOBZ;
    }

    public void setI_CHAOBIAOBZ(Integer i_CHAOBIAOBZ) {
        I_CHAOBIAOBZ = i_CHAOBIAOBZ;
    }

    public Integer getI_ShangChuanBZ() {
        return I_ShangChuanBZ;
    }

    public void setI_ShangChuanBZ(Integer i_ShangChuanBZ) {
        I_ShangChuanBZ = i_ShangChuanBZ;
    }

    public Integer getI_KaiZhangBZ() {
        return I_KaiZhangBZ;
    }

    public void setI_KaiZhangBZ(Integer i_KaiZhangBZ) {
        I_KaiZhangBZ = i_KaiZhangBZ;
    }

    public String getS_JH() {
        return S_JH;
    }

    public void setS_JH(String s_JH) {
        S_JH = s_JH;
    }

    public Integer getI_LIANGGAOSL() {
        return I_LIANGGAOSL;
    }

    public void setI_LIANGGAOSL(Integer i_LIANGGAOSL) {
        I_LIANGGAOSL = i_LIANGGAOSL;
    }

    public Integer getI_LIANGDISL() {
        return I_LIANGDISL;
    }

    public void setI_LIANGDISL(Integer i_LIANGDISL) {
        I_LIANGDISL = i_LIANGDISL;
    }

    public Integer getI_PINGJUNL1() {
        return I_PINGJUNL1;
    }

    public void setI_PINGJUNL1(Integer i_PINGJUNL1) {
        I_PINGJUNL1 = i_PINGJUNL1;
    }

    public Integer getI_SHANGCISL() {
        return I_SHANGCISL;
    }

    public void setI_SHANGCISL(Integer i_SHANGCISL) {
        I_SHANGCISL = i_SHANGCISL;
    }

    public Integer getN_JE() {
        return N_JE;
    }

    public void setN_JE(Integer n_JE) {
        N_JE = n_JE;
    }

    public String getS_JIETITS() {
        return S_JIETITS;
    }

    public void setS_JIETITS(String s_JIETITS) {
        S_JIETITS = s_JIETITS;
    }

    public String getS_ShangCiZTBM() {
        return S_ShangCiZTBM;
    }

    public void setS_ShangCiZTBM(String s_ShangCiZTBM) {
        S_ShangCiZTBM = s_ShangCiZTBM;
    }

    public int getI_SHANGGEDBZQTS() {
        return I_SHANGGEDBZQTS;
    }

    public void setI_SHANGGEDBZQTS(int i_SHANGGEDBZQTS) {
        I_SHANGGEDBZQTS = i_SHANGGEDBZQTS;
    }

    public long getD_SHANGSHANGGYCBRQ() {
        return D_SHANGSHANGGYCBRQ;
    }

    public void setD_SHANGSHANGGYCBRQ(long d_SHANGSHANGGYCBRQ) {
        D_SHANGSHANGGYCBRQ = d_SHANGSHANGGYCBRQ;
    }
}
