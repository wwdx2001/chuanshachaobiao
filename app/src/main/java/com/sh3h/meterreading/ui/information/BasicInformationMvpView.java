package com.sh3h.meterreading.ui.information;

import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/2/16.
 */
public interface BasicInformationMvpView  extends MvpView {
    void onGetCardFinish(DUCard duCard);
    void onGetJianHaoMXFinish(List<JianHaoMX> list);
    void onError(String message);
}
