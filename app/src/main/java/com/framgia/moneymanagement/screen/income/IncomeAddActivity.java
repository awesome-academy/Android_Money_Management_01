package com.framgia.moneymanagement.screen.income;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.repository.IncomeRepository;
import com.framgia.moneymanagement.data.source.remote.IncomeRemoteDataSource;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IncomeAddActivity extends AppCompatActivity implements IncomeAddContract.View {
    private EditText mEditTextAmount, mEditTextDescription, mEditTextCalendar;
    private TextView mTextViewTime;
    private Spinner mSpinnerGroup;
    private TextView mTextViewTitile;
    private Calendar mCalendar;
    private IncomeAddContract.Presenter mPresenter;
    private SimpleDateFormat mSimpleDateFormat;
    private Toolbar mToolbar;

    public static Intent getIntent(Context context) {
        return new Intent(context, IncomeAddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_add);
        initViews();
    }

    private void initViews() {
        mPresenter = new IncomeAddPresenter(this, new IncomeRepository(
                new IncomeRemoteDataSource(FirebaseDatabase.getInstance())));
        mEditTextCalendar = findViewById(R.id.text_income_calendar);
        mEditTextAmount = findViewById(R.id.text_income_amount);
        mTextViewTitile = findViewById(R.id.text_view_title_action_bar);
        mTextViewTime = findViewById(R.id.text_income_calendar);
        mSpinnerGroup = findViewById(R.id.spinner_income_group);
        mSimpleDateFormat = new SimpleDateFormat(IncomeKey.DATE_FORMAT);
        mToolbar = findViewById(R.id.toolbar_income_add);
        mCalendar = Calendar.getInstance();
        mTextViewTime.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        addDataSpinner();
        mEditTextDescription = findViewById(R.id.text_income_note);
        mEditTextCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetTimeDialog();
            }
        });
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_action_bar_titile, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mTextViewTitile = view.findViewById(R.id.text_view_title_action_bar);
        mTextViewTitile.setText(R.string.title_add_income);
        getSupportActionBar().setCustomView(view, params);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onGetTimeDialog() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int date = mCalendar.get(Calendar.DATE);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mCalendar.set(i, i1, i2);
                mEditTextCalendar.setText(mSimpleDateFormat.format(mCalendar.getTime()));
            }
        }, year, month, date);
        dialog.show();
    }

    private void addDataSpinner() {
        String[] arraySpinner = new String[]{IncomeKey.BONUS, IncomeKey.INTEREST,
                IncomeKey.SALARY, IncomeKey.AWARDED, IncomeKey.SELLING, IncomeKey.DIFFERENT};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGroup.setAdapter(adapter);
    }

    @Override
    public void onCreateIncomeSucsses() {
        Toast.makeText(this, R.string.msg_income_create_succces, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateIncomeFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyDescription() {
        Toast.makeText(this, R.string.msg_income_description, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyAmount() {
        Toast.makeText(this, R.string.msg_income_amount, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyTime() {
        Toast.makeText(this, R.string.msg_income_time, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                String time = mSimpleDateFormat.format(mCalendar.getTime());
                String group = mSpinnerGroup.getSelectedItem().toString();
                String description = mEditTextDescription.getText().toString();
                String amount = mEditTextAmount.getText().toString();
                DatabaseReference reference =
                        FirebaseDatabase.getInstance().getReference(Income.Key.INCOME);
                String id = reference.push().getKey();
                String year = String.valueOf(mCalendar.get(Calendar.YEAR));
                String month = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
                mPresenter.setCurrenIncome(id, group, description, amount, time, year, month);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
