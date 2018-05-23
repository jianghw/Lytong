package com.tzly.ctcyh.router.custom.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.tzly.ctcyh.router.util.RudenessScreenHelper;

import java.io.ByteArrayOutputStream;

/**
 * 图片工具类
 */

public final class BitmapUtils {

    private void BitmapUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        int width = firstBitmap.getWidth();
        int height = firstBitmap.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(
                firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        int s_width = secondBitmap.getWidth();
        int s_height = secondBitmap.getHeight();

        canvas.drawBitmap(secondBitmap, width - RudenessScreenHelper.ptInpx(40) - s_width,
                height - s_height - RudenessScreenHelper.ptInpx(60), null);
        return bitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
