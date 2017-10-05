package com.ahmadrosid.dompetku.transaction;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by staf on 04-Oct-17.
 */

public class TransactionPresenter implements TransactionContract.Presenter {

    private TransactionContract.EditView editView;

    @Inject
    TransactionRepository transactionRepository;

    public TransactionPresenter(TransactionContract.EditView editView) {
        DompetkuApp.getIntance().getAppComponent().inject(this);
        this.editView = editView;
    }

    @Override
    public void loadAllTitle(TransactionContract.AllTitleListener titleListener) {
        List<Transaction> transactions = transactionRepository.getTransaksiGroupBy();

        List<String> titles = new ArrayList<String>();

        if (transactions.size() > 5) {
            for (int i=0;i<5;i++)
                titles.add(transactions.get(i).title);
        } else {
            for (Transaction transaction : transactions)
                titles.add(transaction.title);
        }

        if (!titles.isEmpty())
            titleListener.success(titles);
        else
            titleListener.failed("Empty data");
    }

    @Override
    public void loadTransaction(long id) {
        editView.showData(transactionRepository.getTransaksi(id));
    }

    @Override
    public void createTransaction(String title, int amount, Transaction.TransactionType type) {
        transactionRepository.addTransaksi(title, amount, type, new TransactionContract.AddTransactionListener() {
            @Override
            public void success(Transaction transaction) {
                editView.showData(transaction);
            }

            @Override
            public void failed(String message) {
                editView.showError(message);
            }
        });
    }

    @Override
    public void updateTransaction(long id, String title, int amount, Transaction.TransactionType type) {
        transactionRepository.updateTransaksi(id, title, amount, type, new TransactionContract.EditTransactionListener() {
            @Override
            public void success(Transaction transaction) {
                editView.showError("Data Transaksi telah diubah");
            }

            @Override
            public void failed(String message) {
                editView.showError(message);
            }
        });
    }

    @Override
    public void deleteTransaction(long id) {
        transactionRepository.deleteTransaksi(id, new TransactionContract.DeleteTransactionListener() {
            @Override
            public void success() {
                editView.showError("Transaction Deleted");
            }

            @Override
            public void failed(String message) {
                editView.showError(message);
            }
        });
    }

}
