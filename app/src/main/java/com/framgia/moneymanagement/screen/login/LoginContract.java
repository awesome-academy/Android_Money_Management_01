package com.framgia.moneymanagement.screen.login;

public interface LoginContract {
    interface View {
        void login(String userName, String password);

        void onLoginFail();

        void onLoginAccountEmpty();

        void onLoginPassfail();

        void onSaveUserFail();

        void showProgressDiaglog();

        void hideProgressDiaglog();

        void intentActivity();
    }

    interface Presenter {
        void loginAccount(String userName, String password);
    }
}
