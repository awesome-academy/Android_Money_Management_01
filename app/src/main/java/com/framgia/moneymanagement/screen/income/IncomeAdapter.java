package com.framgia.moneymanagement.screen.income;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Income;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {
    private List<Income> mIncomes;
    private LayoutInflater mLayoutInflater;

    public IncomeAdapter() {
        mIncomes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        Context context = parent.getContext();
        View view = mLayoutInflater.inflate(R.layout.item_income, parent, false);
        return new ViewHolder(context, view);
    }

    public void setData(List<Income> incomes) {
        if (incomes == null) {
            return;
        }
        if (getItemCount() != 0) {
            mIncomes.clear();
        }
        mIncomes.addAll(incomes);
        notifyItemRangeChanged(0, getItemCount() - 1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mIncomes.get(position));
    }

    @Override
    public int getItemCount() {
        return mIncomes != null ? mIncomes.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewName, mTextViewDescription;
        private TextView mTextViewAmount;
        private TextView mTextViewMonthAndYear;
        private Context mContext;
        private CardView mCardView;
        private Income mIncome;

        ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTextViewName = itemView.findViewById(R.id.text_income_name);
            mTextViewAmount = itemView.findViewById(R.id.text_income_amount);
            mTextViewDescription = itemView.findViewById(R.id.text_incnome_discription);
            mTextViewMonthAndYear = itemView.findViewById(R.id.text_income_month_and_year);
            mCardView = itemView.findViewById(R.id.cardview);
        }

        void bindData(Income income) {
            if (income == null) {
                return;
            }
            mIncome = income;
            mTextViewName.setText(income.getGroup());
            mTextViewDescription.setText(income.getDescription());
            DecimalFormat decimalFormat = new DecimalFormat(IncomeKey.DECIMAL_FORMAT);
            mTextViewMonthAndYear.setText(income.getTime());
            mTextViewAmount.setText(decimalFormat.format(Integer.parseInt(income.getAmount())) + IncomeKey.MONEY_FORMAT);
        }
    }
}
