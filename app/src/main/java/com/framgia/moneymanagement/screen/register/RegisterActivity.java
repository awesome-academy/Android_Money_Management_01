package com.framgia.moneymanagement.screen.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.repository.AuthenticationRepository;
import com.framgia.moneymanagement.data.source.remote.AuthenticationRemoteDataSource;
import com.framgia.moneymanagement.screen.login.LoginActivity;
import com.framgia.moneymanagement.screen.login.LoginPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements
        RegisterContact.View, View.OnClickListener {
    private ProgressDialog mDialog;
    private EditText mEditTextUserName, mEditTextPassword, mEditTextConfirmPassword;
    private RegisterContact.Presenter mPresenter;
    private Toolbar mToolbar;
    private TextView mTextViewTitile;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        initViews();
    }

    private void initViews() {
        mEditTextUserName = findViewById(R.id.text_register_username);
        mEditTextPassword = findViewById(R.id.text_register_password);
        mEditTextConfirmPassword = findViewById(R.id.text_reigister_confirm_pass);
        mToolbar = findViewById(R.id.toolbar_register);
        initToolbar();
        mPresenter = new RegisterPresenter(new AuthenticationRepository(new AuthenticationRemoteDataSource(
                FirebaseAuth.getInstance(),
                FirebaseDatabase.getInstance())),
                this);
        findViewById(R.id.button_register).setOnClickListener(this);
        mDialog = new ProgressDialog(this);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_action_bar_titile, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mTextViewTitile = view.findViewById(R.id.text_view_title_action_bar);
        mTextViewTitile.setText(R.string.title_register);
        getSupportActionBar().setCustomView(view, params);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onCreateNewAccountFail() {
        Toast.makeText(this, R.string.msg_register_request, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void intentLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showProgressDialog() {
        mDialog.setMessage(getString(R.string.login_loading));
        mDialog.show();
    }

    @Override
    public void hideProgressDiaglog() {
        mDialog.dismiss();
    }

    @Override
    public void onConfirmPasswordError() {
        Toast.makeText(this, R.string.registration_confirm_password_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyEmail() {
        Toast.makeText(this, R.string.registration_empty_username, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorPassword(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveUserFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyConfirmPassword() {
        Toast.makeText(this, R.string.registration_empty_confirm_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyPassWord() {
        Toast.makeText(this, R.string.registration_empty_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                showProgressDialog();
                String userName = mEditTextUserName.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                String confirmPassword = mEditTextConfirmPassword.getText().toString().trim();
                mPresenter.createAccount(userName, password, confirmPassword);
                break;
        }
    }
}
