package com.ff.paint.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ff.paint.R;

import androidx.annotation.Nullable;

/**
 * description:
 * author: FF
 * time: 2019/4/5 16:19
 */
public class XfermodeEraserView extends View {

    private static final String TAG = "XfermodeEraserView";

    private Paint mPaint;

    private Bitmap mTxtBmp, mSrcBmp, mDstBmp;

    private Path mPath;

    private Canvas dstCanvas;

    private PorterDuffXfermode mXfermode;

    private int bitmapWidth, bitmapHeight;

    private boolean isComplete;

    public XfermodeEraserView(Context context) {
        this(context, null);
    }

    public XfermodeEraserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeEraserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);// 滑动宽度

        // 禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 初始化图片对象
        mTxtBmp = BitmapFactory.decodeResource(getResources(), R.drawable.result);// 底图
        mSrcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.eraser);// 上图

        bitmapWidth = mSrcBmp.getWidth();
        bitmapHeight = mSrcBmp.getHeight();

        mDstBmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);

        // 路径（贝塞尔曲线）
        mPath = new Path();

        dstCanvas = new Canvas(mDstBmp);

        // 设置模式为 SRC_OUT，擦橡皮区域为交集区域需要清掉像素
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");


        if (isComplete) {
            Toast.makeText(getContext(), "完成", Toast.LENGTH_SHORT).show();
            canvas.drawBitmap(mTxtBmp, 0, 0, mPaint);
            return;
        }

        // 绘制刮奖结果
        canvas.drawBitmap(mTxtBmp, 0, 0, mPaint);

        // 使用离屏绘制
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint);

        // 先将路径绘制到bitmap上
        dstCanvas.drawPath(mPath, mPaint);

        // 绘制目标图像
        canvas.drawBitmap(mDstBmp, 0, 0, mPaint);
        // 设置模式为 SRC_OUT，擦橡皮区域为交集区域需要清掉像素
        mPaint.setXfermode(mXfermode);
        // 绘制源图像
        canvas.drawBitmap(mSrcBmp, 0, 0, mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);
    }

    private float mEventX, mEventY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEventX = event.getX();
                mEventY = event.getY();
                mPath.moveTo(mEventX, mEventY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (event.getX() - mEventX) / 2 + mEventX;
                float endY = (event.getY() - mEventY) / 2 + mEventY;
                // 画二阶贝塞尔曲线
                mPath.quadTo(mEventX, mEventY, endX, endY);
                mEventX = event.getX();
                mEventY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                new Thread(mRunnable).start();
                break;
        }
        return true;// 消费事件
    }

    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {

            int wipeArea = 0;
            int totalArea = bitmapWidth * bitmapHeight;

            mPixels = new int[totalArea];

            // 拿到所有的像素信息
            mDstBmp.getPixels(mPixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);

            // 遍历统计擦除的区域
            for (int i = 0; i < bitmapWidth; i++) {
                for (int j = 0; j < bitmapHeight; j++) {
                    int index = i + j * bitmapWidth;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            Log.d(TAG, "wipeArea: " + wipeArea + " totalArea: " + totalArea);

            // 根据所占百分比，进行一些操作
            if (wipeArea > 0 && totalArea > 0) {
                int percent = wipeArea * 100 / totalArea;
                Log.d(TAG, "percent = " + percent + "%");

                if (percent < 40) {// 小于40%全部显示
                    isComplete = true;
                    postInvalidate();
                }
            }
        }
    };
}
