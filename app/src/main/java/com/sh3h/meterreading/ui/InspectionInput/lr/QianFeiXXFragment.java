package com.sh3h.meterreading.ui.InspectionInput.lr;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.tools.EmptyViewUtils;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.QianFeiXXBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 欠费列表
 *
 * @author xiaochao.dev@gmail.com
 * @date 2019/12/12 14:57
 */
public class QianFeiXXFragment extends ParentFragment implements QianFeiXXMvpView {

    private RecyclerView mRecyclerView;
    private QianFeiXXAdapter mAdapter;
    private List<QianFeiXXBean.DataBean> mDatas;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mTvAllJine, mTvAllAmount;
    private String xiaogenhao = "";

  @Inject
  QianFeiXXPresenter mQianFeiXXPresenter;
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
    View view = inflater.inflate(R.layout.fragment_qian_fei_xx, container, false);
    mQianFeiXXPresenter.attachView(this);
    ininView(view);
    ininData();
    return view;
  }

    private void ininData() {
        mDatas = new ArrayList<>();
        mAdapter = new QianFeiXXAdapter(R.layout.item_qianfei, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        Bundle arguments = getArguments();
        xiaogenhao = arguments.getString(Const.XIAOGENHAO) == null ? "" : arguments.getString(Const.XIAOGENHAO);
        request();
    }

    private void ininView(View view) {
        mTvAllAmount = view.findViewById(R.id.tv_all_amount);
        mTvAllJine = view.findViewById(R.id.tv_all_jine);
        mRecyclerView = view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRefreshLayout = view.findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
    }

    private void request() {
        EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getQianFeiXX)
                .cacheKey(SPUtils.getInstance().getString(Const.S_YUANGONGH) + xiaogenhao + URL.getQianFeiXX)
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .params("XIAOGENH", xiaogenhao)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onError(ApiException e) {
                        mRefreshLayout.setRefreshing(false);
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        mRefreshLayout.setRefreshing(false);
                        QianFeiXXBean qianFeiXXBean = new Gson().fromJson(s, QianFeiXXBean.class);
                        if ("00".equals(qianFeiXXBean.getMsgCode())) {
                            initCacheData(qianFeiXXBean.getData());
                        } else {
                            mAdapter.setEmptyView(EmptyViewUtils.getSingleIntance().getEmptyView(qianFeiXXBean.getMsgInfo()));
                        }
                    }
                });
    }

    private void initCacheData(List<QianFeiXXBean.DataBean> data) {
        if (data != null) {
            mDatas.clear();
            mDatas.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
        mTvAllAmount.setText("欠费笔数：" + mAdapter.getData().size());
        double allAmount = 0.0;
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            allAmount += mAdapter.getData().get(i).getYSJE();
        }
        DecimalFormat df = new DecimalFormat("#.00");
        mTvAllJine.setText("总欠费金额：" + df.format(allAmount));
    }

  @Override
  protected void lazyLoad() {

  }

  public void initData2(Bundle bundle) {
    xiaogenhao = bundle.getString(Const.XIAOGENHAO) == null ? "" : bundle.getString(Const.XIAOGENHAO);
    request();
  }
}
