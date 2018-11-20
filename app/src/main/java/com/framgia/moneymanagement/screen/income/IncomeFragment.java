package com.framgia.moneymanagement.screen.income;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        View.OnClickListener, IncomeAdapter.OnItemLongClickListener {
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
        mAdapter = new IncomeAdapter(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_income);
        view.findViewById(R.id.button_add).setOnClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onGetDataSucces(List<Income> incomes) {
        mAdapter.setData(incomes);
    }

    @Override
    public void onGetDataFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteIncomeSucces() {
        Toast.makeText(getActivity(), R.string.msg_delele_succes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteIncomeFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
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

    @Override
    public void OnLongClickListener(final String id) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.title_delete)
                .setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.deleteIncome(id);
                    }
                })
                .setNegativeButton(R.string.title_no, null)
                .show();
    }
}
