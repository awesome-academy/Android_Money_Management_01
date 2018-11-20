package com.framgia.moneymanagement.data;

import com.framgia.moneymanagement.data.model.Income;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

public interface IncomeDataSource {
    interface Remote {
        void createIncome(Income income,
                          OnCompleteListener onCompleteListener,
                          OnFailureListener onFailureListener);

        void getIncomes(ValueEventListener valueEventListener);

        void deleteIncome(String id, OnCompleteListener onCompleteListener,
                          OnFailureListener onFailureListener);
    }
}
