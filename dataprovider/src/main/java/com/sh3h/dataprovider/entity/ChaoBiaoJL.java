/**
 * @author zeng.jing
 *
 */
package com.sh3h.dataprovider.entity;

public class ChaoBiaoJL {

	/**
	 * 抄表任务id，自增长ID
	 */
	private int _id;

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
	 * 抄次
	 */
	private int _chaoCi;

	/**
	 * 抄表日期
	 */
	private long _chaoBiaoRQ;

	/**
	 * 上次抄码
	 */
	private int _shangCiCM;

	/**
	 * 本次抄码
	 */
	private int _benCiCM;

	/**
	 * 抄表状态名称
	 */
	private String _chaoBiaoZT;

	/**
	 * 抄见水量
	 */
	private int _chaoJianSL;

	/**
	 * 抄表备注
	 */
	private String _chaoBiaoBZ;

	/**
	 * 抄表员
	 */
	private String _chaoBiaoYuan;

	/**
	 * 抄表状态编码
	 */
	private int _CHAOBIAOZTBM;

	/**
	 * 量高量低原因编码
	 */
	private int _LIANGGAOLDYYBM;


	public int get_CHAOBIAOZTBM() {
		return _CHAOBIAOZTBM;
	}


	public void set_CHAOBIAOZTBM(int _CHAOBIAOZTBM) {
		this._CHAOBIAOZTBM = _CHAOBIAOZTBM;
	}


	public int get_LIANGGAOLDYYBM() {
		return _LIANGGAOLDYYBM;
	}


	public void set_LIANGGAOLDYYBM(int _LIANGGAOLDYYBM) {
		this._LIANGGAOLDYYBM = _LIANGGAOLDYYBM;
	}


	public int getId() {
		return _id;
	}


	public void setId(int id) {
		this._id = id;
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


	public int getChaoCi() {
		return _chaoCi;
	}


	public void setChaoCi(int chaoCi) {
		this._chaoCi = chaoCi;
	}


	public long getChaoBiaoRQ() {
		return _chaoBiaoRQ;
	}


	public void setChaoBiaoRQ(long chaoBiaoRQ) {
		this._chaoBiaoRQ = chaoBiaoRQ;
	}


	public int getShangCiCM() {
		return _shangCiCM;
	}


	public void setShangCiCM(int shangCiCM) {
		this._shangCiCM = shangCiCM;
	}


	public int getBenCiCM() {
		return _benCiCM;
	}


	public void setBenCiCM(int benCiCM) {
		this._benCiCM = benCiCM;
	}


	public String getChaoBiaoZT() {
		return _chaoBiaoZT;
	}


	public void setChaoBiaoZT(String chaoBiaoZT) {
		this._chaoBiaoZT = chaoBiaoZT;
	}


	public int getChaoJianSL() {
		return _chaoJianSL;
	}


	public void setChaoJianSL(int chaoJianSL) {
		this._chaoJianSL = chaoJianSL;
	}


	public String getChaoBiaoBZ() {
		return _chaoBiaoBZ;
	}


	public void setChaoBiaoBZ(String chaoBiaoBZ) {
		this._chaoBiaoBZ = chaoBiaoBZ;
	}

	public String getchaoBiaoYuan() {
		return _chaoBiaoYuan;
	}

	public void setchaoBiaoYuan(String chaoBiaoYuan) {
		this._chaoBiaoYuan = chaoBiaoYuan;
	}


	/**
	 * 构造函数
	 */
	public ChaoBiaoJL() {
	}

}
