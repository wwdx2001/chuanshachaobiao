package com.sh3h.serverprovider.entity;

import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/12 15:06
 */
public class QianFeiXXBean {

    /**
     * MsgCode : 00
     * MsgInfo : 成功
     * data : [{"CID":"948991108","SHUIL":182,"YSJE":1180.5,"WEIYUEJ":2629.9,"ZHANGDANNY":"201507"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201207"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201209"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201210"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201208"},{"CID":"948991108","SHUIL":95,"YSJE":769.5,"WEIYUEJ":950,"ZHANGDANNY":"201202"},{"CID":"948991108","SHUIL":0,"YSJE":0,"WEIYUEJ":0,"ZHANGDANNY":"201201"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201203"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201206"},{"CID":"948991108","SHUIL":148,"YSJE":920.7,"WEIYUEJ":2138.6,"ZHANGDANNY":"201509"},{"CID":"948991108","SHUIL":60,"YSJE":482.1,"WEIYUEJ":600,"ZHANGDANNY":"201212"},{"CID":"948991108","SHUIL":60,"YSJE":464.6,"WEIYUEJ":600,"ZHANGDANNY":"201303"},{"CID":"948991108","SHUIL":60,"YSJE":446.5,"WEIYUEJ":600,"ZHANGDANNY":"201306"},{"CID":"948991108","SHUIL":60,"YSJE":434.8,"WEIYUEJ":600,"ZHANGDANNY":"201308"},{"CID":"948991108","SHUIL":193,"YSJE":1275.4,"WEIYUEJ":2788.8,"ZHANGDANNY":"201506"},{"CID":"948991108","SHUIL":118,"YSJE":0,"WEIYUEJ":0,"ZHANGDANNY":"201510"},{"CID":"948991108","SHUIL":199,"YSJE":1263.1,"WEIYUEJ":2875.5,"ZHANGDANNY":"201508"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201211"},{"CID":"948991108","SHUIL":60,"YSJE":476.2,"WEIYUEJ":600,"ZHANGDANNY":"201301"},{"CID":"948991108","SHUIL":60,"YSJE":469.8,"WEIYUEJ":600,"ZHANGDANNY":"201302"},{"CID":"948991108","SHUIL":60,"YSJE":458.7,"WEIYUEJ":600,"ZHANGDANNY":"201304"},{"CID":"948991108","SHUIL":60,"YSJE":452.9,"WEIYUEJ":600,"ZHANGDANNY":"201305"},{"CID":"948991108","SHUIL":60,"YSJE":481.4,"WEIYUEJ":867,"ZHANGDANNY":"201407"},{"CID":"948991108","SHUIL":60,"YSJE":449.8,"WEIYUEJ":867,"ZHANGDANNY":"201411"},{"CID":"948991108","SHUIL":60,"YSJE":441.2,"WEIYUEJ":600,"ZHANGDANNY":"201307"},{"CID":"948991108","SHUIL":0,"YSJE":0,"WEIYUEJ":0,"ZHANGDANNY":"201501"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201204"},{"CID":"948991108","SHUIL":60,"YSJE":486,"WEIYUEJ":600,"ZHANGDANNY":"201205"}]
     */

    private String MsgCode;
    private String MsgInfo;
    private List<DataBean> data;

    public String getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(String MsgCode) {
        this.MsgCode = MsgCode;
    }

    public String getMsgInfo() {
        return MsgInfo;
    }

    public void setMsgInfo(String MsgInfo) {
        this.MsgInfo = MsgInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * CID : 948991108
         * SHUIL : 182
         * YSJE : 1180.5
         * WEIYUEJ : 2629.9
         * ZHANGDANNY : 201507
         */

        private String CID;
        private int SHUIL;
        private double YSJE;
        private double WEIYUEJ;
        private String ZHANGDANNY;
        private double SHUIFEI;
        private double PAISHUIF;

        public double getSHUIFEI() {
            return SHUIFEI;
        }

        public void setSHUIFEI(double SHUIFEI) {
            this.SHUIFEI = SHUIFEI;
        }

        public double getPAISHUIF() {
            return PAISHUIF;
        }

        public void setPAISHUIF(double PAISHUIF) {
            this.PAISHUIF = PAISHUIF;
        }

        public String getCID() {
            return CID;
        }

        public void setCID(String CID) {
            this.CID = CID;
        }

        public int getSHUIL() {
            return SHUIL;
        }

        public void setSHUIL(int SHUIL) {
            this.SHUIL = SHUIL;
        }

        public double getYSJE() {
            return YSJE;
        }

        public void setYSJE(double YSJE) {
            this.YSJE = YSJE;
        }

        public double getWEIYUEJ() {
            return WEIYUEJ;
        }

        public void setWEIYUEJ(double WEIYUEJ) {
            this.WEIYUEJ = WEIYUEJ;
        }

        public String getZHANGDANNY() {
            return ZHANGDANNY;
        }

        public void setZHANGDANNY(String ZHANGDANNY) {
            this.ZHANGDANNY = ZHANGDANNY;
        }
    }
}
