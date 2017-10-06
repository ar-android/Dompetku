package com.ahmadrosid.dompetku.transaction;

import com.ahmadrosid.dompetku.models.Transaction;

/**
 * Created by staf on 03-Oct-17.
 */

public interface TransactionContract {

    interface AddTransactionListener {
        void success(String title, int amount, int type);
        void failed(String message);
    }

    interface EditView {
        void showData(Transaction transaction);
        void showError(String message);
    }

}
