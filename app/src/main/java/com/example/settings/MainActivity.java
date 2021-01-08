package com.example.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_READ_PHONE_STATE = 10000;
    private final String print_tag = "dev.info";
    private final String print_head = "--";

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(print_tag, print_head + "设备名：" + Device.getGlobalDeviceName(getContentResolver()));
        Log.i(print_tag, print_head + "产品名(build)：" + Device.getBuildProduct());
        Log.i(print_tag, print_head + "产品名(prop)：" + Device.getPropProductName());

        Log.i(print_tag, print_head + "IP(WIFI)：" + Wireless.getWlanIpAddress(this));
        // IPV6??
        Log.i(print_tag, print_head + "MAC(wlan0)：" + Wireless.getWlanMacAddress("wlan0"));
        Log.i(print_tag, print_head + "MAC(wlan1)：" + Wireless.getWlanMacAddress("wlan1"));

        Log.i(print_tag, print_head + "IP(GPRS):" + Gsm.getLocalIpAddress(this));
        Log.i(print_tag, print_head + "MAC(phone1)：" + Wireless.getWlanMacAddress("rmnet_data1"));
        Log.i(print_tag, print_head + "MAC(phone2)：" + Wireless.getWlanMacAddress("rmnet_data2"));

        Log.i(print_tag, print_head + "ICCID(SimSerial)：" + Gsm.getIccId(this));
        Log.i(print_tag, print_head + "IMSI：" + Gsm.getIMSI(this));
        Log.i(print_tag, print_head + "IMEI：" + Gsm.getIMEI(this));
        Log.i(print_tag, print_head + "SignalDbm：" + Gsm.getMobileDbm(this));
    }
}