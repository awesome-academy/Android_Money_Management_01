package com.framgia.moneymanagement.data.source.remote;

import com.framgia.moneymanagement.data.SpendingDataSource;
import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpendingRemoteDataSource implements SpendingDataSource.Remote {
    private FirebaseDatabase mFirebaseDatabase;

    public SpendingRemoteDataSource(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
    }

    @Override
    public void createSpending(Spending spending, OnCompleteListener onCompleteListener,
                               OnFailureListener onFailureListener) {
        DatabaseReference reference = mFirebaseDatabase.getReference().child(User.Key.USER);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Spending.Key.SPENDING)
                .child(spending.getId())
                .setValue(spending)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void getSpendingKey(ValueEventListener valueEventListener) {
        mFirebaseDatabase.getReference(Spending.Key.SPENDING_KEY)
                .addValueEventListener(valueEventListener);
    }
}
