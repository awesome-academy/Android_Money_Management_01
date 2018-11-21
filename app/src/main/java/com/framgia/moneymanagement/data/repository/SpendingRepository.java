package com.framgia.moneymanagement.data.repository;

import com.framgia.moneymanagement.data.SpendingDataSource;
import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.source.remote.SpendingRemoteDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

public class SpendingRepository implements SpendingDataSource.Remote {
    private SpendingDataSource.Remote mRemote;

    public SpendingRepository(SpendingDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void createSpending(Spending spending, OnCompleteListener onCompleteListener,
                               OnFailureListener onFailureListener) {
        mRemote.createSpending(spending, onCompleteListener, onFailureListener);
    }

    @Override
    public void getSpendingKey(ValueEventListener valueEventListener) {
        mRemote.getSpendingKey(valueEventListener);
    }

    @Override
    public void getSpendings(ValueEventListener valueEventListener) {
        mRemote.getSpendings(valueEventListener);
    }

    @Override
    public void deleteSpending(String id,
                               OnCompleteListener onCompleteListener,
                               OnFailureListener onFailureListener) {
        mRemote.deleteSpending(id, onCompleteListener, onFailureListener);
    }
}
