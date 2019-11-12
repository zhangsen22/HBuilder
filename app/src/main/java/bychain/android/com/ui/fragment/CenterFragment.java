package bychain.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.PackageUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import butterknife.BindView;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.MyApplication;
import bychain.android.com.R;
import bychain.android.com.app.AccountManager;
import bychain.android.com.app.Constants;
import bychain.android.com.modle.UserInfoResponse;
import bychain.android.com.presenter.CenterPresenter;
import bychain.android.com.presenter.contract.CenterContract;
import bychain.android.com.presenter.modle.CenterModle;
import bychain.android.com.ui.activity.AddMakeStyleActivity;
import bychain.android.com.ui.activity.IdentityActivity;
import bychain.android.com.ui.activity.LoginAndRegistActivity;
import bychain.android.com.ui.activity.MainActivity;
import bychain.android.com.ui.activity.RecommendToFriendsActivity;
import bychain.android.com.ui.activity.SecurityCenterActivity;
import bychain.android.com.ui.widget.AiPayCheckBoxPopupView;
import bychain.android.com.util.ToastUtil;

public class CenterFragment extends BaseFragment implements CenterContract.View{
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
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
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
        presenter.getUserInfo();
        initUserInfo();
    }

    @OnClick({R.id.ll_center_anquan, R.id.ll_shenfencard, R.id.ll_add_sk_type, R.id.ll_tj_friend, R.id.tv_logout,R.id.cb_check})
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
            case R.id.cb_check:
                //0 关闭 1打开
                if(cbCheck.isChecked()){
                    presenter.setAliOpenFlag(0);
                }else {
                    presenter.setAliOpenFlag(1);
                }
                break;
        }
    }

    @Override
    public void changeNicknameSuccess(String nickname) {
        AccountManager.getInstance().setNickname(nickname);
        tvUsername.setText(AccountManager.getInstance().getNickname());
    }

    @Override
    public void setAliOpenFlagSuccess(int aliOpenFlag) {
//0 关闭 1打开
        if(aliOpenFlag == 0){
            ToastUtil.shortShow("已关闭支付宝接单");
        }else if(aliOpenFlag == 1){
            new XPopup.Builder(getContext())
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .hasStatusBarShadow(true) //启用状态栏阴影
                    .asCustom(new AiPayCheckBoxPopupView(getContext(), new OnConfirmListener() {
                        @Override
                        public void onConfirm() {

                        }
                    })).show();
        }
    }

    @Override
    public void setAliOpenFlagError(int aliOpenFlag) {
//0 关闭 1打开
        if(aliOpenFlag == 0){
            cbCheck.setChecked(true);
        }else if(aliOpenFlag == 1){
            cbCheck.setChecked(false);
        }
    }

    @Override
    public void getUserInfoSuccess(UserInfoResponse userInfoResponse) {

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
        if (iDstatus == 0) {
            tvShenfencardStatus.setText("未验证");
        } else if (iDstatus == 1) {
            tvShenfencardStatus.setText("等待人工审核");
        } else if (iDstatus == 2) {
            tvShenfencardStatus.setText("已验证");
        } else if (iDstatus == 99) {
            tvShenfencardStatus.setText("验证失败");
        }
    }
}
