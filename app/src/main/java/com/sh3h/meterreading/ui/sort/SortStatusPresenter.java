package com.sh3h.meterreading.ui.sort;

import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by limeng on 2016/4/26.
 */
public class SortStatusPresenter extends ParentPresenter<SortStatusMvpView> {
    private ConfigHelper configHelper;

    @Inject
    public SortStatusPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager);
        this.configHelper = configHelper;
    }

    public void getStatus() {
        mSubscription.add(mDataManager.getSortStatus()
                .subscribe(new Subscriber<List<ChaoBiaoZT>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ChaoBiaoZT> duChaoBiaoZT) {
                        getMvpView().initRecyclerView(duChaoBiaoZT);
                    }
                }));
    }

    public void saveSortStatus(String status) {
        mSubscription.add(configHelper.saveUserChangYong(status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        int resource = aBoolean ? R.string.text_saving_success : R.string.text_saving_failure;
                        getMvpView().showMessage(resource);
                    }
                }));
    }
}
