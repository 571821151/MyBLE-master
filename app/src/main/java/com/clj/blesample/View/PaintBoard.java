/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.clj.blesample.R;
import com.clj.blesample.comm.Utils;

public class PaintBoard extends View {

    private float degree;
    private Bitmap bitmap_right;
    private Matrix matrix;

    public PaintBoard(Context context, AttributeSet attrs) {

        super(context, attrs);
        bitmap_right = BitmapFactory.decodeResource(getResources(), R.mipmap.logo2);
        matrix = new Matrix();
        degree = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("cly", "onDraw: " + degree);
        //最大变换范围


        matrix.postRotate(degree, 0, bitmap_right.getHeight());//顺时针旋转45度

        canvas.drawBitmap(bitmap_right, matrix, null);
//        double rect_angle = -degree / 180 * Math.PI;
//
//        Paint mPaint = new Paint();
//        int width = bitmap_right.getWidth();
//        float x_position = (float) Math.cos(rect_angle) * width;
//        float y_position = (float) Math.sin(rect_angle) * width;
//        canvas.drawBitmap(bitmap_right, x_position, y_position, mPaint);
    }

    public void SetDegree(float degree) {
        this.degree = degree;
        invalidate();

    }
}
