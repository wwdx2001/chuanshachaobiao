package com.sh3h.serverprovider.entity;


public class XJXXWordBean {

    /**
     * MMODULE : 模块厂商
     * MVALUE : 20
     * MNAME : 上海自来水给水设备
     */
    private Long id;

    private String MMODULE;
    private String MVALUE;
    private String MNAME;

    public XJXXWordBean(Long id, String MMODULE, String MVALUE, String MNAME) {
        this.id = id;
        this.MMODULE = MMODULE;
        this.MVALUE = MVALUE;
        this.MNAME = MNAME;
    }

    public XJXXWordBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMMODULE() {
        return MMODULE;
    }

    public void setMMODULE(String MMODULE) {
        this.MMODULE = MMODULE;
    }

    public String getMVALUE() {
        return MVALUE;
    }

    public void setMVALUE(String MVALUE) {
        this.MVALUE = MVALUE;
    }

    public String getMNAME() {
        return MNAME;
    }

    public void setMNAME(String MNAME) {
        this.MNAME = MNAME;
    }
}
