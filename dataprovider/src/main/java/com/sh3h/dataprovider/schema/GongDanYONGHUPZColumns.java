package com.sh3h.dataprovider.schema;

public interface GongDanYONGHUPZColumns {

    /**
     * 自增长编号
     */
    public static final String ID = "ID";
    /**
     * 参数编号
     */
    public static final String I_CANSHUBH = "I_CANSHUBH";
    /**
     * 层次关系 大类为0
     */
    public static final String I_LISHUID = "I_LISHUID";
    /**
     * 参数值 存数据库
     */
    public static final String S_CANSHUZ = "S_CANSHUZ";
    /**
     * 参数名称 显示用
     */
    public static final String S_CANSHUMC = "S_CANSHUMC";
    /**
     * 排序
     */
    public static final String I_PAIXU = "I_PAIXU";
    /**
     * 备注
     */
    public static final String S_BEIZHU = "S_BEIZHU";
    /**
     * -1作废 1成功
     */
    public static final String I_JLZT = "I_JLZT";
    /**
     * 操作人
     */
    public static final String S_CAOZUOR = "S_CAOZUOR";
    /**
     * 操作时间
     */
    public static final String D_CAOZUOSJ = "D_CAOZUOSJ";

    /**
     * 拆表
     */
    public static final int CHAIBIAO = 1;

    /**
     * 故障换表
     */
    public static final int GUHUAN = 2;

    /**
     * 掏表
     */
    public static final int TAOBIAO = 3;

    /**
     * 维修
     */
    public static final int WEIXIU = 4;

    /**
     * 业务问题
     */
    public static final int YEWUWENTI = 5;

    /**
     * 反映类别
     */
    public static final int FANYINGLB = 1;

    /**
     * 反映内容
     */
    public static final int FANYINGNR = 2;

    /**
     * 故障换表原因
     */
    public static final int GUHUANYY = 3;

    /**
     * 拆表原因
     */
    public static final int CHAIBIAOYY = 4;
}
