package com.framgia.moneymanagement.screen.statistics;

import com.framgia.moneymanagement.data.model.Income;
import com.framgia.moneymanagement.data.model.Spending;

public abstract class BaseStatisticsPresenter {
    public abstract void getDataIncome();

    public abstract void getDataSpending();

    public abstract boolean isMonth(Spending spending);

    public abstract boolean isMonth(Income income);

    public abstract boolean isYear(Spending spending);

    public abstract boolean isYear(Income income);

    public abstract boolean isIvalidate(int sub);
}
