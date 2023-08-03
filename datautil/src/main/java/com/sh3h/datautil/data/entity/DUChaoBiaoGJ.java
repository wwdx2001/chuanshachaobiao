package com.sh3h.datautil.data.entity;

/**
 * Created by zhangzhe on 2015/12/1.
 */
public class DUChaoBiaoGJ {

    private int ID;

    private String S_CH;

    private String S_CID;

    private long D_CHAOBIAOSJ;

    private int I_TYPE;

    private String S_X;

    private String S_Y;

    private String S_ACCOUNT;

    private int I_RENWUBH;


    public DUChaoBiaoGJ() {
    }

    public DUChaoBiaoGJ(long d_CHAOBIAOSJ, int i_TYPE, int ID, String s_ACCOUNT, String s_CH, String s_CID, String s_X, String s_Y,int i_RENWUBH) {
        D_CHAOBIAOSJ = d_CHAOBIAOSJ;
        I_TYPE = i_TYPE;
        this.ID = ID;
        S_ACCOUNT = s_ACCOUNT;
        S_CH = s_CH;
        S_CID = s_CID;
        S_X = s_X;
        S_Y = s_Y;
        I_RENWUBH = i_RENWUBH;
    }


    public int getI_RENWUBH() {
        return I_RENWUBH;
    }

    public void setI_RENWUBH(int i_RENWUBH) {
        I_RENWUBH = i_RENWUBH;
    }

    public long getD_CHAOBIAOSJ() {
        return D_CHAOBIAOSJ;
    }

    public void setD_CHAOBIAOSJ(long d_CHAOBIAOSJ) {
        D_CHAOBIAOSJ = d_CHAOBIAOSJ;
    }

    public int getI_TYPE() {
        return I_TYPE;
    }

    public void setI_TYPE(int i_TYPE) {
        I_TYPE = i_TYPE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getS_ACCOUNT() {
        return S_ACCOUNT;
    }

    public void setS_ACCOUNT(String s_ACCOUNT) {
        S_ACCOUNT = s_ACCOUNT;
    }

    public String getS_CH() {
        return S_CH;
    }

    public void setS_CH(String s_CH) {
        S_CH = s_CH;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public String getS_X() {
        return S_X;
    }

    public void setS_X(String s_X) {
        S_X = s_X;
    }

    public String getS_Y() {
        return S_Y;
    }

    public void setS_Y(String s_Y) {
        S_Y = s_Y;
    }
}
