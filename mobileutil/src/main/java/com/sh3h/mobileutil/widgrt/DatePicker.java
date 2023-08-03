package com.sh3h.mobileutil.widgrt;

import android.content.Context;
import android.view.View;

import com.kankan.wheel.WheelView;
import com.kankan.wheel.adapter.NumericWheelAdapter;
import com.kankan.wheel.widget.OnWheelChangedListener;
import com.sh3h.mobileutil.R;

import java.util.Arrays;
import java.util.List;

public class DatePicker implements OnWheelChangedListener {

    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    // private WheelView wv_hours;
    // private WheelView wv_mins;
    private Context _context;
    private int _star_year, _end_year;
    private int _monthDate;
    private int _dayDate;
    public static final int AllTime = 1001;
    public static final int AppointTime = 1002;
    private List<String> _list_big;
    private List<String> _list_little;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setMaxValue(int star_year, int end_year, int monthDate,
                            int dayDate) {
        this._star_year = star_year;
        this._end_year = end_year;
        this._monthDate = monthDate;
        this._dayDate = dayDate;
    }

    public DatePicker(View view, Context context) {
        super();
        this.view = view;
        this._context = context;
    }

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void initDateTimePicker(int year, int month, int day, int type) {

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        _list_big = Arrays.asList(months_big);
        _list_little = Arrays.asList(months_little);
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);

        wv_year.setViewAdapter(new NumericWheelAdapter(this._context,
                _star_year, _end_year));
        wv_year.setCurrentItem(year - _star_year);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        if (type == AllTime)
            wv_month.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                    12));
        else
            wv_month.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                    _monthDate));
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (_list_big.contains(String.valueOf(month + 1))) {
            if (type == AllTime)
                wv_day.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                        31));
            else
                wv_day.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                        _dayDate));
        } else if (_list_little.contains(String.valueOf(month + 1))) {
            if (type == AllTime)
                wv_day.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                        30));
            else
                wv_day.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                        _dayDate));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                if (type == AllTime)
                    wv_day.setViewAdapter(new NumericWheelAdapter(
                            this._context, 1, 29));
                else
                    wv_day.setViewAdapter(new NumericWheelAdapter(
                            this._context, 1, _dayDate));
            else if (type == AllTime)
                wv_day.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                        28));
            else
                wv_day.setViewAdapter(new NumericWheelAdapter(this._context, 1,
                        _dayDate));
        }
        wv_day.setCurrentItem(day - 1);
        wv_year.addChangingListener(this);
        wv_month.addChangingListener(this);

    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        sb.append((wv_year.getCurrentItem() + _star_year)).append("-")
                .append((wv_month.getCurrentItem() + 1)).append("-")
                .append((wv_day.getCurrentItem() + 1));
        return sb.toString();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        int yearDate = (wv_year.getCurrentItem() + _star_year);
        int month_num = newValue + 1;

        if (wheel == wv_year) {

            if (yearDate == _end_year) {
                wv_month.setViewAdapter(new NumericWheelAdapter(this._context,
                        1, _monthDate));
            } else {
                wv_month.setViewAdapter(new NumericWheelAdapter(this._context,
                        1, 12));
            }

            wv_month.setCurrentItem(0);
            wv_day.setCurrentItem(0);
            int year_num = newValue + _star_year;
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (_list_big
                    .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1, 31));
            } else if (_list_little.contains(String.valueOf(wv_month
                    .getCurrentItem() + 1))) {
                wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1, 30));
            } else {
                if ((year_num % 4 == 0 && year_num % 100 != 0)
                        || year_num % 400 == 0)
                    wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1,
                            29));
                else
                    wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1,
                            28));
            }
        } else if (wheel == wv_month) {

            wv_day.setCurrentItem(0);
            if (yearDate == _end_year) {
                wv_month.setViewAdapter(new NumericWheelAdapter(this._context,
                        1, _monthDate));
                if (month_num == _monthDate) {
                    wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1,
                            _dayDate));
                } else {
                    seDayAdapter(month_num);
                }
            } else {
                wv_month.setViewAdapter(new NumericWheelAdapter(this._context,
                        1, 12));
                seDayAdapter(month_num);
            }
        }

    }

    private void seDayAdapter(int month_num) {
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (_list_big.contains(String.valueOf(month_num))) {
            wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1, 31));
        } else if (_list_little.contains(String.valueOf(month_num))) {
            wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1, 30));
        } else {
            if (((wv_year.getCurrentItem() + _star_year) % 4 == 0 && (wv_year
                    .getCurrentItem() + _star_year) % 100 != 0)
                    || (wv_year.getCurrentItem() + _star_year) % 400 == 0)
                wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1, 29));
            else
                wv_day.setViewAdapter(new NumericWheelAdapter(_context, 1, 28));
        }
    }
}
