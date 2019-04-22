/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.View;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.clj.blesample.R;

public class PaintBoard extends View {

    private int degeree;

    public PaintBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //paint a circle
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        Matrix matrix = new Matrix();
//        matrix.postTranslate(100, 0);//左移100
        matrix.postRotate(degeree);//顺时针旋转45度
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.logo3), matrix, mPaint);
    }

    public void SetDegreen(int degeree) {
        this.degeree = degeree;
        invalidate();

    }
}