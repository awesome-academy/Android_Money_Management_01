package com.framgia.moneymanagement.screen.statistics;

import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.model.Spending;

import java.util.List;

public interface StatisticsContract {
    interface View {
        void onGetSumIncomeSuccess(int sumIncome);

        void onGetSumIncomeFail(String msg);

        void onGetSumSpendingSuccess(int sumSpending);

        void onGetSumSpendingFail(String msg);

        void onSumSalaryGain(int sub);

        void onSumSalaryReduce(int sub);

        void peaChartArtIncome(List<Income> incomes);

        void peaChartArtSpending(List<Spending> spendings);
    }
}
