/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.entity;

public class JianHao {

	private int _id;
	private int _tiaoJiaH;
	private String _daLei;
	private String _zhongLei;
	private String _xiaoLei;
	private String _jianHao;
	private int _jieTiS;
	private String _beiZhu;

	/**
	 * 构造函数
	 */
	public JianHao() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this._id = id;
	}

	/**
	 * @return the tiaoJiaH
	 */
	public int getTiaoJiaH() {
		return _tiaoJiaH;
	}

	/**
	 * @param tiaoJiaH
	 *            the tiaoJiaH to set
	 */
	public void setTiaoJiaH(int tiaoJiaH) {
		this._tiaoJiaH = tiaoJiaH;
	}

	/**
	 * @return the daLei
	 */
	public String getDaLei() {
		return _daLei;
	}

	/**
	 * @param daLei
	 *            the daLei to set
	 */
	public void setDaLei(String daLei) {
		this._daLei = daLei;
	}

	/**
	 * @return the zhongLei
	 */
	public String getZhongLei() {
		return _zhongLei;
	}

	/**
	 * @param zhongLei
	 *            the zhongLei to set
	 */
	public void setZhongLei(String zhongLei) {
		this._zhongLei = zhongLei;
	}

	/**
	 * @return the xiaoLei
	 */
	public String getXiaoLei() {
		return _xiaoLei;
	}

	/**
	 * @param xiaoLei
	 *            the xiaoLei to set
	 */
	public void setXiaoLei(String xiaoLei) {
		this._xiaoLei = xiaoLei;
	}

	/**
	 * @return the jianHao
	 */
	public String getJianHao() {
		return _jianHao;
	}

	/**
	 * @param jianHao
	 *            the jianHao to set
	 */
	public void setJianHao(String jianHao) {
		this._jianHao = jianHao;
	}

	/**
	 * @return the jieTiS
	 */
	public int getJieTiS() {
		return _jieTiS;
	}

	/**
	 * @param jieTiS
	 *            the jieTiS to set
	 */
	public void setJieTiS(int jieTiS) {
		this._jieTiS = jieTiS;
	}

	/**
	 * @return the beiZhu
	 */
	public String getBeiZhu() {
		return _beiZhu;
	}

	/**
	 * @param beiZhu
	 *            the beiZhu to set
	 */
	public void setBeiZhu(String beiZhu) {
		this._beiZhu = beiZhu;
	}

	public String toString(){
		return this._zhongLei;//this._xiaoLei;
	}

}
