package com.sh3h.mralgorithm.billing;

/**
 * 计算金额返回实体
 */
import java.util.List;

public class BillingResult {

	/**
	 * 执行结果是否成功
	 */
	private boolean _isSuccess;
	/**
	 * 总金额
	 */
	private double _n_zongje;

	public BillingResult(boolean isSuccess, double n_zongje) {
		this._isSuccess = isSuccess;
		this._n_zongje = n_zongje;
	}

	public BillingResult() {
	}

	public boolean isSuccess() {
		return _isSuccess;
	}

	public double outZongJE() {
		return _n_zongje;
	}

}
