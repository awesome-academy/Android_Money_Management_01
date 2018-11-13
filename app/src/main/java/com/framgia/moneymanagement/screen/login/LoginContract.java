package com.framgia.moneymanagement.screen.login;

import android.content.Intent;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public interface LoginContract {
    interface View {
        void login(String userName, String password);

        void onLoginFail();

        void onLoginAccountEmpty();

        void onLoginPassfail();

        void onSaveUserFail();

        void onGetDataFail(String msg);

        void showProgressDiaglog();

        void hideProgressDiaglog();

        void intentActivity();

        void loginWithFacebook(AccessToken token);
    }

    interface Presenter {
        void loginAccount(String userName, String password);

        void getAccountWithGoogle(int requestCode, Intent data);

        void loginWithGoogle(Task<GoogleSignInAccount> task);

        void loginWithFacebook(AccessToken token);
    }
}
