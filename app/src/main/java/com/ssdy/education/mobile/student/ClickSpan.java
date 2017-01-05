package com.ssdy.education.mobile.student;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * description: TextView 点击事件处理
 * Date: 2016/7/11 17:30
 * User: shaobing
 */
public class ClickSpan extends ClickableSpan implements ParcelableSpan {

    //当前字段的位置
    private int number;
    //监听
    private OnSpanClickListener listener;

    private Context mContext;
    public ClickSpan(Context mContext) {

    }
    public ClickSpan(Context mContext, int number, OnSpanClickListener listener) {
        this.number = number;
        this.listener= listener;
        this.mContext =mContext;
    }

    @Override
    public void onClick(View view) {
        LogUtil.d("点击");
        if(listener!=null){
            listener.OnSpanClick(number);
        }
        //点击设置高亮
        if(view instanceof TextView)
        ((TextView)view).setHighlightColor(Color.BLUE);
    }

    @Override
    public int getSpanTypeId() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
//        ds.setColor(Color.RED);
//        ds.setUnderlineText(true);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public   interface OnSpanClickListener{

        void OnSpanClick(int number);
    }



}
