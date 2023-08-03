package com.sh3h.datautil.data.remote;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.entity.DUUpdateInfo;
import com.sh3h.datautil.data.entity.DUUpdateResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.TextUtil;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class Downloader {
    private static final int TIMEOUT = 10 * 1000;// 超时
    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private int newApkVersion = 0;
    private int newDataVersion = 0;

    @Inject
    public Downloader(@ApplicationContext Context context,
                      ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
    }

    /**
     * download each file
     *
     * @param duUpdateInfo
     * @param items
     * @return
     */
    public Observable<DUFileResult> downloadFile(DUUpdateInfo duUpdateInfo, List<DUUpdateResult.Item> items) {
        if ((items == null) || (items.size() <= 0) || (duUpdateInfo == null)) {
            return null;
        }

        DUUpdateResult.Item item = items.remove(0);
        if (TextUtil.isNullOrEmpty(item.getUrl())) {
            return null;
        }

        String url = item.getUrl();
        String version = item.getVersion();
        int ver = TextUtil.getInt(version);
        int index = url.lastIndexOf("/");
        if (index <= 0) {
            return null;
        }

        String name = url.substring(index + 1);
        if (TextUtil.isNullOrEmpty(name)) {
            return null;
        }

        if (name.contains("app")) {
            int appVersion = duUpdateInfo.getAppVersion();
            if (appVersion >= ver) {
                return downloadFile(duUpdateInfo, items);
            }

            String localApkPath = checkLocalApkFile(ver);
            if (localApkPath != null) {
                //installLocalApk(localApkPath);
                // exit system
                return downloadFile(url, name, true);
            } else {
                newApkVersion = ver;
                return downloadFile(url, name, false);
            }
        } else if (name.contains("data")) {
//            int dataVersion = duUpdateInfo.getAppVersion();
            int dataVersion = duUpdateInfo.getDataVersion();
            if (dataVersion >= ver) {
                return downloadFile(duUpdateInfo, items);
            } else {
                newDataVersion = ver;
                return downloadFile(url, name, false);
            }
        } else {
            return null;
        }
    }

    /**
     * 判断是否有apk文件
     */
    private String checkLocalApkFile(int remoteAppVersion) {
        File dir = mConfigHelper.getUpdateFolderPath();
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.isDirectory() && file.listFiles() != null && file.listFiles().length > 0) {
                for (File apkFile : file.listFiles()) {
                    if (apkFile.getName().contains(".apk")) {
                        String path = isDownloader(apkFile, remoteAppVersion);
                        if (path != null) {
                            return path;
                        }
                    }
                }
            }
        }

        return null;
    }

    //判断服务器上的版本是否已经下载到本地
    private String isDownloader(File apkFile, int remoteAppVersion) {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(apkFile.getPath(), PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {
            return null;
        }

        int localAppVersion = packageInfo.versionCode;
        return localAppVersion == remoteAppVersion ? apkFile.getPath() : null;
    }

    /**
     * @param url
     * @param name
     * @param isFileExisting
     * @return
     */
    public Observable<DUFileResult> downloadFile(final String url,
                                                 final String name,
                                                 boolean isFileExisting) {
        if (isFileExisting) {
            return Observable.create(new Observable.OnSubscribe<DUFileResult>() {
                @Override
                public void call(Subscriber<? super DUFileResult> subscriber) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }

                    File file = new File(mConfigHelper.getUpdateFolderPath(), name);
                    subscriber.onNext(getDUFileResult(100, name, file.getPath()));
                }
            });
        } else {
            return Observable.create(new Observable.OnSubscribe<DUFileResult>() {
                @Override
                public void call(Subscriber<? super DUFileResult> subscriber) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }

                    try {
                        //downloadFile1(url, name, subscriber);
                        downloadFile2(url, name, subscriber);
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onError(new Throwable(e.getMessage()));
                    }
                }
            });
        }
    }

    /**
     * @param strUrl
     * @param strName
     * @param subscriber
     * @throws Exception
     */
    private void downloadFile1(String strUrl, String strName,
                               Subscriber<? super DUFileResult> subscriber) throws Exception {
        URL url = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        // 获取下载文件的size
        int totalLength = httpURLConnection.getContentLength();
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("httpURLConnection is failure!");
            //这个地方应该加一个下载失败的处理，但是，因为我们在外面加了一个try---catch，已经处理了Exception,
            //所以不用处理
        }

        File strPath = new File(mConfigHelper.getUpdateFolderPath(), strName);
        InputStream inputStream = httpURLConnection.getInputStream();
        OutputStream outputStream = new FileOutputStream(strPath, false);// 文件存在则覆盖掉

        byte buffer[] = new byte[1024];
        int length = 0;
        int currentLength = 0;// 已经下载好的大小
        int percent = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
            currentLength += length;// 时时获取下载到的大小
            if ((currentLength > 0) && (currentLength < totalLength)) {
                percent = (int) (currentLength * 100.0 / totalLength);
                subscriber.onNext(getDUFileResult(percent, strName, strPath.getPath()));
            }
        }

        subscriber.onNext(getDUFileResult(100, strName, strPath.getPath()));
        subscriber.onCompleted();
        httpURLConnection.disconnect();
        inputStream.close();
        outputStream.close();
    }

    /**
     * @param strUrl
     * @param strName
     * @param subscriber
     * @throws Exception
     */
    private void downloadFile2(String strUrl, final String strName,
                               final Subscriber<? super DUFileResult> subscriber) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(strUrl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                subscriber.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long totalLength = response.body().contentLength();
                    File file = new File(mConfigHelper.getUpdateFolderPath(), strName);
                    String strPath = file.getPath();
                    fos = new FileOutputStream(file);
                    byte[] buf = new byte[2048];
                    int length = 0;
                    int curLength = 0;
                    int percent = 0;
                    while ((length = is.read(buf)) != -1) {
                        fos.write(buf, 0, length);
                        curLength += length;
                        percent = (int) (curLength * 100.0 / totalLength);
                        DUFileResult duFileResult = getDUFileResult(percent, strName, strPath);
                        if (strName.contains("apk")) {
                            duFileResult.setVersion(newApkVersion);
                        } else {
                            duFileResult.setVersion(newDataVersion);
                        }

                        subscriber.onNext(duFileResult);
                    }

//                    if (percent != 100) {
//                        subscriber.onNext(getDUFileResult(100, strName, strPath));
//                    }

                    fos.flush();
                } catch (IOException e) {
                    subscriber.onError(e);
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }

                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    subscriber.onCompleted();
                }
            }
        });
    }

    private DUFileResult getDUFileResult(int percent, String name, String path) {
        return new DUFileResult(percent, name, path);
    }

    /**
     * 解压文件到指定目录
     *
     * @param zipFile
     * @param
     * @author isea533
     */
//    public int upzipFile(File zipFile, String folderPath) throws ZipException, IOException {
//        //public static void upZipFile() throws Exception{
//        ZipFile zfile = new ZipFile(zipFile);
//        Enumeration zList = zfile.entries();
//        ZipEntry ze = null;
//        byte[] buf = new byte[1024];
//        while (zList.hasMoreElements()) {
//            ze = (ZipEntry) zList.nextElement();
//            if (ze.isDirectory()) {
//                String dirStr = folderPath + "/" + ze.getName();
//                //dirstr.trim();
//                //dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
//                File file = new File(dirStr);
//                if (!file.exists()) {
//                    file.mkdir();
//                }
//                continue;
//            }
//            OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(folderPath, ze.getName())));
//            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
//            int readLen = 0;
//            while ((readLen = is.read(buf, 0, 1024)) != -1) {
//                os.write(buf, 0, readLen);
//            }
//            is.close();
//            os.close();
//        }
//        zfile.close();
//        Log.d("upZipFile", "finishssssssssssssssssssss");
//        return 0;
//    }
}
