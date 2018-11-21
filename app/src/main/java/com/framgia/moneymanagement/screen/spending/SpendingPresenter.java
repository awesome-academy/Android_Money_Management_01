package com.framgia.moneymanagement.screen.spending;

import android.support.annotation.NonNull;

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

public class SpendingPresenter implements SpendingContract.Presenter {
    private SpendingRepository mRepository;
    private SpendingContract.View mView;

    public SpendingPresenter(SpendingRepository repository, SpendingContract.View View) {
        mRepository = repository;
        mView = View;
    }

    @Override
    public void getSpending() {
        mRepository.getSpendings(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Spending> spendingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Spending spending = snapshot.getValue(Spending.class);
                    spendingList.add(spending);
                }
                mView.onGetSpendingSucces(spendingList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSpendingFail(databaseError.getMessage());
            }
        });
    }
}
