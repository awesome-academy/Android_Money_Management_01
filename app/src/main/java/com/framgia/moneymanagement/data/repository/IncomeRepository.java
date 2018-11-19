package com.framgia.moneymanagement.data.repository;

import com.framgia.moneymanagement.data.IncomeDataSource;
import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.source.remote.IncomeRemoteDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

public class IncomeRepository implements IncomeDataSource.Remote {
    private IncomeDataSource.Remote mRemote;

    public IncomeRepository(IncomeDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void createIncome(Income income,
                             OnCompleteListener onCompleteListener,
                             OnFailureListener onFailureListener) {
        mRemote.createIncome(income, onCompleteListener, onFailureListener);
    }
}
