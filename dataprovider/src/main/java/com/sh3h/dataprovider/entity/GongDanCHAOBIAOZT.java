package com.sh3h.dataprovider.entity;

public class GongDanCHAOBIAOZT {

    /**
     * 自增长ID
     */
    private int ID;
    /**
     * 抄表状态编码
     */
    private int I_ZHUANGTAIBM;
    /**
     * 抄表分类编码
     */
    private int I_ZHUANGTAIFLBM;
    /**
     * 水量算法编码
     */
    private int I_SHUILIANGSFBM;
    /**
     * 抄见标志(0不抄见，1抄见)
     */
    private int I_CHAOJIANBZ;
    /**
     * 抄表标志(0不抄表，1抄表)
     */
    private int I_CHAOBIAOBZ;
    /**
     * 判断量高量低标志(0不判断量高量低，1 判断量高量低）
     */
    private int I_LIANGGAOLD;
    /**
     * 提示需要登记养户（0不需要养护等级，1需要养护等级）
     */
    private int I_YANGHU;
    /**
     * 提示转外复（0不提示转外复，1提示转外复
     */
    private int I_WAIFU;
    /**
     * 记录状态(-1作废，0正常）
     */
    private int I_JLZT;
    /**
     * 操作时间
     */
    private long D_CAOZUOSJ;
    /**
     * 操作人
     */
    private String S_CAOZUOR;
    /**
     * 状态名称
     */
    private String S_ZHUANGTAIMC;
    /**
     * PC快捷键
     */
    private String S_KUAIJIEJ_PC;
    /**
     * 备注
     */
    private String S_BEIZHU;


    public String getS_BEIZHU() {
        return S_BEIZHU;
    }

    public void setS_BEIZHU(String s_BEIZHU) {
        S_BEIZHU = s_BEIZHU;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getI_ZHUANGTAIBM() {
        return I_ZHUANGTAIBM;
    }

    public void setI_ZHUANGTAIBM(int i_ZHUANGTAIBM) {
        I_ZHUANGTAIBM = i_ZHUANGTAIBM;
    }

    public int getI_ZHUANGTAIFLBM() {
        return I_ZHUANGTAIFLBM;
    }

    public void setI_ZHUANGTAIFLBM(int i_ZHUANGTAIFLBM) {
        I_ZHUANGTAIFLBM = i_ZHUANGTAIFLBM;
    }

    public int getI_SHUILIANGSFBM() {
        return I_SHUILIANGSFBM;
    }

    public void setI_SHUILIANGSFBM(int i_SHUILIANGSFBM) {
        I_SHUILIANGSFBM = i_SHUILIANGSFBM;
    }

    public int getI_CHAOJIANBZ() {
        return I_CHAOJIANBZ;
    }

    public void setI_CHAOJIANBZ(int i_CHAOJIANBZ) {
        I_CHAOJIANBZ = i_CHAOJIANBZ;
    }

    public int getI_CHAOBIAOBZ() {
        return I_CHAOBIAOBZ;
    }

    public void setI_CHAOBIAOBZ(int i_CHAOBIAOBZ) {
        I_CHAOBIAOBZ = i_CHAOBIAOBZ;
    }

    public int getI_LIANGGAOLD() {
        return I_LIANGGAOLD;
    }

    public void setI_LIANGGAOLD(int i_LIANGGAOLD) {
        I_LIANGGAOLD = i_LIANGGAOLD;
    }

    public int getI_YANGHU() {
        return I_YANGHU;
    }

    public void setI_YANGHU(int i_YANGHU) {
        I_YANGHU = i_YANGHU;
    }

    public int getI_WAIFU() {
        return I_WAIFU;
    }

    public void setI_WAIFU(int i_WAIFU) {
        I_WAIFU = i_WAIFU;
    }

    public int getI_JLZT() {
        return I_JLZT;
    }

    public void setI_JLZT(int i_JLZT) {
        I_JLZT = i_JLZT;
    }

    public long getD_CAOZUOSJ() {
        return D_CAOZUOSJ;
    }

    public void setD_CAOZUOSJ(long d_CAOZUOSJ) {
        D_CAOZUOSJ = d_CAOZUOSJ;
    }

    public String getS_CAOZUOR() {
        return S_CAOZUOR;
    }

    public void setS_CAOZUOR(String s_CAOZUOR) {
        S_CAOZUOR = s_CAOZUOR;
    }

    public String getS_ZHUANGTAIMC() {
        return S_ZHUANGTAIMC;
    }

    public void setS_ZHUANGTAIMC(String s_ZHUANGTAIMC) {
        S_ZHUANGTAIMC = s_ZHUANGTAIMC;
    }

    public String getS_KUAIJIEJ_PC() {
        return S_KUAIJIEJ_PC;
    }

    public void setS_KUAIJIEJ_PC(String s_KUAIJIEJ_PC) {
        S_KUAIJIEJ_PC = s_KUAIJIEJ_PC;
    }
}
