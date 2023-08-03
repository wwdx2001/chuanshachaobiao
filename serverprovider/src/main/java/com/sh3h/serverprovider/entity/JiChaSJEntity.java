/**
 *
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liukaiyu
 *
 */
public class JiChaSJEntity {

	private int _id;
	/**
	 * 任务编号
	 */
	private int I_RenWuBH;

	public int getI_RenWuBH() {
		return I_RenWuBH;
	}

	public void setI_RenWuBH(int i_RenWuBH) {
		I_RenWuBH = i_RenWuBH;
	}

	private String _cH;
	private int _ceNeiXH;
	private String _cID;
	private String _sT;
	private int _chaoBiaoN;
	private int _chaoBiaoYue;
	private int _chaoCi;
	private long _chaoBiaoRQ;
	private int _shangciCM;
	private int benCiCM;
	private int _chaoJIanSL;
	private int _zhaungTaiBM;
	private String _zhuangTaiMC;
	private long _shangciCBRQ;
	private int _shangCiZTBM;
	private String _shangCiZTMC;
	private int _shangCiCJSL;
	private int _shangCiZTLXS;
	private int _pingJunL1;
	private int _pingJunL2;
	private int _pingJunL3;
	private double _jE;
	private String _zongBiaoCID;
	private String _ChaoBiaoY;
	private int _chaoBiaoBZ;
	private int _jiuBiaoCM;
	private int _xinBiaoDM;
	private long _huanBiaoRQ;
	private int _fangShiBM;
	private int _liangGaoLDYYBM;
	private int _chaoBiaoID;
	private int _zhuangTaiLXS;
	private int _shuiBiaoBL;
	private double _yongShuiZKL;
	private double _paiShuiZKL;
	private int _tiaoJiaH;
	private String _jianHao;
	private long _xiaZaiSJ;
	private int _lingYongSLSM;
	private int _liangGaoSL;
	private int _liangDiSL;
	private String _x1;
	private String _y1;
	private String _x;
	private String _y;
	private String _chaoBiaobeiZhu;
	private int _ceNeiPX;
	private int I_XiaZaiCS;
	private long D_ZuiHouYCXZSJ;
	private long D_ZuiHouYCSCSJ;
	private int I_ShangChuanBZ;
	private int I_ShenHeBZ; // 审核标志
	//private int I_YanChiBZ;
	//private int I_KaiZhangBZ;
	private int I_LastReadingChild;
	private int I_ReadingChild;

	private int _jiChaCM;
	private int _jiChaSL;
	private long _jiChaCBRQ;
	private int _jiChaZTBM;
	private String _jiChaZTMC;

	public JiChaSJEntity() {

	}

	public JiChaSJEntity(int _id,
						 int i_RenWuBH,
						 String _cH,
						 int _ceNeiXH,
						 String _cID,
						 String _sT,
						 int _chaoBiaoN,
						 int _chaoBiaoYue,
						 int _chaoCi,
						 long _chaoBiaoRQ,
						 int _shangciCM,
						 int benCiCM,
						 int _chaoJIanSL,
						 int _zhaungTaiBM,
						 String _zhuangTaiMC,
						 long _shangciCBRQ,
						 int _shangCiZTBM,
						 String _shangCiZTMC,
						 int _shangCiCJSL,
						 int _shangCiZTLXS,
						 int _pingJunL1,
						 int _pingJunL2,
						 int _pingJunL3,
						 double _jE,
						 String _zongBiaoCID,
						 String _ChaoBiaoY,
						 int _chaoBiaoBZ,
						 int _jiuBiaoCM,
						 int _xinBiaoDM,
						 long _huanBiaoRQ,
						 int _fangShiBM,
						 int _liangGaoLDYYBM,
						 int _chaoBiaoID,
						 int _zhuangTaiLXS,
						 int _shuiBiaoBL,
						 double _yongShuiZKL,
						 double _paiShuiZKL,
						 int _tiaoJiaH,
						 String _jianHao,
						 long _xiaZaiSJ,
						 int _lingYongSLSM,
						 int _liangGaoSL,
						 int _liangDiSL,
						 String _x1,
						 String _y1,
						 String _x,
						 String _y,
						 String _chaoBiaobeiZhu,
						 int _ceNeiPX,
						 int i_XiaZaiCS,
						 long d_ZuiHouYCXZSJ,
						 long d_ZuiHouYCSCSJ,
						 int i_ShangChuanBZ,
						 int i_ShenHeBZ,
						 int i_LastReadingChild,
						 int i_ReadingChild,
						 int 	i_jiChaCM, int i_jiChaSL, int i_jiChaZTBM, long d_jiChaCBRQ, String s_jiChaZTMC
	) {
		this._id = _id;
		I_RenWuBH = i_RenWuBH;
		this._cH = _cH;
		this._ceNeiXH = _ceNeiXH;
		this._cID = _cID;
		this._sT = _sT;
		this._chaoBiaoN = _chaoBiaoN;
		this._chaoBiaoYue = _chaoBiaoYue;
		this._chaoCi = _chaoCi;
		this._chaoBiaoRQ = _chaoBiaoRQ;
		this._shangciCM = _shangciCM;
		this.benCiCM = benCiCM;
		this._chaoJIanSL = _chaoJIanSL;
		this._zhaungTaiBM = _zhaungTaiBM;
		this._zhuangTaiMC = _zhuangTaiMC;
		this._shangciCBRQ = _shangciCBRQ;
		this._shangCiZTBM = _shangCiZTBM;
		this._shangCiZTMC = _shangCiZTMC;
		this._shangCiCJSL = _shangCiCJSL;
		this._shangCiZTLXS = _shangCiZTLXS;
		this._pingJunL1 = _pingJunL1;
		this._pingJunL2 = _pingJunL2;
		this._pingJunL3 = _pingJunL3;
		this._jE = _jE;
		this._zongBiaoCID = _zongBiaoCID;
		this._ChaoBiaoY = _ChaoBiaoY;
		this._chaoBiaoBZ = _chaoBiaoBZ;
		this._jiuBiaoCM = _jiuBiaoCM;
		this._xinBiaoDM = _xinBiaoDM;
		this._huanBiaoRQ = _huanBiaoRQ;
		this._fangShiBM = _fangShiBM;
		this._liangGaoLDYYBM = _liangGaoLDYYBM;
		this._chaoBiaoID = _chaoBiaoID;
		this._zhuangTaiLXS = _zhuangTaiLXS;
		this._shuiBiaoBL = _shuiBiaoBL;
		this._yongShuiZKL = _yongShuiZKL;
		this._paiShuiZKL = _paiShuiZKL;
		this._tiaoJiaH = _tiaoJiaH;
		this._jianHao = _jianHao;
		this._xiaZaiSJ = _xiaZaiSJ;
		this._lingYongSLSM = _lingYongSLSM;
		this._liangGaoSL = _liangGaoSL;
		this._liangDiSL = _liangDiSL;
		this._x1 = _x1;
		this._y1 = _y1;
		this._x = _x;
		this._y = _y;
		this._chaoBiaobeiZhu = _chaoBiaobeiZhu;
		this._ceNeiPX = _ceNeiPX;
		I_XiaZaiCS = i_XiaZaiCS;
		D_ZuiHouYCXZSJ = d_ZuiHouYCXZSJ;
		D_ZuiHouYCSCSJ = d_ZuiHouYCSCSJ;
		I_ShangChuanBZ = i_ShangChuanBZ;
		I_ShenHeBZ = i_ShenHeBZ;
		I_LastReadingChild = i_LastReadingChild;
		I_ReadingChild = i_ReadingChild;
		this._jiChaSL=i_jiChaSL;
		this._jiChaCM=i_jiChaCM;
		this._jiChaCBRQ=d_jiChaCBRQ;
		this._jiChaZTBM=i_jiChaZTBM;
		this._jiChaZTMC=s_jiChaZTMC;
	}

	public int getI_XiaZaiCS() {
		return I_XiaZaiCS;
	}

	public void setI_XiaZaiCS(int i_XiaZaiCS) {
		I_XiaZaiCS = i_XiaZaiCS;
	}

	public long getD_ZuiHouYCXZSJ() {
		return D_ZuiHouYCXZSJ;
	}

	public void setD_ZuiHouYCXZSJ(long d_ZuiHouYCXZSJ) {
		D_ZuiHouYCXZSJ = d_ZuiHouYCXZSJ;
	}

	public long getD_ZuiHouYCSCSJ() {
		return D_ZuiHouYCSCSJ;
	}

	public void setD_ZuiHouYCSCSJ(long d_ZuiHouYCSCSJ) {
		D_ZuiHouYCSCSJ = d_ZuiHouYCSCSJ;
	}

	public int getI_ShangChuanBZ() {
		return I_ShangChuanBZ;
	}

	public void setI_ShangChuanBZ(int i_ShangChuanBZ) {
		I_ShangChuanBZ = i_ShangChuanBZ;
	}

	/**
	 * @return the _id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void setId(int _id) {
		this._id = _id;
	}

	/**
	 * @return the _cH
	 */
	public String getCH() {
		return _cH;
	}

	/**
	 * @param _cH
	 *            the _cH to set
	 */
	public void setCH(String _cH) {
		this._cH = _cH;
	}

	/**
	 * @return the _ceNeiXH
	 */
	public int getCeNeiXH() {
		return _ceNeiXH;
	}

	/**
	 * @param _ceNeiXH
	 *            the _ceNeiXH to set
	 */
	public void setCeNeiXH(int _ceNeiXH) {
		this._ceNeiXH = _ceNeiXH;
	}

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
	public void setCID(String _cID) {
		this._cID = _cID;
	}

	/**
	 * @return the _sT
	 */
	public String getST() {
		return _sT;
	}

	/**
	 * @param _sT
	 *            the _sT to set
	 */
	public void setST(String _sT) {
		this._sT = _sT;
	}

	/**
	 * @return the _chaoBiaoN
	 */
	public int getChaoBiaoN() {
		return _chaoBiaoN;
	}

	/**
	 * @param _chaoBiaoN
	 *            the _chaoBiaoN to set
	 */
	public void setChaoBiaoN(int _chaoBiaoN) {
		this._chaoBiaoN = _chaoBiaoN;
	}

	/**
	 * @return the _chaoBiaoYue
	 */
	public int getChaoBiaoYue() {
		return _chaoBiaoYue;
	}

	/**
	 * @param _chaoBiaoYue
	 *            the _chaoBiaoYue to set
	 */
	public void setChaoBiaoYue(int _chaoBiaoYue) {
		this._chaoBiaoYue = _chaoBiaoYue;
	}

	/**
	 * @return the _chaoCi
	 */
	public int getChaoCi() {
		return _chaoCi;
	}

	/**
	 * @param _chaoCi
	 *            the _chaoCi to set
	 */
	public void setChaoCi(int _chaoCi) {
		this._chaoCi = _chaoCi;
	}

	/**
	 * @return the _chaoBiaoRQ
	 */
	public long getChaoBiaoRQ() {
		return _chaoBiaoRQ;
	}

	/**
	 * @param _chaoBiaoRQ
	 *            the _chaoBiaoRQ to set
	 */
	public void setChaoBiaoRQ(long _chaoBiaoRQ) {
		this._chaoBiaoRQ = _chaoBiaoRQ;
	}

	/**
	 * @return the _shangciCM
	 */
	public int getShangciCM() {
		return _shangciCM;
	}

	/**
	 * @param _shangciCM
	 *            the _shangciCM to set
	 */
	public void setShangciCM(int _shangciCM) {
		this._shangciCM = _shangciCM;
	}

	/**
	 * @return the benCiCM
	 */
	public int getBenCiCM() {
		return benCiCM;
	}

	/**
	 * @param benCiCM
	 *            the benCiCM to set
	 */
	public void setBenCiCM(int benCiCM) {
		this.benCiCM = benCiCM;
	}

	/**
	 * @return the _chaoJIanSL
	 */
	public int getChaoJIanSL() {
		return _chaoJIanSL;
	}

	/**
	 * @param _chaoJIanSL
	 *            the _chaoJIanSL to set
	 */
	public void setChaoJIanSL(int _chaoJIanSL) {
		this._chaoJIanSL = _chaoJIanSL;
	}

	/**
	 * @return the _zhaungTaiBM
	 */
	public int getZhuangTaiBM() {
		return _zhaungTaiBM;
	}

	/**
	 * @param _zhaungTaiBM
	 *            the _zhaungTaiBM to set
	 */
	public void setZhuangTaiBM(int _zhaungTaiBM) {
		this._zhaungTaiBM = _zhaungTaiBM;
	}

	/**
	 * @return the _zhuangTaiMC
	 */
	public String getZhuangTaiMC() {
		return _zhuangTaiMC;
	}

	/**
	 * @param _zhuangTaiMC
	 *            the _zhuangTaiMC to set
	 */
	public void setZhuangTaiMC(String _zhuangTaiMC) {
		this._zhuangTaiMC = _zhuangTaiMC;
	}

	/**
	 * @return the _shangciCBRQ
	 */
	public long getShangciCBRQ() {
		return _shangciCBRQ;
	}

	/**
	 * @param _shangciCBRQ
	 *            the _shangciCBRQ to set
	 */
	public void setShangciCBRQ(long _shangciCBRQ) {
		this._shangciCBRQ = _shangciCBRQ;
	}

	/**
	 * @return the _shangCiZTBM
	 */
	public int getShangCiZTBM() {
		return _shangCiZTBM;
	}

	/**
	 * @param _shangCiZTBM
	 *            the _shangCiZTBM to set
	 */
	public void setShangCiZTBM(int _shangCiZTBM) {
		this._shangCiZTBM = _shangCiZTBM;
	}

	/**
	 * @return the _shangCiZTMC
	 */
	public String getShangCiZTMC() {
		return _shangCiZTMC;
	}

	/**
	 * @param _shangCiZTMC
	 *            the _shangCiZTMC to set
	 */
	public void setShangCiZTMC(String _shangCiZTMC) {
		this._shangCiZTMC = _shangCiZTMC;
	}

	/**
	 * @return the _shangCiCJSL
	 */
	public int getShangCiCJSL() {
		return _shangCiCJSL;
	}

	/**
	 * @param _shangCiCJSL
	 *            the _shangCiCJSL to set
	 */
	public void setShangCiCJSL(int _shangCiCJSL) {
		this._shangCiCJSL = _shangCiCJSL;
	}

	/**
	 * @return the _shangCiZTLXS
	 */
	public int getShangCiZTLXS() {
		return _shangCiZTLXS;
	}

	/**
	 * @param _shangCiZTLXS
	 *            the _shangCiZTLXS to set
	 */
	public void setShangCiZTLXS(int _shangCiZTLXS) {
		this._shangCiZTLXS = _shangCiZTLXS;
	}

	/**
	 * @return the _pingJunL1
	 */
	public int getPingJunL1() {
		return _pingJunL1;
	}

	/**
	 * @param _pingJunL1
	 *            the _pingJunL1 to set
	 */
	public void setPingJunL1(int _pingJunL1) {
		this._pingJunL1 = _pingJunL1;
	}

	/**
	 * @return the _pingJunL2
	 */
	public int getPingJunL2() {
		return _pingJunL2;
	}

	/**
	 * @param _pingJunL2
	 *            the _pingJunL2 to set
	 */
	public void setPingJunL2(int _pingJunL2) {
		this._pingJunL2 = _pingJunL2;
	}

	/**
	 * @return the _pingJunL3
	 */
	public int getPingJunL3() {
		return _pingJunL3;
	}

	/**
	 * @param _pingJunL3
	 *            the _pingJunL3 to set
	 */
	public void setPingJunL3(int _pingJunL3) {
		this._pingJunL3 = _pingJunL3;
	}

	/**
	 * @return the _jE
	 */
	public double getJE() {
		return _jE;
	}

	/**
	 * @param _jE
	 *            the _jE to set
	 */
	public void setJE(double _jE) {
		this._jE = _jE;
	}

	/**
	 * @return the _zongBiaoCID
	 */
	public String getZongBiaoCID() {
		return _zongBiaoCID;
	}

	/**
	 * @param _zongBiaoCID
	 *            the _zongBiaoCID to set
	 */
	public void setZongBiaoCID(String _zongBiaoCID) {
		this._zongBiaoCID = _zongBiaoCID;
	}

	/**
	 * @return the _ChaoBiaoY
	 */
	public String getChaoBiaoY() {
		return _ChaoBiaoY;
	}

	/**
	 * @param _ChaoBiaoY
	 *            the _ChaoBiaoY to set
	 */
	public void setChaoBiaoY(String _ChaoBiaoY) {
		this._ChaoBiaoY = _ChaoBiaoY;
	}

	/**
	 * @return the _chaoBiaoBZ
	 */
	public int getChaoBiaoBZ() {
		return _chaoBiaoBZ;
	}

	/**
	 * @param _chaoBiaoBZ
	 *            the _chaoBiaoBZ to set
	 */
	public void setChaoBiaoBZ(int _chaoBiaoBZ) {
		this._chaoBiaoBZ = _chaoBiaoBZ;
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
	public void setJiuBiaoCM(int _jiuBiaoCM) {
		this._jiuBiaoCM = _jiuBiaoCM;
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
	public void setXinBiaoDM(int _xinBiaoDM) {
		this._xinBiaoDM = _xinBiaoDM;
	}

	/**
	 * @return the _huanBiaoRQ
	 */
	public long getHuanBiaoRQ() {
		return _huanBiaoRQ;
	}

	/**
	 * @param _huanBiaoRQ
	 *            the _huanBiaoRQ to set
	 */
	public void setHuanBiaoRQ(long _huanBiaoRQ) {
		this._huanBiaoRQ = _huanBiaoRQ;
	}

	/**
	 * @return the _fangShiBM
	 */
	public int getFangShiBM() {
		return _fangShiBM;
	}

	/**
	 * @param _fangShiBM
	 *            the _fangShiBM to set
	 */
	public void setFangShiBM(int _fangShiBM) {
		this._fangShiBM = _fangShiBM;
	}

	/**
	 * @return the _liangGaoLDYYBM
	 */
	public int getLiangGaoLDYYBM() {
		return _liangGaoLDYYBM;
	}

	/**
	 * @param _liangGaoLDYYBM
	 *            the _liangGaoLDYYBM to set
	 */
	public void setLiangGaoLDYYBM(int _liangGaoLDYYBM) {
		this._liangGaoLDYYBM = _liangGaoLDYYBM;
	}

	/**
	 * @return the _chaoBiaoID
	 */
	public int getChaoBiaoID() {
		return _chaoBiaoID;
	}

	/**
	 * @param _chaoBiaoID
	 *            the _chaoBiaoID to set
	 */
	public void setChaoBiaoID(int _chaoBiaoID) {
		this._chaoBiaoID = _chaoBiaoID;
	}

	/**
	 * @return the _zhuangTaiLXS
	 */
	public int getZhuangTaiLXS() {
		return _zhuangTaiLXS;
	}

	/**
	 * @param _zhuangTaiLXS
	 *            the _zhuangTaiLXS to set
	 */
	public void setZhuangTaiLXS(int _zhuangTaiLXS) {
		this._zhuangTaiLXS = _zhuangTaiLXS;
	}

	/**
	 * @return the _shuiBiaoBL
	 */
	public int getShuiBiaoBL() {
		return _shuiBiaoBL;
	}

	/**
	 * @param _shuiBiaoBL
	 *            the _shuiBiaoBL to set
	 */
	public void setShuiBiaoBL(int _shuiBiaoBL) {
		this._shuiBiaoBL = _shuiBiaoBL;
	}

	/**
	 * @return the _yongShuiZKL
	 */
	public double getYongShuiZKL() {
		return _yongShuiZKL;
	}

	/**
	 * @param _yongShuiZKL
	 *            the _yongShuiZKL to set
	 */
	public void setYongShuiZKL(double _yongShuiZKL) {
		this._yongShuiZKL = _yongShuiZKL;
	}

	/**
	 * @return the _paiShuiZKL
	 */
	public double getPaiShuiZKL() {
		return _paiShuiZKL;
	}

	/**
	 * @param _paiShuiZKL
	 *            the _paiShuiZKL to set
	 */
	public void setPaiShuiZKL(double _paiShuiZKL) {
		this._paiShuiZKL = _paiShuiZKL;
	}

	/**
	 * @return the _tiaoJiaHl
	 */
	public int getTiaoJiaH() {
		return _tiaoJiaH;
	}


	public void setTiaoJiaH(int _tiaoJiaH) {
		this._tiaoJiaH = _tiaoJiaH;
	}

	/**
	 * @return the _jianHao
	 */
	public String getJianHao() {
		return _jianHao;
	}

	/**
	 * @param _jianHao
	 *            the _jianHao to set
	 */
	public void setJianHao(String _jianHao) {
		this._jianHao = _jianHao;
	}

	/**
	 * @return the _xiaZaiSJ
	 */
	public long getXiaZaiSJ() {
		return _xiaZaiSJ;
	}

	/**
	 * @param _xiaZaiSJ
	 *            the _xiaZaiSJ to set
	 */
	public void setXiaZaiSJ(long _xiaZaiSJ) {
		this._xiaZaiSJ = _xiaZaiSJ;
	}

	/**
	 * @return the _lingYongSLSM
	 */
	public int getLingYongSLSM() {
		return _lingYongSLSM;
	}

	/**
	 * @param _lingYongSLSM
	 *            the _lingYongSLSM to set
	 */
	public void setLingYongSLSM(int _lingYongSLSM) {
		this._lingYongSLSM = _lingYongSLSM;
	}

	/**
	 * @return the _liangGaoSL
	 */
	public int getLiangGaoSL() {
		return _liangGaoSL;
	}

	/**
	 * @param _liangGaoSL
	 *            the _liangGaoSL to set
	 */
	public void setLiangGaoSL(int _liangGaoSL) {
		this._liangGaoSL = _liangGaoSL;
	}

	/**
	 * @return the _liangDiDL
	 */
	public int getLiangDiSL() {
		return _liangDiSL;
	}

	public void setLiangDiSL(int _liangDiSL) {
		this._liangDiSL = _liangDiSL;
	}

	/**
	 * @return the _x1
	 */
	public String getX1() {
		return _x1;
	}

	/**
	 * @param _x1
	 *            the _x1 to set
	 */
	public void setX1(String _x1) {
		this._x1 = _x1;

	}

	/**
	 * @return the _y1
	 */
	public String getY1() {
		return _y1;
	}

	/**
	 * @param _y1
	 *            the _y1 to set
	 */
	public void setY1(String _y1) {
		this._y1 = _y1;
	}

	public int get_jiChaCM() {
		return _jiChaCM;
	}

	public int get_jiChaSL() {
		return _jiChaSL;
	}

	public void set_jiChaSL(int _jiChaSL) {
		this._jiChaSL = _jiChaSL;
	}

	public long get_jiChaCBRQ() {
		return _jiChaCBRQ;
	}

	public void set_jiChaCBRQ(long _jiChaCBRQ) {
		this._jiChaCBRQ = _jiChaCBRQ;
	}

	public int get_jiChaZTBM() {
		return _jiChaZTBM;
	}

	public void set_jiChaZTBM(int _jiChaZTBM) {
		this._jiChaZTBM = _jiChaZTBM;
	}

	public String get_jiChaZTMC() {
		return _jiChaZTMC;
	}

	public void set_jiChaZTMC(String _jiChaZTMC) {
		this._jiChaZTMC = _jiChaZTMC;
	}

	public void set_jiChaCM(int _jiChaCM) {
		this._jiChaCM = _jiChaCM;
	}

	/**
	 * @return the _x
	 */
	public String getX() {
		return _x;
	}

	/**
	 * @param _x
	 *            the _x to set
	 */
	public void setX(String _x) {
		this._x = _x;
	}

	/**
	 * @return the _y
	 */
	public String getY() {
		return _y;
	}

	/**
	 * @param _y
	 *            the _y to set
	 */
	public void setY(String _y) {
		this._y = _y;
	}

	/**
	 * @return the _chaoBiaoBiaoZhu
	 */
	public String getChaoBiaoBeiZhu() {
		return _chaoBiaobeiZhu;
	}

	public void setChaoBiaoBeiZhu(String _chaoBiaobeiZhu) {
		this._chaoBiaobeiZhu = _chaoBiaobeiZhu;
	}

	public int getceNeiPX() {
		return _ceNeiPX;
	}

	public void setceNeiPX(int ceNeiPX) {
		this._ceNeiPX = ceNeiPX;
	}

	public int getI_ShenHeBZ() {
		return I_ShenHeBZ;
	}

	public void setI_ShenHeBZ(int i_ShenHeBZ) {
		I_ShenHeBZ = i_ShenHeBZ;
	}

	//public int getI_YanChiBZ() {
	//    return I_YanChiBZ;
	//}

	//public void setI_YanChiBZ(int i_YanChiBZ) {
	//    I_YanChiBZ = i_YanChiBZ;
	//}

	//public int getI_KaiZhangBZ() {
	//    return I_KaiZhangBZ;
	//}

	//public void setI_KaiZhangBZ(int i_KaiZhangBZ) {
	//    I_KaiZhangBZ = i_KaiZhangBZ;
	//}

	public int getI_LastReadingChild() {
		return I_LastReadingChild;
	}

	public void setI_LastReadingChild(int i_LastReadingChild) {
		I_LastReadingChild = i_LastReadingChild;
	}

	public int getI_ReadingChild() {
		return I_ReadingChild;
	}

	public void setI_ReadingChild(int i_ReadingChild) {
		I_ReadingChild = i_ReadingChild;
	}

	/**
	 * 转换JSONObject对象为JiChaSJEntity
	 *
	 * @param object
	 * @return
	 */
	public static JiChaSJEntity fromJSON(JSONObject object) {
		JiChaSJEntity cbsj = new JiChaSJEntity();

		cbsj.setBenCiCM(object.optInt("i_BenCiCM"));
		cbsj.setceNeiPX(object.optInt("i_CeNeiPX"));
		cbsj.setCeNeiXH(object.optInt("i_CeNeiXH"));
		cbsj.setCH(object.optString("s_CH"));
		cbsj.setChaoBiaoBeiZhu(object.optString("s_CHAOBIAOBZ"));
		cbsj.setChaoBiaoBZ(object.optInt("i_CHAOBIAOBZ"));
		cbsj.setChaoBiaoID(object.optInt("i_CHAOBIAOID"));
		cbsj.setChaoBiaoN(object.optInt("i_ChaoBiaoN"));
		cbsj.setChaoBiaoRQ(object.optLong("d_ChaoBiaoRQ"));
		cbsj.setChaoBiaoY(object.optString("s_CHAOBIAOY"));
		cbsj.setChaoBiaoYue(object.optInt("i_ChaoBiaoY"));
		cbsj.setChaoCi(object.optInt("i_ChaoCi"));
		cbsj.setChaoJIanSL(object.optInt("i_ChaoJianSL"));
		cbsj.setCID(object.optString("s_CID"));
		cbsj.setD_ZuiHouYCSCSJ(object.optLong("d_ZuiHouYCSCSJ"));
		cbsj.setD_ZuiHouYCXZSJ(object.optLong("d_ZuiHouYCXZSJ"));
		cbsj.setFangShiBM(object.optInt("i_FANGSHIBM"));
		cbsj.setHuanBiaoRQ(object.optLong("d_HUANBIAORQ"));
		cbsj.setI_RenWuBH(object.optInt("i_RenWuBH"));
		cbsj.setI_ShangChuanBZ(object.optInt("i_ShangChuanBZ"));
		cbsj.setI_ShenHeBZ(object.optInt("i_ShenHeBZ"));
		//cbsj.setI_YanChiBZ(object.optInt("i_YanChiBZ"));
		//cbsj.setI_KaiZhangBZ(object.optInt("i_KaiZhangBZ"));
		cbsj.setI_XiaZaiCS(object.optInt("i_XiaZaiCS"));
		cbsj.setId(object.optInt("iD"));
		cbsj.setJE(object.optDouble("n_JE"));
		cbsj.setJianHao(object.optString("s_JIANHAO"));
		cbsj.setJiuBiaoCM(object.optInt("i_JIUBIAOCM"));
		cbsj.setLiangDiSL(object.optInt("i_LIANGDISL"));
		cbsj.setLiangGaoLDYYBM(object.optInt("i_LIANGGAOLDYYBM"));
		cbsj.setLiangGaoSL(object.optInt("i_LIANGGAOSL"));
		cbsj.setLingYongSLSM(object.optInt("i_LINGYONGSLSM"));
		cbsj.setPaiShuiZKL(object.optDouble("n_PAISHUIZKL"));
		cbsj.setPingJunL1(object.optInt("i_PINGJUNL1"));
		cbsj.setPingJunL2(object.optInt("i_PINGJUNL2"));
		cbsj.setPingJunL3(object.optInt("i_PINGJUNL3"));
		cbsj.setShangciCBRQ(object.optLong("d_ShangCiCBRQ"));
		cbsj.setShangCiCJSL(object.optInt("i_ShangCiCJSL"));
		cbsj.setShangciCM(object.optInt("i_ShangCiCM"));
		cbsj.setShangCiZTBM(object.optInt("i_ShangCiZTBM"));
		cbsj.setShangCiZTLXS(object.optInt("i_ShangCiZTLXS"));
		cbsj.setShangCiZTMC(object.optString("s_ShangCiZTMC"));
		cbsj.setShuiBiaoBL(object.optInt("i_SHUIBIAOBL"));
		cbsj.setST(object.optString("s_ST"));
		cbsj.setTiaoJiaH(object.optInt("i_TIAOJIAH"));
		cbsj.setX(object.optString("s_X"));
		cbsj.setX1(object.optString("s_X1"));
		cbsj.setXiaZaiSJ(object.optLong("d_XIAZAISJ"));
		cbsj.setXinBiaoDM(object.optInt("i_XINBIAODM"));
		cbsj.setY(object.optString("s_Y"));
		cbsj.setY1(object.optString("s_Y1"));
		cbsj.setYongShuiZKL(object.optDouble("n_YONGSHUIZKL"));
		cbsj.setZhuangTaiBM(object.optInt("i_ZHUANGTAIBM"));
		cbsj.setZhuangTaiLXS(object.optInt("i_ZHUANGTAILXS"));
		cbsj.setZhuangTaiMC(object.optString("s_ZHUANGTAIMC"));
		cbsj.setZongBiaoCID(object.optString("s_ZONGBIAOCID"));
		cbsj.setI_LastReadingChild(object.optInt("i_LastReadingChild"));
		cbsj.setI_ReadingChild(object.optInt("i_ReadingChild"));

		cbsj.set_jiChaCM(object.optInt("i_JiChaCM"));
		cbsj.set_jiChaSL(object.optInt("i_JiChaSL"));
		cbsj.set_jiChaZTBM(object.optInt("i_JiChaZTBM"));
		cbsj.set_jiChaCBRQ(object.optLong("d_JiChaCBRQ"));
		cbsj.set_jiChaZTMC(object.optString("s_JiChaZTMC"));

		return cbsj;
	}

	/**
	 * 转换JSONArray对象为List<JiChaSJEntity>
	 *
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	public static List<JiChaSJEntity> fromJSONArray(JSONArray array)
			throws JSONException {
		List<JiChaSJEntity> list = new ArrayList<JiChaSJEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			JiChaSJEntity cbsj = JiChaSJEntity.fromJSON(object);
			list.add(cbsj);
		}
		return list;
	}

	/**
	 * 转换JiChaSJEntity对象为JSONObject
	 *
	 * @param cbsj
	 * @return
	 */
	public JSONObject toJSON(JiChaSJEntity cbsj) throws JSONException {
		JSONObject object = new JSONObject();

		object.put("ID", cbsj.getId());
		object.put("I_RenWuBH", cbsj.getI_RenWuBH());
		object.put("S_CH", cbsj.getCH());
		object.put("I_CeNeiXH", cbsj.getCeNeiXH());
		object.put("S_CID", cbsj.getCID());
		object.put("S_ST", cbsj.getST());
		object.put("I_ChaoBiaoN", cbsj.getChaoBiaoN());
		object.put("I_ChaoBiaoY", cbsj.getChaoBiaoYue());
		object.put("I_ChaoCi", cbsj.getChaoCi());
		object.put("D_ChaoBiaoRQ", cbsj.getChaoBiaoRQ());
		object.put("I_ShangCiCM", cbsj.getShangciCM());
		object.put("I_BenCiCM", cbsj.getBenCiCM());
		object.put("I_ChaoJianSL", cbsj.getChaoJIanSL());
		object.put("I_ZHUANGTAIBM", cbsj.getZhuangTaiBM());
		object.put("S_ZHUANGTAIMC", cbsj.getZhuangTaiMC());
		object.put("D_ShangCiCBRQ", cbsj.getShangciCBRQ());
		object.put("I_ShangCiZTBM", cbsj.getShangCiZTBM());
		object.put("S_ShangCiZTMC", cbsj.getShangCiZTMC());
		object.put("I_ShangCiCJSL", cbsj.getShangCiCJSL());
		object.put("I_ShangCiZTLXS", cbsj.getShangCiZTLXS());
		object.put("I_PINGJUNL1", cbsj.getPingJunL1());
		object.put("I_PINGJUNL2", cbsj.getPingJunL2());
		object.put("I_PINGJUNL3", cbsj.getPingJunL3());
		object.put("N_JE", cbsj.getJE());
		object.put("S_ZONGBIAOCID", cbsj.getZongBiaoCID());
		object.put("S_CHAOBIAOY", cbsj.getChaoBiaoY());
		object.put("I_CHAOBIAOBZ", cbsj.getChaoBiaoBZ());
		object.put("I_JIUBIAOCM", cbsj.getJiuBiaoCM());
		object.put("I_XINBIAODM", cbsj.getXinBiaoDM());
		object.put("D_HUANBIAORQ", cbsj.getHuanBiaoRQ());
		object.put("I_FANGSHIBM", cbsj.getFangShiBM());
		object.put("I_LIANGGAOLDYYBM", cbsj.getLiangGaoLDYYBM());
		object.put("I_CHAOBIAOID", cbsj.getChaoBiaoID());
		object.put("I_ZHUANGTAILXS", cbsj.getZhuangTaiLXS());
		object.put("I_SHUIBIAOBL", cbsj.getShuiBiaoBL());
		object.put("N_YONGSHUIZKL", cbsj.getYongShuiZKL());
		object.put("N_PAISHUIZKL", cbsj.getPaiShuiZKL());
		object.put("I_TIAOJIAH", cbsj.getTiaoJiaH());
		object.put("S_JIANHAO", cbsj.getJianHao());
		object.put("D_XIAZAISJ", cbsj.getXiaZaiSJ());
		object.put("I_LINGYONGSLSM", cbsj.getLingYongSLSM());
		object.put("I_LIANGGAOSL", cbsj.getLiangGaoSL());
		object.put("I_LIANGDISL", cbsj.getLiangDiSL());
		object.put("S_X1", cbsj.getX1());
		object.put("S_Y1", cbsj.getY1());
		object.put("S_X", cbsj.getX());
		object.put("S_Y", cbsj.getY());
		object.put("S_CHAOBIAOBZ", cbsj.getChaoBiaoBeiZhu());
		object.put("I_XiaZaiCS", cbsj.getI_XiaZaiCS());
		object.put("D_ZuiHouYCXZSJ", cbsj.getD_ZuiHouYCXZSJ());
		object.put("D_ZuiHouYCSCSJ", cbsj.getD_ZuiHouYCSCSJ());
		object.put("I_ShangChuanBZ", cbsj.getI_ShangChuanBZ());
		object.put("I_ShenHeBZ", cbsj.getI_ShenHeBZ());
		object.put("I_LastReadingChild", cbsj.getI_LastReadingChild());
		object.put("I_ReadingChild", cbsj.getI_ReadingChild());

		object.put("I_JiChaSL", cbsj.get_jiChaSL());
		object.put("I_JiChaCM", cbsj.get_jiChaCM());
		object.put("D_JiChaCBRQ", cbsj.get_jiChaCBRQ());
		object.put("I_JiChaZTBM", cbsj.get_jiChaZTBM());
		object.put("S_JiChaZTMC", cbsj.get_jiChaZTMC());
		return object;
	}

	/**
	 * 转换JiChaSJEntity对象为JSONObject
	 *
	 * @return
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();

		object.put("ID", getId());
		object.put("I_RenWuBH", getI_RenWuBH());
		object.put("S_CH", getCH());
		object.put("I_CeNeiXH", getCeNeiXH());
		object.put("S_CID", getCID());
		object.put("S_ST", getST());
		object.put("I_ChaoBiaoN", getChaoBiaoN());
		object.put("I_ChaoBiaoY", getChaoBiaoYue());
		object.put("I_ChaoCi", getChaoCi());
		object.put("D_ChaoBiaoRQ", getChaoBiaoRQ());
		object.put("I_ShangCiCM", getShangciCM());
		object.put("I_BenCiCM", getBenCiCM());
		object.put("I_ChaoJianSL", getChaoJIanSL());
		object.put("I_ZHUANGTAIBM", getZhuangTaiBM());
		object.put("S_ZHUANGTAIMC", getZhuangTaiMC());
		object.put("D_ShangCiCBRQ", getShangciCBRQ());
		object.put("I_ShangCiZTBM", getShangCiZTBM());
		object.put("S_ShangCiZTMC", getShangCiZTMC());
		object.put("I_ShangCiCJSL", getShangCiCJSL());
		object.put("I_ShangCiZTLXS", getShangCiZTLXS());
		object.put("I_PINGJUNL1", getPingJunL1());
		object.put("I_PINGJUNL2", getPingJunL2());
		object.put("I_PINGJUNL3", getPingJunL3());
		object.put("N_JE", getJE());
		object.put("S_ZONGBIAOCID", getZongBiaoCID());
		object.put("S_CHAOBIAOY", getChaoBiaoY());
		object.put("I_CHAOBIAOBZ", getChaoBiaoBZ());
		object.put("I_JIUBIAOCM", getJiuBiaoCM());
		object.put("I_XINBIAODM", getXinBiaoDM());
		object.put("D_HUANBIAORQ", getHuanBiaoRQ());
		object.put("I_FANGSHIBM", getFangShiBM());
		object.put("I_LIANGGAOLDYYBM", getLiangGaoLDYYBM());
		object.put("I_CHAOBIAOID", getChaoBiaoID());
		object.put("I_ZHUANGTAILXS", getZhuangTaiLXS());
		object.put("I_SHUIBIAOBL", getShuiBiaoBL());
		object.put("N_YONGSHUIZKL", getYongShuiZKL());
		object.put("N_PAISHUIZKL", getPaiShuiZKL());
		object.put("I_TIAOJIAH", getTiaoJiaH());
		object.put("S_JIANHAO", getJianHao());
		object.put("D_XIAZAISJ", getXiaZaiSJ());
		object.put("I_LINGYONGSLSM", getLingYongSLSM());
		object.put("I_LIANGGAOSL", getLiangGaoSL());
		object.put("I_LIANGDISL", getLiangDiSL());
		object.put("S_X1", getX1());
		object.put("S_Y1", getY1());
		object.put("S_X", getX());
		object.put("S_Y", getY());
		object.put("S_CHAOBIAOBZ", getChaoBiaoBeiZhu());
		object.put("I_XiaZaiCS", getXiaZaiSJ());
		object.put("D_ZuiHouYCXZSJ", getD_ZuiHouYCXZSJ());
		object.put("D_ZuiHouYCSCSJ", getD_ZuiHouYCSCSJ());
		object.put("I_ShangChuanBZ", getI_ShangChuanBZ());
		object.put("I_ShenHeBZ", getI_ShenHeBZ());
		object.put("I_LastReadingChild", getI_LastReadingChild());
		object.put("I_ReadingChild", getI_ReadingChild());

		object.put("I_JiChaSL", get_jiChaSL());
		object.put("I_JiChaCM", get_jiChaCM());
		object.put("D_JiChaCBRQ", get_jiChaCBRQ());
		object.put("I_JiChaZTBM", get_jiChaZTBM());
		object.put("S_JiChaZTMC", get_jiChaZTMC());


		return object;
	}


	/**
	 * 转换List<JiChaSJEntity>对象为JSONArray
	 *
	 * @param list
	 * @return
	 */
	public JSONArray toJSONArray(List<JiChaSJEntity> list)
			throws JSONException {
		JSONArray array = new JSONArray();

		for (JiChaSJEntity cbsjentity : list) {
			JSONObject object = toJSON(cbsjentity);
			array.put(object);
		}

		return array;
	}

}
