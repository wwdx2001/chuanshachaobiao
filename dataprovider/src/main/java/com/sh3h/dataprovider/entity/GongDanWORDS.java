package com.sh3h.dataprovider.entity;

public class GongDanWORDS {

    /**
     * 自增长编号
     */
    private int ID;
    /**
     * 词语编号
     */
    private int I_WORDSID;
    /**
     * 词语内容
     */
    private String S_WORDSCONTENT;
    /**
     * 词语值
     */
    private String S_WORDSVALUE;
    /**
     * 词语备注
     */
    private String S_WORDSREMARK;
    /**
     * 隶属词语编号
     */
    private int I_BELONGID;

    /**
     * 排序编号
     */
    private int I_SORTID;
    /**
     * 是否激活
     */
    private int I_ISACTIVE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getI_WORDSID() {
        return I_WORDSID;
    }

    public void setI_WORDSID(int i_WORDSID) {
        I_WORDSID = i_WORDSID;
    }

    public String getS_WORDSCONTENT() {
        return S_WORDSCONTENT;
    }

    public void setS_WORDSCONTENT(String s_WORDSCONTENT) {
        S_WORDSCONTENT = s_WORDSCONTENT;
    }

    public String getS_WORDSVALUE() {
        return S_WORDSVALUE;
    }

    public void setS_WORDSVALUE(String s_WORDSVALUE) {
        S_WORDSVALUE = s_WORDSVALUE;
    }

    public String getS_WORDSREMARK() {
        return S_WORDSREMARK;
    }

    public void setS_WORDSREMARK(String s_WORDSREMARK) {
        S_WORDSREMARK = s_WORDSREMARK;
    }

    public int getI_BELONGID() {
        return I_BELONGID;
    }

    public void setI_BELONGID(int i_BELONGID) {
        I_BELONGID = i_BELONGID;
    }

    public int getI_SORTID() {
        return I_SORTID;
    }

    public void setI_SORTID(int i_SORTID) {
        I_SORTID = i_SORTID;
    }

    public int getI_ISACTIVE() {
        return I_ISACTIVE;
    }

    public void setI_ISACTIVE(int i_ISACTIVE) {
        I_ISACTIVE = i_ISACTIVE;
    }
}
