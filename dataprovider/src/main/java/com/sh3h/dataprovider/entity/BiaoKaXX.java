/**
 * @author zeng.jing
 *
 */
package com.sh3h.dataprovider.entity;

public class BiaoKaXX {

	/**
	 * 册本号
	 */
	private String _cH;

	/**
	 * 册内序号
	 */
	private int _ceNeiXH;

	/**
	 * 用户号
	 */
	private String _cID;

	/**
	 * 客户编号
	 */
	private String _keHuBH;

	/**
	 * 客户名称
	 */
	private String _keHuMC;

	/**
	 * 站点
	 */
	private String _sT;

	/**
	 * 地址
	 */
	private String _diZhi;

	/**
	 * 联系人
	 */
	private String _lianXiR;

	/**
	 * 联系电话
	 */
	private String _lianXiDH;

	/**
	 * 联系手机
	 */
	private String _lianXiSJ;

	/**
	 * 收费方式
	 */
	private String _shouFeiFS;

	/**
	 * 银行名称
	 */
	private String _yinHangMC;

	/**
	 * 简号
	 */
	private String _jianHao;

	/**
	 * 简号名称
	 */
	private String _jianHaoMC;

	/**
	 * 用户状态
	 */
	private int _yongHuZT;

	/**
	 * 立户日期
	 */
	private long _liHuRQ;

	/**
	 * 表位
	 */
	private String _biaoWei;

	/**
	 * 水表钢印号
	 */
	private String _shuiBiaoGYH;

	/**
	 * 水表条形码
	 */
	private String _shuiBiaoTXM;

	/**
	 * 口径名称
	 */
	private String _kouJingMC;

	/**
	 * 量程
	 */
	private int _liangCheng;

	/**
	 * 表型
	 */
	private String _biaoXing;

	/**
	 * 水表厂家
	 */
	private String _shuiBiaoCJ;

	/**
	 * 水表分类
	 */
	private int _shuiBiaoFL;

	/**
	 * 水表分类名称
	 */
	private String _shuiBiaoFLMC;

	/**
	 * 水表倍率
	 */
	private int _shuibiaoBL;

	/**
	 * 开账分类
	 */
	private String _kaiZhangFL;

	/**
	 * 功能分类
	 */
	private int _gongNengFL;

	/**
	 * 是否计划用水
	 */
	private int _shiFouJHYS;

	/**
	 * 是否收垃圾费
	 */
	private int _shiFouShouLJF;

	/**
	 * 垃圾费系数
	 */
	private Number _laJiFeiXS;

	/**
	 * 是否收违约金
	 */
	private int _shiFouShouWYJ;

	/**
	 * 是否定额加价
	 */
	private int _shiFouDEJJ;

	/**
	 * 定额水量
	 */
	private int _dingESL;

	/**
	 * 总表编号
	 */
	private String _zongBiaoBH;

	/**
	 * 装表日期
	 */
	private long _zhuangBiaoRQ;

	/**
	 * 换表日期
	 */
	private long _huanBiaoRQ;

	/**
	 * 新表底码
	 */
	private int _xinBiaoDM;

	/**
	 * 旧表抄码
	 */
	private int _jiuBiaoCM;

	/**
	 * 经度
	 */
	private String _x1;

	/**
	 * 维度
	 */
	private String _y1;

	/**
	 * 基站X
	 */
	private String _x;

	/**
	 * 基站Y
	 */
	private String _y;

	/**
	 * 分摊方式
	 */
	private int _fenTanFS;

	/**
	 * 分摊量
	 */
	private int _fenTanL;

	/**
	 * 预存款余额
	 */
	private Number _yuCunKYE;

	/**
	 * 欠费总笔数
	 */
	private int _qianFeiZBS;

	/**
	 * 欠费总金额
	 */
	private Number _qianFeiZJE;

	/**
	 * 水表状态 2 显示为定换、3显示为 故换
	 */
	private int _shuibiaoZT;

	// 人口数
	private Number _RENKOUS;
	// 低保优惠水量
	private int _DIBAOYHSL;
	// 公厕优惠水量
	private int _GONGCEYHSL;

	// 基本用水费折扣率
	private Number _YONGSHUIZKL;
	// 污水费折扣率
	private Number _PAISHUIZKL;
	// 水资源费
	private Number _ZHEKOUL1;
	// 水价调节金
	private Number _ZHEKOUL2;
	// 市区价格调节基金
	private Number _ZHEKOUL3;

	// 二次供水标志(0:否;1:是)(默认0)
	private int _ERCIGS;
	// 电子账单(系统词语表79)(0:否;1:只有电子账单;2:电子纸质并存)
	private int _DIANZIZD;
	// 行政区(系统词语表14)
	private String _XINGZHENGQ;
	// 表卡状态(系统词语表8)
	private String _BIAOKAZT;
	// 涉水对象代码
	private String _SHESHUIID;
	// 价格
	private Number _JIAGE;
	// 水表类型编号
	private String _SHUIBIAOLXBH;
	// 自装表开账系数
	private Number _ZIZHUANGBKZXS;
	// 水表总类(用户词语表17)
	private String _SHUIBIAOZL;
	// 用途分类(用户词语表16)
	private String _SHUIBIAOFL;
	// 远传表编号
	private String _YUANCHUANID;
	// 终端号(远传表)
	private String _ZHONGDUANH;
	// 远传表厂家(1:申水;2:山科;3:三高)(用户词语表27)
	private String _YUANCHUANCJ;
	// 多人口/合用表方案
	private String _DUORENKFA;
	// 是否阶梯
	private int _SHIFOUJT;
	// 多人口/合用表有效期限截止日期
	private long _DUORENKDQ;
	// 供水合同年限
	private long _GONGSHUIHTNX;
	// 房东电话
	private String _FANGDONGDH;
	// 房客电话
	private String _FANGKEDH;
	// 年累计量
	private int _NIANLEIJ;
	// 欠费
	private int _HUANBIAO;
	// 换表
	private int _QIANFEI;

	public int getI_NIANLEIJ() {
		return _NIANLEIJ;
	}

	public void setI_NIANLEIJ(int i_NIANLEIJ) {
		_NIANLEIJ = i_NIANLEIJ;
	}

	public int getshuibiaoZT() {
		return _shuibiaoZT;
	}

	public void setshuibiaoZT(int _shuibiaoZT) {
		this._shuibiaoZT = _shuibiaoZT;
	}

	public String getcH() {
		return _cH;
	}

	public void setcH(String cH) {
		this._cH = cH;
	}

	public int getCeNeiXH() {
		return _ceNeiXH;
	}

	public void setCeNeiXH(int ceNeiXH) {
		this._ceNeiXH = ceNeiXH;
	}

	public String getcID() {
		return _cID;
	}

	public void setcID(String cID) {
		this._cID = cID;
	}

	public String getKeHuBH() {
		return _keHuBH;
	}

	public void setKeHuBH(String keHuBH) {
		this._keHuBH = keHuBH;
	}

	public String getKeHuMC() {
		return _keHuMC;
	}

	public void setKeHuMC(String keHuMC) {
		this._keHuMC = keHuMC;
	}

	public String getsT() {
		return _sT;
	}

	public void setsT(String sT) {
		this._sT = sT;
	}

	public String getDiZhi() {
		return _diZhi;
	}

	public void setDiZhi(String diZhi) {
		this._diZhi = diZhi;
	}

	public String getLianXiR() {
		return _lianXiR;
	}

	public void setLianXiR(String lianXiR) {
		this._lianXiR = lianXiR;
	}

	public String getLianXiDH() {
		return _lianXiDH;
	}

	public void setLianXiDH(String lianXiDH) {
		this._lianXiDH = lianXiDH;
	}

	public String getShouFeiFS() {
		return _shouFeiFS;
	}

	public void setShouFeiFS(String shouFeiFS) {
		this._shouFeiFS = shouFeiFS;
	}

	public String getYinHangMC() {
		return _yinHangMC;
	}

	public void setYinHangMC(String yinHangMC) {
		this._yinHangMC = yinHangMC;
	}

	public String getJianHao() {
		return _jianHao;
	}

	public void setJianHao(String jianHao) {
		this._jianHao = jianHao;
	}

	public String getJianHaoMC() {
		return _jianHaoMC;
	}

	public void setJianHaoMC(String jianHaoMC) {
		this._jianHaoMC = jianHaoMC;
	}

	public int getYongHuZT() {
		return _yongHuZT;
	}

	public void setYongHuZT(int yongHuZT) {
		this._yongHuZT = yongHuZT;
	}

	public long getLiHuRQ() {
		return _liHuRQ;
	}

	public void setLiHuRQ(long liHuRQ) {
		this._liHuRQ = liHuRQ;
	}

	public String getBiaoWei() {
		return _biaoWei;
	}

	public void setBiaoWei(String biaoWei) {
		this._biaoWei = biaoWei;
	}

	public String getShuiBiaoGYH() {
		return _shuiBiaoGYH;
	}

	public void setShuiBiaoGYH(String shuiBiaoGYH) {
		this._shuiBiaoGYH = shuiBiaoGYH;
	}

	public String getShuiBiaoTXM() {
		return _shuiBiaoTXM;
	}

	public void setShuiBiaoTXM(String shuiBiaoTXM) {
		this._shuiBiaoTXM = shuiBiaoTXM;
	}

	public String getKouJingMC() {
		return _kouJingMC;
	}

	public void setKouJingMC(String kouJingMC) {
		this._kouJingMC = kouJingMC;
	}

	public int getLiangCheng() {
		return _liangCheng;
	}

	public void setLiangCheng(int liangCheng) {
		this._liangCheng = liangCheng;
	}

	public String getBiaoXing() {
		return _biaoXing;
	}

	public void setBiaoXing(String biaoXing) {
		this._biaoXing = biaoXing;
	}

	public String getShuiBiaoCJ() {
		return _shuiBiaoCJ;
	}

	public void setShuiBiaoCJ(String shuiBiaoCJ) {
		this._shuiBiaoCJ = shuiBiaoCJ;
	}

	public int getShuiBiaoFL() {
		return _shuiBiaoFL;
	}

	public void setShuiBiaoFL(int shuiBiaoFL) {
		this._shuiBiaoFL = shuiBiaoFL;
	}

	public String getShuiBiaoFLMC() {
		return _shuiBiaoFLMC;
	}

	public void setShuiBiaoFLMC(String shuiBiaoFLMC) {
		this._shuiBiaoFLMC = shuiBiaoFLMC;
	}

	public int getShuibiaoBL() {
		return _shuibiaoBL;
	}

	public void setShuibiaoBL(int shuibiaoBL) {
		this._shuibiaoBL = shuibiaoBL;
	}

	public String getKaiZhangFL() {
		return _kaiZhangFL;
	}

	public void setKaiZhangFL(String kaiZhangFL) {
		this._kaiZhangFL = kaiZhangFL;
	}

	public int getGongNengFL() {
		return _gongNengFL;
	}

	public void setGongNengFL(int gongNengFL) {
		this._gongNengFL = gongNengFL;
	}

	public int getShiFouJHYS() {
		return _shiFouJHYS;
	}

	public void setShiFouJHYS(int shiFouJHYS) {
		this._shiFouJHYS = shiFouJHYS;
	}

	public int getShiFouShouLJF() {
		return _shiFouShouLJF;
	}

	public void setShiFouShouLJF(int shiFouShouLJF) {
		this._shiFouShouLJF = shiFouShouLJF;
	}

	public Number getLaJiFeiXS() {
		return _laJiFeiXS;
	}

	public void setLaJiFeiXS(Number laJiFeiXS) {
		this._laJiFeiXS = laJiFeiXS;
	}

	public int getShiFouShouWYJ() {
		return _shiFouShouWYJ;
	}

	public void setShiFouShouWYJ(int shiFouShouWYJ) {
		this._shiFouShouWYJ = shiFouShouWYJ;
	}

	public int getShiFouDEJJ() {
		return _shiFouDEJJ;
	}

	public void setShiFouDEJJ(int shiFouDEJJ) {
		this._shiFouDEJJ = shiFouDEJJ;
	}

	public int getDingESL() {
		return _dingESL;
	}

	public void setDingESL(int dingESL) {
		this._dingESL = dingESL;
	}

	public String getZongBiaoBH() {
		return _zongBiaoBH;
	}

	public void setZongBiaoBH(String zongBiaoBH) {
		this._zongBiaoBH = zongBiaoBH;
	}

	public long getZhuangBiaoRQ() {
		return _zhuangBiaoRQ;
	}

	public void setZhuangBiaoRQ(long zhuangBiaoRQ) {
		this._zhuangBiaoRQ = zhuangBiaoRQ;
	}

	public long getHuanBiaoRQ() {
		return _huanBiaoRQ;
	}

	public void setHuanBiaoRQ(long huanBiaoRQ) {
		this._huanBiaoRQ = huanBiaoRQ;
	}

	public int getXinBiaoDM() {
		return _xinBiaoDM;
	}

	public void setXinBiaoDM(int xinBiaoDM) {
		this._xinBiaoDM = xinBiaoDM;
	}

	public int getJiuBiaoCM() {
		return _jiuBiaoCM;
	}

	public void setJiuBiaoCM(int jiuBiaoCM) {
		this._jiuBiaoCM = jiuBiaoCM;
	}

	public String getX1() {
		return _x1;
	}

	public void setX1(String x1) {
		this._x1 = x1;
	}

	public String getY1() {
		return _y1;
	}

	public void setY1(String y1) {
		this._y1 = y1;
	}

	public String getX() {
		return _x;
	}

	public void setX(String x) {
		this._x = x;
	}

	public String getY() {
		return _y;
	}

	public void setY(String y) {
		this._y = y;
	}

	public int getFenTanFS() {
		return _fenTanFS;
	}

	public void setFenTanFS(int fenTanFS) {
		this._fenTanFS = fenTanFS;
	}

	public int getFenTanL() {
		return _fenTanL;
	}

	public void setFenTanL(int fenTanL) {
		this._fenTanL = fenTanL;
	}

	public Number getYuCunKYE() {
		return _yuCunKYE;
	}

	public void setYuCunKYE(Number yuCunKYE) {
		this._yuCunKYE = yuCunKYE;
	}

	public int getQianFeiZBS() {
		return _qianFeiZBS;
	}

	public void setQianFeiZBS(int qianFeiZBS) {
		this._qianFeiZBS = qianFeiZBS;
	}

	public Number getQianFeiZJE() {
		return _qianFeiZJE;
	}

	public void setQianFeiZJE(Number qianFeiZJE) {
		this._qianFeiZJE = qianFeiZJE;
	}

	public String getBeiZhu() {
		return beiZhu;
	}

	public void setBeiZhu(String beiZhu) {
		this.beiZhu = beiZhu;
	}

	public String getLianXiSJ() {
		return _lianXiSJ;
	}

	public void setLianXiSJ(String lianXiSJ) {
		this._lianXiSJ = lianXiSJ;
	}

	public Number getRENKOUS() {
		return _RENKOUS;
	}

	public void setRENKOUS(Number RENKOUS) {
		this._RENKOUS = RENKOUS;
	}

	public int getDIBAOYHSL() {
		return _DIBAOYHSL;
	}

	public void setDIBAOYHSL(int DIBAOYHSL) {
		this._DIBAOYHSL = DIBAOYHSL;
	}

	public int getGONGCEYHSL() {
		return _GONGCEYHSL;
	}

	public void setGONGCEYHSL(int GONGCEYHSL) {
		this._GONGCEYHSL = GONGCEYHSL;
	}

	public Number getYONGSHUIZKL() {
		return _YONGSHUIZKL;
	}

	public void setYONGSHUIZKL(Number YONGSHUIZKL) {
		this._YONGSHUIZKL = YONGSHUIZKL;
	}

	public Number getPAISHUIZKL() {
		return _PAISHUIZKL;
	}

	public void setPAISHUIZKL(Number PAISHUIZKL) {
		this._PAISHUIZKL = PAISHUIZKL;
	}

	public Number getZHEKOUL1() {
		return _ZHEKOUL1;
	}

	public void setZHEKOUL1(Number ZHEKOUL1) {
		this._ZHEKOUL1 = ZHEKOUL1;
	}

	public Number getZHEKOUL2() {
		return _ZHEKOUL2;
	}

	public void setZHEKOUL2(Number ZHEKOUL2) {
		this._ZHEKOUL2 = ZHEKOUL2;
	}

	public Number getZHEKOUL3() {
		return _ZHEKOUL3;
	}

	public void setZHEKOUL3(Number ZHEKOUL3) {
		this._ZHEKOUL3 = ZHEKOUL3;
	}

	public int get_ERCIGS() {
		return _ERCIGS;
	}

	public void set_ERCIGS(int _ERCIGS) {
		this._ERCIGS = _ERCIGS;
	}

	public int get_DIANZIZD() {
		return _DIANZIZD;
	}

	public void set_DIANZIZD(int _DIANZIZD) {
		this._DIANZIZD = _DIANZIZD;
	}

	public String get_XINGZHENGQ() {
		return _XINGZHENGQ;
	}

	public void set_XINGZHENGQ(String _XINGZHENGQ) {
		this._XINGZHENGQ = _XINGZHENGQ;
	}

	public String get_BIAOKAZT() {
		return _BIAOKAZT;
	}

	public void set_BIAOKAZT(String _BIAOKAZT) {
		this._BIAOKAZT = _BIAOKAZT;
	}

	public String get_SHESHUIID() {
		return _SHESHUIID;
	}

	public void set_SHESHUIID(String _SHESHUIID) {
		this._SHESHUIID = _SHESHUIID;
	}

	public Number get_JIAGE() {
		return _JIAGE;
	}

	public void set_JIAGE(Number _JIAGE) {
		this._JIAGE = _JIAGE;
	}

	public String get_SHUIBIAOLXBH() {
		return _SHUIBIAOLXBH;
	}

	public void set_SHUIBIAOLXBH(String _SHUIBIAOLXBH) {
		this._SHUIBIAOLXBH = _SHUIBIAOLXBH;
	}

	public Number get_ZIZHUANGBKZXS() {
		return _ZIZHUANGBKZXS;
	}

	public void set_ZIZHUANGBKZXS(Number _ZIZHUANGBKZXS) {
		this._ZIZHUANGBKZXS = _ZIZHUANGBKZXS;
	}

	public String get_SHUIBIAOZL() {
		return _SHUIBIAOZL;
	}

	public void set_SHUIBIAOZL(String _SHUIBIAOZL) {
		this._SHUIBIAOZL = _SHUIBIAOZL;
	}

	public String get_SHUIBIAOFL() {
		return _SHUIBIAOFL;
	}

	public void set_SHUIBIAOFL(String _SHUIBIAOFL) {
		this._SHUIBIAOFL = _SHUIBIAOFL;
	}

	public String get_YUANCHUANID() {
		return _YUANCHUANID;
	}

	public void set_YUANCHUANID(String _YUANCHUANID) {
		this._YUANCHUANID = _YUANCHUANID;
	}

	public String get_ZHONGDUANH() {
		return _ZHONGDUANH;
	}

	public void set_ZHONGDUANH(String _ZHONGDUANH) {
		this._ZHONGDUANH = _ZHONGDUANH;
	}

	public String get_YUANCHUANCJ() {
		return _YUANCHUANCJ;
	}

	public void set_YUANCHUANCJ(String _YUANCHUANCJ) {
		this._YUANCHUANCJ = _YUANCHUANCJ;
	}

	public String get_DUORENKFA() {
		return _DUORENKFA;
	}

	public void set_DUORENKFA(String _DUORENKFA) {
		this._DUORENKFA = _DUORENKFA;
	}

	public int get_SHIFOUJT() {
		return _SHIFOUJT;
	}

	public void set_SHIFOUJT(int _SHIFOUJT) {
		this._SHIFOUJT = _SHIFOUJT;
	}

	public long get_DUORENKDQ() {
		return _DUORENKDQ;
	}

	public void set_DUORENKDQ(long _DUORENKDQ) {
		this._DUORENKDQ = _DUORENKDQ;
	}

	public long get_GONGSHUIHTNX() {
		return _GONGSHUIHTNX;
	}

	public void set_GONGSHUIHTNX(long _GONGSHUIHTNX) {
		this._GONGSHUIHTNX = _GONGSHUIHTNX;
	}

	public String get_FANGDONGDH() {
		return _FANGDONGDH;
	}

	public void set_FANGDONGDH(String _FANGDONGDH) {
		this._FANGDONGDH = _FANGDONGDH;
	}

	public String get_FANGKEDH() {
		return _FANGKEDH;
	}

	public void set_FANGKEDH(String _FANGKEDH) {
		this._FANGKEDH = _FANGKEDH;
	}

	public int get_HUANBIAO() {
		return _HUANBIAO;
	}

	public void set_HUANBIAO(int HUANBIAO) {
		_HUANBIAO = HUANBIAO;
	}

	public int get_QIANFEI() {
		return _QIANFEI;
	}

	public void set_QIANFEI(int QIANFEI) {
		_QIANFEI = QIANFEI;
	}

	/**
	 * 备注
	 */
	private String beiZhu;

	public BiaoKaXX() {

	}

}
