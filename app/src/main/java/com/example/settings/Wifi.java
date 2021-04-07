package com.example.settings;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static android.content.Context.WIFI_SERVICE;

public class Wifi {
    //将搜索到的wifi根据信号强度从强到弱进行排序
    private static void sortByLevel(ArrayList<ScanResult> list) {
        for(int i=0;i<list.size();i++)
            for(int j=1;j<list.size();j++)
            {
                if(list.get(i).level<list.get(j).level)  //level属性即为强度
                {
                    ScanResult temp = null;
                    temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
    }

    public static String getNBWifi(Context context) {
        WifiManager wifiManager;
        ArrayList<ScanResult> list;
        String result = "";
        wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);  //获得系统wifi服务
        list = (ArrayList<ScanResult>)wifiManager.getScanResults();
        sortByLevel(list);
        Iterator it = list.iterator();
        while(it.hasNext()) {
            result = result + "\n" + ((ScanResult)it.next()).toString();
        }
        return result += "\n";
    }

    public static String scanNBWifi(Context context) {
        WifiManager wifiManager;
        ArrayList<ScanResult> list;
        String result = "";
        wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);  //获得系统wifi服务
        list = (ArrayList<ScanResult>)wifiManager.getScanResults();
        sortByLevel(list);
        Iterator it = list.iterator();
        while(it.hasNext()) {
            result = result + "\n" + ((ScanResult)it.next()).toString();
        }
        return result += "\n";
    }

    public static String getConfiguredNetworks(Context context) {
        WifiManager wifiManager;
        List<WifiConfiguration> list;
        String result = "";
        wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);  //获得系统wifi服务
        list = (List<WifiConfiguration>)wifiManager.getConfiguredNetworks();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            result = result + "\n" + ((WifiConfiguration)it.next()).toString();
        }
        return result += "\n";
    }

    public static String getConnectionInfo(Context context) {
        WifiManager wifiManager;
        WifiInfo connInfo;
        String result = "";
        wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);  //获得系统wifi服务
        connInfo = wifiManager.getConnectionInfo();
        if(null == connInfo)
            return "null";
        result = connInfo.toString();
        return result += "\n";
    }
}
