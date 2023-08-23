package com.sh3h.serverprovider.entity;

import java.util.List;

public class ChaoBiaoHistoryBean {


    /**
     * MsgCode : 00
     * MsgInfo : 成功
     * data : [{"CID":"962348722","CHAOMA":6,"CHAOBIAOZTMC":"空屋      ","YONGSHUIL":0,"BEIZHU":"","CHAOBIAOID":50466049,"CHAOBIAONY":"201105"},{"CID":"962348722","CHAOMA":95,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":18,"BEIZHU":"","CHAOBIAOID":75755124,"CHAOBIAONY":"201309"},{"CID":"962348722","CHAOMA":140,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":24,"BEIZHU":"","CHAOBIAOID":78317850,"CHAOBIAONY":"201401"},{"CID":"962348722","CHAOMA":75,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":17,"BEIZHU":"","CHAOBIAOID":69450592,"CHAOBIAONY":"201211"},{"CID":"962348722","CHAOMA":156,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":16,"BEIZHU":"","CHAOBIAOID":79623209,"CHAOBIAONY":"201403"},{"CID":"962348722","CHAOMA":56,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":17,"BEIZHU":"","CHAOBIAOID":73202617,"CHAOBIAONY":"201305"},{"CID":"962348722","CHAOMA":116,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":21,"BEIZHU":"","CHAOBIAOID":77055255,"CHAOBIAONY":"201311"},{"CID":"962348722","CHAOMA":6,"CHAOBIAOZTMC":"J0空屋","YONGSHUIL":0,"BEIZHU":"","CHAOBIAOID":62147961,"CHAOBIAONY":"201111"},{"CID":"962348722","CHAOMA":31,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":13,"BEIZHU":"","CHAOBIAOID":65749461,"CHAOBIAONY":"201205"},{"CID":"962348722","CHAOMA":31,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":13,"BEIZHU":"","CHAOBIAOID":70673065,"CHAOBIAONY":"201301"},{"CID":"962348722","CHAOMA":177,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":21,"BEIZHU":"","CHAOBIAOID":80936570,"CHAOBIAONY":"201405"},{"CID":"962348722","CHAOMA":380,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":3,"BEIZHU":"1","CHAOBIAOID":82030914,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":390,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":10,"BEIZHU":"1","CHAOBIAOID":82030915,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":400,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":10,"BEIZHU":"1","CHAOBIAOID":82030920,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":412,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":12,"BEIZHU":"1","CHAOBIAOID":82030921,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":420,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":8,"BEIZHU":"1","CHAOBIAOID":82030922,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":424,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":4,"BEIZHU":"1","CHAOBIAOID":82030923,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":377,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":200,"BEIZHU":"1","CHAOBIAOID":81915791,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":444,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":20,"BEIZHU":"1","CHAOBIAOID":82034291,"CHAOBIAONY":"201409"},{"CID":"962348722","CHAOMA":6,"CHAOBIAOZTMC":"J0空屋","YONGSHUIL":0,"BEIZHU":"","CHAOBIAOID":63355431,"CHAOBIAONY":"201201"},{"CID":"962348722","CHAOMA":41,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":10,"BEIZHU":"","CHAOBIAOID":66977465,"CHAOBIAONY":"201207"},{"CID":"962348722","CHAOMA":6,"CHAOBIAOZTMC":"空屋      ","YONGSHUIL":0,"BEIZHU":"","CHAOBIAOID":51050131,"CHAOBIAONY":"201107"},{"CID":"962348722","CHAOMA":77,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":21,"BEIZHU":"","CHAOBIAOID":74482227,"CHAOBIAONY":"201307"},{"CID":"962348722","CHAOMA":6,"CHAOBIAOZTMC":"J0空屋","YONGSHUIL":0,"BEIZHU":"","CHAOBIAOID":60948949,"CHAOBIAONY":"201109"},{"CID":"962348722","CHAOMA":18,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":12,"BEIZHU":"","CHAOBIAOID":64540271,"CHAOBIAONY":"201203"},{"CID":"962348722","CHAOMA":58,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":17,"BEIZHU":"","CHAOBIAOID":68183013,"CHAOBIAONY":"201209"},{"CID":"962348722","CHAOMA":39,"CHAOBIAOZTMC":"Z0正常","YONGSHUIL":8,"BEIZHU":"","CHAOBIAOID":71930061,"CHAOBIAONY":"201303"}]
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
         * CID : 962348722
         * CHAOMA : 6
         * CHAOBIAOZTMC : 空屋
         * YONGSHUIL : 0
         * BEIZHU :
         * CHAOBIAOID : 50466049
         * CHAOBIAONY : 201105
         */

        private String CID;
        private int CHAOMA;
        private int realChaoMa;

        public int getRealChaoMa() {
            return realChaoMa;
        }

        public void setRealChaoMa(int realChaoMa) {
            this.realChaoMa = realChaoMa;
        }

        private String CHAOBIAOZTMC;
        private String CHAOBIAOZTMC_TWO;
        private int YONGSHUIL;
        private String BEIZHU;
        private int CHAOBIAOID;
        private String CHAOBIAONY;

        public String getCHAOBIAOZTMC_TWO() {
            return CHAOBIAOZTMC_TWO;
        }

        public void setCHAOBIAOZTMC_TWO(String CHAOBIAOZTMC_TWO) {
            this.CHAOBIAOZTMC_TWO = CHAOBIAOZTMC_TWO;
        }

        public String getCID() {
            return CID;
        }

        public void setCID(String CID) {
            this.CID = CID;
        }

        public int getCHAOMA() {
            return CHAOMA;
        }

        public void setCHAOMA(int CHAOMA) {
            this.CHAOMA = CHAOMA;
        }

        public String getCHAOBIAOZTMC() {
            return CHAOBIAOZTMC;
        }

        public void setCHAOBIAOZTMC(String CHAOBIAOZTMC) {
            this.CHAOBIAOZTMC = CHAOBIAOZTMC;
        }

        public int getYONGSHUIL() {
            return YONGSHUIL;
        }

        public void setYONGSHUIL(int YONGSHUIL) {
            this.YONGSHUIL = YONGSHUIL;
        }

        public String getBEIZHU() {
            return BEIZHU;
        }

        public void setBEIZHU(String BEIZHU) {
            this.BEIZHU = BEIZHU;
        }

        public int getCHAOBIAOID() {
            return CHAOBIAOID;
        }

        public void setCHAOBIAOID(int CHAOBIAOID) {
            this.CHAOBIAOID = CHAOBIAOID;
        }

        public String getCHAOBIAONY() {
            return CHAOBIAONY;
        }

        public void setCHAOBIAONY(String CHAOBIAONY) {
            this.CHAOBIAONY = CHAOBIAONY;
        }
    }
}
