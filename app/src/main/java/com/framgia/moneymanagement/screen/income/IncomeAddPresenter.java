package com.framgia.moneymanagement.screen.income;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.repository.IncomeRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

public class IncomeAddPresenter implements IncomeAddContract.Presenter, OnCompleteListener, OnFailureListener {
    private IncomeAddContract.View mView;
    private IncomeRepository mIncomeRepository;

    public IncomeAddPresenter(IncomeAddContract.View view, IncomeRepository incomeRepository) {
        mView = view;
        mIncomeRepository = incomeRepository;
    }

    @Override
    public void createIncome(Income income) {
        mIncomeRepository.createIncome(income, this, this);
    }

    @Override
    public void setCurrenIncome(String id, String group, String description, String amount,
                                String time, String year, String month) {
        if (ivaliDate(description, amount, time)) {
            createIncome(new Income(id, group, description, amount, time, year, month));
        }
    }

    private boolean ivaliDate(String description, String amount, String time) {
        if (TextUtils.isEmpty(description)) {
            mView.onEmptyDescription();
            return false;
        }
        if (TextUtils.isEmpty(amount)) {
            mView.onEmptyAmount();
            return false;
        }
        if (TextUtils.isEmpty(time)) {
            mView.onEmptyTime();
            return false;
        }
        return true;
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            mView.onCreateIncomeSucsses();
        }
    }


    @Override
    public void onFailure(@NonNull Exception e) {
        mView.onCreateIncomeFail(e.getMessage());
    }
}
