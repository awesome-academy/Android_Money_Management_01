package com.framgia.moneymanagement.screen.income;

import com.framgia.moneymanagement.data.model.Income;

public interface IncomeAddContract {
    interface View {
        void onCreateIncomeSucsses();

        void onCreateIncomeFail(String msg);

        void onEmptyDescription();

        void onEmptyAmount();

        void onEmptyTime();
    }

    interface Presenter {
        void createIncome(Income income);

        void setCurrenIncome(String id, String group, String description, String amount,
                             String time, String year, String month);
    }
}
