package com.sh3h.meterreading.event;

public class BiaoKaListSaveEvent {

    private String RENWUMC;
    private String RENWUID;
    public boolean isSuccess;

    public BiaoKaListSaveEvent(String RENWUMC, String RENWUID, boolean isSuccess) {
        this.RENWUMC = RENWUMC;
        this.RENWUID = RENWUID;
        this.isSuccess = isSuccess;
    }

    public String getRENWUMC() {
        return RENWUMC;
    }

    public void setRENWUMC(String RENWUMC) {
        this.RENWUMC = RENWUMC;
    }

    public String getRENWUID() {
        return RENWUID;
    }

    public void setRENWUID(String RENWUID) {
        this.RENWUID = RENWUID;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
