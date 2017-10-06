package com.ahmadrosid.dompetku.transaction;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.main.MainContract;
import com.ahmadrosid.dompetku.models.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 * @update by tyangjawi03
 */

public class NewTransaction extends Dialog implements View.OnClickListener, TransactionContract.EditView {

    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_done)
    ImageView imgDone;
    @BindView(R.id.item_name)
    AppCompatEditText itemName;
    @BindView(R.id.item_amount)
    AppCompatEditText itemAmount;
    @BindView(R.id.transaction)
    AppCompatSpinner transaction;
    private int type = 0;

    private MainContract.PopUpListener popUpListener;

    private TransactionContract.Presenter presenter;

    public NewTransaction(Context context, MainContract.PopUpListener listener) {
        super(context);
        popUpListener = listener;
        presenter = new TransactionPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_transaction_bottomset);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getContext(),
                R.array.transaction_type,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction.setAdapter(adapter);
    }

    @OnClick({R.id.img_close, R.id.img_done})
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
        if (itemName.getText().toString().isEmpty()) {
            popUpListener.failed("Please input title.");
        } else if (itemAmount.getText().toString().isEmpty()) {
            popUpListener.failed("Please input amount.");
        } else {
            type = transaction.getSelectedItemPosition();

            Transaction.TransactionType transactionType;

            if (type == 0) {
                transactionType = Transaction.TransactionType.PEMASUKAN;
            } else {
                transactionType = Transaction.TransactionType.PENGELUARAN;
            }

            presenter.createTransaction(
                    itemName.getText().toString(),
                    Integer.parseInt(itemAmount.getText().toString()),
                    transactionType
            );

        }
    }

    @Override
    public void showData(Transaction transaction) {

        popUpListener.success();

        dismiss();
    }

    @Override
    public void showError(String message) {
        popUpListener.failed(message);
    }

}
