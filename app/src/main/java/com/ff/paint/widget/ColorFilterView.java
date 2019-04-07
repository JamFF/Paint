package com.ff.paint.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ff.paint.R;
import com.ff.paint.utils.ColorFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * description: 三种滤镜效果
 * author: FF
 * time: 2019/4/6 09:42
 */
public class ColorFilterView extends View {

    private static final String TAG = "ColorFilterView";

    // 4*5颜色矩阵
    private static final float[] COLOR_MATRIX = {
            1, 0, 0, 0, 100,   // red
            0, 1, 0, 0, 100,   // green
            0, 0, 1, 0, 0,   // blue
            0, 0, 0, 1, 0    // alpha
    };

    private Paint mPaint;
    private Bitmap mBitmap;
    private LightingColorFilter mLightingColorFilter;// 照明效果的滤镜
    private PorterDuffColorFilter mPorterDuffColorFilter;// 图层混合的滤镜
    private ColorMatrixColorFilter mColorMatrixColorFilter;// 颜色矩阵的滤镜

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
//        original();
//        removeRed();
//        addGreen();
//        porterDuff();
//        colorMatrix(COLOR_MATRIX);

        ColorMatrix cm = new ColorMatrix();

        // 亮度调节，按照数值将颜色矩阵缩放
//        cm.setScale(1, 2, 1, 1);

        // 饱和度调节 0-无色彩，1- 默认效果，>1饱和度加强
//        cm.setSaturation(2);

        // 色调调节
        cm.setRotate(0, 45);
        colorMatrix(cm);
    }

    /**
     * 原图
     * R' = R * colorMultiply.R + colorAdd.R
     * G' = G * colorMultiply.G + colorAdd.G
     * B' = B * colorMultiply.B + colorAdd.B
     */
    private void original() {
        mLightingColorFilter = new LightingColorFilter(0xffffff, 0x000000);
    }

    // 红色去除掉
    private void removeRed() {
        mLightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000);
    }

    // 绿色更亮
    private void addGreen() {
        mLightingColorFilter = new LightingColorFilter(0xffffff, 0x003000);
    }

    // 混合红色，设置暗色模式
    private void porterDuff() {
        mPorterDuffColorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
    }

    private void colorMatrix(@NonNull float[] array) {
        mColorMatrixColorFilter = new ColorMatrixColorFilter(ColorFilter.colormatrix_gete);
    }

    private void colorMatrix(@NonNull ColorMatrix matrix) {
        mColorMatrixColorFilter = new ColorMatrixColorFilter(matrix);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//        mPaint.setColorFilter(mLightingColorFilter);
//        mPaint.setColorFilter(mPorterDuffColorFilter);
        mPaint.setColorFilter(mColorMatrixColorFilter);
        canvas.drawBitmap(mBitmap, 0, mBitmap.getHeight(), mPaint);
    }
}
