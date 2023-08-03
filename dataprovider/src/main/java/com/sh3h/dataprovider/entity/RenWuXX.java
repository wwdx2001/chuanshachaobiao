/**
 *
 */
package com.sh3h.dataprovider.entity;

/**
 * 表务工单
 *
 * @author zengdezhi
 *
 */
public class RenWuXX {

	private int id;
	private String account;
	private int renWuBH;
	private int type;
	private long createSJ;
	private long paiFaSJ;
	private long huiTianSJ;
	private long state;
	private int subType;

	public int get_id() {
		return id;
	}

	public void set_id(int _id) {
		this.id = _id;
	}

	public String get_account() {
		return account;
	}

	public void set_account(String _account) {
		this.account = _account;
	}

	public int get_renWuBH() {
		return renWuBH;
	}

	public void set_renWuBH(int _renWuBH) {
		this.renWuBH = _renWuBH;
	}

	public int get_type() {
		return type;
	}

	public void set_type(int _type) {
		this.type = _type;
	}

	public long get_createSJ() {
		return createSJ;
	}

	public void set_createSJ(long _createSJ) {
		this.createSJ = _createSJ;
	}

	public long get_paiFaSJ() {
		return paiFaSJ;
	}

	public void set_paiFaSJ(long _paiFaSJ) {
		this.paiFaSJ = _paiFaSJ;
	}

	public long get_huiTianSJ() {
		return huiTianSJ;
	}

	public void set_huiTianSJ(long _huiTianSJ) {
		this.huiTianSJ = _huiTianSJ;
	}

	public long get_state() {
		return state;
	}

	public void set_state(long _state) {
		this.state = _state;
	}

	public int get_subType() {
		return subType;
	}

	public void set_subType(int _subType) {
		this.subType = _subType;
	}

}
