package com.lantern.core.myapplication.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import com.lantern.core.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Keep
public class TreeImageView extends android.support.v7.widget.AppCompatImageView {

    String tag = "TreeImageView";

    Paint paint;
    Bitmap apple;

    List<AppleRect> appleRects;

    @Override
    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        update();
    }


    private int paddingTop;


    void initPaint() {
        appStatusMap = new HashMap<>();
        appleRects = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        apple = BitmapFactory.decodeResource(getResources(), R.mipmap.apple);
    }

    public TreeImageView(Context context) {
        super(context);
        initPaint();
    }

    public TreeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    void update() {
        setPadding(0, paddingTop, 0, 0);
    }



    Map<AppleRect,Boolean> appStatusMap;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(tag, "onDraw");

        for(AppleRect appleRect:appleRects){
            canvas.drawBitmap(apple, null, appleRect.getRect(), paint);

            if(!appStatusMap.containsKey(appleRect)){
                appStatusMap.put(appleRect,true);
                applePick(appleRect);
            }

        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.e(tag, "x,y->" + event.getX() + "," + event.getY());
            appleRects.add(new AppleRect(new Rect(x, y, x+apple.getWidth(), y+apple.getHeight())));
            invalidate();
        }
//        int apppleLeft = destRect.left;
//        int apppleRight = destRect.right;
//        int apppleTop = destRect.top;
//        int apppleBottom = destRect.bottom;
//        Log.e(tag,apppleLeft+" "+apppleRight+" "+apppleTop+" "+apppleBottom);
//        if(x >= apppleLeft && x <= apppleRight
//        && y >= apppleTop && y <= apppleBottom){
//            Log.e(tag,"touch at apple");
//            applePick();
//            return true;
//        }
        return super.onTouchEvent(event);
    }



    void applePick(final AppleRect appleRect) {
        int time = 1500;
        int top = appleRect.getTop();
        int bottom = appleRect.getBottom();
        ObjectAnimator topAnimator = ObjectAnimator.ofInt(appleRect, "top", top, getHeight() - apple.getHeight());
        ObjectAnimator bottomAnimator = ObjectAnimator.ofInt(appleRect, "bottom", bottom, getHeight());
        final AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(topAnimator, bottomAnimator); //设置动画
        animatorSet.setDuration(time);  //设置动画时间
        animatorSet.start();

        postDelayed(new Runnable() {
            @Override
            public void run() {
                appStatusMap.put(appleRect,false);
            }
        }, time);

        new Thread() {
            @Override
            public void run() {
                while (appStatusMap.get(appleRect)) {
                  postInvalidateOnAnimation();
                }
            }
        }.start();

    }
}
