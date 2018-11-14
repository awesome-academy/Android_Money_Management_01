package com.framgia.moneymanagement.screen.register;

public interface RegisterContact {
    interface View {
        void onCreateNewAccountFail();

        void intentLoginActivity();

        void showProgressDialog();

        void hideProgressDiaglog();

        void onConfirmPasswordError();

        void onEmptyEmail();

        void onErrorPassword(String msg);

        void onSaveUserFail(String msg);

        void onEmptyConfirmPassword();

        void onEmptyPassWord();
    }

    interface Presenter {
        void createAccount(String userName, String password, String confirmPassword);
    }
}
