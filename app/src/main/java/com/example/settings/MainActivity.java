package com.example.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_READ_PHONE_STATE = 10000;
    private final String print_tag = "dev.info";
    private final String print_head = "--";

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(print_tag, "======================设备信息============================");
        Log.i(print_tag, print_head + "设备名：" + Device.getGlobalDeviceName(getContentResolver()));
        Log.i(print_tag, print_head + "产品名(build)：" + Device.getBuildProduct());
        Log.i(print_tag, print_head + "产品名(prop)：" + Device.getPropProductName());

        Log.i(print_tag, "======================网络信息============================");
        Log.i(print_tag, print_head + "IP(WIFI)：" + Wireless.getWlanIpAddress(this));
        // IPV6??
        Log.i(print_tag, print_head + "MAC(wlan0)：" + Wireless.getWlanMacAddress("wlan0"));
        Log.i(print_tag, print_head + "MAC(wlan1)：" + Wireless.getWlanMacAddress("wlan1"));
        Log.i(print_tag, print_head + "IP(GPRS):" + Gsm.getLocalIpAddress(this));
        Log.i(print_tag, print_head + "MAC(phone1)：" + Wireless.getWlanMacAddress("rmnet_data1"));
        Log.i(print_tag, print_head + "MAC(phone2)：" + Wireless.getWlanMacAddress("rmnet_data2"));

        //SIM卡状态
        Log.i(print_tag, "======================SIM卡信息============================");
        Log.i(print_tag, print_head + "SIM卡状态：" + Gsm.getSimState(this));
        Log.i(print_tag, print_head + "电话状态：" + Gsm.getCallState(this));
        Log.i(print_tag, print_head + "电话方位：" + Gsm.getCellLocation(this));
        Log.i(print_tag, print_head + "设备软件版本：" + Gsm.getDeviceSoftwareVersion(this));
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

        new Location().listen(this);
    }
}