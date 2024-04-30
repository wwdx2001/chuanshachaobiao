package com.sh3h.meterreading.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.util.Const;
import com.sh3h.serverprovider.entity.VoiceItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/12 10:31
 */
public class VoiceAdapter extends RecyclerView.Adapter<VoiceAdapter.SelectedPicViewHolder> {
    private int maxVoiceCount;
    private Context mContext;
    private List<VoiceItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private OnRecyclerViewItemLongClickListener longListener;
    private boolean isAdded;   //是否额外添加了最后一个语音
    private int type;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position, int type);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, int position, int type);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.longListener = listener;
    }

    public void setVoice(List<VoiceItem> data) {
        if (data == null) {
            return;
        }
        List<VoiceItem> newData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            File file = new File(data.get(i).path);
            if (file.exists()) {
                newData.add(data.get(i));
            }
        }
//        mData = new ArrayList<>(data);
        mData = new ArrayList<>(newData);
        if (getItemCount() < maxVoiceCount) {
//            mData.add(new VoiceItem());
//            mData.add(0, new VoiceItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }


    public List<VoiceItem> getVoices() {
        //由于语音未选满时，最后一张显示添加语音，因此这个方法返回真正的已选语音
//        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        if (isAdded) {
            return new ArrayList<>(mData.subList(0, mData.size()));
        } else {
            return mData;
        }
    }

    public VoiceAdapter(Context mContext, List<VoiceItem> data, int maxVoiceCount) {
        this.mContext = mContext;
        this.maxVoiceCount = maxVoiceCount;
        this.mInflater = LayoutInflater.from(mContext);
        setVoice(data);
    }

    public VoiceAdapter(Context mContext, List<VoiceItem> data, int maxVoiceCount, int type) {
        this.mContext = mContext;
        this.maxVoiceCount = maxVoiceCount;
        this.mInflater = LayoutInflater.from(mContext);
        this.type = type;
        setVoice(data);
    }

    @NonNull
    @Override
    public SelectedPicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.voice_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView mVoiceImg;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            mVoiceImg = itemView.findViewById(R.id.voice_img);
            mVoiceImg.getLayoutParams().width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(80)) / 3;
            mVoiceImg.getLayoutParams().height = mVoiceImg.getLayoutParams().width;
        }

        public void bind(int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            //根据条目位置设置图片
            VoiceItem item = mData.get(position);
//            if (isAdded && position == getItemCount() - 1) {
            if (isAdded && position == 0) {
                clickPosition = Const.IMAGE_ITEM_ADD;
            } else if (isAdded && position != 0) {
//                ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                clickPosition = position - 1;
            } else {
                clickPosition = position;
            }
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, clickPosition, type);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (longListener != null) {
                longListener.onItemLongClick(view, clickPosition, type);
            }
            return true;
        }
    }


}
