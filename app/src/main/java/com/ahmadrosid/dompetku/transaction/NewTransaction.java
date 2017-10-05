package com.ahmadrosid.dompetku.transaction;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.utils.Calculator;
import com.ahmadrosid.dompetku.utils.CalculatorListener;
import com.ahmadrosid.dompetku.utils.TitlePicker;
import com.ahmadrosid.dompetku.utils.TitlePickerListener;
import com.ahmadrosid.dompetku.main.MainContract;
import com.ahmadrosid.dompetku.models.Transaction;

import java.util.List;

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
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.calculator)
    Calculator calculator;
    @BindView(R.id.title_picker)
    TitlePicker titlePicker;

    private Transaction.TransactionType type;

    private MainContract.PopUpListener popUpListener;

    private TransactionContract.Presenter presenter;

    public NewTransaction(Context context, Transaction.TransactionType type, MainContract.PopUpListener listener) {
        super(context);
        popUpListener = listener;
        presenter = new TransactionPresenter(this);
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_transaction_bottomset);
        ButterKnife.bind(this);

        title.setText(type.name());

        if (type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
            titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
        }

        calculator.setListener(new CalculatorListener() {
            @Override
            public void result(int amount) {
                itemAmount.setText(amount + "");
                calculator.setVisibility(View.GONE);
                itemName.requestFocus();
                titlePicker.setVisibility(View.VISIBLE);
            }
        });

        titlePicker.setListener(new TitlePickerListener() {
            @Override
            public void onClickListener(String title) {
                itemName.setText(title);
            }
        });

        presenter.loadAllTitle(new TransactionContract.AllTitleListener() {
            @Override
            public void success(List<String> data) {
                titlePicker.setTextList(data);
            }

            @Override
            public void failed(String message) {

            }
        });

        titlePicker.setVisibility(View.INVISIBLE);
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

            presenter.createTransaction(
                    itemName.getText().toString(),
                    Integer.parseInt(itemAmount.getText().toString()),
                    type
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
