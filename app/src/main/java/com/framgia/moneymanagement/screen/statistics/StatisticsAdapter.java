package com.framgia.moneymanagement.screen.statistics;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.moneymanagement.R;

public class StatisticsAdapter extends FragmentPagerAdapter {
    private static final int STATISTICS_LAST_MONTH_POSITION = 0;
    private static final int STATISTICS_THIS_MONTH_POSITION = 1;
    private static final int TAB_COUNT = 2;
    private Context mContext;

    public StatisticsAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case STATISTICS_LAST_MONTH_POSITION:
                return StatisticsLastMonthFragment.newInstance();
            case STATISTICS_THIS_MONTH_POSITION:
            default:
                return StatisticsThisMonthFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case STATISTICS_LAST_MONTH_POSITION:
                return mContext.getResources().getString(R.string.statistics_last_month);
            case STATISTICS_THIS_MONTH_POSITION:
            default:
                return mContext.getResources().getString(R.string.statistics_this_month);
        }
    }
}
