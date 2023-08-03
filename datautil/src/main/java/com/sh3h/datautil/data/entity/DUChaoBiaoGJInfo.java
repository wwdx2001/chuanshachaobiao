package com.sh3h.datautil.data.entity;

/**
 * Created by zhangzhe on 2015/12/1.
 */
public class DUChaoBiaoGJInfo extends DURequest {

    private int I_Type;

    private String S_Account;

    private String S_CH;

    private int I_RenWUBH;

    private DUChaoBiaoGJ duChaoBiaoGJ;



    public DUChaoBiaoGJInfo(DUChaoBiaoGJ duChaoBiaoGJ,IDUHandler duHandler) {
        this.duChaoBiaoGJ = duChaoBiaoGJ;
        this.duHandler = duHandler;
    }

    public DUChaoBiaoGJInfo(String s_Account,int i_Type,IDUHandler duHandler) {
        S_Account = s_Account;
        I_Type = i_Type;
        this.duHandler = duHandler;
    }

    public DUChaoBiaoGJInfo(int i_RenWUBH, String s_Account, String s_CH,int i_Type,IDUHandler duHandler) {
        I_RenWUBH = i_RenWUBH;
        S_Account = s_Account;
        S_CH = s_CH;
        I_Type = i_Type;
        this.duHandler = duHandler;
    }

    public DUChaoBiaoGJ getDuChaoBiaoGJ() {
        return duChaoBiaoGJ;
    }

    public void setDuChaoBiaoGJ(DUChaoBiaoGJ duChaoBiaoGJ) {
        this.duChaoBiaoGJ = duChaoBiaoGJ;
    }

    public int getI_RenWUBH() {
        return I_RenWUBH;
    }

    public void setI_RenWUBH(int i_RenWUBH) {
        I_RenWUBH = i_RenWUBH;
    }

    public String getS_Account() {
        return S_Account;
    }

    public void setS_Account(String s_Account) {
        S_Account = s_Account;
    }

    public String getS_CH() {
        return S_CH;
    }

    public void setS_CH(String s_CH) {
        S_CH = s_CH;
    }

    public int getI_Type() {
        return I_Type;
    }

    public void setI_Type(int i_Type) {
        I_Type = i_Type;
    }
}
