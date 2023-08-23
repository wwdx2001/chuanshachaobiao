package com.sh3h.serverprovider.entity;

import java.util.List;

/**
 * 外复历史Bean
 *
 * @author Administrator
 */
public class WaiFuHistoryBean {

    //    外复日期、类型、原因、处理结果

    /**
     * MsgCode : 00
     * MsgInfo : 成功
     * data : [{"CID":"948991108","WAIFUYY":"拆表-其他原因","WAIFURQ":"","LEIXING":"换表登记","WAIFUJG":""}]
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
         * WAIFUYY : 拆表-其他原因
         * WAIFURQ :
         * LEIXING : 换表登记
         * WAIFUJG :
         */

        private String CID;
        private String WAIFUYY;
        private String WAIFURQ;
        private String LEIXING;
        private String WAIFUJG;

        public String getCID() {
            return CID;
        }

        public void setCID(String CID) {
            this.CID = CID;
        }

        public String getWAIFUYY() {
            return WAIFUYY;
        }

        public void setWAIFUYY(String WAIFUYY) {
            this.WAIFUYY = WAIFUYY;
        }

        public String getWAIFURQ() {
            return WAIFURQ;
        }

        public void setWAIFURQ(String WAIFURQ) {
            this.WAIFURQ = WAIFURQ;
        }

        public String getLEIXING() {
            return LEIXING;
        }

        public void setLEIXING(String LEIXING) {
            this.LEIXING = LEIXING;
        }

        public String getWAIFUJG() {
            return WAIFUJG;
        }

        public void setWAIFUJG(String WAIFUJG) {
            this.WAIFUJG = WAIFUJG;
        }
    }

}
