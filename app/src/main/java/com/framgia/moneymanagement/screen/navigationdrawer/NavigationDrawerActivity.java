package com.framgia.moneymanagement.screen.navigationdrawer;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.screen.income.IncomeFragment;
import com.framgia.moneymanagement.screen.spending.SpendingFragment;
import com.framgia.moneymanagement.screen.statistics.StatisticsFragment;

public class NavigationDrawerActivity extends AppCompatActivity {
    private Toolbar mToolbarNavigation;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initViews();
        handlerNavigation();
    }

    private void handlerNavigation() {
        replaceFragment(R.string.title_statistics,StatisticsFragment.newInstance());
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_statistics:
                        replaceFragment(R.string.title_statistics, StatisticsFragment.newInstance());
                        break;
                    case R.id.menu_income:
                        replaceFragment(R.string.title_income, IncomeFragment.newInstance());
                        break;
                    case R.id.menu_spending:
                        replaceFragment(R.string.title_spending, SpendingFragment.newInstance());
                        break;
                    case R.id.menu_exit:
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(Gravity.START);
                return false;
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarNavigation);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarNavigation.setNavigationIcon(R.drawable.ic_menu);
        mToolbarNavigation.setTitleTextColor(getResources().getColor(R.color.color_white));
        mToolbarNavigation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    private void initViews() {
        mDrawerLayout = findViewById(R.id.drawer_menu);
        mToolbarNavigation = findViewById(R.id.toolbar_navigation);
        mNavigationView = findViewById(R.id.navigation_mennu);
        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notification:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(int title, Fragment fragment) {
        mToolbarNavigation.setTitle(title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
