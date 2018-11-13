package com.framgia.moneymanagement.screen.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.moneymanagement.data.model.User;
import com.framgia.moneymanagement.data.repository.AuthenticationRepository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginPresenter implements LoginContract.Presenter,
        OnCompleteListener, OnFailureListener {
    private AuthenticationRepository mAuthenicationRepository;
    private LoginContract.View mView;

    LoginPresenter(AuthenticationRepository authencateRepository, LoginContract.View view) {
        mAuthenicationRepository = authencateRepository;
        mView = view;
    }

    @Override
    public void loginAccount(String email, String password) {
        if (validateData(email, password)) {
            login(email, password);
        } else {
            mView.onLoginAccountEmpty();
        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mView.hideProgressDiaglog();
        if (!task.isSuccessful()) {
            mView.onLoginFail();
            return;
        }
        mAuthenicationRepository.saveUser(getCurrentUser(),
                new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            mView.onSaveUserFail();
                            return;
                        }
                        mView.intentActivity();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.onSaveUserFail();
                    }
                });
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            mView.onLoginFail();
        }
        if (e instanceof FirebaseAuthInvalidUserException) {
            mView.onLoginPassfail();
        }
        if (e instanceof FirebaseAuthWeakPasswordException) {
            mView.onLoginPassfail();
        }
    }

    private void login(String email, String password) {
        mAuthenicationRepository.login(email, password,
                this, this);
        mView.showProgressDiaglog();
    }

    private boolean validateData(String email, String password) {
        if (!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))) {
            return true;
        }
        return false;
    }

    private User getCurrentUser() {
        return new User(FirebaseAuth.getInstance().
                getCurrentUser().getUid(), null, null, null);
    }
}
