/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.schema;

/**
 * 元数据 ( META_INFO )表 字段常量
 *
 */
public interface JiaoFeiXXColumns {
	/**
	 * 用户号
	 */
	public static final String CID = "S_CID";

	/**
	 * 账务年月
	 */
	public static final String ZHANGWUNY = "I_ZhangWuNY";

	/**
	 * 水费编号
	 */
	public static final String FEEID = "I_FEEID";
	/**
	 * 抄表日期
	 */
	public static final String CHAOBIAORQ = "D_ChaoBiaoRQ";
	/**
	 * 开账日期
	 */
	public static final String KAIZHANGRQ = "D_KAIZHANGRQ";
	/**
	 * 缴费日期
	 */
	public static final String SHOUFEIRQ = "D_SHOUFEIRQ";

	/**
	 * 账单金额
	 */
	public static final String JE = "N_JE";
	/**
	 * 实收违约金
	 */
	public static final String SHISHOUWYJ = "N_SHISHOUWYJ";
	/**
	 * 实收金额
	 */
	public static final String SHISHOUZJE = "N_SHISHOUZJE";
	/**
	 * 缴费途径
	 */
	public static final String SHOUFEITJ = "S_SHOUFEITJ";

}