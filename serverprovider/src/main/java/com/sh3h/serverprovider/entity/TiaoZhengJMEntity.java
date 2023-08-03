package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiaoZhengJMEntity {

    public TiaoZhengJMEntity() {

    }

    /// 记录id
    public int ID;

    /// 水费编号
    public int I_FEEID;

    /// 账务年月
    public int I_ZHANGWUNY;

    /// 主表名
    public String S_MAINTABLENAME;

    /// 工单编号（用于查看工单信息）
    public String S_GONGDANBH;

    /// 账务处理标志
    public int I_CHULI;

    /// 账务处理类型
    public int I_CHULILX;

    ///开账水量（用于和处理水量进行比较）
    public int I_KAIZHANGSL;

    /// 调整的抄码
    public int I_TIAOZHENGCM;

    /// 处理水量（用于验证是否为空）
    public int S_CHULISL;
    /// 处理水量
    public int I_CHULISL;

    /// 差额调整的调价号
    public String S_TiaoJiaH;

    /// 差额调整的简号
    public String S_JianHao;

    /// 选择的费用组成
    public String S_XuanZeFYZC;

    /// 未选择的费用组成
    public String S_WeiXuanZeFYZC;

    /// 处理原因Text
    public String S_CHULIYY;

    /// 处理原因value,用于验证是否选择过原因
    public int I_CHULIYY;

    /// 备注
    public String S_BEIZHU;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getI_FEEID() {
        return I_FEEID;
    }

    public void setI_FEEID(int i_FEEID) {
        I_FEEID = i_FEEID;
    }

    public int getI_ZHANGWUNY() {
        return I_ZHANGWUNY;
    }

    public void setI_ZHANGWUNY(int i_ZHANGWUNY) {
        I_ZHANGWUNY = i_ZHANGWUNY;
    }

    public String getS_MAINTABLENAME() {
        return S_MAINTABLENAME;
    }

    public void setS_MAINTABLENAME(String s_MAINTABLENAME) {
        S_MAINTABLENAME = s_MAINTABLENAME;
    }

    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setS_GONGDANBH(String s_GONGDANBH) {
        S_GONGDANBH = s_GONGDANBH;
    }

    public int getI_CHULI() {
        return I_CHULI;
    }

    public void setI_CHULI(int i_CHULI) {
        I_CHULI = i_CHULI;
    }

    public int getI_CHULILX() {
        return I_CHULILX;
    }

    public void setI_CHULILX(int i_CHULILX) {
        I_CHULILX = i_CHULILX;
    }

    public int getI_KAIZHANGSL() {
        return I_KAIZHANGSL;
    }

    public void setI_KAIZHANGSL(int i_KAIZHANGSL) {
        I_KAIZHANGSL = i_KAIZHANGSL;
    }

    public int getI_TIAOZHENGCM() {
        return I_TIAOZHENGCM;
    }

    public void setI_TIAOZHENGCM(int i_TIAOZHENGCM) {
        I_TIAOZHENGCM = i_TIAOZHENGCM;
    }

    public int getS_CHULISL() {
        return S_CHULISL;
    }

    public void setS_CHULISL(int s_CHULISL) {
        S_CHULISL = s_CHULISL;
    }

    public int getI_CHULISL() {
        return I_CHULISL;
    }

    public void setI_CHULISL(int i_CHULISL) {
        I_CHULISL = i_CHULISL;
    }

    public String getS_TiaoJiaH() {
        return S_TiaoJiaH;
    }

    public void setS_TiaoJiaH(String s_TiaoJiaH) {
        S_TiaoJiaH = s_TiaoJiaH;
    }

    public String getS_JianHao() {
        return S_JianHao;
    }

    public void setS_JianHao(String s_JianHao) {
        S_JianHao = s_JianHao;
    }

    public String getS_XuanZeFYZC() {
        return S_XuanZeFYZC;
    }

    public void setS_XuanZeFYZC(String s_XuanZeFYZC) {
        S_XuanZeFYZC = s_XuanZeFYZC;
    }

    public String getS_WeiXuanZeFYZC() {
        return S_WeiXuanZeFYZC;
    }

    public void setS_WeiXuanZeFYZC(String s_WeiXuanZeFYZC) {
        S_WeiXuanZeFYZC = s_WeiXuanZeFYZC;
    }

    public String getS_CHULIYY() {
        return S_CHULIYY;
    }

    public void setS_CHULIYY(String s_CHULIYY) {
        S_CHULIYY = s_CHULIYY;
    }

    public int getI_CHULIYY() {
        return I_CHULIYY;
    }

    public void setI_CHULIYY(int i_CHULIYY) {
        I_CHULIYY = i_CHULIYY;
    }

    public String getS_BEIZHU() {
        return S_BEIZHU;
    }

    public void setS_BEIZHU(String s_BEIZHU) {
        S_BEIZHU = s_BEIZHU;
    }

    public static TiaoZhengJMEntity fromJSON(JSONObject object) {
        TiaoZhengJMEntity chaoBiaoDJ = new TiaoZhengJMEntity();

        chaoBiaoDJ.setID(object.optInt("iD"));
        chaoBiaoDJ.setI_FEEID(object.optInt("i_FEEID"));
        chaoBiaoDJ.setI_ZHANGWUNY(object.optInt("i_ZHANGWUNY"));
        chaoBiaoDJ.setS_MAINTABLENAME(object.optString("s_MAINTABLENAME"));
        chaoBiaoDJ.setS_GONGDANBH(object.optString("s_GONGDANBH"));
        chaoBiaoDJ.setS_BEIZHU(object.optString("s_BEIZHU"));
        chaoBiaoDJ.setI_CHULI(object.optInt("i_CHULI"));
        chaoBiaoDJ.setI_CHULILX(object.optInt("i_CHULILX"));
        chaoBiaoDJ.setI_KAIZHANGSL(object.optInt("i_KAIZHANGSL"));
        chaoBiaoDJ.setI_TIAOZHENGCM(object.optInt("i_TIAOZHENGCM"));
        chaoBiaoDJ.setS_CHULISL(object.optInt("s_CHULISL"));
        chaoBiaoDJ.setI_CHULISL(object.optInt("i_CHULISL"));
        chaoBiaoDJ.setS_TiaoJiaH(object.optString("s_TiaoJiaH"));
        chaoBiaoDJ.setS_JianHao(object.optString("s_JianHao"));
        chaoBiaoDJ.setS_XuanZeFYZC(object.optString("s_XuanZeFYZC"));
        chaoBiaoDJ.setS_WeiXuanZeFYZC(object.optString("s_WeiXuanZeFYZC"));
        chaoBiaoDJ.setS_CHULIYY(object.optString("s_CHULIYY"));
        chaoBiaoDJ.setI_CHULIYY(object.optInt("i_CHULIYY"));
        return chaoBiaoDJ;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<TiaoZhengJMEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<TiaoZhengJMEntity> list = new ArrayList<TiaoZhengJMEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            TiaoZhengJMEntity entity = TiaoZhengJMEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<TiaoZhengJMEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (TiaoZhengJMEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(TiaoZhengJMEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", entity.getID());
            obj.put("I_FEEID", entity.getI_FEEID());
            obj.put("I_ZHANGWUNY", entity.getI_ZHANGWUNY());
            obj.put("S_MAINTABLENAME", entity.getS_MAINTABLENAME());
            obj.put("S_GONGDANBH", entity.getS_GONGDANBH());
            obj.put("I_CHULI", entity.getI_CHULI());
            obj.put("S_BEIZHU", entity.getS_BEIZHU());
            obj.put("I_CHULILX", entity.getI_CHULILX());
            obj.put("I_KAIZHANGSL", entity.getI_KAIZHANGSL());
            obj.put("I_TIAOZHENGCM", entity.getI_TIAOZHENGCM());
            obj.put("S_CHULISL", entity.getS_CHULISL());
            obj.put("I_CHULISL", entity.getI_CHULISL());
            obj.put("S_TiaoJiaH", entity.getS_TiaoJiaH());
            obj.put("S_JianHao", entity.getS_JianHao());
            obj.put("S_XuanZeFYZC", entity.getS_XuanZeFYZC());
            obj.put("S_WeiXuanZeFYZC", entity.getS_WeiXuanZeFYZC());
            obj.put("S_CHULIYY", entity.getS_CHULIYY());
            obj.put("I_CHULIYY", entity.getI_CHULIYY());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", getID());
            obj.put("I_FEEID", getI_FEEID());
            obj.put("I_ZHANGWUNY", getI_ZHANGWUNY());
            obj.put("S_MAINTABLENAME", getS_MAINTABLENAME());
            obj.put("S_GONGDANBH", getS_GONGDANBH());
            obj.put("I_CHULI", getI_CHULI());
            obj.put("S_BEIZHU", getS_BEIZHU());
            obj.put("I_CHULILX", getI_CHULILX());
            obj.put("I_KAIZHANGSL", getI_KAIZHANGSL());
            obj.put("I_TIAOZHENGCM", getI_TIAOZHENGCM());
            obj.put("S_CHULISL", getS_CHULISL());
            obj.put("I_CHULISL", getI_CHULISL());
            obj.put("S_TiaoJiaH", getS_TiaoJiaH());
            obj.put("S_JianHao", getS_JianHao());
            obj.put("S_XuanZeFYZC", getS_XuanZeFYZC());
            obj.put("S_WeiXuanZeFYZC", getS_WeiXuanZeFYZC());
            obj.put("S_CHULIYY", getS_CHULIYY());
            obj.put("I_CHULIYY", getI_CHULIYY());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
