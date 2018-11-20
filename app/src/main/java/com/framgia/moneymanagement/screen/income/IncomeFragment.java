package com.framgia.moneymanagement.screen.income;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.repository.IncomeRepository;
import com.framgia.moneymanagement.data.source.remote.IncomeRemoteDataSource;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IncomeFragment extends Fragment implements IncomeContact.View,
        View.OnClickListener {
    private IncomeContact.Presenter mPresenter;
    private IncomeAdapter mAdapter;


    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_income);
        view.findViewById(R.id.button_add).setOnClickListener(this);
        mAdapter = new IncomeAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onGetDataSucces(List<Income> listIncome) {
        mAdapter.setData(listIncome);
    }

    @Override
    public void onGetDataFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void getData() {
        mPresenter = new IncomePresenter(this, new IncomeRepository(
                new IncomeRemoteDataSource(FirebaseDatabase.getInstance())));
        mPresenter.getIncome();
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
