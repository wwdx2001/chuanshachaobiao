/**
 * @author qiweiwei
 *
 */
package com.sh3h.serverprovider.rpc.service;

import org.json.JSONException;

import android.content.Context;

import com.sh3h.serverprovider.entity.ReXianGDEntity;
import com.sh3h.serverprovider.entity.XinXiBGEntity;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

public class ThirdSystemApiService extends BaseApiService {

	private static final String URL = "ThirdSystem.ashx";

	private static final String METHOD_INSERTXINXIBG = "InsertKeHuXXBGToPDAMS";
	private static final String METHOD_SENDNEWHOTXX = "SendNewHotxx";

	public ThirdSystemApiService(Context context) {
		super(context);
	}

	@Override
	public String getHandlerURL() {
		return ThirdSystemApiService.URL;
	}

	/**
	 * 上传客户信息变更内容
	 *
	 * @param xinxi
	 * @return
	 * @throws JSONException
	 */
	public boolean InsertKeHuXXBGToPDAMS(XinXiBGEntity xinxi) {
		boolean success;
		try {
			success = this.callBoolean(ThirdSystemApiService.METHOD_INSERTXINXIBG,
					new Object[] { xinxi.toJSON() });
		} catch (ApiException e) {
			LogUtil.e("API", "上传客户信息变更内容调用失败", e);
			return false;
		}

		return success;
	}

	/**
	 * 热线开单
	 *
	 * @param gd
	 * @return 热线工单号
	 * @throws JSONException
	 */
	public String SendNewHotxx(ReXianGDEntity gd) {
		String returnVal = TextUtil.EMPTY;
		try {
			returnVal = this.callString(ThirdSystemApiService.METHOD_SENDNEWHOTXX,
					new Object[] { gd.toJSON() });
		} catch (ApiException e) {
			LogUtil.e("API", "上传热线工单信息调用失败", e);
			return returnVal;
		}

		return returnVal;
	}

}
