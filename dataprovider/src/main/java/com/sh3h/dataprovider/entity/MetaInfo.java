/**
 * @author zengdezhi
 */
package com.sh3h.dataprovider.entity;

public class MetaInfo {

	/**
	 * 版本号，版本号为正整形数字，如有变化则版本号加1。
	 */
	private int _banBenH;
	/**
	 * 生成者
	 */
	private String _chuangJianZ;
	/**
	 * 生成时间
	 */
	private long _chuangJIanSJ;
	/**
	 * 抄表机编号
	 */
	private String _chaoBiaoJiBH;
	/**
	 * 抄表员编号
	 */
	private String _chaoBiaoYBH;
	/**
	 * 抄表员名称
	 */
	private String _chaoBiaoYMC;

	/**
	 * 密码
	 */
	private String _miMa;

	/**
	 * 构造函数
	 */
	public MetaInfo() {
	}

	/**
	 * @return the _banBenH
	 */
	public int getBanBenH() {
		return _banBenH;
	}

	/**
	 * @param _banBenH
	 *            the _banBenH to set
	 */
	public void setBanBenH(int _banBenH) {
		this._banBenH = _banBenH;
	}

	/**
	 * @return the _chuangJianZ
	 */
	public String getChuangJianZ() {
		return _chuangJianZ;
	}

	/**
	 * @param _chuangJianZ
	 *            the _chuangJianZ to set
	 */
	public void setChuangJianZ(String _chuangJianZ) {
		this._chuangJianZ = _chuangJianZ;
	}

	/**
	 * @return the _chuangJIanSJ
	 */
	public long getChuangJIanSJ() {
		return _chuangJIanSJ;
	}

	/**
	 * @param _chuangJIanSJ
	 *            the _chuangJIanSJ to set
	 */
	public void setChuangJIanSJ(long _chuangJIanSJ) {
		this._chuangJIanSJ = _chuangJIanSJ;
	}

	/**
	 * @return the _chaoBiaoJiBH
	 */
	public String getChaoBiaoJiBH() {
		return _chaoBiaoJiBH;
	}

	/**
	 * @param _chaoBiaoJiBH
	 *            the _chaoBiaoJiBH to set
	 */
	public void setChaoBiaoJiBH(String _chaoBiaoJiBH) {
		this._chaoBiaoJiBH = _chaoBiaoJiBH;
	}

	/**
	 * @return the _chaoBiaoYBH
	 */
	public String getChaoBiaoYBH() {
		return _chaoBiaoYBH;
	}

	/**
	 * @param _chaoBiaoYBH
	 *            the _chaoBiaoYBH to set
	 */
	public void setChaoBiaoYBH(String _chaoBiaoYBH) {
		this._chaoBiaoYBH = _chaoBiaoYBH;
	}

	/**
	 * @return the _chaoBiaoYMC
	 */
	public String getChaoBiaoYMC() {
		return _chaoBiaoYMC;
	}

	/**
	 * @param _chaoBiaoYMC
	 *            the _chaoBiaoYMC to set
	 */
	public void setChaoBiaoYMC(String _chaoBiaoYMC) {
		this._chaoBiaoYMC = _chaoBiaoYMC;
	}

	/**
	 * @return the _miMa
	 */
	public String getMiMa() {
		return _miMa;
	}

	/**
	 * @param _miMa
	 *            the _miMa to set
	 */
	public void setMiMa(String _miMa) {
		this._miMa = _miMa;
	}

}
