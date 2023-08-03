/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.schema;

public interface ChaoBiaoSJColumns {

	/**
	 * ID
	 */
	public static final String ID = "ID";

	public static final String I_RENWUBH = "I_RenWuBH";

	/**
	 * 册内序号
	 */
	public static final String CENETXH = "I_CENEIXH";
	/**
	 * 册本号
	 */
	public static final String CH = "S_CH";
	/**
	 * 用户号
	 */
	public static final String CID = "S_CID";

	public static final String I_GROUPID = "I_GROUPID";
	/**
	 * 站点
	 */
	public static final String ST = "S_ST";

	/**
	 * 抄表年
	 */
	public static final String CHAOBIAON = "I_CHAOBIAON";
	/**
	 * 抄表月
	 */
	public static final String CHAOBIAOYUE = "I_CHAOBIAOY";
	/**
	 * 抄次
	 */
	public static final String CHAOCI = "I_CC";
	/**
	 * 抄表日期
	 */
	public static final String CHAOBIAORQ = "D_CHAOBIAORQ";

	/**
	 * 上次抄码
	 */
	public static final String SHANGCICM = "I_SHANGCICM";
	/**
	 * 本次抄码
	 */
	public static final String BENCICM = "I_BENCICM";
	/**
	 * 抄见水量
	 */
	public static final String CHAOJIANSL = "I_CHAOJIANSL";
	/**
	 * 状态编码
	 */
	public static final String ZHUANGTAIBM = "I_ZHUANGTAIBM";
	/**
	 * 状态名称
	 */
	public static final String ZHUANGTAIMC = "S_ZHUANGTAIMC";

	/**
	 * 上次抄表日期
	 */
	public static final String SHANGCICBRQ = "D_SHANGCICBRQ";
	/**
	 * 上次状态编码
	 */
	public static final String SHANGCIZTBM = "I_SHANGCIZTBM";
	/**
	 * 上次状态名称
	 */
	public static final String SHANGCIZTMC = "S_ShangCiZTMC";
	/**
	 * 上次抄见水量
	 */
	public static final String SHANGCICJSL = "I_ShangCiCJSL";
	/**
	 * 上次状态连续数
	 */
	public static final String SHANGCIZTLXS = "I_SHANGCIZTLXS";
	/**
	 * 前三次平均量
	 */
	public static final String PINGJUN1 = "I_PINGJUNL1";
	/**
	 * 前六次平均量
	 */
	public static final String PINGJUN2 = "I_PINGJUNL2";
	/**
	 * 前十二次平均量
	 */
	public static final String PINGJUN3 = "I_PINGJUNL3";
	/**
	 * 开账金额
	 */
	public static final String JE = "N_JE";
	/**
	 * 总表用户号
	 */
	public static final String ZONGBIAOCID = "S_ZONGBIAOCID";

	/**
	 * 抄表员
	 */
	public static final String CHAOBIAOYUAN = "S_CHAOBIAOY";
	/**
	 * 抄表标志
	 */
	public static final String CHAOBIAOBIAOZHI = "I_CHAOBIAOBZ";
	/**
	 * 旧表抄码
	 */
	public static final String JIUBIAOCM = "I_JIUBIAOCM";
	/**
	 * 新表底码
	 */
	public static final String XINBIAODM = "I_XINBIAODM";
	/**
	 * 换表日期
	 */
	public static final String HUANBIAORQ = "D_HUANBIAORQ";
	/**
	 * 抄表方式编码
	 */
	public static final String FANGSHIBM = "I_FANGSHIBM";
	/**
	 * 量高量低原因编码
	 */
	public static final String LIANGGAOLDYYBM = "I_LIANGGAOLDYYBM";
	/**
	 * 抄表ID
	 */
	public static final String CHAOBIAO_ID = "I_CHAOBIAOID";
	/**
	 * 抄表状态连续数
	 */
	public static final String ZHUANGTAILXS = "I_ZHUANGTAILXS";
	/**
	 * 水表倍率
	 */
	public static final String SHUIBIAOBL = "I_SHUIBIAOBL";
	/**
	 * 用水折扣率
	 */
	public static final String YONGSHUIZKL = "N_YONGSHUIZKL";
	/**
	 * 排水折扣率
	 */
	public static final String PAISHUIZKL = "N_PAISHUIZKL";
	/**
	 * 调价号
	 */
	public static final String TIAOJIAH = "I_TIAOJIAH";

	/**
	 * 简号
	 */
	public static final String JIANHAO = "S_JianHao";
	/**
	 * 下载时间
	 */
	public static final String XIAZAISJ = "D_XIAZAISJ";
	/**
	 * 零用水量说明
	 */
	public static final String LINGYONGSLSM = "I_LINGYONGSLSM";
	/**
	 * 量高水量
	 */
	public static final String LIANGGAOSL = "I_LIANGGAOSL";

	/**
	 * 量低水量
	 */
	public static final String LIANGDISL = "I_LIANGDISL";
	/**
	 * 经度
	 */
	public static final String X1 = "S_X1";
	/**
	 * 纬度
	 */
	public static final String Y1 = "S_Y1";
	/**
	 * 基站X
	 */
	public static final String X = "S_X";
	/**
	 * 基站Y
	 */
	public static final String Y = "S_Y";
	/**
	 * 抄表备注
	 */
	public static final String CHAOBIAOBEIZHU = "S_CHAOBIAOBZ";
	/**
	 * 查找未完成任务
	 */
	public static final int NOWORK = 0;
	/**
	 * 查找已抄任务
	 */
	public static final int FINISHWORK = 1;
	/**
	 * 正常抄表数据
	 */
	public static final int NORMALWORK = 2;
	/**
	 * 异常抄表数据
	 */
	public static final int UNNORMALWORK = 3;
	/**
	 * 查找开账任务
	 */
	public static final int BILLWORK = 2;
	/**
	 * 3延迟
	 */
	public static final int YANCHILX_YC = 3;

	/**
	 * 4外复延迟
	 */
	public static final int YANCHILX_WF = 4;

	/**
	 * 查找延迟任务
	 */
	public static final int DELAYBILLWORK = 5;

	/**
	 * 抄表量高抄表数据
	 */
	public static final int CB_LIANGGAO_SJ = 1;

	/**
	 * 抄表量低抄表数据
	 */
	public static final int CB_LIANGDI_SJ = 2;

	/**
	 * 延迟量高抄表数据
	 */
	public static final int YC_LIANGGAO_SJ = 3;

	/**
	 * 延迟量低抄表数据
	 */
	public static final int YC_LIANGDI_SJ = 4;
	/**
	 * 查找全部任务
	 */
	public static final int ALLWORK = 6;
	/**
	 * 册内排序
	 */
	public static final String CENEIPX = "I_CeNeiPX";

	public static final String I_XIAZAICS = "I_XiaZaiCS";

	public static final String D_ZUIHOUYICXZSJ = "D_ZuiHouYCXZSJ";

	public static final String D_ZUIHOUYICSCSJ = "D_ZuiHouYCSCSJ";

	public static final String I_SHANGCHUANBZ = "I_ShangChuanBZ";

	/**
	 * 上传成功
	 */
	public static final int SHANGCHUAN_OK = 1;

	/**
	 * 上传失败
	 */
	public static final int SHANGCHUAN_NO = 0;

	public static final String I_SHENHEBZ = "I_ShenHeBZ";

	/**
	 * 开帐标志
	 */
	public static final String I_KaiZhangBZ = "I_KaiZhangBZ";
	/**
	 * 阶梯提示
	 */
	public static final String S_JIETITS = "S_JIETITS";
	/**
	 * 延迟原因
	 */
	public static final String S_YANCIYY = "S_YANCIYY";

	/**
	 * 子母表标志，子上次抄码
	 */
	public static final String I_LASTREADINGCHILD = "I_LASTREADINGCHILD";

	/**
	 * 子本次抄码
	 */
	public static final String I_READINGCHILD = "I_READINGCHILD";

	public static final String I_SHANGGEDBZQTS = "I_SHANGGEDBZQTS";

	public static final String D_SHANGSHANGGYCBRQ = "D_SHANGSHANGGYCBRQ";

}
