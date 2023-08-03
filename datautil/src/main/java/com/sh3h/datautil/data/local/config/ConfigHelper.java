package com.sh3h.datautil.data.local.config;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.datautil.util.FileUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class ConfigHelper {
    public static final File STORAGE_PATH = Environment.getExternalStorageDirectory();
    /**
     * 根目录
     */
    public static final String FOLDER_ROOT = "sh3h/meterreading";
    /**
     * 配置文件目录
     */
    public static final String FOLDER_CONFIG = FOLDER_ROOT + "/config";
    /**
     * 多用户配置文件目录
     */
    public static final String FOLDER_USER = FOLDER_ROOT + "/user";
    /**
     * 数据配置文件目录
     */
    public static final String FOLDER_DATA = FOLDER_ROOT + "/data";
    /**
     * 激活文件目录
     */
    public static final String FOLDER_KEY = FOLDER_ROOT + "/key";

    /**
     * 图片存储目录
     */
    public static final String FOLDER_IMAGES = FOLDER_ROOT + "/images";

    /**
     * 图片存储目录
     */
    public static final String QIANMING_IMAGES = FOLDER_IMAGES + "/signature";

    /**
     * 声音存储目录
     */
    public static final String FOLDER_SOUNDS = FOLDER_ROOT + "/sounds";
    /**
     * 更新文件存储目录
     */
    public static final String FOLDER_UPDATE = FOLDER_ROOT + "/update";

    /**
     * www文件存储目录
     */
    public static final String FOLDER_WWW = FOLDER_ROOT + "/www";

    /**
     * 日志文件存储目录
     */
    public static final String FOLDER_LOG = FOLDER_ROOT + "/log";

    /**
     * 用户配置文件名
     */
    public static final String FILE_USER_CONFIG = "user.properties";

    /**
     * 用户登录信息文件名
     */
    public static final String FILE_USER_LOGIN = "login.properties";

    /**
     * 版本记录号
     */
    public static final String FILE_VERSION_UPDATE = "versions.properties";
    /**
     * 系统参数文件名
     */
    public static final String FILE_SYSTEM_CONFIG = "system.properties";

    /**
     * Map参数文件名
     */
    public static final String FILE_MAP_CONFIG = "map.properties";

    /**
     * 下载的配置文件
     */
    public static final String FILE_OTHER_CONFIG = "other.properties";

    /**
     * 用户配置文件名
     */
    public static final String FILE_USER = "users.xml";
    /**
     * 在线用户配置文件名
     */
    public static final String FILE_ONLINE_USER = "online-users.xml";
    /**
     * 词语配置文件名
     */
    public static final String FILE_WORDS = "words.xml";
    /**
     * 数据库文件名
     */
    public static final String DB_NAME = "main.cbj";
    /**
     * 激活文件名
     */
    public static final String FILE_KEY_CONFIG = "key.properties";

    /**
     * map folder
     */
    public static final String FOLDER_MAP = FOLDER_ROOT + "/map";

    private static final String TAG = "ConfigHelper";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final SystemConfig mSystemConfig;
    private final MapConfig mMapConfig;
    private final VersionConfig mVersionConfig;
    private final KeyConfig mKeyConfig;
    private final UserConfig mUserConfig;
    private final OtherConfig mOtherConfig;

    @Inject
    public ConfigHelper(@ApplicationContext Context context,
                        PreferencesHelper preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mSystemConfig = new SystemConfig();
        mMapConfig = new MapConfig();
        mVersionConfig = new VersionConfig();
        mKeyConfig = new KeyConfig();
        mUserConfig = new UserConfig();
        mOtherConfig = new OtherConfig();
    }

    public Observable<Void> initDefaultConfigs() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (!initDefaultConfigFiles()) {
                    subscriber.onError(new Throwable("initDefaultConfigFiles is failure"));
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Void> clearConfigs() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (!clearConfigFiles()) {
                    subscriber.onError(new Throwable("clearConfigs is failure"));
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Void> initUserConfig(final int userId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (!initUserConfigFile(userId)) {
                    subscriber.onError(new Throwable("initDefaultConfigFiles is failure"));
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * initialize configure files
     *
     * @return true: success
     */
    private boolean initDefaultConfigFiles() {
        try {
            // config
            File configDir = new File(STORAGE_PATH, FOLDER_CONFIG);
            if (!configDir.exists()) {
                // 创建 FOLDER_CONFIG
                configDir.mkdirs();
            }

            // 遍历 assets/config 文件夹，在sd卡不存在则复制
            AssetManager assetManager = mContext.getAssets();
            String[] configFiles = assetManager.list("config");
            if (configFiles.length > 0) {
                for (String fileName : configFiles) {
                    File outputFile = new File(STORAGE_PATH, FOLDER_CONFIG + "/" + fileName);
                    if (!outputFile.exists()) {
                        InputStream is = assetManager.open("config/" + fileName);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[256];
                        for (int count = is.read(buffer); count > 0; ) {
                            fos.write(buffer, 0, count);
                            count = is.read(buffer);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            }

            // data
            File dataDir = new File(STORAGE_PATH, FOLDER_DATA);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            String[] dataFiles = assetManager.list("data");
            if (dataFiles.length > 0) {
                for (String fileName : dataFiles) {
                    File outputFile = new File(STORAGE_PATH, FOLDER_DATA + "/" + fileName);
                    if (!outputFile.exists()) {
                        InputStream is = assetManager.open("data/" + fileName);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[256];
                        for (int count = is.read(buffer); count > 0; ) {
                            fos.write(buffer, 0, count);
                            count = is.read(buffer);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            }

            // key
            File keyDir = new File(STORAGE_PATH, FOLDER_KEY);
            if (!keyDir.exists()) {
                keyDir.mkdirs();
            }

            String[] keyFiles = assetManager.list("key");
            if (keyFiles.length > 0) {
                for (String fileName : keyFiles) {
                    File outputFile = new File(STORAGE_PATH, FOLDER_KEY + "/" + fileName);
                    if (!outputFile.exists()) {
                        InputStream is = assetManager.open("key/" + fileName);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[256];
                        for (int count = is.read(buffer); count > 0; ) {
                            fos.write(buffer, 0, count);
                            count = is.read(buffer);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            }

            // image
            File imagesDir = new File(STORAGE_PATH, FOLDER_IMAGES);
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }

            // signature
            File signature = new File(STORAGE_PATH, QIANMING_IMAGES);
            if (!signature.exists()) {
                signature.mkdirs();
            }

            // sound
            File soundsDir = new File(STORAGE_PATH, FOLDER_SOUNDS);
            if (!soundsDir.exists()) {
                soundsDir.mkdirs();
            }

            // update
            File updateDir = new File(STORAGE_PATH, FOLDER_UPDATE);
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }

//            File version = new File(updateDir, FILE_VERSION_UPDATE);
//            if (!version.exists()) {
//                ConfigHelper.LoadDefaultSystemVersion().writeToFile(version.getPath());
//            }

            // log
            File logDir = new File(STORAGE_PATH, FOLDER_LOG);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // user
            File userDir = new File(STORAGE_PATH, FOLDER_USER);
            if (!userDir.exists()) {
                userDir.mkdirs();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * clear all configures
     *
     * @return
     */
    private boolean clearConfigFiles() {
        try {
            // configure
            File oldConfigDir = new File(STORAGE_PATH, FOLDER_CONFIG);
            if (oldConfigDir.exists()) {
                FileUtil.deleteFile(oldConfigDir);
            }

            // user
            File oldUserConfigDir = new File(STORAGE_PATH, FOLDER_USER);
            if (oldUserConfigDir.exists()) {
                FileUtil.deleteFile(oldUserConfigDir);
            }

            // data
            File oldDataDir = new File(STORAGE_PATH, FOLDER_DATA);
            if (oldDataDir.exists()) {
                FileUtil.deleteFile(oldDataDir);
            }

            // image
            File oldImageDir = new File(STORAGE_PATH, FOLDER_IMAGES);
            if (oldImageDir.exists()) {
                FileUtil.deleteFile(oldImageDir);
            }

            // sound
            File oldSoundDir = new File(STORAGE_PATH, FOLDER_SOUNDS);
            if (oldSoundDir.exists()) {
                FileUtil.deleteFile(oldSoundDir);
            }

            // update
            File oldUpdateDir = new File(STORAGE_PATH, FOLDER_UPDATE);
            if (oldUpdateDir.exists()) {
                FileUtil.deleteFile(oldUpdateDir);
            }

            // www
            File oldWWWDir = new File(STORAGE_PATH, FOLDER_WWW);
            if (oldWWWDir.exists()) {
                FileUtil.deleteFile(oldWWWDir);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean initUserConfigFile(int userId) {
        File dir = new File(STORAGE_PATH, FOLDER_USER + "/" + userId);
        File file = new File(dir, FILE_USER_CONFIG);

        if (!file.exists()) {
            dir.mkdirs();
            mUserConfig.loadDefaultConfig();
            mUserConfig.writeProperties(file.getPath());
        } else {
            mUserConfig.readProperties(file.getPath());
        }

        return true;
    }

    /**
     * @return
     */
    public File getConfigFolderPath() {
        return new File(STORAGE_PATH, FOLDER_CONFIG);
    }

    /**
     * get the path of system.properties
     *
     * @return file path
     */
    public File getSystemConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_SYSTEM_CONFIG);
    }

    /**
     * @return
     */
    public File getOtherConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_OTHER_CONFIG);
    }

    /**
     * @return
     */
    public File getUserConfigFilePath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        File dir = new File(STORAGE_PATH, FOLDER_USER + "/" + userSession.getUserId());
        return new File(dir, FILE_USER_CONFIG);
    }

    public File getAccountConfigFilePath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, "/CBJPZ.xml");
    }

    /**
     * get the path of log file
     *
     * @return file path
     */
    public File getLogFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_LOG);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = String.format("log-%s.txt", TextUtil.format(new Date(), TextUtil.FORMAT_DATE_SECOND));
        return new File(dir, name);
    }

    /**
     * @return
     */
    public File getKeyFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_KEY);
        return new File(dir, FILE_KEY_CONFIG);
    }

    /**
     * @return
     */
    public File getVersionFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_UPDATE);
        return new File(dir, FILE_VERSION_UPDATE);
    }

    /**
     * @return
     */
    public File getDBFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_DATA);
        return new File(dir, DB_NAME);
    }

    /**
     * @return
     */
    public File getImageFolderPath() {
        return new File(STORAGE_PATH, FOLDER_IMAGES);
    }

    /**
     * @return
     */
    public File getUpdateFolderPath() {
        return new File(STORAGE_PATH, FOLDER_UPDATE);
    }

    /**
     * @return
     */
    public synchronized SystemConfig getSystemConfig() {
        if (!mSystemConfig.isRead()) {
            mSystemConfig.readProperties(getSystemConfigFilePath().getPath());
        }

        return mSystemConfig;
    }

    /**
     * @return
     */
    public synchronized UserConfig getUserConfig() {
        if (!mUserConfig.isRead()) {
            mUserConfig.readProperties(getUserConfigFilePath().getPath());
        }

        return mUserConfig;
    }

    /**
     * @return
     */
    public KeyConfig getKeyConfig() {
        if (!mKeyConfig.isRead()) {
            mKeyConfig.readProperties(getKeyFilePath().getPath());
        }
        return mKeyConfig;
    }

    /**
     * @return
     */
    public synchronized VersionConfig getVersionConfig() {
        if (!mVersionConfig.isRead()) {
            mVersionConfig.readProperties(getVersionFilePath().getPath());
        }

        return mVersionConfig;
    }

    /**
     * @return
     */
    public synchronized OtherConfig getOtherConfig() {
        if (!mOtherConfig.isRead()) {
            mOtherConfig.readProperties(getOtherConfigFilePath().getPath());
        }

        return mOtherConfig;
    }

    public String getKey() {
        return getKeyConfig().getString(KeyConfig.PARAM_KEY);
    }

    public void setKey(String key) {
        getKeyConfig().set(KeyConfig.PARAM_KEY, key);
        getKeyConfig().writeProperties(getKeyFilePath().getPath());
    }

    public boolean isGridStyle() {
        UserConfig userConfig = getUserConfig();
        int style = userConfig.getInteger(UserConfig.PARAM_SYS_HOME_STYLE, UserConfig.HOME_STYLE_GRID);
        return style == UserConfig.HOME_STYLE_GRID;
    }

    public String getUserChangYong() {
        UserConfig userConfig = getUserConfig();
        return userConfig.getString(UserConfig.PARAM_CB_DEFAULT_CYCBZT);
    }

    public Observable<Boolean> saveGridStyle(boolean isGrid) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_HOME_STYLE,
                isGrid ? UserConfig.HOME_STYLE_GRID : UserConfig.HOME_STYLE_LIST);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveUserChangYong(String zhuangTaiBM) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_CB_DEFAULT_CYCBZT, zhuangTaiBM);

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public boolean isPhotoQualityNormal() {
        UserConfig userConfig = getUserConfig();
        int quality = userConfig.getInteger(UserConfig.PARAM_SYS_QUALITY_PHOTO, UserConfig.QUALITY_MIDDLE);
        return quality == UserConfig.QUALITY_MIDDLE;
    }

    public Observable<Boolean> savePhotoQuality(boolean isNormal) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_QUALITY_PHOTO,
                isNormal ? UserConfig.QUALITY_MIDDLE : UserConfig.QUALITY_HIGH);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public boolean saveDataVersion(int version) {
        VersionConfig versionConfig = getVersionConfig();
        versionConfig.set(VersionConfig.DATA_VERSION, version);
        return versionConfig.writeProperties(getVersionFilePath().getPath());
    }

    public boolean isUpdateVersion() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO, false);
    }

    public boolean isSyncData() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_SYNC_DATA_AUTO, false);
    }

    public boolean isSingleUpload() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_SINGLE_UPLOAD, false);
    }

    public boolean isUploadAfterCeben() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPLOAD_AFTER_CEBEN, false);
    }

    public boolean isUploadAll() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPLOADING_TOTAL, false);
    }

    public boolean isDownloadAll() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL, false);
    }

    public String getBaseUri() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_SERVER_BASE_URI);
    }

    public String getReservedBaseUri() {
        SystemConfig systemConfig = getSystemConfig();
        String uri = systemConfig.getString(SystemConfig.PARAM_SERVER_RESERVED_BASE_URI);
        if (uri == null) {
            uri = "";
        }
        return uri;
    }

    public boolean isReservedAddress() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SERVER_USING_RESERVED, false);
    }

    public String getBrokerUrl() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_BROKER_URL);
    }

    public String getFileUrl() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_FILE_SERVER_URL);
    }

    public String getFileReservedUrl() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_FILE_SERVER_RESERVED_URL);
    }

    public int getKeepAliveInterval() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getInteger(SystemConfig.PARAM_KEEP_LIVE_INTERVAL,
                SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE);
    }

    public boolean isLeftOrRightOperation() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_OPERATION_LEFTORRIGHT, false);
    }

    public String getRegion() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_SYS_REGION);
    }

    public Observable<Boolean> saveUpdateVersion(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveSyncData(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_SYNC_DATA_AUTO,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveSingleUpload(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_SINGLE_UPLOAD,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveUploadAfterCeben(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_UPLOAD_AFTER_CEBEN,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveUploadAll(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_UPLOADING_TOTAL,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveDownloadAll(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveSonAPKConfigure(boolean isReservedAddress, String pictureQuality) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SERVER_USING_RESERVED, isReservedAddress);

        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_QUALITY_PHOTO,
                pictureQuality.equals("normal") ? UserConfig.QUALITY_MIDDLE : UserConfig.QUALITY_HIGH);

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveNetWorkSetting(String baseUri,
                                                  String reservedBaseUri,
                                                  String fileUrl,
                                                  String reservedFileUrl,
                                                  boolean isReservedAddress,
                                                  String mapUri) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SERVER_BASE_URI, baseUri);
        systemConfig.set(SystemConfig.PARAM_SERVER_RESERVED_BASE_URI, reservedBaseUri);
        systemConfig.set(SystemConfig.PARAM_FILE_SERVER_URL, TextUtil.getString(fileUrl));
        systemConfig.set(SystemConfig.PARAM_FILE_SERVER_RESERVED_URL, TextUtil.getString(reservedFileUrl));
        systemConfig.set(SystemConfig.PARAM_SERVER_USING_RESERVED, isReservedAddress);

        //添加arcgis
        MapConfig mapConfig = getMapConfig();
        mapConfig.set(MapConfig.PARAM_MAP_SERVER_URL, mapUri);

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = (getSystemConfig().writeProperties(getSystemConfigFilePath().getPath())) &&
                        (getMapConfig().writeProperties(getMapConfigFilePath().getPath()));
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveLeftOrRightOperation(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_OPERATION_LEFTORRIGHT,
                flag);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public String getReadingDate(boolean isStart) {
        OtherConfig otherConfig = getOtherConfig();
        int date;
        if (isStart) {
            date = otherConfig.getInteger(OtherConfig.READING_START_DATE,
                    OtherConfig.READING_READING_START_DATE);
        } else {
            date = otherConfig.getInteger(OtherConfig.READING_END_DATE,
                    OtherConfig.READING_READING_END_DATE);
        }

        return String.valueOf(date);
    }

    public boolean isCurrentReadingDateValid() {
        OtherConfig otherConfig = getOtherConfig();
        int startDate = otherConfig.getInteger(OtherConfig.READING_START_DATE,
                OtherConfig.READING_READING_START_DATE);
        int endDate = otherConfig.getInteger(OtherConfig.READING_END_DATE,
                OtherConfig.READING_READING_END_DATE);
        if (startDate > endDate) {
            return true;
        }

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);//获取日
        return (startDate <= day) && (day <= endDate);
    }

    public int getMonthCount() {
        return getSystemConfig().getInteger(SystemConfig.CB_DATA_DOWNLOAD_TIME,
                SystemConfig.DEFALUT_DOWNLOAD_TIME);
    }

    public boolean isDownloadingTotal() {
        return getSystemConfig().getBoolean(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL, false);
    }

    public String getChaoJianZTS() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_SYS_CHAOJIANZTS);
    }

    public int getUploadingTimeError() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getInteger(SystemConfig.PARAM_SYS_UPLOADING_TIME_ERROR,
                SystemConfig.DEFALUT_SYS_UPLOADING_TIME_ERROR);
    }

    public double getWaterHighNumberPro() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getDouble(SystemConfig.PARAM_SYS_WATER_HIGH_NUMBER_PRO,
                SystemConfig.DEFALUT_SYS_WATER_HIGH_NUMBER_PRO);
    }

    public String getCheckOutsideType() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_SYS_CHECK_OUT_TYPE);
    }

    /**
     * gisMap   zhangjing
     */
    private String getMapInnerPath() {
        return new File(STORAGE_PATH, FOLDER_MAP).getPath();
    }

    public String getMapPath() {
        boolean isInner = getMapConfig().getBoolean(MapConfig.PARAM_MAP_PATH_INNER, true);
        if (isInner) {
            return getMapInnerPath();
        } else {
            List<String> storageList = getExtSDCardPaths();
            if ((storageList == null) || (storageList.size() <= 0)) {
                return getMapInnerPath();
            } else {
                File mapDir;
                for (String storage : storageList) {
                    mapDir = new File(storage, FOLDER_MAP);
                    if (mapDir.exists()) {
                        return mapDir.getPath();
                    }
                }

                return getMapInnerPath();
            }
        }
    }

    /**
     * @return
     */
    public MapConfig getMapConfig() {
        if (!mMapConfig.isRead()) {
            mMapConfig.readProperties(getMapConfigFilePath().getPath());
        }

        return mMapConfig;
    }

    /**
     * @return
     */
    public File getMapConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_MAP_CONFIG);
    }

    public String getMapServerUrl() {
        return getMapConfig().getString(MapConfig.PARAM_MAP_SERVER_URL);
    }

    private List<String> getExtSDCardPaths() {
        final List<String> paths = new ArrayList<String>();
        if (paths.size() > 0) {
            return paths;
        }

        //Environment.getExternalStorageState();
        String extFileStatus = Environment.getExternalStorageState();
        //Environment.getExternalStorageDirectory();
        File extFile = Environment.getExternalStorageDirectory();
        if (extFileStatus.endsWith(Environment.MEDIA_UNMOUNTED)
                && extFile.exists() && extFile.isDirectory()
                && extFile.canWrite()) {
            paths.add(extFile.getAbsolutePath());
        }

        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile.getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                paths.add(mountPath);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.i(TAG, e.getMessage());
        }
        return paths;
    }

    public double getDefaultLongitude() {
        return getMapConfig().getDouble(MapConfig.PARAM_LONGITUDE_DEFAULT, 0);
    }

    public double getDefaultLatitude() {
        return getMapConfig().getDouble(MapConfig.PARAM_LATITUDE_DEFAULT, 0);
    }

}
