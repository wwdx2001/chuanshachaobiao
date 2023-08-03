package com.sh3h.dataprovider.entity;


public class ChaoBiaoGJ {
    private int _id;
    private String _ch;
    private String _cid;
    private long _chaobiaosj;
    private int _type; // 0: chaobiao; 1: yanchi; 2: yanchi chaobiao
    private String _x;
    private String _y;
    private int mForeignIndex; // only store in memory, not in database

    private String S_ACCOUNT;

    private int I_RENWUBH;



    public String getS_ACCOUNT() {
        return S_ACCOUNT;
    }

    public void setS_ACCOUNT(String s_ACCOUNT) {
        S_ACCOUNT = s_ACCOUNT;
    }

    public int getI_RENWUBH() {
        return I_RENWUBH;
    }

    public void setI_RENWUBH(int i_RENWUBH) {
        I_RENWUBH = i_RENWUBH;
    }

    public ChaoBiaoGJ() {}

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param id
     *            the _id to set
     */
    public void setId(int id) {
        _id = id;
    }

    /**
     * @return the _ch
     */
    public String getCH() {
        return _ch;
    }

    /**
     * @param ch
     *            the _cH to set
     */
    public void setCH(String ch) {
        _ch = ch;
    }

    public String getCID() {
        return _cid;
    }

    public void setCID(String cid) {
        _cid = cid;
    }

    public long getChaoBiaoSJ() {
        return _chaobiaosj;
    }

    public void setChaoBiaoSJ(long chaobiaosj) {
        _chaobiaosj = chaobiaosj;
    }

    public int getType() {
        return _type;
    }

    public void setType(int type) {
        _type = type;
    }

    /**
     * @return the _x
     */
    public String getX() {
        return _x;
    }

    /**
     * @param x
     *            the _x to set
     */
    public void setX(String x) {
        _x = x;
    }

    /**
     * @return the _y
     */
    public String getY() {
        return _y;
    }

    /**
     * @param y
     *            the _y to set
     */
    public void setY(String y) {
        _y = y;
    }

    public int getForeignIndex() {
        return mForeignIndex;
    }

    public void setForeignIndex(int foreignIndex) {
        mForeignIndex = foreignIndex;
    }
}
