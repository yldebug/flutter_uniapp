package com.example.flutter_uniapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Matrix;

import androidx.annotation.Nullable;

public class MiniAppStartLoadingView extends View {

    private int mTotalWidth, mTotalHeight;
    private int mCenterX, mCenterY;
    //底色画笔
    private Paint mCirclePaint;
    //进度条画笔
    private Paint mProgressPaint;
    private float sweepAngle = 10;
    private int startAngle = 1;


    public MiniAppStartLoadingView(Context context) {
        super(context);
        initPaint(context);
    }

    public MiniAppStartLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public MiniAppStartLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    public MiniAppStartLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint(context);
    }

    private void initPaint(Context context) {
        //画黑底的深色圆画笔
        mCirclePaint = new Paint();
        //抗锯齿
        mCirclePaint.setAntiAlias(false);
        // 防抖动
        mCirclePaint.setDither(true);
        // 开启图像过滤，对位图进行滤波处理。
        mCirclePaint.setFilterBitmap(true);
        mCirclePaint.setColor(Color.parseColor("#E6E6E6"));
        //空心圆
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(1.0f);

        //画彩色圆弧的画笔
        mProgressPaint = new Paint();
        //抗锯齿
        mProgressPaint.setAntiAlias(false);
        // 防抖动
        mProgressPaint.setDither(true);
        // 开启图像过滤，对位图进行滤波处理。
        mProgressPaint.setFilterBitmap(true);
        mProgressPaint.setColor(Color.parseColor("#52B259"));
        //空心圆
        mProgressPaint.setStyle(Paint.Style.STROKE);
        //设置笔刷样式为原型
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置圆弧粗
        mProgressPaint.setStrokeWidth(6.0f);
        //将绘制的内容显示在第一次绘制内容之上
        mProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas去锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        //画底色圆
        int width = getWidth() / 2 / 2;
        canvas.drawCircle(mCenterX, mCenterY, width, mCirclePaint);
        //按照圆心旋转
        Matrix matrix = new Matrix();
        matrix.setRotate(startAngle, mCenterX, mCenterY);


        /**
         * 这是一个居中的圆
         */
        float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;

        RectF oval = new RectF(x, y, getWidth() - x, getHeight() - y);
        canvas.drawArc(oval, startAngle, sweepAngle, false, mProgressPaint);

        startAngle++;
        if (startAngle == 360) {
            startAngle = 1;
            sweepAngle++;
        }
        if (sweepAngle != 10 && sweepAngle < 300) {
            sweepAngle++;
        }

        //启动绘制
        postInvalidateDelayed(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mCenterX = mTotalWidth / 2;
        mCenterY = mTotalHeight / 2;
    }
}
