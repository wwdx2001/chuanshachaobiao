package com.sh3h.serverprovider.entity;

import org.json.JSONObject;

/**
 * Created by zhangzhe on 2015/10/15.
 */
public class CombinedInfoEntity {

  //  private String _account;
    private String _ch;
    private String _cid;
    private String _huming;
    private String _dizhi;
    private String _lianxidh;
    private String _shuibiaogyh;
 //   private String _jianhao;
    private String _biaohao;
 //   private double _koujingmin;
 //   private double _koujingmax;
//    private int _qianfeibs;
 //   private double _qianfeije;
//    private long _huanbiaorq;

    public CombinedInfoEntity() {
    }

    public CombinedInfoEntity(String _ch, String _cid, String _huming, String _dizhi, String _biaohao) {
        this._ch = _ch;
        this._cid = _cid;
        this._huming = _huming;
        this._dizhi = _dizhi;
        this._biaohao = _biaohao;
    }

    /*    public CombinedInfoEntity(String _account, String _ch, String _cid, String _huming, String _dizhi, String _lianxidh, String _jianhao, String _biaohao, double _koujingmin, double _koujingmax, int _qianfeibs, double _qianfeije, long _huanbiaorq) {
        this._account = _account;
        this._ch = _ch;
        this._cid = _cid;
        this._huming = _huming;
        this._dizhi = _dizhi;
        this._lianxidh = _lianxidh;
        this._jianhao = _jianhao;
        this._biaohao = _biaohao;
        this._koujingmin = _koujingmin;
        this._koujingmax = _koujingmax;
        this._qianfeibs = _qianfeibs;
        this._qianfeije = _qianfeije;
        this._huanbiaorq = _huanbiaorq;
    }*/

/*    public String get_account() {
        return _account;
    }

    public void set_account(String _account) {
        this._account = _account;
    }*/

    public String get_ch() {
        return _ch;
    }

    public void set_ch(String _ch) {
        this._ch = _ch;
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

    public String get_shuibiaogyh() {
        return _shuibiaogyh;
    }

    public void set_shuibiaogyh(String _shuibiaogyh) {
        this._shuibiaogyh = _shuibiaogyh;
    }

    public String get_lianxidh() {
        return _lianxidh;
    }

    public void set_lianxidh(String _lianxidh) {
        this._lianxidh = _lianxidh;
    }

    /* public String get_jianhao() {
        return _jianhao;
    }

    public void set_jianhao(String _jianhao) {
        this._jianhao = _jianhao;
    }*/

    public String get_biaohao() {
        return _biaohao;
    }

    public void set_biaohao(String _biaohao) {
        this._biaohao = _biaohao;
    }

   /* public double get_koujingmin() {
        return _koujingmin;
    }

    public void set_koujingmin(double _koujingmin) {
        this._koujingmin = _koujingmin;
    }

    public double get_koujingmax() {
        return _koujingmax;
    }

    public void set_koujingmax(double _koujingmax) {
        this._koujingmax = _koujingmax;
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
    }*/


    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        try{
       //     result.put("S_Account",get_account());
            result.put("S_CID",get_cid());
            result.put("S_ShuiBiaoGYH",get_biaohao());
            result.put("S_CH",get_ch());
            result.put("S_HM",get_huming());
            result.put("S_DZ",get_dizhi());
//            result.put("S_ShuiBiaoGYH",get_shuibiaogyh());
            result.put("S_DH", get_lianxidh());
     //       result.put("S_JianHao",get_jianhao());
      //      result.put("I_KouJingMin",get_koujingmin());
      //      result.put("I_KoujJingMax",get_koujingmax());
      //      result.put("I_QianFeiBS",get_qianfeibs());
      //      result.put("I_QianFeiJE",get_qianfeije());
      //      result.put("D_HuanBiaoRQ",get_huanbiaorq());

        }catch (Exception e){
            return  null;
        }

        return  result;
    }
}
