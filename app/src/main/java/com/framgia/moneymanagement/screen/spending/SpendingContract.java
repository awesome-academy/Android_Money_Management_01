package com.framgia.moneymanagement.screen.spending;

import com.framgia.moneymanagement.data.model.Spending;

import java.util.List;

public interface SpendingContract {
    interface View {
        void onGetSpendingSucces(List<Spending> list);

        void onGetSpendingFail(String msg);

        void onDeleteSpendingFail(String msg);

        void onDeleteSpendingSuccses();
    }

    interface Presenter {
        void getSpending();

        void deleteSpending(String id);
    }
}
