package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUShuiLiangFTXXResult extends DUResponse {
    private List<DUShuiLiangFTXX> duShuiLiangFTXXList;

    public DUShuiLiangFTXXResult() {
        duShuiLiangFTXXList = null;
    }

    public List<DUShuiLiangFTXX> getDuShuiLiangFTXXList() {
        return duShuiLiangFTXXList;
    }

    public void setDuShuiLiangFTXXList(List<DUShuiLiangFTXX> duShuiLiangFTXXList) {
        this.duShuiLiangFTXXList = duShuiLiangFTXXList;
    }
}
