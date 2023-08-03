package com.sh3h.meterreading.ui.sort;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperAdapter;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperViewHolder;
import co.paulburke.android.itemtouchhelper.helper.SimpleItemTouchHelperCallback;

public class SortStatusActivity extends ParentActivity implements SortStatusMvpView,
        MenuItem.OnMenuItemClickListener{
    @Inject
    SortStatusPresenter sortStatusPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    @Bind(R.id.rv_sort_status)
    RecyclerView rvSortStatusList;

    private MenuItem okMenuItem;
    private SortStatusRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_status);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        sortStatusPresenter.attachView(this);
        sortStatusPresenter.getStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort_status, menu);
        okMenuItem = menu.findItem(R.id.menu_sort_status_ok);
        okMenuItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_status_ok:
                onOkMenu();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        sortStatusPresenter.detachView();
    }

    @Override
    public void initRecyclerView(List<ChaoBiaoZT> duChaoBiaoZT) {
        adapter = new SortStatusRecyclerAdapter(R.layout.item_sort_status, duChaoBiaoZT);
        rvSortStatusList.setHasFixedSize(true);
        rvSortStatusList.setAdapter(adapter);
        rvSortStatusList.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvSortStatusList);
    }

    @Override
    public void showMessage(int resource) {
        com.sh3h.mobileutil.util.ApplicationsUtil.showMessage(this, resource);
        setResult(1);
        finish();
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            com.sh3h.mobileutil.util.ApplicationsUtil.showMessage(this, message);
        }
        setResult(1);
        finish();
    }

    public class SortStatusRecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder>
            implements ItemTouchHelperAdapter {
        private int mResource;
        private List<ChaoBiaoZT> mListData;

        public SortStatusRecyclerAdapter(int resource,
                                         List<ChaoBiaoZT> duRecordList) {
            mResource = resource;
            mListData = duRecordList;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            ChaoBiaoZT chaoBiaoZT = mListData.get(position);
            holder.setText(chaoBiaoZT.getS_ZHUANGTAIMC());
        }

        @Override
        public void onItemDismiss(int position) {
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
            updateList(fromPosition, toPosition);
            okMenuItem.setVisible(true);
            return true;
        }

        @Override
        public int getItemCount() {
            return mListData == null ? 0 : mListData.size();
        }

        public List<ChaoBiaoZT> getData() {
            return mListData;
        }

        //更改list集合，记录下标
        private void updateList(int fromPosition, int toPosition) {
            ChaoBiaoZT fromStatus = mListData.get(fromPosition);
            ChaoBiaoZT toStatus = mListData.get(toPosition);
            mListData.set(toPosition, fromStatus);
            mListData.set(fromPosition, toStatus);
        }

    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {
        private TextView tvSortStatus;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvSortStatus = (TextView) itemView.findViewById(R.id.tv_sort_status_item);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

        public void setText(String text) {
            tvSortStatus.setText(text);
        }
    }

    private void onOkMenu() {
        String status = "";
        List<ChaoBiaoZT> list = adapter.getData();
        for (ChaoBiaoZT chaoBiaoZT : list) {
            status = status + chaoBiaoZT.getI_ZHUANGTAIBM() + ",";
        }
        if (!TextUtil.isNullOrEmpty(status)) {
            status = status.substring(0, status.length() - 1);
        }
        sortStatusPresenter.saveSortStatus(status);
    }

}
