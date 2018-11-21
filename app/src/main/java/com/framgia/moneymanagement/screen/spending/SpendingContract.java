package com.framgia.moneymanagement.screen.spending;

import com.framgia.moneymanagement.data.model.Spending;

import java.util.List;

public interface SpendingContract {
    interface View {
        void onGetSpendingSucces(List<Spending> list);

        void onGetSpendingFail(String msg);
    }

    interface Presenter {
        void getSpending();
    }
}
