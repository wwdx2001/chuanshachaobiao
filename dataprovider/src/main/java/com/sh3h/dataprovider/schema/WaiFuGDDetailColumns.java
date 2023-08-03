package com.sh3h.dataprovider.schema;

public interface WaiFuGDDetailColumns {


    /**
     *  任务编号
     */
    public static String I_RENWUBH = "I_RENWUBH";

    /**
     * 操作人
     */
    public static String S_CAOZUOR = "S_CAOZUOR";

    /**
     *  序号 
     */
    public static String I_XH = "I_XH";

    /**
     *  简号   
     */
    public static String S_JH= "S_JH";

    /**
     *  简号比率  
     */
    public static String N_BILI = "N_BILI";

    /**
     *  简号类型(0:原简号 1：新简号)
     */
    public static String I_JHTYPE = "I_JHTYPE";

    /**
     * 0:原简号
     */
    public static int JHTYPE_OLD = 0;

    /**
     * 1：新简号
     */
    public static int JHTYPE_NEW = 1;

    /**
     *  客户号
     */
    public static String S_CID = "S_CID";

    /**
     *  抄表年月   
     */
    public static String I_CHAOBIAONY = "I_CHAOBIAONY";

    /**
     *  栋单元房   
     */
    public static String S_DZ = "S_DZ";

    /**
     *  水表号    
     */
    public static String S_TIAOXINGM = "S_TIAOXINGM";

    /**
     * 原水量    
     */
    public static String I_YONGSHUIL = "I_YONGSHUIL";

    /**
     *  调减水量
     */
    public static String I_TIAOJIANSL = "I_TIAOJIANSL";

    /**
     *  调整金额 
     */
    public static String  N_TIAOZHENGJE = "N_TIAOZHENGJE";

    /**
     *  申请缘由
     */
    public static String S_SHENQINGYY = "S_SHENQINGYY";

    /**
     *  备注
     */
    public static String S_BEIZHU = "S_BEIZHU";

    /**
     * 来源编号
     */
    public static String I_LAIYUANBH = "I_LAIYUANBH";

    /**
     * 完成标志
     */
    public static String I_WANCHENGBZ = "I_WANCHENGBZ";

    /**
     * 隐藏标志(0全部，1隐藏)
     */
    public static String I_YINGCANGBZ = "I_YINGCANGBZ";

    /**
     * 0 显示
     */
    public static int YINGCANGBZ_VISIBLE = 0;

    /**
     * 1隐藏
     */
    public static int YINGCANGBZ_GONE = 1;

    /**
     * 复查结果
     */
    public static String S_FUCHAJG = "S_FUCHAJG";

    /**
     * 原底码
     */
    public static String I_YUANCHAOM = "I_YUANCHAOM";
}
