package com.example.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_MOBILE_DUN;
import static android.net.ConnectivityManager.TYPE_VPN;
import static android.net.ConnectivityManager.TYPE_WIFI;
import static androidx.core.content.ContextCompat.getSystemService;

public class Gsm {

    private static final String LOG_TAG = "Gsm";
    private PhoneStateListener phoneStateListener; //定义监听器

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getSimState(Context context) {
        TelephonyManager tm;
        String simState = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            if (tm.getSimState() == tm.SIM_STATE_READY) {
                simState =  "有SIM卡";
            } else if (tm.getSimState() == tm.SIM_STATE_ABSENT) {
                simState = "无SIM卡";
            } else {
                simState = "SIM卡被锁定或未知的状态";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

        return simState;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getCallState(Context context) {
        TelephonyManager tm;
        String callState = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            if (tm.getCallState() == 0) {
                callState =  "无活动";
            } else if (tm.getCallState() == 1) {
                callState = "响铃声";
            } else {
                callState = "摘机";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

        return callState;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getCellLocation(Context context) {
        TelephonyManager tm;
        CellLocation cellLocation = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            cellLocation = tm.getCellLocation();
            if(null == cellLocation)
                return "snull";
        } catch (Exception e) {
            return e.getMessage();
        }

        return cellLocation.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getNetworkCountryIso(Context context) {
        TelephonyManager tm;
        String networkCountryIso = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            networkCountryIso = tm.getNetworkCountryIso();
        } catch (Exception e) {
            return e.getMessage();
        }

        return networkCountryIso;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getNetworkOperator(Context context) {
        TelephonyManager tm;
        String networkOperator = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            networkOperator = tm.getNetworkOperator();
        } catch (Exception e) {
            return e.getMessage();
        }

        return networkOperator;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm;
        String networkOperatorName = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            networkOperatorName = tm.getNetworkOperatorName();
        } catch (Exception e) {
            return e.getMessage();
        }

        return networkOperatorName;
    }

//    /** Network type is unknown */
//    public static final int NETWORK_TYPE_UNKNOWN = TelephonyProtoEnums.NETWORK_TYPE_UNKNOWN; // = 0.
//    /** Current network is GPRS */
//    public static final int NETWORK_TYPE_GPRS = TelephonyProtoEnums.NETWORK_TYPE_GPRS; // = 1.
//    /** Current network is EDGE */
//    public static final int NETWORK_TYPE_EDGE = TelephonyProtoEnums.NETWORK_TYPE_EDGE; // = 2.
//    /** Current network is UMTS */
//    public static final int NETWORK_TYPE_UMTS = TelephonyProtoEnums.NETWORK_TYPE_UMTS; // = 3.
//    /** Current network is CDMA: Either IS95A or IS95B*/
//    public static final int NETWORK_TYPE_CDMA = TelephonyProtoEnums.NETWORK_TYPE_CDMA; // = 4.
//    /** Current network is EVDO revision 0*/
//    public static final int NETWORK_TYPE_EVDO_0 = TelephonyProtoEnums.NETWORK_TYPE_EVDO_0; // = 5.
//    /** Current network is EVDO revision A*/
//    public static final int NETWORK_TYPE_EVDO_A = TelephonyProtoEnums.NETWORK_TYPE_EVDO_A; // = 6.
//    /** Current network is 1xRTT*/
//    public static final int NETWORK_TYPE_1xRTT = TelephonyProtoEnums.NETWORK_TYPE_1XRTT; // = 7.
//    /** Current network is HSDPA */
//    public static final int NETWORK_TYPE_HSDPA = TelephonyProtoEnums.NETWORK_TYPE_HSDPA; // = 8.
//    /** Current network is HSUPA */
//    public static final int NETWORK_TYPE_HSUPA = TelephonyProtoEnums.NETWORK_TYPE_HSUPA; // = 9.
//    /** Current network is HSPA */
//    public static final int NETWORK_TYPE_HSPA = TelephonyProtoEnums.NETWORK_TYPE_HSPA; // = 10.
//    /** Current network is iDen */
//    public static final int NETWORK_TYPE_IDEN = TelephonyProtoEnums.NETWORK_TYPE_IDEN; // = 11.
//    /** Current network is EVDO revision B*/
//    public static final int NETWORK_TYPE_EVDO_B = TelephonyProtoEnums.NETWORK_TYPE_EVDO_B; // = 12.
//    /** Current network is LTE */
//    public static final int NETWORK_TYPE_LTE = TelephonyProtoEnums.NETWORK_TYPE_LTE; // = 13.
//    /** Current network is eHRPD */
//    public static final int NETWORK_TYPE_EHRPD = TelephonyProtoEnums.NETWORK_TYPE_EHRPD; // = 14.
//    /** Current network is HSPA+ */
//    public static final int NETWORK_TYPE_HSPAP = TelephonyProtoEnums.NETWORK_TYPE_HSPAP; // = 15.
//    /** Current network is GSM */
//    public static final int NETWORK_TYPE_GSM = TelephonyProtoEnums.NETWORK_TYPE_GSM; // = 16.
//    /** Current network is TD_SCDMA */
//    public static final int NETWORK_TYPE_TD_SCDMA =
//            TelephonyProtoEnums.NETWORK_TYPE_TD_SCDMA; // = 17.
//    /** Current network is IWLAN */
//    public static final int NETWORK_TYPE_IWLAN = TelephonyProtoEnums.NETWORK_TYPE_IWLAN; // = 18.
//    /** Current network is LTE_CA {@hide} */
//    public static final int NETWORK_TYPE_LTE_CA = TelephonyProtoEnums.NETWORK_TYPE_LTE_CA; // = 19.
//
//    /** Max network type number. Update as new types are added. Don't add negative types. {@hide} */
//    public static final int MAX_NETWORK_TYPE = NETWORK_TYPE_LTE_CA;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getNetworkType(Context context) {
        TelephonyManager tm;
        String networkType = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            networkType = "" + tm.getNetworkType();
        } catch (Exception e) {
            return e.getMessage();
        }

        return networkType;
    }

    /**
     * 返回移动终端的类型：<br/>
     * #PHONE_TYPE_NONE 0
     * #PHONE_TYPE_GSM  1   移动和联通
     * #PHONE_TYPE_CDMA 2   电信
     * #PHONE_TYPE_SIP  3
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getPhoneType(Context context) {
        TelephonyManager tm;
        String phoneType = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            phoneType = "" + tm.getPhoneType();
        } catch (Exception e) {
            return e.getMessage();
        }

        return phoneType;
    }

    // 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字, MCC+MNC
    // MCC：Mobile Country Code，移动国家码，共3位，中国为460;
    // MNC:Mobile NetworkCode，移动网络码，共2位
    // 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
    // 合起来就是（也是Android手机中APN配置文件中的代码）：
    // 中国移动：46000 46002
    // 中国联通：46001
    // 中国电信：46003
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getSimOperator(Context context) {
        TelephonyManager tm;
        String simOperator = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            simOperator = tm.getSimOperator();
        } catch (Exception e) {
            return e.getMessage();
        }

        return simOperator;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getSimOperatorName(Context context) {
        TelephonyManager tm;
        String simOperatorName = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            simOperatorName = tm.getSimOperatorName();
        } catch (Exception e) {
            return e.getMessage();
        }

        return simOperatorName;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getVoiceMailAlphaTag(Context context) {
        TelephonyManager tm;
        String voiceMailAlphaTag = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            voiceMailAlphaTag = tm.getVoiceMailAlphaTag();
        } catch (Exception e) {
            return e.getMessage();
        }

        return voiceMailAlphaTag;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getVoiceMailNumber(Context context) {
        TelephonyManager tm;
        String voiceMailNumber = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            voiceMailNumber = tm.getVoiceMailNumber();
        } catch (Exception e) {
            return e.getMessage();
        }

        return voiceMailNumber;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String hasIccCard(Context context) {
        TelephonyManager tm;
        String hasIccCard = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            hasIccCard = "" + tm.hasIccCard();
        } catch (Exception e) {
            return e.getMessage();
        }

        return hasIccCard;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String isNetworkRoaming(Context context) {
        TelephonyManager tm;
        String isNetworkRoaming = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            isNetworkRoaming = "" + tm.isNetworkRoaming();
        } catch (Exception e) {
            return e.getMessage();
        }

        return isNetworkRoaming;
    }

//    /** Data connection activity: No traffic. */
//    public static final int DATA_ACTIVITY_NONE = 0x00000000;
//    /** Data connection activity: Currently receiving IP PPP traffic. */
//    public static final int DATA_ACTIVITY_IN = 0x00000001;
//    /** Data connection activity: Currently sending IP PPP traffic. */
//    public static final int DATA_ACTIVITY_OUT = 0x00000002;
//    /** Data connection activity: Currently both sending and receiving
//     *  IP PPP traffic. */
//    public static final int DATA_ACTIVITY_INOUT = DATA_ACTIVITY_IN | DATA_ACTIVITY_OUT;
//    /**
//     * Data connection is active, but physical link is down
//     */
//    public static final int DATA_ACTIVITY_DORMANT = 0x00000004;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDataActivity(Context context) {
        TelephonyManager tm;
        String getDataActivity = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            getDataActivity = "" + tm.getDataActivity();
        } catch (Exception e) {
            return e.getMessage();
        }

        return getDataActivity;
    }

//     * @see #DATA_DISCONNECTED    0
//     * @see #DATA_CONNECTING      1
//     * @see #DATA_CONNECTED       2
//     * @see #DATA_SUSPENDED       3
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDataState(Context context) {
        TelephonyManager tm;
        String getDataState = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            getDataState = "" + tm.getDataState();
        } catch (Exception e) {
            return e.getMessage();
        }

        return getDataState;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDeviceSoftwareVersion(Context context) {
        TelephonyManager tm;
        String devSoftVersion = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            devSoftVersion = (String) tm.getDeviceSoftwareVersion();

        } catch (Exception e) {
            return e.getMessage();
        }

        return devSoftVersion;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String getNAI(Context context) {
        TelephonyManager tm;
        String nai = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            nai = (String) tm.getNai();

        } catch (Exception e) {
            return e.getMessage();
        }

        return nai;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getNeighboringCellInfo(Context context) {
        TelephonyManager tm;
        List<NeighboringCellInfo> neighboringCellInfo = null;
        String result = "";
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            neighboringCellInfo = tm.getNeighboringCellInfo();
            if(null == neighboringCellInfo)
                return "snull";
        } catch (Exception e) {
            return e.getMessage();
        }
        for(NeighboringCellInfo nci : neighboringCellInfo) {
            result = result + " lac:" + nci.getLac()
                            + " cid:" + nci.getCid()
                            + " Rssi:" + nci.getRssi()
                            + " Psc:" + nci.getPsc()
                            + " NetworkType:" + nci.getNetworkType();
        }
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
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

    // 举例，一个典型的IMSI号码为460030912121001
    // IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称
    // IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的
    // 其组成为：
    // 1. 前6位数(TAC)是”型号核准号码”，一般代表机型
    // 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地
    // 3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号
    // 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getIMEI(Context context, int slotId) {
        String imei;
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取IMEI号
            imei = telephonyManager.getDeviceId(slotId);
        } catch (Exception e) {
            return e.getMessage();
        }
        return imei;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String getMEID(Context context, int slodId) {
        String imei;
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取MEID号
            imei = telephonyManager.getMeid(slodId);
        } catch (Exception e) {
            return e.getMessage();
        }
        return imei;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getLine1Number(Context context) {
        String line1Number;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取IMSI号
            line1Number = telephonyManager.getLine1Number();
        } catch (Exception e) {
            return e.getMessage();
        }
        return line1Number;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getSimCountryIso(Context context) {
        String simCountryIso;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            //获取IMSI号
            simCountryIso = telephonyManager.getSimCountryIso();
        } catch (Exception e) {
            return e.getMessage();
        }
        return simCountryIso;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void RequestPhoneStatePermission(Context context) {
        //if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }

//            permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
//            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//            }
//
//            permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 102);
//            }
        //}
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

    private static String getMacStr(byte[] macByte) {
        Formatter formatter = new Formatter();
        String sMac = "";
        for (int i = 0; i < macByte.length; i++) {
            sMac = formatter.format(Locale.getDefault(), "%02X%s",
                    macByte[i], (i < macByte.length - 1) ? "-" : "")
                    .toString();
        }
        return sMac;
    }

    public static String getAllInterface() {
        String interfaces = "";
        Enumeration<NetworkInterface> nifs;
        try {
            nifs = NetworkInterface.getNetworkInterfaces();
        } catch (Exception e) {
            e.printStackTrace();
            return interfaces;
        }

        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
            try {
                interfaces = interfaces + nif.getName() + ":" + nif.getDisplayName() + ":" + ((nif.getHardwareAddress()==null)?"null":getMacStr(nif.getHardwareAddress())) + ":" + nif.isUp() + "; ";
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return interfaces;
    }

    public static String getAllInterfaceIP() {
        String interfaces = "";
        Enumeration<NetworkInterface> nifs;
        try {
            nifs = NetworkInterface.getNetworkInterfaces();
        } catch (Exception e) {
            e.printStackTrace();
            return interfaces;
        }

        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
                    interfaces = interfaces + nif.getName() + ":" + nif.getDisplayName() + ":" + addr.getHostAddress() + "; ";
                }
            }
        }
        return interfaces;
    }

    public static String getVpnIpAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() == TYPE_VPN) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return "TYPE_VPN:" + inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if(info.getType() == TYPE_MOBILE) {
                return "TYPE_MOBILE";
            } else if(info.getType() == TYPE_MOBILE_DUN) {
                return "TYPE_MOBILE_DUN";
            } else if(info.getType() == TYPE_WIFI) {
                return "TYPE_WIFI";
            }
        }
        return null;
    }

    public static String getVpnNetworkInfo(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(TYPE_VPN);
        if (info != null && info.isConnected()) {
                return info.toString();
        }
        return null;
    }

    public static int getActiveNetworks(Context context) {
        int networks = 0;
        NetworkInfo[] info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getAllNetworkInfo();
        if (info != null){
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    networks++;
                }
            }
        }
        return networks;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isVpnInUse(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);
        boolean vpnInUse = false;
        try {
            vpnInUse = caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
        } catch (Exception e){

        }
        return vpnInUse;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isVpnInUse2(Context context) {
        List<String> networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    networkList.add(networkInterface.getName());
            }
        } catch (Exception ex) {
            Log.e("VPN", "isVpnUsing Network List didn't received");
        }

        return networkList.contains("ppp0");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getMobileDbm(Context context) {
        String dbm = "";
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
                        dbm = dbm + "indetity:" + ((CellInfoGsm)cellInfo).getCellIdentity().toString();
                        dbm = dbm + " signal:" + cellSignalStrengthGsm.getDbm() + ";\n";
                        Log.e("DEBUG", "cellSignalStrengthGsm" + cellSignalStrengthGsm.toString());
                    }
                    else if (cellInfo instanceof CellInfoCdma)
                    {
                        CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma)cellInfo).getCellSignalStrength();
                        dbm = "" + cellSignalStrengthCdma.getDbm();
                        Log.e("DEBUG", "cellSignalStrengthCdma" + cellSignalStrengthCdma.toString() );
                    }
                    else if (cellInfo instanceof CellInfoWcdma)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                        {
                            CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma)cellInfo).getCellSignalStrength();
                            dbm = "" + cellSignalStrengthWcdma.getDbm();
                            Log.e("DEBUG", "cellSignalStrengthWcdma" + cellSignalStrengthWcdma.toString() );
                        }
                    }
                    else if (cellInfo instanceof CellInfoLte)
                    {
                        dbm = dbm + "indetity:" + ((CellInfoLte)cellInfo).getCellIdentity().toString();
                        CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte)cellInfo).getCellSignalStrength();
                        dbm += " Rsrp:";
                        dbm += cellSignalStrengthLte.getDbm();
                        dbm += " Rsrq:";
                        dbm += cellSignalStrengthLte.getRsrq();
                        dbm += " Rssnr:";
                        dbm += cellSignalStrengthLte.getRssnr();
                        dbm += " Cqi:";
                        dbm += cellSignalStrengthLte.getCqi();
                        dbm += " timingAdvance:";
                        dbm += cellSignalStrengthLte.getTimingAdvance();
                        dbm += " AsuLevel:";
                        dbm += cellSignalStrengthLte.getAsuLevel();
                        dbm += "\n";
//                        Log.e("DEBUG", "getCellIdentity" + ((CellIdentityLte)((CellInfoLte)cellInfo).getCellIdentity()).toString());
//                        Log.e("DEBUG", "getCellSignalStrengthLte" + ((CellSignalStrengthLte)((CellInfoLte)cellInfo).getCellSignalStrength()).toString());
                    }
                }
            }
        }
        return dbm;
    }

    //设置监听器方法
    public void listen(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener(){
            @Override
            public void onCellLocationChanged(CellLocation location){
                Log.i(LOG_TAG,  "onCellLocationChanged：" + location.toString());
            }
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                Log.i(LOG_TAG,  "onSignalStrengthsChanged：" + signalStrength.toString());
            }
            @Override
            public void onCellInfoChanged(List<CellInfo> cellInfo) {
                Log.i(LOG_TAG,  "onCellInfoChanged：" + cellInfo.toString());
            }
        };

        tm.listen(phoneStateListener, PhoneStateListener.LISTEN_CELL_LOCATION); //注册监听器，设定不同的监听类型
    }
}
