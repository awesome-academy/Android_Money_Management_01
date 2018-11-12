package com.framgia.moneymanagement.screen.screen.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moneymanagement.R;

public class LoginActivity extends AppCompatActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
