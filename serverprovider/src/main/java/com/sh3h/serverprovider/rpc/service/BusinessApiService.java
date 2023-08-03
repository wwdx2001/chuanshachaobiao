/**
 * @author liukaiyu
 */
package com.sh3h.serverprovider.rpc.service;

import android.widget.Toast;

import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.BiaoKaXXEntity;
import com.sh3h.serverprovider.entity.BillPreviewEntity;
import com.sh3h.serverprovider.entity.CalculateBillEntity;
import com.sh3h.serverprovider.entity.ChaoBiaoJLEntity;
import com.sh3h.serverprovider.entity.CombinedInfoEntity;
import com.sh3h.serverprovider.entity.FeiYongZKLEntity;
import com.sh3h.serverprovider.entity.HuanBiaoXXEntity;
import com.sh3h.serverprovider.entity.JiaoFeiXXEntity;
import com.sh3h.serverprovider.entity.MeterStateEntity;
import com.sh3h.serverprovider.entity.QianFeiXXEntity;
import com.sh3h.serverprovider.entity.ShuiLiangFTXXEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理服务
 */
public class BusinessApiService extends BaseApiService {

    private static final String URL = "Business.ashx";

    private static final String METHOD_QUERYQIANFEI = "QueryQianF";
    private static final String METHOD_QUERYBIAOKAXX = "QueryBiaoKaXX";
    private static final String METHOD_QUERYJIAOFEIXX = "QueryJiaoFeiXX";
    private static final String METHOD_QUERYHUANBIAOXX = "QueryHuanBiaoXX";
    private static final String METHOD_QUERYCHAOBIAOXX = "QueryChaoBiaoXX";

    private static final String METHOD_QUERYALLQIANFEI = "GetQianFeiXXByCh";
    private static final String METHOD_QUERYALLJIAOFEIXX = "GetJiaoFeiXXByCh";
    private static final String METHOD_QUERYALLHUANBIAOXX = "GetHuanBiaoXXByCh";
    private static final String METHOD_QUERYALLCHAOBIAOXX = "GetChaoBiaoJLByCh";

    private static final String METHOD_ISQIANFEI = "IsQianFei";
    private static final String METHOD_ISHUANBIAOZT = "IsHuanBiaoZT";

    private static final String METHOD_QUERY_METERSTATE = "QueryMeterState";
    private static final String METHOD_UPDATEBIAOKAGIS = "PDA_UpdateBiaoKaGIS";

    private static final String METHOD_QUERYBIAOKAXXLIST = "QueryBiaoKaXXList";

    private static final String METHOD_QUERYVIPBIAOKAXXLIST = "QueryVipBiaoKaXXList";

    private static final String METHOD_QUERYFEIYONGZHEKL = "QueryFeiYongZKLByCeBen";

    private static final String METHOD_QUERYSHUILIANGFTXXLIST = "QueryShuiLiangFTXXList";

    private static final String METHOD_QUERYSHUILIANGFTXX = "QueryShuiLiangFTXX";

    private static final String METHOD_GETCOMBINEDQUERINGRESULTS = "GetCombinedQueryingResults";

    private static final String METHOD_UPDATEBIAOKAXXLIST = "UpdateBiaoKaXXList";

    private static final String METHOD_QUERYBIAOKAXXLISTBYTASKID = "QueryBiaoKaXXListByTaskId";

    private static final String METHOD_QUERYWAIFUBIAOKAXXLIST = "QueryWaiFuBiaoKaXXList";

    private static final String METHOD_CALCULATE_CASH = "CalculateCash";

    @Override
    public String getHandlerURL() {
        return BusinessApiService.URL;
    }

    /**
     * 查询欠费信息
     *
     * @throws JSONException
     */
    public List<QianFeiXXEntity> queryQianF(String S_CID) throws ApiException,
            JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(BusinessApiService.METHOD_QUERYQIANFEI,
                    S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询欠费信息调用失败", e);
            throw e;
        }

        List<QianFeiXXEntity> list = new ArrayList<QianFeiXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = QianFeiXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询所有欠费信息
     *
     * @throws JSONException
     */
    public List<QianFeiXXEntity> queryAllQianF(String volume, int month) throws ApiException,
            JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(BusinessApiService.METHOD_QUERYALLQIANFEI, volume, month);
        } catch (ApiException e) {
            LogUtil.e("API", "查询欠费信息调用失败", e);
            throw e;
        }

        List<QianFeiXXEntity> list = new ArrayList<QianFeiXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = QianFeiXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 根据册本号查询费用折扣率
     *
     * @param S_CH
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public List<FeiYongZKLEntity> queryFeiYongZKL(String S_CH)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYFEIYONGZHEKL, S_CH);
        } catch (ApiException e) {
            LogUtil.e("API", "查询费用折扣率信息调用失败", e);
            throw e;
        }

        List<FeiYongZKLEntity> list = new ArrayList<FeiYongZKLEntity>();
        if (resp != null && resp.length() > 0) {
            list = FeiYongZKLEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 根据册本号查询表卡信息
     *
     * @param S_CH
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public List<BiaoKaXXEntity> queryBiaoKaXXList(String S_CH)
            throws ApiException, JSONException {
        JSONArray resp;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYBIAOKAXXLIST, S_CH);
        } catch (ApiException e) {
            LogUtil.e("API", "根据册本号查询表卡信息调用失败", e);
            throw e;
        }

        List<BiaoKaXXEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = BiaoKaXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    public List<BiaoKaXXEntity> queryVipBiaoKaXXList(String taskId)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYVIPBIAOKAXXLIST, taskId);
        } catch (ApiException e) {
            LogUtil.e("API", "根据册本号查询VIP表卡信息调用失败", e);
            throw e;
        }

        List<BiaoKaXXEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = BiaoKaXXEntity.fromJSONArray(resp);
        }

        return list;
    }


    /**
     * 根据稽查任务号查询表卡信息
     *
     * @param taskid
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public List<BiaoKaXXEntity> queryBiaoKaXXListByTaskId(int taskid)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYBIAOKAXXLISTBYTASKID, taskid);
        } catch (ApiException e) {
            LogUtil.e("API", "根据稽查任务号查询表卡信息调用失败", e);
            throw e;
        }

        List<BiaoKaXXEntity> list = new ArrayList<BiaoKaXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = BiaoKaXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 根据外复登录名查询表卡信息
     *
     * @param account
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public List<BiaoKaXXEntity> queryBiaoKaXXListByAccount(String account)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYWAIFUBIAOKAXXLIST, account);
        } catch (ApiException e) {
            LogUtil.e("API", "根据外复登录名查询表卡信息调用失败", e);
            throw e;
        }

        List<BiaoKaXXEntity> list = new ArrayList<BiaoKaXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = BiaoKaXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 实时查询表卡信息
     *
     * @param S_CID
     * @return
     * @throws ApiException
     */
    public BiaoKaXXEntity queryBiaoKaXX(String S_CID) throws ApiException {
        JSONObject resp = null;
        try {
            resp = this.callJSONObject(BusinessApiService.METHOD_QUERYBIAOKAXX,
                    S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询表卡信息调用失败", e);
            throw e;
        }

        BiaoKaXXEntity bkxx = new BiaoKaXXEntity();
        if (resp != null) {
            bkxx = BiaoKaXXEntity.fromJSON(resp);
        }

        return bkxx;
    }

    /**
     * 查询缴费信息
     *
     * @param S_CID
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public List<JiaoFeiXXEntity> queryJiaoFeiXX(String S_CID)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(BusinessApiService.METHOD_QUERYJIAOFEIXX,
                    S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询缴费信息调用失败", e);
            throw e;
        }

        List<JiaoFeiXXEntity> list = new ArrayList<JiaoFeiXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = JiaoFeiXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询所有缴费信息
     */
    public List<JiaoFeiXXEntity> queryAllJiaoFeiXX(String volume, int month)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(BusinessApiService.METHOD_QUERYALLJIAOFEIXX,
                    volume, month);
        } catch (ApiException e) {
            LogUtil.e("API", "查询缴费信息调用失败", e);
            throw e;
        }

        List<JiaoFeiXXEntity> list = new ArrayList<JiaoFeiXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = JiaoFeiXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询换表信息
     *
     * @param S_CID
     * @return
     */
    public List<HuanBiaoXXEntity> queryHuanBiaoXX(String S_CID)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYHUANBIAOXX, S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询换表记录调用失败", e);
            throw e;
        }

        List<HuanBiaoXXEntity> list = new ArrayList<HuanBiaoXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = HuanBiaoXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询所有换表信息
     *
     * @param volume,month
     * @return
     */
    public List<HuanBiaoXXEntity> queryAllHuanBiaoXX(String volume, int month)
            throws ApiException, JSONException {
        List<HuanBiaoXXEntity> list = new ArrayList<>();
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYALLHUANBIAOXX, volume, month);
        } catch (ApiException e) {
            LogUtil.e("API", "查询换表记录调用失败", e);
            throw e;
        }

//        List<HuanBiaoXXEntity> list = new ArrayList<>();
        if (resp != null && resp.length() > 0) {
            list = HuanBiaoXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询抄表信息
     *
     * @param S_CID
     * @return
     */
    public List<ChaoBiaoJLEntity> queryChaoBiaoXX(String S_CID)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYCHAOBIAOXX, S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表记录调用失败", e);
            throw e;
        }

        List<ChaoBiaoJLEntity> list = new ArrayList<ChaoBiaoJLEntity>();
        if (resp != null && resp.length() > 0) {
            list = ChaoBiaoJLEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 获取所有抄表记录
     */
    public List<ChaoBiaoJLEntity> queryAllChaoBiaoXX(String volume, int month)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(BusinessApiService.METHOD_QUERYALLCHAOBIAOXX, volume, month);
        } catch (ApiException e) {
            LogUtil.e("API", "查询抄表记录调用失败", e);
            throw e;
        }

        List<ChaoBiaoJLEntity> list = new ArrayList<ChaoBiaoJLEntity>();
        if (resp != null && resp.length() > 0) {
            list = ChaoBiaoJLEntity.fromJSONArray(resp);
        }

        return list;
    }


    /**
     * 查询是否为换表状态
     *
     * @param S_CID
     * @return
     * @throws JSONException
     */
    public boolean isHuanBiaoZT(String S_CID) throws ApiException {
        try {
            return this.callBoolean(BusinessApiService.METHOD_ISHUANBIAOZT,
                    S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询是否为换表状态调用失败", e);
            throw e;
        }
    }

    /**
     * 查询用户是否欠费
     *
     * @param S_CID
     * @return
     * @throws JSONException
     */
    public boolean isQianFei(String S_CID) throws ApiException {
        try {
            return this.callBoolean(BusinessApiService.METHOD_ISQIANFEI, S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询用户是否欠费调用失败", e);
            throw e;
        }
    }

    /**
     * 查询用户状态
     *
     * @param S_CID
     * @return
     * @throws JSONException
     */
    public MeterStateEntity queryMeterState(String S_CID) throws ApiException {
        JSONObject resp = null;
        try {
            resp = this.callJSONObject(
                    BusinessApiService.METHOD_QUERY_METERSTATE, S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询用户状态调用失败", e);
            throw e;
        }

        MeterStateEntity bkxx = new MeterStateEntity();
        if (resp != null) {
            bkxx = MeterStateEntity.fromJSON(resp);
        }

        return bkxx;
    }

    /**
     * 更新表卡信息表中GIS坐标
     *
     * @param S_CID
     * @param S_X
     * @param S_Y
     * @return
     * @throws ApiException
     */
    public boolean UpdateBiaoKaGIS(String S_CID, String S_X, String S_Y)
            throws ApiException {
        try {
            return this.callBoolean(BusinessApiService.METHOD_UPDATEBIAOKAGIS,
                    S_CID, S_X, S_Y);
        } catch (ApiException e) {
            LogUtil.e("API", "更新表卡信息表中GIS坐标失败", e);
            throw e;
        }
    }

    /**
     * 根据册本号查询水量分摊信息
     *
     * @param S_CH
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public List<ShuiLiangFTXXEntity> queryShuiLiangFTXXList(String S_CH)
            throws ApiException, JSONException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_QUERYSHUILIANGFTXXLIST, S_CH);
        } catch (ApiException e) {
            LogUtil.e("API", "根据册本号查询水量分摊信息调用失败", e);
            throw e;
        }

        List<ShuiLiangFTXXEntity> list = new ArrayList<ShuiLiangFTXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = ShuiLiangFTXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * 查询水量分摊信息
     *
     * @param S_CID
     * @return
     * @throws ApiException
     * @throws JSONException
     */
    public ShuiLiangFTXXEntity queryShuiLiangFTXX(String S_CID)
            throws ApiException, JSONException {
        JSONObject resp = null;
        try {
            resp = this.callJSONObject(
                    BusinessApiService.METHOD_QUERYSHUILIANGFTXX, S_CID);
        } catch (ApiException e) {
            LogUtil.e("API", "查询水量分摊信息调用失败", e);
            throw e;
        }

        ShuiLiangFTXXEntity entity = new ShuiLiangFTXXEntity();
        if (resp != null) {
            entity = ShuiLiangFTXXEntity.fromJSON(resp);
        }

        return entity;
    }


    /**
     * 组合查询
     *
     * @return
     */
    public List<BiaoKaXXEntity> getCombinedQueryingResults(CombinedInfoEntity combinedInfoEntity) throws ApiException, JSONException {

        JSONArray resp = null;
        try {
            resp = this.callJSONArray(
                    BusinessApiService.METHOD_GETCOMBINEDQUERINGRESULTS,
                    new Object[]{combinedInfoEntity.toJSON()});
        } catch (ApiException e) {
            LogUtil.e("API", "组合查询  调用失败", e);
            throw e;
        }

        List<BiaoKaXXEntity> list = new ArrayList<BiaoKaXXEntity>();
        if (resp != null && resp.length() > 0) {
            list = BiaoKaXXEntity.fromJSONArray(resp);
        }

        return list;
    }

    /**
     * @param account
     * @param taskId
     * @param biaoKaXXEntityList
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public boolean UpdateBiaoKaXXList(String account,
                                      int taskId,
                                      List<BiaoKaXXEntity> biaoKaXXEntityList)
            throws JSONException, ApiException {
        try {
            return this.callBoolean(
                    BusinessApiService.METHOD_UPDATEBIAOKAXXLIST,
                    account, taskId, BiaoKaXXEntity.toJSONArray(biaoKaXXEntityList));
        } catch (ApiException e) {
            LogUtil.e("API", "同步上传更新表卡数据方法调用失败", e);
            throw e;
        }
    }

    /**
     * 计算金额
     *
     * @return
     * @throws JSONException
     * @throws ApiException
     */
    public List<BillPreviewEntity> CalculateCash(CalculateBillEntity calculateBillEntity)
            throws JSONException, ApiException {
        JSONArray resp = null;
        try {
            resp = this.callJSONArray(BusinessApiService.METHOD_CALCULATE_CASH,
                    CalculateBillEntity.toJSON(calculateBillEntity));
        } catch (ApiException e) {
            LogUtil.e("API", "计算金额方法调用失败", e);
            throw e;
        }

        List<BillPreviewEntity> list= new ArrayList<BillPreviewEntity>();
        if (resp != null && resp.length() > 0) {
            list = BillPreviewEntity.fromJSONArray(resp);
        }
        return list;
    }

}
