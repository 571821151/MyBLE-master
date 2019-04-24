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

public class LeftBoard extends View {
    private float progress;
    private Matrix matrix;

    private Bitmap bitmap_left;
    private Bitmap temp_bitmap;

    public LeftBoard(Context context, AttributeSet attrs) {

        super(context, attrs);

        bitmap_left = BitmapFactory.decodeResource(getResources(), R.mipmap.logo2);
        matrix = new Matrix();
        progress = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Utils.Toast(getContext(), "degree" + progress);
        Log.d("cly", "onDraw: " + progress);
        //最大变换范围
        matrix.setRotate(progress, bitmap_left.getWidth(), bitmap_left.getHeight());
//        Log.d("cly", "" + progress);
//        temp_bitmap = Bitmap.createBitmap(bitmap_left);
        canvas.drawBitmap(bitmap_left, matrix, null);
    }

    public void SetDegree(int degree) {
        this.progress = degree;
        invalidate();

    }
}
