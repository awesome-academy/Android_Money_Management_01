package com.framgia.moneymanagement.screen.income;

import com.framgia.moneymanagement.data.model.Income;

import java.util.List;

public interface IncomeContact {
    interface Presenter {
        void getIncome();

        void deleteIncome(String id);
    }

    interface View {
        void onGetDataSucces(List<Income> incomes);

        void onGetDataFail(String msg);

        void deleteIncomeSucces();

        void deleteIncomeFail(String msg);

        void getData();
    }
}
