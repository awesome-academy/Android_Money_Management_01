package com.framgia.moneymanagement.screen.income;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private OnItemLongClickListener mListener;

    public IncomeAdapter(OnItemLongClickListener listener) {
        mIncomes = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        Context context = parent.getContext();
        View view = mLayoutInflater.inflate(R.layout.item_income, parent, false);
        return new ViewHolder(context, view, mListener);
    }

    public void setData(List<Income> incomes) {
        if (incomes == null) {
            return;
        }
        if (getItemCount() != 0) {
            mIncomes.clear();
        }
        mIncomes.addAll(incomes);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mIncomes.get(position));
    }

    @Override
    public int getItemCount() {
        return mIncomes != null ? mIncomes.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView mTextViewName, mTextViewDescription;
        private TextView mTextViewAmount;
        private TextView mTextViewMonthAndYear;
        private Context mContext;
        private CardView mCardView;
        private Income mIncome;
        private OnItemLongClickListener mListener;

        ViewHolder(Context context, View itemView, OnItemLongClickListener listener) {
            super(itemView);
            mContext = context;
            mListener = listener;
            mTextViewName = itemView.findViewById(R.id.text_income_name);
            mTextViewAmount = itemView.findViewById(R.id.text_income_amount);
            mTextViewDescription = itemView.findViewById(R.id.text_incnome_discription);
            mTextViewMonthAndYear = itemView.findViewById(R.id.text_income_month_and_year);
            mCardView = itemView.findViewById(R.id.cardview);
            mCardView.setOnLongClickListener(this);
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

        @Override
        public boolean onLongClick(View view) {
            mListener.OnLongClickListener(mIncome.getId());
            return false;
        }
    }

    public interface OnItemLongClickListener {
        void OnLongClickListener(String id);
    }
}
