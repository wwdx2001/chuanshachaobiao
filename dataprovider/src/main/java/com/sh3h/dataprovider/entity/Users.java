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
public class Users {

	/**
	 * 操作员
	 */
	private String account;
	private String ST;
	private String userName;
	private int type;

	public String get_account() {
		return account;
	}

	public void set_account(String _account) {
		this.account = _account;
	}

	public String get_ST() {
		return ST;
	}

	public void set_ST(String _ST) {
		this.ST = _ST;
	}

	public String get_userName() {
		return userName;
	}

	public void set_userName(String _userName) {
		this.userName = _userName;
	}

	public int get_type() {
		return type;
	}

	public void set_type(int _type) {
		this.type = _type;
	}


}
