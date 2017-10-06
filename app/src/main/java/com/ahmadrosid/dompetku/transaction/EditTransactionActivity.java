package com.ahmadrosid.dompetku.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.detail.DetailTransactionActivity;
import com.ahmadrosid.dompetku.models.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.R.attr.id;

public class EditTransactionActivity extends AppCompatActivity implements TransactionContract.EditView {

    private static String TRANSACTIONKEY = "TRANSACTIONKEY";

    @BindView(R.id.title)
    AppCompatEditText title;
    @BindView(R.id.amount)
    AppCompatEditText amount;
    @BindView(R.id.transaction)
    AppCompatSpinner transaction_type;

    private Transaction transaction;

    public static void start(Context context, Transaction transaction) {
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

        transaction = (Transaction) getIntent().getExtras().getSerializable(TRANSACTIONKEY);

        setupData(transaction);

    }

    private void setupData(Transaction model) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction_type.setAdapter(adapter);

        title.setText(model.title);
        amount.setText("" + model.amount);
        transaction_type.setSelection(model.type.ordinal());
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
                startActivity(new
                        Intent(EditTransactionActivity.this, DetailTransactionActivity.class)
                        .putExtra("id", id));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void done() {
//        if (validate()) {
//            Realm realmDb = Realm.getDefaultInstance();
//            type = transaction.getSelectedItemPosition();
//            realmDb.beginTransaction();
//            Transactions data = realmDb.where(Transactions.class)
//                    .equalTo("id", id).findFirst();
//            data.setTitle(title.getText().toString());
//            data.setAmount(Integer.parseInt(amount.getText().toString()));
//            data.setDate(System.currentTimeMillis());
//            data.setTransaction_type(type);
//            realmDb.commitTransaction();
//            realmDb.close();
//
//            startActivity(new
//                    Intent(EditTransactionActivity.this, DetailTransactionActivity.class)
//                    .putExtra("id", id));
//            finish();
//        }
    }

    private boolean validate() {
        if (title.getText().toString().isEmpty()) {
            showMessage("Please input title.");
        } else if (amount.getText().toString().isEmpty()) {
            showMessage("Please input amoun.");
        } else {
            return true;
        }
        return false;
    }

    private void showMessage(String message) {
        Snackbar.make(title, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(Transaction transaction) {

    }

    @Override
    public void showError(String message) {

    }
}
