package com.ahmadrosid.dompetku.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.detail.DetailTransactionActivity;
import com.ahmadrosid.dompetku.models.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTransactionActivity extends AppCompatActivity implements TransactionContract.EditView {

    private static String TRANSACTIONKEY = "TRANSACTIONKEY";

    @BindView(R.id.title)
    AppCompatEditText title;
    @BindView(R.id.amount)
    AppCompatEditText amount;
    @BindView(R.id.transaction)
    AppCompatSpinner transaction_type;

    private long id;

    private TransactionContract.Presenter presenter;

    public static void start(Context context, long transaction) {
        Intent starter = new Intent(context, EditTransactionActivity.class);
        starter.putExtra(TRANSACTIONKEY, transaction);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet_item);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_close));

        id = getIntent().getLongExtra(TRANSACTIONKEY, 0);

        presenter = new TransactionPresenter(this);
        presenter.loadTransaction(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                done();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void done() {
        if (title.getText().toString().isEmpty()) {
            showError("Please input title.");
        } else if (amount.getText().toString().isEmpty()) {
            showError("Please input amoun.");
        } else {
            Transaction.TransactionType transactionType;
            if (transaction_type.getSelectedItemPosition() == 0) {
                transactionType = Transaction.TransactionType.PEMASUKAN;
            } else {
                transactionType = Transaction.TransactionType.PENGELUARAN;
            }

            TransactionContract.EditTransactionListener listener = new TransactionContract.EditTransactionListener() {

                @Override
                public void success(Transaction transaction) {
                    Toast.makeText(EditTransactionActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void failed(String message) {
                    showError(message);
                }
            };

            presenter.updateTransaction(
                    id,
                    title.getText().toString(),
                    Integer.parseInt(amount.getText().toString()),
                    transactionType
            );
        }

    }

    @Override
    public void showData(Transaction transaction) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_type.setAdapter(adapter);

        title.setText(transaction.title);
        amount.setText("" + transaction.amount);
        transaction_type.setSelection(transaction.type.ordinal());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

}
