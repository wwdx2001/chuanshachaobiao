package com.sh3h.dataprovider.entity;

import java.util.List;

/**
 * Created by zhangzhe on 2015/10/13.
 */
public class ConditionInfo {

    private String _account;        //抄表员

    private String _cebenhao;       //册本号

    private String _cid;            //客户编号

    private String _huming;         //户名

    public String _dizhi;           //地址

    public String _shuibiaogyh;     //水表钢印号

    public String _lianxidh;        //联系电话

    public String _jianhao;         //简号

    public String _biaohao;         //表号

    public double _koujinmin;       //口径下限

    public double _koujinmax;       //口径上限

    public int _qianfeibs;      //欠费笔数

    public double _qianfeije;       //欠费金额

    public long _huanbiaorq;        //换表日期

    public List<String> _ceBenList; //册本号集合


    public String get_cebenhao() {
        return _cebenhao;
    }

    public void set_cebenhao(String _cebenhao) {
        this._cebenhao = _cebenhao;
    }

    public String get_cid() {
        return _cid;
    }

    public void set_cid(String _cid) {
        this._cid = _cid;
    }

    public String get_huming() {
        return _huming;
    }

    public void set_huming(String _huming) {
        this._huming = _huming;
    }

    public String get_dizhi() {
        return _dizhi;
    }

    public void set_dizhi(String _dizhi) {
        this._dizhi = _dizhi;
    }

    public String get_lianxidh() {
        return _lianxidh;
    }

    public void set_lianxidh(String _lianxidh) {
        this._lianxidh = _lianxidh;
    }

    public String get_shuibiaogyh() {
        return _shuibiaogyh;
    }

    public void set_shuibiaogyh(String _shuibiaogyh) {
        this._shuibiaogyh = _shuibiaogyh;
    }

    public String get_jianhao() {
        return _jianhao;
    }

    public void set_jianhao(String _jianhao) {
        this._jianhao = _jianhao;
    }

    public String get_biaohao() {
        return _biaohao;
    }

    public void set_biaohao(String _biaohao) {
        this._biaohao = _biaohao;
    }

    public double get_koujinmin() {
        return _koujinmin;
    }

    public void set_koujinmin(double _koujinmin) {
        this._koujinmin = _koujinmin;
    }

    public double get_koujinmax() {
        return _koujinmax;
    }

    public void set_koujinmax(double _koujinmax) {
        this._koujinmax = _koujinmax;
    }

    public int get_qianfeibs() {
        return _qianfeibs;
    }

    public void set_qianfeibs(int _qianfeibs) {
        this._qianfeibs = _qianfeibs;
    }

    public double get_qianfeije() {
        return _qianfeije;
    }

    public void set_qianfeije(double _qianfeije) {
        this._qianfeije = _qianfeije;
    }

    public long get_huanbiaorq() {
        return _huanbiaorq;
    }

    public void set_huanbiaorq(long _huanbiaorq) {
        this._huanbiaorq = _huanbiaorq;
    }

    public List<String> getCeBenList() {
        return _ceBenList;
    }

    public void setCeBenList(List<String> ceBenList) {
        this._ceBenList = ceBenList;
    }

    public String get_account() {
        return _account;
    }

    public void set_account(String _account) {
        this._account = _account;
    }
}
