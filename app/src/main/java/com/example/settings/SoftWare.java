package com.example.settings;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SoftWare {
    @RequiresApi(api = Build.VERSION_CODES.P)
    private static String getAPPInfo(ApplicationInfo pkgInfo) {
        String pkgInfoStr = "";
        pkgInfoStr += ("name:" + pkgInfo.name + ",");
        pkgInfoStr += ("packageName:" + pkgInfo.packageName + ",");
        pkgInfoStr += ("className:" + pkgInfo.className + ",");
        pkgInfoStr += ("appComponentFactory:" + pkgInfo.appComponentFactory + ",");
        pkgInfoStr += ("backupAgentName:" + pkgInfo.backupAgentName + ",");
        pkgInfoStr += ("dataDir:" + pkgInfo.dataDir + ",");
        pkgInfoStr += ("deviceProtectedDataDir:" + pkgInfo.deviceProtectedDataDir + ",");
        pkgInfoStr += ("manageSpaceActivityName:" + pkgInfo.manageSpaceActivityName + ",");
        pkgInfoStr += ("nativeLibraryDir:" + pkgInfo.nativeLibraryDir + ",");
        pkgInfoStr += ("permission:" + pkgInfo.permission + ",");
        pkgInfoStr += ("processName:" + pkgInfo.processName + ",");
        pkgInfoStr += ("publicSourceDir:" + pkgInfo.publicSourceDir + ",");
        pkgInfoStr += ("sourceDir:" + pkgInfo.sourceDir + ",");
        pkgInfoStr += ("taskAffinity:" + pkgInfo.taskAffinity + ",");
        pkgInfoStr += ("banner:" + pkgInfo.banner + ",");
        pkgInfoStr += ("category:" + pkgInfo.category + ",");
        pkgInfoStr += ("compatibleWidthLimitDp:" + pkgInfo.compatibleWidthLimitDp + ",");
        pkgInfoStr += ("descriptionRes:" + pkgInfo.descriptionRes + ",");
        pkgInfoStr += ("enabled:" + pkgInfo.enabled + ",");
        pkgInfoStr += ("flags:" + pkgInfo.flags + ",");
        pkgInfoStr += ("icon:" + pkgInfo.icon + ",");
        pkgInfoStr += ("labelRes:" + pkgInfo.labelRes + ",");
        pkgInfoStr += ("largestWidthLimitDp:" + pkgInfo.largestWidthLimitDp + ",");
        pkgInfoStr += ("logo:" + pkgInfo.logo + ",");
        pkgInfoStr += ("metaData:" + pkgInfo.metaData + ",");
        pkgInfoStr += ("minSdkVersion:" + pkgInfo.minSdkVersion + ",");
        pkgInfoStr += ("nonLocalizedLabel:" + pkgInfo.nonLocalizedLabel + ",");
        pkgInfoStr += ("requiresSmallestWidthDp:" + pkgInfo.requiresSmallestWidthDp + ",");
        pkgInfoStr += ("sharedLibraryFiles:" + pkgInfo.sharedLibraryFiles + ",");
        pkgInfoStr += ("splitNames:" + pkgInfo.splitNames + ",");
        pkgInfoStr += ("splitPublicSourceDirs:" + pkgInfo.splitPublicSourceDirs + ",");
        pkgInfoStr += ("splitSourceDirs:" + pkgInfo.splitSourceDirs + ",");
        pkgInfoStr += ("storageUuid:" + pkgInfo.storageUuid + ",");
        pkgInfoStr += ("targetSdkVersion:" + pkgInfo.targetSdkVersion + ",");
        pkgInfoStr += ("theme:" + pkgInfo.theme + ",");
        pkgInfoStr += ("uid:" + pkgInfo.uid + ",");
        pkgInfoStr += ("uiOptions:" + pkgInfo.uiOptions + ",");


        return pkgInfoStr;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private static String getPkgInfo(PackageInfo pkgInfo) {
        String pkgInfoStr = "";
        pkgInfoStr += ("permissions:" + pkgInfo.permissions + ",");
        pkgInfoStr += ("packageName:" + pkgInfo.packageName + ",");
        pkgInfoStr += ("sharedUserId:" + pkgInfo.sharedUserId + ",");
        pkgInfoStr += ("versionName:" + pkgInfo.versionName + ",");
        pkgInfoStr += ("activities:" + pkgInfo.activities + ",");
        pkgInfoStr += ("applicationInfo:" + pkgInfo.applicationInfo + ",");
        pkgInfoStr += ("baseRevisionCode:" + pkgInfo.baseRevisionCode + ",");
        pkgInfoStr += ("configPreferences:" + pkgInfo.configPreferences + ",");
        pkgInfoStr += ("featureGroups:" + pkgInfo.featureGroups + ",");
        pkgInfoStr += ("firstInstallTime:" + pkgInfo.firstInstallTime + ",");
        pkgInfoStr += ("gids:" + pkgInfo.gids + ",");
        pkgInfoStr += ("installLocation:" + pkgInfo.installLocation + ",");
        pkgInfoStr += ("instrumentation:" + pkgInfo.instrumentation + ",");
        pkgInfoStr += ("lastUpdateTime:" + pkgInfo.lastUpdateTime + ",");
        pkgInfoStr += ("providers:" + pkgInfo.providers + ",");
        pkgInfoStr += ("receivers:" + pkgInfo.receivers + ",");
        pkgInfoStr += ("reqFeatures:" + pkgInfo.reqFeatures + ",");
        pkgInfoStr += ("requestedPermissions:" + pkgInfo.requestedPermissions + ",");
        pkgInfoStr += ("requestedPermissionsFlags:" + pkgInfo.requestedPermissionsFlags + ",");
        pkgInfoStr += ("services:" + pkgInfo.services + ",");
        pkgInfoStr += ("sharedUserLabel:" + pkgInfo.sharedUserLabel + ",");
        pkgInfoStr += ("splitNames:" + pkgInfo.splitNames + ",");
        pkgInfoStr += ("splitRevisionCodes:" + pkgInfo.splitRevisionCodes + ",");
        pkgInfoStr += ("signatures:" + pkgInfo.signatures + ",");
        pkgInfoStr += ("versionCode:" + pkgInfo.versionCode + ",");


        return pkgInfoStr;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String getAppInfo(Context context, String phoneName) {
        List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(0);
        try {
            for(ApplicationInfo ai : apps) {
                System.out.println(getAPPInfo(ai));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String getPkgInfo(Context context, String phoneName) {
        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(0);
        try {
            for(PackageInfo pi : apps) {
                System.out.println(getPkgInfo(pi));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String appWriteToFile(Context context, String phoneName) {
        List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(0);
        JSONObject obj=new JSONObject();
        try {
            for(ApplicationInfo ai : apps) {
                byte[] bytes = marshall(ai);
                String pkgStr = Base64.encodeToString(bytes, 0);
                JSONObject subObj=new JSONObject();
                subObj.put("pkgName", ai.packageName);
                subObj.put("pkgMarshall", pkgStr);
                obj.accumulate("pkgInfo", subObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //byte[] newbytes = Base64.decode(pkgStr, 0);
        //PackageInfo newPkgInfo = PackageInfo.CREATOR.createFromParcel(unmarshall(newbytes));
        //System.out.println("name:" + newPkgInfo.packageName + "; uid:" + newPkgInfo.applicationInfo.uid);
        try {
            File file = new File("/storage/emulated/0/app.xml");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("" + obj.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String appFileToRead(Context context, String phoneName) {
        File file = new File("/storage/emulated/0/app.xml");
        if (!file.exists()) { // if permission xml file is not exist, we should not config permissions
            return null;
        }

        String jsonStr = "";
        String pLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((pLine = br.readLine()) != null) {
                jsonStr += pLine;
            }
            br.close();
        } catch (Exception e) {
            return null;
        }

        try{
            System.out.println("----------------------getInstalledApplications");
            JSONObject jsonObjectOrig = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObjectOrig.getJSONArray("pkgInfo");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String pkgName = jsonObject.getString("pkgName");
                String pkgMarshall = jsonObject.getString("pkgMarshall");
                byte[] newbytes = Base64.decode(pkgMarshall, 0);
                ApplicationInfo appInfo = ApplicationInfo.CREATOR.createFromParcel(unmarshall(newbytes));
                System.out.println(getAPPInfo(appInfo));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static byte[] marshall(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parcel.setDataPosition(0);
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();

        //Log.d("ParcelableTest", "bytes = " + String.valueOf(bytes) + "parcel" + parcel.toString());
        parcel.recycle();
        return bytes;
    }

    private static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    public static String pkgFileToRead(Context context, String phoneName) {
        File file = new File("/storage/emulated/0/pkg.xml");
        if (!file.exists()) { // if permission xml file is not exist, we should not config permissions
            return null;
        }

        String jsonStr = "";
        String pLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((pLine = br.readLine()) != null) {
                jsonStr += pLine;
            }
            br.close();
        } catch (Exception e) {
            return null;
        }

        try{
            JSONObject jsonObjectOrig = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObjectOrig.getJSONArray("pkgInfo");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String pkgName = jsonObject.getString("pkgName");
                String pkgMarshall = jsonObject.getString("pkgMarshall");
                byte[] newbytes = Base64.decode(pkgMarshall, 0);
                PackageInfo newPkgInfo = PackageInfo.CREATOR.createFromParcel(unmarshall(newbytes));
//                if(newPkgInfo.packageName.contains("calendar"))
//                    System.out.println("name:" + newPkgInfo.packageName + "; uid:" + newPkgInfo.applicationInfo.uid);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String pkgWriteToFile(Context context, String phoneName) {
        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(0);
        JSONObject obj=new JSONObject();
        try {
            System.out.println("----------------------getInstalledPackages");
            for(PackageInfo pi : apps) {
                byte[] bytes = marshall(pi);
                String pkgStr = Base64.encodeToString(bytes, 0);
                JSONObject subObj=new JSONObject();
                subObj.put("pkgName", pi.packageName);
                subObj.put("pkgMarshall", pkgStr);
                obj.accumulate("pkgInfo", subObj);
                System.out.println(getPkgInfo(pi));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //byte[] newbytes = Base64.decode(pkgStr, 0);
        //PackageInfo newPkgInfo = PackageInfo.CREATOR.createFromParcel(unmarshall(newbytes));
        //System.out.println("name:" + newPkgInfo.packageName + "; uid:" + newPkgInfo.applicationInfo.uid);
        try {
            File file = new File("/storage/emulated/0/pkg.xml");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("" + obj.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
