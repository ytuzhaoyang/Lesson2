package com.tengyun.lesson2;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2015/12/29.
 */
public class CircleTransFormation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        //CLAMP拉伸           MIRROR正反            REPEAT平铺

         paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        new Canvas(bitmap).drawCircle(source.getWidth()/2, source.getHeight()/2, Math.min(source.getHeight()/2,source.getWidth()/2), paint);
        //释放原图,否则内存会吃紧
        source.recycle();

        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
