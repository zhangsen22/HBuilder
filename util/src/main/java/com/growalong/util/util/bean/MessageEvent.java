package com.growalong.util.util.bean;

/**
 * 作者 : Created by zhangsen on 2019/5/11
 * 邮箱 : zhangsen839705693@163.com
 */
public class MessageEvent {

    /**
     * 1:挂单 委托出售  切换到我的委托订单页  刷新
     * 2:挂单 委托购买  切换到我的委托订单页  刷新
     */
    private int type;

    public MessageEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
