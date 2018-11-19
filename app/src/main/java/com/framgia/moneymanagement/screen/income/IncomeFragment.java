package com.framgia.moneymanagement.screen.income;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.moneymanagement.R;

public class IncomeFragment extends Fragment implements View.OnClickListener {

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_add).setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                startActivity(IncomeAddActivity.getIntent(getContext()));
                break;
            default:
                break;
        }
    }
}
