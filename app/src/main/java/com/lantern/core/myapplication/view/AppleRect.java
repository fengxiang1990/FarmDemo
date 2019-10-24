package com.lantern.core.myapplication.view;

import android.graphics.Rect;
import android.support.annotation.Keep;

@Keep
public class AppleRect {

    private int top;

    private int bottom;

    private Rect rect;

    public AppleRect(Rect rect){
        this.rect = rect;
    }

    public int getTop() {
        return rect.top;
    }

    public void setTop(int top) {
        this.top = top;
        update();
    }

    public int getBottom() {
        return rect.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
        update();
    }

    private void update(){
        rect.top = this.top;
        rect.bottom = this.bottom;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
