package com.sh3h.serverprovider.entity;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/1/20 10:43
 */
public class ResultEntity {

    /**
     * MsgCode : 00
     * MsgInfo : 成功
     */

    private String MsgCode;
    private String MsgInfo;

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
}
