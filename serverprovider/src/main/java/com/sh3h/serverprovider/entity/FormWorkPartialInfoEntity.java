package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FormWorkPartialInfoEntity {
    // ID
    private int _ID;
    // 任务编号
    private int _RENWUBH;
    // 用户号
    private String _CID;
    // 条形码
    private String _SHUIBIAOTXM;
    // 表务工单类型(belongid=1301)(1故障换表；2定期换表；
    // 3注销拆表；4暂缓供水拆表；5欠费拆表；
    // 6暂缓拆表复装；7验表)
    private int _BIAOWUGDLX;
    // 状态(belongid=1302)(-1取消，0审批，1登记，2派工，3打印，4回填)
    private int _ZT;
    // 施工日期
    private long _SHIGONGRQ;
    // 施工人(派单接收人）
    private String _SHIGONGR;
    // 单据回填结果(S_Words103)(1正常完成-1无法完成)
    private int _JIEGUO;
    // 回填日期
    private long _HUITIANRQ;
    // 回填人
    private String _HUITIANR;
    // 旧表读数
    private int _JIUBIAODS;
    // 新表条形码
    private String _XINBIAOTXM;
    // 新表读数
    private int _XINBIAODS;
    // x坐标
    private String _X1;
    // y坐标
    private String _Y1;
    // 客户签名（图片名称）
    private String _KeHuQM;
    // 照片名称（name1,name2,...）
    private String _ZhaoPianMC;
    /// <summary>
    /// 检测结果
    /// </summary>
    private String _JIANCEJG;

    public FormWorkPartialInfoEntity() {

    }

    public int getID() {
        return _ID;
    }

    public void setID(int ID) {
        _ID = ID;
    }

    public int getRENWUBH() {
        return _RENWUBH;
    }

    public void setRENWUBH(int RENWUBH) {
        _RENWUBH = RENWUBH;
    }

    public String getCID() {
        return _CID;
    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public String getSHUIBIAOTXM() {
        return _SHUIBIAOTXM;
    }

    public void setSHUIBIAOTXM(String SHUIBIAOTXM) {
        _SHUIBIAOTXM = SHUIBIAOTXM;
    }

    public int getBIAOWUGDLX() {
        return _BIAOWUGDLX;
    }

    public void setBIAOWUGDLX(int BIAOWUGDLX) {
        _BIAOWUGDLX = BIAOWUGDLX;
    }

    public int getZT() {
        return _ZT;
    }

    public void setZT(int ZT) {
        _ZT = ZT;
    }

    public long getSHIGONGRQ() {
        return _SHIGONGRQ;
    }

    public void setSHIGONGRQ(long SHIGONGRQ) {
        _SHIGONGRQ = SHIGONGRQ;
    }

    public String getSHIGONGR() {
        return _SHIGONGR;
    }

    public void setSHIGONGR(String SHIGONGR) {
        _SHIGONGR = SHIGONGR;
    }

    public int getJIEGUO() {
        return _JIEGUO;
    }

    public void setJIEGUO(int JIEGUO) {
        _JIEGUO = JIEGUO;
    }

    public long getHUITIANRQ() {
        return _HUITIANRQ;
    }

    public void setHUITIANRQ(long HUITIANRQ) {
        _HUITIANRQ = HUITIANRQ;
    }

    public String getHUITIANR() {
        return _HUITIANR;
    }

    public void setHUITIANR(String HUITIANR) {
        _HUITIANR = HUITIANR;
    }

    public int getJIUBIAODS() {
        return _JIUBIAODS;
    }

    public void setJIUBIAODS(int JIUBIAODS) {
        _JIUBIAODS = JIUBIAODS;
    }

    public String getXINBIAOTXM() {
        return _XINBIAOTXM;
    }

    public void setXINBIAOTXM(String XINBIAOTXM) {
        _XINBIAOTXM = XINBIAOTXM;
    }

    public int getXINBIAODS() {
        return _XINBIAODS;
    }

    public void setXINBIAODS(int XINBIAODS) {
        _XINBIAODS = XINBIAODS;
    }

    public String getX1() {
        return _X1;
    }

    public void setX1(String X1) {
        _X1 = X1;
    }

    public String getY1() {
        return _Y1;
    }

    public void setY1(String Y1) {
        _Y1 = Y1;
    }

    public String getKeHuQM() {
        return _KeHuQM;
    }

    public void setKeHuQM(String KeHuQM) {
        _KeHuQM = KeHuQM;
    }

    public String getZhaoPianMC() {
        return _ZhaoPianMC;
    }

    public void setZhaoPianMC(String ZhaoPianMC) {
        _ZhaoPianMC = ZhaoPianMC;
    }

    public String getJIANCEJG() {
        return _JIANCEJG;
    }

    public void setJIANCEJG(String JIANCEJG) {
        _JIANCEJG = JIANCEJG;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("ID", getID());
            obj.put("I_RENWUBH", getRENWUBH());
            obj.put("S_CID", getCID());
            obj.put("S_SHUIBIAOTXM", getSHUIBIAOTXM());
            obj.put("I_BIAOWUGDLX", getBIAOWUGDLX());
            obj.put("I_ZT", getZT());
            obj.put("D_SHIGONGRQ", getSHIGONGRQ());
            obj.put("S_SHIGONGR", getSHIGONGR());
            obj.put("I_JIEGUO", getJIEGUO());
            obj.put("D_HUITIANRQ", getHUITIANRQ());
            obj.put("S_HUITIANR", getHUITIANR());
            obj.put("I_JIUBIAODS", getJIUBIAODS());
            obj.put("S_XINBIAOTXM", getXINBIAOTXM());
            obj.put("I_XINBIAODS", getXINBIAODS());
            obj.put("S_X1", getX1());
            obj.put("S_Y1", getY1());
            obj.put("S_KeHuQM", getKeHuQM());
            obj.put("S_ZhaoPianMC", getZhaoPianMC());
            obj.put("S_JIANCEJG", getJIANCEJG());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONObject toJSON(FormWorkPartialInfoEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("ID", entity.getID());
            obj.put("I_RENWUBH", entity.getRENWUBH());
            obj.put("S_CID", entity.getCID());
            obj.put("S_SHUIBIAOTXM", entity.getSHUIBIAOTXM());
            obj.put("I_BIAOWUGDLX", entity.getBIAOWUGDLX());
            obj.put("I_ZT", entity.getZT());
            obj.put("D_SHIGONGRQ", entity.getSHIGONGRQ());
            obj.put("S_SHIGONGR", entity.getSHIGONGR());
            obj.put("I_JIEGUO", entity.getJIEGUO());
            obj.put("D_HUITIANRQ", entity.getHUITIANRQ());
            obj.put("S_HUITIANR", entity.getHUITIANR());
            obj.put("I_JIUBIAODS", entity.getJIUBIAODS());
            obj.put("S_XINBIAOTXM", entity.getXINBIAOTXM());
            obj.put("I_XINBIAODS", entity.getXINBIAODS());
            obj.put("S_X1", entity.getX1());
            obj.put("S_Y1", entity.getY1());
            obj.put("S_KeHuQM", entity.getKeHuQM());
            obj.put("S_ZhaoPianMC", entity.getZhaoPianMC());
            obj.put("S_JIANCEJG", entity.getJIANCEJG());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONArray toJSONArray(List<FormWorkPartialInfoEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (FormWorkPartialInfoEntity entity : list) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }

        return array;
    }
}
