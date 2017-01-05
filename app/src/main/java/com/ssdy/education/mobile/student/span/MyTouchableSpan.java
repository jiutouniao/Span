package com.ssdy.education.mobile.student.span;

import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

import com.ssdy.education.mobile.student.LogUtil;

/**
 * 描述：
 * 作者：shaobing
 * 时间： 2017/1/5 10:09
 */
public class MyTouchableSpan  extends  TouchableSpan{
    @Override
    public boolean onTouch(View widget, MotionEvent m) {
        LogUtil.d("onTouch");
        return true;
    }

    @Override
    public void updateDrawState(TextPaint ds) {

    }
}
