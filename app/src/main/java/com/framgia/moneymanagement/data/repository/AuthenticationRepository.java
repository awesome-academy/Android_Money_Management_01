package com.framgia.moneymanagement.data.repository;

import com.facebook.AccessToken;
import com.framgia.moneymanagement.data.AuthenticationDataSource;
import com.framgia.moneymanagement.data.DataCallback;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
        mRemote.saveUser(user, onCompleteListener, onFailureListener);
    }

    @Override
    public void loginWithGoogle(GoogleSignInAccount account,
                                OnCompleteListener onCompleteListener,
                                OnFailureListener onFailureListener) {
        mRemote.loginWithGoogle(account,
                onCompleteListener,
                onFailureListener);
    }

    @Override
    public void loginWithFacebook(AccessToken token, DataCallback<FirebaseUser> callback) {
        mRemote.loginWithFacebook(token, callback);
    }

    @Override
    public void createAccount(String userName, String password,
                             OnCompleteListener onCompleteListener,
                             OnFailureListener onFailureListener) {
        mRemote.createAccount(userName, password, onCompleteListener, onFailureListener);
    }
}
