package com.example.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_READ_PHONE_STATE = 10000;
    private final String print_tag = "dev.info";
    private final String print_head = "-----";

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission Granted

        } else if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            // Permission Denied

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestPermission(Context context) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("requestPermission","checkSelfPermission");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Log.i("requestPermission","shouldShowRequestPermissionRationale");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        0);
            } else {
                Log.i("requestPermission","requestPermissions");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        0);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission(this);

        Log.i(print_tag, "======================设备信息============================");
        Log.i(print_tag, print_head + "设备名：" + Device.getGlobalDeviceName(getContentResolver()));
        Log.i(print_tag, print_head + "产品名(build)：" + Device.getBuildProduct());
        Log.i(print_tag, print_head + "产品名(prop)：" + Device.getPropProductName());
        Log.i(print_tag, print_head + "设备模式(prop)：" + Device.getPropMode());
        Log.i(print_tag, print_head + "设备模式(Build)：" + android.os.Build.TYPE);

        Log.i(print_tag, "======================网络信息============================");
        Log.i(print_tag, print_head + "IP(WIFI)：" + Wireless.getWlanIpAddress(this));
        // IPV6??
        Log.i(print_tag, print_head + "MAC(wlan0)：" + Wireless.getWlanMacAddress("wlan0"));
        Log.i(print_tag, print_head + "MAC(wlan1)：" + Wireless.getWlanMacAddress("wlan1"));
        Log.i(print_tag, print_head + "IP(GPRS):" + Gsm.getLocalIpAddress(this));
        Log.i(print_tag, print_head + "IP(VPN):" + Gsm.getVpnIpAddress(this));
        Log.i(print_tag, print_head + "Network(VPN):" + Gsm.getVpnNetworkInfo(this));
        Log.i(print_tag, print_head + "isVpnInUse:" + Gsm.isVpnInUse(this));
        Log.i(print_tag, print_head + "isVpnInUse2:" + Gsm.isVpnInUse2(this));
        Log.i(print_tag, print_head + "MAC(phone1)：" + Wireless.getWlanMacAddress("rmnet_data1"));
        Log.i(print_tag, print_head + "MAC(phone2)：" + Wireless.getWlanMacAddress("rmnet_data2"));

        //SIM卡状态
        Log.i(print_tag, "======================SIM卡信息============================");
        Log.i(print_tag, print_head + "SIM卡状态：" + Gsm.getSimState(this));
        Log.i(print_tag, print_head + "电话状态：" + Gsm.getCallState(this));
        Log.i(print_tag, print_head + "电话方位：" + Gsm.getCellLocation(this));
        Log.i(print_tag, print_head + "设备软件版本：" + Gsm.getDeviceSoftwareVersion(this));
        Log.i(print_tag, print_head + "网络访问标识符：" + Gsm.getNAI(this));
        Log.i(print_tag, print_head + "ICCID(SimSerial)：" + Gsm.getIccId(this));
        Log.i(print_tag, print_head + "IMSI：" + Gsm.getIMSI(this));
        Log.i(print_tag, print_head + "IMEI：" + Gsm.getIMEI(this));
        Log.i(print_tag, print_head + "Number：" + Gsm.getLine1Number(this));
        Log.i(print_tag, print_head + "附近的电话的信息：" + Gsm.getNeighboringCellInfo(this));
        Log.i(print_tag, print_head + "ISO标准国家码：" + Gsm.getNetworkCountryIso(this));
        Log.i(print_tag, print_head + "MCC+MNC：" + Gsm.getNetworkOperator(this));
        Log.i(print_tag, print_head + "(当前已注册的用户)的名字：" + Gsm.getNetworkOperatorName(this));
        Log.i(print_tag, print_head + "当前使用的网络类型(13-LTE)：" + Gsm.getNetworkType(this));
        Log.i(print_tag, print_head + "手机类型(1-移/联,2-电)：" + Gsm.getPhoneType(this));
        Log.i(print_tag, print_head + "SIM卡的国家码：" + Gsm.getSimCountryIso(this));
        Log.i(print_tag, print_head + "MCC+MNC：" + Gsm.getSimOperator(this));
        Log.i(print_tag, print_head + "服务商名称：" + Gsm.getSimOperatorName(this));
        Log.i(print_tag, print_head + "语音邮件识别符：" + Gsm.getVoiceMailAlphaTag(this));
        Log.i(print_tag, print_head + "获取语音邮件号码：" + Gsm.getVoiceMailNumber(this));
        Log.i(print_tag, print_head + "ICC卡是否存在：" + Gsm.hasIccCard(this));
        Log.i(print_tag, print_head + "是否漫游：" + Gsm.isNetworkRoaming(this));
        Log.i(print_tag, print_head + "获取数据活动状态：" + Gsm.getDataActivity(this));
        Log.i(print_tag, print_head + "获取数据连接状态：" + Gsm.getDataState(this));
        Log.i(print_tag, print_head + "SignalDbm：" + Gsm.getMobileDbm(this));

        Log.i(print_tag, print_head + "WIFINB：" + Wifi.getNBWifi(this));
        Log.i(print_tag, print_head + "已配置WIFI网络：" + Wifi.getConfiguredNetworks(this));
        Log.i(print_tag, print_head + "WIFI链接信息：" + Wifi.getConnectionInfo(this));

        //new Location().getGnssLocation(this);
        //new Location().listen(this);
        //new com.example.settings.Gsm().listen(this);

        Log.i(print_tag, "======================软件信息============================");
        //Log.i(print_tag, print_head + "已安装APK：" + SoftWare.getInstalledApplications(this, "oneplus"));
        //Log.i(print_tag, print_head + "已安装Pkg：" + SoftWare.getInstalledPackage(this, "oneplus"));
        //SoftWare.appWriteToFile(this, "oneplus");
        //SoftWare.appReadFromFile(this, "oneplus");
//        new SoftWare().pkgWriteToFile2(this, "oneplus");
//        new SoftWare().pkgReadFromFile2(this, "oneplus");

        final boolean enableSoftInfo = false;
        if(enableSoftInfo) {
            new SoftWare().getAppInfo(this, "oneplus");
            new SoftWare().getPkgInfo(this, "oneplus");
            new SoftWare().getOnePkgInfo(this, "com.oneplus.filemanager");
        }

        Log.i(print_tag, "======================数据信息============================");
        Data.getAllContactInfo(this);


    }
}