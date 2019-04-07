package com.ff.paint.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ff.paint.R;

import androidx.annotation.Nullable;

/**
 * description: BitmapShader中TileMode的示例
 * author: FF
 * time: 2019/4/1 17:40
 */
public class BitmapShaderLayout extends View {

    private static final String TAG = "BitmapShaderLayout";

    private Paint mPaint;

    private BitmapShader mBitmapShader, mBitmapShader1, mBitmapShader2;

    public BitmapShaderLayout(Context context) {
        this(context, null);
    }

    public BitmapShaderLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapShaderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);

        /**
         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
         * Bitmap:构造shader使用的bitmap
         * tileX：X轴方向的TileMode
         * tileY:Y轴方向的TileMode
         * REPEAT: 绘制区域超过渲染区域的部分，重复排版
         * CLAMP:  绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
         * MIRROR: 绘制区域超过渲染区域的部分，镜像翻转排版
         */
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader1 = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mBitmapShader2 = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 这里因为没有重写，所以设置wrap_content、match_parent都是一样的
        // 可以看下View的onMeasure中调用了getDefaultSize的方法
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 不要在onMeasure、onLayout、onDraw等方法里面做new对象的操作

        Log.d(TAG, "onDraw: ");

        mPaint.setShader(mBitmapShader);
        canvas.drawRect(0, 0, 400, 400, mPaint);

        mPaint.setShader(mBitmapShader1);
        canvas.drawRect(0, 450, 400, 850, mPaint);

        mPaint.setShader(mBitmapShader2);
        canvas.drawRect(0, 900, 400, 1300, mPaint);
    }
}

