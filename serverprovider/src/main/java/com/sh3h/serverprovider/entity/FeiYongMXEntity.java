package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeiYongMXEntity {

    public FeiYongMXEntity() {

    }

    // 费用ID
    public int I_FEIYONGID;

    // 折扣率
    public double N_ZHEKOUL;

    // 备注
    public String S_BEIZHU;

    // 水费编号
    public int I_FEEID;

    // 站点
    public String S_ST;

    // 简号
    public String S_JH;

    // 阶梯顺序
    public int I_JIETISX;

    // 水量
    public int I_SL;

    // 混水每个简号分摊的比例
    public double N_BILI;

    // 调价号
    public int I_TIAOJIAH;

    // 金额
    public double N_JE;

    // 自增长(主键) 禁止关联此ID
    public int ID;

    // 结束月
    public int I_JIESHUY;

    // 价格
    public double N_JIAGE;

    // 记录状态:0  正常（默认）,-1  无效
    public int I_JLZT;

    // 用水量开始计算范围
    public int I_KAISHISL;

    // 用水量结束计算范围
    public int I_JIESHUSL;

    // 起始月
    public int I_QISHIY;

    // 项目编号
    public int I_KID;

    public int getI_FEIYONGID() {
        return I_FEIYONGID;
    }

    public void setI_FEIYONGID(int i_FEIYONGID) {
        I_FEIYONGID = i_FEIYONGID;
    }

    public double getN_ZHEKOUL() {
        return N_ZHEKOUL;
    }

    public void setN_ZHEKOUL(double n_ZHEKOUL) {
        N_ZHEKOUL = n_ZHEKOUL;
    }

    public String getS_BEIZHU() {
        return S_BEIZHU;
    }

    public void setS_BEIZHU(String s_BEIZHU) {
        S_BEIZHU = s_BEIZHU;
    }

    public int getI_FEEID() {
        return I_FEEID;
    }

    public void setI_FEEID(int i_FEEID) {
        I_FEEID = i_FEEID;
    }

    public String getS_ST() {
        return S_ST;
    }

    public void setS_ST(String s_ST) {
        S_ST = s_ST;
    }

    public String getS_JH() {
        return S_JH;
    }

    public void setS_JH(String s_JH) {
        S_JH = s_JH;
    }

    public int getI_JIETISX() {
        return I_JIETISX;
    }

    public void setI_JIETISX(int i_JIETISX) {
        I_JIETISX = i_JIETISX;
    }

    public int getI_SL() {
        return I_SL;
    }

    public void setI_SL(int i_SL) {
        I_SL = i_SL;
    }

    public double getN_BILI() {
        return N_BILI;
    }

    public void setN_BILI(double n_BILI) {
        N_BILI = n_BILI;
    }

    public int getI_TIAOJIAH() {
        return I_TIAOJIAH;
    }

    public void setI_TIAOJIAH(int i_TIAOJIAH) {
        I_TIAOJIAH = i_TIAOJIAH;
    }

    public double getN_JE() {
        return N_JE;
    }

    public void setN_JE(double n_JE) {
        N_JE = n_JE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getI_JIESHUY() {
        return I_JIESHUY;
    }

    public void setI_JIESHUY(int i_JIESHUY) {
        I_JIESHUY = i_JIESHUY;
    }

    public double getN_JIAGE() {
        return N_JIAGE;
    }

    public void setN_JIAGE(double n_JIAGE) {
        N_JIAGE = n_JIAGE;
    }

    public int getI_JLZT() {
        return I_JLZT;
    }

    public void setI_JLZT(int i_JLZT) {
        I_JLZT = i_JLZT;
    }

    public int getI_KAISHISL() {
        return I_KAISHISL;
    }

    public void setI_KAISHISL(int i_KAISHISL) {
        I_KAISHISL = i_KAISHISL;
    }

    public int getI_JIESHUSL() {
        return I_JIESHUSL;
    }

    public void setI_JIESHUSL(int i_JIESHUSL) {
        I_JIESHUSL = i_JIESHUSL;
    }

    public int getI_QISHIY() {
        return I_QISHIY;
    }

    public void setI_QISHIY(int i_QISHIY) {
        I_QISHIY = i_QISHIY;
    }

    public int getI_KID() {
        return I_KID;
    }

    public void setI_KID(int i_KID) {
        I_KID = i_KID;
    }

    public static FeiYongMXEntity fromJSON(JSONObject object) {
        FeiYongMXEntity feiYongMXEntity = new FeiYongMXEntity();
        feiYongMXEntity.setI_FEIYONGID(object.optInt("i_FIYONGID"));
        feiYongMXEntity.setN_ZHEKOUL(object.optDouble("n_ZHEKOUL"));
        feiYongMXEntity.setS_BEIZHU(object.optString("s_BEIZHU"));
        feiYongMXEntity.setI_FEEID(object.optInt("i_FEEID"));
        feiYongMXEntity.setS_ST(object.optString("s_ST"));
        feiYongMXEntity.setS_JH(object.optString("s_JH"));
        feiYongMXEntity.setI_JIETISX(object.optInt("i_JIETISX"));
        feiYongMXEntity.setI_SL(object.optInt("i_SL"));
        feiYongMXEntity.setN_BILI(object.optDouble("n_BILI"));
        feiYongMXEntity.setI_TIAOJIAH(object.optInt("i_TIAOJIAH"));
        feiYongMXEntity.setN_JE(object.optDouble("n_JE"));
        feiYongMXEntity.setID(object.optInt("iD"));
        feiYongMXEntity.setI_JIESHUY(object.optInt("i_JIESHUY"));
        feiYongMXEntity.setN_JIAGE(object.optDouble("n_JIAGE"));
        feiYongMXEntity.setI_JLZT(object.optInt("i_JLZT"));
        feiYongMXEntity.setI_KAISHISL(object.optInt("i_KAISHISL"));
        feiYongMXEntity.setI_JIESHUSL(object.optInt("i_JIESHUSL"));
        feiYongMXEntity.setS_ST(object.optString("s_ST"));
        feiYongMXEntity.setI_QISHIY(object.optInt("i_QISHIY"));
        feiYongMXEntity.setI_KID(object.optInt("i_KID"));
        return feiYongMXEntity;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<FeiYongMXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<FeiYongMXEntity> list = new ArrayList<FeiYongMXEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            FeiYongMXEntity entity = FeiYongMXEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }
}
