package ccash.android.com.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.growalong.util.util.AppPublicUtils;
import com.growalong.util.util.Md5Utils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectWebChatListener;
import com.lxj.xpopup.interfaces.XPopupCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.R;
import ccash.android.com.modle.LaCaraWenChatListItem;
import ccash.android.com.modle.LaCaraWenChatListModle;
import ccash.android.com.presenter.IdCastPresenter;
import ccash.android.com.presenter.contract.IdCastContract;
import ccash.android.com.ui.activity.BalancePassWordActivity;
import ccash.android.com.ui.activity.PaySettingActivity;
import ccash.android.com.ui.widget.CustomPartShadowPopupView;
import ccash.android.com.util.ToastUtil;

public class IdCastPayEditFragment extends BaseFragment implements IdCastContract.View {
    private static final String TAG = IdCastPayEditFragment.class.getSimpleName();
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_yinhang_name)
    EditText etYinhangName;
    @BindView(R.id.et_yinhang_zhiname)
    EditText etYinhangZhiname;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_cast_code)
    EditText etCastCode;
    @BindView(R.id.et_everyday_jine)
    EditText etEverydayJine;
    @BindView(R.id.et_forget_password)
    EditText etForgetPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_wenchat_name)
    EditText etWenchatName;
    private PaySettingActivity paySettingActivity;
    private IdCastPresenter presenter;
    private long wechatPaymentId = 0;
    private BasePopupView show;
    private List<String> idCastCode;

    public static IdCastPayEditFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        IdCastPayEditFragment fragment = new IdCastPayEditFragment();
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
        return R.layout.id_cast_pay_edit_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        AppPublicUtils.setEditTextEnable(etWenchatName, false);
        tvTitle.setText("银行卡设置");
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        String[] orderItemTitle = paySettingActivity.getResources().getStringArray(R.array.id_idcast_begin);
        idCastCode = new ArrayList<>(Arrays.asList(orderItemTitle));
    }

    @OnClick({R.id.iv_back, R.id.tv_forget_password, R.id.tv_submit,R.id.et_wenchat_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                paySettingActivity.finish();
                break;
            case R.id.tv_forget_password:
                BalancePassWordActivity.startThis(paySettingActivity);
                break;
            case R.id.tv_submit:
                String wenchatName = etWenchatName.getText().toString().trim();
                if (TextUtils.isEmpty(wenchatName)) {
                    ToastUtil.shortShow("请选择该拉卡拉所绑定公众号的微信昵称");
                    return;
                }

                if (wechatPaymentId <= 0) {
                    ToastUtil.shortShow("请选择该拉卡拉所绑定公众号的微信昵称");
                    return;
                }
                String yinhangName = etYinhangName.getText().toString().trim();
                if (TextUtils.isEmpty(yinhangName)) {
                    ToastUtil.shortShow("请输入银行名称");
                    return;
                }

                String yinhangZhiname = etYinhangZhiname.getText().toString().trim();
                if (TextUtils.isEmpty(yinhangZhiname)) {
                    ToastUtil.shortShow("请输入所在支行");
                    return;
                }

                String name = etName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.shortShow("请输入真实姓名");
                    return;
                }

                String castCode = etCastCode.getText().toString().trim();
                if (TextUtils.isEmpty(castCode)) {
                    ToastUtil.shortShow("请输入银行卡号");
                    return;
                }

                if(castCode.length() < 6){
                    ToastUtil.shortShow("请输入正确的银行卡号");
                    return;
                }

                String everydayJine = etEverydayJine.getText().toString().trim();
                if (TextUtils.isEmpty(everydayJine)) {
                    ToastUtil.shortShow("请输入银行卡每日收款限额");
                    return;
                }

                String forgetPassword = etForgetPassword.getText().toString().trim();
                if (TextUtils.isEmpty(forgetPassword)) {
                    ToastUtil.shortShow("请输入平台资金密码");
                    return;
                }

                double dailyLimit = Double.parseDouble(everydayJine);
                if (dailyLimit <= 0) {
                    ToastUtil.shortShow("限额不能小于零");
                    return;
                }

                String substring = castCode.substring(0, 6);
                if(!idCastCode.contains(substring)){
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
                            }).asConfirm("请确保当前输入的是平安银行，建设银行或者广发银行的银行卡，否则无法收款。", "",
                            "更换卡号", "继续绑定",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    long currentTime = System.currentTimeMillis();
                                    presenter.bank(0,wechatPaymentId, yinhangName, yinhangZhiname, name, castCode, dailyLimit, Md5Utils.getMD5(forgetPassword + currentTime), currentTime);
                                }
                            }, null, false)
                            .show();
                }else {
                    long currentTime = System.currentTimeMillis();
                    presenter.bank(0,wechatPaymentId, yinhangName, yinhangZhiname, name, castCode, dailyLimit, Md5Utils.getMD5(forgetPassword + currentTime), currentTime);
                }
                break;
            case R.id.et_wenchat_name:
                presenter.getWechatList();
                break;
        }
    }

    @Override
    public void bankSuccess(String name, String account) {
        paySettingActivity.setResult(Activity.RESULT_OK);
        paySettingActivity.finish();
    }

    @Override
    public void getWechatListSuccess(LaCaraWenChatListModle laCaraWenChatListModle) {
        if (laCaraWenChatListModle != null) {
            List<LaCaraWenChatListItem> list = laCaraWenChatListModle.getList();
            if (list != null && list.size() > 0) {
                showPartShadow(list);
            }
        }
    }

    private void showPartShadow(List<LaCaraWenChatListItem> list) {
        if (show != null && show.isShow()) {
            return;
        }
        show = new XPopup.Builder(getContext())
                .atView(etWenchatName)
//                .dismissOnTouchOutside(false)
                .asCustom(new CustomPartShadowPopupView(getContext(), list, new OnSelectWebChatListener() {
                    @Override
                    public void onSelect(int position, long paymentId, String account) {
                        wechatPaymentId = paymentId;
                        etWenchatName.setText(account);
                    }
                },paySettingActivity)).show();
    }

    @Override
    public void setPresenter(IdCastContract.Presenter presenter) {
        this.presenter = (IdCastPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void onDestroyView() {
        if (show != null) {
            if (show.isShow()) {
                show.dismiss();
            }
            show = null;
        }
        super.onDestroyView();
    }
}
