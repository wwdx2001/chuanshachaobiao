package com.sh3h.meterreading.ui.record;

import android.widget.ImageView;

import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.entity.DUBillPreview;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUQianFeiXX;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordResult;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by zhangzhe on 2016/2/17.
 */
public interface RecordLRMVPView extends MvpView {
    void onLoadChaoBiaoZTList(List<DUChaoBiaoZT> duChaoBiaoZTList);
    void onLoadLiangGaoYY(List<DUCiYuXX> duCiYuXXList);
    void onLoadLiangDiYY(List<DUCiYuXX> duCiYuXXList);
    void onLoadRecordInfo(DURecord duRecord);
    void onLoadCardInfo(DUCard duCard);
    void onLoadImgPathList(List<String> imgPathList);
    void onLoadDuoMeiTXXList(List<DUMedia> duMediaList);
    void onLoadJianHaoMXList(List<JianHaoMX> jianHaoMXList);
    void onLoadQianFeiXXList(List<DUQianFeiXX> qianFeiXXList);
    void onUpdateRecord(DURecordResult duRecordResult);
    void onSaveNewImage(DUMedia duMedia);
    void onLoadImageViews(List<ImageView> imageViewList);
    void onDeleteImage(int index,String name);
    void onError(String message);
    void onSwitchRecordError(boolean isNext);
    void onUpdateCard(Boolean aBoolean);
    void onLoadCashResult(List<DUBillPreview> duBillPreviews);
}
