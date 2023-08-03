package com.sh3h.datautil.data.entity;


import java.io.Serializable;

public class DUTask implements IDUEntity, Serializable {
    public static final int TONGBUBZ_NORMAL = 0; // 正常（不需要同步）
    public static final int TONGBUBZ_SYNC = 1;  // 同步

    public static final int CHAOBIAOTASK = 0; // 抄表任务
    public static final int SAMPLINGTASK = 1;  // 抽检任务
    /**
     * 抄表任务id，自增长ID
     */
    private int id;

    /**
     * 任务编号
     */
    private int renWuBH;
    /**
     * 抄表员编号
     */
    private String chaoBiaoYBH;
    /**
     * 派发时间
     */
    private long paiFaSJ;

    /**
     * 抄表员姓名
     */
    private String chaoBiaoYXM;

    /**
     * 账务年月 ：201308
     */
    private int zhangWuNY;

    /**
     * 册本号，册本编号，允许出现字母
     */
    private String cH;

    /**
     * 册本名称
     */
    private String ceBenMC;

    /**
     * 工次
     */
    private int gongCi;

    /**
     * 站点
     */
    private String sT;

    /**
     * 总数
     */
    private int zongShu;

    /**
     * 已抄数量
     */
    private int yiChaoShu;

    /**
     * 抄表周期
     */
    private String chaoBiaoZQ;

    // 同步标志
    private int tongBuBZ;

    private int downloadType;

    private int groupId;

    public DUTask() {
        this.id = 0;
        this.renWuBH = 0;
        this.chaoBiaoYBH = null;
        this.paiFaSJ = 0;
        this.chaoBiaoYXM = null;
        this.zhangWuNY = 0;
        this.cH = null;
        this.ceBenMC = null;
        this.gongCi = 0;
        this.sT = null;
        this.zongShu = 0;
        this.yiChaoShu = 0;
        this.chaoBiaoZQ = null;
        this.tongBuBZ = TONGBUBZ_NORMAL;
    }

    public DUTask(int id,
                  int renWuBH,
                  String chaoBiaoYBH,
                  long paiFaSJ,
                  String chaoBiaoYXM,
                  int zhangWuNY,
                  String cH,
                  String ceBenMC,
                  int gongCi,
                  String sT,
                  int zongShu,
                  int yiChaoShu,
                  String chaoBiaoZQ,
                  int tongBuBZ,
                  int downloadType,
                  int groupId) {
        this.id = id;
        this.renWuBH = renWuBH;
        this.chaoBiaoYBH = chaoBiaoYBH;
        this.paiFaSJ = paiFaSJ;
        this.chaoBiaoYXM = chaoBiaoYXM;
        this.zhangWuNY = zhangWuNY;
        this.cH = cH;
        this.ceBenMC = ceBenMC;
        this.gongCi = gongCi;
        this.sT = sT;
        this.zongShu = zongShu;
        this.yiChaoShu = yiChaoShu;
        this.chaoBiaoZQ = chaoBiaoZQ;
        this.tongBuBZ = tongBuBZ;
        this.downloadType = downloadType;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRenWuBH() {
        return renWuBH;
    }

    public void setRenWuBH(int renWuBH) {
        this.renWuBH = renWuBH;
    }

    public String getChaoBiaoYBH() {
        return chaoBiaoYBH;
    }

    public void setChaoBiaoYBH(String chaoBiaoYBH) {
        this.chaoBiaoYBH = chaoBiaoYBH;
    }

    public long getPaiFaSJ() {
        return paiFaSJ;
    }

    public void setPaiFaSJ(long paiFaSJ) {
        this.paiFaSJ = paiFaSJ;
    }

    public String getChaoBiaoYXM() {
        return chaoBiaoYXM;
    }

    public void setChaoBiaoYXM(String chaoBiaoYXM) {
        this.chaoBiaoYXM = chaoBiaoYXM;
    }

    public int getZhangWuNY() {
        return zhangWuNY;
    }

    public void setZhangWuNY(int zhangWuNY) {
        this.zhangWuNY = zhangWuNY;
    }

    public String getcH() {
        return cH;
    }

    public void setcH(String cH) {
        this.cH = cH;
    }

    public String getCeBenMC() {
        return ceBenMC;
    }

    public void setCeBenMC(String ceBenMC) {
        this.ceBenMC = ceBenMC;
    }

    public int getGongCi() {
        return gongCi;
    }

    public void setGongCi(int gongCi) {
        this.gongCi = gongCi;
    }

    public String getsT() {
        return sT;
    }

    public void setsT(String sT) {
        this.sT = sT;
    }

    public int getZongShu() {
        return zongShu;
    }

    public void setZongShu(int zongShu) {
        this.zongShu = zongShu;
    }

    public int getYiChaoShu() {
        return yiChaoShu;
    }

    public void setYiChaoShu(int yiChaoShu) {
        this.yiChaoShu = yiChaoShu;
    }

    public String getChaoBiaoZQ() {
        return chaoBiaoZQ;
    }

    public void setChaoBiaoZQ(String chaoBiaoZQ) {
        this.chaoBiaoZQ = chaoBiaoZQ;
    }

    public int getTongBuBZ() {
        return tongBuBZ;
    }

    public void setTongBuBZ(int tongBuBZ) {
        this.tongBuBZ = tongBuBZ;
    }

    public int getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(int downloadType) {
        this.downloadType = downloadType;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}