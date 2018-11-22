package com.framgia.moneymanagement.screen.statistics;

import android.support.v4.app.Fragment;

public class StatisticsThisMonthFragment extends BaseStatistics {
    @Override
    public Fragment newInstance() {
        return new StatisticsThisMonthFragment();
    }

    @Override
    public void getData() {
        getPresenterThisMonth().getDataIncome();
    }
}
