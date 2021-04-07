package com.example.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GnssMeasurementsEvent;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class Location {

    private static final String LOG_TAG = "LocationActivity";

    private LocationManager locationManager;
    private GpsStatus.Listener gpsListener;
    private GnssStatus.Callback mGnssStatusCallback ;
    private GnssMeasurementsEvent.Callback gnssMeasurementEventListener;

    public static void RequestPhoneStatePermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 100);
            }

            permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }

            permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 102);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void listen(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            RequestPhoneStatePermission(context);
            return;
        }
        // 获取manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.v(LOG_TAG, "fail to check gps");
            return;
        } else {
            Log.i(LOG_TAG, "所有的Provider：" + locationManager.getProviders(true));
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
            Log.i(LOG_TAG, "最好的Provider：" + locationManager.getBestProvider(criteria, true));
            LocationProvider bestProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                RequestPhoneStatePermission(context);
                return;
            }
            android.location.Location location = locationManager.getLastKnownLocation(bestProvider.getName());

            if (location != null) {
                double longitude = location.getLongitude(); // 纬度
                double latitude = location.getLatitude(); // 经度
                Log.i(LOG_TAG, "经度：" + longitude + ",纬度:" + latitude);
            }

            GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {
                    switch (event) {
                        case GpsStatus.GPS_EVENT_FIRST_FIX:
                            Log.v(LOG_TAG, "第一次定位");
                            break;
                        case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                            //Log.v(LOG_TAG, "卫星状态发生改变");
                            break;
                        case GpsStatus.GPS_EVENT_STARTED:
                            Log.v(LOG_TAG, "启动定位");
                            break;
                        case GpsStatus.GPS_EVENT_STOPPED:
                            Log.v(LOG_TAG, "结束定位");
                            break;
                    }

                }
            };

            GpsStatus.NmeaListener nmeaListener = new GpsStatus.NmeaListener() {
                public void onNmeaReceived(long timestamp, String nmea) {
                    String nmeaResult = "";
                    //check nmea's checksum
                    String[]rawNmeaSplit = nmea.split(",");
//                    Log.i(LOG_TAG, "nvme quality:" + rawNmeaSplit[6]
//                            + " location:" + rawNmeaSplit[2]+ " "+ rawNmeaSplit[3] +"," + rawNmeaSplit[4] + ""+ rawNmeaSplit[5]
//                            + "satellites:" + rawNmeaSplit[7]);
                    for(String nmeaRaw:rawNmeaSplit)
                        nmeaResult = nmeaResult + "," + nmeaRaw;
                    Log.i(LOG_TAG, "nmea raw:" + nmeaResult);
                }
            };

            OnNmeaMessageListener nmeaListener2 = new OnNmeaMessageListener() {
                @Override
                public void onNmeaMessage(String nmea, long l) {
                    Log.i(LOG_TAG, "nmea2 raw:" + nmea);
                }
            };

            locationManager.addGpsStatusListener(gpsListener);

            android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(null != lastKnownLocation) {
//                Log.v(LOG_TAG, "上次GPS位置发生改变," + "时间:" + lastKnownLocation.getTime() + "经度：" + lastKnownLocation.getLongitude() + "纬度："
//                        + lastKnownLocation.getLatitude() + "海拔：" + lastKnownLocation.getAltitude());
                Log.i(LOG_TAG, "上次GPS位置改变," + "时间:" + location.getTime() + "经度：" + location.getLongitude() + "纬度："
                        + location.getLatitude() + "海拔：" + location.getAltitude() + " from mock:" + location.isFromMockProvider()
                        + " Provider:" + location.getProvider() + " describe:" + location.describeContents()
                        + " accuracy:" + location.getAccuracy() + " bearing:" + location.getBearing()
                        + " erTimeNanos:" + location.getElapsedRealtimeNanos() + " BearingAD:" + location.getBearingAccuracyDegrees()
                        + " extras:" + location.getExtras() + " speed:" + location.getSpeed()
                        + " SAMeterPerS:" + location.getSpeedAccuracyMetersPerSecond() + " VAM:" + location.getVerticalAccuracyMeters());
            }

            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

            // 3秒更新一次，或最小位移变化超过1米更新一次；
            // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
//            locationManager.addNmeaListener(nmeaListener);
//            locationManager.addNmeaListener(nmeaListener2);
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Log.v(LOG_TAG, "GPS位置发生改变," + "时间:" + location.getTime() + "经度：" + location.getLongitude() + "纬度："
                    + location.getLatitude() + "海拔：" + location.getAltitude() + " from mock:" + location.isFromMockProvider()
                    + " Provider:" + location.getProvider() + " describe:" + location.describeContents()
                    + " accuracy:" + location.getAccuracy() + " bearing:" + location.getBearing()
                    + " erTimeNanos:" + location.getElapsedRealtimeNanos() + " BearingAD:" + location.getBearingAccuracyDegrees()
                    + " extras:" + location.getExtras() + " speed:" + location.getSpeed()
                    + " SAMeterPerS:" + location.getSpeedAccuracyMetersPerSecond() + " VAM:" + location.getVerticalAccuracyMeters());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(LOG_TAG, "当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(LOG_TAG, "当前GPS状态为服务区外状态");
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(LOG_TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }
    };

//    GpsStatus.Listener listener = new GpsStatus.Listener() {
//        public void onGpsStatusChanged(int event) {
//            switch (event) {
//                // 第一次定位
//                case GpsStatus.GPS_EVENT_FIRST_FIX:
//
//                    break;
//                // 卫星状态改变
//                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                    GpsStatus gpsStauts= locationManager.getGpsStatus(null); // 取当前状态
//                    int maxSatellites = gpsStauts.getMaxSatellites(); //获取卫星颗数的默认最大值
//                    Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();//创建一个迭代器保存所有卫星
//                    int gpsCount = 0;
//                    while (it.hasNext() && gpsCount <= maxSatellites) {
//                        GpsSatellite s = it.next();
//                        //可见卫星数量
//                        if(s.usedInFix()){
//                            //已定位卫星数量
//                            gpsCount++;
//                        }
//                    }
//                    if( gpsCount == 0 && gpsStatus == 1 ){
//                        //setGpsStatus(0);
//                    }
//                    break;
//                // 定位启动
//                case GpsStatus.GPS_EVENT_STARTED:
//                    break;
//                // 定位结束
//                case GpsStatus.GPS_EVENT_STOPPED:
//                    break;
//            }
//        };
//    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getGnssLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mGnssStatusCallback = new GnssStatus.Callback() {
            @Override
            public void onSatelliteStatusChanged(GnssStatus status) {
                int i = 0;
                int count = status.getSatelliteCount();
                Log.i(LOG_TAG, "onSatelliteStatusChan count:" + count);
                for(i = 0; i < count; i++) {
                    Log.i(LOG_TAG, "onSatelliteStatusChanged count:" + status.getSatelliteCount()
                            + "AzimuthDegrees:" + status.getAzimuthDegrees(i)
                            + " CarrierFrequencyHz:" + status.getCarrierFrequencyHz(i)
                            + " Cn0DbHz:" + status.getCn0DbHz(i)
                            + " ConstellationType:" + status.getConstellationType(i)
                            + " ElevationDegrees:" + status.getElevationDegrees(i)
                            + " Svid:" + status.getSvid(i)

                    );
                }
            }
        };
        gnssMeasurementEventListener = new GnssMeasurementsEvent.Callback() {
            @Override
            public void onGnssMeasurementsReceived(GnssMeasurementsEvent eventArgs) {
                super.onGnssMeasurementsReceived(eventArgs);
                //这里我们获取到了回调的测量数据容器：GnssMeasurementsEvent eventArgs
                //TODO:
                Log.i(LOG_TAG, "gnss measure: " + eventArgs.toString());
            }

            @Override
            public void onStatusChanged(int status) {
                super.onStatusChanged(status);
                Log.i(LOG_TAG, "gnss status changed: " + status);
            }
        };
        try {
            locationManager.registerGnssStatusCallback(mGnssStatusCallback);
            locationManager.registerGnssMeasurementsCallback(gnssMeasurementEventListener);
        } catch (SecurityException e) {
            Log.e(LOG_TAG,"Error "+e);
        }
    }
}
