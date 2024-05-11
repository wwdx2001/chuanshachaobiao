package com.sh3h.meterreading.ui.usage_change;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.FullyLinearLayoutManager;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.usage_change.adapter.UsageChangeBasicAdapter;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.view.TextViewLine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsageChangeBasicInformationActivity extends ParentActivity implements View.OnClickListener {

    
    @BindView(R.id.loading_process)
    SmoothProgressBar loadingProcess;
    @BindView(R.id.txt_huming)
    TextViewLine mHuMing;
    @BindView(R.id.txt_customer_code) TextViewLine mCustomerCode;
    @BindView(R.id.txt_address) TextViewLine mAddress;
    @BindView(R.id.txt_host_tel) TextViewLine mHostTel;
    @BindView(R.id.txt_customer_tel) TextViewLine mCustomerTel;
    @BindView(R.id.txt_pay_cost) TextViewLine mPayCost;
    @BindView(R.id.txt_card_status)TextViewLine mCardStatus;
    @BindView(R.id.txt_xingzhengqu) TextViewLine mXingZhengQu;
    @BindView(R.id.txt_zhandian) TextViewLine mZhanDian;
    @BindView(R.id.txt_ch) TextViewLine mCH;
    @BindView(R.id.txt_cenei_xh) TextViewLine mCeNeiXH;
    @BindView(R.id.txt_ercigongshui) TextViewLine mErCiGongShui;
    @BindView(R.id.txt_dianzizhangdan) TextViewLine mDianZiZhangDan;
    @BindView(R.id.txt_fangan) TextViewLine mFangAn;
    @BindView(R.id.txt_shifoujieti) TextViewLine mShiFouJieTi;
    @BindView(R.id.txt_daoqiriqi) TextViewLine mDaoQiRiQi;
    @BindView(R.id.txt_biaohao) TextViewLine mBiaoHao;
    @BindView(R.id.txt_kaizhangfenlei) TextViewLine mKaiZhangFenLei;
    @BindView(R.id.txt_biaoxin) TextViewLine mBiaoXing;
    @BindView(R.id.txt_shuibiaozonglei) TextViewLine mShuiBiaoZongLei;
    @BindView(R.id.txt_yongtufenlei) TextViewLine mYongTuFenLei;
    @BindView(R.id.txt_biaoxishu) TextViewLine mBiaoXiShu;
    @BindView(R.id.txt_shebeibianhao) TextViewLine mSheBeiBianHao;
    @BindView(R.id.txt_zhongduanhao) TextViewLine mZhongDuanHao;
    @BindView(R.id.txt_yuanchuanchangjia) TextViewLine mYuanChuanChangJia;
    @BindView(R.id.txt_shuibiaochangjia) TextViewLine mShuiBiaoChangJia;
    @BindView(R.id.txt_koujing) TextViewLine mKouJing;
    @BindView(R.id.txt_liangcheng) TextViewLine mLiangCheng;
    @BindView(R.id.txt_fentanfangshi) TextViewLine mFenTanFangShi;
    @BindView(R.id.txt_bili) TextViewLine mBiLi;
    @BindView(R.id.txt_lihuriqi) TextViewLine mLiHuRiQi;
    @BindView(R.id.txt_zhuangbiaorq) TextViewLine mZhuangBiaoRiQi;
    @BindView(R.id.txt_jiubiaodima) TextViewLine mJiuBiaoDiMa;
    @BindView(R.id.txt_xinbiaoqm) TextViewLine mXinBiaoQM;
    @BindView(R.id.txt_gongshuinianxian) TextViewLine mGongSHuiNianXian;
    @BindView(R.id.txt_jianhao) TextViewLine mJianHao;
    @BindView(R.id.txt_sheshuiduixiang) TextViewLine mSheShuiDuiXiang;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.user_apply_btn)
    TextView mUserApplyBtn;
    @BindView(R.id.usage_change_apply_btn)
    TextView mUsageChangeApplyBtn;

    
    private UsageChangeBasicAdapter mAdapter;
    private DUCard mDUCard;
    private List<JianHaoMX> mJianHaoMXList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_usage_change_basic_information;
    }

    @Override
    protected void initView1() {
        super.initView1();
        ButterKnife.bind(this);
    }


    @Override
    protected void initData1() {
        super.initData1();

        Intent intent = getIntent();
        mDUCard = (DUCard) intent.getSerializableExtra(Const.BEAN);
        mJianHaoMXList = (List<JianHaoMX>) intent.getSerializableExtra(Const.JIANHAOMX);

        if (mDUCard == null){
            return;
        }

        mAdapter = new UsageChangeBasicAdapter(mJianHaoMXList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        loadingProcess.setVisibility(View.INVISIBLE);

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

    }

    @Override
    protected void initListener1() {
        super.initListener1();
        mUserApplyBtn.setOnClickListener(this);
        mUsageChangeApplyBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_apply_btn:
                Intent intent = new Intent(this, RealNameDetailActivity.class);
                intent.putExtra(Const.S_CID, mDUCard.getCid());
                startActivity(intent);
                break;

            case R.id.usage_change_apply_btn:
                Intent intent1 = new Intent(this, UsageChangeUploadActivity.class);
                intent1.putExtra(Const.S_CID, mDUCard.getCid());
                startActivity(intent1);
                break;
        }
    }
}
