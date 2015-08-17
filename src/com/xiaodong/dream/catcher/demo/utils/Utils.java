/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/12.
 */
package com.xiaodong.dream.catcher.demo.utils;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.sdk.model.LogisticsItem;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class Utils {

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static String getAndroidVersionName(int version) {
        switch (version) {
            case Build.VERSION_CODES.CUPCAKE:
                return "Android 1.3";
            case Build.VERSION_CODES.DONUT:
                return "Android 1.6";
            case Build.VERSION_CODES.ECLAIR:
                return "Android 2.0";
            case Build.VERSION_CODES.ECLAIR_0_1:
                return "Android 2.0.1";
            case Build.VERSION_CODES.ECLAIR_MR1:
                return "Android 2.1";
            case Build.VERSION_CODES.FROYO:
                return "Android 2.2";
            case Build.VERSION_CODES.GINGERBREAD:
                return "Android 2.3";
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                return "Android 2.3.3";
            case Build.VERSION_CODES.HONEYCOMB:
                return "Android 3.0";
            case Build.VERSION_CODES.HONEYCOMB_MR1:
                return "Android 3.1";
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                return "Android 3.2";
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                return "Android 4.0";
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                return "Android 4.0.3";
            case Build.VERSION_CODES.JELLY_BEAN:
                return "Android 4.1";
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                return "Android 4.2";
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                return "Android 4.3";
            case Build.VERSION_CODES.KITKAT:
                return "Android 4.4";
            case Build.VERSION_CODES.KITKAT_WATCH:
                return "Android 4.4W";
            case Build.VERSION_CODES.LOLLIPOP:
                return "Android 5.0";
            default:
                return "Android###";
        }
    }

    public static String getUserAgent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MODEL).append(File.separator)
                .append(getAndroidVersionName(Build.VERSION.SDK_INT));

        return stringBuilder.toString();
    }

    public static void toastErrorMessage(final Activity activity,
                                         final Exception e) {
        if (activity == null || e == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(e.getMessage());

                    String message = jsonObject.getString("message");
                    if (!TextUtils.isEmpty(message)) {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public static List<LogisticsItem> parseLogisticsToList(LogisticsItem[] items){
        if(items == null){
            return null;
        }
        List<LogisticsItem> logisticsItemList = new ArrayList<LogisticsItem>();

        for (LogisticsItem item : items){
            logisticsItemList.add(item);
        }

        return logisticsItemList;
    }

    public static void setExpressHeaderInSearchResult(Activity context, String type,
                                                      String cd, ImageView thumbnail, TextView name, TextView code){
        if(thumbnail == null || name == null || code == null)
            return;
        if (type.equals("shunfeng")){
            thumbnail.setImageResource(R.drawable.sfsy);
            name.setText("顺丰速运");
            code.setText(context.getResources().getString(R.string.express_code_info, cd));
        }
    }
}
