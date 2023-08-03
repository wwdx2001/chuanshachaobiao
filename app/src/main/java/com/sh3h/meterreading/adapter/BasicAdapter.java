package com.sh3h.meterreading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.meterreading.R;

import java.util.List;

/**
 * Created by LiMeng on 2017/10/28.
 */

public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.BasicHolder>{

    private List<JianHaoMX> list;

    public void setList(List<JianHaoMX> list) {
        this.list = list;
    }

    @Override
    public BasicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BasicAdapter.BasicHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jibenxx, parent, false));
    }

    @Override
    public void onBindViewHolder(BasicHolder holder, int position) {
        JianHaoMX jianHaoMX = list.get(position);
        holder.tvJiBen.setText(String.valueOf(jianHaoMX.getJieTiJB()));
        holder.tvMingCheng.setText(jianHaoMX.getFeiYongMC());
        holder.tvPrice.setText(String.valueOf(jianHaoMX.getJiaGe()));
        holder.tvBegin.setText(String.valueOf(jianHaoMX.getKaiShiSL()));
        holder.tvEnd.setText(String.valueOf(jianHaoMX.getJieShuSL()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class BasicHolder extends RecyclerView.ViewHolder{
        private TextView tvJiBen, tvMingCheng ,tvPrice, tvBegin, tvEnd;
        BasicHolder(View view) {
            super(view);
            tvJiBen = (TextView) view.findViewById(R.id.jb_txt_jieti);
            tvMingCheng = (TextView) view.findViewById(R.id.jb_txt_feiYongMC);
            tvPrice = (TextView) view.findViewById(R.id.jb_txt_JiaGe);
            tvBegin = (TextView) view.findViewById(R.id.jb_txt_kaiShiSL);
            tvEnd = (TextView) view.findViewById(R.id.jb_txt_jieShuSL);
        }
    }
}
