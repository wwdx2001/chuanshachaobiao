/**
 * @author qiweiwei
 */
package com.sh3h.mralgorithm.reading;

import java.util.Date;

/**
 * 抄表计算参数实体
 */
public class ReadingParameter {

	/**
	 * 默认构造函数
	 */
	public ReadingParameter() {

	}

	private String _chaobiaoztbm = "-1";// 抄表状态编码

	private int _chaobiaosuanfabm = -1;// 抄表算法编码

	private int _bencicm = -1;// 本次抄码

	private int _shangcicm = -1;// 上次抄码

	private int _bencisl = -1;// 本次水量

	private Date _huanbiaohtqrsj;// 换表回填确认时间

	private Date _shangcixiazaisj;// 上次下载时间

	private long _shangcicbrq;// 上次抄表日期

	private long _bencicbrq;// 本次抄表日期

	private int _liangcheng = -1;// 量程

	private int _shuibiaozt = -1;// 水表状态

	private int _jiubiaocm = -1;// 旧表抄码

	private int _xinbiaodm = -1;// 新表底码

	private int _shuibiaobeil = 1;// 水表倍率

	private int _readingchild = -1;//子表本次抄码

	private int _lastreadingchild  = -1;//子表上次抄表

	private int _shanggedbzqts; // 上个读表周期天数
	private long _shangshanggycbrq; // 上上月抄表日期
	private int _shangcicjsl; // 上次抄见水量

	private int samplingSL;
	private int samplingCM;
	private int samplingZTBM;
	private long samplingRQ;
	private String samplingZTMC;

	private String  _region = "";


	public void reset() {
		_chaobiaoztbm = "-1";// 抄表状态编码
		_chaobiaosuanfabm = -1;// 抄表算法编码
		_bencicm = -1;// 本次抄码
		_shangcicm = -1;// 上次抄码
		_bencisl = -1;// 本次水量
		_huanbiaohtqrsj = null;// 换表回填确认时间
		_shangcixiazaisj = null;// 上次下载时间
		_shangcicbrq = 0;// 上次抄表日期
		_bencicbrq = 0;// 本次抄表日期
		_liangcheng = -1;// 量程
		_shuibiaozt = -1;// 水表状态
		_jiubiaocm = -1;// 旧表抄码
		_xinbiaodm = -1;// 新表底码
		_shuibiaobeil = 1;// 水表倍率

		_lastreadingchild  = -1;//子表上次抄表
		_readingchild = -1;//子表本次抄码
		_shanggedbzqts = 0;
		_shangshanggycbrq = 0;
		_shangcicjsl = 0;
	}
	/**
	 * 获取水表倍率
	 *
	 * @return
	 */
	public int get_shuibiaobeil() {
		return _shuibiaobeil;
	}

	/**
	 * 设置水表倍率
	 *
	 * @param _shuibiaobeil
	 */
	public void set_shuibiaobeil(int _shuibiaobeil) {
		this._shuibiaobeil = _shuibiaobeil;
	}

	/**
	 * 获取抄表算法编码
	 *
	 * @return
	 */
	public int get_chaobiaosuanfabm() {
		return _chaobiaosuanfabm;
	}

	/**
	 * 设置抄表算法编码
	 *
	 * @param _chaobiaosuanfabm
	 */
	public void set_chaobiaosuanfabm(int _chaobiaosuanfabm) {
		this._chaobiaosuanfabm = _chaobiaosuanfabm;
	}

	/**
	 * 获取旧表抄码
	 *
	 * @return
	 */
	public int get_jiubiaocm() {
		return _jiubiaocm;
	}

	/**
	 * 设置旧表抄码
	 *
	 * @param _jiubiaocm
	 */
	public void set_jiubiaocm(int _jiubiaocm) {
		this._jiubiaocm = _jiubiaocm;
	}

	/**
	 * 获取新表抄码
	 *
	 * @return
	 */
	public int get_xinbiaodm() {
		return _xinbiaodm;
	}

	/**
	 * 设置新表抄码
	 *
	 * @param _xinbiaodm
	 */
	public void set_xinbiaodm(int _xinbiaodm) {
		this._xinbiaodm = _xinbiaodm;
	}

	/**
	 * 获取上次抄表日期
	 *
	 * @return
	 */
	public long get_shangcicbrq() {
		return _shangcicbrq;
	}

	/**
	 * 设置上次抄表日期
	 *
	 * @param _shangcicbrq
	 */
	public void set_shangcicbrq(long _shangcicbrq) {
		this._shangcicbrq = _shangcicbrq;
	}

	/**
	 * 获取本次抄表日期
	 *
	 * @return
	 */
	public long get_bencicbrq() {
		return _bencicbrq;
	}

	/**
	 * 设置上次抄表日期
	 *
	 * @param _bencicbrq
	 */
	public void set_bencicbrq(long _bencicbrq) {
		this._bencicbrq = _bencicbrq;
	}

	/**
	 * 获取水表状态
	 *
	 * @return
	 */
	public int get_shuibiaozt() {
		return _shuibiaozt;
	}

	/**
	 * 设置水表状态
	 *
	 * @param _shuibiaozt
	 */
	public void set_shuibiaozt(int _shuibiaozt) {
		this._shuibiaozt = _shuibiaozt;
	}

	/**
	 * 获取量程
	 *
	 * @return
	 */
	public int get_liangcheng() {
		return _liangcheng;
	}

	/**
	 * 设置量程
	 *
	 * @param _liangcheng
	 */
	public void set_liangcheng(int _liangcheng) {
		this._liangcheng = _liangcheng;
	}

	/**
	 * 获取本次抄码
	 *
	 * @return
	 */
	public int get_bencicm() {
		return _bencicm;
	}

	/**
	 * 获取上次抄码
	 *
	 * @return
	 */
	public int get_shangcicm() {
		return _shangcicm;
	}

	/**
	 * 设置上次抄码
	 *
	 * @param _shangcicm
	 */
	public void set_shangcicm(int _shangcicm) {
		this._shangcicm = _shangcicm;
	}

	/**
	 * 设置本次水量
	 *
	 * @param _bencicm
	 */
	public void set_bencicm(int _bencicm) {
		this._bencicm = _bencicm;
	}

	/**
	 * 获取本次水量
	 *
	 * @return
	 */
	public int get_bencisl() {
		return _bencisl;
	}

	/**
	 * 设置本次水量
	 *
	 * @param _bencisl
	 */
	public void set_bencisl(int _bencisl) {
		this._bencisl = _bencisl;
	}

	/**
	 * 获取换表回填确认时间
	 *
	 * @return
	 */
	public Date get_huanbiaohtqrsj() {
		return _huanbiaohtqrsj;
	}

	/**
	 * 设置换表回填确认时间
	 *
	 * @param _huanbiaohtqrsj
	 */
	public void set_huanbiaohtqrsj(Date _huanbiaohtqrsj) {
		this._huanbiaohtqrsj = _huanbiaohtqrsj;
	}

	/**
	 * 获取上次下载时间
	 *
	 * @return
	 */
	public Date get_shangcixiazaisj() {
		return _shangcixiazaisj;
	}

	/**
	 * 设置上次下载时间
	 *
	 * @param _shangcixiazaisj
	 */
	public void set_shangcixiazaisj(Date _shangcixiazaisj) {
		this._shangcixiazaisj = _shangcixiazaisj;
	}

	/**
	 * 获取抄表状态编码
	 *
	 * @return
	 */
	public String get_chaobiaoztbm() {
		return _chaobiaoztbm;
	}

	/**
	 * 设置抄表状态编码
	 *
	 * @param _chaobiaoztbm
	 */
	public void set_chaobiaoztbm(String _chaobiaoztbm) {
		this._chaobiaoztbm = _chaobiaoztbm;
	}

	/**
	 * 获取子表本次抄码
	 * @return
	 */
	public int get_readingchild() {
		return _readingchild;
	}

	/**
	 * 设置子表本次抄码
	 * @param _readingchild
	 */
	public void set_readingchild(int _readingchild) {
		this._readingchild = _readingchild;
	}

	public int get_lastreadingchild() {
		return _lastreadingchild;
	}

	public void set_lastreadingchild(int _lastreadingchild) {
		this._lastreadingchild = _lastreadingchild;
	}

	public int getSamplingSL() {
		return samplingSL;
	}

	public void setSamplingSL(int samplingSL) {
		this.samplingSL = samplingSL;
	}

	public int getSamplingCM() {
		return samplingCM;
	}

	public void setSamplingCM(int samplingCM) {
		this.samplingCM = samplingCM;
	}

	public int getSamplingZTBM() {
		return samplingZTBM;
	}

	public void setSamplingZTBM(int samplingZTBM) {
		this.samplingZTBM = samplingZTBM;
	}

	public long getSamplingRQ() {
		return samplingRQ;
	}

	public void setSamplingRQ(long samplingRQ) {
		this.samplingRQ = samplingRQ;
	}

	public String getSamplingZTMC() {
		return samplingZTMC;
	}

	public void setSamplingZTMC(String samplingZTMC) {
		this.samplingZTMC = samplingZTMC;
	}

	public String get_region() {
		return _region;
	}

	public void set_region(String _region) {
		this._region = _region;
	}

	public int get_shanggedbzqts() {
		return _shanggedbzqts;
	}

	public void set_shanggedbzqts(int _shanggedbzqts) {
		this._shanggedbzqts = _shanggedbzqts;
	}

	public long get_shangshanggycbrq() {
		return _shangshanggycbrq;
	}

	public void set_shangshanggycbrq(long _shangshanggycbrq) {
		this._shangshanggycbrq = _shangshanggycbrq;
	}

	public int get_shangcicjsl() {
		return _shangcicjsl;
	}

	public void set_shangcicjsl(int _shangcicjsl) {
		this._shangcicjsl = _shangcicjsl;
	}
}
