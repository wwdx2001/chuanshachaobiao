package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BiaoKaXXEntity {
    public static final int CHAOBIAO_BIAOKAXX = 0;
    public static final int SAMPLING_BIAOKAXX = 1;
    public static final int NEW_BIAOKAXX = 2;
    public static final int OUTSIDE_BIAOKAXX = 3;

    /**
     * 册本号
     */
    private String _cH;

    /**
     * 册内序号
     */
    private int _ceNeiXH;

    /**
     * 用户号
     */
    private String _cID;

    /**
     * 客户编号
     */
    private String _keHuBH;

    /**
     * 客户名称
     */
    private String _keHuMC;

    /**
     * 站点
     */
    private String _sT;

    /**
     * 地址
     */
    private String _diZhi;

    /**
     * 联系人
     */
    private String S_LIANXIR;

    /**
     * 联系电话
     */
    private String S_LIANXIDH;

    /**
     * 联系手机
     */
    private String _lianXiSJ;

    /**
     * 收费方式
     */
    private String _shouFeiFS;

    /**
     * 银行名称
     */
    private String _yinHangMC;

    /**
     * 简号
     */
    private String _jianHao;

    /**
     * 简号名称
     */
    private String _jianHaoMC;

    /**
     * 用户状态
     */
    private int _yongHuZT;

    /**
     * 立户日期
     */
    private long _liHuRQ;

    /**
     * 表位
     */
    private String _biaoWei;

    /**
     * 水表钢印号
     */
    private String _shuiBiaoGYH;

    /**
     * 水表条形码
     */
    private String _shuiBiaoTXM;

    /**
     * 口径名称
     */
    private String _kouJingMC;

    /**
     * 量程
     */
    private int _liangCheng;

    /**
     * 表型
     */
    private String _biaoXing;

    /**
     * 水表厂家
     */
    private String _shuiBiaoCJ;

    /**
     * 水表分类
     */
    private int _shuiBiaoFL;

    /**
     * 水表分类名称
     */
    private String _shuiBiaoFLMC;

    /**
     * 水表倍率
     */
    private int _shuibiaoBL;

    /**
     * 开账分类
     */
    private String _kaiZhangFL;

    /**
     * 功能分类
     */
    private int _gongNengFL;

    /**
     * 是否计划用水
     */
    private int _shiFouJHYS;

    /**
     * 是否收垃圾费
     */
    private int _shiFouShouLJF;

    /**
     * 垃圾费系数
     */
    private Number _laJiFeiXS;

    /**
     * 是否收违约金
     */
    private int _shiFouShouWYJ;

    /**
     * 是否定额加价
     */
    private int _shiFouDEJJ;

    /**
     * 定额水量
     */
    private int _dingESL;

    /**
     * 总表编号
     */
    private String _zongBiaoBH;

    /**
     * 装表日期
     */
    private long _zhuangBiaoRQ;

    /**
     * 换表日期
     */
    private long _huanBiaoRQ;

    /**
     * 新表底码
     */
    private int _xinBiaoDM;

    /**
     * 旧表抄码
     */
    private int _jiuBiaoCM;

    /**
     * 经度
     */
    private String _x1;

    /**
     * 维度
     */
    private String _y1;

    /**
     * 基站X
     */
    private String _x;

    /**
     * 基站Y
     */
    private String _y;

    /**
     * 分摊方式
     */
    private int _fenTanFS;

    /**
     * 分摊量
     */
    private int _fenTanL;

    /**
     * 预存款余额
     */
    private Number _yuCunKYE;

    /**
     * 欠费总笔数
     */
    private int _qianFeiZBS;

    /**
     * 欠费总金额
     */
    private Number _qianFeiZJE;

    /**
     * 水表状态
     */
    private int _shuibiaoZT;
    /**
     * 年累计量
     */
    private int _nianLeiJ;

    // 人口数
    private Number _RENKOUS;
    // 低保优惠水量
    private int _DIBAOYHSL;
    // 公厕优惠水量
    private int _GONGCEYHSL;

    // 基本用水费折扣率
    private Number _YONGSHUIZKL;
    // 污水费折扣率
    private Number _PAISHUIZKL;
    // 水资源费
    private Number _ZHEKOUL1;
    // 水价调节金
    private Number _ZHEKOUL2;
    // 市区价格调节基金
    private Number _ZHEKOUL3;
    //表卡状态
    //private String _BIAOKAZT;
    // 水表安装记件类别
    private String _MeterInstallTypeName;

    private int _GroupId;

    private String _KuaiHao;

    private String _CustomerType;

    private String _IsVIP;

    private String _VIPCode;

    private long _DateOfContract;

    private long _Contractage;

    private String _PostType;

    private String _Creditrating;

    private String _WaterMode;

    private long _Strongdate;

    private int _PayMethod;
    private int _CaliberValue;
    private int _MonthTotal;
    private int _QuarterTotal;
    private int _YearTotal;
    private String _Extend;


    public int getshuibiaoZT() {
        return _shuibiaoZT;
    }

    public void setshuibiaoZT(int _shuibiaoZT) {
        this._shuibiaoZT = _shuibiaoZT;
    }

    public String getcH() {
        return _cH;
    }

    public void setcH(String cH) {
        this._cH = cH;
    }

    public int getCeNeiXH() {
        return _ceNeiXH;
    }

    public void setCeNeiXH(int ceNeiXH) {
        this._ceNeiXH = ceNeiXH;
    }

    public String getcID() {
        return _cID;
    }

    public void setcID(String cID) {
        this._cID = cID;
    }

    public String getKeHuBH() {
        return _keHuBH;
    }

    public void setKeHuBH(String keHuBH) {
        this._keHuBH = keHuBH;
    }

    public String getKeHuMC() {
        return _keHuMC;
    }

    public void setKeHuMC(String keHuMC) {
        this._keHuMC = keHuMC;
    }

    public String getsT() {
        return _sT;
    }

    public void setsT(String sT) {
        this._sT = sT;
    }

    public String getDiZhi() {
        return _diZhi;
    }

    public void setDiZhi(String diZhi) {
        this._diZhi = diZhi;
    }

    public String getLianXiR() {
        return S_LIANXIR;
    }

    public void setLianXiR(String lianXiR) {
        this.S_LIANXIR = lianXiR;
    }

    public String getLianXiDH() {
        return S_LIANXIDH;
    }

    public void setLianXiDH(String lianXiDH) {
        this.S_LIANXIDH = lianXiDH;
    }

    public String getShouFeiFS() {
        return _shouFeiFS;
    }

    public void setShouFeiFS(String shouFeiFS) {
        this._shouFeiFS = shouFeiFS;
    }

    public String getYinHangMC() {
        return _yinHangMC;
    }

    public void setYinHangMC(String yinHangMC) {
        this._yinHangMC = yinHangMC;
    }

    public String getJianHao() {
        return _jianHao;
    }

    public void setJianHao(String jianHao) {
        this._jianHao = jianHao;
    }

    public String getJianHaoMC() {
        return _jianHaoMC;
    }

    public void setJianHaoMC(String jianHaoMC) {
        this._jianHaoMC = jianHaoMC;
    }

    public int getYongHuZT() {
        return _yongHuZT;
    }

    public void setYongHuZT(int yongHuZT) {
        this._yongHuZT = yongHuZT;
    }

    public long getLiHuRQ() {
        return _liHuRQ;
    }

    public void setLiHuRQ(long liHuRQ) {
        this._liHuRQ = liHuRQ;
    }

    public String getBiaoWei() {
        return _biaoWei;
    }

    public void setBiaoWei(String biaoWei) {
        this._biaoWei = biaoWei;
    }

    public String getShuiBiaoGYH() {
        return _shuiBiaoGYH;
    }

    public void setShuiBiaoGYH(String shuiBiaoGYH) {
        this._shuiBiaoGYH = shuiBiaoGYH;
    }

    public String getShuiBiaoTXM() {
        return _shuiBiaoTXM;
    }

    public void setShuiBiaoTXM(String shuiBiaoTXM) {
        this._shuiBiaoTXM = shuiBiaoTXM;
    }

    public String getKouJingMC() {
        return _kouJingMC;
    }

    public void setKouJingMC(String kouJingMC) {
        this._kouJingMC = kouJingMC;
    }

    public int getLiangCheng() {
        return _liangCheng;
    }

    public void setLiangCheng(int liangCheng) {
        this._liangCheng = liangCheng;
    }

    public String getBiaoXing() {
        return _biaoXing;
    }

    public void setBiaoXing(String biaoXing) {
        this._biaoXing = biaoXing;
    }

    public String getShuiBiaoCJ() {
        return _shuiBiaoCJ;
    }

    public void setShuiBiaoCJ(String shuiBiaoCJ) {
        this._shuiBiaoCJ = shuiBiaoCJ;
    }

    public int getShuiBiaoFL() {
        return _shuiBiaoFL;
    }

    public void setShuiBiaoFL(int shuiBiaoFL) {
        this._shuiBiaoFL = shuiBiaoFL;
    }

    public String getShuiBiaoFLMC() {
        return _shuiBiaoFLMC;
    }

    public void setShuiBiaoFLMC(String shuiBiaoFLMC) {
        this._shuiBiaoFLMC = shuiBiaoFLMC;
    }

    public int getShuibiaoBL() {
        return _shuibiaoBL;
    }

    public void setShuibiaoBL(int shuibiaoBL) {
        this._shuibiaoBL = shuibiaoBL;
    }

    public String getKaiZhangFL() {
        return _kaiZhangFL;
    }

    public void setKaiZhangFL(String kaiZhangFL) {
        this._kaiZhangFL = kaiZhangFL;
    }

    public int getGongNengFL() {
        return _gongNengFL;
    }

    public void setGongNengFL(int gongNengFL) {
        this._gongNengFL = gongNengFL;
    }

    public int getShiFouJHYS() {
        return _shiFouJHYS;
    }

    public void setShiFouJHYS(int shiFouJHYS) {
        this._shiFouJHYS = shiFouJHYS;
    }

    public int getShiFouShouLJF() {
        return _shiFouShouLJF;
    }

    public void setShiFouShouLJF(int shiFouShouLJF) {
        this._shiFouShouLJF = shiFouShouLJF;
    }

    public Number getLaJiFeiXS() {
        return _laJiFeiXS;
    }

    public void setLaJiFeiXS(Number laJiFeiXS) {
        this._laJiFeiXS = laJiFeiXS;
    }

    public int getShiFouShouWYJ() {
        return _shiFouShouWYJ;
    }

    public void setShiFouShouWYJ(int shiFouShouWYJ) {
        this._shiFouShouWYJ = shiFouShouWYJ;
    }

    public int getShiFouDEJJ() {
        return _shiFouDEJJ;
    }

    public void setShiFouDEJJ(int shiFouDEJJ) {
        this._shiFouDEJJ = shiFouDEJJ;
    }

    public int getDingESL() {
        return _dingESL;
    }

    public void setDingESL(int dingESL) {
        this._dingESL = dingESL;
    }

    public String getZongBiaoBH() {
        return _zongBiaoBH;
    }

    public void setZongBiaoBH(String zongBiaoBH) {
        this._zongBiaoBH = zongBiaoBH;
    }

    public long getZhuangBiaoRQ() {
        return _zhuangBiaoRQ;
    }

    public void setZhuangBiaoRQ(long zhuangBiaoRQ) {
        this._zhuangBiaoRQ = zhuangBiaoRQ;
    }

    public long getHuanBiaoRQ() {
        return _huanBiaoRQ;
    }

    public void setHuanBiaoRQ(long huanBiaoRQ) {
        this._huanBiaoRQ = huanBiaoRQ;
    }

    public int getXinBiaoDM() {
        return _xinBiaoDM;
    }

    public void setXinBiaoDM(int xinBiaoDM) {
        this._xinBiaoDM = xinBiaoDM;
    }

    public int getJiuBiaoCM() {
        return _jiuBiaoCM;
    }

    public void setJiuBiaoCM(int jiuBiaoCM) {
        this._jiuBiaoCM = jiuBiaoCM;
    }

    public String getX1() {
        return _x1;
    }

    public void setX1(String x1) {
        this._x1 = x1;
    }

    public String getY1() {
        return _y1;
    }

    public void setY1(String y1) {
        this._y1 = y1;
    }

    public String getX() {
        return _x;
    }

    public void setX(String x) {
        this._x = x;
    }

    public String getY() {
        return _y;
    }

    public void setY(String y) {
        this._y = y;
    }

    public int getFenTanFS() {
        return _fenTanFS;
    }

    public void setFenTanFS(int fenTanFS) {
        this._fenTanFS = fenTanFS;
    }

    public int getFenTanL() {
        return _fenTanL;
    }

    public void setFenTanL(int fenTanL) {
        this._fenTanL = fenTanL;
    }

    public Number getYuCunKYE() {
        return _yuCunKYE;
    }

    public void setYuCunKYE(Number yuCunKYE) {
        this._yuCunKYE = yuCunKYE;
    }

    public int getQianFeiZBS() {
        return _qianFeiZBS;
    }

    public void setQianFeiZBS(int qianFeiZBS) {
        this._qianFeiZBS = qianFeiZBS;
    }

    public Number getQianFeiZJE() {
        return _qianFeiZJE;
    }

    public void setQianFeiZJE(Number qianFeiZJE) {
        this._qianFeiZJE = qianFeiZJE;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }

    public String getLianXiSJ() {
        return _lianXiSJ;
    }

    public void setLianXiSJ(String lianXiSJ) {
        this._lianXiSJ = lianXiSJ;
    }

    public Number getRENKOUS() {
        return _RENKOUS;
    }

    public void setRENKOUS(Number RENKOUS) {
        this._RENKOUS = RENKOUS;
    }

    public int getDIBAOYHSL() {
        return _DIBAOYHSL;
    }

    public void setDIBAOYHSL(int DIBAOYHSL) {
        this._DIBAOYHSL = DIBAOYHSL;
    }

    public int getGONGCEYHSL() {
        return _GONGCEYHSL;
    }

    public void setGONGCEYHSL(int GONGCEYHSL) {
        this._GONGCEYHSL = GONGCEYHSL;
    }

    public Number getYONGSHUIZKL() {
        return _YONGSHUIZKL;
    }

    public void setYONGSHUIZKL(Number YONGSHUIZKL) {
        this._YONGSHUIZKL = YONGSHUIZKL;
    }

    public Number getPAISHUIZKL() {
        return _PAISHUIZKL;
    }

    public void setPAISHUIZKL(Number PAISHUIZKL) {
        this._PAISHUIZKL = PAISHUIZKL;
    }

    public Number getZHEKOUL1() {
        return _ZHEKOUL1;
    }

    public void setZHEKOUL1(Number ZHEKOUL1) {
        this._ZHEKOUL1 = ZHEKOUL1;
    }

    public Number getZHEKOUL2() {
        return _ZHEKOUL2;
    }

    public void setZHEKOUL2(Number ZHEKOUL2) {
        this._ZHEKOUL2 = ZHEKOUL2;
    }

    public Number getZHEKOUL3() {
        return _ZHEKOUL3;
    }

    public void setZHEKOUL3(Number ZHEKOUL3) {
        this._ZHEKOUL3 = ZHEKOUL3;
    }

//	public String get_BIAOKAZT() {
//		return _BIAOKAZT;
//	}
//
//	public void set_BIAOKAZT(String _BIAOKAZT) {
//		this._BIAOKAZT = _BIAOKAZT;
//	}


    public int get_GroupId() {
        return _GroupId;
    }

    public void set_GroupId(int _GroupId) {
        this._GroupId = _GroupId;
    }

    public void setMeterInstallTypeName(String meterInstallTypeName) {
        this._MeterInstallTypeName = meterInstallTypeName;
    }

    public String getMeterInstallTypeName() {
        return this._MeterInstallTypeName;
    }

    public String get_KuaiHao() {
        return _KuaiHao;
    }

    public void set_KuaiHao(String _KuaiHao) {
        this._KuaiHao = _KuaiHao;
    }

    public String get_CustomerType() {
        return _CustomerType;
    }

    public void set_CustomerType(String _CustomerType) {
        this._CustomerType = _CustomerType;
    }

    public String get_IsVIP() {
        return _IsVIP;
    }

    public void set_IsVIP(String _IsVIP) {
        this._IsVIP = _IsVIP;
    }

    public String get_VIPCode() {
        return _VIPCode;
    }

    public void set_VIPCode(String _VIPCode) {
        this._VIPCode = _VIPCode;
    }

    public long get_DateOfContract() {
        return _DateOfContract;
    }

    public void set_DateOfContract(long _DateOfContract) {
        this._DateOfContract = _DateOfContract;
    }

    public long get_Contractage() {
        return _Contractage;
    }

    public void set_Contractage(long _Contractage) {
        this._Contractage = _Contractage;
    }

    public String get_PostType() {
        return _PostType;
    }

    public void set_PostType(String _PostType) {
        this._PostType = _PostType;
    }

    public String get_Creditrating() {
        return _Creditrating;
    }

    public void set_Creditrating(String _Creditrating) {
        this._Creditrating = _Creditrating;
    }

    public String get_WaterMode() {
        return _WaterMode;
    }

    public void set_WaterMode(String _WaterMode) {
        this._WaterMode = _WaterMode;
    }

    public long get_Strongdate() {
        return _Strongdate;
    }

    public void set_Strongdate(long _Strongdate) {
        this._Strongdate = _Strongdate;
    }

    public int get_PayMethod() {
        return _PayMethod;
    }

    public void set_PayMethod(int _PayMethod) {
        this._PayMethod = _PayMethod;
    }

    public int get_CaliberValue() {
        return _CaliberValue;
    }

    public void set_CaliberValue(int _CaliberValue) {
        this._CaliberValue = _CaliberValue;
    }

    public int get_MonthTotal() {
        return _MonthTotal;
    }

    public void set_MonthTotal(int _MonthTotal) {
        this._MonthTotal = _MonthTotal;
    }

    public int get_QuarterTotal() {
        return _QuarterTotal;
    }

    public void set_QuarterTotal(int _QuarterTotal) {
        this._QuarterTotal = _QuarterTotal;
    }

    public int get_YearTotal() {
        return _YearTotal;
    }

    public void set_YearTotal(int _YearTotal) {
        this._YearTotal = _YearTotal;
    }

    public String get_Extend() {
        return _Extend;
    }

    public void set_Extend(String _Extend) {
        this._Extend = _Extend;
    }

    public int get_nianLeiJ() {
        return _nianLeiJ;
    }

    public void set_nianLeiJ(int _nianLeiJ) {
        this._nianLeiJ = _nianLeiJ;
    }

    /**
     * 备注
     */
    private String beiZhu;

    public BiaoKaXXEntity() {

    }

    public static BiaoKaXXEntity fromJSON(JSONObject object) {
        BiaoKaXXEntity bkxx = new BiaoKaXXEntity();
        bkxx.setcH(object.optString("s_CH"));
        bkxx.setCeNeiXH(object.optInt("i_CeNeiXH"));
        bkxx.setcID(object.optString("s_CID"));
        bkxx.set_GroupId(object.optInt("i_GroupId"));
        bkxx.setKeHuBH(object.optString("s_KeHuBH"));
        bkxx.setKeHuMC(object.optString("s_KeHuMC"));
        bkxx.setsT(object.optString("s_ST"));
        bkxx.setDiZhi(object.optString("s_DiZhi"));
        bkxx.setLianXiR(object.optString("s_LianXiR"));
        bkxx.setLianXiSJ(object.optString("s_LIANXISJ"));
        bkxx.setLianXiDH(object.optString("s_LianXiDH"));
        bkxx.setShouFeiFS(object.optString("s_ShouFeiFS"));
        bkxx.setYinHangMC(object.optString("s_YinHangMC"));
        bkxx.setJianHao(object.optString("s_JianHao"));
        bkxx.setJianHaoMC(object.optString("s_JianHaoMC"));
        bkxx.setYongHuZT(object.optInt("i_YongHuZT"));
        bkxx.setLiHuRQ(object.optLong("d_LiHuRQ"));
        bkxx.setShuiBiaoGYH(object.optString("s_ShuiBiaoGYH"));
        bkxx.setShuiBiaoTXM(object.optString("s_ShuiBiaoTXM"));
        bkxx.setKouJingMC(object.optString("s_KouJingMC"));
        bkxx.setLiangCheng(object.optInt("i_LiangCheng"));
        bkxx.setShuiBiaoCJ(object.optString("s_ShuiBiaoCJ"));
        bkxx.setShuiBiaoFL(object.optInt("i_ShuiBiaoFL"));
        bkxx.setShuiBiaoFLMC(object.optString("s_ShuiBiaoFLMC"));
        bkxx.setShuibiaoBL(object.optInt("i_ShuibiaoBL"));
        bkxx.setGongNengFL(object.optInt("i_GongNengFL"));
        bkxx.setShiFouJHYS(object.optInt("i_ShiFouJHYS"));
        bkxx.setShiFouShouLJF(object.optInt("i_ShiFouShouLJF"));
        bkxx.setLaJiFeiXS(object.optDouble("n_LaJiFeiXS"));
        bkxx.setShiFouShouWYJ(object.optInt("i_ShiFouShouWYJ"));
        bkxx.setDingESL(object.optInt("i_DingESL"));
        bkxx.setZongBiaoBH(object.optString("s_ZongBiaoBH"));
        bkxx.setZhuangBiaoRQ(object.optLong("d_ZhuangBiaoRQ"));
        bkxx.setHuanBiaoRQ(object.optLong("d_HuanBiaoRQ"));
        bkxx.setXinBiaoDM(object.optInt("i_XinBiaoDM"));
        bkxx.setJiuBiaoCM(object.optInt("i_JiuBiaoCM"));
        bkxx.setX(object.optString("s_X"));
        bkxx.setX1(object.optString("s_X1"));
        bkxx.setY(object.optString("s_Y"));
        bkxx.setY1(object.optString("s_Y1"));
        bkxx.setFenTanFS(object.optInt("i_FENTANFS"));
        bkxx.setFenTanL(object.optInt("i_FENTANL"));
        bkxx.setYuCunKYE(object.optDouble("n_YuCunKYE"));
        bkxx.setQianFeiZBS(object.optInt("i_QianFeiZBS"));
        bkxx.setQianFeiZJE(object.optDouble("n_QianFeiZJE"));
        bkxx.setshuibiaoZT(object.optInt("i_SHUIBIAOZT"));
        bkxx.setRENKOUS(object.optDouble("n_RENKOUS"));
        bkxx.setDIBAOYHSL(object.optInt("i_DIBAOYHSL"));
        bkxx.setGONGCEYHSL(object.optInt("i_GONGCEYHSL"));
        bkxx.setYONGSHUIZKL(object.optDouble("n_YONGSHUIZKL"));
        bkxx.setPAISHUIZKL(object.optDouble("n_PAISHUIZKL"));
        bkxx.setZHEKOUL1(object.optDouble("n_ZHEKOUL1"));
        bkxx.setZHEKOUL2(object.optDouble("n_ZHEKOUL2"));
        bkxx.setZHEKOUL3(object.optDouble("n_ZHEKOUL3"));
        bkxx.setMeterInstallTypeName(object.optString("s_MeterInstallTypeName"));
        bkxx.set_PayMethod(object.optInt("i_PayMethod"));
        bkxx.set_CaliberValue(object.optInt("i_CaliberValue"));
        bkxx.set_MonthTotal(object.optInt("i_MonthTotal"));
        bkxx.set_QuarterTotal(object.optInt("i_QuarterTotal"));
        bkxx.set_YearTotal(object.optInt("i_YearTotal"));
        bkxx.set_Extend(object.optString("s_ExtraData"));

        bkxx.setBeiZhu(object.optString("s_BEIZHU"));
        bkxx.setBiaoWei(object.optString("s_BiaoWei"));
        bkxx.setBiaoXing(object.optString("s_BiaoXing"));
        bkxx.setKaiZhangFL(object.optString("s_KaiZhangFL"));
        bkxx.setShiFouDEJJ(object.optInt("i_ShiFouDEJJ"));
        bkxx.set_KuaiHao(object.optString("s_KuaiHao"));
        bkxx.set_CustomerType(object.optString("customerType"));
        bkxx.set_IsVIP(object.optString("isVIP"));
        bkxx.set_VIPCode(object.optString("vIPCode"));
        bkxx.set_DateOfContract(object.optLong("dateOfContract"));
        bkxx.set_Contractage(object.optLong("contractage"));
        bkxx.set_PostType(object.optString("postType"));
        bkxx.set_Creditrating(object.optString("creditrating"));
        bkxx.set_WaterMode(object.optString("waterMode"));
        bkxx.set_Strongdate(object.optLong("strongdate"));

        bkxx.set_nianLeiJ(object.optInt("i_NIANLEIJ"));
        return bkxx;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<BiaoKaXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<BiaoKaXXEntity> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            BiaoKaXXEntity entity = BiaoKaXXEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONObject toJSON(BiaoKaXXEntity biaoKaXXEntity) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("s_CH", biaoKaXXEntity.getcH());
        object.put("i_CeNeiXH", biaoKaXXEntity.getCeNeiXH());
        object.put("s_CID", biaoKaXXEntity.getcID());

        return object;
    }

    public static JSONArray toJSONArray(List<BiaoKaXXEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (BiaoKaXXEntity biaoKaXXEntity : list) {
            JSONObject object = toJSON(biaoKaXXEntity);
            array.put(object);
        }

        return array;
    }
}
