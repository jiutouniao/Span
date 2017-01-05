package com.ssdy.education.mobile.student;

/**
 * 描述：用来将录音文件和翻译文件对应
 * 作者：shaobing
 * 时间： 2016/12/14 19:07
 */
public class RecordFileItem {

    //标记文本
    private String message;
    //标记语音的位置
    private long time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
