package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.activity.BalancePassWordActivity;
import hbuilder.android.com.ui.activity.PaySettingActivity;
import hbuilder.android.com.util.ToastUtil;

public class YunShanFunEditFragment extends BaseFragment {
    private static final String TAG = YunShanFunEditFragment.class.getSimpleName();
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.iv_webchat_image)
    ImageView ivWebchatImage;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private PaySettingActivity paySettingActivity;

    public static YunShanFunEditFragment newInstance(String task) {
        Bundle arguments = new Bundle();
        YunShanFunEditFragment fragment = new YunShanFunEditFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paySettingActivity = (PaySettingActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.yunshanfu_edit_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("云闪付设置");
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
    }

    @OnClick({R.id.iv_back, R.id.tv_forget_password,R.id.iv_webchat_image, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                paySettingActivity.finish();
                break;
            case R.id.tv_forget_password:
                BalancePassWordActivity.startThis(paySettingActivity);
                break;
            case R.id.iv_webchat_image:
                ToastUtil.longShow("请先联系客服获取大商户权限再进行添加");
                break;
            case R.id.tv_submit:
                ToastUtil.longShow("请先联系客服获取大商户权限再进行添加");
                break;
        }
    }
}
