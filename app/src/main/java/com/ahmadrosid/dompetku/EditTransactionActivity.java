package com.ahmadrosid.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.detail.DetailTransactionActivity;

import io.realm.Realm;

public class EditTransactionActivity extends AppCompatActivity {

    long id;
    int type;

    private Spinner transaction;
    private EditText title;
    private EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_close));

        title = (EditText) findViewById(R.id.title);
        amount = (EditText) findViewById(R.id.amount);

        Realm realm = Realm.getDefaultInstance();
        id = getIntent().getLongExtra("id", 0);

        realm.beginTransaction();
        Transactions data = realm.where(Transactions.class)
                .equalTo("id", id).findFirst();
        setupData(data);
        realm.commitTransaction();
        realm.close();
    }

    private void setupData(Transactions model) {
        transaction = (Spinner) findViewById(R.id.transaction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transaction.setAdapter(adapter);

        title.setText(model.getTitle());
        amount.setText("" + model.getAmount());
        transaction.setSelection(model.getTransaction_type());
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
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
        if (validate()){
            Realm realmDb = Realm.getDefaultInstance();
            type = transaction.getSelectedItemPosition();
            realmDb.beginTransaction();
            Transactions data = realmDb.where(Transactions.class)
                    .equalTo("id", id).findFirst();
            data.setTitle(title.getText().toString());
            data.setAmount(Integer.parseInt(amount.getText().toString()));
            data.setDate(System.currentTimeMillis());
            data.setTransaction_type(type);
            realmDb.commitTransaction();
            realmDb.close();

            startActivity(new
                    Intent(EditTransactionActivity.this, DetailTransactionActivity.class)
                    .putExtra("id", id));
            finish();
        }
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
}
