package com.sh3h.mralgorithm.gas;


import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReadingParameter {
    private String _st;

    private String _ch;

    private String _cid;

    private String _chaobiaoztbm = "-1";// 抄表状态编码

    private String _chaobiaoztmc = null; // 抄表状态编码

    private int _chaobiaosuanfabm = -1;// 抄表算法编码

    private int _bencicm = -1;// 本次抄码

    private int _shangcicm = -1;// 上次抄码

    private int _shangcixfl; // 上次消费量

    private int _jumin = 1; // 是否居民

    private double _yalicxs = 0.0; // 压力差系数

    private int _bushoulx = 0; // 补收类型

    private double _shizhibsbl = 0.0; // 示值误差

    private double _yibansxbsl = 0.0; // 一般失效补收

    private int _huanbiao = 0; // 是否换表


    private int _bencisl = -1;// 本次水量

    private Date _huanbiaohtqrsj = null;// 换表回填确认时间

    private Date _shangcixiazaisj = null;// 上次下载时间

    private Date _shangcicbrq = null;// 上次抄表日期

    private Date _bencicbrq = null;// 本次抄表日期

    private Date _huanBiaoRQ = null; // 调表日期

    private int _liangcheng = -1;// 量程

    private int _shuibiaozt = -1;// 水表状态

    private int _jiubiaocm = -1;// 旧表抄码

    private int _xinbiaodm = -1;// 新表底码

    private int _shuibiaobeil = 1;// 水表倍率


    private int _jiubiaoxfl = 0; // 旧表消费量

    private int _xinbiaoxfl = 0; // 新表消费量

    private int _buyalc = 0; // 补压力差

    private int _bushoul = 0; // 补收量

    private double _kongzhirlf; // 空置容量费

    private int _xiaofeizl = 0; //  消费总量

    private int _feizhengcjl; // 非正常计量


    private String _binglianzh; // 并联组号

    private int _binglianxh; // 并联序号

    private int _buchangfs; // 补偿方式0：不收补偿费；1：固定值每月；2：表能量乘补偿费系数

    private List<Map<String, Double>> _buChangXSList; // 补偿系数

    private int _qiyuan; // 气源  1：人工煤气；2：天然气

    private double _biaonengl; // 表能量

    private boolean _isFinalBingLian;
    private int _bingLianError;

    private double _rijunyl;

    private int _guSuanFS;
    private int _buShouFS;

    private double _tongbi;
    private double _huanbi;

    private int _lgld;

    /**
     * 默认构造函数
     */
    public ReadingParameter() {

    }

    public void reset() {
        _st = null;
        _ch = null;
        _cid = null;
        _chaobiaoztbm = "-1";// 抄表状态编码
        _chaobiaoztmc = null; // 抄表状态名称
        _chaobiaosuanfabm = -1;// 抄表算法编码
        _bencicm = -1;// 本次抄码
        _shangcicm = -1;// 上次抄码
        _shangcixfl = 0;
        _jumin = 1; // 是否居民
        _yalicxs = 0.0; // 压力差系数
        _bushoulx = 0; // 补收类型
        _shizhibsbl = 0.0; // 示值误差
        _yibansxbsl = 0.0; // 一般失效补收
        _huanbiao = 0; // 是否换表

        _bencisl = -1;// 本次水量
        _huanbiaohtqrsj = null;// 换表回填确认时间
        _shangcixiazaisj = null;// 上次下载时间
        _shangcicbrq = null;// 上次抄表日期
        _bencicbrq = null;// 本次抄表日期
        _huanBiaoRQ = null;
        _liangcheng = -1;// 量程
        _shuibiaozt = -1;// 水表状态
        _jiubiaocm = -1;// 旧表抄码
        _xinbiaodm = -1;// 新表底码
        _shuibiaobeil = 1;// 水表倍率

        _jiubiaoxfl = 0; // 旧表消费量
        _xinbiaoxfl = 0; // 新表消费量
        _buyalc = 0; // 补压力差
        _bushoul = 0; // 补收量
        _kongzhirlf = 0.0;
        _xiaofeizl = 0; //  消费总量
        _feizhengcjl = 0;

        _binglianzh = null;
        _binglianxh = 0;

        _buChangXSList = null;
        _qiyuan = 0;
        _biaonengl = 0.0;

        _isFinalBingLian = false;
        _bingLianError = 0;
        _rijunyl = 0.0;

        _guSuanFS = 0;
        _buShouFS = 0;

        _tongbi = 0.0;
        _huanbi = 0.0;

        _lgld = 0;
    }

    public String get_st() {
        return _st;
    }

    public void set_st(String st) {
        _st = st;
    }

    public String get_ch() {
        return _ch;
    }

    public void set_ch(String ch) {
        _ch = ch;
    }

    public String get_cid() {
        return _cid;
    }

    public void set_cid(String cid) {
        _cid = cid;
    }

    /**
     * 获取水表倍率
     *
     * @return
     */
    public int get_shuibiaobeil() {
        return _shuibiaobeil;
    }

    /**
     * 设置水表倍率
     *
     * @param _shuibiaobeil
     */
    public void set_shuibiaobeil(int _shuibiaobeil) {
        this._shuibiaobeil = _shuibiaobeil;
    }

    /**
     * 获取抄表算法编码
     *
     * @return
     */
    public int get_chaobiaosuanfabm() {
        return _chaobiaosuanfabm;
    }

    /**
     * 设置抄表算法编码
     *
     * @param _chaobiaosuanfabm
     */
    public void set_chaobiaosuanfabm(int _chaobiaosuanfabm) {
        this._chaobiaosuanfabm = _chaobiaosuanfabm;
    }

    /**
     * 获取旧表抄码
     *
     * @return
     */
    public int get_jiubiaocm() {
        return _jiubiaocm;
    }

    /**
     * 设置旧表抄码
     *
     * @param _jiubiaocm
     */
    public void set_jiubiaocm(int _jiubiaocm) {
        this._jiubiaocm = _jiubiaocm;
    }

    /**
     * 获取新表抄码
     *
     * @return
     */
    public int get_xinbiaodm() {
        return _xinbiaodm;
    }

    /**
     * 设置新表抄码
     *
     * @param _xinbiaodm
     */
    public void set_xinbiaodm(int _xinbiaodm) {
        this._xinbiaodm = _xinbiaodm;
    }

    /**
     * 获取上次抄表日期
     *
     * @return
     */
    public Date get_shangcicbrq() {
        return _shangcicbrq;
    }

    /**
     * 设置上次抄表日期
     *
     * @param _shangcicbrq
     */
    public void set_shangcicbrq(Date _shangcicbrq) {
        this._shangcicbrq = _shangcicbrq;
    }

    /**
     * 获取本次抄表日期
     *
     * @return
     */
    public Date get_bencicbrq() {
        return _bencicbrq;
    }

    /**
     * 设置上次抄表日期
     *
     * @param _bencicbrq
     */
    public void set_bencicbrq(Date _bencicbrq) {
        this._bencicbrq = _bencicbrq;
    }

    public Date get_huanBiaoRQ() {
        return _huanBiaoRQ;
    }

    public void set_huanBiaoRQ(Date huanBiaoRQ) {
        _huanBiaoRQ = huanBiaoRQ;
    }

    /**
     * 获取水表状态
     *
     * @return
     */
    public int get_shuibiaozt() {
        return _shuibiaozt;
    }

    /**
     * 设置水表状态
     *
     * @param _shuibiaozt
     */
    public void set_shuibiaozt(int _shuibiaozt) {
        this._shuibiaozt = _shuibiaozt;
    }

    /**
     * 获取量程
     *
     * @return
     */
    public int get_liangcheng() {
        return _liangcheng;
    }

    /**
     * 设置量程
     *
     * @param _liangcheng
     */
    public void set_liangcheng(int _liangcheng) {
        this._liangcheng = _liangcheng;
    }

    /**
     * 获取本次抄码
     *
     * @return
     */
    public int get_bencicm() {
        return _bencicm;
    }

    /**
     * 设置本次水量
     *
     * @param _bencicm
     */
    public void set_bencicm(int _bencicm) {
        this._bencicm = _bencicm;
    }

    /**
     * 获取上次抄码
     *
     * @return
     */
    public int get_shangcicm() {
        return _shangcicm;
    }

    /**
     * 设置上次抄码
     *
     * @param _shangcicm
     */
    public void set_shangcicm(int _shangcicm) {
        this._shangcicm = _shangcicm;
    }

    public int get_shangcixfl() {
        return _shangcixfl;
    }

    public void set_shangcixfl(int shangcixfl) {
        _shangcixfl = shangcixfl;
    }

    public int getJuMin() {
        return _jumin;
    }

    public void setJuMin(int jumin) {
        _jumin = jumin;
    }


    public double getYaLiCXS() {
        return _yalicxs;
    }

    public void setYaLiCXS(double yalicxs) {
        _yalicxs = yalicxs;
    }

    public int getBuShouLX() {
        return _bushoulx;
    }

    public void setBuShouLX(int bushoulx) {
        _bushoulx = bushoulx;
    }

    public double getKongZhiRLF() {
        return _kongzhirlf;
    }

    public void setKongZhiRLF(double kongzhirlf) {
        _kongzhirlf = kongzhirlf;
    }

    public double getShiZhiBSBL() {
        return _shizhibsbl;
    }

    public void setShiZhiBSBL(double shizhibsbl) {
        _shizhibsbl = shizhibsbl;
    }

    public double getYibanSXBSL() {
        return _yibansxbsl;
    }

    public void setYibanSXBSL(double yibansxbsl) {
        _yibansxbsl = yibansxbsl;
    }

    public int getHuanBiao() {
        return _huanbiao;
    }

    public void setHuanBiao(int huanbiao) {
        _huanbiao = huanbiao;
    }
    /**
     * 获取本次水量
     *
     * @return
     */
    public int get_bencisl() {
        return _bencisl;
    }

    /**
     * 设置本次水量
     *
     * @param _bencisl
     */
    public void set_bencisl(int _bencisl) {
        this._bencisl = _bencisl;
    }

    /**
     * 获取换表回填确认时间
     *
     * @return
     */
    public Date get_huanbiaohtqrsj() {
        return _huanbiaohtqrsj;
    }

    /**
     * 设置换表回填确认时间
     *
     * @param _huanbiaohtqrsj
     */
    public void set_huanbiaohtqrsj(Date _huanbiaohtqrsj) {
        this._huanbiaohtqrsj = _huanbiaohtqrsj;
    }

    /**
     * 获取上次下载时间
     *
     * @return
     */
    public Date get_shangcixiazaisj() {
        return _shangcixiazaisj;
    }

    /**
     * 设置上次下载时间
     *
     * @param _shangcixiazaisj
     */
    public void set_shangcixiazaisj(Date _shangcixiazaisj) {
        this._shangcixiazaisj = _shangcixiazaisj;
    }

    /**
     * 获取抄表状态编码
     *
     * @return
     */
    public String get_chaobiaoztbm() {
        return _chaobiaoztbm;
    }

    /**
     * 设置抄表状态编码
     *
     */
    public void set_chaobiaoztbm(String _chaobiaoztbm) {
        this._chaobiaoztbm = _chaobiaoztbm;
    }

    public String get_chaobiaoztmc() {
        return _chaobiaoztmc;
    }

    public void set_chaobiaoztmc(String chaobiaoztmc) {
        _chaobiaoztmc = chaobiaoztmc;
    }

    public int getJiuBiaoXFL() { return _jiubiaoxfl; }

    public void setJiuBiaoXFL(int jiubiaoxfl) {
        _jiubiaoxfl = jiubiaoxfl;
    }

    public int getXinBiaoXFL() { return _xinbiaoxfl; }

    public void setXinBiaoXFL(int xinbiaoxfl) {
        _xinbiaoxfl = xinbiaoxfl;
    }

    public int getBuYaLC() { return _buyalc; }

    public void setBuYaLC(int buyalc) {
        _buyalc = buyalc;
    }

    public int getBuShouL() { return _bushoul; }

    public void setBuShouL(int bushoul) {
        _bushoul = bushoul;
    }

    public int getXiaoFeiZL() {
        return _xiaofeizl;
    }

    public void setXiaoFeiZL(int xiaofeizl) {
        _xiaofeizl = xiaofeizl;
    }

    public int get_feizhengcjl() {
        return _feizhengcjl;
    }

    public void set_feizhengcjl(int feizhengcjl) {
        _feizhengcjl = feizhengcjl;
    }

    public String getBingLianZH() {
        return _binglianzh;
    }

    public void setBingLianZH(String binglianzh) {
        _binglianzh = binglianzh;
    }

    public int getBingLianXH() {
        return _binglianxh;
    }

    public void setBingLianXH(int binglianxh) {
        _binglianxh = binglianxh;
    }

    public int getBuChangFS() {
        return _buchangfs;
    }

    public void setBuChangFS(int buchangfs) {
        _buchangfs = buchangfs;
    }

    public List<Map<String, Double>> getBuChangXSList() {
        return _buChangXSList;
    }

    public void setBuChangXSList(List<Map<String, Double>> buChangXSList) {
        _buChangXSList = buChangXSList;
    }

    public int getQiYuan() {
        return _qiyuan;
    }

    public void setQiYuan(int qiyuan) {
        _qiyuan = qiyuan;
    }

    public double getBiaoNengL() {
        return _biaonengl;
    }

    public void setBiaoNengL(double biaonengl) {
        _biaonengl = biaonengl;
    }

    public boolean getIsFinalBingLian() {
        return _isFinalBingLian;
    }

    public void setIsFinalBingLian(boolean isFinalBingLian) {
        _isFinalBingLian = isFinalBingLian;
    }

    public int getBingLianError() {
        return _bingLianError;
    }

    public void setBingLianError(int bingLianError) {
        _bingLianError = bingLianError;
    }

    public double getRiJunYL() {
        return _rijunyl;
    }

    public void setRiJunYL(double rijunyl) {
        _rijunyl = rijunyl;
    }

    public int getGuSuanFS() {
        return _guSuanFS;
    }

    public void setGuSuanFS(int gusuanfs) {
        _guSuanFS = gusuanfs;
    }

    public int getBuShouFS() {
        return _buShouFS;
    }

    public void setBuShouFS(int bushoufs) {
        _buShouFS = bushoufs;
    }

    public double get_tongbi() {
        return _tongbi;
    }

    public void set_tongbi(double tongbi) {
        _tongbi = tongbi;
    }

    public double get_huanbi() {
        return _huanbi;
    }

    public void set_huanbi(double huanbi) {
        _huanbi = huanbi;
    }

    public int get_lgld() {
        return _lgld;
    }

    public void set_lgld(int lgld) {
        _lgld = lgld;
    }
}
