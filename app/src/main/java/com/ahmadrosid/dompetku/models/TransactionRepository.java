package com.ahmadrosid.dompetku.models;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by staf on 04-Oct-17.
 */

public class TransactionRepository {

    public void addTransaksi(String title, int amount, Transaction.TransactionType type, TransactionListener listener) {
        long date = System.currentTimeMillis();
        Transaction transaction = new Transaction(title, amount, date, type);
        if (transaction.save() > 0) {
            listener.success();
        } else {
            listener.failed("Error insert data");
        }
    }

    public void addPemasukan(String title, int amount, TransactionListener listener) {
        addTransaksi(title, amount, Transaction.TransactionType.PEMASUKAN, listener);
    }

    public void addPengeluaran(String title, int amount, TransactionListener listener) {
        addTransaksi(title, amount, Transaction.TransactionType.PENGELUARAN, listener);
    }

    public List<Transaction> getTransaksiList() {
        return new Select().from(Transaction.class).execute();
    }

}
