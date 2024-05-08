package com.sh3h.meterreading.ui.InspectionInput.tools;

import android.support.v4.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.sh3h.meterreading.R;

import java.util.Calendar;
import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/12/11 15:02
 */
public class PickerViewUtils {

    private PickerViewUtils() {
    }

    public static PickerViewUtils getInstance() {
        return SingleHolder.mInstance;
    }

    static class SingleHolder {
        private static PickerViewUtils mInstance = new PickerViewUtils();
    }

    public void showTimeDialog(String title, OnTimeSelectListener onTimeSelectListener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(2010, 0, 1);
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year, month, day);
//        endDate.set(2025, 11, 31);
        TimePickerView timePickerView = new TimePickerBuilder(ActivityUtils.getTopActivity(), onTimeSelectListener)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
                .setTitleText(title == null ? "请选择" : title)//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
//                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        timePickerView.show();
    }

    /**
     * 显示日期选择对话框
     *
     * @param title
     * @param onTimeSelectListener
     */
    public void showTimeDialog(String title, OnTimeSelectListener onTimeSelectListener, boolean[] type) {
        KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(2010, 0, 1);

        TimePickerView timePickerView = new TimePickerBuilder(ActivityUtils.getTopActivity(), onTimeSelectListener)
//                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setType(type)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
                .setTitleText(title == null ? "请选择" : title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
//                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定

                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        timePickerView.show();
    }

    public void showTimeDialog(String title, OnTimeSelectListener onTimeSelectListener, OnDismissListener onDismissListener, int startYear, int startMonth, int startDay) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 1, 1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(startYear, startMonth - 1, startDay);

        if (startYear + 1 > Calendar.getInstance().get(Calendar.YEAR)) {
            selectedDate.set(startYear, startMonth, startDay);
            endDate.set(startYear, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        } else {
            selectedDate.set(startYear + 1, startMonth - 1, startDay);
            endDate.set(startYear + 1, Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }
//        if (startYear == 2025 && startMonth == 12) {
//            endDate.set(startYear + 1, 11, 31);
//        } else {
//            endDate.set(2025, 11, 31);
//        }

        TimePickerView timePickerView = new TimePickerBuilder(ActivityUtils.getTopActivity(), onTimeSelectListener)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
                .setTitleText(title == null ? "请选择" : title)//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
//                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        timePickerView.show();
        timePickerView.setOnDismissListener(onDismissListener);
    }

    public void showTimeDialog(String title, OnTimeSelectListener onTimeSelectListener, boolean[] type, int afterTime, int startYear, int startMonth, int startDay) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(startYear, startMonth, startDay + afterTime);
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(startYear, startMonth, startDay);

//        if (startYear + 1 > Calendar.getInstance().get(Calendar.YEAR)) {
//            selectedDate.set(startYear, startMonth, startDay);
//            endDate.set(startYear, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//        } else {
//            selectedDate.set(startYear + 1, startMonth - 1, startDay);
//            endDate.set(startYear + 1, Calendar.getInstance().get(Calendar.MONTH),
//                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//        }
//        if (startYear == 2025 && startMonth == 12) {
//            endDate.set(startYear + 1, 11, 31);
//        } else {
//            endDate.set(2025, 11, 31);
//        }

        TimePickerView timePickerView = new TimePickerBuilder(ActivityUtils.getTopActivity(), onTimeSelectListener)
                .setType(type)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
                .setTitleText(title == null ? "请选择" : title)//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
//                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), R.color.colorPrimary))//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        timePickerView.show();
    }

    public void showCustomDialog(String title, List<String> data, OnOptionsSelectListener onOptionsSelectListener) {
        if (data == null || data.size() == 0) {
            return;
        }
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(ActivityUtils.getTopActivity(),
                onOptionsSelectListener)
                .setOutSideCancelable(false)
                .setSelectOptions(0)
                .setTitleText(title == null ? "请选择" : title)
                .setCyclic(false, false, false)
                .setLineSpacingMultiplier(2)
                .build();
        optionsPickerView.setPicker(data);
        optionsPickerView.show();
    }

    public void showNPickerDialog(String title, List<String> data1, List<String> data2, OnOptionsSelectListener onOptionsSelectListener) {
        if (data1 == null || data1.size() == 0) {
            return;
        }
        if (data2 == null || data2.size() == 0) {
            return;
        }
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(ActivityUtils.getTopActivity(),
                onOptionsSelectListener)
                .setOutSideCancelable(true)
                .setSelectOptions(0)
                .setTitleText(title == null ? "请选择" : title)
                .setCyclic(false, false, false)
                .setLineSpacingMultiplier(2)
                .build();
        optionsPickerView.setNPicker(data1, data2, null);
        optionsPickerView.show();
    }

  public void showZhengJianLXDialog(String title, List<String> data, OnOptionsSelectListener onOptionsSelectListener) {
    if (data == null || data.size() == 0) {
      return;
    }
    int selectOptions = 0;
    if(data.size()>4){
      selectOptions = 4;
    }
    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(ActivityUtils.getTopActivity(),
      onOptionsSelectListener)
      .setOutSideCancelable(false)
      .setSelectOptions(selectOptions)
      .setTitleText(title == null ? "请选择" : title)
      .setCyclic(false, false, false)
      .setLineSpacingMultiplier(2)
      .build();
    optionsPickerView.setPicker(data);
    optionsPickerView.show();
  }
}
