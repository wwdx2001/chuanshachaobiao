package com.sh3h.serverprovider.entity;

import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/3/4 16:45
 */
public class DuShuEntity {

    /**
     * MsgCode : 00
     * MsgInfo : 成功
     * data : [{"DUSHU":735,"DUSHURQ":"2014-07-27 10:08:52"}]
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
         * DUSHU : 735
         * DUSHURQ : 2014-07-27 10:08:52
         */

        private int DUSHU;
        private String DUSHURQ;

        public int getDUSHU() {
            return DUSHU;
        }

        public void setDUSHU(int DUSHU) {
            this.DUSHU = DUSHU;
        }

        public String getDUSHURQ() {
            return DUSHURQ;
        }

        public void setDUSHURQ(String DUSHURQ) {
            this.DUSHURQ = DUSHURQ;
        }
    }
}
