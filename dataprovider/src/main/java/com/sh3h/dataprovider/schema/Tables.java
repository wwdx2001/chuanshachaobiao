/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.schema;

/**
 * Tables 定义表名
 */
public interface Tables {

	/**
	 * 元数据表
	 */
	public static final String META_INFO = "META_INFO";

	/**
	 * 抄表任务
	 */
	public static final String CB_CHAOBIAORW = "CB_ChaoBiaoRW";

	/**
	 * 抄表记录
	 */
	public static final String CB_CHAOBIAOJL = "CB_ChaoBiaoJL";

	/**
	 * 抄表数据
	 */
	public static final String CB_CHAOBIAOSJ = "CB_CHAOBIAOSJ";
	/**
	 * 抄表状态
	 */
	public static final String CB_ChaoBiaoZT = "CB_ChaoBiaoZT";

	/**
	 * 抄表状态
	 */
	public static final String CB_ChaoBiaoZT2 = "CB_ChaoBiaoZT";

	/**
	 * 抄表状态分类
	 */
	public static final String CB_ChAOBIAOZTFL = "CB_ChaoBiaoZTFL";

	/**
	 * 表卡信息表
	 */
	public static final String KG_BiaoKaXX = "KG_BiaoKaXX";

	/**
	 * 水量分摊信息表
	 */
	public static final String KG_ShuiLiangFTXX = "KG_ShuiLiangFTXX ";

	/**
	 * 简号信息表
	 */
	public static final String JG_JianHao = "JG_JianHao";

	/**
	 * 简号明细表
	 */
	public static final String JG_JianHaoMX = "JG_JianHaoMX";

	/**
	 * 用戶
	 */
	public static final String CBJPZ = "CBJPZ";

	/**
	 * 费用组成表
	 */
	public static final String JG_FeiYongZC = "JG_FeiYongZC";

	/**
	 * 多媒体信息表
	 */
	public static final String CB_DuoMeiTXX = "CB_DuoMeiTXX";

	/**
	 * 词语信息表
	 */
	public static final String CY_CiYuXX = "CY_CiYuXX";

	/**
	 * 欠费信息表
	 */
	public static final String ZW_QianFeiXX = "ZW_QianFeiXX";

	/**
	 * 缴费信息表
	 */
	public static final String SF_JiaoFeiXX = "SF_JiaoFeiXX";

	/**
	 * 员工信息表
	 */
	public static final String SX_YuanGongXX = "SX_YuanGongXX";

	/**
	 * 定额加价倍率表
	 */
	public static final String JG_DingEJJBL = "JG_DingEJJBL";
	/**
	 * 热线工单表
	 */
	public static final String GD_ReXianGD = "GD_ReXianGD";

	/**
	 * 登陆历史记录表
	 */
	public static final String LG_DengLuLS = "LG_DengLuLS";

	/**
	 * 客户资料变更
	 */
	public static final String KG_XinXiBG = "KG_XinXiBG";
	/**
	 * 公告信息
	 */
	public static final String SX_GONGGAOXX = "SX_GONGGAOXX";
	/**
	 * 换表记录
	 */
	public static final String BW_HuanBiaoJL = "BW_HuanBiaoJL";
	/**
	 * 费用折扣率
	 */
	public static final String KG_FeiYongZKL = "KG_FeiYongZKL";
	/**
	 * 表务工单
	 */
	public static final String PDA_BIAOWUGD = "PDA_BIAOWUGD";
	/**
	 * 延迟
	 */
	public static final String PDA_YANCHI = "PDA_YANCHI";
	/**
	 * 任务信息
	 */
	public static final String PDA_RENWUXX = "PDA_RENWUXX";
	/**
	 * 外复工单（主表）
	 */
	public static final String PDA_WaiFuGDMain = "PDA_WaiFuGDMain";
	/**
	 * 外复工单(明细表)
	 */
	public static final String PDA_WaiFuGDDetail = "PDA_WaiFuGDDetail";
	/**
	 * 用户 表
	 */
	public static final String PDA_USERS = "PDA_USERS";
	/**
	 * 轨迹
	 */
	public static final String CB_GUIJI = "CB_GuiJi";

	/**
	 * 领用人信息表
	 */
	public static final String AS_USERINFO = "user";
	/**
	 * 已领用册本信息
	 */
	public static final String CB_YILINGYCBXX = "CB_YILINGYCBXX";
	/**
	 * 抄表数据（保存用）
	 */
	public static final String CB_CHAOBIAOSJLR = "CB_CHAOBIAOSJLR";
	/**
	 * 基本信息、用户查看信息
	 */
	public static final String KG_YONGHUJBXX = "KG_YONGHUJBXX";
	/**
	 * 补偿系数
	 */
	public static final String CB_BUCHANGXS = "CB_BUCHANGXS";
	/**
	 * 抄表状态
	 */
	public static final String CB_CHAOBIAOZT = "CB_CHAOBIAOZT";
	/**
	 * 设备信息
	 */
	public static final String KG_SHEBEIXX = "KG_SHEBEIXX";
	/**
	 * 用气历史信息
	 */
	public static final String KG_YONGQILSXX = "KG_YONGQILSXX";
	/**
	 * 报警器信息
	 */
	public static final String KG_BAOJINGQXX = "KG_BAOJINGQXX";
	/**
	 * 报警器年检信息
	 */
	public static final String KG_BAOJINGQLJXX = "KG_BAOJINGQLJXX";
	/**
	 * 报警器保养信息
	 */
	public static final String KG_BAOJINGQBYXX = "KG_BAOJINGQBYXX";
	/**
	 * 报警系统派工明细
	 */
	public static final String KG_BAOJINGXTPGMX = "KG_BAOJINGXTPGMX";
	/**
	 * 校正仪信息
	 */
	public static final String KG_JIAOZHENGYXX = "KG_JIAOZHENGYXX";
	/**
	 * 燃气表信息
	 */
	public static final String CK_RANQIBXX = "CK_RANQIBXX";
	/**
	 * 调表记录
	 */
	public static final String KG_DIAOBIAOJL = "KG_DIAOBIAOJL";
	/**
	 * 校正仪录入
	 */
	public static final String KG_JIAOZHENGYLR = "KG_JIAOZHENGYLR";
	/**
	 * 抄表分析
	 */
	public static final String CB_CHAOBIAOFX = "CB_CHAOBIAOFX";
	/**
	 * 违章信息输入
	 */
	public static final String KG_WEIZHANGXX = "KG_WEIZHANGXX";
	/**
	 * 违章情况表
	 */
	public static final String GB_WEIZHANGQK = "GB_WEIZHANGQK";
	/**
	 * 分析原因表
	 */
	public static final String CB_FENXIYY = "CB_FENXIYY";
	/**
	 * 文件信息
	 */
	public static final String KG_WENJIANXX = "KG_WENJIANXX";

	/**
	 * 非正常计量输入信息
	 */
	public static final String BW_FZCJLSR = "BW_FZCJLSR";

	/**
	 * 抄表轨迹
	 */
	public static final String CB_CHAOBIAOGJ = "CB_ChaoBiaoGJ";

	/**
	 * 抄表备注
	 */
	public static final String HOT_WORDSINFO = "CB_NEIFUBZ";
	/**
	 * 工单
	 */
	public static final String BW_GONGDAN = "BW_GONGDAN";


	/**
	 * 工单抄表状态表
	 */
	public static final String GD_CHAOBIAOZT = "GD_CHAOBIAOZT";

	/**
	 * 系统词语表
	 */
	public static final String GD_WORDS = "GD_WORDS";
	/**
	 * 用户PZ表
	 */
	public static final String GD_YONGHUPZ = "GD_YONGHUPZ";
	/**
	 * 抄表备注表
	 */
	public static final String CB_CHAOBIAOBZ = "CB_CHAOBIAOBZ";
}