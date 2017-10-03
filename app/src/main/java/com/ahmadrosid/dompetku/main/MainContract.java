package com.ahmadrosid.dompetku.main;

import com.ahmadrosid.dompetku.data.Ballance;
import com.ahmadrosid.dompetku.data.Transactions;

import java.util.List;

/**
 * Created by staf on 03-Oct-17.
 */

public interface MainContract {

    interface View {
        void showBalance(int ballance);
        void showError(String message);
        void showListTransaksi(List<Transactions> transactionses);
    }

    interface Presenter {
        void loadData();
        void addTransaksi(Transactions transactions);
        void deleteTransaksi(Transactions transactions);
        void updateTransaksi(Transactions transactions);
    }

}
