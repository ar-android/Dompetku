package com.ahmadrosid.dompetku.di;

import com.ahmadrosid.dompetku.main.MainPresenter;
import com.ahmadrosid.dompetku.transaction.TransactionPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by staf on 03-Oct-17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    MainPresenter inject(MainPresenter mainPresenter);
    TransactionPresenter inject(TransactionPresenter transactionPresenter);

}
