package com.sh3h.serverprovider.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QianFeiXXEntity {
    /**
     * 册本号
     */
    private String _cH;

    /**
     * 用户号
     */
    private String _cId;

    /**
     * 抄表年
     */
    private int _chaoBiaoN;

    /**
     * 抄表月
     */
    private int _chaoBiaoY;

    /**
     * 账务年月
     */
    private int _zhangWuNY;

    /**
     * 抄表日期
     */
    private long _chaoBiaoRQ;

    /**
     * 抄次
     */
    private int _chaoCi;

    /**
     * 水费编号
     */
    private int _feeId;

    /**
     * 水量
     */
    private int _kaiZhangSL;

    private double _SHUIFEI;
    private double _PAISHUIF;

    public double get_SHUIFEI() {
        return _SHUIFEI;
    }

    public void set_SHUIFEI(double _SHUIFEI) {
        this._SHUIFEI = _SHUIFEI;
    }

    public double get_PAISHUIF() {
        return _PAISHUIF;
    }

    public void set_PAISHUIF(double _PAISHUIF) {
        this._PAISHUIF = _PAISHUIF;
    }

    /**
     * 账单金额
     */
    private Number _jE;

    /**
     * 违约金
     */
    private Number _yingShouWYJ;

    public String getcH() {
        return _cH;
    }

    public void setcH(String cH) {
        this._cH = cH;
    }

    public String getcId() {
        return _cId;
    }

    public void setcId(String cId) {
        this._cId = cId;
    }

    public int getChaoBiaoN() {
        return _chaoBiaoN;
    }

    public void setChaoBiaoN(int chaoBiaoN) {
        this._chaoBiaoN = chaoBiaoN;
    }

    public int getChaoBiaoY() {
        return _chaoBiaoY;
    }

    public void setChaoBiaoY(int chaoBiaoY) {
        this._chaoBiaoY = chaoBiaoY;
    }

    public int getZhangWuNY() {
        return _zhangWuNY;
    }

    public void setZhangWuNY(int zhangWuNY) {
        this._zhangWuNY = zhangWuNY;
    }

    public long getChaoBiaoRQ() {
        return _chaoBiaoRQ;
    }

    public void setChaoBiaoRQ(long chaoBiaoRQ) {
        this._chaoBiaoRQ = chaoBiaoRQ;
    }

    public int getChaoCi() {
        return _chaoCi;
    }

    public void setChaoCi(int chaoCi) {
        this._chaoCi = chaoCi;
    }

    public int getFeeId() {
        return _feeId;
    }

    public void setFeeId(int feeId) {
        _feeId = feeId;
    }

    public int getKaiZhangSL() {
        return _kaiZhangSL;
    }

    public void setKaiZhangSL(int kaiZhangSL) {
        this._kaiZhangSL = kaiZhangSL;
    }

    public Number getjE() {
        return _jE;
    }

    public void setjE(Number jE) {
        this._jE = jE;
    }

    public Number getYingShouWYJ() {
        return _yingShouWYJ;
    }

    public void setYingShouWYJ(Number yingShouWYJ) {
        this._yingShouWYJ = yingShouWYJ;
    }

    public QianFeiXXEntity() {

    }

    /**
     * 转换JsonObject对象为QianFeiXXEntity实体
     *
     * @param object
     * @return
     */
    public static QianFeiXXEntity fromJSON(JSONObject object) {
        QianFeiXXEntity qianf = new QianFeiXXEntity();
        qianf.setcH(object.optString("s_CH"));
        qianf.setChaoBiaoN(object.optInt("i_CHAOBIAON"));
        qianf.setChaoBiaoRQ(object.optLong("d_ChaoBiaoRQ"));
        qianf.setChaoBiaoY(object.optInt("i_CHAOBIAOY"));
        qianf.setChaoCi(object.optInt("i_CHAOCI"));
        qianf.setcId(object.optString("s_CID"));
        qianf.setFeeId(object.optInt("i_FEEID"));
        qianf.setjE(object.optDouble("n_JE"));
        qianf.setKaiZhangSL(object.optInt("i_KAIZHANGSL"));
        qianf.setYingShouWYJ(object.optDouble("n_YINGSHOUWYJ"));
        qianf.setZhangWuNY(object.optInt("i_ZhangWuNY"));
        qianf.set_SHUIFEI(object.optDouble("n_SHUIFEI"));
        qianf.set_PAISHUIF(object.optDouble("n_PAISHUIF"));
        return qianf;
    }

    /**
     * 转换JsonArray对象为QianFeiXXEntity实体集合
     *
     * @param object
     * @return
     */
    public static List<QianFeiXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<QianFeiXXEntity> list = new ArrayList<QianFeiXXEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            Log.e("qianfeixinxi", "is" + object.toString());
            QianFeiXXEntity qianf = QianFeiXXEntity.fromJSON(object);
            list.add(qianf);
        }
        return list;
    }
}
