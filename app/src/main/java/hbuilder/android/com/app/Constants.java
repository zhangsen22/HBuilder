package hbuilder.android.com.app;
import hbuilder.android.com.util.FileUtils;
/**
 * 静态变量类
 */
public class Constants {
    public static final String HTML_BASE = "https://down.xnbc999.top/";//h5域名和apk下载地址域名
    //二维码图片保存本地的路径
    public static final String FILTER_IMAGE_PATH = FileUtils.getFilterImageDir() + "filter_image.jpg";
    public static final String FILTERCHONGBI_IMAGE_PATH = FileUtils.getFilterImageDir() + "chongbi_filter_image.jpg";
    public static final String HOWGETALIPAYID = HTML_BASE+"aliid.htm";//如何获取支付宝id
    public static final String NOTIFYCLICK = HTML_BASE+"msg.html";
    public static final String USERXIEYI = HTML_BASE+"userpro.html";
    public static final String KEFUANDHELP = HTML_BASE+"help.html";
    public static final String WEBCHATTG = HTML_BASE+"service.html";//微信和tg按钮
    public static final String SESSIONID = "sessionid";
    public static final String USDTPRICE = "Usdt_Price";
    public static final String VERSION = "version";
    /**
     * android 8.0以上通知栏渠道ID
     */
    public static final String NOTIFICATION_CHANNEL_DOWNLOAD = "bingo_channel_02";//下载安装包进度通知栏 渠道ID


    public static final int REQUESTCODE_10 = 10;
    public static final int REQUESTCODE_11 = 11;
    public static final int REQUESTCODE_12 = 12;
    public static final int REQUESTCODE_13 = 13;
    public static final int REQUESTCODE_14 = 14;
    public static final int RECYCLEVIEW_TOTALCOUNT = 10;
}
