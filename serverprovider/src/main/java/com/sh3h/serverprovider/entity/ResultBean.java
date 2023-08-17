package com.sh3h.serverprovider.entity;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/1/20 10:43
 */
public class ResultBean<T> {

    /**
     * MsgCode : 00
     * MsgInfo : 成功
     */

    private String MsgCode;
    private String MsgInfo;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "MsgCode='" + MsgCode + '\'' +
                ", MsgInfo='" + MsgInfo + '\'' +
                ", data=" + data +
                '}';
    }
}
