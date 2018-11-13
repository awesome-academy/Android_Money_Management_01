package com.framgia.moneymanagement.screen.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.framgia.moneymanagement.data.DataCallback;
import com.framgia.moneymanagement.data.model.User;
import com.framgia.moneymanagement.data.repository.AuthenticationRepository;

import com.framgia.moneymanagement.screen.NavigationDrawerActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginContract.Presenter,
        OnCompleteListener, OnFailureListener {
    private AuthenticationRepository mAuthenicationRepository;
    private LoginContract.View mView;
    private DataCallback<FirebaseUser> mCallback;

    LoginPresenter(AuthenticationRepository authencateRepository, LoginContract.View view) {
        mAuthenicationRepository = authencateRepository;
        mView = view;
        initSignInCallback();
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
    public void getAccountWithGoogle(int requestCode, Intent data) {
        if (requestCode == LoginKey.REQUEST_CODE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            loginWithGoogle(task);
        }
    }

    @Override
    public void loginWithGoogle(Task<GoogleSignInAccount> task) {
        try {
            mAuthenicationRepository.loginWithGoogle(task.getResult(ApiException.class),
                    this,
                    this);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginWithFacebook(AccessToken token) {
        mView.showProgressDiaglog();
        mAuthenicationRepository.loginWithFacebook(token, mCallback);
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
        try {
            GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
            if (account == null) {
                mAuthenicationRepository.saveUser(getCurrentUser(), new OnCompleteListener() {
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
            } else {
                return;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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

    private void initSignInCallback() {
        mCallback = new DataCallback<FirebaseUser>() {
            @Override
            public void onGetDataSucces(FirebaseUser data) {
                if (data == null) {
                    return;
                }
                mView.hideProgressDiaglog();
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
            public void onGetDataFailed(String msg) {
                mView.onGetDataFail(msg);
            }
        };
    }
}
