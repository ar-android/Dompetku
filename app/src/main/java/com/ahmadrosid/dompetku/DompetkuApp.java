package com.ahmadrosid.dompetku;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.ahmadrosid.dompetku.di.AppComponent;
import com.ahmadrosid.dompetku.di.AppModule;
import com.ahmadrosid.dompetku.di.DaggerAppComponent;

/**
 * Created by ocittwo on 1/26/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */
public class DompetkuApp extends Application {

    private static DompetkuApp intance = new DompetkuApp();
    private static AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }

    public static DompetkuApp getIntance() {
        return intance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }
}
