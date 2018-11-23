package com.framgia.moneymanagement.screen.statistics;

import android.support.annotation.NonNull;

import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.repository.IncomeRepository;
import com.framgia.moneymanagement.data.repository.SpendingRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BaseStatisticsThisMonthPresenter extends BaseStatisticsPresenter {
    private IncomeRepository mIncomeRepository;
    private SpendingRepository mSpendingRepository;
    private StatisticsContract.View mView;
    private int mSumIncomeThisMonth;

    public BaseStatisticsThisMonthPresenter(IncomeRepository incomeRepository, SpendingRepository spendingRepository, StatisticsContract.View view) {
        mIncomeRepository = incomeRepository;
        mSpendingRepository = spendingRepository;
        mView = view;
    }

    @Override
    public void getDataIncome() {
        mIncomeRepository.getIncomes(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Income> incomes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Income income = snapshot.getValue(Income.class);
                    if (isYear(income) && isMonth(income)) {
                        mSumIncomeThisMonth += Integer.parseInt(income.getAmount());
                        incomes.add(income);
                    }
                }
                mView.onGetSumIncomeSuccess(mSumIncomeThisMonth);
                mView.peaChartArtIncome(incomes);
                getDataSpending();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSumIncomeFail(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getDataSpending() {
        mSpendingRepository.getSpendings(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sumSpending = 0;
                List<Spending> spendings = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Spending spending = snapshot.getValue(Spending.class);
                    if (isYear(spending) && isMonth(spending)) {
                        sumSpending += Integer.parseInt(spending.getAmount());
                        spendings.add(spending);
                    }
                }
                mView.onGetSumSpendingSuccess(sumSpending);
                mView.peaChartArtSpending(spendings);
                int sub = mSumIncomeThisMonth - sumSpending;
                if (isIvalidate(sub)) {
                    mView.onSumSalaryGain(sub);
                    return;
                }
                mView.onSumSalaryReduce(sub);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSumSpendingFail(databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean isMonth(Spending spending) {
        return spending.getMonth().equals(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
    }

    @Override
    public boolean isMonth(Income income) {
        return income.getMonth().equals(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
    }

    @Override
    public boolean isYear(Spending spending) {
        return spending.getYear().equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }

    @Override
    public boolean isYear(Income income) {
        return income.getYear().equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }

    @Override
    public boolean isIvalidate(int sub) {
        return sub >= 0;
    }
}
