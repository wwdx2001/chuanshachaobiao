package com.sh3h.dataprovider.schema;

public interface WaiFuGDMainColumns {

	/**
	 * 任务编号
	 */
	public static String I_RENWUBH = "I_RENWUBH";

	/**
	 * 工单类型
	 */
	public static String I_TYPE = "I_TYPE";

	/**
	 * 21:用水性质修改
	 */
	public static final int YONGSHUIXZXG = 21;
	/**
	 * 22:人口变更
	 */
	public static final int RENKOUSHUBG = 22;
	/**
	 * 23:公厕
	 */
	public static final int GONGCEYHSP = 23;
	/**
	 * 24:用水比例调整
	 */
	public static final int YONGSHUIBILTZ = 24;
	/**
	 * 25:核查
	 */
	public static final int SHUIBIAOZZHC = 25;
	/**
	 * 26:远传表自传
	 */
	public static final int YUANCHUANBZZ = 26;
	/**
	 * 27:调减水量
	 */
	public static final int TIAOJIANSL = 27;
	/**
	 * 客户号
	 */
	public static String S_CID = "S_CID";

	/**
	 * 册号
	 */
	public static String S_CH = "S_CH";

	/**
	 * 户名
	 */
	public static String S_HM = "S_HM";

	/**
	 * 用水地址
	 */
	public static String S_DZ = "S_DZ";

	/**
	 * 电话号码
	 */
	public static String S_DIANHUAHM = "S_DIANHUAHM";

	/**
	 * 原用水性质
	 */
	public static String S_JH_OLD = "S_JH_OLD";

	/**
	 * 新用水性质
	 */
	public static String S_JH_NEW = "S_JH_NEW";

	/**
	 * 提交日期
	 */
	public static String D_TIJIAORQ = "D_TIJIAORQ";

	/**
	 * 申请人
	 */
	public static String S_SHENQINGR = "S_SHENQINGR";

	/**
	 * 申请原因
	 */
	public static String S_SHENQINGYY = "S_SHENQINGYY";

	/**
	 * 申请人口数
	 */
	public static String I_RENKOUS = "I_RENKOUS";

	/**
	 * 优惠水量
	 */
	public static String I_YOUHUISL = "I_YOUHUISL";

	/**
	 * 蹲位数
	 */
	public static String I_DUNWEIS = "I_DUNWEIS";

	/**
	 * 是否全额
	 */
	public static String B_QUANER = "B_QUANER";

	/**
	 * 上传标志（0未上传 1已上传）
	 */
	public static String I_SHANGCHUANBZ = "I_SHANGCHUANBZ";

	/**
	 * 未上传 0
	 */
	public static final int SHANGCHUAN_NO = 0;

	/**
	 * 已上传1
	 */
	public static final int SHANGCHUAN_OK = 1;

	/**
	 * 完成标志（0未完成 1已完成）
	 */
	public static String I_WANCHENGBZ = "I_WANCHENGBZ";

	/**
	 * 未完成 0
	 */
	public static final int WANCHENG_NO = 0;

	/**
	 * 已完成1
	 */
	public static final int WANCHENG_OK = 1;

	/**
	 * 来源编号
	 */
	public static String I_LAIYUANBH = "I_LAIYUANBH";

	/**
	 * 操作人
	 */
	public static String S_CAOZUOR = "S_CAOZUOR";

	/**
	 * 申请人核实人口数【人口性质变更；】
	 */
	public static String I_HESHIRKS = "I_HESHIRKS";

	/**
	 * 申请人核实蹲位数【公测审批；】
	 */
	public static String I_HESHIDWS = "I_HESHIDWS";

	/**
	 * 施工日期
	 */
	public static String D_SHIGONGRQ = "D_SHIGONGRQ";

	/**
	 * 核查情况【水表自转核查】
	 */
	public static String S_HECHAQK = "S_HECHAQK";

	/**
	 * 审核意见【用水比例调整；水表自转核查】
	 */
	public static String S_SHENHEYJ = "S_SHENHEYJ";

	/**
	 * 1:同意,2:不同意，【用水性质变更用-复核意见；用水比例调整-审批意见；调减数量-复核意见】
	 */
	public static String I_SHENPIJG = "I_SHENPIJG";

	/**
	 * 1同意审批
	 */
	public static int SHENPIJG_OK = 1;

	/**
	 * 2不同意审批
	 */
	public static int SHENPIJG_NO = 2;
	/**
	 * 复核意见原因【调减水量-】
	 */
	public static String S_FUHEYJYY = "S_FUHEYJYY";

	/**
	 * 备注
	 */
	public static String S_BEIZHU = "S_BEIZHU";

	/**
	 * 水表号（条形码）
	 */
	public static String S_TIAOXINGM = "S_TIAOXINGM";

	/**
	 * 客户签名
	 */
	public static String S_KEHUQM = "S_KEHUQM";

	/**
	 * 照片名称
	 */
	public static String S_ZHAOPIANMC = "S_ZHAOPIANMC";

	/**
	 * 经度
	 */
	public static final String S_X1 = "S_X1";

	/**
	 * 维度
	 */
	public static final String S_Y1 = "S_Y1";
}
