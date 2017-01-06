package com.ssdy.education.mobile.student.span;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.ssdy.education.mobile.student.LogUtil;

/**
 * 描述：图文混排点击事件处理
 * 作者：shaobing
 * 时间： 2016/12/29 20:52
 */
public class TouchableSpan extends ClickableSpan {
    //是否正在被按压
    private boolean mIsPressed;
    //是否被选择
    private boolean isLongClick;
    private int mPressedBackgroundColor;
    private int mNormalTextColor;
    private int mPressedTextColor;
    //当前字段的位置
    private int number;
    //监听
    private OnSpanClickListener listener;


    /**
     *
     * @param normalTextColor 正常的字体颜色
     * @param pressedTextColor 点击之后的字体颜色
     * @param pressedBackgroundColor  背景颜色
     * @param number                   当前span段数
     * @param listener                 监听反馈
     */
    public TouchableSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor,int number,OnSpanClickListener listener) {
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
        this.number = number;
        this.listener= listener;
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }

    public void setLongClick(boolean isLongClick) {
        this.isLongClick = isLongClick;
        if(listener!=null){
            listener.onSpanLongClick(number);
        }
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0x00eeeeee;
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        LogUtil.d("TouchableSpan  onClick");
        if(!isLongClick){
            if(listener!=null){
                listener.onSpanClick(number);
            }
        }else{
            isLongClick = false;
        }

    }
    public interface OnSpanClickListener{
        //长按
        void onSpanLongClick(int number);
        //短按
        void onSpanClick(int number);
    }
}