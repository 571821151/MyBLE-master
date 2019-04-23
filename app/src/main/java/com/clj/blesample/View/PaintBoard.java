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

    private double degree;
    private Bitmap bitmap_right;
    private Matrix matrix;

    public PaintBoard(Context context, AttributeSet attrs) {

        super(context, attrs);
        bitmap_right = BitmapFactory.decodeResource(getResources(), R.mipmap.logo2);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Utils.Toast(getContext(),"degree"+degree);
        if (degree > 50) return;
        double rect_angle = -degree / 180 * Math.PI;

        Paint mPaint = new Paint();
        int width = bitmap_right.getWidth();
        float x_position = (float) Math.cos(rect_angle) * width;
        float y_position = (float) Math.sin(rect_angle) * width;
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        matrix.postRotate((float) degree, 0, bitmap_right.getHeight()/2);//顺时针旋转45度
        canvas.drawBitmap(bitmap_right, matrix, mPaint);
        canvas.drawBitmap(bitmap_right, x_position, y_position, mPaint);
    }

    public void SetDegree(int degree) {
        this.degree = degree;
        invalidate();

    }
}