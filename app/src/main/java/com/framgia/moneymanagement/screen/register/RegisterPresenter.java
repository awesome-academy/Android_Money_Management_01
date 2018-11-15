package com.framgia.moneymanagement.screen.register;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.moneymanagement.data.model.User;
import com.framgia.moneymanagement.data.repository.AuthenticationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterPresenter implements RegisterContact.Presenter,
        OnCompleteListener, OnFailureListener {
    private AuthenticationRepository mAuthencateRepository;
    private RegisterContact.View mView;

    public RegisterPresenter(AuthenticationRepository authencateRepository, RegisterContact.View view) {
        mAuthencateRepository = authencateRepository;
        mView = view;
    }

    @Override
    public void createAccount(String userName, String password, String confirmPassword) {
        if (validateData(userName, password, confirmPassword)) {
            mAuthencateRepository.createAccount(userName, password,
                    this, this);
            mView.showProgressDialog();
        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mView.hideProgressDiaglog();
        if (!task.isSuccessful()) {
            mView.onCreateNewAccountFail();
            return;
        }
        mAuthencateRepository.saveUser(getCurrentUser(),
                new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        mView.intentLoginActivity();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.onSaveUserFail(e.getMessage());
                    }
                });
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseAuthWeakPasswordException) {
            mView.onErrorPassword(e.getMessage());
        }
    }

    private User getCurrentUser() {
        return new User(FirebaseAuth.getInstance().
                getCurrentUser().getUid(), null, null, null);
    }

    private boolean validateData(String email, String password, String confirmPassword) {
        mView.hideProgressDiaglog();
        if (TextUtils.isEmpty(email)) {
            mView.onEmptyEmail();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mView.onEmptyPassWord();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            mView.onEmptyConfirmPassword();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            mView.onConfirmPasswordError();
            return false;
        }
        return true;
    }
}
