package com.framgia.moneymanagement.screen.income;

import android.support.annotation.NonNull;

import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.repository.IncomeRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IncomePresenter implements IncomeContact.Presenter {
    private IncomeContact.View mView;
    private IncomeRepository mIncomeRepository;

    public IncomePresenter(IncomeContact.View view, IncomeRepository incomeRepository) {
        mView = view;
        mIncomeRepository = incomeRepository;
    }

    @Override
    public void getIncome() {
        mIncomeRepository.getIncomes(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Income> incomeList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Income income = snapshot.getValue(Income.class);
                    incomeList.add(income);
                }
                mView.onGetDataSucces(incomeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetDataFail(databaseError.getMessage());
            }
        });
    }
}
