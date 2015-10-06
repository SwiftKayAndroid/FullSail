package com.swiftkaydevelopment.fullsailcommunicate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by khaines178 on 9/19/15.
 */
public class Dot  extends View {


    int paintColor;
    boolean isSelected;

    public Dot(Context context) {
        super(context);
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Dot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

        canvas.drawCircle(w/3,h/3,w/3,paint);


    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
                sbmp.getWidth() / 2+0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);


        return output;
    }

}
