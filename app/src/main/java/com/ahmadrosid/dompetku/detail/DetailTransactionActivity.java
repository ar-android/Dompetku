package com.ahmadrosid.dompetku.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmadrosid.dompetku.EditTransactionActivity;
import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.main.MainActivity;
import com.ahmadrosid.dompetku.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class DetailTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ballance)
    TextView ballance;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    private Calendar calendar = Calendar.getInstance();

    long id;

    public static void start(Context context, Transaction transaction) {
        Intent starter = new Intent(context, DetailTransactionActivity.class);
        starter.putExtra("Transaction", transaction);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (id > 0)
            loadData();
    }

    private void loadData() {
        Transaction transaction = (Transaction) getIntent().getExtras().getSerializable("Transaction");

        setupData(transaction);
    }

    private void setupData(Transaction model) {
        getSupportActionBar().setTitle(model.title);

        Date dates = new Date(model.date);
        calendar.setTime(dates);

        String data_amount = CurrencyHelper.format(model.amount);
        title.setText(model.title);
        time.setText(format("EEE, MMM d, yy"));
        ballance.setText(data_amount);
        amount.setText(data_amount);

        if (model.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new
                        Intent(DetailTransactionActivity.this, MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_edit, R.id.btn_delete})
    public void onClick(View view) {
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
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

}
