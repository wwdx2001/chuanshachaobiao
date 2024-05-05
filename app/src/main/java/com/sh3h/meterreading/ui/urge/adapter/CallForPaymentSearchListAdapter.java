package com.sh3h.meterreading.ui.urge.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.sh3h.meterreading.R;

import java.util.List;

public class CallForPaymentSearchListAdapter extends BaseQuickAdapter<CallForPaymentSearchBean, BaseViewHolder> {
    public CallForPaymentSearchListAdapter(@Nullable List<CallForPaymentSearchBean> data) {
        super(R.layout.item_call_for_payment_search_list_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CallForPaymentSearchBean bean) {
        View itemView = baseViewHolder.itemView;
        CheckBox itemCallForSearchListCb = itemView.findViewById(R.id.item_call_for_search_list_cb);
        TextView itemCallForSearchListCid = itemView.findViewById(R.id.item_call_for_search_list_cid);
        TextView itemCallForPaySearchListUserNameTv = itemView.findViewById(R.id.item_call_for_pay_search_list_user_name_tv);
        TextView itemCallForPaySearchListPhoneTv = itemView.findViewById(R.id.item_call_for_pay_search_list_phone_tv);
        TextView itemCallForPaySearchListTimeTv = itemView.findViewById(R.id.item_call_for_pay_search_list_time_tv);
        TextView itemCallForPaySearchListSourceTv = itemView.findViewById(R.id.item_call_for_pay_search_list_source_tv);
        TextView itemCallForPaySearchListAddressTv = itemView.findViewById(R.id.item_call_for_pay_search_list_address_tv);

        itemCallForSearchListCid.setText(bean.getS_CID());
        itemCallForPaySearchListUserNameTv.setText(bean.getS_HM());
        itemCallForPaySearchListPhoneTv.setText(bean.getS_SHOUJI());
        itemCallForPaySearchListTimeTv.setText(bean.getD_ZJSLSJ());
        itemCallForPaySearchListAddressTv.setText(bean.getS_DZ());

        baseViewHolder.addOnClickListener(R.id.item_call_for_search_list_cb);

        itemCallForSearchListCb.setOnCheckedChangeListener(null);
        itemCallForSearchListCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.setCheck(isChecked);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
