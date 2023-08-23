package com.sh3h.serverprovider.entity;

import java.util.List;

public class XunJianXXWords {

    /**
     * MsgCode : 0
     * MsgInfo : 成功
     * data : [{"MMODULE":"模块厂商","MVALUE":"20","MNAME":"上海自来水给水设备"},{"MMODULE":"模块厂商","MVALUE":"21","MNAME":"宁波东海仪表水道有限公司"},{"MMODULE":"模块厂商","MVALUE":"22","MNAME":"宁波水表股份有限公司作废"},{"MMODULE":"模块厂商","MVALUE":"23","MNAME":"福州真兰水表公司"},{"MMODULE":"模块厂商","MVALUE":"1","MNAME":"上海水务建设工程有限公司"},{"MMODULE":"模块厂商","MVALUE":"2","MNAME":"上海水表厂"},{"MMODULE":"模块厂商","MVALUE":"3","MNAME":"宁波东海集团有限公司"},{"MMODULE":"模块厂商","MVALUE":"4","MNAME":"米诺测量仪表（上海）有限公司"},{"MMODULE":"模块厂商","MVALUE":"5","MNAME":"埃创仪表系统（苏州）有限公司"},{"MMODULE":"模块厂商","MVALUE":"0","MNAME":"其他"},{"MMODULE":"模块厂商","MVALUE":"24","MNAME":"上海ABB工程有限公司"},{"MMODULE":"模块厂商","MVALUE":"8","MNAME":"杭州竟达电子有限公司"},{"MMODULE":"模块厂商","MVALUE":"25","MNAME":"深圳市拓安信自动化仪表有限公司"},{"MMODULE":"模块厂商","MVALUE":"7","MNAME":"宁波水表股份有限公司"},{"MMODULE":"模块类型","MVALUE":"1","MNAME":"模块类型1"},{"MMODULE":"数据采集方式","MVALUE":"1","MNAME":"NB"},{"MMODULE":"数据采集方式","MVALUE":"2","MNAME":"电力"},{"MMODULE":"位置分类","MVALUE":"01","MNAME":"地上"},{"MMODULE":"位置分类","MVALUE":"02","MNAME":"地下"},{"MMODULE":"位置分类","MVALUE":"03","MNAME":"室内龙头"},{"MMODULE":"位置分类","MVALUE":"04","MNAME":"室外龙头"},{"MMODULE":"位置分类","MVALUE":"05","MNAME":"管弄"},{"MMODULE":"位置分类","MVALUE":"06","MNAME":"嵌墙"},{"MMODULE":"用水性质","MVALUE":"01","MNAME":"居民生活用水"},{"MMODULE":"用水性质","MVALUE":"02","MNAME":"经营服务"},{"MMODULE":"用水性质","MVALUE":"03","MNAME":"行政事业用水"},{"MMODULE":"用水性质","MVALUE":"04","MNAME":"特种用水"},{"MMODULE":"用水性质","MVALUE":"05","MNAME":"工业用水"},{"MMODULE":"用水性质","MVALUE":"06","MNAME":"其他用水"},{"MMODULE":"业务类型","MVALUE":"00","MNAME":"加装"}]
     */

    private String MsgCode;
    private String MsgInfo;
    private List<XJXXWordBean> data;

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

    public List<XJXXWordBean> getData() {
        return data;
    }

    public void setData(List<XJXXWordBean> data) {
        this.data = data;
    }

}
