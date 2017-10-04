package com.ahmadrosid.dompetku.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.StateBottomeSet;
import com.ahmadrosid.dompetku.detail.DetailTransactionActivity;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.transaction.NewTransaction;
import com.ahmadrosid.dompetku.transaction.TransactionContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View, StateBottomeSet {

    @BindView(R.id.ballance)
    TextView ballanceTextView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.list_wallet)
    ListView listWallet;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        LinearLayout bottomSheetViewgroup = (LinearLayout) findViewById(R.id.bottom_sheet);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetViewgroup);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        NewTransaction modalBottomSheet = new NewTransaction(new TransactionContract.AddTransactionListener() {

            @Override
            public void success(String title, int amount, int type) {
                if (type == 0) {
                    presenter.addTransaksi(title, amount, Transaction.TransactionType.PEMASUKAN);
                } else {
                    presenter.addTransaksi(title, amount, Transaction.TransactionType.PENGELUARAN);
                }
            }

            @Override
            public void failed(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
    }

    @Override
    public void showBalance(int ballance) {
        ballanceTextView.setText(CurrencyHelper.format(ballance));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListTransaksi(List<Transaction> transactions) {
        MainAdapter adapter = new MainAdapter(this, transactions, new MainContract.ListViewListener() {
            @Override
            public void onClickListener(Transaction transactions) {
                DetailTransactionActivity.start(MainActivity.this, transactions.getId());
            }

            @Override
            public void onLongClickListener(Transaction transactions) {

            }
        });

        listWallet.setAdapter(adapter);
    }

    @Override
    public void onDismiss() {

    }

}
