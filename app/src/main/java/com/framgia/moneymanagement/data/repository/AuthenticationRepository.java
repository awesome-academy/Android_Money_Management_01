package com.framgia.moneymanagement.data.repository;

import com.framgia.moneymanagement.data.AuthenticationDataSource;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationRepository implements AuthenticationDataSource.Remote {
    private AuthenticationDataSource.Remote mRemote;

    public AuthenticationRepository(AuthenticationDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void login(String userName, String password,
                      OnCompleteListener onCompleteListener,
                      OnFailureListener onFailureListener) {
        mRemote.login(userName, password, onCompleteListener, onFailureListener);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mRemote.getCurrentUser();
    }

    @Override
    public void saveUser(User user, OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener) {
        mRemote.saveUser(user,onCompleteListener,onFailureListener);
    }


}
