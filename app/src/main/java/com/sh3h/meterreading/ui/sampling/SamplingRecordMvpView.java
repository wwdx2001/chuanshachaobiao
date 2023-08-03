package com.sh3h.meterreading.ui.sampling;

import android.widget.ImageView;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUSampling;
import com.sh3h.datautil.data.entity.DUSamplingResult;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by limeng on 2016/5/30.
 */
public interface SamplingRecordMvpView extends MvpView {
    void onLoadChaoBiaoZTList(List<DUChaoBiaoZT> duChaoBiaoZTList);

    void onLoadLiangGaoYY(List<DUCiYuXX> duCiYuXXList);

    void onLoadLiangDiYY(List<DUCiYuXX> duCiYuXXList);

    void onLoadBeiZhuYY(List<DUCiYuXX> duCiYuXXList);

    void onLoadRecordInfo(DUSampling duSampling);

    void onLoadCardInfo(DUCard duCard);

    void onLoadImgPathList(List<String> imgPathList);

    void onLoadDuoMeiTXXList(List<DUMedia> duMediaList);

    void onUpdateRecord(DUSamplingResult duSamplingResult);

    void onSaveNewImage(DUMedia duMedia);

    void onLoadImageViews(List<ImageView> imageViewList);

    void onDeleteImage(int index);

    void onError(String message);

    void onSwitchRecordError(boolean isNext);

    void onUpdateCard(Boolean aBoolean);

    void isCanInput(boolean isCan);

}
