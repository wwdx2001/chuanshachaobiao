/**
 * @author zeng.jing
 *
 */
package com.sh3h.dataprovider.schema;


/**
 * 抄表记录 CB_ChaoBiaoJL
 *
 */
public interface ChaoBiaoJLColumns {

	/**
	 * 抄表记录id,自动增长
	 */
	public static final String ID = "ID";
	/**
	 * 用户号
	 */
	public static final String S_CID = "S_CID";
	/**
	 * 抄表年
	 */
	public static final String I_CHAOBIAON = "I_CHAOBIAON";
	/**
	 * 抄表月
	 */
	public static final String I_CHAOBIAOY = "I_CHAOBIAOY";
	/**
	 * 抄次
	 */
	public static final String I_ChaoCi = "I_ChaoCi";
	/**
	 * 抄表日期
	 */
	public static final String D_ChaoBiaoRQ = "D_ChaoBiaoRQ";
	/**
	 * 上次抄码
	 */
	public static final String I_ShangCiCM = "I_ShangCiCM";
	/**
	 * 本次抄码
	 */
	public static final String I_BenCiCM = "I_BENCICM";
	/**
	 * 抄表状态名称
	 */
	public static final String S_ChaoBiaoZT = "S_CHAOBIAOZT";
	/**
	 * 抄见水量
	 */
	public static final String I_CHAOJIANSL = "I_CHAOJIANSL";
	/**
	 * 抄表员
	 */
	public static final String S_ChaoBiaoYUAN = "S_ChaoBiaoY";
	/**
	 * 抄表备注
	 */
	public static final String S_CHAOBIAOBZ = "S_CHAOBIAOBZ";

	/**
	 * 状态编码
	 */
	public static final String I_CHAOBIAOZTBM = "I_CHAOBIAOZTBM";

	/**
	 * 量高量低原因编码
	 */
	public static final String I_LIANGGAOLDYYBM = "I_LIANGGAOLDYYBM";

}
