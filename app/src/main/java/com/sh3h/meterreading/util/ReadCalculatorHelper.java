/**
 *
 */
package com.sh3h.meterreading.util;

import android.content.Context;

import com.sh3h.dataprovider.DBManager;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.mralgorithm.reading.ReadCalculator;
import com.sh3h.mralgorithm.reading.ReadState;
import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

import java.util.List;

/**
 * @author liukaiyu 改写抄表算法接口
 */
public class ReadCalculatorHelper {

	private static ReadCalculator _instance = null;

	private static ReadCalculator getInstance(Context context) {
		if (_instance == null) {
			_instance = createCalculator(context);
		}
		return _instance;
	}

	/**
	 * 加载抄表状态和算法的对应关系
	 * @param context
	 * @return
	 */
	private static ReadCalculator createCalculator(Context context) {
		ReadCalculator rc = new ReadCalculator();
		List<ChaoBiaoZT> list = DBManager.getInstance().getAllChaobiaozt();
		if (list != null && list.size() > 0) {
			for (ChaoBiaoZT chaoBiaoZT : list) {
				rc.appendReadState(new ReadState(String.valueOf(chaoBiaoZT.getI_ZHUANGTAIBM()),
						chaoBiaoZT.getI_SHUILIANGSFBM()));
			}
		}
		return rc;
	}

	public static ReadingResult calculate(Context context, ReadingParameter param) {
		return getInstance(context).calculate(param);
	}
}
