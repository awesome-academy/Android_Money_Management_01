package com.framgia.moneymanagement.data.source.remote;

import com.framgia.moneymanagement.data.IncomeDataSource;
import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IncomeRemoteDataSource implements IncomeDataSource.Remote {
    private FirebaseDatabase mFirebaseDatabase;

    public IncomeRemoteDataSource(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
    }

    @Override
    public void createIncome(Income income,
                             OnCompleteListener onCompleteListener,
                             OnFailureListener onFailureListener) {
        DatabaseReference reference = mFirebaseDatabase.getReference(User.Key.USER);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(Income.Key.INCOME).
                child(income.getId()).
                setValue(income).
                addOnCompleteListener(onCompleteListener).
                addOnFailureListener(onFailureListener);
    }

    @Override
    public void getIncomes(ValueEventListener valueEventListener) {
        mFirebaseDatabase.getReference(User.Key.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Income.Key.INCOME)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void deleteIncome(String id,
                             OnCompleteListener onCompleteListener,
                             OnFailureListener onFailureListener) {
        mFirebaseDatabase.getReference(User.Key.USER)
                .child(FirebaseAuth.getInstance().getUid())
                .child(Income.Key.INCOME)
                .child(id)
                .removeValue()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }
}
