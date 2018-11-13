package com.framgia.moneymanagement.data.source.remote;

import com.framgia.moneymanagement.data.AuthenticationDataSource;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource.Remote {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    public AuthenticationRemoteDataSource(FirebaseAuth auth, FirebaseDatabase firebaseDatabase) {
        mAuth = auth;
        mFirebaseDatabase = firebaseDatabase;
    }

    @Override
    public void login(String userName, String password,
                      OnCompleteListener onCompleteListener,
                      OnFailureListener onFailureListener) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void saveUser(User user, OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener) {
        mFirebaseDatabase.getReference(User.Key.USER)
                .child(getCurrentUser().getUid()).setValue(user)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }
}
