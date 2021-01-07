package com.example.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.provider.Settings;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_READ_PHONE_STATE = 10000;
    private final String print_head = "------";
    private int signal_dbm = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView global_devname_view = (TextView) findViewById(R.id.global_devname);
        String gdev_name = Settings.Global.getString(getContentResolver(),
                Settings.Global.DEVICE_NAME);
        global_devname_view.setText(gdev_name);
        System.out.println(print_head + "设备名：" + gdev_name);

        TextView build_devname_view = (TextView) findViewById(R.id.build_devname);
        String bdev_name = android.os.Build.PRODUCT;
        build_devname_view.setText(bdev_name);
        System.out.println(print_head + "产品名(build)：" + bdev_name);

        TextView prop_devname_view = (TextView) findViewById(R.id.prop_devname);
        String prop_dev_name = null;
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClass.getMethod("get", String.class);
            Object object = new Object();
            Object obj = getMethod.invoke(object, "ro.product.name");
            if(null != obj)
                prop_dev_name = (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        prop_devname_view.setText("NULL");
        if(null != prop_dev_name) {
            prop_devname_view.setText(prop_dev_name);
            System.out.println(print_head + "产品名(prop)：" + gdev_name);
        }

        System.out.println(print_head + "IP(WIFI)：" + Wireless.getWlanIpAddress(this));
        // IPV6??
        System.out.println(print_head + "MAC(wlan0)：" + Wireless.getWlanMacAddress("wlan0"));
        System.out.println(print_head + "MAC(wlan1)：" + Wireless.getWlanMacAddress("wlan1"));
        System.out.println(print_head + "IP(GPRS):" + Gsm.getLocalIpAddress(this));
        System.out.println(print_head + "MAC(phone1)：" + Wireless.getWlanMacAddress("rmnet_data1"));
        System.out.println(print_head + "MAC(phone2)：" + Wireless.getWlanMacAddress("rmnet_data2"));

        System.out.println(print_head + "ICCID：" + Gsm.getIccId(this));
        System.out.println(print_head + "IMSI：" + Gsm.getIMSI(this));
        System.out.println(print_head + "IMEI：" + Gsm.getIMEI(this));
        System.out.println(print_head + "SignalDbm：" + Gsm.getMobileDbm(this));
    }
}