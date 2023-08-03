package com.sh3h.meterreading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

/**
 * Created by LiMeng on 2017/10/24.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder>
        implements View.OnClickListener {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_STATUS = 1;
    public static final int TYPE_GUIJI = 2;

    public interface OnItemClickListener {
        /**
         * @param type 类型
         * @param position 下标
         */
        void onItemClick(int type, int position);
    }

    private List<DUTask> taskList;
    private OnItemClickListener onItemClickListener;

    public void setTaskList(List<DUTask> taskList) {
        this.taskList = taskList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chaobiaorw, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        DUTask duTask = taskList.get(position);
        holder.tv_ch.setText(String.format(ConstDataUtil.LOCALE, "%s/%d",
                TextUtil.getString(duTask.getcH()), duTask.getGongCi()));
        holder.tv_ch.setTag(duTask);
        holder.tv_count.setText(String.valueOf(duTask.getZongShu()));
        holder.yichaoshu.setText(String.valueOf(duTask.getYiChaoShu()));
        holder.tv_zhangWuNY.setText(String.valueOf(duTask.getZhangWuNY()));
        holder.tvPeriod.setText(TextUtil.getString(duTask.getChaoBiaoZQ()));
        holder.item.setOnClickListener(this);
        int resourceId;
        if (duTask.getTongBuBZ() == 2){
            resourceId = R.string.text_uploaded;
        }else if (duTask.getTongBuBZ() == 1){
            resourceId = R.string.text_upload_photo;
        }else if (duTask.getYiChaoShu() <= 0){
            resourceId = R.string.chaobiaorw_button_start;
        }else if (duTask.getYiChaoShu() == duTask.getZongShu()){
            resourceId = R.string.text_upload;
        }else {
            resourceId = R.string.chaobiaorw_button_continue;
        }
        holder.tv_chaobiaoZT.setText(resourceId);
        holder.tv_chaobiaoZT.setOnClickListener(this);
        holder.tv_chaobiaoGJ.setOnClickListener(this);
        holder.item.setTag(position);
        holder.tv_chaobiaoZT.setTag(position);
        holder.tv_chaobiaoGJ.setTag(position);
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener == null){
            return;
        }

        switch (v.getId()){
            case R.id.item:
                onItemClickListener.onItemClick(TYPE_ITEM, (Integer) v.getTag());
                break;
            case R.id.tv_chaobiaoZT:
                onItemClickListener.onItemClick(TYPE_STATUS, (Integer) v.getTag());
                break;
            case R.id.tv_chaobiaoGJ:
                onItemClickListener.onItemClick(TYPE_GUIJI, (Integer) v.getTag());
                break;
            default:
                break;
        }
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        private RelativeLayout item;
        private TextView tv_ch;
        private TextView tv_count;
        private TextView yichaoshu;
        private TextView tv_zhangWuNY;
        private TextView tvPeriod;
        private TextView tv_chaobiaoZT;
        private TextView tv_chaobiaoGJ;

        TaskHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.item);
            tv_ch = (TextView) itemView.findViewById(R.id.ch);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            yichaoshu = (TextView) itemView.findViewById(R.id.yichaoshu);
            tv_zhangWuNY = (TextView) itemView.findViewById(R.id.tv_zhangWuNY);
            tvPeriod = (TextView) itemView.findViewById(R.id.tv_chaobiaoZQ);
            tv_chaobiaoZT = (TextView) itemView.findViewById(R.id.tv_chaobiaoZT);
            tv_chaobiaoGJ = (TextView) itemView.findViewById(R.id.tv_chaobiaoGJ);
        }
    }
}
