package com.ahmadrosid.dompetku.main;

import com.ahmadrosid.dompetku.models.Transaction;

import java.util.List;

/**
 * Created by staf on 03-Oct-17.
 */

public interface MainContract {

    interface View {
        void showBalance(int ballance, int expend);
        void showError(String message);
        void showListTransaksi(List<Transaction> transactionses);
    }

    interface Presenter {
        void loadData();
        void addTransaksi(String title, int amount, Transaction.TransactionType type);
        void deleteTransaksi(Transaction transactions);
        void updateTransaksi(Transaction transactions);
    }

    interface ListViewListener {
        void onClickListener(Transaction transactions);
        void onLongClickListener(Transaction transactions);
    }

    interface PopUpListener {
        void success();
        void failed(String message);
    }

}
