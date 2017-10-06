package com.ahmadrosid.dompetku.main;

import android.util.Log;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionListener;
import com.ahmadrosid.dompetku.models.TransactionRepository;
import com.ahmadrosid.dompetku.transaction.TransactionContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Inject
    TransactionRepository transactionRepository;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void loadData() {
        Calendar current = Calendar.getInstance();

        Calendar start = Calendar.getInstance();
        start.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), 0, 23, 59, 59);

        Calendar end = Calendar.getInstance();
        end.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH)+1, 0, 23, 59, 59);

        DateFormat format = new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss");

        Log.e("Current",    format.format(current.getTime())    + " " + current.getTimeInMillis());
        Log.e("Start",      format.format(start.getTime())      + " " + start.getTimeInMillis());
        Log.e("End",        format.format(end.getTime())        + " " + end.getTimeInMillis());

        long s = start.getTimeInMillis();
        long e = end.getTimeInMillis();

        List<Transaction> data = transactionRepository.getTransaksiList(s, e);

        view.showListTransaksi(data);

        int ballance = 0;
        int expend = 0;
        for (Transaction transaction : data) {
            Log.e("Transaksi", transaction.date+"");
            if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
                ballance += transaction.amount;
            } else {
                ballance -= transaction.amount;
                expend += transaction.amount;
            }
        }

        view.showBalance(ballance, expend);
    }

    @Override
    public void addTransaksi(String title, int amount, Transaction.TransactionType type) {
        transactionRepository.addTransaksi(title, amount, type, new TransactionContract.AddTransactionListener() {
            @Override
            public void success(Transaction transaction) {
                loadData();
            }

            @Override
            public void failed(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void deleteTransaksi(Transaction transactions) {
        transactionRepository.deleteTransaksi(transactions.getId(), new TransactionContract.DeleteTransactionListener() {
            @Override
            public void success() {
                loadData();
            }

            @Override
            public void failed(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void updateTransaksi(Transaction transactions) {

    }

}
