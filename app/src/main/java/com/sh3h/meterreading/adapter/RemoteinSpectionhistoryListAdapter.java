package com.sh3h.meterreading.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.InspectionInputActivity;
import com.sh3h.meterreading.util.Const;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;

import java.util.List;

public class RemoteinSpectionhistoryListAdapter extends RecyclerView.Adapter<RemoteinSpectionhistoryListAdapter.TaskHolder>  {
  public static final int TYPE_ITEM = 0;
  public static final int TYPE_STATUS = 1;
  public static final int TYPE_GUIJI = 2;
  private Context mContext;

  public RemoteinSpectionhistoryListAdapter(Context mContext) {
    this.mContext = mContext;
  }

  public interface OnLongClickListener{
    void onLongClick(View view, int position);
  }


  public interface OnItemClickListener {
    /**
     * @param type 类型
     * @param position 下标
     */
    void onItemClick(int type, int position);
  }

  private List<BiaoKaBean> biaoKaBeans;
  private List<BiaoKaListBean> biaoKaListBeans;
  private OnItemClickListener onItemClickListener;
  private OnLongClickListener onLongClickListener;
  private boolean isHistory;

  public void setTaskList(List<BiaoKaListBean> biaoKaListBeans) {
    this.biaoKaListBeans = biaoKaListBeans;
    notifyDataSetChanged();
  }
  public void setHistory(boolean isHistory) {
    this.isHistory = isHistory;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
  public void setOnLongClickListener(OnLongClickListener onLongClickListener){
    this.onLongClickListener=onLongClickListener;
  }

  @Override
  public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TaskHolder(LayoutInflater.from(parent.getContext())
      .inflate(R.layout.item_remote_list, parent, false));
  }

  @Override
  public void onBindViewHolder(TaskHolder holder, final int position) {
    BiaoKaListBean biaoKaListBean = biaoKaListBeans.get(position);
    holder.tv_xgh.setText(biaoKaListBean.getS_CID());
    holder.tv_huming.setText(biaoKaListBean.getS_HUMING());
    holder.tv_ysdz.setText(biaoKaListBean.getS_DIZHI());
    holder.tv_xjzt.setText( biaoKaListBean.getXUNJIANZT());
    holder.tv_bkzt.setText(biaoKaListBean.getBIAOKAZT());



    holder.item.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        BiaoKaBean item = mBiaoKaBeans.get(position);
        Intent intent=new Intent(mContext, InspectionInputActivity.class);
        intent.putExtra(Const.IS_HISTORY, true);
        intent.putExtra(Const.POSITION, position);
        intent.putExtra(Const.RENWUID,biaoKaListBeans.get(position).getS_RENWUID());
        intent.putExtra(Const.RENWUMC, biaoKaListBeans.get(position).getS_RENWUMC());
        intent.putExtra(Const.TYPE, Const.XUNJIANTASK_TYPE);
        intent.putExtra(Const.XIAOGENHAO, biaoKaListBeans.get(position).getS_CID());
        mContext.startActivity(intent);


      }
    });

}



  @Override
  public int getItemCount() {
    return biaoKaListBeans == null ? 0 : biaoKaListBeans.size();
  }



  class TaskHolder extends RecyclerView.ViewHolder{
    private TextView tv_xgh;
    private TextView tv_huming;
    private TextView tv_bkzt;
    private TextView tv_ysdz;
    private TextView tv_xjzt;
    private CardView item;

    TaskHolder(View itemView) {
      super(itemView);
      item = (CardView) itemView.findViewById(R.id.fs_cardview);
      tv_xgh = (TextView) itemView.findViewById(R.id.tv_xgh);
      tv_huming = (TextView) itemView.findViewById(R.id.tv_yonghum);
      tv_bkzt = (TextView) itemView.findViewById(R.id.tv_biaokazt);
      tv_ysdz = (TextView) itemView.findViewById(R.id.tv_yongshuidz);
      tv_xjzt = (TextView) itemView.findViewById(R.id.tv_xunjianzt);
    }
  }
}
