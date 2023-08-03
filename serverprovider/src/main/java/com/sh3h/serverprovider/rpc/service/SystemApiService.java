/**
 * @author qiweiwei
 *
 */
package com.sh3h.serverprovider.rpc.service;

import org.json.JSONObject;

import com.sh3h.serverprovider.entity.DeviceInfoEntity;
import com.sh3h.serverprovider.entity.KeepAliveEntity;
import com.sh3h.serverprovider.entity.KeepAliveInfoEntity;
import com.sh3h.serverprovider.entity.RegisterResultEntity;
import com.sh3h.mobileutil.util.LogUtil;

/**
 * SystemApiService
 */
public class SystemApiService extends BaseApiService {

	private static final String URL = "System.ashx";

	private static final String METHOD_FEEDBACK = "feedBack";
	private static final String METHOD_ISREGISTERED = "isRegistered";
	private static final String METHOD_REGISTERMACHINE = "registerMachine";
	private static final String METHOD_KEEPALIVE = "keepAlive";

//	public SystemApiService(Context context) {
//		super(context);
//	}

	/**
	 *
	 */
	@Override
	public String getHandlerURL() {
		return SystemApiService.URL;
	}

	/**
	 * 是否激活
	 *
	 * @param deviceInfo 设备信息
	 * @param registercode 激活码
	 * @return
	 */
	public RegisterResultEntity isRegistered(DeviceInfoEntity deviceInfo,
											 String registercode) throws ApiException {
		JSONObject resp = null;

		try {
			resp = this.callJSONObject(SystemApiService.METHOD_ISREGISTERED,
					deviceInfo.toJson(), registercode);
		} catch (ApiException e) {
			LogUtil.e("API", "是否激活方法调用失败", e);
			throw e;
		}
		RegisterResultEntity result = null;
		if (resp != null) {
			result = RegisterResultEntity.fromJson(resp);
		}

		return result;
	}

	/**
	 * 设备激活
	 *
	 * @param deviceInfo
	 * @return 激活结果
	 */
	public RegisterResultEntity registerMachine(DeviceInfoEntity deviceInfo)
			throws ApiException {
		JSONObject resp = null;
		try {
			resp = this.callJSONObject(SystemApiService.METHOD_REGISTERMACHINE,
					new Object[] { deviceInfo.toJson() });
		} catch (ApiException e) {
			LogUtil.e("API", "激活设备调用失败", e);
			throw e;
		}

		RegisterResultEntity result = null;
		if (resp != null) {
			result = RegisterResultEntity.fromJson(resp);
		}
		return result;
	}

	/**
	 * 发送呼吸包
	 *
	 * @param aliveinfo
	 *            客户端信息
	 */
	public KeepAliveEntity keepAlive(KeepAliveInfoEntity aliveinfo) throws ApiException {
		JSONObject jsonObject = null;
		try {
			jsonObject = this.callJSONObject(SystemApiService.METHOD_KEEPALIVE,
					new Object[] { aliveinfo.toJSON() });
		} catch (ApiException e) {
			LogUtil.e("API", "呼吸包调用失败", e);
			throw e;
		}

		KeepAliveEntity keepAliveEntity = null;
		if (jsonObject != null) {
			keepAliveEntity = KeepAliveEntity.fromJson(jsonObject);
		}

		return keepAliveEntity;
	}

	/**
	 * 意见反馈
	 *
	 * @param content
	 *            反馈意见
	 * @return 是否反馈成功
	 */
	public boolean feedBack(String content) throws ApiException {
		boolean result = false;

		try {
			result = this.callBoolean(SystemApiService.METHOD_FEEDBACK, content);
		} catch (ApiException e) {
			LogUtil.e("API", "意见反馈接口调用失败", e);
			throw e;
		}

		return result;
	}

}
