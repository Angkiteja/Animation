package com.example.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;


public class PulseAnimationView extends View {
    //properti
    private float mRadius;
    private final Paint mPaint = new Paint(); //memberi warna
    private static final int COLOR_ADJUSTER = 5;
    private float mX; //posisi x
    private float mY; //posisi y

    private static final int ANIMATION_DURATION = 4000;
    private static final long ANIMATION_DELAY = 1000;

    private AnimatorSet mPulseAnimatorSet = new AnimatorSet();

    public PulseAnimationView(Context context) {
        super(context);
    }

    public PulseAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //yg mau digambar apa?


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mRadius, mPaint);
    }

    //yg diubah radius nya
    public void setRadius(float radius){
        this.mRadius = radius;

        mPaint .setColor(Color.GREEN + (int)radius/COLOR_ADJUSTER);
        invalidate();
        //invalidate : menggambar lagi menggunakan mDraw nya
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //simpan X Y nya
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mX = event.getX();
            mY = event.getY();
        }
        //panggil mPulseAnimatorSet
        if(mPulseAnimatorSet != null && mPulseAnimatorSet.isRunning()){
            mPulseAnimatorSet.cancel();
        }
        mPulseAnimatorSet.start();

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        ObjectAnimator growAnimator = ObjectAnimator.ofFloat(this, "radius", 8,getWidth());
        //berapa lama?
        growAnimator.setDuration(ANIMATION_DURATION);
        //interpolar pake apa?
        growAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator shrinkAnimator = ObjectAnimator.ofFloat(this, "radius", getWidth(),0);
        //berapa lama?
        shrinkAnimator.setDuration(ANIMATION_DURATION);
        //interpolar pake apa?
        shrinkAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        //DELAY
        shrinkAnimator.setStartDelay(ANIMATION_DELAY);


        ObjectAnimator repeatAnimator = ObjectAnimator.ofFloat(this, "radius", 0, getWidth());
        repeatAnimator.setStartDelay(ANIMATION_DELAY);
        repeatAnimator.setDuration(ANIMATION_DURATION);

        mPulseAnimatorSet.play(growAnimator).before(shrinkAnimator);
        mPulseAnimatorSet.play(repeatAnimator). after(shrinkAnimator);
    }
}
