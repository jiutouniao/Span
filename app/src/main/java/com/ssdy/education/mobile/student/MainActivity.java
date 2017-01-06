package com.ssdy.education.mobile.student;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.ssdy.education.mobile.student.span.LinkTouchMovementMethod;
import com.ssdy.education.mobile.student.span.TouchableSpan;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TouchableSpan.OnSpanClickListener {


    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.sv_text)
    ScrollView svText;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    //录音翻译文件
    private List<RecordFileItem> mListRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        handleSpan();
    }



    private void initData(){
        mListRecord = new ArrayList<>();
        for(int i=0;i<5;i++){
            RecordFileItem item = new RecordFileItem();
            item.setMessage(""+i+i+i+i+i+i+i+i+i+i+i+i+i+i+i);
            item.setTime(i*3);
            mListRecord.add(item);
        }

    }

    /**
     * 分段显示文字
     */
    private void handleSpan() {
        if (mListRecord != null) {
            SpannableStringBuilder mStringBuilder = new SpannableStringBuilder("");
            for (int i = 0; i < mListRecord.size(); i++) {
                int start = mStringBuilder.length();
                mStringBuilder.append(mListRecord.get(i).getMessage());
                if (i != mListRecord.size() - 1) {
                    mStringBuilder.append(",");
                }
                int end = mStringBuilder.length();
                mStringBuilder.setSpan(new TouchableSpan(Color.parseColor("#080808"),Color.parseColor("#080808"),Color.parseColor("#89B4FD"),i,this), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvText.setText(mStringBuilder.append(" "));
            tvText.setMovementMethod(new LinkTouchMovementMethod());// 让链接的点击事件响应的必要一句代码
        }
    }

    @Override
    public void onSpanLongClick(int number) {
        LogUtil.d("onSpanLongClick  :"+number);

    }

    @Override
    public void onSpanClick(int number) {
        LogUtil.d("onSpanClick  :"+number);
    }
}
