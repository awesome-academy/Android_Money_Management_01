package com.framgia.moneymanagement.screen.spending;

import com.framgia.moneymanagement.data.model.Spending;

import java.util.List;

public interface SpendingAddContract {
    interface View {
        void onCreateSpendingSucsses();

        void onCreateSpendingFail(String msg);

        void onEmptyDescription();

        void onEmptyAmount();

        void onEmptyTime();

        void onGetSpendingKey(List<String> strings);

        void onGetSpendingFail(String msg);
    }

    interface Presenter {
        void createSpending(Spending spending);

        boolean isValidate(Spending spending);

        void getSpendingKey();
    }
}
