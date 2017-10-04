package com.ahmadrosid.dompetku.transaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.StateBottomeSet;
import com.ahmadrosid.dompetku.data.Transactions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 *
 * @update by tyangjawi03
 */

public class NewTransaction extends BottomSheetDialogFragment implements View.OnClickListener {

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
    Unbinder unbinder;

    private int type = 0;

    private TransactionContract.AddTransactionListener addTransactionListener;

    public NewTransaction(TransactionContract.AddTransactionListener listener) {
        addTransactionListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_transaction_bottomset, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.transaction_type, android.R.layout.simple_spinner_item);
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
            addTransactionListener.failed("Please input title.");
        } else if (itemAmount.getText().toString().isEmpty()) {
            addTransactionListener.failed("Please input amount.");
        } else {
            type = transaction.getSelectedItemPosition();

            addTransactionListener.success(
                    itemName.getText().toString(),
                    Integer.parseInt(itemAmount.getText().toString()),
                    type
            );

            ((StateBottomeSet) getActivity()).onDismiss();
            dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
