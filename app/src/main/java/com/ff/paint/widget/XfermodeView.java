package com.ff.paint.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * description: 图像混合模式
 * author: FF
 * time: 2019/4/1 20:29
 */
public class XfermodeView extends View {

    private static final String TAG = "XfermodeView";

    private boolean offScreen = false;// 是否使用离屏绘制

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    private PorterDuffXfermode mXfermode;

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();


        // API 14之后，图层混合的有些函数是不支持硬件加速，需要禁用
        // 如果放在onDraw中设置，会导致onDraw无限次被调用
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 使用一个Bitmap缓冲

        // 在onDraw设置LAYER_TYPE_HARDWARE或者LAYER_TYPE_NONE（默认），不会出现onDraw无限次被调用的情况
        // setLayerType(View.LAYER_TYPE_HARDWARE, null);// 使用GPU缓冲，也就是硬件加速
        // setLayerType(View.LAYER_TYPE_NONE, null);// 默认

        // 设置背景，可以看到离屏绘制的区别
        setBackgroundColor(Color.GRAY);// 如果放在onDraw中，会再次执行onDraw

        // 设置混合模式为DST_IN
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");

        // 图层混合有三种使用场景
        // 1.ComposeShader的构造方法
        // 2.画笔Paint.setXfermode()
        // 3.PorterDuffColorFilter

        int layerId = 0;
        if (offScreen) {
            // 使用离屏绘制，短时间的离屏缓冲，把要绘制的内容单独绘制在缓冲层，保证Xfermode的使用不会出现错误
            // 在绘制之前保存，绘制之后恢复
            layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint);
        }

        // 绘制目标图，矩形
        canvas.drawBitmap(createRectBitmap(mWidth, mHeight), 0, 0, mPaint);
        // 设置混合模式
        mPaint.setXfermode(mXfermode);
        // 绘制源图，圆图，重叠区域右下角部分
        canvas.drawBitmap(createCircleBitmap(mWidth, mHeight), 0, 0, mPaint);
        // 清除混合模式
        mPaint.setXfermode(null);

        // 画布回滚
        if (offScreen && layerId != 0) {
            canvas.restoreToCount(layerId);
        }
    }

    public void setOffScreen(boolean offScreen) {
        this.offScreen = offScreen;
    }

    /**
     * 画矩形Dst
     *
     * @param width
     * @param height
     * @return
     */
    private Bitmap createRectBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint dstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dstPaint.setColor(0xFF66AAFF);
        canvas.drawRect(new Rect(width / 20, height / 3, 2 * width / 3, 19 * height / 20), dstPaint);
        return bitmap;
    }

    /**
     * 画圆Src
     *
     * @param width
     * @param height
     * @return
     */
    private Bitmap createCircleBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        srcPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(width * 2 / 3, height / 3, height / 4, srcPaint);
        return bitmap;
    }
}
