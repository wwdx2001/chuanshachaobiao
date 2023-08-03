package com.sh3h.dataprovider.entity;

public class GongDanYONGHUPZ {

    /**
     * 自增长编号
     */
    private int ID;
    /**
     * 参数编号
     */
    private int I_CANSHUBH;
    /**
     * 层次关系 大类为0
     */
    private int I_LISHUID;
    /**
     * 参数值 存数据库
     */
    private String S_CANSHUZ;
    /**
     * 参数名称 显示用
     */
    private String S_CANSHUMC;
    /**
     * 排序
     */
    private int I_PAIXU;
    /**
     * 备注
     */
    private String S_BEIZHU;
    /**
     * -1作废 1成功
     */
    private int I_JLZT;
    /**
     * 操作人
     */
    private String S_CAOZUOR;
    /**
     * 操作时间
     */
    private long D_CAOZUOSJ;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getI_CANSHUBH() {
        return I_CANSHUBH;
    }

    public void setI_CANSHUBH(int i_CANSHUBH) {
        I_CANSHUBH = i_CANSHUBH;
    }

    public int getI_LISHUID() {
        return I_LISHUID;
    }

    public void setI_LISHUID(int i_LISHUID) {
        I_LISHUID = i_LISHUID;
    }

    public String getS_CANSHUZ() {
        return S_CANSHUZ;
    }

    public void setS_CANSHUZ(String s_CANSHUZ) {
        S_CANSHUZ = s_CANSHUZ;
    }

    public String getS_CANSHUMC() {
        return S_CANSHUMC;
    }

    public void setS_CANSHUMC(String s_CANSHUMC) {
        S_CANSHUMC = s_CANSHUMC;
    }

    public int getI_PAIXU() {
        return I_PAIXU;
    }

    public void setI_PAIXU(int i_PAIXU) {
        I_PAIXU = i_PAIXU;
    }

    public String getS_BEIZHU() {
        return S_BEIZHU;
    }

    public void setS_BEIZHU(String s_BEIZHU) {
        S_BEIZHU = s_BEIZHU;
    }

    public int getI_JLZT() {
        return I_JLZT;
    }

    public void setI_JLZT(int i_JLZT) {
        I_JLZT = i_JLZT;
    }

    public String getS_CAOZUOR() {
        return S_CAOZUOR;
    }

    public void setS_CAOZUOR(String s_CAOZUOR) {
        S_CAOZUOR = s_CAOZUOR;
    }

    public long getD_CAOZUOSJ() {
        return D_CAOZUOSJ;
    }

    public void setD_CAOZUOSJ(long d_CAOZUOSJ) {
        D_CAOZUOSJ = d_CAOZUOSJ;
    }
}
