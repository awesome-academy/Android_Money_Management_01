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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.repository.AuthenticationRepository;
import com.framgia.moneymanagement.data.source.remote.AuthenticationRemoteDataSource;
import com.framgia.moneymanagement.screen.NavigationDrawerActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements
        LoginContract.View, View.OnClickListener {
    private EditText mEditTextUser;
    private EditText mEditTextPassWord;
    private LoginContract.Presenter mPresenter;
    private ProgressDialog mDialogLogin;
    private Toolbar mToolbar;
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;

    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        initViews();
        initFacebookSDK();
    }

    private void initFacebookSDK() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions(LoginKey.FACEBOOK_PERMISIONS);
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult == null) return;
                AccessToken accessToken = loginResult.getAccessToken();
                loginWithFacebook(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initComponent() {
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_google).setOnClickListener(this);
        mDialogLogin = new ProgressDialog(this);
        findViewById(R.id.button_facebook).setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    private void initViews() {
        mPresenter = new LoginPresenter(new AuthenticationRepository(new AuthenticationRemoteDataSource(
                FirebaseAuth.getInstance(),
                FirebaseDatabase.getInstance())),
                this);
        mEditTextUser = findViewById(R.id.text_username);
        mEditTextPassWord = findViewById(R.id.text_password);
        mToolbar = findViewById(R.id.toolbar_login);
        mLoginButton = findViewById(R.id.button_facebook);
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
    public void onGetDataFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
    public void loginWithFacebook(AccessToken token) {
        mPresenter.loginWithFacebook(token);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                String username = mEditTextUser.getText().toString().trim();
                String password = mEditTextPassWord.getText().toString().trim();
                login(username, password);
                break;
            case R.id.button_google:
                showProgressDiaglog();
                loginWithGoogle();
                break;
            default:
                break;
        }
    }

    private void loginWithGoogle() {
        GoogleSignInOptions mSign = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.server_client_id)).
                requestEmail().
                build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, mSign);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LoginKey.REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getAccountWithGoogle(requestCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
