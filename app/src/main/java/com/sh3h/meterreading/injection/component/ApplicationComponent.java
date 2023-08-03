package com.sh3h.meterreading.injection.component;

import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.MainApplication;
import com.sh3h.meterreading.location.GpsLocation;
import com.sh3h.meterreading.service.KeepAliveService;
import com.sh3h.meterreading.service.SyncService;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.meterreading.injection.module.ApplicationModule;
import com.sh3h.meterreading.service.VersionService;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainApplication mainApplication);
    void inject(SyncService syncService);
    void inject(VersionService versionService);
    void inject(KeepAliveService keepAliveService);

    @ApplicationContext Context context();
//    Application application();
//    RestfulApiService ribotsService();
    PreferencesHelper preferencesHelper();
//    DatabaseHelper databaseHelper();
    ConfigHelper configHelper();
    DataManager dataManager();
    Bus eventBus();
    GpsLocation gpsLocation();

//    MyName myName();
 //   UserModel userModel();
}