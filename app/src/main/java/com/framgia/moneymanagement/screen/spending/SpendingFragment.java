package com.framgia.moneymanagement.screen.spending;

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
import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.repository.SpendingRepository;
import com.framgia.moneymanagement.data.source.remote.SpendingRemoteDataSource;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SpendingFragment extends Fragment implements View.OnClickListener, SpendingContract.View {
    private SpendingContract.Presenter mPresenter;
    private SpendingAdapter mAdapter;

    public static SpendingFragment newInstance() {
        return new SpendingFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        view.findViewById(R.id.button_spending_add).setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_spending);
        mAdapter = new SpendingAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getData() {
        mPresenter = new SpendingPresenter(new SpendingRepository(
                new SpendingRemoteDataSource(FirebaseDatabase.getInstance())), this);
        mPresenter.getSpending();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spending, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_spending_add:
                startActivity(SpendingAddActivity.getIntent(getContext()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetSpendingSucces(List<Spending> list) {
        mAdapter.addData(list);
    }

    @Override
    public void onGetSpendingFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
