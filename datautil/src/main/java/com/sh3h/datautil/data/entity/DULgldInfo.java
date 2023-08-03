package com.sh3h.datautil.data.entity;

/**
 * Created by LiMeng on 2017/10/30.
 */

public class DULgldInfo {
    public static final int FILTER_RECORD_LG_INDEX = 0; // 抄表量高
    public static final int FILTER_RECORD_Ld_INDEX = 1; // 抄表量低
    public static final int FILTER_DELAY_LG_INDEX = 2; // 延迟量高
    public static final int FILTER_DELAY_LD_INDEX = 3; // 延迟量低

    private String account;
    private int filter;

    public DULgldInfo(String account, int filter) {
        this.account = account;
        this.filter = filter;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }
}
