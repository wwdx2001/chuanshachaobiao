package com.sh3h.meterreading.ui.InspectionInput.lr;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.InspectionInputActivity;
import com.sh3h.meterreading.ui.InspectionInput.tools.PickerViewUtils;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.BiaoWeiImage;
import com.sh3h.serverprovider.entity.ChaoBiaoHistoryBean;
import com.sh3h.serverprovider.entity.DuShuEntity;
import com.sh3h.serverprovider.entity.DuShuEntity2;
import com.sh3h.serverprovider.entity.ImageItem;
import com.sh3h.serverprovider.entity.XJXXWordBean;
import com.sh3h.serverprovider.entity.YuCeDataEntity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 录入操作页面
 *
 * @author xiaochao.dev@gmail.com
 * @date 2019/12/12 13:17
 */
public class LROperatingFragment extends ParentFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, LROperatingMvpView, TextWatcher {

  private TextView mTvPaiZhaoShiBie;
  private EditText mEtXunJianChaoma, mEtTxm, mEtXjyl, mEtYlsm, mEtBz;
  private TextView mTvXiaogenhao, mTvNB, mTvHuming, mTvDizhi, mTvBiaohao, mTvChaobiaoYL, mTvShangcicm,
    mTvPingjun, mTvChaobiaoRQ, mTvShiShiDS, picturePreview, tuidanInfo, tvYsxz,mTvChaobiaoyccm,mTvChaobiaoycsl;
  private TextView mTvBiaoweizb;
  private Group group;

  private String[] ysxz = {"工业用水", "其他用水", "行政事业用水", "特种用水", "经营服务用水", "居民生活用水"};
  private int biaokaPosition;
  private BiaoKaBean mBiaoKaBean;
  private BiaoKaListBean mBiaoKaListBean;
  private BiaoKaWholeEntity biaoKaWholeEntity;
  private String renWuId;
//  private ArrayList<ImageItem> imageItems;
  private String renwuMC;
  private TextView mTvCbzt;
  private String type;
  private TextView mTvIsGongdanDJ, mEtGongDanDJBZ, mTvMkgzYY, mTvGzhbYY, mTvIsGenghuanBP;
  private List<XJXXWordBean> mGongdanDjWords;
  private List<XJXXWordBean> mMkgzyyWords;
  private List<XJXXWordBean> mGzhbyyWords;
  private List<XJXXWordBean> mSfghbpWords;
  private List<XJXXWordBean> cbztWords;
  private String isGongDanDJValue = "";
  private String isGenghuanBPValue = "";
  private String mokuaigzyyValue = "";
  private String guzhnaghbyyValue = "";
  private String xiaogenghao;
  private String title;


  @Inject
  LROperatingPresenter mLrOperatingPresenter;
  protected Activity mActivity;



  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = getActivity();
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_lroperating, container, false);
    mLrOperatingPresenter.attachView(this);
    initView(view);
    initData();
    return view;
  }

  public void initData2(Bundle bundle) {
    if (bundle != null) {
      biaokaPosition = bundle.getInt(Const.POSITION);
      renWuId = bundle.getString(Const.RENWUID);
      biaoKaWholeEntity = bundle.getParcelable(Const.BIAOKAWHOLEBEAN);
      renwuMC = bundle.getString(Const.RENWUMC);
      type = bundle.getString(Const.TYPE);
      mBiaoKaBean = bundle.getParcelable(Const.BEAN);
      xiaogenghao=bundle.getString(Const.XIAOGENHAO);
      title=bundle.getString(Const.TITLE);
    }
    if (title != null && title.equals("抄表巡检")){
      mEtXunJianChaoma.setHint("抄表巡检抄码");
    }
//    mLrOperatingPresenter.loadBiaoka(xiaogenghao);
//    mLrOperatingPresenter.loadWordData("巡检登记工单");
//    mLrOperatingPresenter.loadWordData("模块故障原因");
//    mLrOperatingPresenter.loadWordData("故障换表原因");
//    mLrOperatingPresenter.loadWordData("是否更换表盘");
//    mLrOperatingPresenter.loadWordData("抄表状态");
    mTvXiaogenhao.setText(mBiaoKaBean.getXIAOGENH());
    mTvNB.setText(mBiaoKaBean.getISNB().equals("1") ? "是" : "否");
    mTvHuming.setText(mBiaoKaBean.getHUMING());
//    SpanUtils.with(mTvShiShiDS)
//      .append("刷新实时读数")
//      .setUnderline()
//      .setForegroundColor(ContextCompat.getColor(getContext()
//        == null ? ActivityUtils.getTopActivity()
//        : getContext(), R.color.colorPrimary)).create();
    SpanUtils.with(mTvChaobiaoyccm)
      .append("获取预测数据")
      .setUnderline()
      .setForegroundColor(ContextCompat.getColor(getContext()
        == null ? ActivityUtils.getTopActivity()
        : getContext(), R.color.colorPrimary)).create();
    SpanUtils.with(mTvChaobiaoycsl)
      .setUnderline()
      .setForegroundColor(ContextCompat.getColor(getContext()
        == null ? ActivityUtils.getTopActivity()
        : getContext(), R.color.colorPrimary)).create();
//        mTvXiaogenhao.setText("2382877828");
//        mTvNB.setText("是");
//        mTvHuming.setText("张三");
    SpanUtils.with(mTvDizhi)
      .append(mBiaoKaBean.getDIZHI())
      .setUnderline()
      .setForegroundColor(ContextCompat.getColor(getContext()
        == null ? ActivityUtils.getTopActivity()
        : getContext(), R.color.colorPrimary)).create();
    if (!StringUtils.isEmpty(mBiaoKaBean.getS_X()) && !StringUtils.isEmpty(mBiaoKaBean.getS_Y())) {
      SpanUtils.with(mTvBiaoweizb)
        .append("最近一次抄准位置（" + mBiaoKaBean.getS_X() + "，" + mBiaoKaBean.getS_Y() + "）")
        .setUnderline()
        .setForegroundColor(ContextCompat.getColor(getContext()
          == null ? ActivityUtils.getTopActivity()
          : getContext(), R.color.colorPrimary)).create();
    }
    mTvBiaohao.setText(mBiaoKaBean.getBIAOHAO());
    mTvChaobiaoYL.setText(String.valueOf(mBiaoKaBean.getCHAOBIAOYL()));
    mTvShangcicm.setText(String.valueOf(mBiaoKaBean.getSHANGCICM()));
    mTvPingjun.setText(String.valueOf(mBiaoKaBean.getQIANSANCIPJ()));
    mTvChaobiaoRQ.setText(mBiaoKaBean.getCHAOBIAORQ());


    mGongdanDjWords = mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_GONGDANDJ);
    LogUtils.e("helloworld222", "isyc=" + mBiaoKaBean.getISYC());
    if ("F".equals(mBiaoKaBean.getISYC())) {// 不是远传表，不能选登记故障模块工单（2）
      List<XJXXWordBean> ycdjWordLists = new ArrayList<>();
      for (int i = 0; i < mGongdanDjWords.size(); i++) {
        if ("2".equals(mGongdanDjWords.get(i).getMVALUE())) {
          continue;
        }
        ycdjWordLists.add(mGongdanDjWords.get(i));
      }
      mGongdanDjWords = ycdjWordLists;
    }
    mMkgzyyWords= mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_MKGZYY);
    mGzhbyyWords = mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_GZHBYY);
    mSfghbpWords = mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_GGBP);


    // 默认不登记工单
    isGongDanDJValue = "0";
    mTvIsGongdanDJ.setText(mGongdanDjWords.size() > 0 ? mGongdanDjWords.get(0).getMNAME() : "不登记工单");

//        if (mBiaoKaBean.getXJLX() != null) {
//            if (!"".equals(mBiaoKaBean.getXJLX())) {
//                if ("2".equals(mBiaoKaBean.getXJLX())) {
//                    changeLayout(View.GONE);
//                } else {
//                    changeLayout(View.VISIBLE);
//                }
//            } else {
//                changeLayout(View.VISIBLE);
//            }
//        } else {
//            changeLayout(View.VISIBLE);
//        }
    if ("1".equals(type)) {
      changeLayout(View.VISIBLE);
    } else if ("2".equals(type)) {
      changeLayout(View.GONE);
    }

    if (type != null && !Const.XUNJIANTASK_TYPE.equals(type)) {
      mTvCbzt.setVisibility(View.GONE);
    }


//    getYuCeData(false);
//    getDuShu(false);
    List<XJXXWordBean> ysxzList =  mLrOperatingPresenter.getHotlineWordData("用水性质",mBiaoKaBean.getYONGSHUIXZ());
    if (ysxzList.size() > 0) {
      tvYsxz.setText(ysxzList.get(0).getMNAME());
    } else {
      tvYsxz.setText(mBiaoKaBean.getYONGSHUIXZ() + " 无匹配词语");
    }

    if (biaoKaWholeEntity != null) {
      mEtXunJianChaoma.setText(biaoKaWholeEntity.getXUNJIANCM());
      mEtTxm.setText(biaoKaWholeEntity.getTXM());
      mEtXjyl.setText(biaoKaWholeEntity.getXUNJIANYL());
      mEtYlsm.setText(biaoKaWholeEntity.getYLSM());
      mEtBz.setText(biaoKaWholeEntity.getBEIZHU());
      mTvCbzt.setText(biaoKaWholeEntity.getCbzt());
      // 保存在本地数据初始化
      isGongDanDJValue = biaoKaWholeEntity.getGONGDANDJ() == null ? "0" : biaoKaWholeEntity.getGONGDANDJ();
      guzhnaghbyyValue = biaoKaWholeEntity.getBIAOWU_GZYY() == null ? "" : biaoKaWholeEntity.getBIAOWU_GZYY();
      mokuaigzyyValue = biaoKaWholeEntity.getMOKUAI_GZYY() == null ? "" : biaoKaWholeEntity.getMOKUAI_GZYY();
      List<XJXXWordBean> wordBeans = mLrOperatingPresenter.getHotlineWordData( mGongdanDjWords.size() > 0 ? mGongdanDjWords.get(0).getMMODULE() : "巡检登记工单",isGongDanDJValue);
      if (wordBeans.size() > 0) {
        mTvIsGongdanDJ.setText(wordBeans.get(0).getMNAME());
      }
      switch (isGongDanDJValue) {
        case "1"://登记故障换表单
          mEtGongDanDJBZ.setVisibility(View.VISIBLE);
          mTvGzhbYY.setVisibility(View.VISIBLE);
          mTvMkgzYY.setVisibility(View.GONE);
          mTvIsGenghuanBP.setVisibility(View.GONE);
          mEtGongDanDJBZ.setText(biaoKaWholeEntity.getGONGDANDJBZ());
          List<XJXXWordBean> gzhbyyBeans = mLrOperatingPresenter.getHotlineWordData( mGzhbyyWords.size() > 0 ? mGzhbyyWords.get(0).getMMODULE() : "故障换表原因",guzhnaghbyyValue);
          if (gzhbyyBeans.size() > 0) {
            mTvGzhbYY.setText(gzhbyyBeans.get(0).getMNAME());
          }
          break;
        case "2": // 登记模块故障工单
          mEtGongDanDJBZ.setVisibility(View.VISIBLE);
          mTvGzhbYY.setVisibility(View.GONE);
          mTvMkgzYY.setVisibility(View.VISIBLE);
          mTvIsGenghuanBP.setVisibility(View.GONE);
          mEtGongDanDJBZ.setText(biaoKaWholeEntity.getGONGDANDJBZ());
          List<XJXXWordBean> mkgzyyBeans = mLrOperatingPresenter.getHotlineWordData( mMkgzyyWords.size() > 0 ? mMkgzyyWords.get(0).getMMODULE() : "故障换表原因",mokuaigzyyValue);
          if (mkgzyyBeans.size() > 0) {
            mTvMkgzYY.setText(mkgzyyBeans.get(0).getMNAME());
          }
          if (mokuaigzyyValue.equals("5") || mokuaigzyyValue.equals("6")) {
            isGenghuanBPValue = biaoKaWholeEntity.getSFGHBP();
            mTvIsGenghuanBP.setVisibility(View.VISIBLE);
            List<XJXXWordBean> sfghbpBeans = mLrOperatingPresenter.getHotlineWordData(  mSfghbpWords.size() > 0 ? mSfghbpWords.get(0).getMMODULE() : "是否更换表盘",isGenghuanBPValue);
            if (sfghbpBeans.size() > 0) {
              mTvIsGenghuanBP.setText(sfghbpBeans.get(0).getMNAME());
            }
          }
          break;
        case "0":
        default:
          mEtGongDanDJBZ.setVisibility(View.GONE);
          mTvGzhbYY.setVisibility(View.GONE);
          mTvMkgzYY.setVisibility(View.GONE);
          mTvIsGenghuanBP.setVisibility(View.GONE);
          break;
      }
    }
    if (StringUtils.isEmpty(mBiaoKaBean.getTDYY()) && StringUtils.isEmpty(mBiaoKaBean.getTDSJ())
      && StringUtils.isEmpty(mBiaoKaBean.getTDR()) && StringUtils.isEmpty(mBiaoKaBean.getTDBZ())) {
      tuidanInfo.setVisibility(View.GONE);
    } else {
      tuidanInfo.setVisibility(View.VISIBLE);
      // 如果是退单，需要重新上传图片
      if (biaoKaWholeEntity != null) {
        biaoKaWholeEntity.setIsUploadImage(false);
        mLrOperatingPresenter.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
      }
    }



  }

  public void initData() {
    Bundle bundle = getArguments();
    if (bundle != null) {
      biaokaPosition = bundle.getInt(Const.POSITION);
      renWuId = bundle.getString(Const.RENWUID);
      biaoKaWholeEntity = bundle.getParcelable(Const.BIAOKAWHOLEBEAN);
      renwuMC = bundle.getString(Const.RENWUMC);
      type = bundle.getString(Const.TYPE);
      mBiaoKaBean = bundle.getParcelable(Const.BEAN);
      xiaogenghao=bundle.getString(Const.XIAOGENHAO);
      title=bundle.getString(Const.TITLE);
    }
    if (title != null && title.equals("抄表巡检")){
      mEtXunJianChaoma.setHint("抄表巡检抄码");
    }
//    mLrOperatingPresenter.loadBiaoka(xiaogenghao);
//    mLrOperatingPresenter.loadWordData("巡检登记工单");
//    mLrOperatingPresenter.loadWordData("模块故障原因");
//    mLrOperatingPresenter.loadWordData("故障换表原因");
//    mLrOperatingPresenter.loadWordData("是否更换表盘");
//    mLrOperatingPresenter.loadWordData("抄表状态");
    mTvXiaogenhao.setText(mBiaoKaBean.getXIAOGENH());
    mTvNB.setText(mBiaoKaBean.getISNB().equals("1") ? "是" : "否");
    mTvHuming.setText(mBiaoKaBean.getHUMING());
    mTvShiShiDS.setText(String.valueOf(mBiaoKaBean.getI_YCCHAOMA()));
//    SpanUtils.with(mTvShiShiDS)
//            .append("刷新实时读数")
//            .setUnderline()
//            .setForegroundColor(ContextCompat.getColor(getContext()
//                    == null ? ActivityUtils.getTopActivity()
//                    : getContext(), R.color.colorPrimary)).create();
    SpanUtils.with(mTvChaobiaoyccm)
            .append("获取预测数据")
            .setUnderline()
            .setForegroundColor(ContextCompat.getColor(getContext()
                    == null ? ActivityUtils.getTopActivity()
                    : getContext(), R.color.colorPrimary)).create();
    SpanUtils.with(mTvChaobiaoycsl)
            .setUnderline()
            .setForegroundColor(ContextCompat.getColor(getContext()
                    == null ? ActivityUtils.getTopActivity()
                    : getContext(), R.color.colorPrimary)).create();
//        mTvXiaogenhao.setText("2382877828");
//        mTvNB.setText("是");
//        mTvHuming.setText("张三");
    SpanUtils.with(mTvDizhi)
            .append(mBiaoKaBean.getDIZHI())
            .setUnderline()
            .setForegroundColor(ContextCompat.getColor(getContext()
                    == null ? ActivityUtils.getTopActivity()
                    : getContext(), R.color.colorPrimary)).create();
    if (!StringUtils.isEmpty(mBiaoKaBean.getS_X()) && !StringUtils.isEmpty(mBiaoKaBean.getS_Y())) {
      SpanUtils.with(mTvBiaoweizb)
              .append("最近一次抄准位置（" + mBiaoKaBean.getS_X() + "，" + mBiaoKaBean.getS_Y() + "）")
              .setUnderline()
              .setForegroundColor(ContextCompat.getColor(getContext()
                      == null ? ActivityUtils.getTopActivity()
                      : getContext(), R.color.colorPrimary)).create();
    }
    mTvBiaohao.setText(mBiaoKaBean.getBIAOHAO());
    mTvChaobiaoYL.setText(String.valueOf(mBiaoKaBean.getCHAOBIAOYL()));
    mTvShangcicm.setText(String.valueOf(mBiaoKaBean.getSHANGCICM()));
    mTvPingjun.setText(String.valueOf(mBiaoKaBean.getQIANSANCIPJ()));
    mTvChaobiaoRQ.setText(mBiaoKaBean.getCHAOBIAORQ());


    mGongdanDjWords = mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_GONGDANDJ);
    LogUtils.e("helloworld222", "isyc=" + mBiaoKaBean.getISYC());
    if ("F".equals(mBiaoKaBean.getISYC())) {// 不是远传表，不能选登记故障模块工单（2）
      List<XJXXWordBean> ycdjWordLists = new ArrayList<>();
      for (int i = 0; i < mGongdanDjWords.size(); i++) {
        if ("2".equals(mGongdanDjWords.get(i).getMVALUE())) {
          continue;
        }
        ycdjWordLists.add(mGongdanDjWords.get(i));
      }
      mGongdanDjWords = ycdjWordLists;
    }
    mMkgzyyWords= mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_MKGZYY);
    mGzhbyyWords = mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_GZHBYY);
    mSfghbpWords = mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORD_GGBP);


    // 默认不登记工单
    isGongDanDJValue = "0";
    mTvIsGongdanDJ.setText(mGongdanDjWords.size() > 0 ? mGongdanDjWords.get(0).getMNAME() : "不登记工单");

//        if (mBiaoKaBean.getXJLX() != null) {
//            if (!"".equals(mBiaoKaBean.getXJLX())) {
//                if ("2".equals(mBiaoKaBean.getXJLX())) {
//                    changeLayout(View.GONE);
//                } else {
//                    changeLayout(View.VISIBLE);
//                }
//            } else {
//                changeLayout(View.VISIBLE);
//            }
//        } else {
//            changeLayout(View.VISIBLE);
//        }
    if ("1".equals(type)) {
      changeLayout(View.VISIBLE);
    } else if ("2".equals(type)) {
      changeLayout(View.GONE);
    }

    if (type != null && !Const.XUNJIANTASK_TYPE.equals(type)) {
      mTvCbzt.setVisibility(View.GONE);
    }


//    getYuCeData(false);
//    getDuShu(true);
    List<XJXXWordBean> ysxzList =  mLrOperatingPresenter.getHotlineWordData("用水性质",mBiaoKaBean.getYONGSHUIXZ());
    if (ysxzList.size() > 0) {
      tvYsxz.setText(ysxzList.get(0).getMNAME());
    } else {
      tvYsxz.setText(mBiaoKaBean.getYONGSHUIXZ() + " 无匹配词语");
    }

    if (biaoKaWholeEntity != null) {
      mEtXunJianChaoma.setText(biaoKaWholeEntity.getXUNJIANCM());
      mEtTxm.setText(biaoKaWholeEntity.getTXM());
      mEtXjyl.setText(biaoKaWholeEntity.getXUNJIANYL());
      mEtYlsm.setText(biaoKaWholeEntity.getYLSM());
      mEtBz.setText(biaoKaWholeEntity.getBEIZHU());
      mTvCbzt.setText(biaoKaWholeEntity.getCbzt());
      // 保存在本地数据初始化
      isGongDanDJValue = biaoKaWholeEntity.getGONGDANDJ() == null ? "0" : biaoKaWholeEntity.getGONGDANDJ();
      guzhnaghbyyValue = biaoKaWholeEntity.getBIAOWU_GZYY() == null ? "" : biaoKaWholeEntity.getBIAOWU_GZYY();
      mokuaigzyyValue = biaoKaWholeEntity.getMOKUAI_GZYY() == null ? "" : biaoKaWholeEntity.getMOKUAI_GZYY();
      List<XJXXWordBean> wordBeans = mLrOperatingPresenter.getHotlineWordData( mGongdanDjWords.size() > 0 ? mGongdanDjWords.get(0).getMMODULE() : "巡检登记工单",isGongDanDJValue);
      if (wordBeans.size() > 0) {
        mTvIsGongdanDJ.setText(wordBeans.get(0).getMNAME());
      }
      switch (isGongDanDJValue) {
        case "1"://登记故障换表单
          mEtGongDanDJBZ.setVisibility(View.VISIBLE);
          mTvGzhbYY.setVisibility(View.VISIBLE);
          mTvMkgzYY.setVisibility(View.GONE);
          mTvIsGenghuanBP.setVisibility(View.GONE);
          mEtGongDanDJBZ.setText(biaoKaWholeEntity.getGONGDANDJBZ());
          List<XJXXWordBean> gzhbyyBeans = mLrOperatingPresenter.getHotlineWordData( mGzhbyyWords.size() > 0 ? mGzhbyyWords.get(0).getMMODULE() : "故障换表原因",guzhnaghbyyValue);
          if (gzhbyyBeans.size() > 0) {
            mTvGzhbYY.setText(gzhbyyBeans.get(0).getMNAME());
          }
          break;
        case "2": // 登记模块故障工单
          mEtGongDanDJBZ.setVisibility(View.VISIBLE);
          mTvGzhbYY.setVisibility(View.GONE);
          mTvMkgzYY.setVisibility(View.VISIBLE);
          mTvIsGenghuanBP.setVisibility(View.GONE);
          mEtGongDanDJBZ.setText(biaoKaWholeEntity.getGONGDANDJBZ());
          List<XJXXWordBean> mkgzyyBeans = mLrOperatingPresenter.getHotlineWordData( mMkgzyyWords.size() > 0 ? mMkgzyyWords.get(0).getMMODULE() : "故障换表原因",mokuaigzyyValue);
          if (mkgzyyBeans.size() > 0) {
            mTvMkgzYY.setText(mkgzyyBeans.get(0).getMNAME());
          }
          if (mokuaigzyyValue.equals("5") || mokuaigzyyValue.equals("6")) {
            isGenghuanBPValue = biaoKaWholeEntity.getSFGHBP();
            mTvIsGenghuanBP.setVisibility(View.VISIBLE);
            List<XJXXWordBean> sfghbpBeans = mLrOperatingPresenter.getHotlineWordData(  mSfghbpWords.size() > 0 ? mSfghbpWords.get(0).getMMODULE() : "是否更换表盘",isGenghuanBPValue);
            if (sfghbpBeans.size() > 0) {
              mTvIsGenghuanBP.setText(sfghbpBeans.get(0).getMNAME());
            }
          }
          break;
        case "0":
        default:
          mEtGongDanDJBZ.setVisibility(View.GONE);
          mTvGzhbYY.setVisibility(View.GONE);
          mTvMkgzYY.setVisibility(View.GONE);
          mTvIsGenghuanBP.setVisibility(View.GONE);
          break;
      }
    }
    if (StringUtils.isEmpty(mBiaoKaBean.getTDYY()) && StringUtils.isEmpty(mBiaoKaBean.getTDSJ())
            && StringUtils.isEmpty(mBiaoKaBean.getTDR()) && StringUtils.isEmpty(mBiaoKaBean.getTDBZ())) {
      tuidanInfo.setVisibility(View.GONE);
    } else {
      tuidanInfo.setVisibility(View.VISIBLE);
      // 如果是退单，需要重新上传图片
      if (biaoKaWholeEntity != null) {
        biaoKaWholeEntity.setIsUploadImage(false);
        mLrOperatingPresenter.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
      }
    }



  }

  private void changeLayout(int gone) {
    mTvCbzt.setVisibility(gone);
    mEtXunJianChaoma.setVisibility(gone);
//    mTvPaiZhaoShiBie.setVisibility(gone);
    mEtTxm.setVisibility(gone);
//    mEtXjyl.setVisibility(gone);
//    mEtYlsm.setVisibility(gone);
  }

  protected void initView(View view) {
    mTvCbzt = view.findViewById(R.id.tv_cbzt);
    mTvPaiZhaoShiBie = view.findViewById(R.id.tv_paizhao);
    mTvPaiZhaoShiBie.setOnClickListener(this);
    mEtXunJianChaoma = view.findViewById(R.id.et_xjcm);
    mEtXunJianChaoma.addTextChangedListener(this);
    mEtTxm = view.findViewById(R.id.et_txm);
    mEtTxm.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() == 4){
          if (mEtTxm.getText().toString().equals(mBiaoKaBean.getBIAOHAO().substring(mBiaoKaBean.getBIAOHAO().length() - 4))) {
            mEtTxm.setText(mBiaoKaBean.getBIAOHAO());
          }
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
    mEtTxm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (!b) {
          String biaohaoSlice = mBiaoKaBean.getBIAOHAO().substring(mBiaoKaBean.getBIAOHAO().length() - 4);
          if (mEtTxm.getText().toString().length() < 4) {
            new AlertDialog.Builder(getActivity())
              .setMessage("当前输入位数小于4位，是否继续输入")
              .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  mEtXunJianChaoma.requestFocus();
                }
              })
              .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  mEtTxm.requestFocus();
                }
              }).create().show();
          } else if (mEtTxm.getText().toString().length() == 4) {
            if (mEtTxm.getText().toString().equals(biaohaoSlice)) {
              mEtTxm.setText(mBiaoKaBean.getBIAOHAO());
            } else {
              new AlertDialog.Builder(getActivity())
                .setMessage("当前输入条码后4位与表号不匹配，请输入完整条码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    mEtXunJianChaoma.requestFocus();
                  }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    mEtTxm.setText("");
                    mEtTxm.requestFocus();
                  }
                }).create().show();
            }
          }else{
            if(!mBiaoKaBean.getBIAOHAO().equals(mEtTxm.getText().toString())){
              new AlertDialog.Builder(getActivity())
                .setMessage("当前输入条码与表号不匹配，是否重新输入")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    mEtXunJianChaoma.requestFocus();
                  }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    mEtTxm.setText("");
                    mEtTxm.requestFocus();
                  }
                }).create().show();
            }
          }
        }
      }
    });
    mTvXiaogenhao = view.findViewById(R.id.tv_xiaogenhao);
    mTvNB = view.findViewById(R.id.tv_yhzt);
    mTvHuming = view.findViewById(R.id.tv_huming);
    mTvDizhi = view.findViewById(R.id.tv_dizhi);
    mTvBiaohao = view.findViewById(R.id.tv_biaohao);
    mTvChaobiaoYL = view.findViewById(R.id.tv_chaobiaoyl);
    mTvShangcicm = view.findViewById(R.id.tv_shangcicm);
    mTvPingjun = view.findViewById(R.id.tv_pingjun);
    mTvChaobiaoRQ = view.findViewById(R.id.tv_chaobiaorq);
    mTvShiShiDS = view.findViewById(R.id.tv_shishids);
    mTvChaobiaoyccm = view.findViewById(R.id.tv_chaobiaoyccm);
    mTvChaobiaoycsl = view.findViewById(R.id.tv_chaobiaoycsl);

    mEtXjyl = view.findViewById(R.id.et_xjyl);
    mEtYlsm = view.findViewById(R.id.et_ylsm);
    mEtBz = view.findViewById(R.id.et_bz);
    tvYsxz = view.findViewById(R.id.tv_ysxz);
    mTvBiaoweizb = view.findViewById(R.id.tv_bwzb);
//        mTvGis = view.findViewById(R.id.tv_gis);
    picturePreview = view.findViewById(R.id.picture_preview);
    tuidanInfo = view.findViewById(R.id.tuidan_info);
    picturePreview.setOnClickListener(this);
    tuidanInfo.setOnClickListener(this);
    mTvCbzt.setOnClickListener(this);

    mTvIsGongdanDJ = view.findViewById(R.id.tv_sfgddj);
    mEtGongDanDJBZ = view.findViewById(R.id.et_gddjbz);
    mTvMkgzYY = view.findViewById(R.id.tv_mkggyy);
    mTvGzhbYY = view.findViewById(R.id.tv_gzhbyy);
    mTvIsGenghuanBP = view.findViewById(R.id.tv_sfghbp);
    mTvIsGongdanDJ.setOnClickListener(this);
    mTvMkgzYY.setOnClickListener(this);
    mTvGzhbYY.setOnClickListener(this);
    mTvIsGenghuanBP.setOnClickListener(this);
    mTvChaobiaoyccm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getYuCeData(true);
      }
    });
    mTvChaobiaoycsl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getYuCeData(true);
      }
    });
//    mTvShiShiDS.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        getDuShu(true);
//      }
//    });

    if (getActivity() instanceof InspectionInputActivity) {
      InspectionInputActivity activity2 = (InspectionInputActivity) getActivity();
      if (activity2.isHistory) {
        mEtTxm.setEnabled(false);
        mEtXunJianChaoma.setEnabled(false);
        mTvPaiZhaoShiBie.setEnabled(false);
        mEtYlsm.setEnabled(false);
        mEtBz.setEnabled(false);
        mTvCbzt.setEnabled(false);
        mEtXjyl.setEnabled(false);
        mTvIsGongdanDJ.setEnabled(false);
        mTvMkgzYY.setEnabled(false);
        mTvGzhbYY.setEnabled(false);
        mTvIsGenghuanBP.setEnabled(false);
        mEtGongDanDJBZ.setEnabled(false);
      }
    }
  }

  private void getDuShu(boolean isShowDialog) {
    IProgressDialog iProgressDialog = new IProgressDialog() {
      @Override
      public Dialog getDialog() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在获取实时读数...");
        return progressDialog;
      }
    };
    if (mBiaoKaBean.getISNB().equals("1")) {//NB表获取实时读数
      EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getNBDuShu)
        .params("TXM", mBiaoKaBean.getBIAOHAO())
//                    .params("TXM", "72811711012247")
        .execute(new ProgressDialogCallBack<String>(iProgressDialog, isShowDialog, true) {
          @Override
          public void onError(ApiException e) {
            super.onError(e);
            ToastUtils.showLong(e.getMessage());
          }

          @Override
          public void onSuccess(String s) {
            DuShuEntity2 duShuEntity2 = GsonUtils.getGson().fromJson(s, DuShuEntity2.class);
            if ("1".equals(duShuEntity2.getResult())) {
              SpanUtils.with(mTvShiShiDS)
                .append(duShuEntity2.getBENCICM() + " | " +
                  duShuEntity2.getCHAOBIAOSJ())
                .setForegroundColor(ContextCompat.getColor(getContext()
                  == null ? ActivityUtils.getTopActivity()
                  : getContext(), R.color.colorPrimary)).create();
            } else {
              ToastUtils.showLong(duShuEntity2.getErr());
            }
          }
        });
    } else {
      EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getDuShu)
        .params("XIAOGENH", mBiaoKaBean.getXIAOGENH())
        .params("DANGQIANSJ", TimeUtils.getNowString())
        .execute(new ProgressDialogCallBack<String>(iProgressDialog, isShowDialog, true) {
          @Override
          public void onError(ApiException e) {
            super.onError(e);
            ToastUtils.showLong(e.getMessage());
          }

          @Override
          public void onSuccess(String s) {
            DuShuEntity duShuEntity = GsonUtils.fromJson(s, DuShuEntity.class);
            if ("00".equals(duShuEntity.getMsgCode())) {
              List<DuShuEntity.DataBean> data = duShuEntity.getData();
              if (data != null && data.size() > 0) {
                SpanUtils.with(mTvShiShiDS)
                  .append(duShuEntity.getData().get(0).getDUSHU() + " | " +
                    duShuEntity.getData().get(0).getDUSHURQ())
                  .setForegroundColor(ContextCompat.getColor(getContext()
                    == null ? ActivityUtils.getTopActivity()
                    : getContext(), R.color.colorPrimary)).create();
              }
            } else {
              ToastUtils.showLong(duShuEntity.getMsgInfo());
            }
          }
        });
    }
  }


  @SuppressLint("WrongConstant")
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_paizhao:
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent,101);
        break;
      case R.id.picture_preview:
        getBWPictures();
        break;
      case R.id.tuidan_info:
//        String tdyy = mBiaoKaBean.getTDYY();
//        List<XJXXWordBean> wordBeans = GreenDaoUtils.getInstance().getDaoSession(BaseApplication.getInstance())
//          .getXJXXWordBeanDao().queryBuilder()
//          .where(XJXXWordBeanDao.Properties.MMODULE.eq("抄表巡检退单原因"))
//          .where(XJXXWordBeanDao.Properties.MVALUE.eq(tdyy))
//          .list();
//        if (wordBeans != null && wordBeans.size() > 0) {
//          tdyy = wordBeans.get(0).getMNAME();
//        }
//        String[] items = {"    退单原因：" + tdyy, "    退单时间：" + mBiaoKaBean.getTDSJ()
//          , "    退单人：" + mBiaoKaBean.getTDR(), "    退单备注：" + mBiaoKaBean.getTDBZ()};
//        new androidx.appcompat.app.AlertDialog.Builder(ActivityUtils.getTopActivity())
//          .setItems(items, null)
//          .create()
//          .show();
        break;
      case R.id.tv_cbzt:
        List<String> list1 = new ArrayList<>();
        List<List<String>> list2 = new ArrayList<>();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        List<XJXXWordBean> cbztWords =mLrOperatingPresenter.getHotlineWordData(Const.XUNJIAN_WORDS_CBZT);
        if (cbztWords.size() == 0 || cbztWords == null){
          ToastUtils.showShort("未获取到词语");
          return;
        }
        for (int i = 0; i < cbztWords.size(); i++) {
          String mvalue = cbztWords.get(i).getMVALUE();
          String mname = cbztWords.get(i).getMNAME();
          map.put(mname, mvalue);
          if (!list1.contains(mvalue)) {
            list1.add(mvalue);
          }
        }
        for (int i = 0; i < list1.size(); i++) {
          String cbzt = list1.get(i);
          List<String> stringList = new ArrayList<>();
          for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(cbzt)) {
              stringList.add(entry.getKey());
            }
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
          }
          list2.add(stringList);
        }
        initOptionPicker(list1, list2);
        break;
      case R.id.tv_sfgddj:
        if (mGongdanDjWords.size() == 0) {
          ToastUtils.showShort("未获取到词语");
          return;
        }
        final List<String> gddjListStr = new ArrayList<>();
        for (int i = 0; i < mGongdanDjWords.size(); i++) {
          gddjListStr.add(mGongdanDjWords.get(i).getMNAME());
        }
        PickerViewUtils.getInstance().showCustomDialog(mGongdanDjWords.get(0).getMMODULE(), gddjListStr, new OnOptionsSelectListener() {
          @Override
          public void onOptionsSelect(int options1, int options2, int options3, View v) {
            isGongDanDJValue = mGongdanDjWords.get(options1).getMVALUE();
            mTvIsGongdanDJ.setText(gddjListStr.get(options1));
            switch (isGongDanDJValue) {
              case "1":
                mEtGongDanDJBZ.setVisibility(View.VISIBLE);
                mTvGzhbYY.setVisibility(View.VISIBLE);
                mTvMkgzYY.setVisibility(View.GONE);
                mTvIsGenghuanBP.setVisibility(View.GONE);
                break;
              case "2":
                mEtGongDanDJBZ.setVisibility(View.VISIBLE);
                mTvGzhbYY.setVisibility(View.GONE);
                mTvMkgzYY.setVisibility(View.VISIBLE);
                mTvIsGenghuanBP.setVisibility(View.GONE);
                break;
              case "0":
              default:
                mEtGongDanDJBZ.setVisibility(View.GONE);
                mTvGzhbYY.setVisibility(View.GONE);
                mTvMkgzYY.setVisibility(View.GONE);
                mTvIsGenghuanBP.setVisibility(View.GONE);
                break;
            }
          }
        });
        break;
      case R.id.tv_sfghbp:
//        if (mSfghbpWords.size() == 0) {
//          ToastUtils.showShort("未获取到词语");
//          return;
//        }
//        List<String> sfghbpListStr = new ArrayList<>();
//        for (int i = 0; i < mSfghbpWords.size(); i++) {
//          sfghbpListStr.add(mSfghbpWords.get(i).getMNAME());
//        }
//        PickerViewUtils.getInstance().showCustomDialog(mSfghbpWords.get(0).getMMODULE(), sfghbpListStr, new OnOptionsSelectListener() {
//          @Override
//          public void onOptionsSelect(int options1, int options2, int options3, View v) {
//            isGenghuanBPValue = mSfghbpWords.get(options1).getMVALUE();
//            mTvIsGenghuanBP.setText(sfghbpListStr.get(options1));
//          }
//        });
        break;
      case R.id.tv_mkggyy:
//        if (mMkgzyyWords.size() == 0) {
//          ToastUtils.showShort("未获取到词语");
//          return;
//        }
//        List<String> mkgzyyListStr = new ArrayList<>();
//        for (int i = 0; i < mMkgzyyWords.size(); i++) {
//          mkgzyyListStr.add(mMkgzyyWords.get(i).getMNAME());
//        }
//        PickerViewUtils.getInstance().showCustomDialog(mMkgzyyWords.get(0).getMMODULE(), mkgzyyListStr, new OnOptionsSelectListener() {
//          @Override
//          public void onOptionsSelect(int options1, int options2, int options3, View v) {
//            mokuaigzyyValue = mMkgzyyWords.get(options1).getMVALUE();
//            mTvMkgzYY.setText(mkgzyyListStr.get(options1));
//            if (mokuaigzyyValue.equals("5") || mokuaigzyyValue.equals("6")) {
//              mTvIsGenghuanBP.setVisibility(View.VISIBLE);
//            } else {
//              mTvIsGenghuanBP.setVisibility(View.GONE);
//            }
//          }
//        });
        break;
      case R.id.tv_gzhbyy:
//        if (mGzhbyyWords.size() == 0) {
//          ToastUtils.showShort("未获取到词语");
//          return;
//        }
//        List<String> gzhbyyListStr = new ArrayList<>();
//        for (int i = 0; i < mGzhbyyWords.size(); i++) {
//          gzhbyyListStr.add(mGzhbyyWords.get(i).getMNAME());
//        }
//        PickerViewUtils.getInstance().showCustomDialog(mGzhbyyWords.get(0).getMMODULE(), gzhbyyListStr, new OnOptionsSelectListener() {
//          @Override
//          public void onOptionsSelect(int options1, int options2, int options3, View v) {
//            guzhnaghbyyValue = mGzhbyyWords.get(options1).getMVALUE();
//            mTvGzhbYY.setText(gzhbyyListStr.get(options1));
//          }
//        });
        break;
      default:
        break;
    }
  }


  @Override
  protected void lazyLoad() {

  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void afterTextChanged(Editable editable) {

  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onloadBiaokaListData(List<BiaoKaListBean> biaoKaBeans) {
    if (biaoKaBeans != null){
      mBiaoKaListBean=biaoKaBeans.get(0);


      mTvXiaogenhao.setText(mBiaoKaBean.getXIAOGENH());
      mTvNB.setText(mBiaoKaBean.getISNB().equals("1") ? "是" : "否");
      mTvHuming.setText(mBiaoKaBean.getHUMING());
//      SpanUtils.with(mTvShiShiDS)
//        .append("刷新实时读数")
//        .setUnderline()
//        .setForegroundColor(ContextCompat.getColor(getContext()
//          == null ? ActivityUtils.getTopActivity()
//          : getContext(), R.color.colorPrimary)).create();
      SpanUtils.with(mTvChaobiaoyccm)
        .append("获取预测数据")
        .setUnderline()
        .setForegroundColor(ContextCompat.getColor(getContext()
          == null ? ActivityUtils.getTopActivity()
          : getContext(), R.color.colorPrimary)).create();
      SpanUtils.with(mTvChaobiaoycsl)
        .setUnderline()
        .setForegroundColor(ContextCompat.getColor(getContext()
          == null ? ActivityUtils.getTopActivity()
          : getContext(), R.color.colorPrimary)).create();
//        mTvXiaogenhao.setText("2382877828");
//        mTvNB.setText("是");
//        mTvHuming.setText("张三");
      SpanUtils.with(mTvDizhi)
        .append(mBiaoKaBean.getDIZHI())
        .setUnderline()
        .setForegroundColor(ContextCompat.getColor(getContext()
          == null ? ActivityUtils.getTopActivity()
          : getContext(), R.color.colorPrimary)).create();
      if (!StringUtils.isEmpty(mBiaoKaBean.getS_X()) && !StringUtils.isEmpty(mBiaoKaBean.getS_Y())) {
        SpanUtils.with(mTvBiaoweizb)
          .append("最近一次抄准位置（" + mBiaoKaBean.getS_X() + "，" + mBiaoKaBean.getS_Y() + "）")
          .setUnderline()
          .setForegroundColor(ContextCompat.getColor(getContext()
            == null ? ActivityUtils.getTopActivity()
            : getContext(), R.color.colorPrimary)).create();
      }
      mTvBiaohao.setText(mBiaoKaBean.getBIAOHAO());
      mTvChaobiaoYL.setText(String.valueOf(mBiaoKaBean.getCHAOBIAOYL()));
      mTvShangcicm.setText(String.valueOf(mBiaoKaBean.getSHANGCICM()));
      mTvPingjun.setText(String.valueOf(mBiaoKaBean.getQIANSANCIPJ()));
      mTvChaobiaoRQ.setText(mBiaoKaBean.getCHAOBIAORQ());
    }
  }

  @Override
  public void onloadBiaokaList(List<BiaoKaBean> biaoKaBeans) {
    if (biaoKaBeans != null){
      mBiaoKaBean=biaoKaBeans.get(0);
      mLrOperatingPresenter.loadBiaokaList(xiaogenghao);
    }

  }

  @Override
  public void onloadWordData(List<XJXXWordBean> xjxxWordBeans, String type) {
    if (xjxxWordBeans == null){
      return;
    }

    switch (type){
      case "巡检登记工单":
        mGongdanDjWords =xjxxWordBeans;
        if ("F".equals(mBiaoKaBean.getISYC())) {// 不是远传表，不能选登记故障模块工单（2）
          List<XJXXWordBean> ycdjWordLists = new ArrayList<>();
          for (int i = 0; i < mGongdanDjWords.size(); i++) {
            if ("2".equals(mGongdanDjWords.get(i).getMVALUE())) {
              continue;
            }
            ycdjWordLists.add(mGongdanDjWords.get(i));
          }
          mGongdanDjWords = ycdjWordLists;
        }
        break;
      case "模块故障原因":
        mMkgzyyWords=xjxxWordBeans;
        break;
      case "故障换表原因":
        mGzhbyyWords=xjxxWordBeans;
        break;
      case "是否更换表盘":
        mSfghbpWords=xjxxWordBeans;
        break;
      case "抄表状态":
        cbztWords=xjxxWordBeans;
        break;
    }

  }

  /**
   * 初始化实时读数
   */
  private void getYuCeData(final boolean isShowDialog) {
    final IProgressDialog iProgressDialog = new IProgressDialog() {
      @Override
      public Dialog getDialog() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在获取预测数据...");
        return progressDialog;
      }
    };
    EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.GETCHAOBIAOHISTORYCHAOJIAN)
      .cacheKey(SPUtils.getInstance().getString(Const.S_YUANGONGH) + mBiaoKaBean.getXIAOGENH() + URL.GETCHAOBIAOHISTORYCHAOJIAN)
      .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
      .params("XIAOGENH", mBiaoKaBean.getXIAOGENH())
      .execute(new ProgressDialogCallBack<String>(iProgressDialog, true, true) {
        @Override
        public void onStart() {
          super.onStart();
        }

        @Override
        public void onError(ApiException e) {
          super.onError(e);
          ToastUtils.showLong(e.getMessage());
        }

        @Override
        public void onSuccess(String s) {
          ChaoBiaoHistoryBean chaoBiaoHistoryBean = new Gson().fromJson(s, ChaoBiaoHistoryBean.class);
          if ("00".equals(chaoBiaoHistoryBean.getMsgCode())) {
            List<ChaoBiaoHistoryBean.DataBean> data = chaoBiaoHistoryBean.getData();
            List<ChaoBiaoHistoryBean.DataBean> dataBeans = null;
            if (data.size() > 3) {
              dataBeans = data.subList(0, 3);
            } else {
              dataBeans = data;
            }
            AddYuanChuanYCCMAndYCSL(dataBeans,isShowDialog,iProgressDialog);
          } else {
//            AddYuanChuanYCCMAndYCSL(null,isShowDialog,iProgressDialog);
            ToastUtils.showShort(chaoBiaoHistoryBean.getMsgInfo());
          }
        }
      });


  }

  private void AddYuanChuanYCCMAndYCSL(List<ChaoBiaoHistoryBean.DataBean> dataBeans,boolean isShowDialog,IProgressDialog iProgressDialog){
    EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.AddYuanChuanYCCMAndYCSL)
//                    .params("yuanchuancm", "505")
//                    .params("xunjiandate", "2013/5/25")
//                    .params("chaobiaoid", "74779611")
      .params("yuanchuancm", String.valueOf(dataBeans.get(0).getCHAOMA()))
      .params("xunjiandate",dataBeans.get(0).getCHAOBIAONY())
      .params("chaobiaoid", String.valueOf(dataBeans.get(0).getCHAOBIAOID()))
      .execute(new ProgressDialogCallBack<String>(iProgressDialog, isShowDialog, true) {
        @Override
        public void onError(ApiException e) {
          super.onError(e);
          ToastUtils.showLong(e.getMessage());
        }

        @Override
        public void onSuccess(String s) {
          YuCeDataEntity yuCeDataEntity = GsonUtils.getGson().fromJson(s, YuCeDataEntity.class);
          if (!"false".equals(yuCeDataEntity.getMsgCode())) {
            SpanUtils.with(mTvChaobiaoyccm)
              .append(yuCeDataEntity.getMsgCode())
              .setForegroundColor(ContextCompat.getColor(getContext()
                == null ? ActivityUtils.getTopActivity()
                : getContext(), R.color.colorPrimary)).create();
            SpanUtils.with(mTvChaobiaoycsl)
              .append(yuCeDataEntity.getMsgInfo())
              .setForegroundColor(ContextCompat.getColor(getContext()
                == null ? ActivityUtils.getTopActivity()
                : getContext(), R.color.colorPrimary)).create();
          } else {
            ToastUtils.showLong(yuCeDataEntity.getMsgInfo());
          }
        }
      });
  }
  private void getBWPictures() {
    final ArrayList<ImageItem> imageItems = new ArrayList<>();
    IProgressDialog iProgressDialog = new IProgressDialog() {
      @Override
      public Dialog getDialog() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在获取表位照片...");
        return progressDialog;
      }
    };
    EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getBWImages)
//                .params("XIAOGENH", "981027542")
      .params("XIAOGENH", mBiaoKaBean.getXIAOGENH())
      .execute(new ProgressDialogCallBack<String>(iProgressDialog, true, false) {
        @Override
        public void onError(ApiException e) {
          super.onError(e);
          ToastUtils.showLong(e.getMessage());
        }

        @Override
        public void onSuccess(String s) {
          BiaoWeiImage biaoWeiImage = GsonUtils.fromJson(s, BiaoWeiImage.class);
          if ("00".equals(biaoWeiImage.getMsgCode())) {
            List<BiaoWeiImage.DataBean> data = biaoWeiImage.getData();
            for (int i = 0; i < data.size(); i++) {
              ImageItem item = new ImageItem();
              item.path = data.get(i).getURL();
              imageItems.add(item);
            }
//            Intent intent = new Intent(ActivityUtils.getTopActivity(), MyImagePreviewActivity.class);
//            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
//            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//            intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//            ActivityUtils.getTopActivity().startActivity(intent);
          } else {
            ToastUtils.showLong(biaoWeiImage.getMsgInfo());
          }
        }
      });
  }
  private void initOptionPicker(List<String> list1, final List<List<String>> list2) {//条件选择器初始化

    /**
     * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
     */

    OptionsPickerView<String> pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(int options1, int options2, int options3, View v) {
        mTvCbzt.setText(list2.get(options1).get(options2));
        //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(options2)
//                        /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
//                btn_Options.setText(tx);
      }
    })
            .setTitleText("请选择抄表状态")
            .setContentTextSize(20)//设置滚轮文字大小
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
            .setSelectOptions(0, 0)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
            .setCancelColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))
            .setSubmitColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))
//                .setTextColorCenter(Color.LTGRAY)
            .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setLabels("", "", "")
            .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setOutSideColor(0x55000000) //设置外部遮罩颜色
            .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
              @Override
              public void onOptionsSelectChanged(int options1, int options2, int options3) {
                String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//                        mTvCbzt.setText(list2.get(options1).get(options2));
//                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
              }
            })
            .build();

//        pvOptions.setSelectOptions(1,1);
    /*pvOptions.setPicker(options1Items);//一级选择器*/
    pvOptions.setPicker(list1, list2);//二级选择器
    pvOptions.show();
    /*pvOptions.setPicker(options1Item

     */
  }
  public boolean isCanSave(boolean isToast) {
    if (mTvIsGongdanDJ.getVisibility() == View.VISIBLE && StringUtils.isTrimEmpty(mTvIsGongdanDJ.getText().toString())) {
      ToastUtils.showLong("请选择是否登记工单");
      return false;
    }
    if (mTvGzhbYY.getVisibility() == View.VISIBLE && StringUtils.isTrimEmpty(mTvGzhbYY.getText().toString())) {
      ToastUtils.showLong("请选择故障换表原因");
      return false;
    }
    if (mTvMkgzYY.getVisibility() == View.VISIBLE && StringUtils.isTrimEmpty(mTvMkgzYY.getText().toString())) {
      ToastUtils.showLong("请选择模块故障原因");
      return false;
    }
    if (mTvIsGenghuanBP.getVisibility() == View.VISIBLE && StringUtils.isTrimEmpty(mTvIsGenghuanBP.getText().toString())) {
      ToastUtils.showLong("请选择是否更换表盘");
      return false;
    }
    if ("2".equals(mBiaoKaBean.getXJLX())) {
      if ("".equals(mEtBz.getText().toString().trim())) {
        if (isToast) {
          ToastUtils.showLong("请填写备注");
        }
        return false;
      }
    } else {
      if (StringUtils.isEmpty(mTvCbzt.getText().toString()) && mTvCbzt.getVisibility() == View.VISIBLE) {
        if (isToast) {
          ToastUtils.showLong("请选择抄表状态");
        }
        return false;
      }
      if ("".equals(mEtXunJianChaoma.getText().toString().trim())) {
        if (isToast) {
          ToastUtils.showLong("请填写巡检抄码");
        }
        return false;
      }
      if ("".equals(mEtTxm.getText().toString().trim())) {
        if (isToast) {
          ToastUtils.showLong("请填写条形码");
        }
        return false;
      }
//        if ("".equals(mEtXjyl.getText().toString().trim())) {
//            if (isToast) {
//                ToastUtils.showLong("请填写巡检用量");
//            }
//            return false;
//        }
      try {
        if (StringUtils.isTrimEmpty(mEtYlsm.getText().toString().trim()) && Integer.parseInt(mEtXjyl.getText().toString().trim()) == 0) {
          if (isToast) {
            ToastUtils.showLong("请填写0用量说明");
          }
          return false;
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public BiaoKaWholeEntity getLuRuData(BiaoKaWholeEntity biaoKaWholeEntity) {
    biaoKaWholeEntity.setID((long) (renWuId + mBiaoKaBean.getXIAOGENH()).hashCode());
    biaoKaWholeEntity.setRENWUID(renWuId);
    biaoKaWholeEntity.setXIAOGENH(mBiaoKaBean.getXIAOGENH());
    biaoKaWholeEntity.setXUNJIANCM(mEtXunJianChaoma.getText().toString().trim());
    biaoKaWholeEntity.setTXM(mEtTxm.getText().toString().trim());
    biaoKaWholeEntity.setXUNJIANYL(mEtXjyl.getText().toString().trim());
    biaoKaWholeEntity.setYLSM(mEtYlsm.getText().toString().trim());
    biaoKaWholeEntity.setBEIZHU(mEtBz.getText().toString().trim());
    biaoKaWholeEntity.setCbzt(mTvCbzt.getText().toString());
    biaoKaWholeEntity.setDetailInfo(GsonUtils.toJson(mBiaoKaBean));

    biaoKaWholeEntity.setGONGDANDJ(isGongDanDJValue);
    biaoKaWholeEntity.setGONGDANDJBZ(mEtGongDanDJBZ.getVisibility() == View.VISIBLE ? mEtGongDanDJBZ.getText().toString() : "");
    biaoKaWholeEntity.setBIAOWU_GZYY(mTvGzhbYY.getVisibility() == View.VISIBLE ? guzhnaghbyyValue : "");
    biaoKaWholeEntity.setMOKUAI_GZYY(mTvMkgzYY.getVisibility() == View.VISIBLE ? mokuaigzyyValue : "");
    biaoKaWholeEntity.setSFGHBP(mTvIsGenghuanBP.getVisibility() == View.VISIBLE ? isGenghuanBPValue : "");
//        biaoKaWholeEntity.setImages6(GsonUtils.toJson(imageItems));
    return biaoKaWholeEntity;
  }

  public boolean isNeedSave() {
    if ("2".equals(mBiaoKaBean.getXJLX())) {
      if ("".equals(mEtBz.getText().toString().trim())) {
        return false;
      }
    } else {
      if ("".equals(mEtXunJianChaoma.getText().toString().trim()) && "".equals(mEtTxm.getText().toString().trim()) &&
              "".equals(mEtXjyl.getText().toString().trim()) && "".equals(mEtYlsm.getText().toString().trim())
              && "".equals(mEtBz.getText().toString().trim())
              && (mTvGzhbYY.getVisibility() != View.VISIBLE)
              && (mTvMkgzYY.getVisibility() != View.VISIBLE)
              && (mTvIsGenghuanBP.getVisibility() != View.VISIBLE)
              && (mEtGongDanDJBZ.getVisibility() != View.VISIBLE)) {
        return false;
      }
    }
    return true;
  }

  public void processResult(String code) {
    if (code != null){
      mEtTxm.setText(code);
    }
  }


}





