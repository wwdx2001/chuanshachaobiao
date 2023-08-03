/**
 * @author zeng.jing
 *
 */
package com.sh3h.dataprovider.schema;

public interface YanChiBiaoColumns {
	/**
	 * ID
	 */
	public static final String I_ID = "ID";

	/**
	 * 任务编号
	 */
	public static final String I_RENWUBH = "I_RENWUBH";

	/**
	 * 抄表ID（PK）
	 */
	public static final String I_CHAOBIAOID = "I_CHAOBIAOID";

	/**
	 * 客户号
	 */
	public static final String S_CID = "S_CID";

	/**
	 * 抄见水量
	 */
	public static final String I_CHAOJIANSL = "I_CHAOJIANSL";

	/**
	 * 上次抄码
	 */
	public static final String I_SHANGCICM = "I_SHANGCICM";

	/**
	 * 抄回抄码
	 */
	public static final String I_CHAOHUICM = "I_CHAOHUICM";
	/**
	 * 抄表状态编码
	 */
	public static final String I_ZHUANGTAIBM = "I_ZHUANGTAIBM";

	/**
	 * 抄表状态中文名称
	 */
	public static final String S_ZHUANGTAIMC = "S_ZHUANGTAIMC";

	/**
	 * 量高量低编码
	 */
	public static final String I_LIANGGAOLDBM = "I_LIANGGAOLDBM";

	/**
	 * 抄表日期
	 */
	public static final String D_CHAOBIAORQ = "D_CHAOBIAORQ";

	/**
	 * 抄表年
	 */
	public static final String I_CHAOBIAON = "I_CHAOBIAON";

	/**
	 * 抄表月
	 */
	public static final String I_CHAOBIAOY = "I_CHAOBIAOY";

	/**
	 * 抄表员
	 */
	public static final String S_CHAOBIAOY = "S_CHAOBIAOY";

	/**
	 * 抄表方式编码
	 */
	public static final String I_FANGSHIBM = "I_FANGSHIBM";

	/**
	 * 抄表状态
	 */
	public static final String I_CHAOBIAOZT = "I_CHAOBIAOZT";

	/**
	 * 上次抄表日期
	 */
	public static final String D_SHANGCICBRQ = "D_SHANGCICBRQ";

	/**
	 * 站点
	 */
	public static final String S_ST = "S_ST";

	/**
	 * 册本号
	 */
	public static final String S_CH = "S_CH";

	/**
	 * 册内序号
	 */
	public static final String I_CENEIXH = "I_CENEIXH";

	/**
	 * 旧表抄码
	 */
	public static final String I_JIUBIAOCM = "I_JIUBIAOCM";

	/**
	 * 新表底码
	 */
	public static final String I_XINBIAODM = "I_XINBIAODM";

	/**
	 * 换表日期
	 */
	public static final String D_HUANBIAORQ = "D_HUANBIAORQ";

	/**
	 * 换表回填时间
	 */
	public static final String D_HUANBIAOHTSJ = "D_HUANBIAOHTSJ";

	/**
	 * 登记时间
	 */
	public static final String D_DENGJISJ = "D_DENGJISJ";

	/**
	 * 状态
	 */
	public static final String I_ZHUANGTAI = "I_ZHUANGTAI";

	/**
	 * 延迟员编号
	 */
	public static final String S_YANCHIYBH = "S_YANCHIYBH";

	/**
	 * 回填员编号
	 */
	public static final String S_HUITIANYBH = "S_HUITIANYBH";

	/**
	 * 回填时间
	 */
	public static final String D_HUITIANSJ = "D_HUITIANSJ";

	/**
	 * 处理情况
	 */
	public static final String S_CHULIQK = "S_CHULIQK";

	/**
	 * 操作人
	 */
	public static final String S_CAOZUOR = "S_CAOZUOR";

	/**
	 * 操作时间
	 */
	public static final String D_CAOZUOSJ = "D_CAOZUOSJ";

	/**
	 * 换表方式
	 */
	public static final String I_HUANBIAOFS = "I_HUANBIAOFS";

	/**
	 * 抄表备注
	 */
	public static final String S_CHAOBIAOBZ = "S_CHAOBIAOBZ";

	/**
	 * 水表条形码
	 */
	public static final String S_SHUIBIAOTXM = "S_SHUIBIAOTXM";

	/**
	 * 延迟原因
	 */
	public static final String S_YANCHIYY = "S_YANCHIYY";

	/**
	 * 真实抄码
	 */
	public static final String I_ZHENSHICM = "I_ZHENSHICM";

	/**
	 * 0用水量说明
	 */
	public static final String I_LINYONGSLSM = "I_LINYONGSLSM";

	/**
	 * 经度
	 */
	public static final String S_X = "S_X";

	/**
	 * 纬度
	 */
	public static final String S_Y = "S_Y";

	/**
	 * 延迟类型(0:延迟,1:延迟外复)
	 */
	public static final String I_YANCHILX = "I_YANCHILX";

	/**
	 * 普通延迟
	 */
	public static final int YANCHILX_YC = 0;

	/**
	 * 延迟外复
	 */
	public static final int YANCHILX_WF = 1;

	/**
	 * 全部延迟
	 */
	public static final int YANCHILX_ALL = 2;

	/**
	 * 延迟单编号
	 */
	public static final String S_YANCHIBH = "S_YANCHIBH";

	/**
	 * 抄表标志(0:未抄, 1:已抄 2:开账)
	 */
	public static final String I_CHAOBIAOBZ = "I_CHAOBIAOBZ";

	/**
	 * 查找未完成任务
	 */
	public static final int NOWORK = 0;
	/**
	 * 查找已抄任务
	 */
	public static final int FINISHWORK = 1;

	/**
	 * 查找全部任务
	 */
	public static final int ALLWORK = 4;

	/**
	 * 上传标志
	 */
	public static final String I_ShangChuanBZ = "I_ShangChuanBZ";
	/**
	 * 查找未上传任务
	 */
	public static final int NOSHANGCHUAN = 0;
	/**
	 * 查找已上传任务
	 */
	public static final int FINISHSHANGCHUAN = 1;

	/**
	 * 开账标志
	 */
	public static final String I_KaiZhangBZ = "I_KaiZhangBZ";

	/**
	 * 简号
	 */
	public static final String S_JH = "S_JH";

	/**
	 * 量高水量
	 */
	public static final String I_LIANGGAOSL = "I_LIANGGAOSL";

	/**
	 * 量低水量
	 */
	public static final String I_LIANGDISL = "I_LIANGDISL";

	/**
	 * 前三月平均量
	 */
	public static final String I_PINGJUNL1 = "I_PINGJUNL1";

	/**
	 * 上次水量
	 */
	public static final String I_SHANGCISL = "I_SHANGCISL";

	/**
	 * 开帐金额
	 */
	public static final String N_JE = "N_JE";

	/**
	 * 阶梯提示
	 */
	public static final String S_JIETITS = "S_JIETITS";

	/**
	 * 上次状态编码
	 */

	public static final String S_ShangCiZTBM = "S_ShangCiZTBM";
}
