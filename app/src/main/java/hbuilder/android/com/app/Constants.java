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
    public static final String SESSIONID = "sessionid";
    public static final String WALLET_BALANCE = "wallet_balance";
    public static final String USDTPRICE = "Usdt_Price";

    public static final String bankName = "BANKNAME";//银行卡真实姓名
    public static final String bankAccount = "BANKACCOUNT";//银行卡号

    public static final String aliPayName = "ALIPAYNAME";//支付宝名
    public static final String aliPayAccount = "ALIPAYACCOUNT";//支付宝账号
    public static final String aliPayBase64Img = "ALIPAYBASE64IMG";//支付宝二维码收款码

    public static final String webChatName = "WEBCHATNAME";//微信名
    public static final String webChatAccount = "WEBCHATACCOUNT";//微信号
    public static final String webChatBase64Img = "WEBCHATBASE64IMG";//微信二维码收款码
}
