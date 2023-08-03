/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.schema;

/**
 * 抄表任务 CB_ChaoBiaoRW
 *
 */
public interface ChaoBiaoRWColumns {
	/**
	 * 抄表任务id,自动增长
	 */
	public static final String ID = "ID";

	public static final String I_RENWUBH = "I_RenWuBH";

	public static final String S_CHAOBIAOYBH = "S_ChaoBiaoYBH";

	public static final String D_PAIFASJ = "D_PaiFaSJ";
	/**
	 * 账务年月 格式 ：201308
	 */
	public static final String I_ZHANGWUNY = "I_ZhangWuNY";
	/**
	 * 册本号，册本编号，允许出现字母
	 */
	public static final String S_CH = "S_CH";
	/**
	 * 册本名称
	 */
	public static final String S_CEBENMC = "S_CeBenMC";
	/**
	 * 工次
	 */
	public static final String I_GONGCI = "I_GongCi";
	/**
	 * 站点
	 */
	public static final String S_ST = "S_ST";
	/**
	 * 总数，册本内总共需要抄表的表的数量
	 */
	public static final String I_ZONGSHU = "I_ZongShu";

	/**
	 * 已抄数量
	 */
	public static final String I_YICHAOSHU = "I_YiChaoShu";

	/**
	 * 抄表周期
	 */
	public static final String S_CHAOBIAOZQ = "S_CHAOBIAOZQ";

	/**
	 * 抄表员姓名
	 */
	public static final String S_ChaoBiaoYXM = "S_ChaoBiaoYXM";

}
