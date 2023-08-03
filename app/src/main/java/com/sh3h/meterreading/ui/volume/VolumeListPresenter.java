package com.sh3h.meterreading.ui.volume;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCardInfo;
import com.sh3h.datautil.data.entity.DURecord;
import com.sh3h.datautil.data.entity.DURecordInfo;
import com.sh3h.datautil.data.entity.DURequest;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.data.entity.DUTaskInfo;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.meterreading.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangzhe on 2016/2/1.
 */
public class VolumeListPresenter extends ParentPresenter<VolumeListMvpView> {
    private static final String TAG = "VolumeListPresenter";

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public VolumeListPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public void loadCardXXs(String volume) {
        LogUtil.i(TAG, "---loadCardXXs 1---");
        if (volume == null) {
            LogUtil.i(TAG, "---loadCardXXs 2---");
            getMvpView().onError("volume is null");
        } else {
            LogUtil.i(TAG, "---loadCardXXs 3---");
            DUCardInfo duCardInfo = new DUCardInfo(volume, DUCardInfo.FilterType.SEARCHING_ALL);
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
    }

    public void loadRecordXXs(DURecordInfo.FilterType filterType,
                              int taskId,
                              String volume,
                              String key,
                              long limit) {
        LogUtil.i(TAG, "---loadRecordXXs 1---");
        if (volume == null) {
            LogUtil.i(TAG, "---loadRecordXXs 2---");
            getMvpView().onError("volume is null");
        } else {
            LogUtil.i(TAG, "---loadRecordXXs 3---");
            UserSession userSession = mPreferencesHelper.getUserSession();
            DURecordInfo duRecordInfo = new DURecordInfo(
                    filterType,
                    TextUtil.getString(userSession.getAccount()),
                    taskId,
                    volume,
                    TextUtil.getString(key),
                    limit);
            mSubscription.add(mDataManager.getRecords(duRecordInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<DURecord>>() {
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
                        public void onNext(List<DURecord> duRecordList) {
                            LogUtil.i(TAG, "---loadRecordXXs onNext ---");
                            getMvpView().onLoadRecords(duRecordList);
                        }
                    }));
        }
    }

    public void checkForUpdatingCard(int taskId,
                                     String volume) {
        LogUtil.i(TAG, "---checkForUpdatingCard---");
        UserSession userSession = mPreferencesHelper.getUserSession();
        DUTaskInfo duTaskInfo = new DUTaskInfo(
                TextUtil.getString(userSession.getAccount()),
                taskId,
                TextUtil.getString(volume),
                DUTaskInfo.FilterType.ONE);

        mSubscription.add(mDataManager.getTask(duTaskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUTask>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---checkForUpdatingCard onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---checkForUpdatingCard onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUTask duTask) {
                        LogUtil.i(TAG, "---checkForUpdatingCard onNext---");
                        getMvpView().onCheckForUpdatingCard(duTask.getTongBuBZ() == DUTask.TONGBUBZ_NORMAL);
                    }
                }));
    }
}
