package com.example.apple.sometestdemo.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.Utils.Mylog;


/**
 * Created by apple on 16/6/27.
 * 自定义view
 */
public class MyCustomView extends View {

    private String myText;

    private int myTextColor;

    private int myTextSize;

    private Paint myPaint;
    private Rect mBound;

    public MyCustomView(Context context , AttributeSet attrs){
        this(context,attrs,0);
    }

    public MyCustomView(Context context){
        this(context,null);
    }

    public MyCustomView(Context context , AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);

        // 获得自定义样式属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCustomView,defStyle,0);
        for (int i = 0,n = a.getIndexCount(); i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.MyCustomView_myText:
                    myText = a.getString(attr);
                    break;
                case R.styleable.MyCustomView_myTextColor:
                    myTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyCustomView_myTextSize:
                    /// 默认15dp ，typeValue把sp转化为px
                    myTextSize = a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,15,getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }

        a.recycle();
        myPaint = new Paint();
        myPaint.setTextSize(myTextSize);
        mBound = new Rect();
        myPaint.getTextBounds(myText,0,myText.length(),mBound);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ///super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }
        else {
            myPaint.setTextSize(myTextSize);
            myPaint.getTextBounds(myText,0,myText.length(),mBound);
            float textWidth = mBound.width();
            int desired = (int)(getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }


        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }
        else {
            myPaint.setTextSize(myTextSize);
            myPaint.getTextBounds(myText,0,myText.length(),mBound);
            float textHeight = mBound.height();
            int desired = (int)(getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        myPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), myPaint);

        myPaint.setColor(myTextColor);
        //canvas.drawText(myText, (getWidth() - mBound.width()) / 2, (getHeight() - mBound.height()) / 2, myPaint);

        canvas.drawText(myText, (getWidth() - mBound.width()) / 2, getHeight() / 2 + mBound.height() / 2, myPaint);

        Mylog.dLog("getWidth: " + getWidth());
        Mylog.dLog("mBoundWit: " + mBound.width());
        Mylog.dLog("getHeight: " + getHeight());
        Mylog.dLog("mBoundHeight: " + mBound.height());
    }
}
