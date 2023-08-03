/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.entity;

public class ChaoBiaoZT {

	private int _zhuangTaiBM;
	private int _shuiLiangSFBM;
	private int _zhuangTaiFLBM;
	private String _zhuangTaiMC;
	private String _kuaiJieJPC;

	/**
	 * 构造函数
	 */
	public ChaoBiaoZT() {

	}

	/**
	 * @return the zhuangTaiBM
	 */
	public int getZhuangTaiBM() {
		return _zhuangTaiBM;
	}

	/**
	 * @param zhuangTaiBM
	 *            the zhuangTaiBM to set
	 */
	public void setZhuangTaiBM(int zhuangTaiBM) {
		this._zhuangTaiBM = zhuangTaiBM;
	}

	/**
	 * @return the shuiLiangSFBM
	 */
	public int getShuiLiangSFBM() {
		return _shuiLiangSFBM;
	}

	/**
	 * @param shuiLiangSFBM
	 *            the shuiLiangSFBM to set
	 */
	public void setShuiLiangSFBM(int shuiLiangSFBM) {
		this._shuiLiangSFBM = shuiLiangSFBM;
	}

	/**
	 * @return the zhuangTaiFLBM
	 */
	public int getZhuangTaiFLBM() {
		return _zhuangTaiFLBM;
	}

	/**
	 * @param zhuangTaiFLBM
	 *            the zhuangTaiFLBM to set
	 */
	public void setZhuangTaiFLBM(int zhuangTaiFLBM) {
		this._zhuangTaiFLBM = zhuangTaiFLBM;
	}

	/**
	 * @return the zhuangTaiMC
	 */
	public String getZhuangTaiMC() {
		return _zhuangTaiMC;
	}

	/**
	 * @param zhuangTaiMC
	 *            the zhuangTaiMC to set
	 */
	public void setZhuangTaiMC(String zhuangTaiMC) {
		this._zhuangTaiMC = zhuangTaiMC;
	}

	/**
	 * @return the kuaiJieJPC
	 */
	public String getKuaiJieJPC() {
		return _kuaiJieJPC;
	}

	/**
	 * @param kuaiJieJPC
	 *            the kuaiJieJPC to set
	 */
	public void setKuaiJieJPC(String kuaiJieJPC) {
		this._kuaiJieJPC = kuaiJieJPC;
	}

	@Override
	public String toString() {
		return this._zhuangTaiMC;
	}

}
