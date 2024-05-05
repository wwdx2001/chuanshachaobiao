package com.example.dataprovider3.entity;

public class CallForPaymentSearchBean {


    /**
     * S_CID : 10000231315
     * S_HM : null
     * S_DZ : 宏雅路151弄25号501室
     * S_SHOUJI : null
     * D_ZJSLSJ : Date(1714286713000)
     */

    private String S_CID;
    private String S_HM;
    private String S_DZ;
    private String S_SHOUJI;
    private String D_ZJSLSJ;
    private boolean isCheck;

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String S_CID) {
        this.S_CID = S_CID;
    }

    public String getS_HM() {
        return S_HM;
    }

    public void setS_HM(String S_HM) {
        this.S_HM = S_HM;
    }

    public String getS_DZ() {
        return S_DZ;
    }

    public void setS_DZ(String S_DZ) {
        this.S_DZ = S_DZ;
    }

    public String getS_SHOUJI() {
        return S_SHOUJI;
    }

    public void setS_SHOUJI(String S_SHOUJI) {
        this.S_SHOUJI = S_SHOUJI;
    }

    public String getD_ZJSLSJ() {
        return D_ZJSLSJ;
    }

    public void setD_ZJSLSJ(String D_ZJSLSJ) {
        this.D_ZJSLSJ = D_ZJSLSJ;
    }
}
