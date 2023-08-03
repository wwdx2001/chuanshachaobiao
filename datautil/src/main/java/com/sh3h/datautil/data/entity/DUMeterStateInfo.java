package com.sh3h.datautil.data.entity;


public class DUMeterStateInfo extends DURequest {
    private String customerId;

    public DUMeterStateInfo() {

    }

    public DUMeterStateInfo(String customerId,
                            IDUHandler duHandler) {
        this.customerId = customerId;
        this.duHandler = duHandler;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
