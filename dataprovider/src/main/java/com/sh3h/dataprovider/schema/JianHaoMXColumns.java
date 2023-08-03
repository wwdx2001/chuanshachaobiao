/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.schema;

/**
 * 元数据 ( META_INFO )表 字段常量
 *
 */
public interface JianHaoMXColumns {
	/**
	 * ID
	 */
	public static final String ID = "ID";

	/**
	 * 调价号
	 */
	public static final String TIAOJIAH = "I_TIAOJIAH";
	/**
	 * 简号
	 */
	public static final String JIANHAO = "S_JIANHAO";

	/**
	 * 费用大类ID
	 */
	public static final String FEIYONGDLID = "I_FEIYONGDLID";
	/**
	 * 费用ID
	 */
	public static final String FEIYONGID = "I_FEIYONGID";

	/**
	 * 起始月
	 */
	public static final String QISHIY = "I_QISHIY";
	/**
	 * 结束月
	 */
	public static final String JIESHUY = "I_JIESHUY";
	/**
	 * 开始水量
	 */
	public static final String KAISHISL = "I_KAISHISL";
	/**
	 * 结束水量
	 */
	public static final String JIESHUSL = "I_JIESHUSL";
	/**
	 * 阶梯级别
	 */
	public static final String JIETIJB = "I_JIETIJB";
	/**
	 * 价格
	 */
	public static final String JIAGE = "N_JIAGE";
	/**
	 * 折扣率
	 */
	public static final String ZHEKOUL = "N_ZHEKOUL";
	/**
	 * 折扣类型
	 */
	public static final String ZHEKOULX = "I_ZHEKOULX";
	/**
	 * 备注
	 */
	public static final String FeiYongMC = "S_FEIYONGMC";

	/**
	 * 费用系数
	 */
	public static final String XISHU = "N_XISHU";
	/**
	 * 阶梯数
	 */
	public static final String JIETISHU="I_JIETIS";
	/**
	 * 简号大类名称
	 */
	public static final String DALEI="S_DALEI";
	/**
	 * 简号中类
	 */
	public static final String ZHONGLEI="S_ZHONGLEI";


}