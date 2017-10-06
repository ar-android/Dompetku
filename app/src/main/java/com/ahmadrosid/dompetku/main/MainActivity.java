package com.ahmadrosid.dompetku.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.dompetku.DetailTransactionActivity;
import com.ahmadrosid.dompetku.NewTransaction;
import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.data.Ballance;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.list.AdapterTransactionList;
import com.ahmadrosid.dompetku.list.TransactionItemHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.ballance)
    TextView ballanceTextView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.list_wallet)
    RecyclerView listWallet;

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
        NewTransaction modalBottomSheet = new NewTransaction();
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
    }

    @Override
    public void showBalance(Ballance ballance) {
        ballanceTextView.setText(CurrencyHelper.format(ballance.getAmount()));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListTransaksi(List<Transactions> transactionses) {
        AdapterTransactionList adapter = new AdapterTransactionList(transactionses) {
            @Override protected void bindHolder(TransactionItemHolder holder, final Transactions model) {
                holder.bind(model);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        startActivity(new
                                Intent(MainActivity.this, DetailTransactionActivity.class)
                                .putExtra("id", model.getId()));
                        finish();
                    }
                });
            }
        };

        listWallet.setLayoutManager(new LinearLayoutManager(this));
        listWallet.setAdapter(adapter);
        listWallet.setHasFixedSize(true);
    }

}
