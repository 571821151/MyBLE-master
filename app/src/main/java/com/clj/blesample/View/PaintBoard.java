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
import android.widget.Toast;

import com.clj.blesample.R;

public class PaintBoard extends View {
    private final String TAG = PaintBoard.class.getName();

    private float degree;
    private Bitmap bitmap_right_1;
    private Bitmap bitmap_right_2;
    private Matrix matrix;

    public PaintBoard(Context context, AttributeSet attrs) {

        super(context, attrs);
        bitmap_right_1 = BitmapFactory.decodeResource(getResources(), R.mipmap.logo3);
        bitmap_right_2 = BitmapFactory.decodeResource(getResources(), R.mipmap.logo4);
        matrix = new Matrix();
        degree = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + degree);
        //最大变换范围
        matrix.setRotate(degree, 0, bitmap_right_1.getHeight());//顺时针旋转45度

        canvas.drawBitmap(bitmap_right_1, matrix, null);
        double rect_angle = -degree / 180 * Math.PI;

        int width = bitmap_right_1.getWidth();
        float x_position = (float) Math.cos(rect_angle) * width;
        float y_position = (float) Math.sin(rect_angle) * width;
        Toast.makeText(getContext(), "" + y_position + "x" + x_position, Toast.LENGTH_SHORT).show();
        canvas.drawBitmap(bitmap_right_2, x_position, -y_position, null);
    }

    public void SetDegree(float degree) {
        this.degree = degree;
        invalidate();

    }
}
