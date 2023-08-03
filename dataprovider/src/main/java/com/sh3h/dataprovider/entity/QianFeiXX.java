/**
 * @author zeng.jing
 *
 */
package com.sh3h.dataprovider.entity;

public class QianFeiXX {

	/**
	 * 册本号 
	 */
	private String _cH;

	/**
	 * 用户号
	 */
	private String _cId;

	/**
	 * 抄表年
	 */
	private int _chaoBiaoN;

	/**
	 * 抄表月
	 */
	private int _chaoBiaoY;

	/**
	 * 账务年月
	 */
	private int _zhangWuNY;

	/**
	 * 抄表日期
	 */
	private long _chaoBiaoRQ;

	/**
	 * 抄次
	 */
	private int _chaoCi;

	/**
	 * 水费编号
	 */
	private int _feeId;

	/**
	 * 水量
	 */
	private int _kaiZhangSL;

	/**
	 * 账单金额
	 */
	private Number _jE;

	/**
	 * 违约金
	 */
	private Number _yingShouWYJ;

	public String getcH() {
		return _cH;
	}

	public void setcH(String cH) {
		this._cH = cH;
	}

	public String getcId() {
		return _cId;
	}

	public void setcId(String cId) {
		this._cId = cId;
	}

	public int getChaoBiaoN() {
		return _chaoBiaoN;
	}

	public void setChaoBiaoN(int chaoBiaoN) {
		this._chaoBiaoN = chaoBiaoN;
	}

	public int getChaoBiaoY() {
		return _chaoBiaoY;
	}

	public void setChaoBiaoY(int chaoBiaoY) {
		this._chaoBiaoY = chaoBiaoY;
	}

	public int getZhangWuNY() {
		return _zhangWuNY;
	}

	public void setZhangWuNY(int zhangWuNY) {
		this._zhangWuNY = zhangWuNY;
	}

	public long getChaoBiaoRQ() {
		return _chaoBiaoRQ;
	}

	public void setChaoBiaoRQ(long chaoBiaoRQ) {
		this._chaoBiaoRQ = chaoBiaoRQ;
	}

	public int getChaoCi() {
		return _chaoCi;
	}

	public void setChaoCi(int chaoCi) {
		this._chaoCi = chaoCi;
	}

	public int getFeeId() {
		return _feeId;
	}

	public void setFeeId(int feeId) {
		_feeId = feeId;
	}

	public int getKaiZhangSL() {
		return _kaiZhangSL;
	}

	public void setKaiZhangSL(int kaiZhangSL) {
		this._kaiZhangSL = kaiZhangSL;
	}

	public Number getjE() {
		return _jE;
	}

	public void setjE(Number jE) {
		this._jE = jE;
	}

	public Number getYingShouWYJ() {
		return _yingShouWYJ;
	}

	public void setYingShouWYJ(Number yingShouWYJ) {
		this._yingShouWYJ = yingShouWYJ;
	}

	public QianFeiXX() {

	}

}
