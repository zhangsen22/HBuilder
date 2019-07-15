package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.ui.activity.HelpAndKefuActivity;
import hbuilder.android.com.ui.activity.WebViewActivity;

public class HelpAndKefuFragment extends BaseFragment {
    private static final String TAG = HelpAndKefuFragment.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.ll_kefu_webchat)
    LinearLayout llKefuWebchat;
    @BindView(R.id.ll_kefu_tb)
    LinearLayout llKefuTb;
    @BindView(R.id.ll_jiaocheng)
    LinearLayout llJiaocheng;
    @BindView(R.id.ll_alpayid)
    LinearLayout llAlpayid;
    @BindView(R.id.ll_alishoukuanma)
    LinearLayout llAlishoukuanma;
    @BindView(R.id.ll_alipayedu)
    LinearLayout llAlipayedu;
    private HelpAndKefuActivity helpAndKefuActivity;

    public static HelpAndKefuFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        HelpAndKefuFragment fragment = new HelpAndKefuFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpAndKefuActivity = (HelpAndKefuActivity) getActivity();
    }


    @Override
    protected int getRootView() {
        return R.layout.help_and_kefu_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("帮助与客服");
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
    }

    @OnClick({R.id.ll_kefu_webchat, R.id.ll_kefu_tb, R.id.iv_back,R.id.ll_jiaocheng, R.id.ll_alpayid, R.id.ll_alishoukuanma, R.id.ll_alipayedu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                helpAndKefuActivity.finish();
                break;
            case R.id.ll_kefu_webchat:
                break;
            case R.id.ll_kefu_tb:
                break;
            case R.id.ll_jiaocheng:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, Constants.JIAOCHENGGONGLUO, true);
                break;
            case R.id.ll_alpayid:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, Constants.HOWGETALIPAYID, true);
                break;
            case R.id.ll_alishoukuanma:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, Constants.KAITONGALIPAYSHANGJIAMA, true);
                break;
            case R.id.ll_alipayedu:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, Constants.ALIPAYEDU, true);
                break;
        }
    }
}
