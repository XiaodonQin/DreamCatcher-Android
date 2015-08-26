package com.xiaodong.dream.catcher.demo.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.xiaodong.dream.catcher.demo.Constants;
import com.xiaodong.dream.catcher.demo.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class ImageUtils {
    private static String TAG = "ImageUtils";

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasIcecreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    // get file or dir size
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static Bitmap getResizedBmp(FileDescriptor fileDescriptor,
                                       int targetWidth, int targetHeight, boolean scaled) {
        Bitmap outBmp = null;
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, outOptions);
        int iBmpWidth = outOptions.outWidth;
        int iBmpHeight = outOptions.outHeight;
        BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
        Bitmap originBmp = null;
        try {
            if (scaled) {
                float f7 = Math.min(1.0F, (1.0F * targetWidth) / iBmpWidth);
                float f14 = Math.min(f7, 1.0F * targetHeight / iBmpHeight);
                int idstWidth = (int) (iBmpWidth * f14);
                int idstHeight = (int) (iBmpHeight * f14);
                double d3 = (int) (Math.log(1.0F / f14) / Math.log(2.0D));
                // Log.v(TAG,
                // "doJob d3:"+d3+" targetW:"+mTargetWidth+" targetH:"+mTargetHeight+" bmpW:"+iBmpWidth+" bmpH:"+iBmpHeight);
                localOptions2.inSampleSize = (int) Math.pow(2.0D, d3);
                originBmp = BitmapFactory.decodeFileDescriptor(fileDescriptor,
                        null, localOptions2);
                if (originBmp == null) {
                    // Log.v(TAG, "doJob null:"+paramObject);
                    return null;
                }
                outBmp = Bitmap.createScaledBitmap(originBmp, idstWidth,
                        idstHeight, false);
            } else {
                float widthOriginRate = (1.0F * targetWidth) / iBmpWidth;
                float heightOriginRate = (1.0F * targetHeight) / iBmpHeight;
                float bmpRate = Math.max(widthOriginRate, heightOriginRate);

                int iScaledWidth = Math.round(iBmpWidth * bmpRate);
                int iScaledHeight = Math.round(iBmpHeight * bmpRate);
                double exponent = (int) (Math.log(1.0F / bmpRate) / Math
                        .log(2.0D));
                // Log.v(TAG,
                // "doJob d3:"+d3+" targetW:"+mTargetWidth+" targetH:"+mTargetHeight+" bmpW:"+iBmpWidth+" bmpH:"+iBmpHeight);
                // localOptions2.inSampleSize = (int) Math.pow(2.0D, exponent);
                originBmp = BitmapFactory.decodeFileDescriptor(fileDescriptor,
                        null, localOptions2);
                if (originBmp != null) {
                    outBmp = Bitmap.createScaledBitmap(originBmp, iScaledWidth,
                            iScaledHeight, false);
                }
                if ((outBmp != originBmp) && (originBmp != null)) {
                    originBmp.recycle();
                    originBmp = outBmp;
                }

                int iOutWidth = Math.min(iScaledWidth, targetWidth);
                int iOutHeight = Math.min(iScaledHeight, targetHeight);
                int ixFirstPixel = (iScaledWidth - iOutWidth) / 2;
                int iyFirstPixel = (iScaledHeight - iOutHeight) / 2;
                if (originBmp != null) {
                    outBmp = Bitmap.createBitmap(originBmp, ixFirstPixel,
                            iyFirstPixel, iOutWidth, iOutHeight);
                }
            }
            if ((outBmp != originBmp) && (originBmp != null)) {
                originBmp.recycle();
                originBmp = null;
            }
            return outBmp;
        } catch (Exception localException) {
            localException.printStackTrace();
        } catch (OutOfMemoryError localOutOfMemoryError) {
            localOutOfMemoryError.printStackTrace();
        }
        return null;
    }

    public static Bitmap getResizedBmp(InputStream is, int targetWidth,
                                       int targetHeight, boolean scaled) {
        Bitmap outBmp = null;
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, outOptions);
        int iBmpWidth = outOptions.outWidth;
        int iBmpHeight = outOptions.outHeight;
        BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
        Bitmap originBmp = null;
        try {
            if (!scaled) {
                int iOutWidth = Math.min(iBmpWidth, targetWidth);
                int iOutHeight = Math.min(iBmpHeight, targetHeight);
                int ixFirstPixel = (iBmpWidth - iOutWidth) / 2;
                int iyFirstPixel = (iBmpHeight - iOutHeight) / 2;
                originBmp = BitmapFactory.decodeStream(is, null, localOptions2);
                if (originBmp != null) {
                    outBmp = Bitmap.createBitmap(originBmp, ixFirstPixel,
                            iyFirstPixel, iOutWidth, iOutHeight);
                }

            } else {
                float f7 = Math.min(1.0F, (1.0F * targetWidth) / iBmpWidth);
                float f14 = Math.min(f7, 1.0F * targetHeight / iBmpHeight);
                int idstWidth = (int) (iBmpWidth * f14);
                int idstHeight = (int) (iBmpHeight * f14);
                double d3 = (int) (Math.log(1.0F / f14) / Math.log(2.0D));
                // Log.v(TAG,
                // "doJob d3:"+d3+" targetW:"+mTargetWidth+" targetH:"+mTargetHeight+" bmpW:"+iBmpWidth+" bmpH:"+iBmpHeight);
                localOptions2.inSampleSize = (int) Math.pow(2.0D, d3);
                originBmp = BitmapFactory.decodeStream(is, null, localOptions2);
                if (originBmp == null) {
                    // Log.v(TAG, "doJob null:"+paramObject);
                    return null;
                }
                outBmp = Bitmap.createScaledBitmap(originBmp, idstWidth,
                        idstHeight, false);
            }
            if ((outBmp != originBmp) && (originBmp != null)) {
                originBmp.recycle();
            }
            return outBmp;
        } catch (Exception localException) {
            localException.printStackTrace();
        } catch (OutOfMemoryError localOutOfMemoryError) {
            localOutOfMemoryError.printStackTrace();
        }
        return null;
    }

    public static Bitmap getResizedBmp(Bitmap originBmp, int targetWidth,
                                       int targetHeight, boolean scaled) {
        if (originBmp == null) {
            return null;
        }
        Bitmap outBmp = null;
        int iBmpWidth = originBmp.getWidth();
        int iBmpHeight = originBmp.getHeight();
        // Bitmap originBmp = null;
        try {
            if (!scaled) {
                float widthRate = Math.min(1.0F, (1.0F * targetWidth)
                        / iBmpWidth);
                float heightRate = Math.min(1.0F, (1.0F * targetHeight)
                        / iBmpHeight);
                float bmpRate = Math.max(widthRate, heightRate);
                int idstWidth = (int) (iBmpWidth * bmpRate);
                int idstHeight = (int) (iBmpHeight * bmpRate);

                if (originBmp != null) {
                    outBmp = Bitmap.createScaledBitmap(originBmp, idstWidth,
                            idstHeight, false);
                }
                if ((outBmp != originBmp) && (originBmp != null)) {
                    originBmp.recycle();
                    originBmp = outBmp;
                }

                iBmpWidth = originBmp.getWidth();
                iBmpHeight = originBmp.getHeight();

                int iOutWidth = Math.min(iBmpWidth, targetWidth);
                int iOutHeight = Math.min(iBmpHeight, targetHeight);
                int ixFirstPixel = (iBmpWidth - iOutWidth) / 2;
                int iyFirstPixel = (iBmpHeight - iOutHeight) / 2;
                if (originBmp != null) {
                    outBmp = Bitmap.createBitmap(originBmp, ixFirstPixel,
                            iyFirstPixel, iOutWidth, iOutHeight);
                }
            } else {
                float f7 = Math.min(1.0F, (1.0F * targetWidth) / iBmpWidth);
                float f14 = Math.min(f7, 1.0F * targetHeight / iBmpHeight);
                int idstWidth = (int) (iBmpWidth * f14);
                int idstHeight = (int) (iBmpHeight * f14);
                double d3 = (int) (Math.log(1.0F / f14) / Math.log(2.0D));
                if (originBmp == null) {
                    // Log.v(TAG, "doJob null:"+paramObject);
                    return null;
                }
                outBmp = Bitmap.createScaledBitmap(originBmp, idstWidth,
                        idstHeight, false);
            }
            if ((outBmp != originBmp) && (originBmp != null)) {
                originBmp.recycle();
            }
            return outBmp;
        } catch (Exception localException) {
            localException.printStackTrace();
        } catch (OutOfMemoryError localOutOfMemoryError) {
            localOutOfMemoryError.printStackTrace();
        }
        return null;
    }

    public static int detectNetwork(Activity act) {

        ConnectivityManager manager = (ConnectivityManager) act
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return Constants.NET_LINK_TYPE_ERROR;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return Constants.NET_LINK_TYPE_ERROR;
        }
        if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return Constants.NET_LINK_TYPE_WIFI;
        } else if (networkinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return Constants.NET_LINK_TYPE_MOBILE;
        }
        return Constants.NET_LINK_TYPE_ERROR;
    }

    public static boolean checkNetwork(final Activity activity) {
        if (activity == null) {
            return false;
        }
        switch (detectNetwork(activity)) {
            case Constants.NET_LINK_TYPE_ERROR:
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(activity,
                                R.string.net_unavailable, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
                return false;
        }
        return true;
    }

    /**
     * Simple network connection check.
     *
     * @param context
     */
    public static void checkConnection(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, R.string.no_network_connection_toast, Toast.LENGTH_LONG).show();
            Log.e(TAG, "checkConnection - no connection found");
        }
    }
}
