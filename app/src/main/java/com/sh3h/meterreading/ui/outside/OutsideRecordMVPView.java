package com.sh3h.meterreading.ui.outside;

import android.widget.ImageView;

import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJ;
import com.sh3h.datautil.data.entity.DUWaiFuCBSJResult;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/4/18.
 */
public interface OutsideRecordMVPView extends MvpView {
    void onLoadChaoBiaoZTList(List<DUChaoBiaoZT> duChaoBiaoZTList);
    void onLoadLiangGaoYY(List<DUCiYuXX> duCiYuXXList);
    void onLoadLiangDiYY(List<DUCiYuXX> duCiYuXXList);
    void onLoadWaiFuCBSJInfo(DUWaiFuCBSJ duWaiFuCBSJ);
    void onLoadCardInfo(DUCard duCard);
    void onLoadImgPathList(List<String> imgPathList);
    void onLoadDuoMeiTXXList(List<DUMedia> duMediaList);
    void onUpdateWaiFuCBSJ(DUWaiFuCBSJResult duWaiFuCBSJResult);
    void onSaveNewImage(DUMedia duMedia);
    void onLoadImageViews(List<ImageView> imageViewList);
    void onDeleteImage(int index);
    void onError(String message);
    void onSwitchRecordError(boolean isNext);
}
