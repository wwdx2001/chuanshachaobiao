package com.sh3h.dataprovider.entity;

public class UpdateInfo {
	// 版本名称
	private String versionName;
	// 版本号
	private int versionCode;
	// 版本描述
	private String versionDesc;
	// 更新时间
	private String updateDate;
	// apk安装路径
	private String filePath;
	//版本类型
	private String versionType;

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String apkPath) {
		this.filePath = apkPath;
	}

}
