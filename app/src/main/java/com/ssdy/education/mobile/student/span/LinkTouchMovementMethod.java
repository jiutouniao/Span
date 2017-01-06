package com.ssdy.education.mobile.student.span;

import android.os.Handler;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

import com.ssdy.education.mobile.student.LogUtil;

/**
 * 需要防止第二个触摸点的参与
 * 描述：图文混排点击事件处理
 * 作者：shaobing
 * 时间： 2016/12/29 20:52
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {
    private TouchableSpan mPressedSpan;
    private long startTime;
    private float startx;
    private float starty;
    private float movex;
    private float movey;
    // 0默认状态 1.排除第一次点击  2.代表长按
    private  int state = 0;
    private Handler mHandler = new Handler();
    @Override
    public boolean onTouchEvent(TextView textView, final Spannable spannable, MotionEvent event) {
        LogUtil.d("onTouchEvent");
        switch (event.getAction()& MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                startx = event.getX();
                starty = event.getY();
                movex =event.getX();
                movey = event.getY();
                mPressedSpan = getPressedSpan(textView, spannable, event);
                if (mPressedSpan != null) {
                    mPressedSpan.setPressed(true);
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPressedSpan != null ) {
                            if((Math.abs(movex-startx)+Math.abs(movey-starty))<4 && state==0){
                                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan), spannable.getSpanEnd(mPressedSpan));
                                state =1;
                            }
                        }

                    }
                },100);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPressedSpan != null ) {
                            //判断是否为长按
                            if((System.currentTimeMillis()-startTime)>500){
                                mPressedSpan.setLongClick(true);
                            }
                            mHandler.removeCallbacksAndMessages(null);
                        }
                    }
                },500);
                break;
            case MotionEvent.ACTION_MOVE:{
                LogUtil.d("ACTION_MOVE");
                TouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
                if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                    mPressedSpan.setPressed(false);
                    mPressedSpan = null;
                }
                if( event.getActionIndex()==0){
                    movex =event.getX();
                    movey = event.getY();
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                LogUtil.d("ACTION_UP");
                if (mPressedSpan != null) {
                    mPressedSpan.setPressed(false);
                    super.onTouchEvent(textView, spannable, event);
                }
                if(mPressedSpan != null && state==0){
                    Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan), spannable.getSpanEnd(mPressedSpan));
                }
                mPressedSpan = null;
                state = 0;
//                Selection.removeSelection(spannable);
                break;
        }
        return true;
    }



    TouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);
        TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);
        TouchableSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

}

