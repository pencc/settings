package com.example.settings;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class Data {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void RequestPhoneStatePermission(Context context) {
        int permissionCheck = context.getApplicationContext().checkSelfPermission(Manifest.permission.READ_CONTACTS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void getAllContactInfo(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                RequestPhoneStatePermission(context);
            }
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            Uri uri = intent.getData();
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);
            //移动到游标到联系人表第一行
            if (cursor != null && cursor.moveToFirst()) {
                //联系人姓名
                String nameStr = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //读取通讯录的号码
                String phoneStr = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                cursor.close();
                System.out.println("name:" + nameStr + "; number:" + phoneStr);
            } else {
                System.out.println("db cursor not exist.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
