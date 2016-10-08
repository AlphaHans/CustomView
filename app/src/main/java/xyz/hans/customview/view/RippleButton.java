package xyz.hans.customview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

/**
 * Created by Hans on 2016/10/8.
 */

public class RippleButton extends Button {
    private static final String TAG = RippleButton.class.getSimpleName();
    //点击的x轴位置
    private float mClickX;
    //点击的y轴位置
    private float mClickY;
    private Paint mPaint;
    public float mRadius;
    private ValueAnimator mRadiusValueAnimator;

    public RippleButton(Context context) {
        super(context);
        init();
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        //开启抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setAlpha(50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //检测到点击事件的时候，进行水波拦截
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            mClickX = event.getX();
            mClickY = event.getY();
//            Log.d(TAG, "坐标x为：" + mClickX + " 坐标y为：" + mClickY);
            mRadius = 50;//初始绘制的半径是50
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "ACTION_UP");
            startAnimator();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void startAnimator() {
        if (mRadiusValueAnimator == null) {
            mRadiusValueAnimator = ValueAnimator.ofFloat(50, 1000);
            mRadiusValueAnimator.setDuration(500);
            mRadiusValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRadius = (Float) animation.getAnimatedValue();
//                    Log.d(TAG, "value = " + mRadius);
                    invalidate();
                }
            });
            mRadiusValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //动画完成之后 则恢复原来的样子
                    mRadius = 0;
                    invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mRadiusValueAnimator.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        Log.d(TAG, "draw");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG, "onDraw");
        canvas.drawCircle(mClickX, mClickY, mRadius, mPaint);
    }
}
