package com.framgia.moneymanagement.screen.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.moneymanagement.R;

public class StatisticsThisMonthFragment extends Fragment {

    public static StatisticsThisMonthFragment newInstance() {
        return new StatisticsThisMonthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics_this_month, container, false);
    }
}
