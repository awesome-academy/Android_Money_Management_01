package com.framgia.moneymanagement.screen.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.screen.login.LoginActivity;
import com.framgia.moneymanagement.screen.register.RegisterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                startActivity(LoginActivity.getIntent(this));
                break;
            case R.id.button_register:
                startActivity(RegisterActivity.getIntent(this));
                break;
            default:
                break;
        }
    }
}
