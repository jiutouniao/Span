package com.ssdy.education.mobile.student.span;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.ssdy.education.mobile.student.LogUtil;

/**
 * 描述：
 * 作者：shaobing
 * 时间： 2017/1/4 20:48
 */
public class CustomLinkMovementMethod extends LinkMovementMethod{

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        LogUtil.d("onTouchEvent");
        boolean b = super.onTouchEvent(widget,buffer,event);
        //解决点击事件冲突问题
        if(!b && event.getAction() == MotionEvent.ACTION_UP){
            ViewParent parent = widget.getParent();//处理widget的父控件点击事件
            if (parent instanceof ViewGroup) {
                return ((ViewGroup) parent).performClick();
            }
        }
        return b;
    }

    public static CustomLinkMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new CustomLinkMovementMethod();

        return sInstance;
    }


    private static CustomLinkMovementMethod sInstance;

}