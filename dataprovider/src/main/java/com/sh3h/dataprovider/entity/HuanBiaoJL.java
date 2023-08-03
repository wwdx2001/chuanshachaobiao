/**
 * @author qiweiwei
 *
 */
package com.sh3h.dataprovider.entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class HuanBiaoJL {

	private String _cID;
	private String _biaoWeiXX;
	private String _dengJiR;
	private long _dengJiRQ;
	private String _huanBiaoLX;
	private String _huanBiaoYY;
	private String _jiubiaoBH;
	private String _jiuBiaoGYH;
	private String _jiuBiaoCJ;
	private String _xinBiaoBH;
	private String _xinBiaoGYH;
	private String _xinBiaoCJ;
	private String _jiuBiaoXH;
	private String _xinBiaoXH;
	private String _jiuBiaoKJ;
	private String _xinBiaoKJ;
	private int _xinBiaoLC;
	private int _shangCiCM;
	private int _jiuBiaoCM;
	private int _xinBiaoDM;
	private long _shiGongRQ;
	private String _huiTianR;
	private long _huanBiaoHTRQ;
	private String _huanBiaoZT;
	private String _beiZhu;

	/**
	 * @return the _cID
	 */
	public String getCID() {
		return _cID;
	}

	/**
	 * @param _cID
	 *            the _cID to set
	 */
	public void setCID(String cID) {
		this._cID = cID;
	}

	/**
	 * @return the _biaoWeiXX
	 */
	public String getBiaoWeiXX() {
		return _biaoWeiXX;
	}

	/**
	 * @param _biaoWeiXX
	 *            the _biaoWeiXX to set
	 */
	public void setBiaoWeiXX(String biaoWeiXX) {
		this._biaoWeiXX = biaoWeiXX;
	}

	/**
	 * @return the _dengJiRBH
	 */
	public String getDengJiR() {
		return _dengJiR;
	}

	/**
	 * @param _dengJiRBH
	 *            the _dengJiRBH to set
	 */
	public void setDengJiR(String dengJiR) {
		this._dengJiR = dengJiR;
	}

	/**
	 * @return the _dengJiRQ
	 */
	public long getDengJiRQ() {
		return _dengJiRQ;
	}

	/**
	 * @param _dengJiRQ
	 *            the _dengJiRQ to set
	 */
	public void setDengJiRQ(long dengJiRQ) {
		this._dengJiRQ = dengJiRQ;
	}

	/**
	 * @return the _huanBiaoLX
	 */
	public String getHuanBiaoLX() {
		return _huanBiaoLX;
	}

	/**
	 * @param _huanBiaoLX
	 *            the _huanBiaoLX to set
	 */
	public void setHuanBiaoLX(String huanBiaoLX) {
		this._huanBiaoLX = huanBiaoLX;
	}

	/**
	 * @return the _huanBiaoYY
	 */
	public String getHuanBiaoYY() {
		return _huanBiaoYY;
	}

	/**
	 * @param _huanBiaoYY
	 *            the _huanBiaoYY to set
	 */
	public void setHuanBiaoYY(String huanBiaoYY) {
		this._huanBiaoYY = huanBiaoYY;
	}

	/**
	 * @return the _jiubiaoBH
	 */
	public String getJiubiaoBH() {
		return _jiubiaoBH;
	}

	/**
	 * @param _jiubiaoBH
	 *            the _jiubiaoBH to set
	 */
	public void setJiubiaoBH(String jiubiaoBH) {
		this._jiubiaoBH = jiubiaoBH;
	}

	/**
	 * @return the _jiuBiaoGYH
	 */
	public String getJiuBiaoGYH() {
		return _jiuBiaoGYH;
	}

	/**
	 * @param _jiuBiaoGYH
	 *            the _jiuBiaoGYH to set
	 */
	public void setJiuBiaoGYH(String jiuBiaoGYH) {
		this._jiuBiaoGYH = jiuBiaoGYH;
	}

	/**
	 * @return the _jiuBiaoCJ
	 */
	public String getJiuBiaoCJ() {
		return _jiuBiaoCJ;
	}

	/**
	 * @param _jiuBiaoCJ
	 *            the _jiuBiaoCJ to set
	 */
	public void setJiuBiaoCJ(String jiuBiaoCJ) {
		this._jiuBiaoCJ = jiuBiaoCJ;
	}

	/**
	 * @return the _xinBiaoBH
	 */
	public String getXinBiaoBH() {
		return _xinBiaoBH;
	}

	/**
	 * @param _xinBiaoBH
	 *            the _xinBiaoBH to set
	 */
	public void setXinBiaoBH(String xinBiaoBH) {
		this._xinBiaoBH = xinBiaoBH;
	}

	/**
	 * @return the _xinBiaoGYH
	 */
	public String getXinBiaoGYH() {
		return _xinBiaoGYH;
	}

	/**
	 * @param _xinBiaoGYH
	 *            the _xinBiaoGYH to set
	 */
	public void setXinBiaoGYH(String xinBiaoGYH) {
		this._xinBiaoGYH = xinBiaoGYH;
	}

	/**
	 * @return the _xinBiaoCJ
	 */
	public String getXinBiaoCJ() {
		return _xinBiaoCJ;
	}

	/**
	 * @param _xinBiaoCJ
	 *            the _xinBiaoCJ to set
	 */
	public void setXinBiaoCJ(String xinBiaoCJ) {
		this._xinBiaoCJ = xinBiaoCJ;
	}

	/**
	 * @return the _jiuBiaoXH
	 */
	public String getJiuBiaoXH() {
		return _jiuBiaoXH;
	}

	/**
	 * @param _jiuBiaoXH
	 *            the _jiuBiaoXH to set
	 */
	public void setJiuBiaoXH(String jiuBiaoXH) {
		this._jiuBiaoXH = jiuBiaoXH;
	}

	/**
	 * @return the _xinBiaoXH
	 */
	public String getXinBiaoXH() {
		return _xinBiaoXH;
	}

	/**
	 * @param _xinBiaoXH
	 *            the _xinBiaoXH to set
	 */
	public void setXinBiaoXH(String xinBiaoXH) {
		this._xinBiaoXH = xinBiaoXH;
	}

	/**
	 * @return the _jiuBiaoKJ
	 */
	public String getJiuBiaoKJ() {
		return _jiuBiaoKJ;
	}

	/**
	 * @param _jiuBiaoKJ
	 *            the _jiuBiaoKJ to set
	 */
	public void setJiuBiaoKJ(String jiuBiaoKJ) {
		this._jiuBiaoKJ = jiuBiaoKJ;
	}

	/**
	 * @return the _xinBiaoKJ
	 */
	public String getXinBiaoKJ() {
		return _xinBiaoKJ;
	}

	/**
	 * @param _xinBiaoKJ
	 *            the _xinBiaoKJ to set
	 */
	public void setXinBiaoKJ(String xinBiaoKJ) {
		this._xinBiaoKJ = xinBiaoKJ;
	}

	/**
	 * @return the _xinBiaoLC
	 */
	public int getXinBiaoLC() {
		return _xinBiaoLC;
	}

	/**
	 * @param _xinBiaoLC
	 *            the _xinBiaoLC to set
	 */
	public void setXinBiaoLC(int xinBiaoLC) {
		this._xinBiaoLC = xinBiaoLC;
	}

	/**
	 * @return the _shangCiCM
	 */
	public int getShangCiCM() {
		return _shangCiCM;
	}

	/**
	 * @param _shangCiCM
	 *            the _shangCiCM to set
	 */
	public void setShangCiCM(int shangCiCM) {
		this._shangCiCM = shangCiCM;
	}

	/**
	 * @return the _jiuBiaoCM
	 */
	public int getJiuBiaoCM() {
		return _jiuBiaoCM;
	}

	/**
	 * @param _jiuBiaoCM
	 *            the _jiuBiaoCM to set
	 */
	public void setJiuBiaoCM(int jiuBiaoCM) {
		this._jiuBiaoCM = jiuBiaoCM;
	}

	/**
	 * @return the _xinBiaoDM
	 */
	public int getXinBiaoDM() {
		return _xinBiaoDM;
	}

	/**
	 * @param _xinBiaoDM
	 *            the _xinBiaoDM to set
	 */
	public void setXinBiaoDM(int xinBiaoDM) {
		this._xinBiaoDM = xinBiaoDM;
	}

	/**
	 * @return the _shiGongRQ
	 */
	public long getShiGongRQ() {
		return _shiGongRQ;
	}

	/**
	 * @param _shiGongRQ
	 *            the _shiGongRQ to set
	 */
	public void setShiGongRQ(long shiGongRQ) {
		this._shiGongRQ = shiGongRQ;
	}

	/**
	 * @return the _huiTianRBH
	 */
	public String getHuiTianR() {
		return _huiTianR;
	}

	/**
	 * @param _huiTianRBH
	 *            the _huiTianRBH to set
	 */
	public void setHuiTianR(String huiTianR) {
		this._huiTianR = huiTianR;
	}

	/**
	 * @return the _huanBiaoHTRQ
	 */
	public long getHuanBiaoHTRQ() {
		return _huanBiaoHTRQ;
	}

	/**
	 * @param _huanBiaoHTRQ
	 *            the _huanBiaoHTRQ to set
	 */
	public void setHuanBiaoHTRQ(long huanBiaoHTRQ) {
		this._huanBiaoHTRQ = huanBiaoHTRQ;
	}

	/**
	 * @return the _huanBiaoZT
	 */
	public String getHuanBiaoZT() {
		return _huanBiaoZT;
	}

	/**
	 * @param _huanBiaoZT
	 *            the _huanBiaoZT to set
	 */
	public void setHuanBiaoZT(String huanBiaoZT) {
		this._huanBiaoZT = huanBiaoZT;
	}

	/**
	 * @return the _beiZhu
	 */
	public String getBeiZhu() {
		return _beiZhu;
	}

	/**
	 * @param _beiZhu
	 *            the _beiZhu to set
	 */
	public void setBeiZhu(String beiZhu) {
		this._beiZhu = beiZhu;
	};
	
	public static HuanBiaoJL fromJSON(JSONObject object)
	{
		return null;
	}
	
	public static List<HuanBiaoJL> fromJSONArray(JSONArray object) {
		return null;
	}

}
