package com.framgia.moneymanagement.screen.spending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Spending;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.ViewHolder> {
    private List<Spending> mSpendings;
    private LayoutInflater mLayoutInflater;

    public SpendingAdapter() {
        mSpendings = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        Context context = parent.getContext();
        View view = mLayoutInflater.inflate(R.layout.item_spending, parent, false);
        return new ViewHolder(view, context);
    }

    public void addData(List<Spending> spendings) {
        if (spendings == null) {
            return;
        }
        if (getItemCount() != 0) {
            mSpendings.clear();
        }
        mSpendings.addAll(spendings);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mSpendings.get(position));
    }

    @Override
    public int getItemCount() {
        return mSpendings != null ? mSpendings.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewName, mTextViewDiscription;
        private TextView mTextViewMonthAndYear, mTextViewAmount;
        private Context mContext;
        private CardView mCardView;
        private Spending mSpending;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            mCardView = itemView.findViewById(R.id.cardview_spending);
            mTextViewName = itemView.findViewById(R.id.text_spending_name);
            mTextViewDiscription = itemView.findViewById(R.id.text_spending_description);
            mTextViewAmount = itemView.findViewById(R.id.text_spending_amount);
            mTextViewMonthAndYear = itemView.findViewById(R.id.text_spending_time);
        }

        void bindData(Spending spending) {
            if (spending == null) {
                return;
            }
            mSpending = spending;
            mTextViewName.setText(spending.getGroup());
            mTextViewMonthAndYear.setText(spending.getTime());
            mTextViewDiscription.setText(spending.getDescription());
            DecimalFormat decimalFormat = new DecimalFormat(SpendingKey.DECIMAL_FORMAT);
            mTextViewAmount.setText(decimalFormat.format(Integer.parseInt(spending.getAmount()))
                    + SpendingKey.MONEY_FORMAT);
        }
    }
}
