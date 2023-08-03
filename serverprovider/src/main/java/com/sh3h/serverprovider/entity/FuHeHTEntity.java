package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FuHeHTEntity {

    public FuHeHTEntity() {

    }

    /// 主键
    private int ID;

    /// 工单编号
    private String S_GONGDANBH;

    /// 回填时间
    private long D_HUITIANRQ;

    /// 处理人
    private String S_HUITIANR;

    /// 复核抄码
    private String S_FUHECM;

    /// 确认结果
    private int I_QUERENJG;

    /// 工单养护类型
    private int I_YANGHUGDLX;

    /// 养护工单类型名称
    private String S_YANGHUGD;

    /// 账务处理类型
    private int I_ZHANGWUCLLX;

    /// 账务处理类型
    private String S_ZHANGWUCL;

    /// 工单状态
    private int I_ZuoFei;

    /// 责任投诉
    private int I_YouZeRTS;

    /// 处理类别
    private String S_CHULILB;

    /// 处理内容
    private String S_CHULINR;

    /// 发生原因
    private String S_FANSHENGYY;

    /// 解决措施
    private String S_JIEJUECS;

    /// 处理结果
    private String S_CHULIJG;

    /// 复核备注
    private String S_HUITIANBZ;

    /// 差错人
    private String S_CHACUOR;

    /// 分值
    private String I_FENZHI;

    /// 差错备注
    private String S_CHAZUOBZ;

    /// 复核状态
    private String I_FUCHAOZT;

    /// 留条编号
    private String S_LIUTIAOBH;

    /// 工单类型
    private String S_GONGDANLX;

    /// 三来复核
    private String S_SANLAIFH;

    /// 原始抄码
    private int I_YUANHSHICM;

    /// CID
    private String S_CID;

    /// 地址
    private String S_DZ;

    public void reset() {
        ID = 0;
        S_GONGDANBH = null;
        D_HUITIANRQ = 0;
        S_HUITIANR = null;
        S_FUHECM = null;
        I_QUERENJG = 0;
        I_YANGHUGDLX = 0;
        S_YANGHUGD = null;
        I_ZHANGWUCLLX = 0;
        S_ZHANGWUCL = null;
        I_ZuoFei = 0;
        I_YouZeRTS = 0;
        S_CHULILB = null;
        S_CHULINR = null;
        S_FANSHENGYY = null;
        S_JIEJUECS = null;
        S_CHULIJG = null;
        S_HUITIANBZ = null;
        S_CHACUOR = null;
        I_FENZHI = null;
        S_CHAZUOBZ = null;
        I_FUCHAOZT = null;
        S_LIUTIAOBH = null;
        S_GONGDANLX = null;
        S_SANLAIFH = null;
        I_YUANHSHICM = 0;
        S_CID = null;
        S_DZ = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setS_GONGDANBH(String s_GONGDANBH) {
        S_GONGDANBH = s_GONGDANBH;
    }

    public long getD_HUITIANRQ() {
        return D_HUITIANRQ;
    }

    public void setD_HUITIANRQ(long d_HUITIANRQ) {
        D_HUITIANRQ = d_HUITIANRQ;
    }

    public String getS_HUITIANR() {
        return S_HUITIANR;
    }

    public void setS_HUITIANR(String s_HUITIANR) {
        S_HUITIANR = s_HUITIANR;
    }

    public String getS_FUHECM() {
        return S_FUHECM;
    }

    public void setS_FUHECM(String s_FUHECM) {
        S_FUHECM = s_FUHECM;
    }

    public int getI_QUERENJG() {
        return I_QUERENJG;
    }

    public void setI_QUERENJG(int i_QUERENJG) {
        I_QUERENJG = i_QUERENJG;
    }

    public int getI_YANGHUGDLX() {
        return I_YANGHUGDLX;
    }

    public void setI_YANGHUGDLX(int i_YANGHUGDLX) {
        I_YANGHUGDLX = i_YANGHUGDLX;
    }

    public String getS_YANGHUGD() {
        return S_YANGHUGD;
    }

    public void setS_YANGHUGD(String s_YANGHUGD) {
        S_YANGHUGD = s_YANGHUGD;
    }

    public int getI_ZHANGWUCLLX() {
        return I_ZHANGWUCLLX;
    }

    public void setI_ZHANGWUCLLX(int i_ZHANGWUCLLX) {
        I_ZHANGWUCLLX = i_ZHANGWUCLLX;
    }

    public String getS_ZHANGWUCL() {
        return S_ZHANGWUCL;
    }

    public void setS_ZHANGWUCL(String s_ZHANGWUCL) {
        S_ZHANGWUCL = s_ZHANGWUCL;
    }

    public int getI_ZuoFei() {
        return I_ZuoFei;
    }

    public void setI_ZuoFei(int i_ZuoFei) {
        I_ZuoFei = i_ZuoFei;
    }

    public int getI_YouZeRTS() {
        return I_YouZeRTS;
    }

    public void setI_YouZeRTS(int i_YouZeRTS) {
        I_YouZeRTS = i_YouZeRTS;
    }

    public String getS_CHULILB() {
        return S_CHULILB;
    }

    public void setS_CHULILB(String s_CHULILB) {
        S_CHULILB = s_CHULILB;
    }

    public String getS_CHULINR() {
        return S_CHULINR;
    }

    public void setS_CHULINR(String s_CHULINR) {
        S_CHULINR = s_CHULINR;
    }

    public String getS_FANSHENGYY() {
        return S_FANSHENGYY;
    }

    public void setS_FANSHENGYY(String s_FANSHENGYY) {
        S_FANSHENGYY = s_FANSHENGYY;
    }

    public String getS_JIEJUECS() {
        return S_JIEJUECS;
    }

    public void setS_JIEJUECS(String s_JIEJUECS) {
        S_JIEJUECS = s_JIEJUECS;
    }

    public String getS_CHULIJG() {
        return S_CHULIJG;
    }

    public void setS_CHULIJG(String s_CHULIJG) {
        S_CHULIJG = s_CHULIJG;
    }

    public String getS_HUITIANBZ() {
        return S_HUITIANBZ;
    }

    public void setS_HUITIANBZ(String s_HUITIANBZ) {
        S_HUITIANBZ = s_HUITIANBZ;
    }

    public String getS_CHACUOR() {
        return S_CHACUOR;
    }

    public void setS_CHACUOR(String s_CHACUOR) {
        S_CHACUOR = s_CHACUOR;
    }

    public String getI_FENZHI() {
        return I_FENZHI;
    }

    public void setI_FENZHI(String i_FENZHI) {
        I_FENZHI = i_FENZHI;
    }

    public String getS_CHAZUOBZ() {
        return S_CHAZUOBZ;
    }

    public void setS_CHAZUOBZ(String s_CHAZUOBZ) {
        S_CHAZUOBZ = s_CHAZUOBZ;
    }

    public String getI_FUCHAOZT() {
        return I_FUCHAOZT;
    }

    public void setI_FUCHAOZT(String i_FUCHAOZT) {
        I_FUCHAOZT = i_FUCHAOZT;
    }

    public String getS_LIUTIAOBH() {
        return S_LIUTIAOBH;
    }

    public void setS_LIUTIAOBH(String s_LIUTIAOBH) {
        S_LIUTIAOBH = s_LIUTIAOBH;
    }

    public String getS_GONGDANLX() {
        return S_GONGDANLX;
    }

    public void setS_GONGDANLX(String s_GONGDANLX) {
        S_GONGDANLX = s_GONGDANLX;
    }

    public String getS_SANLAIFH() {
        return S_SANLAIFH;
    }

    public void setS_SANLAIFH(String s_SANLAIFH) {
        S_SANLAIFH = s_SANLAIFH;
    }

    public int getI_YUANHSHICM() {
        return I_YUANHSHICM;
    }

    public void setI_YUANHSHICM(int i_YUANHSHICM) {
        I_YUANHSHICM = i_YUANHSHICM;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public String getS_DZ() {
        return S_DZ;
    }

    public void setS_DZ(String s_DZ) {
        S_DZ = s_DZ;
    }

    public static FuHeHTEntity fromJSON(JSONObject object) {
        FuHeHTEntity htEntity = new FuHeHTEntity();
        htEntity.setID(object.optInt("iD"));
        htEntity.setS_GONGDANBH(object.optString("s_GONGDANBH"));
        htEntity.setD_HUITIANRQ(object.optLong("d_HUITIANRQ"));
        htEntity.setS_HUITIANR(object.optString("s_HUITIANR"));
        htEntity.setS_FUHECM(object.optString("s_FUHECM"));
        htEntity.setI_QUERENJG(object.optInt("i_QUERENJG"));
        htEntity.setI_YANGHUGDLX(object.optInt("i_YANGHUGDLX"));
        htEntity.setS_YANGHUGD(object.optString("s_YANGHUGD"));
        htEntity.setI_ZHANGWUCLLX(object.optInt("i_ZHANGWUCLLX"));
        htEntity.setS_ZHANGWUCL(object.optString("s_ZHANGWUCL"));
        htEntity.setI_ZuoFei(object.optInt("i_ZuoFei"));
        htEntity.setI_YouZeRTS(object.optInt("i_YouZeRTS"));
        htEntity.setS_CHULILB(object.optString("s_CHULILB"));
        htEntity.setS_CHULINR(object.optString("s_CHULINR"));
        htEntity.setS_FANSHENGYY(object.optString("s_FANSHENGYY"));
        htEntity.setS_JIEJUECS(object.optString("s_JIEJUECS"));
        htEntity.setS_CHULIJG(object.optString("s_CHULIJG"));
        htEntity.setS_HUITIANBZ(object.optString("s_HUITIANBZ"));
        htEntity.setS_CHACUOR(object.optString("s_CHACUOR"));
        htEntity.setI_FENZHI(object.optString("i_FENZHI"));
        htEntity.setS_CHAZUOBZ(object.optString("s_CHAZUOBZ"));
        htEntity.setI_FUCHAOZT(object.optString("i_FUCHAOZT"));
        htEntity.setS_LIUTIAOBH(object.optString("S_LIUTIAOBH"));
        htEntity.setS_GONGDANLX(object.optString("s_GONGDANLX"));
        htEntity.setS_SANLAIFH(object.optString("s_SANLAIFH"));
        htEntity.setI_YUANHSHICM(object.optInt("i_YUANHSHICM"));
        htEntity.setS_CID(object.optString("s_CID"));
        htEntity.setS_DZ(object.optString("s_DZ"));

        return htEntity;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<FuHeHTEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<FuHeHTEntity> list = new ArrayList<FuHeHTEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            FuHeHTEntity entity = FuHeHTEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<FuHeHTEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (FuHeHTEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(FuHeHTEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", entity.getID());
            obj.put("S_GONGDANBH", entity.getS_GONGDANBH());
            obj.put("D_HUITIANRQ", entity.getD_HUITIANRQ());
            obj.put("S_HUITIANR", entity.getS_HUITIANR());
            obj.put("S_FUHECM", entity.getS_FUHECM());
            obj.put("I_QUERENJG", entity.getI_QUERENJG());
            obj.put("I_YANGHUGDLX", entity.getI_YANGHUGDLX());
            obj.put("S_YANGHUGD", entity.getS_YANGHUGD());
            obj.put("I_ZHANGWUCLLX", entity.getI_ZHANGWUCLLX());
            obj.put("S_ZHANGWUCL", entity.getS_ZHANGWUCL());
            obj.put("I_ZuoFei", entity.getI_ZuoFei());
            obj.put("I_YouZeRTS", entity.getI_YouZeRTS());
            obj.put("S_CHULILB", entity.getS_CHULILB());
            obj.put("S_CHULINR", entity.getS_CHULINR());
            obj.put("S_FANSHENGYY", entity.getS_FANSHENGYY());
            obj.put("S_JIEJUECS", entity.getS_JIEJUECS());
            obj.put("S_CHULIJG", entity.getS_CHULIJG());
            obj.put("S_HUITIANBZ", entity.getS_HUITIANBZ());
            obj.put("S_CHACUOR", entity.getS_CHACUOR());
            obj.put("I_FENZHI", entity.getI_FENZHI());
            obj.put("S_CHAZUOBZ", entity.getS_CHAZUOBZ());
            obj.put("I_FUCHAOZT", entity.getI_FUCHAOZT());
            obj.put("S_LIUTIAOBH", entity.getS_LIUTIAOBH());
            obj.put("S_GONGDANLX", entity.getS_GONGDANLX());
            obj.put("S_SANLAIFH", entity.getS_SANLAIFH());
            obj.put("I_YUANHSHICM", entity.getI_YUANHSHICM());
            obj.put("S_CID", entity.getS_CID());
            obj.put("S_DZ", entity.getS_DZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", getID());
            obj.put("S_GONGDANBH", getS_GONGDANBH());
            obj.put("D_HUITIANRQ", getD_HUITIANRQ());
            obj.put("S_HUITIANR", getS_HUITIANR());
            obj.put("S_FUHECM", getS_FUHECM());
            obj.put("I_QUERENJG", getI_QUERENJG());
            obj.put("I_YANGHUGDLX", getI_YANGHUGDLX());
            obj.put("S_YANGHUGD", getS_YANGHUGD());
            obj.put("I_ZHANGWUCLLX", getI_ZHANGWUCLLX());
            obj.put("S_ZHANGWUCL", getS_ZHANGWUCL());
            obj.put("I_ZuoFei", getI_ZuoFei());
            obj.put("I_YouZeRTS", getI_YouZeRTS());
            obj.put("S_CHULILB", getS_CHULILB());
            obj.put("S_CHULINR", getS_CHULINR());
            obj.put("S_FANSHENGYY", getS_FANSHENGYY());
            obj.put("S_JIEJUECS", getS_JIEJUECS());
            obj.put("S_CHULIJG", getS_CHULIJG());
            obj.put("S_HUITIANBZ", getS_HUITIANBZ());
            obj.put("S_CHACUOR", getS_CHACUOR());
            obj.put("I_FENZHI", getI_FENZHI());
            obj.put("S_CHAZUOBZ", getS_CHAZUOBZ());
            obj.put("I_FUCHAOZT", getI_FUCHAOZT());
            obj.put("S_LIUTIAOBH", getS_LIUTIAOBH());
            obj.put("S_GONGDANLX", getS_GONGDANLX());
            obj.put("S_SANLAIFH", getS_SANLAIFH());
            obj.put("I_YUANHSHICM", getI_YUANHSHICM());
            obj.put("S_CID", getS_CID());
            obj.put("S_DZ", getS_DZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
