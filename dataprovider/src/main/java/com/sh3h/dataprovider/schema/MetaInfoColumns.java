/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.schema;

/**
 * 元数据 ( META_INFO )表 字段常量
 *
 */
public interface MetaInfoColumns {
	/**
	 * 版本号，版本号为正整形数字，如有变化则版本号加1。
	 */
	public static final String I_BANBENH = "I_BanBenH";
	/**
	 * 创建者，生成文件的程序或组织
	 */
	public static final String S_CHUANGJIANZ = "S_ChuangJianZ";
	/**
	 * 生成时间 ，下载任务是给定系统时间
	 */
	public static final String D_CHUANGJIANSJ = "D_ChuangJianSJ";
	/**
	 * 抄表机编号
	 */
	public static final String S_CHAOBIAOJIBH = "S_ChaoBiaoJiBH";
	/**
	 * 抄表员编号
	 */
	public static final String S_CHAOBIAOYBH = "S_ChaoBiaoYBH";
	/**
	 * 抄表员名称
	 */
	public static final String S_CHAOBIAOYXM = "S_ChaoBiaoYXM";
	/**
	 * 密码
	 */
	public static final String S_MIMA = "S_MIMA";

}