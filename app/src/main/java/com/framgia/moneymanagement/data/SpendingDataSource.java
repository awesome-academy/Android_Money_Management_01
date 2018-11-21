package com.framgia.moneymanagement.data;

import com.framgia.moneymanagement.data.model.Spending;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

public interface SpendingDataSource {
    interface Remote {
        void createSpending(Spending spending, OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener);

        void getSpendingKey(ValueEventListener valueEventListener);

        void getSpendings(ValueEventListener valueEventListener);

        void deleteSpending(String id, OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener);
    }
}
