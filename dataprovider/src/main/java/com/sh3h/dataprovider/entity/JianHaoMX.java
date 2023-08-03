/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.entity;

public class JianHaoMX {

	private int _id;
	private int _tiaoJiaH;
	private String _jianHao;
	private int _feiYongDLID;
	private int _feiYongId;
	private int _qiShiY;
	private int _jieShuY;
	private int _kaiShiSL;
	private int _jieShuSL;
	private int _jieTiJB;
	private double _jiaGe;
	private double _zheKouL;
	private int _zheKouLX;
	private String _feiYongMC;
	private double _xiShu;
	private int _jieTiS;
	private String _daLei;
	private String _zhongLei;

	/**
	 * 获取阶梯数
	 * @return
	 */
	public int getjieTiS() {
		return _jieTiS;
	}

	/**
	 * 设置阶梯数
	 * @param _jieTiS
	 */
	public void setjieTiS(int _jieTiS) {
		this._jieTiS = _jieTiS;
	}

	/**
	 * 获取大类
	 * @return
	 */
	public String getdaLei() {
		return _daLei;
	}

	public void setdaLei(String _daLei) {
		this._daLei = _daLei;
	}

	/**
	 * 获取简号中类名称
	 * @return
	 */
	public String getzhongLei() {
		return _zhongLei;
	}

	public void setzhongLei(String _zhongLei) {
		this._zhongLei = _zhongLei;
	}

	/**
	 * 构造函数
	 */
	public JianHaoMX() {

	}

	public JianHaoMX(int _id, int _tiaoJiaH, String _jianHao, int _feiYongDLID, int _feiYongId, int _qiShiY, int _jieShuY, int _kaiShiSL, int _jieShuSL, int _jieTiJB, double _jiaGe, double _zheKouL, int _zheKouLX, String _feiYongMC, double _xiShu, int _jieTiS, String _daLei, String _zhongLei) {
		this._id = _id;
		this._tiaoJiaH = _tiaoJiaH;
		this._jianHao = _jianHao;
		this._feiYongDLID = _feiYongDLID;
		this._feiYongId = _feiYongId;
		this._qiShiY = _qiShiY;
		this._jieShuY = _jieShuY;
		this._kaiShiSL = _kaiShiSL;
		this._jieShuSL = _jieShuSL;
		this._jieTiJB = _jieTiJB;
		this._jiaGe = _jiaGe;
		this._zheKouL = _zheKouL;
		this._zheKouLX = _zheKouLX;
		this._feiYongMC = _feiYongMC;
		this._xiShu = _xiShu;
		this._jieTiS = _jieTiS;
		this._daLei = _daLei;
		this._zhongLei = _zhongLei;
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
	 * @return the filYongDLID
	 */
	public int getFeiYongDLID() {
		return _feiYongDLID;
	}

	/**
	 * @param feiYongDLID
	 *            the filYongDLID to set
	 */
	public void setFeiYongDLID(int feiYongDLID) {
		this._feiYongDLID = feiYongDLID;
	}

	/**
	 * @return the feiYongId
	 */
	public int getFeiYongId() {
		return _feiYongId;
	}

	/**
	 * @param feiYongId
	 *            the feiYongId to set
	 */
	public void setFeiYongId(int feiYongId) {
		this._feiYongId = feiYongId;
	}

	/**
	 * @return the qiShiY
	 */
	public int getQiShiY() {
		return _qiShiY;
	}

	/**
	 * @param qiShiY
	 *            the qiShiY to set
	 */
	public void setQiShiY(int qiShiY) {
		this._qiShiY = qiShiY;
	}

	/**
	 * @return the jieShuY
	 */
	public int getJieShuY() {
		return _jieShuY;
	}

	/**
	 * @param jieShuY
	 *            the jieShuY to set
	 */
	public void setJieShuY(int jieShuY) {
		this._jieShuY = jieShuY;
	}

	/**
	 * @return the kaiShiSL
	 */
	public int getKaiShiSL() {
		return _kaiShiSL;
	}

	/**
	 * @param kaiShiSL
	 *            the kaiShiSL to set
	 */
	public void setKaiShiSL(int kaiShiSL) {
		this._kaiShiSL = kaiShiSL;
	}

	/**
	 * @return the jieShuSL
	 */
	public int getJieShuSL() {
		return _jieShuSL;
	}

	/**
	 * @param jieShuSL
	 *            the jieShuSL to set
	 */
	public void setJieShuSL(int jieShuSL) {
		this._jieShuSL = jieShuSL;
	}

	/**
	 * @return the jieTiJB
	 */
	public int getJieTiJB() {
		return _jieTiJB;
	}

	/**
	 * @param jieTiJB
	 *            the jieTiJB to set
	 */
	public void setJieTiJB(int jieTiJB) {
		this._jieTiJB = jieTiJB;
	}

	/**
	 * @return the jiaGe
	 */
	public double getJiaGe() {
		return _jiaGe;
	}

	/**
	 * @param jiaGe
	 *            the jiaGe to set
	 */
	public void setJiaGe(double jiaGe) {
		this._jiaGe = jiaGe;
	}

	/**
	 * @return the zheKouL
	 */
	public double getZheKouL() {
		return _zheKouL;
	}

	/**
	 * @param zheKouL
	 *            the zheKouL to set
	 */
	public void setZheKouL(double zheKouL) {
		this._zheKouL = zheKouL;
	}

	/**
	 * @return the zheKouLX
	 */
	public int getZheKouLX() {
		return _zheKouLX;
	}

	/**
	 * @param zheKouLX
	 *            the zheKouLX to set
	 */
	public void setZheKouLX(int zheKouLX) {
		this._zheKouLX = zheKouLX;
	}

	/**
	 * @return 费用名称
	 */
	public String getFeiYongMC() {
		return _feiYongMC;
	}

	/**
	 * @param feiyongmc
	 *            the beiZhu to set
	 */
	public void setFeiYongMC(String feiyongmc) {
		this._feiYongMC = feiyongmc;
	}

	/**
	 * 获取系数
	 * @return
	 */
	public double getxiShu() {
		return _xiShu;
	}

	/**
	 * 设置系数
	 * @param _xiShu
	 */
	public void setxiShu(double _xiShu) {
		this._xiShu = _xiShu;
	}

}
