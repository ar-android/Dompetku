package com.ahmadrosid.dompetku.main;

import com.ahmadrosid.dompetku.data.Ballance;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.models.Transaction;

import java.util.List;

/**
 * Created by staf on 03-Oct-17.
 */

public interface MainContract {

    interface View {
        void showBalance(int ballance);
        void showError(String message);
        void showListTransaksi(List<Transaction> transactionses);
    }

    interface Presenter {
        void loadData();
        void addTransaksi(String title, int amount, Transaction.TransactionType type);
        void deleteTransaksi(Transactions transactions);
        void updateTransaksi(Transactions transactions);
    }

    interface ListViewListener {
        void onClickListener(Transaction transactions);
        void onLongClickListener(Transaction transactions);
    }
}
