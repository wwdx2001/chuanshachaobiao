package com.sh3h.datautil.data.entity;


import java.io.Serializable;

public class DUCard implements Serializable {
    public static final int SHUIBIAOZT_NORMAL = 0;  // 正常
    public static final int SHUIBIAOZT_NEW = 1;     // 新装
    public static final int SHUIBIAOZT_SWITCH = 2; // 换表
    public static final int SHUIBIAOZT_REMOVE = 3; // 拆表

    //江门表卡状态
    public static final int JM_YONGHUZT_CANCLE = -2;  // 注销
    public static final int JM_YONGHUZT_REMOVE = -1;  // 拆表
    public static final int JM_YONGHUZT_NORMAL = 0; // 正常
    public static final int JM_YONGHUZT_STOP = 1; // 停用


//    public static final int YONGHUZT_NORMAL = 0; // 正常
//    public static final int YONGHUZT_TINGYONG = -1; // 停用(报停)
//    public static final int YONGHUZT_CHAIBIAO = -2; // 拆表
//    public static final int YONGHUZT_ZHUXIAO = -3; // 注销
//    public static final int YONGHUZT_TUIKA = -4; // 退卡
//    public static final int YONGHUZT_DAITINGY = -10; // 待停用(待报停)
//    public static final int YONGHUZT_QIANFEITS = -20; // 欠费停水
//    public static final int YONGHUZT_DAIXIAOH = -30; // 待销户

    public static final int SHANGCHUANBZ_TEMPORARY = -1; // 临时册本
    public static final int SHANGCHUANBZ_WEISHANGC = 0; // 未上传
    public static final int SHANGCHUANBZ_ZHENGZAISC = 1;  // 正在上传
    public static final int SHANGCHUANBZ_YISHANGC = 2;  // 已上传

    private int id;
    private String ch;
    private int ceneixh;
    private String cid;
    private String kehubh;
    private String kehumc;
    private String st;
    private String dizhi;
    private String lianxir;
    private String lianxisj;
    private String lianxidh;
    private String shoufeifs;
    private String yinhangmc;
    private String jianhao;
    private String jianhaomc;
    private int yonghuzt;
    private long lihu;
    private String biaowei;
    private String shuibiaogyh;
    private String shuibiaotxm;
    private String koujingmc;
    private int liangcheng;
    private String biaoxing;
    private String shuibiaocj;
    private int shuibiaofli;
    private String shuibiaoflmc;
    private int shuibiaobl;
    private String kaizhangfl;
    private int gongnengfl;
    private int shifoujhys;
    private int shifoushouljf;
    private double lajifeixs;
    private int shifoushouwyj;
    private int shifoudejj;
    private int dingesl;
    private String zongbiaobh;
    private long zhuangbiaorq;
    private long huanbiaorq;
    private int xinbiaodm;
    private int jiubiaocm;
    private String x1;
    private String y1;
    private String x;
    private String y;
    private int fentanfs;
    private int fentanl;
    private double yucunkye;
    private int qianfeizbs;
    private double qianfeizje;
    private String beizhu;
    private int shuibiaozt;
    private double renkous;
    private int dibaoyhsl;
    private int gongceyhsl;
    private double yongshuizkl;
    private double paishuizkl;
    private double zhekoul1;
    private double zhekoul2;
    private double zhekoul3;
    private int ercigs;
    private int dianzizd;
    private String xingzhengq;
    private String biaokazt;
    private String sheshuiid;
    private double jiage;
    private String shuibiaolxbh;
    private double zizhuangbkzxs;
    private String shuibiaozl;
    private String shuibiaofls;
    private String yuanchuanid;
    private String zhongduanh;
    private String yuanchuancj;
    private String duorenkfa;
    private int shifoujt;
    private long duorenkjz;
    private int gongshuihtnx;
    private String fangdongdh;
    private String fangkedh;
    private int nianleij;
    private int huanbiao;
    private int qianfei;
    private int xiugaibz;
    private int shangchuanbz;
    private int downloadType;
    private int groupId;
    private String kuaihao;
    private String customerType;
    private String isVIP;
    private String vIPCode;
    private long dateOfContract;
    private long contractage;
    private String postType;
    private String creditrating;
    private String waterMode;
    private long strongdate;

    private int payMethod;
    private int caliberValue;
    private int monthTotal;
    private int quarterTotal;
    private int yearTotal;
    private String extend;

    public DUCard() {

    }

    public long getI_DingESL() {
        return I_DingESL;
    }

    public void setI_DingESL(long i_DingESL) {
        I_DingESL = i_DingESL;
    }

    private long I_DingESL;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getIsVIP() {
        return isVIP;
    }

    public void setIsVIP(String isVIP) {
        this.isVIP = isVIP;
    }

    public String getvIPCode() {
        return vIPCode;
    }

    public void setvIPCode(String vIPCode) {
        this.vIPCode = vIPCode;
    }

    public long getDateOfContract() {
        return dateOfContract;
    }

    public void setDateOfContract(long dateOfContract) {
        this.dateOfContract = dateOfContract;
    }

    public long getContractage() {
        return contractage;
    }

    public void setContractage(long contractage) {
        this.contractage = contractage;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCreditrating() {
        return creditrating;
    }

    public void setCreditrating(String creditrating) {
        this.creditrating = creditrating;
    }

    public String getWaterMode() {
        return waterMode;
    }

    public void setWaterMode(String waterMode) {
        this.waterMode = waterMode;
    }

    public long getStrongdate() {
        return strongdate;
    }

    public void setStrongdate(long strongdate) {
        this.strongdate = strongdate;
    }

    public DUCard(int id,
                  String ch,
                  int ceneixh,
                  String cid,
                  String kehubh,
                  String kehumc,
                  String st,
                  String dizhi,
                  String lianxir,
                  String lianxisj,
                  String lianxidh,
                  String shoufeifs,
                  String yinhangmc,
                  String jianhao,
                  String jianhaomc,
                  int yonghuzt,
                  long lihu,
                  String biaowei,
                  String shuibiaogyh,
                  String shuibiaotxm,
                  String koujingmc,
                  int liangcheng,
                  String biaoxing,
                  String shuibiaocj,
                  int shuibiaofli,
                  String shuibiaoflmc,
                  int shuibiaobl,
                  String kaizhangfl,
                  int gongnengfl,
                  int shifoujhys,
                  int shifoushouljf,
                  double lajifeixs,
                  int shifoushouwyj,
                  int shifoudejj,
                  int dingesl,
                  String zongbiaobh,
                  long zhuangbiaorq,
                  long huanbiaorq,
                  int xinbiaodm,
                  int jiubiaocm,
                  String x1,
                  String y1,
                  String x,
                  String y,
                  int fentanfs,
                  int fentanl,
                  double yucunkye,
                  int qianfeizbs,
                  double qianfeizje,
                  String beizhu,
                  int shuibiaozt,
                  double renkous,
                  int dibaoyhsl,
                  int gongceyhsl,
                  double yongshuizkl,
                  double paishuizkl,
                  double zhekoul1,
                  double zhekoul2,
                  double zhekoul3,
                  int ercigs,
                  int dianzizd,
                  String xingzhengq,
                  String biaokazt,
                  String sheshuiid,
                  double jiage,
                  String shuibiaolxbh,
                  double zizhuangbkzxs,
                  String shuibiaozl,
                  String shuibiaofls,
                  String yuanchuanid,
                  String zhongduanh,
                  String yuanchuancj,
                  String duorenkfa,
                  int shifoujt,
                  long duorenkjz,
                  int gongshuihtnx,
                  String fangdongdh,
                  String fangkedh,
                  int nianleij,
                  int huanbiao,
                  int qianfei,
                  int xiugaibz,
                  int shangchuanbz,
                  int downloadType,
                  int groupId,
                  String kuaihao,
                  String customerType,
                  String isVIP,
                  String vIPCode,
                  long dateOfContract,
                  long contractage,
                  String postType,
                  String creditrating,
                  String waterMode,
                  long strongdate,
                  int payMethod,
                  int caliberValue,
                  int monthTotal,
                  int quarterTotal,
                  int yearTotal,
                  String extend) {
        this.id = id;
        this.ch = ch;
        this.ceneixh = ceneixh;
        this.cid = cid;
        this.kehubh = kehubh;
        this.kehumc = kehumc;
        this.st = st;
        this.dizhi = dizhi;
        this.lianxir = lianxir;
        this.lianxisj = lianxisj;
        this.lianxidh = lianxidh;
        this.shoufeifs = shoufeifs;
        this.yinhangmc = yinhangmc;
        this.jianhao = jianhao;
        this.jianhaomc = jianhaomc;
        this.yonghuzt = yonghuzt;
        this.lihu = lihu;
        this.biaowei = biaowei;
        this.shuibiaogyh = shuibiaogyh;
        this.shuibiaotxm = shuibiaotxm;
        this.koujingmc = koujingmc;
        this.liangcheng = liangcheng;
        this.biaoxing = biaoxing;
        this.shuibiaocj = shuibiaocj;
        this.shuibiaofli = shuibiaofli;
        this.shuibiaoflmc = shuibiaoflmc;
        this.shuibiaobl = shuibiaobl;
        this.kaizhangfl = kaizhangfl;
        this.gongnengfl = gongnengfl;
        this.shifoujhys = shifoujhys;
        this.shifoushouljf = shifoushouljf;
        this.lajifeixs = lajifeixs;
        this.shifoushouwyj = shifoushouwyj;
        this.shifoudejj = shifoudejj;
        this.dingesl = dingesl;
        this.zongbiaobh = zongbiaobh;
        this.zhuangbiaorq = zhuangbiaorq;
        this.huanbiaorq = huanbiaorq;
        this.xinbiaodm = xinbiaodm;
        this.jiubiaocm = jiubiaocm;
        this.x1 = x1;
        this.y1 = y1;
        this.x = x;
        this.y = y;
        this.fentanfs = fentanfs;
        this.fentanl = fentanl;
        this.yucunkye = yucunkye;
        this.qianfeizbs = qianfeizbs;
        this.qianfeizje = qianfeizje;
        this.beizhu = beizhu;
        this.shuibiaozt = shuibiaozt;
        this.renkous = renkous;
        this.dibaoyhsl = dibaoyhsl;
        this.gongceyhsl = gongceyhsl;
        this.yongshuizkl = yongshuizkl;
        this.paishuizkl = paishuizkl;
        this.zhekoul1 = zhekoul1;
        this.zhekoul2 = zhekoul2;
        this.zhekoul3 = zhekoul3;
        this.ercigs = ercigs;
        this.dianzizd = dianzizd;
        this.xingzhengq = xingzhengq;
        this.biaokazt = biaokazt;
        this.sheshuiid = sheshuiid;
        this.jiage = jiage;
        this.shuibiaolxbh = shuibiaolxbh;
        this.zizhuangbkzxs = zizhuangbkzxs;
        this.shuibiaozl = shuibiaozl;
        this.shuibiaofls = shuibiaofls;
        this.yuanchuanid = yuanchuanid;
        this.zhongduanh = zhongduanh;
        this.yuanchuancj = yuanchuancj;
        this.duorenkfa = duorenkfa;
        this.shifoujt = shifoujt;
        this.duorenkjz = duorenkjz;
        this.gongshuihtnx = gongshuihtnx;
        this.fangdongdh = fangdongdh;
        this.fangkedh = fangkedh;
        this.nianleij = nianleij;
        this.huanbiao = huanbiao;
        this.qianfei = qianfei;
        this.xiugaibz = xiugaibz;
        this.shangchuanbz = shangchuanbz;
        this.downloadType = downloadType;
        this.groupId = groupId;
        this.kuaihao = kuaihao;
        this.customerType = customerType;
        this.isVIP = isVIP;
        this.vIPCode = vIPCode;
        this.dateOfContract = dateOfContract;
        this.contractage = contractage;
        this.postType = postType;

        this.creditrating = creditrating;
        this.waterMode = waterMode;
        this.strongdate = strongdate;

        this.payMethod = payMethod;
        this.caliberValue = caliberValue;
        this.monthTotal = monthTotal;
        this.quarterTotal = quarterTotal;
        this.yearTotal = yearTotal;
        this.extend = extend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getKehubh() {
        return kehubh;
    }

    public void setKehubh(String kehubh) {
        this.kehubh = kehubh;
    }

    public String getKehumc() {
        return kehumc;
    }

    public void setKehumc(String kehumc) {
        this.kehumc = kehumc;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }

    public String getLianxir() {
        return lianxir;
    }

    public void setLianxir(String lianxir) {
        this.lianxir = lianxir;
    }

    public String getLianxisj() {
        return lianxisj;
    }

    public void setLianxisj(String lianxisj) {
        this.lianxisj = lianxisj;
    }

    public String getLianxidh() {
        return lianxidh;
    }

    public void setLianxidh(String lianxidh) {
        this.lianxidh = lianxidh;
    }

    public String getShoufeifs() {
        return shoufeifs;
    }

    public void setShoufeifs(String shoufeifs) {
        this.shoufeifs = shoufeifs;
    }

    public String getYinhangmc() {
        return yinhangmc;
    }

    public void setYinhangmc(String yinhangmc) {
        this.yinhangmc = yinhangmc;
    }

    public String getJianhao() {
        return jianhao;
    }

    public void setJianhao(String jianhao) {
        this.jianhao = jianhao;
    }

    public String getJianhaomc() {
        return jianhaomc;
    }

    public void setJianhaomc(String jianhaomc) {
        this.jianhaomc = jianhaomc;
    }

    public int getYonghuzt() {
        return yonghuzt;
    }

    public void setYonghuzt(int yonghuzt) {
        this.yonghuzt = yonghuzt;
    }

    public long getLihu() {
        return lihu;
    }

    public void setLihu(long lihu) {
        this.lihu = lihu;
    }

    public String getBiaowei() {
        return biaowei;
    }

    public void setBiaowei(String biaowei) {
        this.biaowei = biaowei;
    }

    public String getShuibiaogyh() {
        return shuibiaogyh;
    }

    public void setShuibiaogyh(String shuibiaogyh) {
        this.shuibiaogyh = shuibiaogyh;
    }

    public String getShuibiaotxm() {
        return shuibiaotxm;
    }

    public void setShuibiaotxm(String shuibiaotxm) {
        this.shuibiaotxm = shuibiaotxm;
    }

    public String getKoujingmc() {
        return koujingmc;
    }

    public void setKoujingmc(String koujingmc) {
        this.koujingmc = koujingmc;
    }

    public int getLiangcheng() {
        return liangcheng;
    }

    public void setLiangcheng(int liangcheng) {
        this.liangcheng = liangcheng;
    }

    public String getBiaoxing() {
        return biaoxing;
    }

    public void setBiaoxing(String biaoxing) {
        this.biaoxing = biaoxing;
    }

    public String getShuibiaocj() {
        return shuibiaocj;
    }

    public void setShuibiaocj(String shuibiaocj) {
        this.shuibiaocj = shuibiaocj;
    }

    public int getShuibiaofli() {
        return shuibiaofli;
    }

    public void setShuibiaofli(int shuibiaofli) {
        this.shuibiaofli = shuibiaofli;
    }

    public String getShuibiaoflmc() {
        return shuibiaoflmc;
    }

    public void setShuibiaoflmc(String shuibiaoflmc) {
        this.shuibiaoflmc = shuibiaoflmc;
    }

    public int getShuibiaobl() {
        return shuibiaobl;
    }

    public void setShuibiaobl(int shuibiaobl) {
        this.shuibiaobl = shuibiaobl;
    }

    public String getKaizhangfl() {
        return kaizhangfl;
    }

    public void setKaizhangfl(String kaizhangfl) {
        this.kaizhangfl = kaizhangfl;
    }

    public int getGongnengfl() {
        return gongnengfl;
    }

    public void setGongnengfl(int gongnengfl) {
        this.gongnengfl = gongnengfl;
    }

    public int getShifoujhys() {
        return shifoujhys;
    }

    public void setShifoujhys(int shifoujhys) {
        this.shifoujhys = shifoujhys;
    }

    public int getShifoushouljf() {
        return shifoushouljf;
    }

    public void setShifoushouljf(int shifoushouljf) {
        this.shifoushouljf = shifoushouljf;
    }

    public double getLajifeixs() {
        return lajifeixs;
    }

    public void setLajifeixs(double lajifeixs) {
        this.lajifeixs = lajifeixs;
    }

    public int getShifoushouwyj() {
        return shifoushouwyj;
    }

    public void setShifoushouwyj(int shifoushouwyj) {
        this.shifoushouwyj = shifoushouwyj;
    }

    public int getShifoudejj() {
        return shifoudejj;
    }

    public void setShifoudejj(int shifoudejj) {
        this.shifoudejj = shifoudejj;
    }

    public int getDingesl() {
        return dingesl;
    }

    public void setDingesl(int dingesl) {
        this.dingesl = dingesl;
    }

    public String getZongbiaobh() {
        return zongbiaobh;
    }

    public void setZongbiaobh(String zongbiaobh) {
        this.zongbiaobh = zongbiaobh;
    }

    public long getZhuangbiaorq() {
        return zhuangbiaorq;
    }

    public void setZhuangbiaorq(long zhuangbiaorq) {
        this.zhuangbiaorq = zhuangbiaorq;
    }

    public long getHuanbiaorq() {
        return huanbiaorq;
    }

    public void setHuanbiaorq(long huanbiaorq) {
        this.huanbiaorq = huanbiaorq;
    }

    public int getXinbiaodm() {
        return xinbiaodm;
    }

    public void setXinbiaodm(int xinbiaodm) {
        this.xinbiaodm = xinbiaodm;
    }

    public int getJiubiaocm() {
        return jiubiaocm;
    }

    public void setJiubiaocm(int jiubiaocm) {
        this.jiubiaocm = jiubiaocm;
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

    public int getFentanfs() {
        return fentanfs;
    }

    public void setFentanfs(int fentanfs) {
        this.fentanfs = fentanfs;
    }

    public int getFentanl() {
        return fentanl;
    }

    public void setFentanl(int fentanl) {
        this.fentanl = fentanl;
    }

    public double getYucunkye() {
        return yucunkye;
    }

    public void setYucunkye(double yucunkye) {
        this.yucunkye = yucunkye;
    }

    public int getQianfeizbs() {
        return qianfeizbs;
    }

    public void setQianfeizbs(int qianfeizbs) {
        this.qianfeizbs = qianfeizbs;
    }

    public double getQianfeizje() {
        return qianfeizje;
    }

    public void setQianfeizje(double qianfeizje) {
        this.qianfeizje = qianfeizje;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public int getShuibiaozt() {
        return shuibiaozt;
    }

    public void setShuibiaozt(int shuibiaozt) {
        this.shuibiaozt = shuibiaozt;
    }

    public double getRenkous() {
        return renkous;
    }

    public void setRenkous(double renkous) {
        this.renkous = renkous;
    }

    public int getDibaoyhsl() {
        return dibaoyhsl;
    }

    public void setDibaoyhsl(int dibaoyhsl) {
        this.dibaoyhsl = dibaoyhsl;
    }

    public int getGongceyhsl() {
        return gongceyhsl;
    }

    public void setGongceyhsl(int gongceyhsl) {
        this.gongceyhsl = gongceyhsl;
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

    public double getZhekoul1() {
        return zhekoul1;
    }

    public void setZhekoul1(double zhekoul1) {
        this.zhekoul1 = zhekoul1;
    }

    public double getZhekoul2() {
        return zhekoul2;
    }

    public void setZhekoul2(double zhekoul2) {
        this.zhekoul2 = zhekoul2;
    }

    public double getZhekoul3() {
        return zhekoul3;
    }

    public void setZhekoul3(double zhekoul3) {
        this.zhekoul3 = zhekoul3;
    }

    public int getErcigs() {
        return ercigs;
    }

    public void setErcigs(int ercigs) {
        this.ercigs = ercigs;
    }

    public int getDianzizd() {
        return dianzizd;
    }

    public void setDianzizd(int dianzizd) {
        this.dianzizd = dianzizd;
    }

    public String getXingzhengq() {
        return xingzhengq;
    }

    public void setXingzhengq(String xingzhengq) {
        this.xingzhengq = xingzhengq;
    }

    public String getBiaokazt() {
        return biaokazt;
    }

    public void setBiaokazt(String biaokazt) {
        this.biaokazt = biaokazt;
    }

    public String getSheshuiid() {
        return sheshuiid;
    }

    public void setSheshuiid(String sheshuiid) {
        this.sheshuiid = sheshuiid;
    }

    public double getJiage() {
        return jiage;
    }

    public void setJiage(double jiage) {
        this.jiage = jiage;
    }

    public String getShuibiaolxbh() {
        return shuibiaolxbh;
    }

    public void setShuibiaolxbh(String shuibiaolxbh) {
        this.shuibiaolxbh = shuibiaolxbh;
    }

    public double getZizhuangbkzxs() {
        return zizhuangbkzxs;
    }

    public void setZizhuangbkzxs(double zizhuangbkzxs) {
        this.zizhuangbkzxs = zizhuangbkzxs;
    }

    public String getShuibiaozl() {
        return shuibiaozl;
    }

    public void setShuibiaozl(String shuibiaozl) {
        this.shuibiaozl = shuibiaozl;
    }

    public String getShuibiaofls() {
        return shuibiaofls;
    }

    public void setShuibiaofls(String shuibiaofls) {
        this.shuibiaofls = shuibiaofls;
    }

    public String getYuanchuanid() {
        return yuanchuanid;
    }

    public void setYuanchuanid(String yuanchuanid) {
        this.yuanchuanid = yuanchuanid;
    }

    public String getZhongduanh() {
        return zhongduanh;
    }

    public void setZhongduanh(String zhongduanh) {
        this.zhongduanh = zhongduanh;
    }

    public String getYuanchuancj() {
        return yuanchuancj;
    }

    public void setYuanchuancj(String yuanchuancj) {
        this.yuanchuancj = yuanchuancj;
    }

    public String getDuorenkfa() {
        return duorenkfa;
    }

    public void setDuorenkfa(String duorenkfa) {
        this.duorenkfa = duorenkfa;
    }

    public int getShifoujt() {
        return shifoujt;
    }

    public void setShifoujt(int shifoujt) {
        this.shifoujt = shifoujt;
    }

    public long getDuorenkjz() {
        return duorenkjz;
    }

    public void setDuorenkjz(long duorenkjz) {
        this.duorenkjz = duorenkjz;
    }

    public int getGongshuihtnx() {
        return gongshuihtnx;
    }

    public void setGongshuihtnx(int gongshuihtnx) {
        this.gongshuihtnx = gongshuihtnx;
    }

    public String getFangdongdh() {
        return fangdongdh;
    }

    public void setFangdongdh(String fangdongdh) {
        this.fangdongdh = fangdongdh;
    }

    public String getFangkedh() {
        return fangkedh;
    }

    public void setFangkedh(String fangkedh) {
        this.fangkedh = fangkedh;
    }

    public int getNianleij() {
        return nianleij;
    }

    public void setNianleij(int nianleij) {
        this.nianleij = nianleij;
    }

    public int getHuanbiao() {
        return huanbiao;
    }

    public void setHuanbiao(int huanbiao) {
        this.huanbiao = huanbiao;
    }

    public int getQianfei() {
        return qianfei;
    }

    public void setQianfei(int qianfei) {
        this.qianfei = qianfei;
    }

    public int getXiugaibz() {
        return xiugaibz;
    }

    public void setXiugaibz(int xiugaibz) {
        this.xiugaibz = xiugaibz;
    }

    public int getShangchuanbz() {
        return shangchuanbz;
    }

    public void setShangchuanbz(int shangchuanbz) {
        this.shangchuanbz = shangchuanbz;
    }

    public int getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(int downloadType) {
        this.downloadType = downloadType;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getKuaihao() {
        return kuaihao;
    }

    public void setKuaihao(String kuaihao) {
        this.kuaihao = kuaihao;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getCaliberValue() {
        return caliberValue;
    }

    public void setCaliberValue(int caliberValue) {
        this.caliberValue = caliberValue;
    }

    public int getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(int monthTotal) {
        this.monthTotal = monthTotal;
    }

    public int getQuarterTotal() {
        return quarterTotal;
    }

    public void setQuarterTotal(int quarterTotal) {
        this.quarterTotal = quarterTotal;
    }

    public int getYearTotal() {
        return yearTotal;
    }

    public void setYearTotal(int yearTotal) {
        this.yearTotal = yearTotal;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
