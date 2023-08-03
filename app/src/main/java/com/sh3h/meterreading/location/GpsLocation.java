package com.sh3h.meterreading.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.meterreading.util.CoordTransformUtil;
import com.sh3h.meterreading.util.TransformUtil;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhangjing on 2016/6/20.
 */
@Singleton
public class GpsLocation {

    public static class MRLocation {
        private double mLongitude;
        private double mLatitude;
        private float mDirection;
        private float mAccuracy;

        public MRLocation() {
            mLongitude = 0.0;
            mLatitude = 0.0;
            mDirection = 0.0f;
            mAccuracy = 0.0f;
        }

        public MRLocation(double longitude,
                          double latitude,
                          float direction,
                          float accuracy) {
            mLongitude = longitude;
            mLatitude = latitude;
            mDirection = direction;
            mAccuracy = accuracy;
        }

        public void setLongitude(double longitude) {
            mLongitude = longitude;
        }

        public double getLongitude() {
            return mLongitude;
        }

        public void setLatitude(double latitude) {
            mLatitude = latitude;
        }

        public double getLatitude() {
            return mLatitude;
        }

        public void setDirection(float direction) {
            mDirection = direction;
        }

        public float getDirection() {
            return mDirection;
        }

        public void setAccuracy(float accuracy) {
            mAccuracy = accuracy;
        }

        public float getAccuracy() {
            return mAccuracy;
        }
    }

    public interface MRLocationListener {
        void updateLocation(boolean isLocated, MRLocation mrLocation);
    }

    private static final String TAG = "GpsLocation";

    private final Context mContext;
    private LocationManager mLocationManager;
    private List<MRLocationListener> mMRLocationListenerList;
    private boolean mIsGpsLocated;
    private MRLocation mMRLocation;
    private int mReferenceCount;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private boolean mNeedCoordTransform;

    @Inject
    public GpsLocation(@ApplicationContext Context context) {
        mContext = context;
        mLocationManager = null;
        mMRLocationListenerList = null;
        mIsGpsLocated = false;
        mMRLocation = null;
        mReferenceCount = 0;
        mLocationClient = null;
        mMyLocationListener = null;
        mNeedCoordTransform = false;

        LogUtil.i(TAG, "---GpsLocation----");
    }

    /**
     * @param isBaiduLoc
     */
    public void initLocation(boolean isBaiduLoc, boolean needCoordTransform) {
        LogUtil.i(TAG, "---initLocation 1----");
        if (mReferenceCount++ > 0) {
            LogUtil.i(TAG, "---initLocation 2----");
            return;
        }

        mMRLocationListenerList = new ArrayList<>();
        mIsGpsLocated = false;
        mMRLocation = new MRLocation();
        //mLock = new byte[0];
        mNeedCoordTransform = needCoordTransform;

        // gps location & listener
        if (isBaiduLoc) {
            mLocationClient = new LocationClient(mContext);
            mMyLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(mMyLocationListener);
            setLocationOption();
            mLocationClient.start();
        } else {
            try {
                mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000, 1, mGpsLocationListener);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 1, mNetworkLocationListener);
                //mLocationManager.addGpsStatusListener(mMyGpsStatusListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        LogUtil.i(TAG, "---initLocation 3----");
    }

    /**
     * @param isBaiduLoc
     */
    public void destroyLocation(boolean isBaiduLoc) {
        LogUtil.i(TAG, "---destroyLocation 1----");
        if (--mReferenceCount > 0) {
            LogUtil.i(TAG, "---destroyLocation 2----");
            return;
        }

        if (isBaiduLoc) {
            mLocationClient.unRegisterLocationListener(mMyLocationListener);
            mLocationClient.stop();
        } else {
            try {
                mLocationManager.removeUpdates(mGpsLocationListener);
                mLocationManager.removeUpdates(mNetworkLocationListener);
                //mLocationManager.removeGpsStatusListener(mMyGpsStatusListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        LogUtil.i(TAG, "---destroyLocation 3----");
    }

    /**
     * @param mrLocationListener
     */
    public void registerMRLocationListener(MRLocationListener mrLocationListener) {
        if (mReferenceCount <= 0) {
            return;
        }

        if (mrLocationListener != null) {
            mMRLocationListenerList.add(mrLocationListener);
        }
    }

    /**
     * @param mrLocationListener
     */
    public void unRegisterMRLocationListener(MRLocationListener mrLocationListener) {
        if (mReferenceCount <= 0) {
            return;
        }

        if (mrLocationListener != null) {
            mMRLocationListenerList.remove(mrLocationListener);
        }
    }

    /**
     * @return
     */
    public MRLocation getMRLocation() {
        return mMRLocation;
    }

    /**
     * @return
     */
    public boolean isGpsLocated() {
        return mIsGpsLocated;
    }

    /**
     * set location options for location client
     */
    private void setLocationOption() {
        if (mLocationClient != null) {
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
            option.setCoorType("gcj02");//返回的定位结果是百度经纬度，百度手机地图对外接口中的坐标系默认是bd09ll
            option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
            option.setIsNeedAddress(false);//返回的定位结果包含地址信息
            option.setOpenGps(true);//可选，默认false,设置是否使用gps
            option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
            mLocationClient.setLocOption(option);
        }
    }

    /**
     *
     */
    private LocationListener mGpsLocationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            LogUtil.i(TAG, "---onLocationChanged(gps)----");
            updateLocation(location);
        }
    };

    /**
     *
     */
    private LocationListener mNetworkLocationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            LogUtil.i(TAG, "---onLocationChanged(network)----");
            updateLocation(location);
        }
    };

    /**
     * @param location
     */
    private void updateLocation(Location location) {
        LogUtil.i(TAG, "---updateLocation----");

        mIsGpsLocated = false;
        mMRLocation = new MRLocation();
        if (location == null) {
            return;
        }

        mIsGpsLocated = true;
        double[] xy = TransformUtil.transformBL2XY(location.getLatitude(), location.getLongitude());
        mMRLocation.setLongitude(xy[0]);
        mMRLocation.setLatitude(xy[1]);

        if (location.hasBearing()) {
            mMRLocation.setDirection(location.getBearing());
        }

        if (location.hasAccuracy()) {
            mMRLocation.setAccuracy(location.getAccuracy());
        }

        for (MRLocationListener mrLocationListener : mMRLocationListenerList) {
            mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation);
        }
    }

    /**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mIsGpsLocated = false;
            mMRLocation = new MRLocation();
            if (location == null) {
                return;
            }

            if ((location.getLocType() == BDLocation.TypeGpsLocation)
                    || (location.getLocType() == BDLocation.TypeNetWorkLocation)
                    || (location.getLocType() == BDLocation.TypeOffLineLocation)) {
                mIsGpsLocated = true;
                if (mNeedCoordTransform) {
                    //double[] gcj02 = CoordTransformUtil.bd09togcj02(120.688539,28.008895);
                    //double[] wgs84 = CoordTransformUtil.gcj02towgs84(gcj02[0], gcj02[1]);
                    double[] gcj02 = new double[]{location.getLongitude(), location.getLatitude()};
                    double[] wgs84 = CoordTransformUtil.gcj02towgs84(location.getLongitude(), location.getLatitude());
                    double[] xy = TransformUtil.transformBL2XY(wgs84[1], wgs84[0]);
                    mMRLocation.setLongitude(xy[0]);
                    mMRLocation.setLatitude(xy[1]);

                    LogUtil.i(TAG, "---onReceiveLocation gcj02["
                            + gcj02[0] + ", " + gcj02[1] + "] wgs84["
                            + wgs84[0] + ", " + wgs84[1] + "] xy["
                            + xy[0] + ", " + xy[1] + "]");
                } else {
                    mMRLocation.setLongitude(location.getLongitude());
                    mMRLocation.setLatitude(location.getLatitude());

                    LogUtil.i(TAG, "---onReceiveLocation [" + mMRLocation.getLongitude()
                            + ", " + mMRLocation.getLatitude() + "]");
                }
                mMRLocation.setDirection(location.getDirection());
                mMRLocation.setAccuracy(location.getRadius());

                for (MRLocationListener mrLocationListener : mMRLocationListenerList) {
                    mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation);
                }
            } else {
                LogUtil.i(TAG, "---onReceiveLocation [gps is invalid]");
            }
        }
    }

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            switch (action) {
//                case SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR:
//                    // key 验证失败，相应处理
//                    Toast.makeText(context,
//                            SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR,
//                            Toast.LENGTH_LONG).show();
//                    break;
//                case SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR:
//                    Toast.makeText(context,
//                            SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR,
//                            Toast.LENGTH_LONG).show();
//                    break;
//                case SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE:
//                    Toast.makeText(context,
//                            SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE,
//                            Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
//    };
}
