package com.ahmadrosid.dompetku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class DetailTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private Calendar calendar = Calendar.getInstance();

    private TextView ballance;
    private TextView title;
    private TextView amount;
    private TextView type;
    private TextView time;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ballance = (TextView) findViewById(R.id.ballance);
        title = (TextView) findViewById(R.id.title);
        amount = (TextView) findViewById(R.id.amount);
        type = (TextView) findViewById(R.id.type);
        time = (TextView) findViewById(R.id.time);

        loadData();
    }

    @Override protected void onResume() {
        super.onResume();
        if (id > 0)
            loadData();
    }

    private void loadData() {
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
        getSupportActionBar().setTitle(model.getTitle());

        Date dates = new Date(model.getDate());
        calendar.setTime(dates);

        String data_amount = CurrencyHelper.format(model.getAmount());
        title.setText(model.getTitle());
        time.setText(format("EEE, MMM d, yy"));
        ballance.setText(data_amount);
        amount.setText(data_amount);

        if (model.getTransaction_type() == 0) {
            type.setText("Pemasukan");
        } else {
            type.setText("Pengeluaran");
        }

        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    public String format(String sdfPattern) {
        return new SimpleDateFormat(sdfPattern).format(calendar.getTime());
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new
                        Intent(DetailTransactionActivity.this, MainActivity.class) );
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                startActivity(new
                        Intent(DetailTransactionActivity.this, EditTransactionActivity.class)
                        .putExtra("id", id));
                finish();
                break;
            case R.id.btn_delete:
                delete();
                break;
        }
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Are you sure to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        Transactions data = realm.where(Transactions.class)
                                .equalTo("id", id).findFirst();
                        data.deleteFromRealm();
                        realm.commitTransaction();
                        realm.close();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
