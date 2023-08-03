package com.sh3h.mralgorithm.billing;


import java.util.List;

public class BillingParameterEx {
    private List<BillingParameter> _billingParameterList;
    private String _chaobiaozq; // 抄表周期
    private double _renkous; // 人口数
    private int _dibaoyhsl; // 低保优惠水量
    private int _gongceyhsl; // 公厕优惠水量

    private double _yongshuizkl; // 基本用水费折扣率(费用大类id:0)
    private double _paishuizkl; // 污水费折扣率(费用大类id:1)
    private double _zhekoul1; // 水资源费(费用大类id:2)
    private double _zhekoul2; // 水价调节金(费用大类id:3)
    private double _zhekoul3; // 市区价格调节基金(费用大类id:4)

    private int _bencisl;// 本次抄见水量

    public BillingParameterEx() {
        _billingParameterList = null;
        _chaobiaozq = "每月抄";
        _renkous = 0.0;
        _dibaoyhsl = 0;
        _gongceyhsl = 0;

        _yongshuizkl = 0.0;
        _paishuizkl = 0.0;
        _zhekoul1 = 0.0;
        _zhekoul2 = 0.0;
        _zhekoul3 = 0.0;

        _bencisl = 0;
    }

    //
    public List<BillingParameter> getBillingParameterList() {
        return _billingParameterList;
    }

    public void setBillingParameterList(List<BillingParameter> billingParameterList) {
        _billingParameterList = billingParameterList;
    }

    //
    public String getChaobiaozq() {
        return _chaobiaozq;
    }

    public void setChaobiaozq(String chaobiaozq) {
        _chaobiaozq = chaobiaozq;
    }

    //
    public double getRenkous() {
        return _renkous;
    }

    public void setRenkous(double renkous) {
        _renkous = renkous;
    }

    //
    public int getDibaoyhsl() {
        return _dibaoyhsl;
    }

    public void setDibaoyhsl(int dibaoyhsl) {
        _dibaoyhsl = dibaoyhsl;
    }

    //
    public int getGongceyhsl() {
        return _gongceyhsl;
    }

    public void setGongceyhsl(int gongceyhsl) {
        _gongceyhsl = gongceyhsl;
    }

    //
    public int getBencisl() {
        return _bencisl;
    }

    public void setBencisl(int bencisl) {
        _bencisl = bencisl;
    }

    public double getYongshuizkl() {
        return _yongshuizkl;
    }

    public void setYongshuizkl(double yongshuizkl) {
        _yongshuizkl = yongshuizkl;
    }

    public double getPaishuizkl() {
        return _paishuizkl;
    }

    public void setPaishuizkl(double paishuizkl) {
        _paishuizkl = paishuizkl;
    }

    public double getZhekoul1() {
        return _zhekoul1;
    }

    public void setZhekoul1(double zhekoul1) {
        _zhekoul1 = zhekoul1;
    }

    public double getZhekoul2() {
        return _zhekoul2;
    }

    public void setZhekoul2(double zhekoul2) {
        _zhekoul2 = zhekoul2;
    }

    public double getZhekoul3() {
        return _zhekoul3;
    }

    public void setZhekoul3(double zhekoul3) {
        _zhekoul3 = zhekoul3;
    }
}
