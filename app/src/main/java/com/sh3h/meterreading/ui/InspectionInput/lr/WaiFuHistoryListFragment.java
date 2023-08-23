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

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.WaiFuHistoryBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * 用户信息-历史外复
 *
 * @author Administrator
 */
public class WaiFuHistoryListFragment extends ParentFragment implements WaiFuHistoryListPresenterMvpView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private WaiFuHistoryAdapter adapter;
    private List<WaiFuHistoryBean.DataBean> mData;
    private SwipeRefreshLayout mRefreshLayout;
    private String xiaogenhao = "";



  @Inject
  WaiFuHistoryListPresenter mWaiFuHistoryListPresenter;
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
    View view = inflater.inflate(R.layout.fragment_waifu_history_list, container, false);
    mWaiFuHistoryListPresenter.attachView(this);
      ininView(view);
      ininData();
    return view;
  }

    private void ininData() {
      Bundle arguments = getArguments();
      xiaogenhao = arguments.getString(Const.XIAOGENHAO) == null ? "" : arguments.getString(Const.XIAOGENHAO);
      request();
    }

    private void ininView(View view) {
        mData = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WaiFuHistoryAdapter(R.layout.item_waifu_history, mData);
        recyclerView.setAdapter(adapter);
        mRefreshLayout = view.findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
    }

    public static WaiFuHistoryListFragment newInstance(String param1, String param2) {
        WaiFuHistoryListFragment fragment = new WaiFuHistoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }





    private void request() {
        EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getWaiFuHistory)
                .cacheKey(SPUtils.getInstance().getString(Const.S_YUANGONGH) +xiaogenhao+ URL.getWaiFuHistory)
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
                        WaiFuHistoryBean waiFuHistoryBean = new Gson().fromJson(s, WaiFuHistoryBean.class);
                        if ("00".equals(waiFuHistoryBean.getMsgCode())) {
                            initCacheData(waiFuHistoryBean.getData());
                        } else {
//                            adapter.setEmptyView(EmptyViewUtils.getSingleIntance().getEmptyView(waiFuHistoryBean.getMsgInfo()));
                        }
                    }
                });
    }

    private void initCacheData(List<WaiFuHistoryBean.DataBean> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
//            adapter.notifyDataSetChanged();
        }
    }

  @Override
  protected void lazyLoad() {

  }

  public void initData2(Bundle bundle) {
    xiaogenhao = bundle.getString(Const.XIAOGENHAO) == null ? "" : bundle.getString(Const.XIAOGENHAO);
    request();
  }
}
