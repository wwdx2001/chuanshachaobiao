package com.sh3h.meterreading.ui.delay;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DUDelayRecordInfo;
import com.sh3h.datautil.data.entity.DUDelayRecord;
import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.datautil.data.entity.DUMediaInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiMeng on 2017/10/23.
 */

class DelayListPresenter extends ParentPresenter<DelayListMvpView> {
    private static final String TAG = "DelayListPresenter";
    private PreferencesHelper helper;
    private String account;

    @Inject
    DelayListPresenter(DataManager dataManager, PreferencesHelper helper) {
        super(dataManager);
        this.helper = helper;
        account = helper.getUserSession().getAccount();
    }

    public void loadCardXXs() {
        LogUtil.i(TAG, "---loadCardXXs 1---");
        DUCardInfo duCardInfo = new DUCardInfo(account);
        mSubscription.add(mDataManager.getCards(duCardInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadCardXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadCardXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUCard> duCardList) {
                        getMvpView().onLoadCards(duCardList);
                    }
                }));
    }

    public void loadRecordXXs(DUDelayRecordInfo.FilterType filterType, long limit) {
        LogUtil.i(TAG, "---loadRecordXXs 1---");
        DUDelayRecordInfo duRecordInfo = new DUDelayRecordInfo(filterType,
                account);
        mSubscription.add(mDataManager.getRecords(duRecordInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUDelayRecord>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadRecordXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---loadRecordXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUDelayRecord> duRecordList) {
                        LogUtil.i(TAG, "---loadRecordXXs onNext ---");
                        getMvpView().onLoadRecords(duRecordList);
                    }
                }));
    }

    /**
     * 2018.7.22 libao
     * 查询延迟图片数量
     */
    public void getDelayImageSize(){
        LogUtil.i(TAG, "---getDelayImageSize---");
        DUMedia duMedia = new DUMedia();
        duMedia.setAccount(account);
        duMedia.setType(DUMedia.MEDIA_TYPE_DELAY);
        DUMediaInfo duMediaInfo = new DUMediaInfo(
                DUMediaInfo.OperationType.MORE_ITEMS_DELAY,
                DUMediaInfo.MeterReadingType.DELAYING,
                0,
                DUMediaInfo.LIMIT,
                duMedia);

        mSubscription.add( mDataManager.getMediaList(duMediaInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUMedia>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getDelayImageSize onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getDelayImageSize onError: %s---", e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUMedia> duMedia) {
                        LogUtil.i(TAG, "---getDelayImageSize onNext ---");
                        getMvpView().onGetImageSize(duMedia.size());
                    }
                }));
    }
    public void checkForUpdatingCard() {
        LogUtil.i(TAG, "---checkForUpdatingCard---");
//        DUTaskInfo duTaskInfo = new DUTaskInfo(
//                account,
//                taskId,
//                TextUtil.getString(volume),
//                DUTaskInfo.FilterType.ONE);
//
//        mSubscription.add(mDataManager.getTask(duTaskInfo)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<DUTask>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.i(TAG, "---checkForUpdatingCard onCompleted---");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.i(TAG, "---checkForUpdatingCard onError---" + e.getMessage());
//                        getMvpView().onError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(DUTask duTask) {
//                        LogUtil.i(TAG, "---checkForUpdatingCard onNext---");
//                        getMvpView().onCheckForUpdatingCard(duTask.getTongBuBZ() == DUTask.TONGBUBZ_NORMAL);
//                    }
//                }));
    }

}
