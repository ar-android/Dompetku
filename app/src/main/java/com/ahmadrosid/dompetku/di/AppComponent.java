package com.ahmadrosid.dompetku.di;

import com.ahmadrosid.dompetku.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by staf on 03-Oct-17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    MainPresenter inject(MainPresenter mainPresenter);

}
