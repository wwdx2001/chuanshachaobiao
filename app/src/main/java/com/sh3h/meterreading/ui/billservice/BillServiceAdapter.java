package com.sh3h.meterreading.ui.billservice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.example.dataprovider3.entity.DUBillServiceInfoResultBean;
import com.sh3h.meterreading.R;

import java.util.List;

/**
 * Created by LiMeng on 2017/10/24.
 */

public class BillServiceAdapter extends RecyclerView.Adapter<BillServiceAdapter.BillServiceHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    public static final int TYPE_TAKE_PHOTO = 0;
    public static final int TYPE_BROWSE_PHOTO = 1;

    private View itemView;

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }

    @Override
    public boolean onLongClick(View v) {
        if (null != onLongClickListener) {
            onLongClickListener.onLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

    public interface OnItemClickListener {
        /**
         * @param type     类型
         * @param position 下标
         */
        void onItemClick(int type, int position);
    }

    private List<DUBillServiceInfoResultBean> billServiceInfos;
    private OnItemClickListener onItemClickListener;
    private OnLongClickListener onLongClickListener;

    public void setBillServiceList(List<DUBillServiceInfoResultBean> billServiceInfos) {
        this.billServiceInfos = billServiceInfos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public BillServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BillServiceHolder(getItemView(parent));
    }

    private View getItemView(ViewGroup parent) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill_service, parent, false);
        return itemView;
    }

    @Override
    public void onBindViewHolder(BillServiceHolder holder, int position) {
        DUBillServiceInfoResultBean duBillServiceInfo = billServiceInfos.get(position);
        holder.tvCH.setText(duBillServiceInfo.getS_ZHUMA());
        holder.tvAccountTime.setText(String.valueOf(duBillServiceInfo.getI_ZHANGWUNY()));
        holder.tvPaiFaSJ.setText(TimeUtils.millis2String(duBillServiceInfo.getD_PAIFASJ(), "yyyy-MM-dd HH:mm"));
        holder.tvXiaZaiSJ.setText(TimeUtils.millis2String(duBillServiceInfo.getD_xiazaisj(), "yyyy-MM-dd HH:mm"));
        if ("2".equals(duBillServiceInfo.getI_RENWUZT() + "")) {
            holder.btnTakePhoto.setText("已完成");
            holder.btnTakePhoto.setOnClickListener(null);
            holder.btnTakePhoto.setBackgroundResource(R.drawable.button8_background);
            holder.btnTakePhoto.setTextColor(itemView.getResources().getColor(R.color.text_white));
        } else {
            holder.btnTakePhoto.setText("拍照");
            holder.btnTakePhoto.setOnClickListener(this);
            holder.btnTakePhoto.setBackgroundResource(R.drawable.button8);
            holder.btnTakePhoto.setTextColor(itemView.getResources().getColor(R.color.button_text));
        }
        holder.btnTakePhoto.setTag(position);
        holder.btnBrowse.setOnClickListener(this);
        holder.btnBrowse.setTag(position);
    }

    @Override
    public int getItemCount() {
        return billServiceInfos == null ? 0 : billServiceInfos.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_take_photo:
                onItemClickListener.onItemClick(TYPE_TAKE_PHOTO, (Integer) v.getTag());
                break;
            case R.id.btn_browse:
                onItemClickListener.onItemClick(TYPE_BROWSE_PHOTO, (Integer) v.getTag());
                break;
            default:
                break;
        }
    }

    class BillServiceHolder extends RecyclerView.ViewHolder {
        private TextView tvCH;
        private TextView tvPaiFaSJ;
        private TextView tvXiaZaiSJ;
        private TextView tvAccountTime;
        private TextView btnTakePhoto;
        private TextView btnBrowse;

        BillServiceHolder(View itemView) {
            super(itemView);
            tvCH = (TextView) itemView.findViewById(R.id.tv_ch);
            tvPaiFaSJ = (TextView) itemView.findViewById(R.id.tv_paifSJ);
            tvXiaZaiSJ = (TextView) itemView.findViewById(R.id.tv_xiazaiSJ);
            tvAccountTime = (TextView) itemView.findViewById(R.id.tv_zhangWuNY);
            btnTakePhoto = (TextView) itemView.findViewById(R.id.btn_take_photo);
            btnBrowse = (TextView) itemView.findViewById(R.id.btn_browse);
        }
    }
}
