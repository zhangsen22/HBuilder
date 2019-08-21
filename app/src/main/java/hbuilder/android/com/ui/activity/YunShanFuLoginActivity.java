package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.util.CommonFunction;

/**
 * 所有WebView的显示 (可用于显示点击底部广告Banner条打开的网页)
 */

public class YunShanFuLoginActivity extends BaseActivity {
    public static final String TAG = "WebViewActivity";
    public static final String WEB_VIEW_URL = "url";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    private String mUrl;

    private static String cookieRes="";//Cookie内容

    public static void launchVerifyCodeForResult(BaseActivity baseActivity, String url,int requestCode) {
        Intent i = new Intent(baseActivity, YunShanFuLoginActivity.class);
        i.putExtra(WEB_VIEW_URL, url);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        baseActivity.startActivityForResult(i,requestCode);
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop(flTitleComtent);
        mUrl = getIntent().getStringExtra(WEB_VIEW_URL);
        GALogger.d(TAG, "mUrl == " + mUrl);
        if (CommonFunction.isEmptyOrNullStr(mUrl)) {
            finish();
        }
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView,true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDatabaseEnabled(false);
        mWebView.getSettings().setDomStorageEnabled(false);
        mWebView.getSettings().setGeolocationEnabled(false);
        //    mWebView.getSettings().setPluginsEnabled(false);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setJavaScriptEnabled(true); ///------- 设置javascript 可用
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("TAG", "shouldOverrideUrlLoading() into, url=" + url);
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                // 返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                // 返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if(url.contains(Constants.YUNSHANFULOGINSUCCESS)){
                    /**
                     * 云闪付登陆成功
                     */
                    if(!getCookie().equals("")){
                        Intent intent = new Intent();
                        intent.putExtra("cookieRes",cookieRes);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
                return false;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String s = request.getUrl().toString();
                Log.d("TAG", "shouldOverrideUrlLoading()  edd into, url=" + s);
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                // 返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if(s.contains(Constants.YUNSHANFULOGINSUCCESS)){
                    /**
                     * 云闪付登陆成功
                     */
                    if(!getCookie().equals("")){
                        Intent intent = new Intent();
                        intent.putExtra("cookieRes",cookieRes);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            CookieManager.getInstance().removeSessionCookie();// 移除
            CookieManager.getInstance().removeAllCookie();// 移除
            mWebView.stopLoading();
            mWebView.setWebViewClient(null);
            mWebView.removeJavascriptInterface("callMethod");
            mWebView = null;
        } catch (Exception e) {
            GALogger.d(TAG, "onDestroy() error happen");
            e.printStackTrace();
        }
    }

    private static String getCookie(){
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieStr  = cookieManager.getCookie("https://ctq.95516.com/s/user/getCardList");
        int cookieUserPos=cookieStr.indexOf("cookieUser");
        int usernamePos=cookieStr.indexOf("username");
        if(cookieUserPos>=0&&usernamePos>=0){
            int cookieEnd=cookieStr.indexOf(";",cookieUserPos);
            int nameEnd=cookieStr.indexOf(";",usernamePos);
            if(cookieEnd==-1)
                cookieEnd=cookieStr.length();
            if(nameEnd==-1)
                nameEnd=cookieStr.length();
            //res=cookieStr.substring(cookieUserPos,47+cookieUserPos)+"&"+cookieStr.substring(usernamePos,usernamePos+20);
            cookieRes=cookieStr.substring(cookieUserPos,cookieEnd)+"&"+cookieStr.substring(usernamePos,nameEnd);
        }
        return cookieRes;
    }
}