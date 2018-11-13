package com.framgia.moneymanagement.screen.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.repository.AuthenticationRepository;
import com.framgia.moneymanagement.data.source.remote.AuthenticationRemoteDataSource;
import com.framgia.moneymanagement.screen.NavigationDrawerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements
        LoginContract.View, View.OnClickListener {
    private EditText mEditTextUser;
    private EditText mEditTextPassWord;
    private LoginContract.Presenter mPresenter;
    private ProgressDialog mDialogLogin;
    private Toolbar mToolbar;

    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initComponent() {
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_google).setOnClickListener(this);
        mDialogLogin = new ProgressDialog(this);
    }

    private void initViews() {
        mPresenter = new LoginPresenter(new AuthenticationRepository(new AuthenticationRemoteDataSource(
                FirebaseAuth.getInstance(),
                FirebaseDatabase.getInstance())),
                this);
        mEditTextUser = findViewById(R.id.text_username);
        mEditTextPassWord = findViewById(R.id.text_password);
        mToolbar = findViewById(R.id.toolbar_login);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void login(String userName, String password) {
        mPresenter.loginAccount(userName, password);
    }

    @Override
    public void onLoginFail() {
        Toast.makeText(this, R.string.msg_login_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginAccountEmpty() {
        Toast.makeText(this, R.string.msg_login_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginPassfail() {
        Toast.makeText(this, R.string.msg_login_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveUserFail() {
        Toast.makeText(this, R.string.msg_save_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDiaglog() {
        mDialogLogin.setMessage(getString(R.string.login_loading));
        mDialogLogin.show();
    }

    @Override
    public void hideProgressDiaglog() {
        mDialogLogin.dismiss();
    }

    @Override
    public void intentActivity() {
        startActivity(new Intent(this, NavigationDrawerActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                String username = mEditTextUser.getText().toString().trim();
                String password = mEditTextPassWord.getText().toString().trim();
                login(username, password);
                break;
            default:
                break;
        }
    }
}
