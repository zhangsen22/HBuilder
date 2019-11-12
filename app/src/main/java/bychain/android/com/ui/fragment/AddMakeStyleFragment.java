package bychain.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import butterknife.BindView;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.R;
import bychain.android.com.app.AccountManager;
import bychain.android.com.presenter.CenterPresenter;
import bychain.android.com.presenter.contract.CenterContract;
import bychain.android.com.presenter.modle.CenterModle;
import bychain.android.com.ui.activity.AddMakeStyleActivity;
import bychain.android.com.ui.activity.AliPayListActivity;
import bychain.android.com.ui.activity.IdCastPayListActivity;
import bychain.android.com.ui.activity.WebChatListActivity;
import bychain.android.com.util.ToastUtil;

public class AddMakeStyleFragment extends BaseFragment implements CenterContract.View {
    private static final String TAG = AddMakeStyleFragment.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_alipay_click)
    LinearLayout llAlipayClick;
    @BindView(R.id.ll_ylcard_click)
    LinearLayout llYlcardClick;
    @BindView(R.id.ll_webchat_click)
    LinearLayout llWebchatClick;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nickname_click)
    LinearLayout llNicknameClick;
    private AddMakeStyleActivity addMakeStyleActivity;
    private CenterPresenter presenter;

    public static AddMakeStyleFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        AddMakeStyleFragment fragment = new AddMakeStyleFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMakeStyleActivity = (AddMakeStyleActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.add_make_style_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG, "lazyLoadData  ........");
        //初始化presenter
        new CenterPresenter(this, new CenterModle());
    }

    @OnClick({R.id.iv_back, R.id.ll_alipay_click, R.id.ll_ylcard_click, R.id.ll_webchat_click,R.id.ll_nickname_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                addMakeStyleActivity.finish();
                break;
            case R.id.ll_alipay_click:
                AliPayListActivity.startThis(addMakeStyleActivity);
                break;
            case R.id.ll_ylcard_click:
                if (!AccountManager.getInstance().isHaveWechatPayee()) {
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
                            }).asConfirm("请先绑定微信收款方式", "",
                            "取消", "确定",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    WebChatListActivity.startThis(addMakeStyleActivity);
                                }
                            }, null, false)
                            .show();
                } else {
                    IdCastPayListActivity.startThis(addMakeStyleActivity);
                }
                break;
            case R.id.ll_webchat_click:
                WebChatListActivity.startThis(addMakeStyleActivity);
                break;
            case R.id.ll_nickname_click:
                new XPopup.Builder(getContext())
                        .dismissOnBackPressed(false)
                        .dismissOnTouchOutside(false)
                        .autoOpenSoftInput(true)
//                        .moveUpToKeyboard(false) //是否移动到软键盘上面，默认为true
                        .asInputConfirm("请输入要修改的昵称", "", "昵称", false,
                                new OnInputConfirmListener() {
                                    @Override
                                    public void onConfirm(String text) {
                                        if (!TextUtils.isEmpty(text)) {
                                            presenter.changeNickname(text);
                                        } else {
                                            ToastUtil.shortShow("请输入要修改的昵称");
                                        }
                                    }
                                })
                        .show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tvTitle.setText("法币设置");
        tvNickname.setText(AccountManager.getInstance().getNickname());
        GALogger.d(TAG, "onResume  ........");
    }

    @Override
    public void changeNicknameSuccess(String nickname) {
        AccountManager.getInstance().setNickname(nickname);
        tvNickname.setText(AccountManager.getInstance().getNickname());
    }

    @Override
    public void setAliOpenFlagSuccess(int aliOpenFlag) {

    }

    @Override
    public void setAliOpenFlagError(int aliOpenFlag) {

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
}
