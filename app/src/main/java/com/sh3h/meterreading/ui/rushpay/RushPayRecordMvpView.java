package com.sh3h.meterreading.ui.rushpay;

import android.widget.ImageView;

import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DURushPayTask;
import com.sh3h.datautil.data.entity.DURushPayTaskResult;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/10/15.
 */
public interface RushPayRecordMvpView extends MvpView {

    void onLoadImgPathList(List<String> imgPathList);

    void onLoadDuoMeiTXXList(List<DUMedia> duMediaList);

    void onUpdateRushPayTask(DURushPayTaskResult duRushPayTaskResult);

    void onSaveNewImage(DUMedia duMedia);

    void onLoadImageViews(List<ImageView> imageViewList);

    void onDeleteImage(int index);

    void onError(String message);

    void onSwitchRecordError(boolean isNext);

    void onLoadRushPayTask(DURushPayTask duRushPayTask);

}
