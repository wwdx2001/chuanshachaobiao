package com.sh3h.mobileutil.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import com.sh3h.mobileutil.R;
import com.sh3h.mobileutil.widgrt.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerDialog extends BaseDialog implements OnClickListener {

	private Context _context;
	private DatePicker _wheelMain;
	private DatePickerListener _listener = null;
	/**
	 * _star_year开始时间，_end_year结束时间，_monthDate 月份最大显示值，_dayDate天数最大显示值
	 */
	private int _star_year = 1990, _end_year = 2100, _monthDate, _dayDate;

	public DatePickerDialog(Context context, int star_year, int end_year,
							int monthDate, int dayDate, DatePickerListener listener, int type) {
		super(context);
		this._context = context;
		this._listener = listener;
		if (type == DatePicker.AppointTime) {
			this._dayDate = dayDate;
			this._monthDate = monthDate;
			this._end_year = end_year;
			this._star_year = star_year;
		}
		init(type);
	}

	public void init(int type) {
		LayoutInflater inflater = (LayoutInflater) this._context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dataPicker = inflater.inflate(R.layout.datapicker, null);
		this.setTitle("选择时间");
		setButton1(this._context.getString(R.string.text_ok), this);
		setButton2(this._context.getString(R.string.text_cancel), this);
		_wheelMain = new DatePicker(dataPicker, this._context);
		_wheelMain.setMaxValue(_star_year, _end_year, _monthDate, _dayDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		_wheelMain.initDateTimePicker(year, month, day, type);
		setView(dataPicker);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == BUTTON1) {// Ok
			String dateString = _wheelMain.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this._listener.getTime(date);
		}
	}
}
