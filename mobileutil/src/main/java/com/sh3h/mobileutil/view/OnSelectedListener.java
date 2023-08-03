/**
 * @author qiweiwei@shanghai3h.com
 */
package com.sh3h.mobileutil.view;

/**
 * 选中事件监听器接口 
 */
public interface OnSelectedListener {

	/**
	 * 选中事件，回调方法
	 *
	 * @param index 选中索引号
	 * @param value 选中值
	 */
	void onSelectedResult(int which, int[] index, Object[] value);
}
