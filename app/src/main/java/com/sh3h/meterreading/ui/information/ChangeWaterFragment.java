package com.sh3h.meterreading.ui.information;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.dataprovider.schema.ChaoBiaoSJColumns;
import com.sh3h.datautil.data.entity.DUHuanBiaoJL;
import com.sh3h.datautil.data.entity.DUHuanBiaoJLInfo;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sh3h.meterreading.ui.information.CustomerInformationActivity.IS_LOCAL_SEARCH;

/**
 * Created by xulongjun on 2016/2/16.
 * 换表记录
 */
public class ChangeWaterFragment extends ParentFragment implements ChangeWaterMvpView {
    public static final String TAG = "ChangeWaterFragment";

    @Inject
    Bus mEventBus;

    @BindView(R.id.lv_huanbiaojllist)
    RecyclerView _huanBiaoJLlist;

    @BindView(R.id.loading_process)
    SmoothProgressBar loadingProcess;

    @Inject
    ChangeWaterPresenter mChangeWaterPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        LogUtil.i(TAG, "---BasicInformationFragment onCreate---");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changewater, container, false);
        LogUtil.i("ChangeWaterFragment", "onCreateView");

        ButterKnife.bind(this, view);
        mChangeWaterPresenter.attachView(this);
        init();
        mEventBus.register(this);

        return view;
    }

    private void init() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ChaoBiaoSJColumns.CID))) {
            return;
        }
        DUHuanBiaoJLInfo duHuanBiaoJLInfo = new DUHuanBiaoJLInfo();
        duHuanBiaoJLInfo.setFilterType(DUHuanBiaoJLInfo.FilterType.ONE);
        duHuanBiaoJLInfo.setCustomerId(_bundle.getString(ChaoBiaoSJColumns.CID));
        mChangeWaterPresenter.getHuanBiaoXXs(duHuanBiaoJLInfo, _bundle.getBoolean(IS_LOCAL_SEARCH, true), getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mChangeWaterPresenter.detachView();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onError(String message) {
        loadingProcess.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetHuanBiaoXXs(List<DUHuanBiaoJL> duHuanBiaoJLs) {
        loadingProcess.setVisibility(View.INVISIBLE);
        if (duHuanBiaoJLs != null) {
            _huanBiaoJLlist.setLayoutManager(new LinearLayoutManager(_huanBiaoJLlist.getContext()));
            SimpleStringRecyclerViewAdapter mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), duHuanBiaoJLs);
            _huanBiaoJLlist.setAdapter(mAdapter);
        }
    }

    @Subscribe
    public void onCustomerInformationUpdating(UIBusEvent.CustomerInformationUpdating customerInformationUpdating) {
        LogUtil.i(TAG, "---onCustomerInformationUpdating---");
        init();
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<DUHuanBiaoJL> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public final View mView;
            public final TextView mHuanBiaoRQN;
            public final TextView mHuanBiaoRQYR;
            public final TextView mXinBiaoBH;
            public final TextView mXinBiaoQM;
            public final TextView mJiuBiaoZS;
            public final TextView mHuBiaoY;
            public final TextView mBeiZhu;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mHuanBiaoRQN = (TextView) view.findViewById(R.id.tv_huanbiaorqn);
                mHuanBiaoRQYR = (TextView) view.findViewById(R.id.tv_huanbiaorqyr);
                mXinBiaoBH = (TextView) view.findViewById(R.id.tv_xinbiaobh);
                mXinBiaoQM = (TextView) view.findViewById(R.id.tv_xinbiaoqima);
                mJiuBiaoZS = (TextView) view.findViewById(R.id.tv_jiubiaozs);
                mHuBiaoY = (TextView) view.findViewById(R.id.tv_huanbiaoyuan);
                mBeiZhu = (TextView) view.findViewById(R.id.tv_beiZhu);
            }
        }

        public DUHuanBiaoJL getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<DUHuanBiaoJL> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_huanbiaojl, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mHuanBiaoRQN
                    .setText(TextUtil.format(mValues.get(position).getHuanbiaohtrq(), TextUtil.FORMAT_DATE_YEAR));
            holder.mHuanBiaoRQYR.setText(TextUtil.format(mValues.get(position).getHuanbiaohtrq(),
                    TextUtil.FORMAT_DATE_NO_YEAR_SLASH));
            holder.mXinBiaoBH.setText(String.valueOf(mValues.get(position).getXinbiaobh()));
            holder.mXinBiaoQM.setText(String.valueOf(mValues.get(position).getXinbiaodm()));
            holder.mJiuBiaoZS.setText(String.valueOf(mValues.get(position).getJiubiaocm()));
            holder.mHuBiaoY.setText(mValues.get(position).getHuitianr() != null ? String.valueOf(mValues.get(position)
                    .getHuitianr()) : TextUtil.EMPTY);

            if (mValues.get(position).getBeizhu() == null
                    || mValues.get(position).getBeizhu().equalsIgnoreCase(TextUtil.EMPTY)) {
                holder.mView.findViewById(R.id.tableRow_beiZhu).setVisibility(View.GONE);
            } else {
                holder.mView.findViewById(R.id.tableRow_beiZhu).setVisibility(View.VISIBLE);
                holder.mBeiZhu.setText(mValues.get(position).getBeizhu());
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
