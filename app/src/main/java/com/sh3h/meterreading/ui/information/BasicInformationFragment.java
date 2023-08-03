package com.sh3h.meterreading.ui.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.dataprovider.schema.ChaoBiaoSJColumns;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.BasicAdapter;
import com.sh3h.meterreading.adapter.FullyLinearLayoutManager;
import com.sh3h.meterreading.adapter.TaskListAdapter;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.view.TextViewLine;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xulongjun on 2016/2/16.
 * 基本信息
 */
public class BasicInformationFragment extends ParentFragment implements BasicInformationMvpView, View.OnClickListener {
    public static final String TAG = "BasicInformationFragment";

    @Inject
    Bus mEventBus;

    @Bind(R.id.loading_process)
    SmoothProgressBar loadingProcess;

    @Bind(R.id.txt_huming) TextViewLine mHuMing;
    @Bind(R.id.txt_customer_code) TextViewLine mCustomerCode;
    @Bind(R.id.txt_address) TextViewLine mAddress;
    @Bind(R.id.txt_host_tel) TextViewLine mHostTel;
    @Bind(R.id.txt_customer_tel) TextViewLine mCustomerTel;
    @Bind(R.id.txt_pay_cost) TextViewLine mPayCost;
    @Bind(R.id.txt_card_status)TextViewLine mCardStatus;
    @Bind(R.id.txt_xingzhengqu) TextViewLine mXingZhengQu;
    @Bind(R.id.txt_zhandian) TextViewLine mZhanDian;
    @Bind(R.id.txt_ch) TextViewLine mCH;
    @Bind(R.id.txt_cenei_xh) TextViewLine mCeNeiXH;
    @Bind(R.id.txt_ercigongshui) TextViewLine mErCiGongShui;
    @Bind(R.id.txt_dianzizhangdan) TextViewLine mDianZiZhangDan;
    @Bind(R.id.txt_fangan) TextViewLine mFangAn;
    @Bind(R.id.txt_shifoujieti) TextViewLine mShiFouJieTi;
    @Bind(R.id.txt_daoqiriqi) TextViewLine mDaoQiRiQi;
    @Bind(R.id.txt_biaohao) TextViewLine mBiaoHao;
    @Bind(R.id.txt_kaizhangfenlei) TextViewLine mKaiZhangFenLei;
    @Bind(R.id.txt_biaoxin) TextViewLine mBiaoXing;
    @Bind(R.id.txt_shuibiaozonglei) TextViewLine mShuiBiaoZongLei;
    @Bind(R.id.txt_yongtufenlei) TextViewLine mYongTuFenLei;
    @Bind(R.id.txt_biaoxishu) TextViewLine mBiaoXiShu;
    @Bind(R.id.txt_shebeibianhao) TextViewLine mSheBeiBianHao;
    @Bind(R.id.txt_zhongduanhao) TextViewLine mZhongDuanHao;
    @Bind(R.id.txt_yuanchuanchangjia) TextViewLine mYuanChuanChangJia;
    @Bind(R.id.txt_shuibiaochangjia) TextViewLine mShuiBiaoChangJia;
    @Bind(R.id.txt_koujing) TextViewLine mKouJing;
    @Bind(R.id.txt_liangcheng) TextViewLine mLiangCheng;
    @Bind(R.id.txt_fentanfangshi) TextViewLine mFenTanFangShi;
    @Bind(R.id.txt_bili) TextViewLine mBiLi;
    @Bind(R.id.txt_lihuriqi) TextViewLine mLiHuRiQi;
    @Bind(R.id.txt_zhuangbiaorq) TextViewLine mZhuangBiaoRiQi;
    @Bind(R.id.txt_jiubiaodima) TextViewLine mJiuBiaoDiMa;
    @Bind(R.id.txt_xinbiaoqm) TextViewLine mXinBiaoQM;
    @Bind(R.id.txt_gongshuinianxian) TextViewLine mGongSHuiNianXian;
    @Bind(R.id.txt_jianhao) TextViewLine mJianHao;
    @Bind(R.id.txt_sheshuiduixiang) TextViewLine mSheShuiDuiXiang;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Inject BasicInformationPresenter mBasicInformationPresenter;

    private DUCard mDUCard;
    private List<JianHaoMX> mJianHaoMXList;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        LogUtil.i(TAG, "---BasicInformationFragment onCreate---");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jibenxx, container, false);
        LogUtil.i("BasicInformationFragment", "onCreateView");

        ButterKnife.bind(this, view);
        mBasicInformationPresenter.attachView(this);
        init();
        mEventBus.register(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mBasicInformationPresenter.detachView();
    }

    @Subscribe
    public void onCustomerInformationUpdating(UIBusEvent.CustomerInformationUpdating customerInformationUpdating) {
        LogUtil.i(TAG, "---onCustomerInformationUpdating---");
        init();
    }

    private void init() {
        Bundle _bundle = getArguments();
        if (_bundle == null
                || TextUtil.isNullOrEmpty(_bundle.getString(ChaoBiaoSJColumns.CID))) {
            return;
        }

        mDUCard = (DUCard) _bundle.getSerializable(CustomerInformationActivity.DUCARD);
        if (mDUCard != null) {
            mBasicInformationPresenter.getianHaoMX(mDUCard.getJianhao());
        } else {
            DUCardInfo duCardInfo = new DUCardInfo();
            duCardInfo.setCustomerId(_bundle.getString(ChaoBiaoSJColumns.CID));
            duCardInfo.setFilterType(DUCardInfo.FilterType.SEARCHING_ONE);
            mBasicInformationPresenter.getOneCard(duCardInfo);
        }
    }

    @Override
    public void onGetCardFinish(DUCard duCard) {
        if (duCard == null) {
            return;
        }

        this.mDUCard = duCard;
        mBasicInformationPresenter.getianHaoMX(duCard.getJianhao());
    }

    @Override
    public void onGetJianHaoMXFinish(List<JianHaoMX> list) {
        this.mJianHaoMXList = list;
        initView();
    }

    private void initView() {
        loadingProcess.setVisibility(View.INVISIBLE);

        if (mDUCard == null){
            return;
        }

        mCustomerCode.setText(mDUCard.getCid());
        mHuMing.setText(mDUCard.getKehumc());
        mAddress.setText(mDUCard.getDizhi());
        mHostTel.setText(mDUCard.getFangdongdh());
        mCustomerTel.setText(mDUCard.getFangkedh());
        mPayCost.setText(mDUCard.getShoufeifs());
        mCardStatus.setText(mDUCard.getBiaokazt());
        mXingZhengQu.setText(mDUCard.getXingzhengq());
        mZhanDian.setText(mDUCard.getSt());
        mCH.setText(mDUCard.getCh());
        mCeNeiXH.setText(String.valueOf(mDUCard.getCeneixh()));
        mErCiGongShui.setText(String.valueOf(mDUCard.getErcigs()));
        mDianZiZhangDan.setText(String.valueOf(mDUCard.getDianzizd()));
        mFangAn.setText(mDUCard.getDuorenkfa());
        mShiFouJieTi.setText(mDUCard.getShifoujt() == 0 ? "否" : "是");
        long time = mDUCard.getDuorenkjz();
        if (time != 0){
            mDaoQiRiQi.setText(TextUtil.format(time, TextUtil.FORMAT_DATE_ISDROP));
        }
        mBiaoHao.setText(mDUCard.getShuibiaogyh());
        mKaiZhangFenLei.setText(mDUCard.getKaizhangfl());
        mBiaoXing.setText(mDUCard.getBiaoxing());
        mShuiBiaoZongLei.setText(mDUCard.getShuibiaozl());
        mYongTuFenLei.setText(mDUCard.getShuibiaoflmc());
        mBiaoXiShu.setText(String.valueOf(mDUCard.getZizhuangbkzxs()));
        mSheBeiBianHao.setText(mDUCard.getYuanchuanid());
        mZhongDuanHao.setText(mDUCard.getZhongduanh());
        mYuanChuanChangJia.setText(mDUCard.getYuanchuancj());
        mShuiBiaoChangJia.setText(mDUCard.getShuibiaocj());
        mKouJing.setText(mDUCard.getKoujingmc());
        mLiangCheng.setText(String.valueOf(mDUCard.getLiangcheng()));
        mFenTanFangShi.setText(String .valueOf(mDUCard.getFentanfs()));
        mBiLi.setText(String.valueOf(mDUCard.getFentanl()));
        time = mDUCard.getLihu();
        if (time != 0){
            mLiHuRiQi.setText(TextUtil.format(time, TextUtil.FORMAT_DATE_ISDROP));
        }
        time = mDUCard.getZhuangbiaorq();
        if (time != 0){
            mZhuangBiaoRiQi.setText(TextUtil.format(time, TextUtil.FORMAT_DATE_ISDROP));
        }
        mJiuBiaoDiMa.setText(String.valueOf(mDUCard.getJiubiaocm()));
        mXinBiaoQM.setText(String.valueOf(mDUCard.getXinbiaodm()));
        mGongSHuiNianXian.setText(String.valueOf(mDUCard.getGongshuihtnx()));
        mJianHao.setText(mDUCard.getJianhao());
        mSheShuiDuiXiang.setText(mDUCard.getSheshuiid());


        BasicAdapter adapter = new BasicAdapter();
        adapter.setList(mJianHaoMXList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        loadingProcess.setVisibility(View.INVISIBLE);
        LogUtil.e(TAG, message);
    }

    @Override
    public void onClick(View v) {

    }

    public void takeMobile(TextView tv) {
        if (tv.getText().toString().trim().equals("")) {
            LogUtil.i(TAG, "---BasicInformationFragment no phone---");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv.getText().toString().trim()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
