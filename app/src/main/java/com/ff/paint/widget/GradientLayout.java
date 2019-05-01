package com.ff.paint.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.ff.paint.R;

/**
 * description: 着色器
 * author: FF
 * time: 2019/3/31 21:03
 */
public class GradientLayout extends View {

    private static final String TAG = "GradientLayout";

    private Paint mPaint;
    private Paint mPaint1;

    private LinearGradient mLinearGradient, mLinearGradient1;// 线性渐变着色器

    private RadialGradient mRadialGradient;// 环形渐变着色器

    private SweepGradient mSweepGradient;// 扫描渐变着色器

    private BitmapShader mBitmapShader;// 位图着色器

    private ComposeShader mComposeShader;// 组合着色器

    public GradientLayout(Context context) {
        this(context, null);
    }

    public GradientLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        init1();
    }

    private void init() {

        mPaint = new Paint(); //初始化
        mPaint.setColor(Color.RED);// 设置颜色
//        mPaint.setARGB(255, 255, 255, 0); // 设置Paint对象颜色,范围为0~255,b数值越大越透明
//        mPaint.setAlpha(20); // 设置alpha不透明度,范围为0~255,数值越小越透明
        mPaint.setAntiAlias(true); // 抗锯齿,边缘更平滑
        mPaint.setStyle(Paint.Style.STROKE); // FILL填充,STROKE描边（向外扩充）,FILL_AND_STROKE填充加描边
        mPaint.setStrokeWidth(4);//描边宽度
//        mPaint.setStrokeCap(Paint.Cap.ROUND); //圆角效果
//        mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格
//        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED)); //设置环形渲染器
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)); //设置图层混合模式
//        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器
//        mPaint.setFilterBitmap(true); //设置双线性过滤
//        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜 ,传入度数和样式
//        mPaint.setTextScaleX(2);// 设置文本缩放倍数
//        mPaint.setTextSize(38);// 设置字体大小
//        mPaint.setTextAlign(Paint.Align.LEFT);//对其方式
//        mPaint.setUnderlineText(true);// 设置下划线
//
//        String str = "Android高级工程师";
//        Rect rect = new Rect();
//        mPaint.getTextBounds(str, 0, str.length(), rect); //测量文本大小，将文本大小信息存放在rect中
//        mPaint.measureText(str); //获取文本的宽
//        mPaint.getFontMetrics(); //获取字体度量对象
    }

    private void init1() {
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.FILL);

        /**
         * 线性渲染,LinearGradient(float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[], @Nullable float positions[], @NonNull TileMode tile)
         * (x0,y0)：渐变起始点坐标
         * (x1,y1):渐变结束点坐标
         * color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
         * color1:渐变结束颜色
         * colors:渐变数组
         * positions:位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化。
         * tile:用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
         */
        mLinearGradient = new LinearGradient(0, 400, 400, 800,
                new int[]{Color.RED, Color.BLUE}, null, Shader.TileMode.CLAMP);

        mLinearGradient1 = new LinearGradient(0, 800, 400, 1200,
                new int[]{Color.GREEN, Color.RED, Color.BLUE}, new float[]{0, 0.7f, 1},
                Shader.TileMode.CLAMP);

        /**
         * 环形渲染，RadialGradient(float centerX, float centerY, float radius, @ColorInt int colors[], @Nullable float stops[], TileMode tileMode)
         * centerX ,centerY：shader的中心坐标，开始渐变的坐标
         * radius:渐变的半径
         * centerColor,edgeColor:中心点渐变颜色，边界的渐变颜色
         * colors:渐变颜色数组
         * stoops:渐变位置数组，类似扫描渐变的positions数组，取值[0,1],中心点为0，半径到达位置为1.0f
         * tileMode:shader未覆盖以外的填充模式。
         */
        mRadialGradient = new RadialGradient(200, 1400, 200,
                new int[]{Color.GREEN, Color.YELLOW, Color.RED}, null, Shader.TileMode.CLAMP);

        /**
         * 扫描渲染，SweepGradient(float cx, float cy, @ColorInt int color0,int color1)
         * cx,cy 渐变中心坐标
         * color0,color1：渐变开始结束颜色
         * colors，positions：类似LinearGradient,用于多颜色渐变,positions为null时，根据颜色线性渐变
         */
        mSweepGradient = new SweepGradient(600, 200, Color.RED, Color.GREEN);

        /**
         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
         * Bitmap:构造shader使用的bitmap
         * tileX：X轴方向的TileMode
         * tileY:Y轴方向的TileMode
         * REPEAT: 绘制区域超过渲染区域的部分，重复排版
         * CLAMP:  绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
         * MIRROR: 绘制区域超过渲染区域的部分，镜像翻转排版
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        /**
         * 组合渲染，
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, Xfermode mode)
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, PorterDuff.Mode mode)
         * shaderA,shaderB:要混合的两种shader
         * Xfermode mode： 组合两种shader颜色的模式
         * PorterDuff.Mode mode: 组合两种shader颜色的模式
         */
        LinearGradient linearGradient = new LinearGradient(400, 800, 800, 1200,
                new int[]{Color.YELLOW, Color.RED, Color.GREEN}, null, Shader.TileMode.CLAMP);
        mComposeShader = new ComposeShader(mBitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
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

        canvas.drawCircle(200, 200, 200, mPaint);

        mPaint1.setShader(mLinearGradient);
        canvas.drawRect(0, 400, 400, 800, mPaint1);


        mPaint1.setShader(mLinearGradient1);
        canvas.drawRect(0, 800, 400, 1200, mPaint1);


        mPaint1.setShader(mRadialGradient);
        canvas.drawCircle(200, 1400, 200, mPaint1);


        mPaint1.setShader(mSweepGradient);
        canvas.drawCircle(600, 200, 200, mPaint1);


        mPaint1.setShader(mBitmapShader);
        canvas.drawRect(400, 400, 800, 800, mPaint1);


        mPaint1.setShader(mComposeShader);
        canvas.drawRect(400, 800, 800, 1200, mPaint1);
    }
}
