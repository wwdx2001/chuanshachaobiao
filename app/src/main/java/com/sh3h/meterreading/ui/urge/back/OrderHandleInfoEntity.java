package com.sh3h.meterreading.ui.urge.back;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2021/12/28 15:35
 */
@Entity
public class OrderHandleInfoEntity {
    @Id
    private long keyID;
    private String taskId;
    private String taskType;
    private int taskState;
    private int replyState;
    private long repairTime;
    private String repairPerson;
    private String repairCondition;
    private int oldRead;
    private String oldBarCode;
    private String newNumber;
    private String newBarCode;
    private int newRead;
    private String newManufactory;
    private String newManufactoryCode;
    private String valve;
    private String newModel;
    private String newModelCode;
    private String cap;
    private String cardSwitcher;
    private String newCaliber;
    private String newCaliberCode;
    private String failureReason;
    private String failureReasonCode;
    private String subsequentMeasure;
    private String cardId;
    private String newMeterBox;
    private String newMeterBoxCode;
    private String newMeterBoxCap;
    private String newMeterBoxCapCode;
    private String maintainedReason;
    private String maintainedReasonCode;
    private boolean lockedValve;
    private String rejectedReason;
    private boolean unableSwitchCard;
    private String remark;
    private String extend;
    private int operatePerson;//操作人
    private String username;

    private boolean isExceedLimitTime;  //是否超过7天

    private String S_GONGDANBH;
    private String S_CHULINR;
    private String D_CHULISJ;
    private String S_JIEJUECS;
    private String S_KOUJING;
    private String S_KOUJING_Code;
    private String S_ShiGongR;
    private String S_CHULIBZ;
    private String s_kehufk;
    private String S_CHULIJG;
    private String S_CHULILB;
    private String S_FASHENGYY;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //2017年3月1日添加
    /*private int lastDisassembleRead; // '上次拆表读数',  number*/
    private String reinstallBarCode; //'复装条码',string
    private int reinstallRead;//'复装读数',number
    private int confirmRead;//'确认读数（默认上次拆表读数）',number
    private int newStartCode;//'新表底码',number


    @Generated(hash = 313860216)
    public OrderHandleInfoEntity(long keyID, String taskId, String taskType, int taskState, int replyState,
            long repairTime, String repairPerson, String repairCondition, int oldRead, String oldBarCode, String newNumber,
            String newBarCode, int newRead, String newManufactory, String newManufactoryCode, String valve,
            String newModel, String newModelCode, String cap, String cardSwitcher, String newCaliber,
            String newCaliberCode, String failureReason, String failureReasonCode, String subsequentMeasure, String cardId,
            String newMeterBox, String newMeterBoxCode, String newMeterBoxCap, String newMeterBoxCapCode,
            String maintainedReason, String maintainedReasonCode, boolean lockedValve, String rejectedReason,
            boolean unableSwitchCard, String remark, String extend, int operatePerson, String username,
            boolean isExceedLimitTime, String S_GONGDANBH, String S_CHULINR, String D_CHULISJ, String S_JIEJUECS,
            String S_KOUJING, String S_KOUJING_Code, String S_ShiGongR, String S_CHULIBZ, String s_kehufk,
            String S_CHULIJG, String S_CHULILB, String S_FASHENGYY, String reinstallBarCode, int reinstallRead,
            int confirmRead, int newStartCode) {
        this.keyID = keyID;
        this.taskId = taskId;
        this.taskType = taskType;
        this.taskState = taskState;
        this.replyState = replyState;
        this.repairTime = repairTime;
        this.repairPerson = repairPerson;
        this.repairCondition = repairCondition;
        this.oldRead = oldRead;
        this.oldBarCode = oldBarCode;
        this.newNumber = newNumber;
        this.newBarCode = newBarCode;
        this.newRead = newRead;
        this.newManufactory = newManufactory;
        this.newManufactoryCode = newManufactoryCode;
        this.valve = valve;
        this.newModel = newModel;
        this.newModelCode = newModelCode;
        this.cap = cap;
        this.cardSwitcher = cardSwitcher;
        this.newCaliber = newCaliber;
        this.newCaliberCode = newCaliberCode;
        this.failureReason = failureReason;
        this.failureReasonCode = failureReasonCode;
        this.subsequentMeasure = subsequentMeasure;
        this.cardId = cardId;
        this.newMeterBox = newMeterBox;
        this.newMeterBoxCode = newMeterBoxCode;
        this.newMeterBoxCap = newMeterBoxCap;
        this.newMeterBoxCapCode = newMeterBoxCapCode;
        this.maintainedReason = maintainedReason;
        this.maintainedReasonCode = maintainedReasonCode;
        this.lockedValve = lockedValve;
        this.rejectedReason = rejectedReason;
        this.unableSwitchCard = unableSwitchCard;
        this.remark = remark;
        this.extend = extend;
        this.operatePerson = operatePerson;
        this.username = username;
        this.isExceedLimitTime = isExceedLimitTime;
        this.S_GONGDANBH = S_GONGDANBH;
        this.S_CHULINR = S_CHULINR;
        this.D_CHULISJ = D_CHULISJ;
        this.S_JIEJUECS = S_JIEJUECS;
        this.S_KOUJING = S_KOUJING;
        this.S_KOUJING_Code = S_KOUJING_Code;
        this.S_ShiGongR = S_ShiGongR;
        this.S_CHULIBZ = S_CHULIBZ;
        this.s_kehufk = s_kehufk;
        this.S_CHULIJG = S_CHULIJG;
        this.S_CHULILB = S_CHULILB;
        this.S_FASHENGYY = S_FASHENGYY;
        this.reinstallBarCode = reinstallBarCode;
        this.reinstallRead = reinstallRead;
        this.confirmRead = confirmRead;
        this.newStartCode = newStartCode;
    }

    @Generated(hash = 622722988)
    public OrderHandleInfoEntity() {
    }


    public String getTaskId() {
        return taskId == null ? "" : taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType == null ? "" : taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public int getReplyState() {
        return replyState;
    }

    public void setReplyState(int replyState) {
        this.replyState = replyState;
    }

    public long getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(long repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairPerson() {
        return repairPerson == null ? "" : repairPerson;
    }

    public void setRepairPerson(String repairPerson) {
        this.repairPerson = repairPerson;
    }

    public String getRepairCondition() {
        return repairCondition == null ? "" : repairCondition;
    }

    public void setRepairCondition(String repairCondition) {
        this.repairCondition = repairCondition;
    }

    public int getOldRead() {
        return oldRead;
    }

    public void setOldRead(int oldRead) {
        this.oldRead = oldRead;
    }

    public String getOldBarCode() {
        return oldBarCode == null ? "" : oldBarCode;
    }

    public void setOldBarCode(String oldBarCode) {
        this.oldBarCode = oldBarCode;
    }

    public String getNewNumber() {
        return newNumber == null ? "" : newNumber;
    }

    public void setNewNumber(String newNumber) {
        this.newNumber = newNumber;
    }

    public String getNewBarCode() {
        return newBarCode == null ? "" : newBarCode;
    }

    public void setNewBarCode(String newBarCode) {
        this.newBarCode = newBarCode;
    }

    public int getNewRead() {
        return newRead;
    }

    public void setNewRead(int newRead) {
        this.newRead = newRead;
    }

    public String getNewManufactory() {
        return newManufactory == null ? "" : newManufactory;
    }

    public void setNewManufactory(String newManufactory) {
        this.newManufactory = newManufactory;
    }

    public String getValve() {
        return valve == null ? "" : valve;
    }

    public void setValve(String valve) {
        this.valve = valve;
    }

    public String getNewModel() {
        return newModel == null ? "" : newModel;
    }

    public void setNewModel(String newModel) {
        this.newModel = newModel;
    }

    public String getCap() {
        return cap == null ? "" : cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCardSwitcher() {
        return cardSwitcher == null ? "" : cardSwitcher;
    }

    public void setCardSwitcher(String cardSwitcher) {
        this.cardSwitcher = cardSwitcher;
    }

    public String getNewCaliber() {
        return newCaliber == null ? "" : newCaliber;
    }

    public void setNewCaliber(String newCaliber) {
        this.newCaliber = newCaliber;
    }

    public String getFailureReason() {
        return failureReason == null ? "" : failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getSubsequentMeasure() {
        return subsequentMeasure == null ? "" : subsequentMeasure;
    }

    public void setSubsequentMeasure(String subsequentMeasure) {
        this.subsequentMeasure = subsequentMeasure;
    }

    public String getCardId() {
        return cardId == null ? "" : cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getNewMeterBox() {
        return newMeterBox == null ? "" : newMeterBox;
    }

    public void setNewMeterBox(String newMeterBox) {
        this.newMeterBox = newMeterBox;
    }

    public String getNewMeterBoxCap() {
        return newMeterBoxCap == null ? "" : newMeterBoxCap;
    }

    public void setNewMeterBoxCap(String newMeterBoxCap) {
        this.newMeterBoxCap = newMeterBoxCap;
    }

    public String getMaintainedReason() {
        return maintainedReason == null ? "" : maintainedReason;
    }

    public void setMaintainedReason(String maintainedReason) {
        this.maintainedReason = maintainedReason;
    }

    public boolean isLockedValve() {
        return lockedValve;
    }

    public void setLockedValve(boolean lockedValve) {
        this.lockedValve = lockedValve;
    }

    public String getRejectedReason() {
        return rejectedReason == null ? "" : rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public boolean isUnableSwitchCard() {
        return unableSwitchCard;
    }

    public void setUnableSwitchCard(boolean unableSwitchCard) {
        this.unableSwitchCard = unableSwitchCard;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtend() {
        return extend == null ? "" : extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public int getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(int operatePerson) {
        this.operatePerson = operatePerson;
    }

    public String getReinstallBarCode() {
        return reinstallBarCode == null ? "" : reinstallBarCode;
    }

    public void setReinstallBarCode(String reinstallBarCode) {
        this.reinstallBarCode = reinstallBarCode;
    }

    public int getReinstallRead() {
        return reinstallRead;
    }

    public void setReinstallRead(int reinstallRead) {
        this.reinstallRead = reinstallRead;
    }

    public int getConfirmRead() {
        return confirmRead;
    }

    public void setConfirmRead(int confirmRead) {
        this.confirmRead = confirmRead;
    }

    public int getNewStartCode() {
        return newStartCode;
    }

    public void setNewStartCode(int newStartCode) {
        this.newStartCode = newStartCode;
    }

    public boolean isExceedLimitTime() {
        return isExceedLimitTime;
    }

    public void setExceedLimitTime(boolean exceedLimitTime) {
        isExceedLimitTime = exceedLimitTime;
    }

    public String getS_GONGDANBH() {
        return S_GONGDANBH == null ? "" : S_GONGDANBH;
    }

    public void setS_GONGDANBH(String s_GONGDANBH) {
        S_GONGDANBH = s_GONGDANBH;
    }

    public String getS_CHULINR() {
        return S_CHULINR == null ? "" : S_CHULINR;
    }

    public void setS_CHULINR(String s_CHULINR) {
        S_CHULINR = s_CHULINR;
    }

    public String getD_CHULISJ() {
        return D_CHULISJ == null ? "" : D_CHULISJ;
    }

    public void setD_CHULISJ(String d_CHULISJ) {
        D_CHULISJ = d_CHULISJ;
    }

    public String getS_JIEJUECS() {
        return S_JIEJUECS == null ? "" : S_JIEJUECS;
    }

    public void setS_JIEJUECS(String s_JIEJUECS) {
        S_JIEJUECS = s_JIEJUECS;
    }

    public String getS_KOUJING() {
        return S_KOUJING == null ? "" : S_KOUJING;
    }

    public void setS_KOUJING(String s_KOUJING) {
        S_KOUJING = s_KOUJING;
    }

    public String getS_ShiGongR() {
        return S_ShiGongR == null ? "" : S_ShiGongR;
    }

    public void setS_ShiGongR(String s_ShiGongR) {
        S_ShiGongR = s_ShiGongR;
    }

    public String getS_CHULIBZ() {
        return S_CHULIBZ == null ? "" : S_CHULIBZ;
    }

    public void setS_CHULIBZ(String s_CHULIBZ) {
        S_CHULIBZ = s_CHULIBZ;
    }

    public boolean getLockedValve() {
        return this.lockedValve;
    }

    public boolean getUnableSwitchCard() {
        return this.unableSwitchCard;
    }

    public boolean getIsExceedLimitTime() {
        return this.isExceedLimitTime;
    }

    public void setIsExceedLimitTime(boolean isExceedLimitTime) {
        this.isExceedLimitTime = isExceedLimitTime;
    }

    public long getKeyID() {
        return this.S_GONGDANBH.hashCode();
    }

    public void setKeyID(long keyID) {
        this.keyID = this.S_GONGDANBH.hashCode();
    }

    public String getS_CHULIJG() {
        return this.S_CHULIJG;
    }

    public void setS_CHULIJG(String S_CHULIJG) {
        this.S_CHULIJG = S_CHULIJG;
    }

    public String getS_CHULILB() {
        return this.S_CHULILB;
    }

    public void setS_CHULILB(String S_CHULILB) {
        this.S_CHULILB = S_CHULILB;
    }

    public String getS_FASHENGYY() {
        return this.S_FASHENGYY;
    }

    public void setS_FASHENGYY(String S_FASHENGYY) {
        this.S_FASHENGYY = S_FASHENGYY;
    }

    public String getNewManufactoryCode() {
        return this.newManufactoryCode;
    }

    public void setNewManufactoryCode(String newManufactoryCode) {
        this.newManufactoryCode = newManufactoryCode;
    }

    public String getNewModelCode() {
        return this.newModelCode;
    }

    public void setNewModelCode(String newModelCode) {
        this.newModelCode = newModelCode;
    }

    public String getNewMeterBoxCode() {
        return this.newMeterBoxCode;
    }

    public void setNewMeterBoxCode(String newMeterBoxCode) {
        this.newMeterBoxCode = newMeterBoxCode;
    }

    public String getNewMeterBoxCapCode() {
        return this.newMeterBoxCapCode;
    }

    public void setNewMeterBoxCapCode(String newMeterBoxCapCode) {
        this.newMeterBoxCapCode = newMeterBoxCapCode;
    }

    public String getMaintainedReasonCode() {
        return this.maintainedReasonCode;
    }

    public void setMaintainedReasonCode(String maintainedReasonCode) {
        this.maintainedReasonCode = maintainedReasonCode;
    }

    public String getFailureReasonCode() {
        return this.failureReasonCode;
    }

    public void setFailureReasonCode(String failureReasonCode) {
        this.failureReasonCode = failureReasonCode;
    }

    public String getS_KOUJING_Code() {
        return this.S_KOUJING_Code;
    }

    public void setS_KOUJING_Code(String S_KOUJING_Code) {
        this.S_KOUJING_Code = S_KOUJING_Code;
    }

    public String getNewCaliberCode() {
        return this.newCaliberCode;
    }

    public void setNewCaliberCode(String newCaliberCode) {
        this.newCaliberCode = newCaliberCode;
    }

    public String getS_kehufk() {
        return this.s_kehufk;
    }

    public void setS_kehufk(String s_kehufk) {
        this.s_kehufk = s_kehufk;
    }
}
