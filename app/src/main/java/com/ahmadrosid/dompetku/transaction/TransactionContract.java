package com.ahmadrosid.dompetku.transaction;

import com.ahmadrosid.dompetku.data.Transactions;

/**
 * Created by staf on 03-Oct-17.
 */

public interface TransactionContract {

    interface AddTransactionListener {
        void success(Transactions transactions);
        void failed(String message);
    }
}
