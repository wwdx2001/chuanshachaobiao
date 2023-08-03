package com.sh3h.mralgorithm.controlcheck;

public class ControlItems {
	private boolean chaomaInPut;
	private boolean shuiliangInPut;

	public ControlItems(boolean chaoma, boolean shuiliang) {
		this.chaomaInPut = chaoma;
		this.shuiliangInPut = shuiliang;
	}

	/**
	 * 获取抄码是否可输入
	 *
	 * @return
	 */
	public boolean getChaoMaInPut() {
		return this.chaomaInPut;
	}

	/**
	 * 获取水量是否可输入
	 *
	 * @return
	 */
	public boolean getShuiLiangInPut() {
		return this.shuiliangInPut;
	}
}
