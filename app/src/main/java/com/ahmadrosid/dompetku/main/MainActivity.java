package com.ahmadrosid.dompetku.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import com.ahmadrosid.dompetku.transaction.EditTransactionActivity;
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
        NewTransaction modalBottomSheet = new NewTransaction(new MainContract.PopUpListener() {

            @Override
            public void success() {
                presenter.loadData();
            }

            @Override
            public void failed(String message) {
                showError(message);
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
            public void onLongClickListener(final Transaction transactions) {
                CharSequence[] menuItems = new CharSequence[] {"Detail", "Edit", "Delete"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(transactions.title);
                builder.setItems(menuItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0 :
                                DetailTransactionActivity.start(MainActivity.this, transactions.getId());
                                break;
                            case 1 :
                                EditTransactionActivity.start(MainActivity.this, transactions.getId());
                                break;
                            case 2 :
                                delete(transactions);
                                break;
                        }
                    }
                });

                builder.show();
            }
        });

        listWallet.setAdapter(adapter);
    }

    @Override
    public void onDismiss() {

    }

    private void delete(final Transaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Are you sure to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.deleteTransaksi(transaction);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
