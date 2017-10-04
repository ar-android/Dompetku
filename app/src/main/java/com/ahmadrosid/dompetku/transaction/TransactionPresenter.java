package com.ahmadrosid.dompetku.transaction;

import com.activeandroid.query.Select;
import com.ahmadrosid.dompetku.models.Transaction;

/**
 * Created by staf on 04-Oct-17.
 */

public class TransactionPresenter implements TransactionContract.Presenter {

    private TransactionContract.EditView editView;

    public TransactionPresenter(TransactionContract.EditView editView) {
        this.editView = editView;
    }

    @Override
    public void loadTransaction(long id) {
        Transaction transaction = new Select().from(Transaction.class).executeSingle();

        editView.showData(transaction);
    }

    @Override
    public void createTransaction(String title, int amount, Transaction.TransactionType type, TransactionContract.AddTransactionListener listener) {

    }

    @Override
    public void updateTransaction(long id, String title, int amount, Transaction.TransactionType type, TransactionContract.EditTransactionListener listener) {
        Transaction transaction = new Select().from(Transaction.class).where("id = ?", id).executeSingle();

        transaction.title = title;
        transaction.amount = amount;
        transaction.type = type;

        if (transaction.save() > 0) {
            listener.success(transaction);
        } else {
            listener.failed("Error update data");
        }
    }

    @Override
    public void deleteTransaction(long id) {

    }
}
