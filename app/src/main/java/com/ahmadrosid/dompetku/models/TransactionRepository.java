package com.ahmadrosid.dompetku.models;

import android.util.Log;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.ahmadrosid.dompetku.transaction.TransactionContract;

import java.util.List;

/**
 * Created by staf on 04-Oct-17.
 */

public class TransactionRepository {

    public void addTransaksi(String title, int amount, Transaction.TransactionType type, TransactionContract.AddTransactionListener listener) {
        long date = System.currentTimeMillis();
        Transaction transaction = new Transaction(title, amount, date, type);
        if (transaction.save() > 0) {
            listener.success(transaction);
        } else {
            listener.failed("Error insert data");
        }
    }

    public List<Transaction> getTransaksiGroupBy() {
        return new Select().from(Transaction.class).groupBy("Title").orderBy("id desc").execute();
    }

    public List<Transaction> getTransaksiList(long start, long end) {
        From query = new Select().from(Transaction.class)
                .where("Date >= " + start + " AND Date <= " + end)
                .orderBy("id desc");

        Log.e("QUERY", query.toSql());

        return query.execute();
    }

    public List<Transaction> getTransaksiList() {
        return new Select().from(Transaction.class).orderBy("id desc").execute();
    }

    public Transaction getTransaksi(long id) {
        return (Transaction) new Select().from(Transaction.class).where("id = ?", id).executeSingle();
    }

    public void updateTransaksi(long id, String title, int amount, Transaction.TransactionType type, TransactionContract.EditTransactionListener listener) {
        Transaction transaction = getTransaksi(id);

        transaction.title = title;
        transaction.amount = amount;
        transaction.type = type;

        if (transaction.save() > 0) {
            listener.success(transaction);
        } else {
            listener.failed("Update data failed");
        }
    }

    public void deleteTransaksi(long id, TransactionContract.DeleteTransactionListener listener) {
        try {
            Transaction transaction = getTransaksi(id);
            transaction.delete();
            listener.success();
        } catch (Exception e) {
            listener.failed(e.getMessage());
        }
    }

}
