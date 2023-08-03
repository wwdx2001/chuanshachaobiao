/**
 *
 */
package com.sh3h.dataprovider.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 表务工单
 *
 * @author zengdezhi
 *
 */
public class BiaoWuGD {

	private int id;
	private int renWuBH;
	private String CID;
	private String shuiBiaoTXM;
	private String ST;
	private String kouJing;
	private String biaoXingID;
	private String shuiBiaoCJID;
	private String HM;
	private String DZ;
	private int biaoWuGDLX;
	private int ZT;
	private Date dengJiRQ;
	private String dengJiR;
	private int daYinCS;
	private Date shiGongRQ;
	private String shiGongR;
	private Date fuHeRQ;
	private String fuHeR;
	private String beiZhu;
	private int jieGuo;
	private Date huiTianRQ;
	private String huiTianR;
	private String lianXiR;
	private String lianXiDH;
	private Date yuYueRQ;
	private Date shangCiHBRQ;
	private int KID;
	private String caoZuoR;
	private Date caoZuoSJ;
	private int jiuBiaoDS;
	private String xinBiaoTXM;
	private int xinBiaoDS;
	private int biaoWuSQPH;
	private String CH;
	private String paiDanR;
	private Date paiDanSJ;
	private String jieDanR;
	private Date zuiHouSGRQ;
	private String X1;
	private String Y1;
	private String gongDanBH;
	private String keHuQM;
	private String zhaoPianMC;
	private int shangchuanBZ;
	private int chaobiaoBZ;

	// / <summary>
	// / 换表原因°
	// / </summary>
	private String guZhangYY;
	// / <summary>
	// / 上抄表状态 原始状态
	// / </summary>
	// private String _chaoBiaoZT;
	// / <summary>
	// / 换表说明
	// / </summary>
	private String huanBiaoSM;
	// / <summary>
	// / 水表钢印号
	// / </summary>
	private String shuiBiaoGYH;
	// / <summary>
	// / 新表钢印号
	// / </summary>
	private String xinBiaoGYH;
	// / <summary>
	// / 欠费金额
	// / </summary>
	private double qianFeiJE;
	// / <summary>
	// / 检测结果
	// / </summary>
	private String jianCeJG;
	// / <summary>
	// / 拆表原因
	// / </summary>
	private String chaiBiaoYY;
	// / <summary>
	// / 合同号
	// / </summary>
	private String heTongH;
	// / <summary>
	// / 水表规格
	// / </summary>
	private String shuiBiaoGG;
	// / <summary>
	// / 用水性质
	// / </summary>
	private String yongShuiXZ;
	// / <summary>
	// / 审?¨?核?人¨?
	// / </summary>
	private String shenHeR;
	// / <summary>
	// / 审核日期
	// / </summary>
	private Date shenHeRQ;

	private String chaoBiaoZT;

	// 返回结果
	private ResultInfo resultInfo;

	public ResultInfo get_resultInfo() {
		return resultInfo;
	}

	public void set_resultInfo(ResultInfo _resultInfo) {
		this.resultInfo = _resultInfo;
	}

	public int get_id() {
		return id;
	}

	public void set_id(int _id) {
		this.id = _id;
	}

	public int get_renWuBH() {
		return renWuBH;
	}

	public void set_renWuBH(int _renWuBH) {
		this.renWuBH = _renWuBH;
	}

	public String get_CID() {
		return CID;
	}

	public void set_CID(String _CID) {
		this.CID = _CID;
	}

	public String get_shuiBiaoTXM() {
		return shuiBiaoTXM;
	}

	public void set_shuiBiaoTXM(String _shuiBiaoTXM) {
		this.shuiBiaoTXM = _shuiBiaoTXM;
	}

	public String get_ST() {
		return ST;
	}

	public void set_ST(String _ST) {
		this.ST = _ST;
	}

	public String get_kouJing() {
		return kouJing;
	}

	public void set_kouJing(String _kouJing) {
		this.kouJing = _kouJing;
	}

	public String get_biaoXingID() {
		return biaoXingID;
	}

	public void set_biaoXingID(String _biaoXingID) {
		this.biaoXingID = _biaoXingID;
	}

	public String get_shuiBiaoCJID() {
		return shuiBiaoCJID;
	}

	public void set_shuiBiaoCJID(String _shuiBiaoCJID) {
		this.shuiBiaoCJID = _shuiBiaoCJID;
	}

	public String get_HM() {
		return HM;
	}

	public void set_HM(String _HM) {
		this.HM = _HM;
	}

	public String get_DZ() {
		return DZ;
	}

	public void set_DZ(String _DZ) {
		this.DZ = _DZ;
	}

	public int get_biaoWuGDLX() {
		return biaoWuGDLX;
	}

	public void set_biaoWuGDLX(int _biaoWuGDLX) {
		this.biaoWuGDLX = _biaoWuGDLX;
	}

	public int get_ZT() {
		return ZT;
	}

	public void set_ZT(int _ZT) {
		this.ZT = _ZT;
	}

	public Date get_dengJiRQ() {
		return dengJiRQ;
	}

	public void set_dengJiRQ(Date _dengJiRQ) {
		this.dengJiRQ = _dengJiRQ;
	}

	public String get_dengJiR() {
		return dengJiR;
	}

	public void set_dengJiR(String _dengJiR) {
		this.dengJiR = _dengJiR;
	}

	public int get_daYinCS() {
		return daYinCS;
	}

	public void set_daYinCS(int _daYinCS) {
		this.daYinCS = _daYinCS;
	}

	public Date get_shiGongRQ() {
		return shiGongRQ;
	}

	public void set_shiGongRQ(Date _shiGongRQ) {
		this.shiGongRQ = _shiGongRQ;
	}

	public String get_shiGongR() {
		return shiGongR;
	}

	public void set_shiGongR(String _shiGongR) {
		this.shiGongR = _shiGongR;
	}

	public Date get_fuHeRQ() {
		return fuHeRQ;
	}

	public void set_fuHeRQ(Date _fuHeRQ) {
		this.fuHeRQ = _fuHeRQ;
	}

	public String get_fuHeR() {
		return fuHeR;
	}

	public void set_fuHeR(String _fuHeR) {
		this.fuHeR = _fuHeR;
	}

	public String get_beiZhu() {
		return beiZhu;
	}

	public void set_beiZhu(String _beiZhu) {
		this.beiZhu = _beiZhu;
	}

	public int get_jieGuo() {
		return jieGuo;
	}

	public void set_jieGuo(int _jieGuo) {
		this.jieGuo = _jieGuo;
	}

	public Date get_huiTianRQ() {
		return huiTianRQ;
	}

	public void set_huiTianRQ(Date _huiTianRQ) {
		this.huiTianRQ = _huiTianRQ;
	}

	public String get_huiTianR() {
		return huiTianR;
	}

	public void set_huiTianR(String _huiTianR) {
		this.huiTianR = _huiTianR;
	}

	public String get_lianXiR() {
		return lianXiR;
	}

	public void set_lianXiR(String _lianXiR) {
		this.lianXiR = _lianXiR;
	}

	public String get_lianXiDH() {
		return lianXiDH;
	}

	public void set_lianXiDH(String _lianXiDH) {
		this.lianXiDH = _lianXiDH;
	}

	public Date get_yuYueRQ() {
		return yuYueRQ;
	}

	public void set_yuYueRQ(Date _yuYueRQ) {
		this.yuYueRQ = _yuYueRQ;
	}

	public Date get_shangCiHBRQ() {
		return shangCiHBRQ;
	}

	public void set_shangCiHBRQ(Date _shangCiHBRQ) {
		this.shangCiHBRQ = _shangCiHBRQ;
	}

	public int get_KID() {
		return KID;
	}

	public void set_KID(int _KID) {
		this.KID = _KID;
	}

	public String get_caoZuoR() {
		return caoZuoR;
	}

	public void set_caoZuoR(String _caoZuoR) {
		this.caoZuoR = _caoZuoR;
	}

	public Date get_caoZuoSJ() {
		return caoZuoSJ;
	}

	public void set_caoZuoSJ(Date _caoZuoSJ) {
		this.caoZuoSJ = _caoZuoSJ;
	}

	public int get_jiuBiaoDS() {
		return jiuBiaoDS;
	}

	public void set_jiuBiaoDS(int _jiuBiaoDS) {
		this.jiuBiaoDS = _jiuBiaoDS;
	}

	public String get_xinBiaoTXM() {
		return xinBiaoTXM;
	}

	public void set_xinBiaoTXM(String _xinBiaoTXM) {
		this.xinBiaoTXM = _xinBiaoTXM;
	}

	public int get_xinBiaoDS() {
		return xinBiaoDS;
	}

	public void set_xinBiaoDS(int _xianBiaoDS) {
		this.xinBiaoDS = _xianBiaoDS;
	}

	public int get_biaoWuSQPH() {
		return biaoWuSQPH;
	}

	public void set_biaoWuSQPH(int _biaoWuSQPH) {
		this.biaoWuSQPH = _biaoWuSQPH;
	}

	public String get_CH() {
		return CH;
	}

	public void set_CH(String _CH) {
		this.CH = _CH;
	}

	public String get_paiDanR() {
		return paiDanR;
	}

	public void set_paiDanR(String _paiDanR) {
		this.paiDanR = _paiDanR;
	}

	public Date get_paiDanSJ() {
		return paiDanSJ;
	}

	public void set_paiDanSJ(Date _paiDanSJ) {
		this.paiDanSJ = _paiDanSJ;
	}

	public String get_jieDanR() {
		return jieDanR;
	}

	public void set_jieDanR(String _jieDanR) {
		this.jieDanR = _jieDanR;
	}

	public Date get_zuiHouSGRQ() {
		return zuiHouSGRQ;
	}

	public void set_zuiHouSGRQ(Date _zuiHouSGRQ) {
		this.zuiHouSGRQ = _zuiHouSGRQ;
	}

	public String get_X1() {
		return X1;
	}

	public void set_X1(String _X1) {
		this.X1 = _X1;
	}

	public String get_Y1() {
		return Y1;
	}

	public void set_Y1(String _Y1) {
		this.Y1 = _Y1;
	}

	public String get_gongDanBH() {
		return gongDanBH;
	}

	public void set_gongDanBH(String _gongDanBH) {
		this.gongDanBH = _gongDanBH;
	}

	public String get_keHuQM() {
		return keHuQM;
	}

	public void set_keHuQM(String _keHuQM) {
		this.keHuQM = _keHuQM;
	}

	public String get_zhaoPianMC() {
		return zhaoPianMC;
	}

	public void set_zhaoPianMC(String _zhangPianMC) {
		this.zhaoPianMC = _zhangPianMC;
	}

	public int get_shangchuanBZ() {
		return shangchuanBZ;
	}

	public void set_shangchuanBZ(int _shangchuanBZ) {
		this.shangchuanBZ = _shangchuanBZ;
	}

	public int get_chaobiaoBZ() {
		return chaobiaoBZ;
	}

	public void set_chaobiaoBZ(int _chaobiaoBZ) {
		this.chaobiaoBZ = _chaobiaoBZ;
	}

	public String get_guZhangYY() {
		return guZhangYY;
	}

	public void set_guZhangYY(String _guZhangYY) {
		this.guZhangYY = _guZhangYY;
	}

	public String get_huanBiaoSM() {
		return huanBiaoSM;
	}

	public void set_huanBiaoSM(String _huanBiaoSM) {
		this.huanBiaoSM = _huanBiaoSM;
	}

	public String get_shuiBiaoGYH() {
		return shuiBiaoGYH;
	}

	public void set_shuiBiaoGYH(String _shuiBiaoGYH) {
		this.shuiBiaoGYH = _shuiBiaoGYH;
	}

	public String get_xinBiaoGYH() {
		return xinBiaoGYH;
	}

	public void set_xinBiaoGYH(String _xinBiaoGYH) {
		this.xinBiaoGYH = _xinBiaoGYH;
	}

	public double get_qianFeiJE() {
		return qianFeiJE;
	}

	public void set_qianFeiJE(double _qianFeiJE) {
		this.qianFeiJE = _qianFeiJE;
	}

	public String get_jianCeJG() {
		return jianCeJG;
	}

	public void set_jianCeJG(String _jianCeJG) {
		this.jianCeJG = _jianCeJG;
	}

	public String get_chaiBiaoYY() {
		return chaiBiaoYY;
	}

	public void set_chaiBiaoYY(String _chaiBiaoYY) {
		this.chaiBiaoYY = _chaiBiaoYY;
	}

	public String get_heTongH() {
		return heTongH;
	}

	public void set_heTongH(String _heTongH) {
		this.heTongH = _heTongH;
	}

	public String get_shuiBiaoGG() {
		return shuiBiaoGG;
	}

	public void set_shuiBiaoGG(String _shuiBiaoGG) {
		this.shuiBiaoGG = _shuiBiaoGG;
	}

	public String get_yongShuiXZ() {
		return yongShuiXZ;
	}

	public void set_yongShuiXZ(String _yongShuiXZ) {
		this.yongShuiXZ = _yongShuiXZ;
	}

	public String get_shenHeR() {
		return shenHeR;
	}

	public void set_shenHeR(String _shenHeR) {
		this.shenHeR = _shenHeR;
	}

	public Date get_shenHeRQ() {
		return shenHeRQ;
	}

	public void set_shenHeRQ(Date _shenHeRQ) {
		this.shenHeRQ = _shenHeRQ;
	}

	public String get_chaoBiaoZT() {
		return chaoBiaoZT;
	}

	public void set_chaoBiaoZT(String _chaoBiaoZT) {
		this.chaoBiaoZT = _chaoBiaoZT;
	}

}
