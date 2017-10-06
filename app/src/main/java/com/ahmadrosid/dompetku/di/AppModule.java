package com.ahmadrosid.dompetku.di;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.TransactionRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by staf on 03-Oct-17.
 */

@Module
public class AppModule {

    private DompetkuApp dompetkuApp;

    public AppModule(DompetkuApp dompetkuApp) {
        this.dompetkuApp = dompetkuApp;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    TransactionRepository provideTransactionRepository() {
        return new TransactionRepository();
    }
}
