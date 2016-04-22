package com.swiftkaydevelopment.fullsailcommunicate.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Kevin Haines on 9/19/15.
 */
public class Dot extends View {

    private int paintColor;
    private boolean isSelected;

    public Dot(Context context) {
        super(context);
        init();
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Dot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        this.setLayoutParams(params);
    }
    public void setPaintColor(int colorRes){
        paintColor = colorRes;

    }
    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;

    }
    @Override
    protected void onDraw(Canvas canvas) {

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }


        int w = getWidth(), h = getHeight();

        final Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        if(isSelected){
            paint.setStyle(Paint.Style.FILL);
        }else {
            paint.setStyle(Paint.Style.STROKE);
        }

        canvas.drawCircle(w / 3, h / 3, w / 3, paint);


    }
}

