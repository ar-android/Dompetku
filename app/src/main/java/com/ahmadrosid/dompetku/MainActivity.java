package com.ahmadrosid.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmadrosid.dompetku.data.Ballance;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.list.AdapterTransactionList;
import com.ahmadrosid.dompetku.list.TransactionItemHolder;
import com.ahmadrosid.dompetku.transaction.NewTransaction;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements StateBottomeSet {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottom();
            }
        });
        loadData();
    }

    @Override protected void onResume() {
        super.onResume();
        if (realm != null)
            loadData();
    }

    private void loadData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                List<Transactions> data = realm.where(Transactions.class).findAll();
                setListWallet(data);
                long b = realm.where(Ballance.class).count();
                if (b > 0){
                    Ballance ballance = realm.where(Ballance.class).findFirst();
                    ((TextView)findViewById(R.id.ballance)).setText(CurrencyHelper.format(ballance.getAmount()));
                }
            }
        });
    }

    private void setListWallet(List<Transactions> data) {
        AdapterTransactionList adapter = new AdapterTransactionList(data) {
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
        RecyclerView rv = (RecyclerView) findViewById(R.id.list_wallet);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
    }

    private void openBottom() {
        LinearLayout bottomSheetViewgroup
                = (LinearLayout) findViewById(R.id.bottom_sheet);

        BottomSheetBehavior bottomSheetBehavior =
                BottomSheetBehavior.from(bottomSheetViewgroup);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        NewTransaction modalBottomSheet = new NewTransaction();
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override public void onDismiss() {
        loadData();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                editBalance();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editBalance() {
        LinearLayout bottomSheetViewgroup
                = (LinearLayout) findViewById(R.id.bottom_sheet);

        BottomSheetBehavior bottomSheetBehavior =
                BottomSheetBehavior.from(bottomSheetViewgroup);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        EditBalance modalBottomSheet = new EditBalance();
        modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
