/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.clj.blesample.R;

public class MainBoard extends View {
    private float left_progress;
    private float right_progress;

    private Matrix matrix;
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix matrix3;

    private Bitmap bitmap_left;
    private Bitmap bitmap_mid;
    private Bitmap bitmap_right_1;
    private Bitmap bitmap_right_2;


    public MainBoard(Context context, AttributeSet attrs) {

        super(context, attrs);
        bitmap_left = BitmapFactory.decodeResource(getResources(), R.mipmap.log1);
        bitmap_mid = BitmapFactory.decodeResource(getResources(), R.mipmap.logo2);
        bitmap_right_1 = BitmapFactory.decodeResource(getResources(), R.mipmap.log4);
        bitmap_right_2 = BitmapFactory.decodeResource(getResources(), R.mipmap.logo4);


        left_progress = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Integer height = getHeight() * 3 / 5;

        // Utils.Toast(getContext(), "degree" + left_progress);
        Log.d("cly", "onDraw: " + left_progress + "left bar getWidth" + bitmap_left.getWidth() + ":" + bitmap_left.getHeight());
        //最大变换范围
        //left image
        matrix = new Matrix();
        matrix.setRotate(left_progress, bitmap_left.getWidth(), bitmap_left.getHeight() / 2);
        matrix.postTranslate(0, height);
        canvas.drawBitmap(bitmap_left, matrix, paint);

        //mid image
        matrix1 = new Matrix();
        matrix1.setTranslate(bitmap_left.getWidth(), height);
        canvas.drawBitmap(bitmap_mid, matrix1, paint);

        Integer left_width = bitmap_left.getWidth() + bitmap_mid.getWidth();
        //right bar
        matrix2 = new Matrix();
        matrix2.setRotate(right_progress, 0, bitmap_right_1.getHeight() / 2);

        matrix2.postTranslate(left_width, height);
        canvas.drawBitmap(bitmap_right_1, matrix2, null);
        double rect_angle = -right_progress / 180 * Math.PI;

        int width = bitmap_right_1.getWidth();
        float x_position = (float) Math.cos(rect_angle) * width;
        float y_position = (float) Math.sin(rect_angle) * width;
        canvas.drawBitmap(bitmap_right_2, x_position + left_width, height - y_position, null);
    }

    public void SetLeftDegree(int degree) {
        this.left_progress = degree;
        invalidate();

    }

    public void SetRightDegree(int degree) {
        this.right_progress = degree;
        invalidate();

    }
}
