package com.growalong.util.util.bean;

/**
 * 作者 : Created by zhangsen on 2019/5/11
 * 邮箱 : zhangsen839705693@163.com
 */
public class MessageEvent {

    /**
     * 1 : 首页客服底部按钮显示隐藏
     * 2 : 一对一班主任咨询发送视频
     * 3:收到环信新消息
     * 4:首页mainactivity 切换到录制的fragment
     * 5:查看了新消息
     * 6:首页好友页面刷新数据
     * 7:自我介绍视频审核通过
     */
    private int type;

    public MessageEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
