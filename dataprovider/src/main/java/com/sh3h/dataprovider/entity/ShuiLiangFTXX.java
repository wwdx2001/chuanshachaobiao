package com.sh3h.dataprovider.entity;

public class ShuiLiangFTXX {

	public ShuiLiangFTXX() {

	}
	/**
	 * 用户号
	 */
	private String _s_cid;
	/**
	 * 简号
	 */
	private String _s_JianHao;
	/**
	 * 分摊方式
	 */
	private int _i_FenTanFS;
	/**
	 * 分摊量
	 */
	private double _i_FentTanL;
	/**
	 * 排序
	 */
	private int _i_PaiXu;

	public String gets_cid() {
		return _s_cid;
	}

	public void sets_cid(String _s_cid) {
		this._s_cid = _s_cid;
	}

	public String gets_JianHao() {
		return _s_JianHao;
	}

	public void sets_JianHao(String _s_JianHao) {
		this._s_JianHao = _s_JianHao;
	}

	public int geti_FenTanFS() {
		return _i_FenTanFS;
	}

	public void seti_FenTanFS(int _i_FenTanFS) {
		this._i_FenTanFS = _i_FenTanFS;
	}

	public double geti_FentTanL() {
		return _i_FentTanL;
	}

	public void seti_FentTanL(double _i_FentTanL) {
		this._i_FentTanL = _i_FentTanL;
	}

	public int geti_PaiXu() {
		return _i_PaiXu;
	}

	public void seti_PaiXu(int _i_PaiXu) {
		this._i_PaiXu = _i_PaiXu;
	}


}
