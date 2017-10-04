package com.ahmadrosid.dompetku.main;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionListener;
import com.ahmadrosid.dompetku.models.TransactionRepository;

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
        List<Transaction> data = transactionRepository.getTransaksiList();

        view.showListTransaksi(data);
    }

    @Override
    public void addTransaksi(String title, int amount, Transaction.TransactionType type) {
        transactionRepository.addTransaksi(title, amount, type, new TransactionListener() {
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
    public void deleteTransaksi(Transaction transactions) {

    }

    @Override
    public void updateTransaksi(Transaction transactions) {

    }
}
