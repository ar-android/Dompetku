package com.ahmadrosid.dompetku.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by staf on 04-Oct-17.
 */

public class MainAdapter extends ArrayAdapter<Transaction> {

    private List<Transaction> transactions;

    private MainContract.ListViewListener listViewListener;

    public MainAdapter(@NonNull Context context, @NonNull List<Transaction> transactions, MainContract.ListViewListener listener) {
        super(context, 0, transactions);
        this.transactions = transactions;
        listViewListener = listener;
    }

    @Nullable
    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Transaction transaction = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_wallet_list, parent, false);
        }

        new ViewHolder(convertView, transaction);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewListener.onClickListener(transaction);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listViewListener.onLongClickListener(transaction);
                return true;
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.icon_wallet_list)
        ImageView iconWalletList;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.type)
        TextView type;

        ViewHolder(View view, Transaction transaction) {
            ButterKnife.bind(this, view);

            title.setText(transaction.title);

            Date dates = new Date(transaction.date);
            String date = new SimpleDateFormat("EEE, MMM d, yy").format(dates);
            this.time.setText(date);

            String data = CurrencyHelper.format(transaction.amount);

            if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
                this.amount.setText(" + " + data);
                iconWalletList.setImageResource(R.drawable.wallet_in);
                type.setText("PEMASUKAN");
                type.setBackgroundResource(R.color.colorPrimary);
            } else {
                this.amount.setText(" - " + data);
                iconWalletList.setImageResource(R.drawable.wallet_out);
                type.setText("PENGELUARAN");
                type.setBackgroundResource(R.color.colorAccent);
            }
        }

    }
}
