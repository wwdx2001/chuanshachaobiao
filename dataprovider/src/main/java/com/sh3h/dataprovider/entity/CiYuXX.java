package com.sh3h.dataprovider.entity;

public class CiYuXX {

	private int _id;
	private int _wordsId;
	private String _wordsContent;
	private String _wordsValue;
	private String _wordsRemark;
	private int _belongId;
	private int _sortId;
	private int _isActive;

	public int getid() {
		return _id;
	}

	public void setid(int _id) {
		this._id = _id;
	}

	public int getwordsId() {
		return _wordsId;
	}

	public void setwordsId(int _wordsId) {
		this._wordsId = _wordsId;
	}

	public String getwordsContent() {
		return _wordsContent;
	}

	public void setwordsContent(String _wordsContent) {
		this._wordsContent = _wordsContent;
	}

	public String getwordsValue() {
		return _wordsValue;
	}

	public void setwordsValue(String _wordsValue) {
		this._wordsValue = _wordsValue;
	}

	public String getwordsRemark() {
		return _wordsRemark;
	}

	public void setwordsRemark(String _wordsRemark) {
		this._wordsRemark = _wordsRemark;
	}

	public int getbelongId() {
		return _belongId;
	}

	public void setbelongId(int _belongId) {
		this._belongId = _belongId;
	}

	public int getsortId() {
		return _sortId;
	}

	public void setsortId(int _sortId) {
		this._sortId = _sortId;
	}

	public int getisActive() {
		return _isActive;
	}

	public void setisActive(int _isActive) {
		this._isActive = _isActive;
	}

	public String toString() {
		return this._wordsContent;
	}
}
