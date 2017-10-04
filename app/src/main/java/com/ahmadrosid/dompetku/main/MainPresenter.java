package com.ahmadrosid.dompetku.main;

import android.widget.TextView;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.data.Ballance;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Inject
    Realm realm;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void loadData() {
        if (realm == null) {
            view.showError("Data error");
            return;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                List<Transactions> data = realm.where(Transactions.class).findAll();
                view.showListTransaksi(data);

                int ballance = 0;

                for (Transactions transaction : data) {
                    if (transaction.getTransaction_type() == 1) {
                        ballance -= transaction.getAmount();
                    } else {
                        ballance += transaction.getAmount();
                    }
                }

                view.showBalance(ballance);

            }
        });
    }

    @Override
    public void addTransaksi(String title, int amount, Transaction.TransactionType type) {
        long date = System.currentTimeMillis();
        Transaction transaction = new Transaction(title, amount, date, type);
        transaction.save();

        loadData();
    }

    @Override
    public void deleteTransaksi(Transactions transactions) {

    }

    @Override
    public void updateTransaksi(Transactions transactions) {

    }
}
