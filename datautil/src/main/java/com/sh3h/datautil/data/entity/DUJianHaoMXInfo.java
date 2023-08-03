package com.sh3h.datautil.data.entity;


public class DUJianHaoMXInfo extends DURequest {
    private String jianHao;

    public DUJianHaoMXInfo(String jianHao,
                           IDUHandler duHandler) {
        this.jianHao = jianHao;
        this.duHandler = duHandler;
    }

    public String getJianHao() {
        return jianHao;
    }

    public void setJianHao(String jianHao) {
        this.jianHao = jianHao;
    }
}
