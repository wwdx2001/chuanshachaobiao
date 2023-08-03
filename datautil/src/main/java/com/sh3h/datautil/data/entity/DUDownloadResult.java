package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUDownloadResult extends DUResponse {
    public static final String TEMPORARY_VOLUME = "temporary_volume";

    public enum FilterType {
        NONE("none"),
        CARD("card"),
        RECORD("record"),
        QIANFEI("qianfei"),
        CHAOBIAOXX("chaobiaoxx"),
        JIAOFEIXX("jiaofeixx"),
        HUANBIAOXX("huanbiaoxx"),
        PRERECORD("prerecord"),
        PAYMENT("payment"),
        ARREARAGE("arrearage"),
        REPLACEMENT("replacement"),
        TEMPORARY("temporary"),
        WAIFU("waifu"),
        BACKFLOW("backflow"),
        DELAY("delay"),
        JICHA("jicha"),
        RUSHPAY("RushPay");

        private String name;
        FilterType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private FilterType filterType;
    private String volume;
    private int count;
    private int taskId;
    private List<DURushPayTask> duRushPayTaskList;

    public DUDownloadResult() {
        filterType = FilterType.NONE;
        volume = null;
        count = 0;
    }

    public DUDownloadResult(FilterType filterType,
                            String volume,
                            int count) {
        this.filterType = filterType;
        this.volume = volume;
        this.count = count;
    }

    public DUDownloadResult(FilterType filterType,
                            List<DURushPayTask> duRushPayTaskList,
                            int count) {
        this.filterType = filterType;
        this.duRushPayTaskList = duRushPayTaskList;
        this.count = count;
    }

    public DUDownloadResult(FilterType filterType,
                            int taskId,
                            int count) {
        this.filterType = filterType;
        this.taskId = taskId;
        this.count = count;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<DURushPayTask> getDuRushPayTaskList() {
        return duRushPayTaskList;
    }

    public void setDuRushPayTaskList(List<DURushPayTask> duRushPayTaskList) {
        this.duRushPayTaskList = duRushPayTaskList;
    }
}
