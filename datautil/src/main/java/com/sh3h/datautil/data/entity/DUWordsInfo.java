package com.sh3h.datautil.data.entity;

import java.util.List;

/**
 * Created by liurui on 2016/2/2.
 */
public class DUWordsInfo extends DURequest {
    public enum FilterType {
        DELETE,
        INSERT
    }

    public static final String XMLCBChaoBiaoZT = "CB_ChaoBiaoZT.xml";
    public static final String XMLCBChaoBiaoZTFL = "CB_ChaoBiaoZTFL.xml";
    public static final String XMLCYCiYuXX = "CY_CiYuXX.xml";
    public static final String XMLJGDingEJJBL = "JG_DingEJJBL.xml";
    public static final String XMLJGFeiYongZC = "JG_FeiYongZC.xml";
    public static final String XMLJGJianHao = "JG_JianHao.xml";
    public static final String XMLJGJianHaoMX = "JG_JianHaoMX.xml";

    private String xmlPath;
    private FilterType mFilterType;
    private List<IDUEntity> duEntityList;


    public DUWordsInfo(FilterType mFilterType) {
        this.mFilterType = mFilterType;
    }

    public DUWordsInfo(String xmlPath, FilterType mFilterType) {
        this.xmlPath = xmlPath;
        this.mFilterType = mFilterType;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public FilterType getmFilterType() {
        return mFilterType;
    }

    public void setmFilterType(FilterType mFilterType) {
        this.mFilterType = mFilterType;
    }

    public List<IDUEntity> getDuEntityList() {
        return duEntityList;
    }

    public void setDuEntityList(List<IDUEntity> duEntityList) {
        this.duEntityList = duEntityList;
    }
}
