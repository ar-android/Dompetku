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
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.main.MainActivity;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.transaction.EditTransactionActivity;
import com.ahmadrosid.dompetku.transaction.TransactionContract;
import com.ahmadrosid.dompetku.transaction.TransactionPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailTransactionActivity extends AppCompatActivity implements View.OnClickListener, TransactionContract.EditView {

    public static String EXTRAKEY = "TRANSACTIONKEY";

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

    private TransactionContract.Presenter presenter;
    private long id;

    public static void start(Context context, long id) {
        Intent starter = new Intent(context, DetailTransactionActivity.class);
        starter.putExtra(EXTRAKEY, id);
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

        id = getIntent().getLongExtra(EXTRAKEY, 0);

        presenter = new TransactionPresenter(this);
        presenter.loadTransaction(id);
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
                EditTransactionActivity.start(this, id);
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
                        presenter.deleteTransaction(id);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void showData(Transaction transaction) {
        getSupportActionBar().setTitle(transaction.title);

        Date dates = new Date(transaction.date);
        calendar.setTime(dates);

        String data_amount = CurrencyHelper.format(transaction.amount);
        title.setText(transaction.title);
        time.setText(format("EEE, MMM d, yy"));
        ballance.setText(data_amount);
        amount.setText(data_amount);

        if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
            type.setText("Pemasukan");
        } else {
            type.setText("Pengeluaran");
        }

        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
