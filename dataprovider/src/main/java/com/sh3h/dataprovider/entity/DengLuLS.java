/**
 * @author zeng.jing
 *
 */
package com.sh3h.dataprovider.entity;

public class DengLuLS {

	private String S_YUANGONGZH;
	private Long D_DengLuSJ;
	private int I_DengLuFS;
	private int I_YanZhengFS;
	private String S_MiMa;
	private int userID;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getYUANGONGZH() {
		return S_YUANGONGZH;
	}

	public void setS_YUANGONGZH(String YUANGONGZH) {
		S_YUANGONGZH = YUANGONGZH;
	}

	public Long getDengLuSJ() {
		return D_DengLuSJ;
	}

	public void setD_DengLuSJ(Long DengLuSJ) {
		D_DengLuSJ = DengLuSJ;
	}

	public int getDengLuFS() {
		return I_DengLuFS;
	}

	public void setI_DengLuFS(int DengLuFS) {
		I_DengLuFS = DengLuFS;
	}

	public int getYanZhengFS() {
		return I_YanZhengFS;
	}

	public void setI_YanZhengFS(int YanZhengFS) {
		I_YanZhengFS = YanZhengFS;
	}

	public String getMiMa() {
		return S_MiMa;
	}

	public void setS_MiMa(String MiMa) {
		S_MiMa = MiMa;
	}

}
