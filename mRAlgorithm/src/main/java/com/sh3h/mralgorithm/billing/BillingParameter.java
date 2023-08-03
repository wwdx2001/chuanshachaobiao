package com.sh3h.mralgorithm.billing;

/**
 * 开账计算金额实体类
 *
 * @author liukaiyu
 *
 */
public class BillingParameter {
    private int _tiaojiah;// 调价号

    private String _jh;// 简号

    private int _feiyongdlid;// 费用大类id

    private int _feiyongid;// 费用id

    private int _qishiy;// 起始月

    private int _jieshuy;// 结束月

    private int _kaishisl;// 开始水量

    private int _jieshusl;// 结束水量

    private int _jietijb;// 阶梯级别

    private double _zhekoul; // 折扣率

    private int _zhekoulx; // 折扣类型

    private double _jiage;// 价格

    private double _xishu;//费用大类对应的系数

    private int _jietis; // 阶梯数

    private int _renkousl; // 一个人口水量(8, 12)

    //private int _bencisl;// 本次抄见水量

    //private int _jietisl;// 阶梯内水量

    //private double _jietije;// 阶梯内金额

    public BillingParameter(){}
    /**
     * 获取调价号
     *
     * @return
     */
    public int get_tiaojiah() {
        return _tiaojiah;
    }

    /**
     * 设置调价号
     *
     * @param tiaojiah
     */
    public void set_tiaojiah(int tiaojiah) {
        this._tiaojiah = tiaojiah;
    }

    /**
     * 获取简号
     *
     * @return
     */
    public String get_jh() {
        return _jh;
    }

    /**
     * 设置简号
     *
     * @param jh
     */
    public void set_jh(String jh) {
        this._jh = jh;
    }

    /**
     * 获取费用大类id
     *
     * @return
     */
    public int get_feiyongdlid() {
        return _feiyongdlid;
    }

    /**
     * 设置费用大类id
     *
     * @param feiyongdlid
     */
    public void set_feiyongdlid(int feiyongdlid) {
        this._feiyongdlid = feiyongdlid;
    }

    /**
     * 获取费用id
     *
     * @return
     */
    public int get_feiyongid() {
        return _feiyongid;
    }

    /**
     * 设置费用id
     *
     * @param feiyongid
     */
    public void set_feiyongid(int feiyongid) {
        this._feiyongid = feiyongid;
    }

    /**
     * 获取起始月
     *
     * @return
     */
    public int get_qishiy() {
        return _qishiy;
    }

    /**
     * 设置起始月
     *
     * @param qishiy
     */
    public void set_qishiy(int qishiy) {
        this._qishiy = qishiy;
    }

    /**
     * 获取结束月
     *
     * @return
     */
    public int get_jieshuy() {
        return _jieshuy;
    }

    /**
     * 设置结束月
     *
     * @param jieshuy
     */
    public void set_jieshuy(int jieshuy) {
        this._jieshuy = jieshuy;
    }

    /**
     * 获取开始水量
     *
     * @return
     */
    public int get_kaishisl() {
        return _kaishisl;
    }

    /**
     * 设置开始水量
     *
     * @param kaishisl
     */
    public void set_kaishisl(int kaishisl) {
        this._kaishisl = kaishisl;
    }

    /**
     * 获取阶梯结束水量
     *
     * @return
     */
    public int get_jieshusl() {
        return _jieshusl;
    }

    /**
     * 设置阶梯结束水量
     *
     * @param jieshusl
     */
    public void set_jieshusl(int jieshusl) {
        this._jieshusl = jieshusl;
    }

    /**
     * 获取阶梯级别
     *
     * @return
     */
    public int get_jietijb() {
        return _jietijb;
    }

    /**
     * 设置阶梯级别
     *
     * @param jietijb
     */
    public void set_jietijb(int jietijb) {
        this._jietijb = jietijb;
    }


    /**
     * 获取折扣率
     *
     * @return
     */
    public double get_zhekoul() {
        return _zhekoul;
    }

    /**
     * 设置折扣率
     *
     * @param zhekoul
     */
    public void set_zhekoul(double zhekoul) {
        this._zhekoul = zhekoul;
    }

    /**
     * 获取折扣类型
     *
     * @return
     */
    public int get_zhekoulx() {
        return _zhekoulx;
    }

    /**
     * 设置折扣类型
     *
     * @param zhekoulx
     */
    public void set_zhekoulx(int zhekoulx) {
        this._zhekoulx = zhekoulx;
    }

    /**
     * 获取价格
     *
     * @return
     */
    public double get_jiage() {
        return _jiage;
    }

    /**
     * 设置价格
     *
     * @param jiage
     */
    public void set_jiage(double jiage) {
        this._jiage = jiage;
    }

    /**
     * 获取费用组成大类系数
     * @return
     */
    public double get_xishu() {
        return _xishu;
    }

    public void set_xishu(double xishu) {
        this._xishu = xishu;
    }

    /**
     * 获取阶梯数
     *
     * @return
     */
    public int get_jietis() {
        return _jietis;
    }

    /**
     * 设置阶梯数
     *
     * @param jietis
     */
    public void set_jietis(int jietis) {
        this._jietis = jietis;
    }

    /**
     * 获取人口水量
     *
     * @return
     */
    public int get_renkousl() {
        return _renkousl;
    }

    /**
     * 设置人口水量
     *
     * @param renkousl
     */
    public void set_renkousl(int renkousl) {
        this._renkousl = renkousl;
    }


    /**
     * 获取本次抄码
     *
     * @return
     */
    //public int get_bencisl() {
    //    return _bencisl;
    //}

    /**
     * 设置本次抄码
     *
     * @param bencisl
     */
    //public void set_bencisl(int bencisl) {
    //   this._bencisl = bencisl;
    //}

    /**
     * 获取阶梯内水量
     *
     * @return
     */
    //public int get_jietisl() {
    //	return _jietisl;
    //}

    /**
     * 设置阶梯内水量
     *
     * @param jietisl
     */
    //public void set_jietisl(int jietisl) {
    //	this._jietisl = jietisl;
    //}

    /**
     * 获取阶梯内金额
     *
     * @return
     */
    //public double get_jietije() {
    //	return _jietije;
    //}

    /**
     * 设置阶梯内金额
     *
     * @param _jietije
     */
    //public void set_jietije(double _jietije) {
    //	this._jietije = _jietije;
    //}
}
