package com.framgia.moneymanagement.screen.income;

import com.framgia.moneymanagement.data.model.Income;

import java.util.List;

public interface IncomeContact {
    interface Presenter {
        void getIncome();
    }

    interface View {
        void onGetDataSucces(List<Income> listIncome);

        void onGetDataFail(String msg);
    }
}
