package com.ahmadrosid.dompetku.transaction;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionRepository;

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
    public void deleteTransaction(long id, TransactionContract.DeleteTransactionListener listener) {
        transactionRepository.deleteTransaksi(id, listener);
    }

}
