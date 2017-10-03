package com.ahmadrosid.dompetku.transaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.StateBottomeSet;
import com.ahmadrosid.dompetku.data.Transactions;

import io.realm.Realm;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class NewTransaction extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText item_name;
    private EditText item_amount;
    private Spinner transaction;
    private int type = 0;

    private TransactionContract.AddTransactionListener addTransactionListener;

    public NewTransaction(TransactionContract.AddTransactionListener listener) {
        addTransactionListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_transaction_bottomset, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        item_name = (EditText) view.findViewById(R.id.item_name);
        item_amount = (EditText) view.findViewById(R.id.item_amount);

        view.findViewById(R.id.img_close).setOnClickListener(this);
        view.findViewById(R.id.img_done).setOnClickListener(this);

        transaction = (Spinner) view.findViewById(R.id.transaction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.transaction_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                dismiss();
                break;
            case R.id.img_done:
                process();
                break;
        }
    }

    private void process() {
        if (validate()) {
            type = transaction.getSelectedItemPosition();

            Transactions data = new Transactions();
            data.setTitle(item_name.getText().toString());
            data.setAmount(Integer.parseInt(item_amount.getText().toString()));
            data.setDate(System.currentTimeMillis());
            data.setTransaction_type(type);

            addTransactionListener.success(data);

            ((StateBottomeSet) getActivity()).onDismiss();
            dismiss();
        } else {
            addTransactionListener.failed("Fill Title and Amount.");
        }
    }

    private boolean validate() {
        if (item_name.getText().toString().isEmpty()) {
            showMessage("Please input title.");
        } else if (item_amount.getText().toString().isEmpty()) {
            showMessage("Please input amoun.");
        } else {
            return true;
        }
        return false;
    }

    private void showMessage(String message) {
        Snackbar.make(item_name, message, Snackbar.LENGTH_SHORT).show();
    }
}
