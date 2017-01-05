package com.ssdy.education.mobile.student.span2;


import android.os.Handler;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;
import com.ssdy.education.mobile.student.LogUtil;

/**
 * 描述：图文混排点击事件处理
 * 作者：shaobing
 * 时间： 2016/12/29 20:52
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {
    private TouchableSpan mPressedSpan;
    private long startTime;
    private Handler mHandler = new Handler();

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        LogUtil.d("onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                mPressedSpan = getPressedSpan(textView, spannable, event);
                if (mPressedSpan != null) {
                    mPressedSpan.setPressed(true);
                    Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
                            spannable.getSpanEnd(mPressedSpan));
                }
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
//                Selection.removeSelection(spannable);
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mPressedSpan != null) {
                    mPressedSpan.setPressed(false);
                    super.onTouchEvent(textView, spannable, event);
                }
                mPressedSpan = null;
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

