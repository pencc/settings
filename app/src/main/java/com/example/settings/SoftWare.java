package com.example.settings;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoftWare {
    @RequiresApi(api = Build.VERSION_CODES.P)
    private String getAPPInfo(ApplicationInfo pkgInfo) {
        String pkgInfoStr = "";
        pkgInfoStr += ("uid:" + pkgInfo.uid + ",");
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
        pkgInfoStr += ("flags:" + pkgInfo.flags + " -----allowBackup:" + (pkgInfo.flags & ApplicationInfo.FLAG_ALLOW_BACKUP) + ",");
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
        pkgInfoStr += ("uiOptions:" + pkgInfo.uiOptions + ",");


        return pkgInfoStr;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private String getPkgInfo(PackageInfo pkgInfo) {
        String pkgInfoStr = "appInfo.uid:";
        if(null != pkgInfo.applicationInfo) {
            pkgInfoStr += pkgInfo.applicationInfo.uid;
        } else {
            pkgInfoStr += "null";
        }
        pkgInfoStr += (",permissions:" + pkgInfo.permissions + ",");
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
    public String getAppInfo(Context context, String phoneName) {
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
    public void getOnePkgInfo(Context context, String pkgName) {
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
            System.out.println("getOnePkgInfo:" + getPkgInfo(pkgInfo));
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String getPkgInfo(Context context, String phoneName) {
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

    class PkgInfo implements Parcelable {
        public String packageName;
        public PermissionInfo[] permissions;
        public String sharedUserId;
        public String versionName;
        public ActivityInfo[] activities;
        public int baseRevisionCode;
        public ConfigurationInfo[] configPreferences;
        public FeatureGroupInfo[] featureGroups;
        public long firstInstallTime;
        public int[] gids;
        public int installLocation;
        public InstrumentationInfo[] instrumentation;
        public long lastUpdateTime;
        public ProviderInfo[] providers;
        public ActivityInfo[] receivers;
        public FeatureInfo[] reqFeatures;
        public String[] requestedPermissions;
        public int[] requestedPermissionsFlags;
        public ServiceInfo[] services;
        public int sharedUserLabel;
        public String[] splitNames;
        public int[] splitRevisionCodes;
        public Signature[] signatures;
        public SigningInfo signingInfo;
        public long longVersionCode;

        public final Creator<PkgInfo> CREATOR = new Creator<PkgInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public PkgInfo createFromParcel(Parcel in) {
                return new PkgInfo(in);
            }

            @Override
            public PkgInfo[] newArray(int size) {
                return new PkgInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int parcelableFlags) {
            dest.writeString(packageName);
            dest.writeStringArray(splitNames);
            dest.writeLong(longVersionCode);
            dest.writeString(versionName);
            dest.writeInt(baseRevisionCode);
            dest.writeIntArray(splitRevisionCodes);
            dest.writeString(sharedUserId);
            dest.writeInt(sharedUserLabel);
            dest.writeLong(firstInstallTime);
            dest.writeLong(lastUpdateTime);
            dest.writeIntArray(gids);
            dest.writeTypedArray(activities, parcelableFlags | 0x0002);
            dest.writeTypedArray(receivers, parcelableFlags | 0x0002);
            dest.writeTypedArray(services, parcelableFlags | 0x0002);
            dest.writeTypedArray(providers, parcelableFlags | 0x0002);
            dest.writeTypedArray(instrumentation, parcelableFlags);
            dest.writeTypedArray(permissions, parcelableFlags);
            dest.writeStringArray(requestedPermissions);
            dest.writeIntArray(requestedPermissionsFlags);
            dest.writeTypedArray(signatures, parcelableFlags);
            dest.writeTypedArray(configPreferences, parcelableFlags);
            dest.writeTypedArray(reqFeatures, parcelableFlags);
            dest.writeTypedArray(featureGroups, parcelableFlags);
            dest.writeInt(installLocation);
            if (signingInfo != null) {
                dest.writeInt(1);
                signingInfo.writeToParcel(dest, parcelableFlags);
            } else {
                dest.writeInt(0);
            }

        }

        public PkgInfo() {

        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        private PkgInfo(Parcel source) {
            packageName = source.readString();
            splitNames = source.createStringArray();
            longVersionCode = source.readLong();
            versionName = source.readString();
            baseRevisionCode = source.readInt();
            splitRevisionCodes = source.createIntArray();
            sharedUserId = source.readString();
            sharedUserLabel = source.readInt();
            firstInstallTime = source.readLong();
            lastUpdateTime = source.readLong();
            gids = source.createIntArray();
            activities = source.createTypedArray(ActivityInfo.CREATOR);
            receivers = source.createTypedArray(ActivityInfo.CREATOR);
            services = source.createTypedArray(ServiceInfo.CREATOR);
            providers = source.createTypedArray(ProviderInfo.CREATOR);
            instrumentation = source.createTypedArray(InstrumentationInfo.CREATOR);
            permissions = source.createTypedArray(PermissionInfo.CREATOR);
            requestedPermissions = source.createStringArray();
            requestedPermissionsFlags = source.createIntArray();
            signatures = source.createTypedArray(Signature.CREATOR);
            configPreferences = source.createTypedArray(ConfigurationInfo.CREATOR);
            reqFeatures = source.createTypedArray(FeatureInfo.CREATOR);
            featureGroups = source.createTypedArray(FeatureGroupInfo.CREATOR);
            installLocation = source.readInt();
            int hasSigningInfo = source.readInt();
            if (hasSigningInfo != 0) {
                signingInfo = SigningInfo.CREATOR.createFromParcel(source);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String pkgWriteToFile2(Context context, String phoneName) {
        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES | PackageManager.GET_PERMISSIONS
                | PackageManager.GET_SHARED_LIBRARY_FILES | PackageManager.GET_RECEIVERS | PackageManager.GET_GIDS
                | PackageManager.GET_INSTRUMENTATION | PackageManager.GET_META_DATA);
        JSONObject obj=new JSONObject();
        try {
            for(PackageInfo ai : apps) {
                PkgInfo pkgInfo = new PkgInfo();
                pkgInfo.packageName = ai.packageName;
                pkgInfo.permissions = ai.permissions;
                pkgInfo.sharedUserId = ai.sharedUserId;
                pkgInfo.versionName = ai.versionName;
                pkgInfo.activities = ai.activities;
                pkgInfo.baseRevisionCode = ai.baseRevisionCode;
                pkgInfo.configPreferences = ai.configPreferences;
                pkgInfo.featureGroups = ai.featureGroups;
                pkgInfo.firstInstallTime = ai.firstInstallTime;
                pkgInfo.gids = ai.gids;
                pkgInfo.installLocation = ai.installLocation;
                pkgInfo.instrumentation = ai.instrumentation;
                pkgInfo.lastUpdateTime = ai.lastUpdateTime;
                pkgInfo.providers = ai.providers;
                pkgInfo.receivers = ai.receivers;
                pkgInfo.reqFeatures = ai.reqFeatures;
                pkgInfo.requestedPermissions = ai.requestedPermissions;
                pkgInfo.requestedPermissionsFlags = ai.requestedPermissionsFlags;
                pkgInfo.services = ai.services;
                pkgInfo.sharedUserLabel = ai.sharedUserLabel;
                pkgInfo.splitNames = ai.splitNames;
                pkgInfo.splitRevisionCodes = ai.splitRevisionCodes;
                pkgInfo.signatures = ai.signatures;
                pkgInfo.signingInfo = ai.signingInfo;
                pkgInfo.longVersionCode = ai.getLongVersionCode();

                byte[] bytes = marshall(pkgInfo);
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
            File file = new File("/storage/emulated/0/pkg-one.xml");

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
    public String pkgReadFromFile2(Context context, String phoneName) {
        File file = new File("/storage/emulated/0/pkg-one.xml");
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
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String pkgNameStr = jsonObject.getString("pkgName");
                    String pkgMarshallStr = jsonObject.getString("pkgMarshall");
                    byte[] newbytes = Base64.decode(pkgMarshallStr, 0);
                    PkgInfo pkgInfo = new PkgInfo().CREATOR.createFromParcel(unmarshall(newbytes));
                    System.out.println("" + pkgInfo.packageName + "," + pkgInfo.sharedUserId + "," + pkgInfo.lastUpdateTime);
                } catch (Exception e) {
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

//    @RequiresApi(api = Build.VERSION_CODES.P)
//    public String pkgWriteToFile(Context context, String phoneName) {
//        //MATCH_SYSTEM_ONLY
//        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(
//                PackageManager.GET_ACTIVITIES | PackageManager.GET_RECEIVERS | PackageManager.GET_SERVICES);
//        JSONObject obj=new JSONObject();
//        try {
//                byte[] bytes = marshall(pi);
//                JSONObject subObj=new JSONObject();
//                subObj.put("packageName", pi.packageName);
//                subObj.put("permissions", pi.permissions);  // PermissionInfo[]
//                subObj.put("sharedUserId", pi.sharedUserId);
//                subObj.put("versionName", pi.versionName);
//                subObj.put("activities", pi.activities);  // ActivityInfo[]
//                subObj.put("applicationInfo", pi.applicationInfo);  // ApplicationInfo
//                subObj.put("baseRevisionCode", pi.baseRevisionCode);  // int
//                subObj.put("configPreferences", pi.configPreferences);  // ConfigurationInfo[]
//                subObj.put("featureGroups", pi.featureGroups);  // FeatureGroupInfo[]
//                subObj.put("firstInstallTime", pi.firstInstallTime);  // long
//                subObj.put("gids", pi.gids);  // int[]
//                subObj.put("installLocation", pi.installLocation);  // int
//                subObj.put("instrumentation", pi.instrumentation);  // InstrumentationInfo[]
//                subObj.put("lastUpdateTime", pi.lastUpdateTime);  // long
//                subObj.put("providers", pi.providers);  // ProviderInfo[]
//                subObj.put("receivers", pi.receivers);  // ActivityInfo[]
//                subObj.put("reqFeatures", pi.reqFeatures);  // FeatureInfo[]
//                subObj.put("requestedPermissions", pi.requestedPermissions);  // String[]
//                subObj.put("requestedPermissionsFlags", pi.requestedPermissionsFlags);  // int[]
//                subObj.put("services", pi.services);  // ServiceInfo[]
//                subObj.put("sharedUserLabel", pi.sharedUserLabel);  // int
//                subObj.put("splitNames", pi.splitNames);  // String[]
//                subObj.put("splitRevisionCodes", pi.splitRevisionCodes);  // int[]
//                subObj.put("signatures", pi.signatures);  // Signature[]
//                subObj.put("getLongVersionCode", pi.getLongVersionCode());  // long
//                obj.accumulate("pkgInfo", subObj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //byte[] newbytes = Base64.decode(pkgStr, 0);
//        //PackageInfo newPkgInfo = PackageInfo.CREATOR.createFromParcel(unmarshall(newbytes));
//        //System.out.println("name:" + newPkgInfo.packageName + "; uid:" + newPkgInfo.applicationInfo.uid);
//        try {
//            File file = new File("/storage/emulated/0/app-one.xml");
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            FileWriter fw = new FileWriter(file.getAbsoluteFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write("" + obj.toString());
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return null;
//    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String pkgReadFromFile(Context context, String phoneName) {
        File file = new File("/storage/emulated/0/app-one.xml");
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
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    AppInfo appInfo = new AppInfo(jsonObject);
                    appInfo.getJsonResult("packageName");
                    String packageName = (String) appInfo.getJsonResult("packageName");
                    appInfo.getJsonResult("name");
                    appInfo.getJsonResult("className");
                    appInfo.getJsonResult("appComponentFactory");
                    appInfo.getJsonResult("dataDir");
                    appInfo.getJsonResult("deviceProtectedDataDir");
                    appInfo.getJsonResult("manageSpaceActivityName");
                    appInfo.getJsonResult("nativeLibraryDir");
                    String permission = (String) appInfo.getJsonResult("permission");
                    String processName = (String) appInfo.getJsonResult("processName");
                    appInfo.getJsonResult("publicSourceDir");
                    appInfo.getJsonResult("sourceDir");
                    appInfo.getJsonResult("taskAffinity");
                    boolean enabled = (boolean)appInfo.getJsonResult("enabled");
                    int flags = (int)appInfo.getJsonResult("flags");
                    int icon = (int)appInfo.getJsonResult("icon");
                    int labelRes = (int)appInfo.getJsonResult("labelRes");
                    Bundle metaData = appInfo.getJsonResult("metaData") == null ? null : new Bundle();
                    int minSdkVersion = (int)appInfo.getJsonResult("minSdkVersion");
                    CharSequence nonLocalizedLabel = (String)appInfo.getJsonResult("nonLocalizedLabel");
                    String[] sharedLibraryFiles = null;
                    JSONArray slFilesJsonStr = (JSONArray)appInfo.getJsonResult("sharedLibraryFiles");
                    if(null != slFilesJsonStr && slFilesJsonStr.length() > 0) {
                        ArrayList al = new ArrayList<String>(slFilesJsonStr.length());
                        for (int j=0; j < slFilesJsonStr.length(); j++) {
                            al.add(slFilesJsonStr.getString(j));
                        }
                        sharedLibraryFiles = (String[]) al.toArray(new String[]{});
                    }
                    UUID storageUuid = UUID.fromString((String)appInfo.getJsonResult("storageUuid"));
                    int targetSdkVersion = (int)appInfo.getJsonResult("targetSdkVersion");
                    int uid = (int)appInfo.getJsonResult("uid");
                    System.out.println(packageName + "," + permission + "," + processName + "," + enabled + "," + flags
                            + "," + icon + "," + labelRes + "," + metaData + ","
                            + (sharedLibraryFiles == null ? "null" : sharedLibraryFiles[0])
                            + "," + storageUuid + "," + uid + "," + nonLocalizedLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String appWriteToFile(Context context, String phoneName) {
        //MATCH_SYSTEM_ONLY
        List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(
                PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);
        JSONObject obj=new JSONObject();
        try {
            for(ApplicationInfo ai : apps) {
                byte[] bytes = marshall(ai);
                JSONObject subObj=new JSONObject();
                subObj.put("packageName", ai.packageName);
                subObj.put("name", ai.name);
                subObj.put("className", ai.className);
                subObj.put("appComponentFactory", ai.appComponentFactory);
                subObj.put("dataDir", ai.dataDir);
                subObj.put("deviceProtectedDataDir", ai.deviceProtectedDataDir);
                subObj.put("manageSpaceActivityName", ai.manageSpaceActivityName);
                subObj.put("nativeLibraryDir", ai.nativeLibraryDir);
                subObj.put("permission", ai.permission);
                subObj.put("processName", ai.processName);
                subObj.put("publicSourceDir", ai.publicSourceDir);
                subObj.put("sourceDir", ai.sourceDir);
                subObj.put("taskAffinity", ai.taskAffinity);
                subObj.put("enabled", ai.enabled); // boolean
                subObj.put("flags", ai.flags);  // int
                subObj.put("icon", ai.icon);  // int
                subObj.put("labelRes", ai.labelRes);  // int
                subObj.put("metaData", ai.metaData);  // Bundle
                subObj.put("minSdkVersion", ai.minSdkVersion);  // int
                subObj.put("nonLocalizedLabel", ai.nonLocalizedLabel);  // CharSequence
                JSONArray sharedLibraryFilesArray = null;
                if(ai.sharedLibraryFiles != null) {
                    sharedLibraryFilesArray = new JSONArray(ai.sharedLibraryFiles);
                    subObj.put("sharedLibraryFiles", sharedLibraryFilesArray);  // String[]
                }
                subObj.put("storageUuid", ai.storageUuid);  // UUID
                subObj.put("targetSdkVersion", ai.targetSdkVersion);  // int
                subObj.put("uid", ai.uid);  // int
                obj.accumulate("pkgInfo", subObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //byte[] newbytes = Base64.decode(pkgStr, 0);
        //PackageInfo newPkgInfo = PackageInfo.CREATOR.createFromParcel(unmarshall(newbytes));
        //System.out.println("name:" + newPkgInfo.packageName + "; uid:" + newPkgInfo.applicationInfo.uid);
        try {
            File file = new File("/storage/emulated/0/app-one.xml");

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

    static class AppInfo {
        private JSONObject jsobObj;
        AppInfo(JSONObject jsobObj) {
            this.jsobObj = jsobObj;
        }

        private Object getJsonResult(String key){
            try {
                return jsobObj.get(key);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String appReadFromFile(Context context, String phoneName) {
        File file = new File("/storage/emulated/0/app-one.xml");
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
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    AppInfo appInfo = new AppInfo(jsonObject);
                    appInfo.getJsonResult("packageName");
                    String packageName = (String) appInfo.getJsonResult("packageName");
                    appInfo.getJsonResult("name");
                    appInfo.getJsonResult("className");
                    appInfo.getJsonResult("appComponentFactory");
                    appInfo.getJsonResult("dataDir");
                    appInfo.getJsonResult("deviceProtectedDataDir");
                    appInfo.getJsonResult("manageSpaceActivityName");
                    appInfo.getJsonResult("nativeLibraryDir");
                    String permission = (String) appInfo.getJsonResult("permission");
                    String processName = (String) appInfo.getJsonResult("processName");
                    appInfo.getJsonResult("publicSourceDir");
                    appInfo.getJsonResult("sourceDir");
                    appInfo.getJsonResult("taskAffinity");
                    boolean enabled = (boolean)appInfo.getJsonResult("enabled");
                    int flags = (int)appInfo.getJsonResult("flags");
                    int icon = (int)appInfo.getJsonResult("icon");
                    int labelRes = (int)appInfo.getJsonResult("labelRes");
                    Bundle metaData = appInfo.getJsonResult("metaData") == null ? null : new Bundle();
                    int minSdkVersion = (int)appInfo.getJsonResult("minSdkVersion");
                    CharSequence nonLocalizedLabel = (String)appInfo.getJsonResult("nonLocalizedLabel");
                    String[] sharedLibraryFiles = null;
                    JSONArray slFilesJsonStr = (JSONArray)appInfo.getJsonResult("sharedLibraryFiles");
                    if(null != slFilesJsonStr && slFilesJsonStr.length() > 0) {
                        ArrayList al = new ArrayList<String>(slFilesJsonStr.length());
                        for (int j=0; j < slFilesJsonStr.length(); j++) {
                            al.add(slFilesJsonStr.getString(j));
                        }
                        sharedLibraryFiles = (String[]) al.toArray(new String[]{});
                    }
                    UUID storageUuid = UUID.fromString((String)appInfo.getJsonResult("storageUuid"));
                    int targetSdkVersion = (int)appInfo.getJsonResult("targetSdkVersion");
                    int uid = (int)appInfo.getJsonResult("uid");
                    System.out.println(packageName + "," + permission + "," + processName + "," + enabled + "," + flags
                        + "," + icon + "," + labelRes + "," + metaData + ","
                            + (sharedLibraryFiles == null ? "null" : sharedLibraryFiles[0])
                             + "," + storageUuid + "," + uid + "," + nonLocalizedLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String appWriteToFileParcel(Context context, String phoneName) {
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
            File file = new File("/storage/emulated/0/app-test.xml");

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
    public String appFileToReadParcel(Context context, String phoneName) {
        File file = new File("/storage/emulated/0/app-test.xml");
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

    private byte[] marshall(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parcel.setDataPosition(0);
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();

        //Log.d("ParcelableTest", "bytes = " + String.valueOf(bytes) + "parcel" + parcel.toString());
        parcel.recycle();
        return bytes;
    }

    private Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    public String pkgFileToReadParcel(Context context, String phoneName) {
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
    public String pkgWriteToFileParcel(Context context, String phoneName) {
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
