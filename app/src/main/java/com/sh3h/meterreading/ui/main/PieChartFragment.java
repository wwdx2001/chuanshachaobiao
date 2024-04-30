package com.sh3h.meterreading.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PieChartFragment extends ParentFragment implements OnClickListener, OnChartValueSelectedListener {
    private static final String TAG = "PieChartFragment";

    @BindView(R.id.chart1)
    PieChart mChart;

    @Inject
    Bus mEventBus;

    private StatisticsActivity statisticsActivity;

    private boolean isPrepared;

    private Map<String, Integer> mMap;

    private static int[] COLORS = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsActivity = (StatisticsActivity) getActivity();
        statisticsActivity.getActivityComponent().inject(this);
        mEventBus.register(this);
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_piechart, container, false);

        ButterKnife.bind(this, rootView);

        COLORS = new int[]{
                getResources().getColor(R.color.colors_pie_3),
                getResources().getColor(R.color.colors_pie_9),
                getResources().getColor(R.color.colors_pie_10),
                getResources().getColor(R.color.colors_pie_4),
                getResources().getColor(R.color.colors_pie_5),
                getResources().getColor(R.color.colors_pie_6),};

        isPrepared = true;
        lazyLoad();

        LogUtil.i(TAG, "---onCreateView---");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        isPrepared = false;
        mEventBus.unregister(this);
//        ButterKnife.unbind(this);
        LogUtil.i(TAG, "---onDestroyView---");
    }

    /**
     * 更新饼图数据
     */
    private void updatePieChart() {
        mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setDragDecelerationFrictionCoef(0.95f);


        mChart.setDrawHoleEnabled(false);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        mChart.setTouchEnabled(false);

		mChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mChart.setRotationEnabled(false);
        setData(3, 100);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData(int count, float range) {
        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry(mMap.get(MainActivity.YICHAO), 0));
        yVals1.add(new Entry(mMap.get(MainActivity.WEICHAO), 1));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add(MainActivity.YICHAO);
        xVals.add(MainActivity.WEICHAO);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setValueTextSize(15);
        boolean hasZeroData = false;
        for (int i = 0; i< yVals1.size();i++){
            if (yVals1.get(i).getVal() == 0){
                hasZeroData = true;
            }
        }
        if (hasZeroData){
            dataSet.setSliceSpace(0);
        }else {
            dataSet.setSliceSpace(2f);
        }
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        dataSet.setColors(COLORS);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

		xVals.add(MainActivity.YICHAO);
		xVals.add(MainActivity.WEICHAO);
        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);

        mChart.invalidate();
    }

    public void refreshChart() {
        if (mChart != null) {
            mChart.animateY(1000);
            mChart.invalidate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshChart();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        refreshChart();
    }
    @Subscribe
    public void onStatisticDataFinish(UIBusEvent.StatisticDataFinish statisticDataFinish) {
        LogUtil.i(TAG, "---onStatisticDataFinish---");
        DUTask duTask = statisticDataFinish.getDuTask();
        mMap = new HashMap<>();
        mMap.put(MainActivity.YICHAO, duTask.getYiChaoShu());
        mMap.put(MainActivity.WEICHAO, duTask.getZongShu() - duTask.getYiChaoShu());
        updatePieChart();
    }
}
