/**
 * @author qiweiwei
 *
 */
package com.sh3h.dataprovider;

import com.sh3h.dataprovider.greendaoDao.DataProviderImpl;

/**
 * MeterReadingDataProviderManager
 */
public class DBManager {

	//private static IMeterReadingDataProvider _defaultInstance = null;
	private static IDataProvider _dataProvider = null;
	/**
	 * 获取IMeterReadingDataProvider实例
	 *
	 * @param ctx
	 *            上下文
	 * @return
	 */
//	public static IMeterReadingDataProvider getDataProvider(Context ctx) {
//		if (_defaultInstance == null) {
//			DBHelper.openDateBase(ctx);
//			_defaultInstance = new DefaultMeterReadingDataProvider();
////			_defaultInstance = new DataProviderImpl();
//		}
//		return _defaultInstance;
//	}


	/**
	 * 获取IGreenDaoMeterReadingDataProvider实例
	 *
	 * @return
	 */
	public static IDataProvider getInstance() {
		if (_dataProvider == null) {
//			DBHelper.openDateBase();
			synchronized (IDataProvider.class){
				if(_dataProvider == null){
					_dataProvider = new DataProviderImpl();
				}
			}
		}

		return _dataProvider;
	}
}
