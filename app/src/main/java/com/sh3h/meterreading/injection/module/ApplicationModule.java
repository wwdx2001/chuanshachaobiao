package com.sh3h.meterreading.injection.module;

import android.app.Application;
import android.content.Context;

import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

//    @Provides
//    @Singleton
//    RestfulApiService provideRibotsService() {
//        return RestfulApiService.Creator.newHttpService();
//    }

//    @Provides
//    @Singleton
//    DataConfig providerDataConfig() {
//        File sdDir = Environment.getExternalStorageDirectory();
//        File dbFile = new File(sdDir, "sh3h/meterreading/data/main.cbj");
//        return new DataConfig(dbFile.getPath(), "sh3h", 0);
//    }
}
