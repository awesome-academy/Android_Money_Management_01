package com.framgia.moneymanagement.screen.spending;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.repository.SpendingRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpendingAddPresenter implements SpendingAddContract.Presenter, OnCompleteListener, OnFailureListener {
    private SpendingRepository mRepository;
    private SpendingAddContract.View mView;

    public SpendingAddPresenter(SpendingRepository repository, SpendingAddContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void createSpending(Spending spending) {
        if (isValidate(spending)) {
            mRepository.createSpending(spending, this, this);
        }
    }

    @Override
    public boolean isValidate(Spending spengding) {
        if (TextUtils.isEmpty(spengding.getDescription())) {
            mView.onEmptyDescription();
            return false;
        }
        if (TextUtils.isEmpty(spengding.getAmount())) {
            mView.onEmptyAmount();
            return false;
        }
        if (TextUtils.isEmpty(spengding.getTime())) {
            mView.onEmptyTime();
            return false;
        }
        return true;
    }

    @Override
    public void getSpendingKey() {
        mRepository.getSpendingKey(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> strings = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String string = snapshot.getValue().toString();
                    strings.add(string);
                }
                mView.onGetSpendingKey(strings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSpendingFail(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            mView.onCreateSpendingSucsses();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        mView.onCreateSpendingFail(e.getMessage());
    }
}
