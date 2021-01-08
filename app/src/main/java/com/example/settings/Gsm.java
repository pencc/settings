package com.example.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Gsm {
    public static String getIccId(Context context) {
        TelephonyManager tm;
        String iccid = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            iccid = (String) tm.getSimSerialNumber();

        } catch (Exception e) {
            return e.getMessage();
        }

        return iccid;
    }

    public static String getIMEI(Context context) {
        String imei;
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取IMEI号
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            return e.getMessage();
        }
        return imei;
    }

    public static String getIMSI(Context context) {
        String imsi;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取IMSI号
            imsi = telephonyManager.getSubscriberId();
        } catch (Exception e) {
            return e.getMessage();
        }
        return imsi;
    }

    public static String getLine1Number(Context context) {
        String imsi;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取IMSI号
            imsi = telephonyManager.getSubscriberId();
        } catch (Exception e) {
            return e.getMessage();
        }
        return imsi;
    }

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
        }
    }

    //GPRS连接下的ip
    public static String getLocalIpAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
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
    public static int getMobileDbm(Context context) {
        int dbm = 0;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            RequestPhoneStatePermission(context);
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
                        Log.e("DEBUG", "cellSignalStrengthGsm" + cellSignalStrengthGsm.toString());
                    }
                    else if (cellInfo instanceof CellInfoCdma)
                    {
                        CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma)cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthCdma.getDbm();
                        Log.e("DEBUG", "cellSignalStrengthCdma" + cellSignalStrengthCdma.toString() );
                    }
                    else if (cellInfo instanceof CellInfoWcdma)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                        {
                            CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma)cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthWcdma.getDbm();
                            Log.e("DEBUG", "cellSignalStrengthWcdma" + cellSignalStrengthWcdma.toString() );
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

}
