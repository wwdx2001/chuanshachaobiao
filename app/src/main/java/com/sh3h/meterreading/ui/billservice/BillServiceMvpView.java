package com.sh3h.meterreading.ui.billservice;

import android.widget.ImageView;

import com.example.dataprovider3.entity.DUBillServiceInfoResultBean;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.meterreading.ui.base.MvpView;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

public interface BillServiceMvpView extends MvpView {

    void onBillServiceListNext(List<DUBillServiceInfoResultBean> mListData);

    void onSaveNewImage(DUMedia duMedia);

    void onLoadImageViews(List<ImageView> imageViewList);

    void onLoadImgPathList(List<String> imgPathList);

    void onLoadDuoMeiTXXList(List<DUMedia> duMediaList);

    void onDeleteImage(int index);

    void notifyListData(DUBillServiceInfoResultBean resultBean);

    void onError(ApiException e);

    void onFile(String info);

    void showProgressBar();

    void hideProgressBar();

    void showProgressDialog(String msg);

    void hideProgressDialog();

}
