package com.framgia.moneymanagement.screen.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.moneymanagement.R;

public class StatisticsLastMonthFragment extends Fragment {

    public static StatisticsLastMonthFragment newInstance() {
        return new StatisticsLastMonthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics_last_month, container, false);
    }
}
