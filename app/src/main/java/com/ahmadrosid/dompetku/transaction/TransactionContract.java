package com.ahmadrosid.dompetku.transaction;

/**
 * Created by staf on 03-Oct-17.
 */

public interface TransactionContract {

    interface AddTransactionListener {
        void success(String title, int amount, int type);
        void failed(String message);
    }
}
