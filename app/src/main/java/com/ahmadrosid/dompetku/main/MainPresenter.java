package com.ahmadrosid.dompetku.main;

import android.widget.TextView;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.data.Ballance;
import com.ahmadrosid.dompetku.data.Transactions;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by staf on 03-Oct-17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Inject
    Realm realm;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void loadData() {
        if (realm == null) {
            view.showError("Data error");
            return;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                List<Transactions> data = realm.where(Transactions.class).findAll();
                view.showListTransaksi(data);

                long b = realm.where(Ballance.class).count();
                if (b > 0){
                    Ballance ballance = realm.where(Ballance.class).findFirst();
                    view.showBalance(ballance);
                }
            }
        });
    }

    @Override
    public void addTransaksi(Transactions transactions) {
        realm.beginTransaction();

        realm.copyToRealm(transactions);
        realm.commitTransaction();

        realm.close();
    }

    @Override
    public void deleteTransaksi(Transactions transactions) {

    }

    @Override
    public void updateTransaksi(Transactions transactions) {

    }
}
