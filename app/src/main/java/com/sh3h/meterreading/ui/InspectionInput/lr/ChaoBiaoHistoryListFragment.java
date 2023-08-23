package com.sh3h.meterreading.ui.InspectionInput.lr;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.image.MyImagePreviewActivity;
import com.sh3h.meterreading.ui.InspectionInput.tools.EmptyViewUtils;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.BiaoWeiImage;
import com.sh3h.serverprovider.entity.ChaoBiaoHistoryBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * 用户信息-历史抄表
 *
 * @author Administrator
 */
public class ChaoBiaoHistoryListFragment extends ParentFragment implements ChaoBiaoHistoryListMvpView, BaseQuickAdapter.OnItemChildClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ChaoBiaoHistoryAdapter adapter;
    private List<ChaoBiaoHistoryBean.DataBean> mData;
    private SwipeRefreshLayout mRefreshLayout;
    private String xiaogenhao = "";


          @Inject
          ChaoBiaoHistoryListPresenter mChaoBiaoHistoryListPresenter;
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
            View view = inflater.inflate(R.layout.fragment_chaobiao_history_list, container, false);
            mChaoBiaoHistoryListPresenter.attachView(this);
              ininView(view);
              ininData();
            return view;
          }

    public static ChaoBiaoHistoryListFragment newInstance(String param1, String param2) {
        ChaoBiaoHistoryListFragment fragment = new ChaoBiaoHistoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void ininData() {
      Bundle arguments = getArguments();
      xiaogenhao = arguments.getString(Const.XIAOGENHAO) == null ? "" : arguments.getString(Const.XIAOGENHAO);
    }



    private void ininView(View view) {
        mData = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChaoBiaoHistoryAdapter(R.layout.item_chaobiao_history, mData);
        adapter.setOnItemChildClickListener(this);
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



    private void request() {
        EasyHttp.post(URL.BASE_XUNJIAN_URL+URL.getChaoBiaoHistory)
                .cacheKey(SPUtils.getInstance().getString(Const.S_YUANGONGH) + xiaogenhao + URL.getChaoBiaoHistory)
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
                        ToastUtils.showLong(e.getMessage());
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(String s) {
                        mRefreshLayout.setRefreshing(false);
                        ChaoBiaoHistoryBean chaoBiaoHistoryBean = new Gson().fromJson(s, ChaoBiaoHistoryBean.class);
                        if ("00".equals(chaoBiaoHistoryBean.getMsgCode())) {
                            initCacheData(chaoBiaoHistoryBean.getData());
                        } else {
                            adapter.setEmptyView(EmptyViewUtils.getSingleIntance().getEmptyView(chaoBiaoHistoryBean.getMsgInfo()));
                        }
                    }
                });
    }

    private void initCacheData(List<ChaoBiaoHistoryBean.DataBean> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

          @Override
          protected void lazyLoad() {

          }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
        IProgressDialog iProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("正在获取照片...");
                return progressDialog;
            }
        };
        EasyHttp.post(URL.BASE_URL+URL.getBWLISHICBImages)
                .params("CHAOBIAOID", String.valueOf(adapter.getData().get(position).getCHAOBIAOID()))
                .execute(new ProgressDialogCallBack<String>(iProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        BiaoWeiImage biaoWeiImage = GsonUtils.fromJson(s, BiaoWeiImage.class);
                        if ("00".equals(biaoWeiImage.getMsgCode())) {
                            ArrayList<ImageItem> imageItems = new ArrayList<>();
                            List<BiaoWeiImage.DataBean> data = biaoWeiImage.getData();
                            for (int i = 0; i < data.size(); i++) {
                                ImageItem imageItem = new ImageItem();
                                imageItem.path = data.get(i).getURL();
                                imageItems.add(imageItem);
                            }
                            Intent intent = new Intent(ActivityUtils.getTopActivity(), MyImagePreviewActivity.class);
                            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
                            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                            intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                            ActivityUtils.getTopActivity().startActivity(intent);
                        } else {
                            ToastUtils.showLong(biaoWeiImage.getMsgInfo());
                        }
                    }
                });
    }

  public void initData2(Bundle bundle) {
    xiaogenhao = bundle.getString(Const.XIAOGENHAO) == null ? "" : bundle.getString(Const.XIAOGENHAO);
  }
}
