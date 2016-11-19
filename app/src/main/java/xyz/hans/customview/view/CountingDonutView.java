package xyz.hans.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Hans on 16/11/5.
 * 描绘了一个 从 一点半方向开始 角度递减的圆环
 * 主要是为了后面的 圆形倒数头像做准备
 * {@link CircleProgressImageView}
 */
public class CountingDonutView extends View {

    private Paint mPaint;
    private RectF mRectF;
    private int mCurrentRadius;

    public CountingDonutView(Context context) {
        super(context);
        init();
    }


    public CountingDonutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountingDonutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形
        mPaint.setStrokeWidth(10);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(-360, 0);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer radius = (Integer) animation.getAnimatedValue();
                mCurrentRadius = radius;
                invalidate();
                Log.d("DonutView", "radius = " + radius);
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("DonutView", " left = " + left + " top = " + top
                + " right = " + right + " bottom = " + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRectF == null) {
            int left = getLeft();
            int right = getRight();
            //注意这里的 left top right bottom是相对于当前View自身的坐标系,而不是屏幕的
            mRectF = new RectF(10, 10, right - left - 10, right - left - 10);
        }
        //从相对于三点钟方向的-45°开始转(就是一点半开始)
        canvas.drawArc(mRectF, 0, mCurrentRadius, false, mPaint);
    }
}
