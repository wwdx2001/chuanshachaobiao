package com.sh3h.meterreading.injection.component;

import com.sh3h.meterreading.injection.annotation.PerActivity;
import com.sh3h.meterreading.injection.module.ActivityModule;
import com.sh3h.meterreading.ui.InspectionInput.InspectionInputActivity;
import com.sh3h.meterreading.ui.InspectionInput.RemoteLRFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.ChaoBiaoHistoryListFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.LRDuoMeitiFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.LROperatingFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.QianFeiXXFragment;
import com.sh3h.meterreading.ui.InspectionInput.lr.WaiFuHistoryListFragment;
import com.sh3h.meterreading.ui.RemoteinSpectionOHistoryListActivity.RemoteinSpectionOHistoryListActivity;
import com.sh3h.meterreading.ui.billservice.BillServiceActivity;
import com.sh3h.meterreading.ui.check.CheckActivity;
import com.sh3h.meterreading.ui.delay.DelayListActivity;
import com.sh3h.meterreading.ui.delayRecord.DelayRecordActivity;
import com.sh3h.meterreading.ui.delayRecord.DelayRecordLRFragment;
import com.sh3h.meterreading.ui.forceImage.ForceImageActivity;
import com.sh3h.meterreading.ui.information.ArrearsWaterFragment;
import com.sh3h.meterreading.ui.information.BasicInformationFragment;
import com.sh3h.meterreading.ui.information.ChangeWaterFragment;
import com.sh3h.meterreading.ui.information.CustomerInformationActivity;
import com.sh3h.meterreading.ui.information.PayWaterFragment;
import com.sh3h.meterreading.ui.information.ReadWaterFragment;
import com.sh3h.meterreading.ui.lgld.LgldListActivity;
import com.sh3h.meterreading.ui.login.LoginActivity;
import com.sh3h.meterreading.ui.main.BarChartFragment;
import com.sh3h.meterreading.ui.main.MainActivity;
import com.sh3h.meterreading.ui.main.MyWorkFragment;
import com.sh3h.meterreading.ui.main.PieChartFragment;
import com.sh3h.meterreading.ui.main.StatisticsActivity;
import com.sh3h.meterreading.ui.main.StatisticsFragment;
import com.sh3h.meterreading.ui.outside.OutsideListActivity;
import com.sh3h.meterreading.ui.outside.OutsideRecordActivity;
import com.sh3h.meterreading.ui.record.DetailInfoFragment;
import com.sh3h.meterreading.ui.record.RecordActivity;
import com.sh3h.meterreading.ui.record.RecordLRFragment;
import com.sh3h.meterreading.ui.remoteinspection.RemoteinSpectionListActivity;
import com.sh3h.meterreading.ui.rushpay.RushPayRecordActivity;
import com.sh3h.meterreading.ui.rushpay.RushPayTaskActivity;
import com.sh3h.meterreading.ui.sampling.SamplingListActivity;
import com.sh3h.meterreading.ui.sampling.SamplingRecordActivity;
import com.sh3h.meterreading.ui.sampling.SamplingTaskActivity;
import com.sh3h.meterreading.ui.search.CombinedSearchActivity;
import com.sh3h.meterreading.ui.search.CombinedSearchResultActivity;
import com.sh3h.meterreading.ui.setting.SettingActivity;
import com.sh3h.meterreading.ui.setting.SettingFragment;
import com.sh3h.meterreading.ui.setting.UserCommonActivity;
import com.sh3h.meterreading.ui.sort.SortStatusActivity;
import com.sh3h.meterreading.ui.task.TaskListActivity;
import com.sh3h.meterreading.ui.temporary.AdjustTemporaryActivity;
import com.sh3h.meterreading.ui.urge.CallForPaymentArrearsFeesDetailActivity;
import com.sh3h.meterreading.ui.urge.CallForPaymentOrderDetailActivity;
import com.sh3h.meterreading.ui.urge.CallForPaymentTaskActivity;
import com.sh3h.meterreading.ui.urge.CallForPaymentWordOrderActivity;
import com.sh3h.meterreading.ui.urge.back.OrderBackHandleFragment;
import com.sh3h.meterreading.ui.urge.fragment.CallForPayDetailFragment;
import com.sh3h.meterreading.ui.urge.fragment.CallForPaymentBackfillFragment;
import com.sh3h.meterreading.ui.urge.fragment.CallForPaymentMediaFragment;
import com.sh3h.meterreading.ui.urge.fragment.OrderDetailMessageFragment;
import com.sh3h.meterreading.ui.urge.fragment.OrderElseMessageFragment;
import com.sh3h.meterreading.ui.urge_search.CallForPaymentSearchActivity;
import com.sh3h.meterreading.ui.usage_change.RealNameDetailActivity;
import com.sh3h.meterreading.ui.usage_change.UsageChangeSearchListActivity;
import com.sh3h.meterreading.ui.usage_change.UsageChangeUploadActivity;
import com.sh3h.meterreading.ui.volume.AdjustNumberActivity;
import com.sh3h.meterreading.ui.volume.VolumeListActivity;
import com.sh3h.meterreading.ui.welcome.WelcomeActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent{
    void inject(CheckActivity checkActivity);
    void inject(WelcomeActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
    void inject(TaskListActivity taskListActivity);
    void inject(CombinedSearchActivity combinedSearchActivity);
    void inject(CombinedSearchResultActivity fuzzySearchResultActivity);

    void inject(VolumeListActivity volumeListActivity);
    void inject(DelayListActivity delayListActivity);
    void inject(LgldListActivity lgldListActivity);
    void inject(StatisticsActivity statisticsActivity);
//    DaggerActivityComponent

    void inject(SettingFragment settingFragment);
    void inject(AdjustNumberActivity adjustNumberActivity);
    void inject(CustomerInformationActivity customerInformationActivity);
    void inject(RecordActivity recordActivity);
    void inject(DelayRecordActivity recordActivity);

    void inject(RecordLRFragment recordLRFragment);
    void inject(DelayRecordLRFragment recordLRFragment);

    void inject(BasicInformationFragment basicInformationFragment);
    void inject(ReadWaterFragment readWaterFragment);
    void inject(PayWaterFragment payWaterFragment);
    void inject(ArrearsWaterFragment arrearsWaterFragment);
    void inject(ChangeWaterFragment changeWaterFragment);


    void inject(MyWorkFragment myWorkFragment);

    void inject(StatisticsFragment statisticsFragment);
    void inject(BarChartFragment barChartFragment);
    void inject(PieChartFragment pieChartFragment);
//    void inject(ArcGisMapActivity arcGisMapActivity);
    void inject(AdjustTemporaryActivity adjustTemporaryActivity);
    void inject(DetailInfoFragment detailInfoFragment);
    void inject(UserCommonActivity userCommonActivity);
    void inject(ForceImageActivity forceImageActivity);
    void inject(SortStatusActivity sortStatusActivity);

    void inject(SettingActivity settingActivity);

    void inject(RushPayRecordActivity rushPayRecordActivity);
    void inject(RushPayTaskActivity rushPayTaskActivity);

    void inject(SamplingListActivity samplingListActivity);
    void inject(SamplingRecordActivity samplingRecordActivity);
    void inject(SamplingTaskActivity samplingTaskActivity);

    void inject(OutsideListActivity outsideListActivity);
    void inject(OutsideRecordActivity outsideRecordActivity);

    void inject(RemoteinSpectionListActivity remoteinSpectionListActivity);
    void inject(RemoteinSpectionOHistoryListActivity remoteinSpectionOHistoryListActivity);
    void inject(InspectionInputActivity inspectionInputActivity);
    void inject(RemoteLRFragment remoteLRFragment);
    void inject(ChaoBiaoHistoryListFragment chaoBiaoHistoryListFragment);
    void inject(LRDuoMeitiFragment lrDuoMeitiFragment);
    void inject(LROperatingFragment lrOperatingFragment);
    void inject(QianFeiXXFragment qianFeiXXFragment);
    void inject(WaiFuHistoryListFragment waiFuHistoryListFragment);

    /**
     * 催缴
     */
    void inject(CallForPaymentTaskActivity taskActivity);
    void inject(CallForPaymentWordOrderActivity callForPaymentWordOrderActivity);
    void inject(CallForPaymentOrderDetailActivity callForPaymentOrderDetailActivity);
    void inject(CallForPaymentArrearsFeesDetailActivity callForPaymentArrearsFeesDetailActivity);
    void inject(CallForPayDetailFragment callForPayDetailFragment);
    void inject(OrderDetailMessageFragment orderDetailMessageFragment);
    void inject(OrderElseMessageFragment orderElseMessageFragment);
    void inject(CallForPaymentBackfillFragment callForPaymentBackfillFragment);
    void inject(OrderBackHandleFragment orderBackHandleFragment);
    void inject(CallForPaymentMediaFragment callForPaymentMediaFragment);
    void inject(CallForPaymentSearchActivity callForPaymentSearchActivity);

    /**
     * 用户实名制/用水性质变更
     */
    void inject(UsageChangeSearchListActivity usageChangeSearchListActivity);
    void inject(BillServiceActivity billServiceActivity);
    void inject(RealNameDetailActivity realNameDetailActivity);
    void inject(UsageChangeUploadActivity usageChangeUploadActivity);
}
