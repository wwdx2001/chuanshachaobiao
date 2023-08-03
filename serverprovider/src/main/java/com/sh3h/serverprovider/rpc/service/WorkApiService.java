package com.sh3h.serverprovider.rpc.service;

import android.util.Base64;

import com.sh3h.serverprovider.entity.BiaoKaXXEntity;
import com.sh3h.serverprovider.entity.ChaiBiaoDJEntity;
import com.sh3h.serverprovider.entity.ChaiBiaoDJSearchEntity;
import com.sh3h.serverprovider.entity.ChaiBiaoDJSearchResultEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoJLEntity;
import com.sh3h.serverprovider.entity.DangQianZWNYEntity;
import com.sh3h.serverprovider.entity.DelayInfoEntity;
import com.sh3h.serverprovider.entity.DelayPartialInfoEntity;
import com.sh3h.serverprovider.entity.DiMaTZEntity;
import com.sh3h.serverprovider.entity.DiMaTZXXEntity;
import com.sh3h.serverprovider.entity.DingQiHBDJEntity;
import com.sh3h.serverprovider.entity.DingQiHBSearchEntity;
import com.sh3h.serverprovider.entity.DingQiHBSearchResultEntity;
import com.sh3h.serverprovider.entity.ExternalWorkDetailInfoEntity;
import com.sh3h.serverprovider.entity.ExternalWorkMainInfoEntity;
import com.sh3h.serverprovider.entity.ExternalWorkPartialDetailInfoEntity;
import com.sh3h.serverprovider.entity.ExternalWorkPartialMainInfoEntity;
import com.sh3h.serverprovider.entity.FeiYongMXEntity;
import com.sh3h.serverprovider.entity.FormWorkInfoEntity;
import com.sh3h.serverprovider.entity.FormWorkPartialInfoEntity;
import com.sh3h.serverprovider.entity.FuHeDXXEntity;
import com.sh3h.serverprovider.entity.FuHeHTEntity;
import com.sh3h.serverprovider.entity.HuanBiaoXXEntity;
import com.sh3h.serverprovider.entity.JiaoFeiXXEntity;
import com.sh3h.serverprovider.entity.LoginInfo2Entity;
import com.sh3h.serverprovider.entity.QianFeiXXEntity;
import com.sh3h.serverprovider.entity.RemoteDataEntity;
import com.sh3h.serverprovider.entity.RemoteResultInfoEntity;
import com.sh3h.serverprovider.entity.ResultInfoEntity;
import com.sh3h.serverprovider.entity.SanLaiTDEntity;
import com.sh3h.serverprovider.entity.TiaoJiaHXXEntity;
import com.sh3h.serverprovider.entity.TiaoZhengJMEntity;
import com.sh3h.serverprovider.entity.UploadInfoEntity;
import com.sh3h.serverprovider.entity.VolumeTrackEntity;
import com.sh3h.serverprovider.entity.WorkStaffEntity;
import com.sh3h.serverprovider.entity.WorkTaskInfoEntity;
import com.sh3h.serverprovider.entity.YangHuGDEntity;
import com.sh3h.serverprovider.entity.YuYueXXEntity;
import com.sh3h.serverprovider.entity.ZhanDianXXEntity;
import com.sh3h.serverprovider.entity.ZhangWuCLCommonSearchEntity;
import com.sh3h.serverprovider.entity.ZhangWuCLCommonSearchResultEntity;
import com.sh3h.mobileutil.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkApiService extends BaseApiService {
    private static final String URL = "Work.ashx";

    // 下载所有的工单任务
    private static final String METHOD_DOWNLOADALLWORKTASKS = "DownloadAllWorkTasks";
    // 下载单条工单任务
    private static final String METHOD_DOWNLOADWORKTASK = "DownloadWorkTask";
    // 下载表务工单
    private static final String METHOD_DOWNLOADFORMWORKS = "DownloadFormWorks";
    // 上传表务工单数据，下载需要更新的数据
    private static final String METHOD_SYNCFORMWORKS = "SyncFormWorks";
    // 上传单条表务工单数据
    private static final String METHOD_UPLOADFORMWORK = "UpLoadFormWork";
    // 上传客户签名或照片 【taskid任务编号，fileName 图片名称】
    private static final String METHOD_UPLOADPICTURE = "UploadPicture";
    // 下载客户签名或照片
    private static final String METHOD_DOWNLOADPICTURE = "DownloadPicture";

    // 远程开帐
    private static final String METHOD_MAKEOUTBILL = "MakeOutBill";
    // 远程计算金额
    private static final String METHOD_CALCULATECASH = "CalculateCash";
    // 提交延迟
    private static final String METHOD_COMMITDELAYINFO = "CommitDelayInfo";
    // 下载延迟数据
    private static final String METHOD_DOWNLOADDELAYRENWUBH = "DownloadDelayRenwuBH";
    // 下载延迟数据
    private static final String METHOD_DOWNLOADDELAYINFOS = "DownloadDelayInfos";
    // 下载延迟表卡
    private static final String METHOD_QUERYDELAYBIAOKAXX = "QueryDelayBiaoKaXX";
    // 下载延迟欠费
    private static final String METHOD_QUERYDELAYQIANFEI = "QueryDelayQianF";
    // 下载抄表记录
    private static final String METHOD_QUERYDELAYCHAOBIAOJL = "QueryDelayChaoBiaoJL";
    // 下载缴费信息
    private static final String METHOD_QUERYDELAYJIAOFEIXX = "QueryDelayJiaoFeiXX";
    // 下载换表信息
    private static final String METHOD_QUERYDELAYHUANBIAOXX = "QueryDelayHuanBiaoXX";
    // 同步延迟数据
    private static final String METHOD_SYNCDELAYINFOS = "SyncDelayInfos";
    // 上传延迟数据
    private static final String METHOD_UPLOADDELAYINFO = "UploadDelayInfo";

    // 下载外复工单主信息
    private static final String METHOD_DOWNLOADEXTERNALWORKMAININFOS = "DownloadExternalWorkMainInfos";
    // 下载外复工单明细信息
    private static final String METHOD_DOWNLOADEXTERNALWORKDETAILINFOS = "DownloadExternalWorkDetailInfos";
    // 上传外复工单数据，下载需要更新的数据
    private static final String METHOD_SYNCEXTERNALWORKS = "SyncExternalWorks";
    // 上传单条外复工单数据
    private static final String METHOD_UPLOADEXTERNALWORK = "UploadExternalWork";

    // 根据任务类型和站点得到对应的员工信息
    private static final String METHOD_GETWORKSTAFFS = "GetWorkStaffs";

    // 获得册本轨迹
    private static final String METHOD_GETVOLUMETRACKS = "GetVolumeTracks";
    //查询故障换表
    private static final String METHOD_SEARCH_GUZHANG_HB = "SearchGuZhangHBList";

    //查询未销减免1, 未消调整1, 差额调整1, 已销减免1, 已销调整1, 底码调整1
    private static final String METHOD_SEARCH_WEIXIAOJM = "SearchWeiXiaoJM";
    //查询未销减免1, 未消调整1, 已销减免1, 已销调整1
    private static final String METHOD_SEARCH_WEIXIAOCL = "SearchWeiXiaoJMList";
    // 复核回填退单
    private static final String METHOD_COMMITFUHETD = "CommitFuHeTD";
    //复核回填回填
    private static final String METHOD_FUHE_HUTIAN = "CommitFuHeHT";
    //登记故障换表
    private static final String METHOD_GUZHANG_HB = "CommitGuZhangHB";
    //查询复核单详情
    private static final String METHOD_FUHED_XX = "SearchFuHeDXX";
    //未销减免2提交审核, 差额调整2, 未消调整2，已销调整2，已销减免2
    private static final String METHOD_WEIXIAO_JM = "CommitWeiXiaoJM";
    //查询原始费用明细
    private static final String METHOD_FEIYONG_MX_LIST = "SearchFeiYongMXYSList";
    //查询处理明细、保留明细、预算
    private static final String METHOD_FEIYONG_MX_YS_LIST = "SearchMingXiList";
    // 查询现调价号
    private static final String METHOD_SEARCHTIAOJIAHXXLIST = "SearchTiaoJiaHXXList";
    // 查询底码调整
    private static final String METHOD_SEARCHDIMATZXX = "SearchDiMaTZXX";
    // 根据传入账号判断底码调整还是重置底码
    private static final String METHOD_CHECKDIMATZ = "CheckDiMaTZ";
    // 判断是否在底码流程中
    private static final String METHOD_CHECKDIMATZISINWORKFLOW = "CheckDiMaTZIsInWorkFlow";
    // 提交底码调整
    private static final String METHOD_COMMITDIMATZ = "CommitDiMaTZ";
    // 查询定期换表
    private static final String METHOD_SEARCHDINGQIHB = "SearchDingQiHB";
    // 登记定期换表
    private static final String METHOD_DINGQIHBDJ = "CommitDingQiHB";
    // 登记养护工单
    private static final String METHOD_COMMITYANGHUGD = "CommitYangHuGD";
    // 查询站点信息
    private static final String METHOD_SEARCHZHANDIANXX = "SearchZhanDianXX";
    // 上传照片
    private static final String METHOD_UPLOADPICTURE2 = "UploadPicture2";
    //查询当前账务年月
    private static final String METHOD_DANGQIANZHANGWUNY = "SearchDangQianZWNY";
    //判断是否为银行送盘
    private static final String METHOD_SearchISHUAZHANG = "SearchISHUAZHANG";

    @Override
    public String getHandlerURL() {
        return WorkApiService.URL;
    }

    /**
     * 下载所有的工单任务
     *
     * @throws ApiException , JSONException
     */
    public List<WorkTaskInfoEntity> DownloadAllWorkTasks(String account,
                                                         String deviceID) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    WorkApiService.METHOD_DOWNLOADALLWORKTASKS, account,
                    deviceID);
        } catch (ApiException e) {
            LogUtil.e("API", "下载所有的工单任务失败", e);
            throw e;
        }

        List<WorkTaskInfoEntity> list = new ArrayList<WorkTaskInfoEntity>();
        if (array != null) {
            list = WorkTaskInfoEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载单条工单任务
     *
     * @throws ApiException , JSONException
     */
    public WorkTaskInfoEntity DownloadWorkTask(String account, String deviceID,
                                               int taskId) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(
                    WorkApiService.METHOD_DOWNLOADWORKTASK, account, deviceID,
                    taskId);
        } catch (ApiException e) {
            LogUtil.e("API", "下载单条工单任务", e);
            throw e;
        }

        WorkTaskInfoEntity entity = new WorkTaskInfoEntity();
        if (object != null) {
            entity = WorkTaskInfoEntity.fromJSON(object);
        }
        return entity;
    }

    /**
     * 下载所有的表务工单
     *
     * @throws ApiException , JSONException
     */
    public List<FormWorkInfoEntity> DownloadFormWorks(String account,
                                                      String deviceID, String taskIds) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_DOWNLOADFORMWORKS,
                    account, deviceID, taskIds);
        } catch (ApiException e) {
            LogUtil.e("API", "下载所有的表务工单失败", e);
            throw e;
        }

        List<FormWorkInfoEntity> list = new ArrayList<FormWorkInfoEntity>();
        if (array != null) {
            list = FormWorkInfoEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 上传表务工单数据，下载需要更新的数据
     *
     * @throws ApiException , JSONException
     */
    public List<FormWorkInfoEntity> SyncFormWorks(String account,
                                                  String deviceID, List<FormWorkPartialInfoEntity> infos)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SYNCFORMWORKS,
                    account, deviceID,
                    FormWorkPartialInfoEntity.toJSONArray(infos));
        } catch (ApiException e) {
            LogUtil.e("API", "上传表务工单数据，下载需要更新的数据失败", e);
            throw e;
        }

        List<FormWorkInfoEntity> list = new ArrayList<FormWorkInfoEntity>();
        if (array != null) {
            list = FormWorkInfoEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 上传单条表务工单数据
     *
     * @throws ApiException , JSONException
     */
    public ResultInfoEntity UpLoadFormWork(String account, String deviceID,
                                           FormWorkPartialInfoEntity info) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_UPLOADFORMWORK,
                    account, deviceID, FormWorkPartialInfoEntity.toJSON(info));
        } catch (ApiException e) {
            LogUtil.e("API", "上传单条表务工单数据失败", e);
            throw e;
        }

        ResultInfoEntity entity = null;
        if (object != null) {
            entity = ResultInfoEntity.fromJSON(object);
        } else {
            entity = new ResultInfoEntity();
        }

        return entity;
    }

    /**
     * 上传客户签名或照片
     */
    public ResultInfoEntity UploadPicture(String account, int taskId,
                                          String fileName, byte[] buffer) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            String data = Base64.encodeToString(buffer, Base64.DEFAULT);
            object = this.callJSONObject(WorkApiService.METHOD_UPLOADPICTURE,
                    account, taskId, fileName, data);
        } catch (ApiException e) {
            LogUtil.e("API", "上传客户签名或照片失败", e);
            throw e;
        }

        ResultInfoEntity entity = null;
        if (object != null) {
            entity = ResultInfoEntity.fromJSON(object);
        } else {
            entity = new ResultInfoEntity();
        }

        return entity;
    }

    /**
     * 下载客户签名或照片
     */
    public byte[] DownloadPicture(String account, int taskId, String fileName)
            throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_DOWNLOADPICTURE,
                    account, taskId, fileName);
        } catch (ApiException e) {
            LogUtil.e("API", "上传客户签名或照片失败", e);
            throw e;
        }

        byte[] date = null;
        if (object != null) {
            date = object.toString().getBytes();
        }

        return date;
    }

    /**
     * 远程开帐
     */
    public RemoteResultInfoEntity MakeOutBill(String account, String deviceID,
                                              RemoteDataEntity data) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_MAKEOUTBILL,
                    account, deviceID, data.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "远程开帐", e);
            throw e;
        }

        RemoteResultInfoEntity entity = null;
        if (object != null) {
            entity = RemoteResultInfoEntity.fromJSON(object);
        } else {
            entity = new RemoteResultInfoEntity();
        }

        return entity;
    }

    /**
     * 远程计算金额
     */
    public RemoteResultInfoEntity CalculateCash(String account,
                                                String deviceID, RemoteDataEntity data) throws JSONException,
            ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_CALCULATECASH,
                    account, deviceID, data.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "远程计算金额", e);
            throw e;
        }

        RemoteResultInfoEntity entity = null;
        if (object != null) {
            entity = RemoteResultInfoEntity.fromJSON(object);
        } else {
            entity = new RemoteResultInfoEntity();
        }

        return entity;
    }

    /**
     * 提交延迟
     */
    public ResultInfoEntity CommitDelayInfo(String account, String deviceID,
                                            DelayPartialInfoEntity info) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_COMMITDELAYINFO,
                    account, deviceID, info.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "提交延迟", e);
            throw e;
        }

        ResultInfoEntity entity = null;
        if (object != null) {
            entity = ResultInfoEntity.fromJSON(object);
        } else {
            entity = new ResultInfoEntity();
        }

        return entity;
    }

    /**
     * 下载延迟数据
     */
    public String DownloadDelayRenwuBH(String Account,String deviceId) throws JSONException, ApiException {
        String string = null;
        try {
            string = this.callString(
                    WorkApiService.METHOD_DOWNLOADDELAYRENWUBH, Account, deviceId);
        } catch (ApiException e) {
            LogUtil.e("API", "下载延迟数据", e);
            throw e;
        }

        return string;
    }

    /**
     * 下载延迟数据
     */
    public List<DelayInfoEntity> DownloadDelayInfos(String Account,String deviceId) throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(
                    WorkApiService.METHOD_DOWNLOADDELAYINFOS, Account, deviceId);
        } catch (ApiException e) {
            LogUtil.e("API", "下载延迟数据", e);
            throw e;
        }

        List<DelayInfoEntity> list = null;
        if (array != null) {
            list = DelayInfoEntity.fromJSONArray(array);
        }

        return list;
    }

    /**
     * 下载延迟表卡
     */
    public List<BiaoKaXXEntity> QueryDelayBiaoKaXX(String account, String deviceID)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(
                    WorkApiService.METHOD_QUERYDELAYBIAOKAXX, account, deviceID);
        } catch (ApiException e) {
            LogUtil.e("API", "下载延迟数据", e);
            throw e;
        }

        List<BiaoKaXXEntity> list = null;
        if (array != null) {
            list = BiaoKaXXEntity.fromJSONArray(array);
        } else {
            list = new ArrayList<>();
        }

        return list;
    }

    /**
     * 下载延迟欠费信息
     *
     * @throws JSONException
     */
    public List<QianFeiXXEntity> getDelayQianFeiXXs(String account, String deviceId)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(WorkApiService.METHOD_QUERYDELAYQIANFEI,
                    account, deviceId);
        } catch (ApiException e) {
            LogUtil.e("API", "查询延迟欠费信息调用失败", e);
            throw e;
        }

        List<QianFeiXXEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = QianFeiXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 获取延迟抄表记录
     */
    public List<ChaoBiaoJLEntity> queryDelayChaoBiaoJL(String account, String deviceId)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(WorkApiService.METHOD_QUERYDELAYCHAOBIAOJL, account, deviceId);
        } catch (ApiException e) {
            LogUtil.e("API", "延迟抄表记录调用失败", e);
            throw e;
        }

        List<ChaoBiaoJLEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = ChaoBiaoJLEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询延迟缴费信息
     */
    public List<JiaoFeiXXEntity> queryDelayJiaoFeiXX(String account, String deviceId)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(WorkApiService.METHOD_QUERYDELAYJIAOFEIXX, account, deviceId);
        } catch (ApiException e) {
            LogUtil.e("API", "延期缴费信息调用失败", e);
            throw e;
        }

        List<JiaoFeiXXEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = JiaoFeiXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 延迟换表信息
     *
     * @return
     */
    public List<HuanBiaoXXEntity> queryDelayHuanBiaoXX(String account, String deviceId)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(WorkApiService.METHOD_QUERYDELAYHUANBIAOXX, account, deviceId);
        } catch (ApiException e) {
            LogUtil.e("API", "延迟换表记录调用失败", e);
            throw e;
        }

        List<HuanBiaoXXEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = HuanBiaoXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 同步延迟数据
     */
    public List<DelayInfoEntity> SyncDelayInfos(String account,
                                                String deviceID, List<DelayInfoEntity> list) throws JSONException,
            ApiException {
        System.out.println("SyncDelayInfos:" + Thread.currentThread().getName());
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SYNCDELAYINFOS,
                    account, deviceID, DelayInfoEntity.toJSONArray(list));
        } catch (ApiException e) {
            LogUtil.e("API", "同步延迟数据", e);
            throw e;
        }

        List<DelayInfoEntity> delayInfoEntities;
        if (array != null) {
            delayInfoEntities = DelayInfoEntity.fromJSONArray(array);
        } else {
            delayInfoEntities = new ArrayList<>();
        }

        return delayInfoEntities;
    }

    /**
     * 上传延迟数据
     */
    public ResultInfoEntity UploadDelayInfo(String account, String deviceID,
                                            RemoteDataEntity data) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_UPLOADDELAYINFO,
                    account, deviceID, data.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "上传延迟数据", e);
            throw e;
        }

        ResultInfoEntity entity = null;
        if (object != null) {
            entity = ResultInfoEntity.fromJSON(object);
        } else {
            entity = new ResultInfoEntity();
        }

        return entity;
    }

    /**
     * 下载外复工单主信息
     */
    public List<ExternalWorkMainInfoEntity> DownloadExternalWorkMainInfos(
            String account, String deviceID, String taskIds)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(
                    WorkApiService.METHOD_DOWNLOADEXTERNALWORKMAININFOS,
                    account, deviceID, taskIds);
        } catch (ApiException e) {
            LogUtil.e("API", "下载外复工单主信息", e);
            throw e;
        }

        List<ExternalWorkMainInfoEntity> list = null;
        if (array != null) {
            list = ExternalWorkMainInfoEntity.fromJSONArray(array);
        } else {
            list = new ArrayList<ExternalWorkMainInfoEntity>();
        }

        return list;
    }

    /**
     * 下载外复工单明细信息
     */
    public List<ExternalWorkDetailInfoEntity> DownloadExternalWorkDetailInfos(
            String account, String deviceID, int taskId) throws JSONException,
            ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(
                    WorkApiService.METHOD_DOWNLOADEXTERNALWORKDETAILINFOS,
                    account, deviceID, taskId);
        } catch (ApiException e) {
            LogUtil.e("API", "下载外复工单明细信息", e);
            throw e;
        }

        List<ExternalWorkDetailInfoEntity> list = null;
        if (array != null) {
            list = ExternalWorkDetailInfoEntity.fromJSONArray(array);
        } else {
            list = new ArrayList<ExternalWorkDetailInfoEntity>();
        }

        return list;
    }

    /**
     * 上传外复工单数据，下载需要更新的数据
     */
    public List<ExternalWorkMainInfoEntity> SyncExternalWorks(String account,
                                                              String deviceID, List<ExternalWorkPartialMainInfoEntity> mainInfos,
                                                              List<ExternalWorkPartialDetailInfoEntity> detailInfos)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SYNCEXTERNALWORKS,
                    account, deviceID, ExternalWorkPartialMainInfoEntity
                            .toJSONArray(mainInfos),
                    ExternalWorkPartialDetailInfoEntity
                            .toJSONArray(detailInfos));
        } catch (ApiException e) {
            LogUtil.e("API", "上传外复工单数据，下载需要更新的数据", e);
            throw e;
        }

        List<ExternalWorkMainInfoEntity> list = null;
        if (array != null) {
            list = ExternalWorkMainInfoEntity.fromJSONArray(array);
        } else {
            list = new ArrayList<ExternalWorkMainInfoEntity>();
        }

        return list;
    }

    /**
     * 上传单条外复工单数据
     *
     * @throws ApiException , JSONException
     */
    public ResultInfoEntity UploadExternalWork(String account, String deviceID,
                                               ExternalWorkPartialMainInfoEntity mainInfo,
                                               List<ExternalWorkPartialDetailInfoEntity> detailInfos)
            throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(
                    WorkApiService.METHOD_UPLOADEXTERNALWORK, account,
                    deviceID, ExternalWorkPartialMainInfoEntity
                            .toJSON(mainInfo),
                    ExternalWorkPartialDetailInfoEntity
                            .toJSONArray(detailInfos));
        } catch (ApiException e) {
            LogUtil.e("API", "上传单条外复工单数据", e);
            throw e;
        }

        ResultInfoEntity entity = null;
        if (object != null) {
            entity = ResultInfoEntity.fromJSON(object);
        } else {
            entity = new ResultInfoEntity();
        }

        return entity;
    }

    /**
     * 根据任务类型和站点得到对应的员工信息
     */
    public List<WorkStaffEntity> GetWorkStaffs(int type, String station)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_GETWORKSTAFFS,
                    type, station);
        } catch (ApiException e) {
            LogUtil.e("API", "根据任务类型和站点得到对应的员工信息", e);
            throw e;
        }

        List<WorkStaffEntity> list = null;
        if (array != null) {
            list = WorkStaffEntity.fromJSONArray(array);
        } else {
            list = new ArrayList<WorkStaffEntity>();
        }

        return list;
    }

    /**
     * 获得册本轨迹
     */
    public List<VolumeTrackEntity> GetVolumeTracks(String volume)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_GETVOLUMETRACKS, volume);
        } catch (ApiException e) {
            LogUtil.e("API", "获得册本轨迹失败", e);
            throw e;
        }

        List<VolumeTrackEntity> list = null;
        if (array != null) {
            list = VolumeTrackEntity.fromJSONArray(array);
        } else {
            list = new ArrayList<VolumeTrackEntity>();
        }

        return list;
    }


    /**
     * 查询故障换表, 拆表登记, 养护工单
     *
     * @param chaiBiaoDJSearch
     * @return
     */
    public List<ChaiBiaoDJSearchResultEntity> SearchGuZhangHBList(ChaiBiaoDJSearchEntity chaiBiaoDJSearch) throws JSONException, ApiException {

        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SEARCH_GUZHANG_HB,
                    new Object[]{chaiBiaoDJSearch.toJSON()});
        } catch (ApiException e) {
            LogUtil.e("API", "查询故障换表信息失败", e);
            throw e;
        }
        List<ChaiBiaoDJSearchResultEntity> dateInfo = null;
        if (array != null) {
            dateInfo = ChaiBiaoDJSearchResultEntity.fromJSONArray(array);
        }
        return dateInfo;
    }

    /**
     * 查询未销减免1, 未消调整1, 差额调整1, 已销减免1, 已销调整1, 底码调整1
     *
     * @param gongDanBH 工单编号
     * @return String
     */
    public String SearchWeiXiaoJM(String gongDanBH) throws JSONException, ApiException {
        String result = "";
        try {
            result = this.callString(WorkApiService.METHOD_SEARCH_WEIXIAOJM, gongDanBH);
        } catch (ApiException e) {
            LogUtil.e("API", "查询未销减免1信息失败", e);
            throw e;
        }
        return result;
    }

    /**
     * 查询未销减免1, 未消调整1, 已销减免1, 已销调整1
     *
     * @param zhangWuCLCommonSearch
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ZhangWuCLCommonSearchResultEntity> SearchWeiXiaoJMList(ZhangWuCLCommonSearchEntity zhangWuCLCommonSearch, boolean isChaE)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SEARCH_WEIXIAOCL,
                    zhangWuCLCommonSearch.toJSON(),
                    isChaE);
        } catch (ApiException e) {
            LogUtil.e("API", "查询未销减免1信息失败", e);
            throw e;
        }

        List<ZhangWuCLCommonSearchResultEntity> dataList = null;
        if (array != null) {
            dataList = ZhangWuCLCommonSearchResultEntity.fromJSONArray(array);
        }
        return dataList;

    }

    /**
     * 复核回填退单
     *
     * @param sanLaiTDEntity
     * @param loginInfo2Entity
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitFuHeTD(SanLaiTDEntity sanLaiTDEntity,
                                LoginInfo2Entity loginInfo2Entity)
            throws JSONException, ApiException {
        boolean result = false;

        try {
            result = this.callBoolean(WorkApiService.METHOD_COMMITFUHETD,
                    sanLaiTDEntity.toJSON(),
                    loginInfo2Entity.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "复核回填退单失败", e);
            throw e;
        }

        return result;
    }

    /**
     * @param fuHeHTEntity     复合类
     * @param uploadInfoList   上传类
     * @param loginInfo2Entity 登陆类
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitFuHeHT(FuHeHTEntity fuHeHTEntity,
                                List<UploadInfoEntity> uploadInfoList,
                                LoginInfo2Entity loginInfo2Entity)
            throws JSONException, ApiException {
        boolean result = false;

        try {
            result = this.callBoolean(WorkApiService.METHOD_FUHE_HUTIAN,
                    fuHeHTEntity.toJSON(),
                    UploadInfoEntity.toJSONArray(uploadInfoList),
                    loginInfo2Entity.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "复合回填失败", e);
            throw e;
        }

        return result;
    }

    /**
     * 登记故障换表, 拆表登记
     *
     * @param chaiBiaoDJEntity 抄表登记
     * @param yuYueXXEntity    预约信息
     * @param loginInfo2Entity 登录信息
     * @param uploadInfo_list1 上传信息1
     * @param uploadInfo_list2 上传信息2
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitGuZhangHB(ChaiBiaoDJEntity chaiBiaoDJEntity,
                                   YuYueXXEntity yuYueXXEntity,
                                   LoginInfo2Entity loginInfo2Entity,
                                   List<UploadInfoEntity> uploadInfo_list1,
                                   List<UploadInfoEntity> uploadInfo_list2) throws JSONException, ApiException {
        boolean result = false;
        try {
            result = this.callBoolean(WorkApiService.METHOD_GUZHANG_HB,
                    chaiBiaoDJEntity.toJSON(),
                    yuYueXXEntity.toJSON(),
                    loginInfo2Entity.toJSON(),
                    UploadInfoEntity.toJSONArray(uploadInfo_list1),
                    UploadInfoEntity.toJSONArray(uploadInfo_list2));
        } catch (ApiException e) {
            LogUtil.e("API", "登记故障换表失败", e);
            throw e;
        }
        return result;
    }


    /**
     * 查询复核单详情
     *
     * @param gongDanBH 工单编号
     * @return FuHeDXXEntity
     * @throws JSONException
     * @throws ApiException
     */
    public FuHeDXXEntity SearchFuHeDXX(String gongDanBH) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(
                    WorkApiService.METHOD_FUHED_XX, gongDanBH);
        } catch (ApiException e) {
            LogUtil.e("API", "查询复核单详情数据失败", e);
            throw e;
        }
        FuHeDXXEntity entity = null;
        if (object != null) {
            entity = FuHeDXXEntity.fromJSON(object);
        }
        return entity;
    }

    /**
     * 未销减免2提交审核, 差额调整2, 未消调整2，已销调整2，已销减免2
     *
     * @param tiaoZhengJMEntity 调整减免
     * @param loginInfo2Entity  登录信息
     * @param uploadInFo_list   上传信息
     * @return boolean
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitWeiXiaoJM(TiaoZhengJMEntity tiaoZhengJMEntity,
                                   LoginInfo2Entity loginInfo2Entity,
                                   List<UploadInfoEntity> uploadInFo_list)
            throws JSONException, ApiException {
        boolean result = false;
        try {
            result = this.callBoolean(WorkApiService.METHOD_WEIXIAO_JM,
                    tiaoZhengJMEntity.toJSON(),
                    loginInfo2Entity.toJSON(),
                    UploadInfoEntity.toJSONArray(uploadInFo_list));
        } catch (ApiException e) {
            LogUtil.e("API", "提交审核失败", e);
            throw e;
        }
        return result;
    }

    /**
     * 查询原始费用明细
     *
     * @param feeId       水费编号
     * @param zhangWuLX   账务联系
     * @param zbTableName
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<FeiYongMXEntity> SearchFeiYongMXYSList(int feeId, String zhangWuLX, String zbTableName) throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_FEIYONG_MX_LIST, feeId, zhangWuLX, zbTableName);
        } catch (ApiException e) {
            LogUtil.e("API", "查询原始费用明细失败", e);
            throw e;
        }
        List<FeiYongMXEntity> dataList = null;
        if (array != null) {
            dataList = FeiYongMXEntity.fromJSONArray(array);
        }
        return dataList;
    }

    /**
     * 查询处理明细、保留明细、预算
     *
     * @param tiaoZhengJMEntity
     * @return List<FeiYongMXEntity>
     * @throws JSONException
     * @throws ApiException
     */
    public List<FeiYongMXEntity> SearchMingXiList(TiaoZhengJMEntity tiaoZhengJMEntity) throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_FEIYONG_MX_YS_LIST, new Object[]{tiaoZhengJMEntity.toJSON()});
        } catch (ApiException e) {
            LogUtil.e("API", "查询处理明细、保留明细、预算失败", e);
            throw e;
        }
        List<FeiYongMXEntity> dataList = null;
        if (array != null) {
            dataList = FeiYongMXEntity.fromJSONArray(array);
        }
        return dataList;
    }

    /**
     * 查询现调价号
     *
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<TiaoJiaHXXEntity> SearchTiaoJiaHXXList()
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SEARCHTIAOJIAHXXLIST);
        } catch (ApiException e) {
            LogUtil.e("API", "查询现调价号失败", e);
            throw e;
        }

        List<TiaoJiaHXXEntity> dataList = null;
        if (array != null) {
            dataList = TiaoJiaHXXEntity.fromJSONArray(array);
        }

        return dataList;
    }

    /**
     * 查询底码调整
     *
     * @param cid
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public DiMaTZXXEntity SearchDiMaTZXX(String cid) throws JSONException, ApiException {
        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_SEARCHDIMATZXX, cid);
        } catch (ApiException e) {
            LogUtil.e("API", "查询底码调整失败", e);
            throw e;
        }

        DiMaTZXXEntity entity = null;
        if (object != null) {
            entity = DiMaTZXXEntity.fromJSON(object);
        }

        return entity;
    }

    /**
     * 根据传入账号判断底码调整还是重置底码
     *
     * @param cid
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CheckDiMaTZ(String cid) throws JSONException, ApiException {
        boolean result = false;
        try {
            result = this.callBoolean(WorkApiService.METHOD_CHECKDIMATZ, cid);
        } catch (ApiException e) {
            LogUtil.e("API", "根据传入账号判断底码调整还是重置底码失败", e);
            throw e;
        }

        return result;
    }

    /**
     * 判断是否在底码流程中
     *
     * @param cid
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CheckDiMaTZIsInWorkFlow(String cid) throws JSONException, ApiException {
        boolean result = false;
        try {
            result = this.callBoolean(WorkApiService.METHOD_CHECKDIMATZISINWORKFLOW, cid);
        } catch (ApiException e) {
            LogUtil.e("API", "判断是否在底码流程中失败", e);
            throw e;
        }

        return result;
    }

    /**
     * 提交底码调整
     *
     * @param diMaTZEntity
     * @param loginInfo2Entity
     * @param diMaStatus
     * @param uploadInfoList
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitDiMaTZ(DiMaTZEntity diMaTZEntity,
                                LoginInfo2Entity loginInfo2Entity,
                                int diMaStatus,
                                List<UploadInfoEntity> uploadInfoList)
            throws JSONException, ApiException {
        boolean result = false;

        try {
            result = this.callBoolean(WorkApiService.METHOD_COMMITDIMATZ,
                    diMaTZEntity.toJSON(),
                    loginInfo2Entity.toJSON(),
                    diMaStatus,
                    UploadInfoEntity.toJSONArray(uploadInfoList));
        } catch (ApiException e) {
            LogUtil.e("API", "提交底码调整失败", e);
            throw e;
        }

        return result;
    }

    /**
     * 查询定期换表
     *
     * @param dingQiHBSearchEntity
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<DingQiHBSearchResultEntity> SearchDingQiHB(DingQiHBSearchEntity dingQiHBSearchEntity)
            throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SEARCHDINGQIHB, new Object[]{dingQiHBSearchEntity.toJSON()});
        } catch (ApiException e) {
            LogUtil.e("API", "查询定期换表失败", e);
            throw e;
        }

        List<DingQiHBSearchResultEntity> dataList = null;
        if (array != null) {
            dataList = DingQiHBSearchResultEntity.fromJSONArray(array);
        }

        return dataList;
    }

    /**
     * 登记定期换表
     *
     * @param dingQiHBDJEntity
     * @param loginInfo2Entity
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitDingQiHB(DingQiHBDJEntity dingQiHBDJEntity,
                                  LoginInfo2Entity loginInfo2Entity)
            throws JSONException, ApiException {
        boolean result = false;

        try {
            result = this.callBoolean(WorkApiService.METHOD_DINGQIHBDJ,
                    dingQiHBDJEntity.toJSON(),
                    loginInfo2Entity.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "登记定期换表失败", e);
            throw e;
        }

        return result;
    }

    /**
     * 登记养护工单
     *
     * @param yangHuGDEntity
     * @param loginInfo2Entity
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean CommitYangHuGD(YangHuGDEntity yangHuGDEntity,
                                  LoginInfo2Entity loginInfo2Entity)
            throws JSONException, ApiException {
        boolean result = false;

        try {
            result = this.callBoolean(WorkApiService.METHOD_COMMITYANGHUGD,
                    yangHuGDEntity.toJSON(),
                    loginInfo2Entity.toJSON());
        } catch (ApiException e) {
            LogUtil.e("API", "登记养护工单失败", e);
            throw e;
        }

        return result;
    }

    /**
     * 查询站点信息
     *
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ZhanDianXXEntity> SearchZhanDianXX() throws JSONException, ApiException {
        JSONArray array = null;
        try {
            array = this.callJSONArray(WorkApiService.METHOD_SEARCHZHANDIANXX);
        } catch (ApiException e) {
            LogUtil.e("API", "查询站点信息失败", e);
            throw e;
        }

        List<ZhanDianXXEntity> dataList = null;
        if (array != null) {
            dataList = ZhanDianXXEntity.fromJSONArray(array);
        }

        return dataList;
    }

    /**
     * 上传图片
     *
     * @param loginInfo2Entity
     * @param gongDanBH
     * @param fileName
     * @param data
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean UploadPicture(LoginInfo2Entity loginInfo2Entity,
                                 String gongDanBH,
                                 String fileName,
                                 byte[] data)
            throws JSONException, ApiException {
        try {
            String data2 = Base64.encodeToString(data, Base64.DEFAULT);

            return this.callBoolean(WorkApiService.METHOD_UPLOADPICTURE2,
                    loginInfo2Entity.toJSON(),
                    gongDanBH,
                    fileName,
                    data2);
        } catch (ApiException e) {
            LogUtil.e("API", "上传照片失败", e);
            throw e;
        }
    }

    /**
     * 获取当前账务年月
     *
     * @throws JSONException
     * @throws ApiException
     * @return　DangQianZWNYEntity
     */
    public DangQianZWNYEntity SearchDangQianZWNY() throws JSONException, ApiException {

        JSONObject object = null;
        try {
            object = this.callJSONObject(WorkApiService.METHOD_DANGQIANZHANGWUNY);
        } catch (ApiException e) {
            LogUtil.e("API", "查询底码调整失败", e);
            throw e;
        }

        DangQianZWNYEntity entity = null;
        if (object != null) {
            entity = DangQianZWNYEntity.fromJSON(object);
        }
        return entity;
    }

    /**
     * 判断未销账务处理是否为银行送盘
     *
     * @param S_CID        客户编号
     * @param I_HUAZHANGBH 划账编号
     * @return boolean
     * @throws JSONException
     * @throws ApiException
     */
    public boolean SearchISHUAZHANG(String S_CID, int I_HUAZHANGBH) throws JSONException, ApiException {
        boolean result = false;
        try {
            result = this.callBoolean(WorkApiService.METHOD_SearchISHUAZHANG, I_HUAZHANGBH, S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询底码调整失败", e);
            throw e;
        }
        return result;
    }


}
