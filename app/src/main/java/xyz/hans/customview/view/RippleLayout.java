package xyz.hans.customview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by Hans on 2016/10/8.
 * 带水波纹的 RelativeLayout 拦截事件
 * 此VieGroup内的View无法获取事件
 */
public class RippleLayout extends RelativeLayout {
    private static final String TAG = RippleLayout.class.getSimpleName();
    //点击的x轴位置
    private float mClickX;
    //点击的y轴位置
    private float mClickY;
    private Paint mPaint;
    //圆半径
    public float mRadius;
    //值动画 从50-1000
    private ValueAnimator mRadiusValueAnimator;
    private static final float START = 50;
    private static final float END = 1000;

    public RippleLayout(Context context) {
        super(context);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        //开启抗锯齿
        mPaint.setAntiAlias(true);
        //透明
        mPaint.setColor(Color.TRANSPARENT);
        //透明度为50
        mPaint.setAlpha(50);
        //一定要手动设置为false 因为ViewGroup默认不会调用onDraw 除非手动调用此方法
        // 或者设置背景
        setWillNotDraw(false);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            //当手机按下和移动的时候会绘制一个半径为50的圆
            mClickX = event.getX();
            mClickY = event.getY();
            Log.d(TAG, "坐标x为：" + mClickX + " 坐标y为：" + mClickY);
            mRadius = START;//初始绘制的半径是50
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指抬起的时候，会开始动画
            Log.d(TAG, "ACTION_UP");
            startAnimator();
        }
        return super.onInterceptTouchEvent(event);
    }

    private void startAnimator() {
        if (mRadiusValueAnimator == null) {
            mRadiusValueAnimator = ValueAnimator.ofFloat(START, END);
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
        Log.d(TAG, "draw");
        canvas.drawCircle(mClickX, mClickY, mRadius, mPaint);
    }
}
