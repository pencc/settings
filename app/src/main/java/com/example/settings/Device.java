package com.example.settings;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;

public class Device {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getGlobalDeviceName(ContentResolver resolver) {
        return Settings.Global.getString(resolver, Settings.Global.DEVICE_NAME);
    }

    public static String getBuildProduct() {
        return android.os.Build.PRODUCT;
    }

    public static String getPropProductName() {
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
        return prop_dev_name;
    }

    public static String getSerialNo() {
        String prop_dev_name = null;
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClass.getMethod("get", String.class);
            Object object = new Object();
            Object obj = getMethod.invoke(object, "ro.serialno");
            if(null != obj)
                prop_dev_name = (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop_dev_name;
    }

    public static String getBootSerialNo() {
        String prop_dev_name = null;
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClass.getMethod("get", String.class);
            Object object = new Object();
            Object obj = getMethod.invoke(object, "ro.boot.serialno");
            if(null != obj)
                prop_dev_name = (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop_dev_name;
    }

    public static String getPropMode() {
        String prop_dev_name = "";
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClass.getMethod("get", String.class);
            Object object = new Object();
            Object obj = getMethod.invoke(object, "ro.secure");
            if(null != obj)
                prop_dev_name = prop_dev_name + "ro.secure:" + (String)obj;
            obj = getMethod.invoke(object, "ro.adb.secure");
            if(null != obj)
                prop_dev_name = prop_dev_name + " ro.adb.secure:" + (String)obj;
            obj = getMethod.invoke(object, "ro.debuggable");
            if(null != obj)
                prop_dev_name = prop_dev_name + " ro.debuggable:" + (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop_dev_name;
    }

    public static String getPropBackup() {
        String prop_dev_name = "";
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClass.getMethod("get", String.class);
            Object object = new Object();
            Object obj = getMethod.invoke(object, "tel.backup");
            if(null != obj)
                prop_dev_name = prop_dev_name + "tel.backup:" + (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop_dev_name;
    }
}
