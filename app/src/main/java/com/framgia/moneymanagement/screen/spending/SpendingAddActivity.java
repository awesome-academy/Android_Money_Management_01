package com.framgia.moneymanagement.screen.spending;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.framgia.moneymanagement.data.model.Spending;
import com.framgia.moneymanagement.data.repository.SpendingRepository;
import com.framgia.moneymanagement.data.source.remote.SpendingRemoteDataSource;
import com.framgia.moneymanagement.screen.income.IncomeAddActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SpendingAddActivity extends AppCompatActivity implements
        SpendingAddContract.View, View.OnClickListener {
    private Spinner mSpinnerGroup;
    private SpendingAddContract.Presenter mPresenter;
    private EditText mEditTextNote, mEditTextAmount, mEditTextCalendar;
    private TextView mTextViewTime, mTextViewTitle;
    private Toolbar mToolbar;
    private Calendar mCalendar;
    private SimpleDateFormat mSimpleDateFormat;

    public static Intent getIntent(Context context) {
        return new Intent(context, SpendingAddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_add);
        initViews();
    }

    private void initViews() {
        mPresenter = new SpendingAddPresenter(new SpendingRepository(
                new SpendingRemoteDataSource(FirebaseDatabase.getInstance())), this);
        mEditTextNote = findViewById(R.id.text_spending_note);
        mEditTextAmount = findViewById(R.id.text_spending_amount);
        mEditTextCalendar = findViewById(R.id.text_spending_calendar);
        mSpinnerGroup = findViewById(R.id.spinner_spending_group);
        mToolbar = findViewById(R.id.toolbar_spending_add);
        mTextViewTime = findViewById(R.id.text_spending_calendar);
        mTextViewTitle = findViewById(R.id.text_view_title_action_bar);
        mSimpleDateFormat = new SimpleDateFormat(SpendingKey.DATE_FORMAT);
        mCalendar = Calendar.getInstance();
        mTextViewTime.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        initToolbar();
        mEditTextCalendar.setOnClickListener(this);
        mPresenter.getSpendingKey();
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
        mTextViewTitle = view.findViewById(R.id.text_view_title_action_bar);
        mTextViewTitle.setText(R.string.title_spending);
        getSupportActionBar().setCustomView(view, params);
    }

    public void getTimeDialog() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int date = mCalendar.get(Calendar.DATE);
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mCalendar.set(i, i1, i2);
                        mEditTextCalendar.setText(mSimpleDateFormat.format(mCalendar.getTime()));
                    }
                }, year, month, date);
        dialog.show();
    }

    private void addDataSpinner(List<String> arraySpinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGroup.setAdapter(adapter);
    }

    @Override
    public void onCreateSpendingSucsses() {
        Toast.makeText(this, R.string.msg_income_create_succces, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateSpendingFail(String msg) {
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
    public void onGetSpendingKey(List<String> spendingKeys) {
        addDataSpinner(spendingKeys);
    }

    @Override
    public void onGetSpendingFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
                String note = mEditTextNote.getText().toString();
                String amount = mEditTextAmount.getText().toString();
                DatabaseReference reference =
                        FirebaseDatabase.getInstance().getReference(Spending.Key.SPENDING);
                String id = reference.push().getKey();
                String year = String.valueOf(mCalendar.get(Calendar.YEAR));
                String month = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
                Spending spending = new Spending(id, group, note, amount, time, year, month);
                mPresenter.createSpending(spending);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_spending_calendar:
                getTimeDialog();
                break;
            default:
                break;
        }
    }
}
