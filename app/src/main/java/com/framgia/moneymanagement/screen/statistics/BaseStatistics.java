package com.framgia.moneymanagement.screen.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.repository.IncomeRepository;
import com.framgia.moneymanagement.data.repository.SpendingRepository;
import com.framgia.moneymanagement.data.source.remote.IncomeRemoteDataSource;
import com.framgia.moneymanagement.data.source.remote.SpendingRemoteDataSource;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseStatistics extends Fragment implements StatisticsContract.View {
    public static final int PIE_CHART_ART_TEXT_SIZE = 10;
    public static final int PIE_CHART_ART_CIRCLE_ALPHA = 0;
    public static final int PIE_CHART_ART_SLICE_SPACE = 2;
    public static final float PIE_CHART_ART_HOLDER_RADIUS = 25f;
    public static final String SPACE = "  ";
    public static final String UNIT_MONEY = " Đ";
    private TextView mTextIncome;
    private TextView mTextSpending;
    private TextView mTextSum;
    private PieChart mPieChartInCome;
    private PieChart mPieChartSpending;
    private int[] mColor;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextIncome = view.findViewById(R.id.text_income);
        mTextSpending = view.findViewById(R.id.text_spending);
        mTextSum = view.findViewById(R.id.text_sum);
        mPieChartInCome = view.findViewById(R.id.piechart_income);
        mPieChartSpending = view.findViewById(R.id.piechart_spending);
        mColor = getActivity().getResources().getIntArray(R.array.array_color);
        initPieChart(mPieChartInCome);
        initPieChart(mPieChartSpending);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_statistics, container, false);
    }

    @Override
    public void onGetSumIncomeSuccess(int sumIncome) {
        mTextIncome.setText(getResources().getString(R.string.statistic_sum_income)
                .concat(SPACE)
                .concat(String.valueOf(sumIncome))
                .concat(UNIT_MONEY));
    }

    @Override
    public void onGetSumIncomeFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSumSpendingSuccess(int sumSpending) {
        mTextSpending.setText(getResources().getString(R.string.statistic_sum_spending)
                .concat(SPACE)
                .concat(String.valueOf(sumSpending))
                .concat(UNIT_MONEY));
    }

    @Override
    public void onGetSumSpendingFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSumSalaryGain(int sub) {
        mTextSum.setText(getResources().getString(R.string.statistic_sum_salary_gain)
                .concat(SPACE)
                .concat(String.valueOf(sub))
                .concat(UNIT_MONEY));
    }

    @Override
    public void onSumSalaryReduce(int sub) {
        mTextSum.setText(getResources().getString(R.string.statistic_sum_salary_reduce)
                .concat(SPACE)
                .concat(String.valueOf(sub))
                .concat(UNIT_MONEY));
    }

    @Override
    public void peaChartArtIncome(List<Income> incomes) {
        mPieChartInCome.getDescription().setText(getString(R.string.title_income));
        List<PieEntry> yEntrys = new ArrayList<>();
        for (int i = 0; i < incomes.size(); i++) {
            yEntrys.add(new PieEntry(Float.parseFloat(incomes.get(i).getAmount()), i));
        }
        chartArt(mPieChartInCome, yEntrys);
    }

    @Override
    public void peaChartArtSpending(List<Spending> spendings) {
        mPieChartSpending.getDescription().setText(getString(R.string.title_spending));
        List<PieEntry> yEntrys = new ArrayList<>();
        for (int i = 0; i < spendings.size(); i++) {
            yEntrys.add(new PieEntry(Float.parseFloat(spendings.get(i).getAmount()), i));
        }
        chartArt(mPieChartSpending, yEntrys);
    }

    public abstract Fragment newInstance();

    public abstract void getData();

    public BaseStatisticsThisMonthPresenter getPresenterThisMonth() {
        return new BaseStatisticsThisMonthPresenter(new IncomeRepository(
                new IncomeRemoteDataSource(FirebaseDatabase.getInstance())),
                new SpendingRepository(new SpendingRemoteDataSource(FirebaseDatabase.getInstance())),
                this);
    }

    public StatisticLastMonthPresenterBase getPresenterLastMonth() {
        return new StatisticLastMonthPresenterBase(new IncomeRepository(
                new IncomeRemoteDataSource(FirebaseDatabase.getInstance())),
                new SpendingRepository(new SpendingRemoteDataSource(FirebaseDatabase.getInstance())),
                this);
    }

    private void initPieChart(PieChart pieChart) {
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(PIE_CHART_ART_HOLDER_RADIUS);
        pieChart.getDescription().setTextSize(PIE_CHART_ART_TEXT_SIZE);
        pieChart.setTransparentCircleAlpha(PIE_CHART_ART_CIRCLE_ALPHA);
        pieChart.setCenterTextSize(PIE_CHART_ART_TEXT_SIZE);
        pieChart.setDrawEntryLabels(true);
    }

    private void chartArt(PieChart pieChart, List<PieEntry> yEntrys) {
        PieDataSet pieDataSet = new PieDataSet(yEntrys, null);
        pieDataSet.setSliceSpace(PIE_CHART_ART_SLICE_SPACE);
        pieDataSet.setValueTextSize(PIE_CHART_ART_TEXT_SIZE);
        pieDataSet.setColors(mColor);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
