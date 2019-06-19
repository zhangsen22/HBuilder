package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.activity.BalancePassWordActivity;
import hbuilder.android.com.ui.activity.ChangePwdActivity;
import hbuilder.android.com.ui.activity.SecurityCenterActivity;

public class SecurityCenterFragment extends BaseFragment {
    private static final String TAG = SecurityCenterFragment.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_change_password)
    LinearLayout llChangePassword;
    @BindView(R.id.ll_change_blanues_password)
    LinearLayout llChangeBlanuesPassword;
    private SecurityCenterActivity securityCenterActivity;

    public static SecurityCenterFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        SecurityCenterFragment fragment = new SecurityCenterFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        securityCenterActivity = (SecurityCenterActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.security_center_fragment;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        tvTitle.setText("安全中心");
    }

    @OnClick({R.id.iv_back, R.id.ll_change_password, R.id.ll_change_blanues_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                securityCenterActivity.finish();
                break;
            case R.id.ll_change_password:
                ChangePwdActivity.startThis(securityCenterActivity);
                break;
            case R.id.ll_change_blanues_password:
                BalancePassWordActivity.startThis(securityCenterActivity);
                break;
        }
    }
}
