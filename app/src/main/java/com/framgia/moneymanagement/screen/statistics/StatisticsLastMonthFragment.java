package com.framgia.moneymanagement.screen.statistics;

import android.support.v4.app.Fragment;

public class StatisticsLastMonthFragment extends BaseStatistics {
    @Override
    public Fragment newInstance() {
        return new StatisticsLastMonthFragment();
    }

    @Override
    public void getData() {
        getPresenterLastMonth().getDataIncome();
    }
}
