package com.framgia.moneymanagement.screen.register;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.moneymanagement.R;

public class RegisterActivity extends AppCompatActivity {

    public static Intent getIntent(Context context){
        return new Intent(context,RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
    }
}
