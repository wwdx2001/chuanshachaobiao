/**
 * @author zengdezhi
 *
 */
package com.sh3h.dataprovider.entity;

public class ChaoBiaoZTFL {

	private int _fenLeiBM;
	private String _fenLeiMC;

	/**
	 * @return the fenLeiBM
	 */
	public int getFenLeiBM() {
		return _fenLeiBM;
	}

	/**
	 * @param fenLeiBM
	 *            the fenLeiBM to set
	 */
	public void setFenLeiBM(int fenLeiBM) {
		this._fenLeiBM = fenLeiBM;
	}

	/**
	 * @return the fenLeiMC
	 */
	public String getFenLeiMC() {
		return _fenLeiMC;
	}

	/**
	 * @param fenLeiMC
	 *            the fenLeiMC to set
	 */
	public void setFenLeiMC(String fenLeiMC) {
		this._fenLeiMC = fenLeiMC;
	}
	
	@Override
	public String toString() {
		return this._fenLeiMC;
	}

}
