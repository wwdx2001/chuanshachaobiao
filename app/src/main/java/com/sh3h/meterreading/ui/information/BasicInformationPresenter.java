package com.sh3h.meterreading.ui.information;

import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2016/2/16.
 */
public class BasicInformationPresenter extends ParentPresenter<BasicInformationMvpView> {

    private static final String TAG = "BasicInformationPresenter";


    @Inject
    public BasicInformationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getOneCard(DUCardInfo duCardInfo){
        mSubscription.add(mDataManager.getCard(duCardInfo)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<DUCard>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i(TAG, "---getOneCard onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i(TAG, String.format("---getOneCard onError: %s---", e.getMessage()));
                                getMvpView().onError(e.getMessage());
                            }

                            @Override
                            public void onNext(DUCard duCard) {
                                LogUtil.i(TAG, "---getOneCard onNext---");
                                getMvpView().onGetCardFinish(duCard);
                            }
                        })
        );
    }

    public void getianHaoMX(String jianHao){
        mSubscription.add(mDataManager.getJianHaoMXByJH(jianHao)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<JianHaoMX>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getianHaoMX onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getianHaoMX onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<JianHaoMX> duCard) {
                        LogUtil.i(TAG, "---getianHaoMX onNext---");
                        getMvpView().onGetJianHaoMXFinish(duCard);
                    }
                })
        );
    }

}
