/**
 *
 */
package com.sh3h.serverprovider.rpc.service;

import android.util.Base64;

import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.ChaoBiaoRWEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoSJEntity;
import com.sh3h.serverprovider.entity.GongGaoXXEntity;
import com.sh3h.serverprovider.entity.JiChaRWEntity;
import com.sh3h.serverprovider.entity.JiChaSJEntity;
import com.sh3h.serverprovider.entity.RenWuXXEntity;
import com.sh3h.serverprovider.entity.RushPaySJEntity;
import com.sh3h.serverprovider.entity.RushPaySJInfoEntity;
import com.sh3h.serverprovider.entity.RushPaySJResultEntity;
import com.sh3h.serverprovider.entity.WaiFuSJEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liukaiyu
 */
public class SynchronousTaskApiService extends BaseApiService {

    private static final String URL = "SynchronousTask.ashx";

    private static final String METHOD_GETRENWUBHBYCHAOBIAOY = "getRenWuBHByChaoBiaoY";

    private static final String METHOD_GETVIPTASKIDS = "GetVipTaskIds";

    private static final String METHOD_GETCURRENTRWBYCHAOBIAOY = "getCurrentRWByChaoBiaoY";

    private static final String METHOD_UPDATEVIPCHAOBIAOSJTOSERVER = "updateVipChaoBiaoSJToServer";

    private static final String METHOD_UPDATECHAOBIAOSJTOSERVER = "updateChaoBiaoSJToServer";

    private static final String METHOD_DOWNLOADCHAOBIAORW = "downLoadChaoBiaoRW";

    private static final String METHOD_DOWNLOADCHAOBIAOSJ = "downLoadChaoBiaoSJ";

    private static final String METHOD_DOWNLOADALLWORKTASKS = "DownloadAllWorkTasks";

    private static final String METHOD_DOWNLOADVIPCHAOBIAOSJ = "DownloadNewVipRecords";

    private static final String METHOD_UPLOADSINGLECHAOBIAOSJ = "upLoadSingleChaoBiaoSJ";

    // 获取公告信息
    private static final String METHOD_TONGBUGONGGAOXX = "getGongGaoXX";
    private static final String METHOD_GETGONGGAOBYBH = "getGongGaoByGongGaoBH";

    // 上传图片
    private static final String METHOD_UPLOADFILETOSERVER = "upLoadFileToServer";

    // 下载临时册本
    private static final String METHOD_DOWNLOADTEMPORARYRECORDS = "downloadTemporaryRecords";

    // upload the file
    private static final String METHOD_UPLOADFILE = "upLoadFile";

    private static final String METHOD_DOWNLOADVIPCHAOBIAORW = "downLoadVipChaoBiaoRW";

    // 上传图片关系
    private static final String METHOD_UPLOADFILERELATION = "uploadFileRelation";

    //下载催缴数据
    private static final String METHOD_DOWNLOADRUSHPAYS = "DownLoadRushPays";
    //同步更新催缴数据
    private static final String METHOD_SYNCRECORDSRUSHPAY = "SyncRecordsRushPay";
    //查询催缴任务编号
    private static final String METHOD_GETRUSHPAYRENWUBHBYCHAOBIAOY = "getRushPayBHByChaoBiaoY";
    //下载稽查任务
    private static final String METHOD_DOWNLOADJICHARW = "downLoadJiChaRW";
    //查询稽查任务编号
    private static final String METHOD_GETJICHARENWUBHBYCHAOBIAOY = "getJiChaRenWuBHByChaoBiaoY";
    //下载稽查数据
    private static final String METHOD_DOWNLOADJICHASJ = "DownLoadJiChaSJ";
    //同步更新稽查数据
    private static final String METHOD_UPDATEJICHASJTOSERVER = "updateJichaSJToServer";
    //同步更新外复数据
    private static final String METHOD_UPDATEOUTCHAOBIAOSJTOSERVER = "SyncWaiFuCBSJList";
    //下载外复数据
    private static final String METHOD_DOWNLOADOUTCHAOBIAOSJ = "downLoadOutChaoBiaoSJ";



    @Override
    public String getHandlerURL() {
        return URL;
    }

    /**
     * 查询抄表员当前任务编号方法 返回字符串为多个任务编号拼接，已逗号分隔，例如：1,3,6
     *
     * @param Account
     * @return
     * @throws ApiException
     */
    public RenWuXXEntity getRenWuBHByChaoBiaoY(String Account) throws JSONException, ApiException {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETRENWUBHBYCHAOBIAOY,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前任务编号方法调用失败", e);
            throw e;
        }

        RenWuXXEntity renWuXXEntity = null;
        if (jsonObject != null) {
            renWuXXEntity = RenWuXXEntity.fromJSON(jsonObject);
        }

        return renWuXXEntity;
    }


    public RenWuXXEntity getVipTaskIds(String Account) throws JSONException, ApiException {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETVIPTASKIDS,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前Vip任务编号方法调用失败", e);
            throw e;
        }

        RenWuXXEntity renWuXXEntity = null;
        if (jsonObject != null) {
            renWuXXEntity = RenWuXXEntity.fromJSON(jsonObject);
        }

        return renWuXXEntity;
    }

    /**
     * 查询抄表员当前催缴编号方法 返回字符串为多个任务编号拼接，已逗号分隔，例如：1,3,6
     *
     * @param Account
     * @return
     * @throws ApiException
     */
    public RenWuXXEntity getRushPayRenWuBHByChaoBiaoY(String Account) throws JSONException, ApiException {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETRUSHPAYRENWUBHBYCHAOBIAOY,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前催缴任务编号方法调用失败", e);
            throw e;
        }

        RenWuXXEntity renWuXXEntity = null;
        if (jsonObject != null) {
            renWuXXEntity = RenWuXXEntity.fromJSON(jsonObject);
        }

        return renWuXXEntity;
    }

    /**
     * 查询抄表员当前任务列表方法
     *
     * @param Account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoRWEntity> getCurrentRWByChaoBiaoY(String Account)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_GETCURRENTRWBYCHAOBIAOY,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前任务列表方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoRWEntity> list = new ArrayList<ChaoBiaoRWEntity>();
        if (array != null) {
            list = ChaoBiaoRWEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 同步上传更新抄表数据
     *
     * @param list
     * @param deviceID
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> updateChaoBiaoSJToServer(
            List<ChaoBiaoSJEntity> list, String deviceID, String s_chaobiaoybh,
            int renwubh) throws JSONException, ApiException {
        JSONArray array;
        ChaoBiaoSJEntity cbsjentity = new ChaoBiaoSJEntity();
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_UPDATECHAOBIAOSJTOSERVER,
                    cbsjentity.toJSONArray(list), deviceID, s_chaobiaoybh,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传更新抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> returnlist = new ArrayList<>();
        if (array != null) {
            returnlist = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return returnlist;
    }

    /**
     * 同步上传更新VIP抄表数据
     *
     * @param list
     * @param deviceID
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> updateVipChaoBiaoSJToServer(
            List<ChaoBiaoSJEntity> list, String deviceID, String s_chaobiaoybh,
            int renwubh) throws JSONException, ApiException {
        JSONArray array;
        ChaoBiaoSJEntity cbsjentity = new ChaoBiaoSJEntity();
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_UPDATEVIPCHAOBIAOSJTOSERVER,
                    cbsjentity.toJSONArray(list), deviceID, s_chaobiaoybh,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传更新Vip抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> returnlist = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            returnlist = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return returnlist;
    }

    /**
     * 下载新任务
     *
     * @param renwubh
     * @param deviceid
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoRWEntity> downLoadChaoBiaoRW(int renwubh,
                                                     String deviceid, String account) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADCHAOBIAORW,
                    renwubh, deviceid, account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新任务方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoRWEntity> list = new ArrayList<ChaoBiaoRWEntity>();
        if (array != null) {
            list = ChaoBiaoRWEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载新Vip任务
     *
     * @param renwubh
     * @param deviceid
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoRWEntity> downLoadVipChaoBiaoRW(int renwubh,
                                                     String deviceid, String account) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADVIPCHAOBIAORW,
                    renwubh, deviceid, account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新VIP任务方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoRWEntity> list = new ArrayList<ChaoBiaoRWEntity>();
        if (array != null) {
            list = ChaoBiaoRWEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载新抄表数据
     *
     * @param renwubh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> downLoadChaoBiaoSJ(int renwubh)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADCHAOBIAOSJ,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> list = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            list = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载延迟数据
     *
     * @param renwubh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> DownloadAllWorkTasks(int renwubh)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADALLWORKTASKS,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> list = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            list = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载新外复数据
     *
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<WaiFuSJEntity> downLoadWaiFuCBSJ(String account)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADOUTCHAOBIAOSJ,
                    account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载外复数据方法调用失败", e);
            throw e;
        }

        List<WaiFuSJEntity> list = new ArrayList<>();
        if (array != null) {
            list = WaiFuSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 下载新VIP抄表数据
     *
     * @param renwubh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> downLoadVipChaoBiaoSJ(int renwubh)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADVIPCHAOBIAOSJ,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新VIP抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> list = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            list = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 单条抄表数据上传
     *
     * @param cbsjentity
     * @param deviceID
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public int upLoadSingleChaoBiaoSJ(ChaoBiaoSJEntity cbsjentity,
                                      String deviceID) throws JSONException, ApiException {
        // 返回码信息如下：
        // 1 上传成功，需要回写本地上传标志
        // 0 上传失败，该抄表数据已审核，需要回写本地上传和审核标志
        // -1 上传失败，该抄表数据所在的任务已完成，需要清理本地该任务编号下的数据
        // -2 上传失败，该抄表数据所在的任务已撤销，需要清理本地该任务编号下的数据
        try {
            return this.callInt(
                    SynchronousTaskApiService.METHOD_UPLOADSINGLECHAOBIAOSJ,
                    cbsjentity.toJSON(), deviceID);
        } catch (ApiException e) {
            LogUtil.e("API", "单条抄表数据上传方法调用失败", e);
            throw e;
        }

    }

    /**
     * 同步公告接口
     *
     * @param shebeibh
     * @param chaobiaoybh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<GongGaoXXEntity> tongBuGongGaoXX(String shebeibh,
                                                 String chaobiaoybh) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_TONGBUGONGGAOXX, shebeibh,
                    chaobiaoybh);
        } catch (ApiException e) {
            LogUtil.e("API", "同步公告方法调用失败", e);
            throw e;
        }

        List<GongGaoXXEntity> list = new ArrayList<GongGaoXXEntity>();
        if (array != null) {
            list = GongGaoXXEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 根据公告编号获取公告信息
     *
     * @param gonggaobh
     * @param chaobiaoybh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public GongGaoXXEntity getGongGaoXXByBH(int gonggaobh, String chaobiaoybh)
            throws JSONException, ApiException {
        JSONObject object;
        try {
            object = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETGONGGAOBYBH, gonggaobh,
                    chaobiaoybh);
        } catch (ApiException e) {
            LogUtil.e("API", "根据公告编号获取公告信息方法调用失败", e);
            throw e;
        }
        GongGaoXXEntity ggxx = new GongGaoXXEntity();
        if (object != null)
            ggxx = GongGaoXXEntity.fromJSON(object);

        return ggxx;
    }

    /**
     * 上传图片信息
     *
     * @param filename
     * @param s_ch
     * @param chaobiaoid
     * @param s_cid 上传文件 [MEDIA_GROUP] 这个字段要改下（抄表RecordFile  稽查传Sampling，重点户传VipAccount）
     * @return

     * @throws JSONException
     * @throws IOException
     */
    public boolean upLoadFileToServer(String filename,
                                      String s_ch,
                                      int chaobiaoid,
                                      String s_cid,
                                      byte[] buffer)
            throws JSONException, IOException {
        try {
            String array = Base64.encodeToString(buffer, Base64.DEFAULT);
            return this.callBoolean(
                    SynchronousTaskApiService.METHOD_UPLOADFILETOSERVER,
                    filename, s_ch, chaobiaoid, s_cid, array);
        } catch (ApiException e) {
            LogUtil.e("API", "上传图片信息方法调用失败", e);
            return false;
        }
    }

    /**
     * 上传file关系
     * @param meterReaderId
     * @param cid
     * @param url
     * @param fileHash
     * @param mediaType
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public boolean uploadFileRelation(int meterReaderId,
                                      String cid,
                                      String url,
                                      String fileHash,
                                      String mediaType)
            throws JSONException, IOException {
        try {
            return this.callBoolean(
                    SynchronousTaskApiService.METHOD_UPLOADFILERELATION,
                    meterReaderId, cid, url, fileHash, mediaType);
        } catch (ApiException e) {
            LogUtil.e("API", "上传file关系", e);
            return false;
        }
    }

    /**
     * 下载临时册本抄表数据
     *
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<ChaoBiaoSJEntity> downTemporaryLoadChaoBiaoSJ(String account)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADTEMPORARYRECORDS,
                    account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载临时册本抄表数据方法调用失败", e);
            throw e;
        }

        List<ChaoBiaoSJEntity> list = new ArrayList<ChaoBiaoSJEntity>();
        if (array != null) {
            list = ChaoBiaoSJEntity.fromJSONArray(array);
        }
        return list;
    }



    /**
     * 下载催缴数据
     *
     * @param accouont
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<RushPaySJEntity> downLoadRushPays(String accouont)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADRUSHPAYS,
                    accouont);
        } catch (ApiException e) {
            LogUtil.e("API", "下载催缴数据方法调用失败", e);
            throw e;
        }

        List<RushPaySJEntity> list = new ArrayList<RushPaySJEntity>();
        if (array != null) {
            list = RushPaySJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 同步上传催缴数据
     *
     * @param list
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<RushPaySJResultEntity> updateRushPaySJToServer(List<RushPaySJInfoEntity> list, String account)
            throws JSONException, ApiException {
        JSONArray array;
        RushPaySJInfoEntity rushPaySJInfoEntity = new RushPaySJInfoEntity();
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_SYNCRECORDSRUSHPAY,
                    rushPaySJInfoEntity.toJSONArray(list), account);
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传催缴数据方法调用失败", e);
            throw e;
        }

        List<RushPaySJResultEntity> rushPaySJResultEntities = new ArrayList<>();
        if (array != null) {
            rushPaySJResultEntities = RushPaySJResultEntity.fromJSONArray(array);
        }
        return rushPaySJResultEntities;
    }

    /**
     * 同步上传外复数据
     *
     * @param list
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<WaiFuSJEntity> updateWaiFuSJToServer(List<WaiFuSJEntity> list, String account)
            throws JSONException, ApiException {
        JSONArray array;
        WaiFuSJEntity waiFuSJEntity = new WaiFuSJEntity();
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_UPDATEOUTCHAOBIAOSJTOSERVER,
                    waiFuSJEntity.toJSONArray(list), account);
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传外复数据方法调用失败", e);
            throw e;
        }

        List<WaiFuSJEntity> waiFuSJEntities = new ArrayList<>();
        if (array != null) {
            waiFuSJEntities = WaiFuSJEntity.fromJSONArray(array);
        }
        return waiFuSJEntities;
    }

    /**
     * upload the file
     * @param account
     * @param filename
     * @param buffer
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public boolean uploadFile(String account,
                              String filename,
                              byte[] buffer)
            throws JSONException, IOException {
        try {
            String array = Base64.encodeToString(buffer, Base64.DEFAULT);
            return this.callBoolean(
                    SynchronousTaskApiService.METHOD_UPLOADFILE,
                    account, filename, array);
        } catch (ApiException e) {
            LogUtil.e("API", "上传文件方法调用失败", e);
            return false;
        }
    }

    /**
     * 下载稽查任务
     *
     * @param renwubh
     * @param deviceid
     * @param account
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<JiChaRWEntity> downLoadJiChaRW(int renwubh,
                                               String deviceid, String account) throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADJICHARW,
                    renwubh, deviceid, account);
        } catch (ApiException e) {
            LogUtil.e("API", "下载新任务方法调用失败", e);
            throw e;
        }

        List<JiChaRWEntity> list = new ArrayList<JiChaRWEntity>();
        if (array != null) {
            list = JiChaRWEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 查询抄表员当前稽查任务编号方法 返回字符串为多个任务编号拼接，已逗号分隔，例如：1,3,6
     *
     * @param Account
     * @return
     * @throws ApiException
     */
    public RenWuXXEntity getJichaRenWuBHByChaoBiaoY(String Account) throws JSONException, ApiException {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.callJSONObject(
                    SynchronousTaskApiService.METHOD_GETJICHARENWUBHBYCHAOBIAOY,
                    Account);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表员当前稽查任务编号方法调用失败", e);
            throw e;
        }

        RenWuXXEntity renWuXXEntity = null;
        if (jsonObject != null) {
            renWuXXEntity = RenWuXXEntity.fromJSON(jsonObject);
        }

        return renWuXXEntity;
    }

    /**
     * 下载稽查数据
     *
     * @param renwubh
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<JiChaSJEntity> downLoadJiChaSJ(int renwubh)
            throws JSONException, ApiException {
        JSONArray array;
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_DOWNLOADJICHASJ,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "下载稽查数据方法调用失败", e);
            throw e;
        }

        List<JiChaSJEntity> list = new ArrayList<JiChaSJEntity>();
        if (array != null) {
            list = JiChaSJEntity.fromJSONArray(array);
        }
        return list;
    }

    /**
     * 同步上传稽查数据
     *
     * @param list
     * @param deviceID
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<JiChaSJEntity> updateJiChaSJToServer(
            List<JiChaSJEntity> list, String deviceID, String s_chaobiaoybh,
            int renwubh) throws JSONException, ApiException {
        JSONArray array;
        JiChaSJEntity cbsjentity = new JiChaSJEntity();
        try {
            array = this.callJSONArray(
                    SynchronousTaskApiService.METHOD_UPDATEJICHASJTOSERVER,
                    cbsjentity.toJSONArray(list), deviceID, s_chaobiaoybh,
                    renwubh);
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传更新抄表数据方法调用失败", e);
            throw e;
        }

        List<JiChaSJEntity> returnlist = new ArrayList<JiChaSJEntity>();
        if (array != null) {
            returnlist = JiChaSJEntity.fromJSONArray(array);
        }
        return returnlist;
    }


}
