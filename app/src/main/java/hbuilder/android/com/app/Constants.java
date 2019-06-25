package hbuilder.android.com.app;

import hbuilder.android.com.util.FileUtils;

/**
 */
public class Constants {
    //二维码图片保存本地的路径
    public static final String FILTER_IMAGE_PATH = FileUtils.getFilterImageDir() + "filter_image.jpg";
    public static final String FILTERCHONGBI_IMAGE_PATH = FileUtils.getFilterImageDir() + "chongbi_filter_image.jpg";
    public static final String JIAOCHENGGONGLUO = "https://down.no-time.cn/teacher2.html";
    public static final String HOWGETALIPAYID = "https://down.no-time.cn/aliid.htm";
    public static final String NOTIFYCLICK = "https://down.no-time.cn/msg.html";
    public static final String USERXIEYI = "https://down.no-time.cn/userpro.html";
    public static final String HOWGETDIANYUANERWEIMA = "https://down.no-time.cn/wxqr.htm";
    public static final String SESSIONID = "sessionid";
    public static final String WALLET_BALANCE = "wallet_balance";
    public static final String USDTPRICE = "Usdt_Price";
    /**
     * android 8.0以上通知栏渠道ID
     */
    public static final String NOTIFICATION_CHANNEL_DOWNLOAD = "bingo_channel_02";//下载安装包进度通知栏 渠道ID



    public static final int REQUESTCODE_10 = 10;
    public static final int REQUESTCODE_11 = 11;
}
