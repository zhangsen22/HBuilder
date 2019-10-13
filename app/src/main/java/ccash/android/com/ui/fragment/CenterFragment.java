package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.PackageUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.app.AccountManager;
import ccash.android.com.app.Constants;
import ccash.android.com.presenter.CenterPresenter;
import ccash.android.com.presenter.contract.CenterContract;
import ccash.android.com.presenter.modle.CenterModle;
import ccash.android.com.ui.activity.AddMakeStyleActivity;
import ccash.android.com.ui.activity.IdentityActivity;
import ccash.android.com.ui.activity.LoginAndRegistActivity;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.RecommendToFriendsActivity;
import ccash.android.com.ui.activity.SecurityCenterActivity;

public class CenterFragment extends BaseFragment implements CenterContract.View {
    private static final String TAG = CenterFragment.class.getSimpleName();
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_center_anquan)
    LinearLayout tvCenterAnquan;
    @BindView(R.id.ll_shenfencard)
    LinearLayout tvShenfencard;
    @BindView(R.id.ll_add_sk_type)
    LinearLayout llAddSkType;
    @BindView(R.id.ll_tj_friend)
    LinearLayout llTjFriend;
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.ll_center_bg)
    LinearLayout llCenterBg;
    @BindView(R.id.tv_shenfencard_status)
    TextView tvShenfencardStatus;
    private MainActivity mainActivity;
    private CenterPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    public static CenterFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        CenterFragment fragment = new CenterFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.center_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "CenterFragment   is    initView");
        setRootViewPaddingTop(llCenterBg);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        //初始化presenter
        new CenterPresenter(this, new CenterModle());
        initUserInfo();
    }

    @OnClick({R.id.ll_center_anquan, R.id.ll_shenfencard, R.id.ll_add_sk_type, R.id.ll_tj_friend, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_center_anquan:
                SecurityCenterActivity.startThis(mainActivity);
                break;
            case R.id.ll_shenfencard:
                int iDstatus = AccountManager.getInstance().getIDstatus();//0未验证，1等待人工审核 2 已验证 99 验证失败
                if (iDstatus == 0 || iDstatus == 99) {
                    IdentityActivity.startThis(mainActivity, Constants.REQUESTCODE_10);
                }
                break;
            case R.id.ll_add_sk_type:
                AddMakeStyleActivity.startThis(mainActivity);
                break;
            case R.id.ll_tj_friend:
                RecommendToFriendsActivity.startThis(mainActivity);
                break;
            case R.id.tv_logout:
                //带确认和取消按钮的弹窗
                new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
                        // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                        .setPopupCallback(new XPopupCallback() {
                            @Override
                            public void onShow() {
                                Log.e("tag", "onShow");
                            }

                            @Override
                            public void onDismiss() {
                                Log.e("tag", "onDismiss");
                            }
                        }).asConfirm("你确定要退出此账号吗?", "",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                LoginAndRegistActivity.startThis(mainActivity);
                                mainActivity.finish();
                            }
                        }, null, false)
                        .show();
                break;
        }
    }

    @Override
    public void changeNicknameSuccess(String nickname) {
        AccountManager.getInstance().setNickname(nickname);
        tvUsername.setText(AccountManager.getInstance().getNickname());
    }

    @Override
    public void setPresenter(CenterContract.Presenter presenter) {
        this.presenter = (CenterPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    public void onActivityResultCenter(int requestCode) {
        initUserInfo();
    }

    private void initUserInfo() {
        tvUsername.setText(AccountManager.getInstance().getNickname());
        tvAccount.setText(AccountManager.getInstance().getPhoneNumber());
        tvVersionCode.setText(PackageUtil.getAppVersionName(MyApplication.appContext));
        int iDstatus = AccountManager.getInstance().getIDstatus();//0未验证，1等待人工审核 2 已验证 99 验证失败
        if(iDstatus == 0){
            tvShenfencardStatus.setText("未验证");
        }else if(iDstatus == 1){
            tvShenfencardStatus.setText("等待人工审核");
        }else if(iDstatus == 2){
            tvShenfencardStatus.setText("已验证");
        }else if(iDstatus == 99){
            tvShenfencardStatus.setText("验证失败");
        }
    }
}
