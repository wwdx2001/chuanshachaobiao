package com.sh3h.meterreading.ui.usage_change.presenter;

import android.os.Build;

import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeContract;
import com.sh3h.meterreading.util.StringCheckUtils;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2016/2/16.
 */
public class UsageChangeBasicPresenterImpl extends ParentPresenter<UsageChangeContract.View> {

    private static final String TAG = "BasicInformationPresenter";

    @Inject
    public UsageChangeBasicPresenterImpl(DataManager dataManager) {
        super(dataManager);
    }

    public void getCombinedResults(DUCombinedInfo duCombinedInfo, boolean isLocalSearch) {
        if (duCombinedInfo == null) {
            return;
        }
        mSubscription.add(mDataManager.getCombinedResult(duCombinedInfo, isLocalSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getCombinedResults onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getCombinedResults onError: %s---",
                                e.getMessage()));
                        getMvpView().error(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCard> duCards) {
                        LogUtil.i(TAG, "---getCombinedResults onNext---");
                        getMvpView().success(duCards);
                    }
                }));
    }

    public void getianHaoMX(String jianHao) {
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
                        getMvpView().error(e.getMessage());
                    }

                    @Override
                    public void onNext(List<JianHaoMX> duCard) {
                        LogUtil.i(TAG, "---getianHaoMX onNext---");
                        getMvpView().onGetJianHaoMXFinish(duCard);
                    }
                })
        );
    }

    /**
     * 查询列表数据
     * @param searchText 关键字
     * @param duCards 数据源
     */
    public void searchData(String searchText, List<DUCard> duCards) {
        if (duCards != null && duCards.size() > 0) {
            if (!TextUtil.isNullOrEmpty(searchText)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<DUCard> collect;
                    if (StringCheckUtils.isInt(searchText)) {
                        collect = duCards.stream()
                                .filter(receiptListEntity -> receiptListEntity.getCid().contains(searchText))
                                .collect(Collectors.toList());
                    } else {
                        collect = duCards.stream()
                                .filter(receiptListEntity -> receiptListEntity.getCh().contains(searchText) || receiptListEntity.getDizhi().contains(searchText))
                                .collect(Collectors.toList());
                    }
                    getMvpView().searchDataNotify(collect);
                } else {
                    List<DUCard> datas = new ArrayList<>();
                    for (DUCard data : duCards) {
                        if (data.getCid().contains(searchText) || data.getCh().contains(searchText) || data.getDizhi().contains(searchText)) {
                            datas.add(data);
                        }
                    }
                    getMvpView().searchDataNotify(datas);
                }
            } else {
                getMvpView().searchDataNotify(duCards);
            }
        }
    }

}
