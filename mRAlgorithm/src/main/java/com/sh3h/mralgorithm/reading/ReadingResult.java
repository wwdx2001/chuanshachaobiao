/**
 * @author qiweiwei
 *
 */
package com.sh3h.mralgorithm.reading;

/**
 * 抄表计算结果
 */
public class ReadingResult {
	/**
	 * 执行结果是否成功
	 */
	private boolean _isSuccess;
	/**
	 * 输出消息
	 */
	private String _outmsg;

	/**
	 * 输出方法参数实体类
	 */
	private ReadingParameter _outEntity;

	public ReadingResult(boolean isSuccess, String outmsg,
						 ReadingParameter param) {
		this._isSuccess = isSuccess;
		this._outmsg = outmsg;
		this._outEntity = param;
	}

	public boolean isSuccess() {
		return _isSuccess;
	}

	public String outMsg() {
		return _outmsg;
	}

	public ReadingParameter outEntity() {
		return _outEntity;
	}
}
