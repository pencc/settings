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

    private String getIccId() {
        TelephonyManager tm;
        String iccid = null;
        try {
            tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission();
            }
            iccid = (String) tm.getSimSerialNumber();

        } catch (Exception e) {
            return e.getMessage();
        }

        return iccid;
    }

    private String getIMEI() {
        String imei;
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission();
            }
            //获取IMEI号
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            return e.getMessage();
        }
        return imei;
    }

    private String getIMSI() {
        String imsi;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission();
            }
            //获取IMSI号
            imsi = telephonyManager.getSubscriberId();
        } catch (Exception e) {
            return e.getMessage();
        }
        return imsi;
    }

    public void RequestPhoneStatePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = this.getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 100);
            }

            permissionCheck = this.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        return;
    }

    private String intToIp(int i) {
        return ((i >> 24) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                (i & 0xFF);
    }

    // get wifi ip
    private String getWlanIpAddress() {
        WifiManager wifiManager;
        String ip;
        try {
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ip = intToIp(ipAddress);
        } catch (Exception e) {
            ip = e.toString();
        }

        return ip;
    }

    // get wifi mac
    public static String getWlanMacAddress(String name) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase(name)) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    //GPRS连接下的ip
    public String getLocalIpAddress() {
        NetworkInfo info = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public int getMobileDbm() {
        int dbm = 0;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            RequestPhoneStatePermission();
        }
        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            if (null != cellInfoList)
            {
                for (CellInfo cellInfo : cellInfoList)
                {
                    if (cellInfo instanceof CellInfoGsm)
                    {
                        CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm)cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthGsm.getDbm();
                        Log.e("66666", "cellSignalStrengthGsm" + cellSignalStrengthGsm.toString());
                    }
                    else if (cellInfo instanceof CellInfoCdma)
                    {
                        CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma)cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthCdma.getDbm();
                        Log.e("66666", "cellSignalStrengthCdma" + cellSignalStrengthCdma.toString() );
                    }
                    else if (cellInfo instanceof CellInfoWcdma)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                        {
                            CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma)cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthWcdma.getDbm();
                            Log.e("66666", "cellSignalStrengthWcdma" + cellSignalStrengthWcdma.toString() );
                        }
                    }
                    else if (cellInfo instanceof CellInfoLte)
                    {
                        CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte)cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthLte.getDbm();
                    }
                }
            }
        }
        return dbm;
    }

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

        System.out.println(print_head + "IP(WIFI)：" + getWlanIpAddress());
        // IPV6??
        System.out.println(print_head + "MAC(wlan0)：" + getWlanMacAddress("wlan0"));
        System.out.println(print_head + "MAC(wlan1)：" + getWlanMacAddress("wlan1"));
        System.out.println(print_head + "IP(GPRS):" + getLocalIpAddress());
        System.out.println(print_head + "MAC(phone1)：" + getWlanMacAddress("rmnet_data1"));
        System.out.println(print_head + "MAC(phone2)：" + getWlanMacAddress("rmnet_data2"));

        System.out.println(print_head + "ICCID：" + getIccId());
        System.out.println(print_head + "IMSI：" + getIMSI());
        System.out.println(print_head + "IMEI：" + getIMEI());
        System.out.println(print_head + "SignalDbm：" + getMobileDbm());
    }
}