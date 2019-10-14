package bychain.android.com.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.DateUtil;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
import com.growalong.util.util.bean.MessageEvent;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import org.greenrobot.eventbus.EventBus;
import java.text.DecimalFormat;
import butterknife.BindView;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.MyApplication;
import bychain.android.com.R;
import bychain.android.com.modle.AliPayee;
import bychain.android.com.modle.BankPayee;
import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.BuyBusinessResponse;
import bychain.android.com.modle.WechatPayee;
import bychain.android.com.presenter.BusinessBuyDetailsPresenter;
import bychain.android.com.presenter.contract.BusinessBuyDetailsContract;
import bychain.android.com.ui.activity.BusinessBuyDetailsActivity;
import bychain.android.com.ui.widget.CenterErWeiMaPopupView;
import bychain.android.com.util.ToastUtil;

public class BusinessBuyDetailsFragment extends BaseFragment implements BusinessBuyDetailsContract.View {
    private static final String TAG = BusinessBuyDetailsFragment.class.getSimpleName();
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.tv_shoukuai_type_name)
    TextView tvShoukuaiTypeName;
    @BindView(R.id.tv_shoukuai_name)
    TextView tvShoukuaiName;
    @BindView(R.id.tv_shoukuai_account)
    TextView tvShoukuaiAccount;
    @BindView(R.id.tv_shoukuai_cankaoma)
    TextView tvShoukuaiCankaoma;
    @BindView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @BindView(R.id.tv_re_pay)
    TextView tvRePay;
    @BindView(R.id.iv_popview)
    ImageView ivPopview;
    @BindView(R.id.iv_copy)
    ImageView ivCopy;
    private BusinessBuyDetailsActivity businessBuyDetailsActivity;
    private BusinessBuyDetailsPresenter presenter;
    private BuyBusinessResponse buyBusinessResponse;
    private double price;
    private double num;
    private int type;
    private long createTime = 0;
    private CountDownTimer timer;

    public static BusinessBuyDetailsFragment newInstance(@Nullable BuyBusinessResponse buyBusinessResponse, double price, double num, int type) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("buyBusinessResponse", buyBusinessResponse);
        arguments.putDouble("price", price);
        arguments.putDouble("num", num);
        arguments.putInt("type", type);
        BusinessBuyDetailsFragment fragment = new BusinessBuyDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessBuyDetailsActivity = (BusinessBuyDetailsActivity) getActivity();
        buyBusinessResponse = getArguments().getParcelable("buyBusinessResponse");
        price = getArguments().getDouble("price");
        num = getArguments().getDouble("num");
        type = getArguments().getInt("type");
    }

    @Override
    protected int getRootView() {
        return R.layout.business_buy_details_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("购买" + MyApplication.appContext.getResources().getString(R.string.bco));
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        tvOrderCode.setText(buyBusinessResponse.getTradeId());
        tvPayPrice.setText(new DecimalFormat("0.00").format(price * num));
        createTime = buyBusinessResponse.getCurrentTime();
        String payee = GsonUtil.getInstance().objTojson(buyBusinessResponse.getPayee());
        if (!TextUtils.isEmpty(payee)) {
            if (type == 1) {
                tvShoukuaiTypeName.setText("支付宝");
                AliPayee aliPayee = GsonUtil.getInstance().getServerBean(payee, AliPayee.class);
                if (aliPayee != null) {
                    tvShoukuaiName.setText(aliPayee.getName());
                    tvShoukuaiAccount.setText(aliPayee.getAccount());
                }
            } else if (type == 2) {
                tvShoukuaiTypeName.setText("微信");
                WechatPayee wechatPayee = GsonUtil.getInstance().getServerBean(payee, WechatPayee.class);
                if (wechatPayee != null) {
                    tvShoukuaiName.setText(wechatPayee.getName());
                    tvShoukuaiAccount.setText(wechatPayee.getAccount());
                }
            } else if (type == 3) {
                ivPopview.setVisibility(View.GONE);
                BankPayee bankPayee = GsonUtil.getInstance().getServerBean(payee, BankPayee.class);
                if (bankPayee != null) {
                    tvShoukuaiTypeName.setText(bankPayee.getBankName());
                    tvShoukuaiName.setText(bankPayee.getName());
                    tvShoukuaiAccount.setText(bankPayee.getAccount());
                }
            }

            tvShoukuaiCankaoma.setText(buyBusinessResponse.getPayCode() + "");

            long currentTime = System.currentTimeMillis();
            if (currentTime >= createTime + 10 * 60 * 1000) {

            } else {
                timer = new CountDownTimer(createTime + 10 * 60 * 1000 - currentTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //如果是Fragment 就判断getActivity() 是否为NULL
                        //如果是Activity 就判断!activity.isFinishing() 是否为NULL
                        if (businessBuyDetailsActivity != null) {
                            int left = (int) ((millisUntilFinished - 1000) / 1000);
                            GALogger.d(TAG, "left       " + left);
                            if (left > 0) {
                                tvCountDown.setText(DateUtil.getCurrentDateString2(millisUntilFinished));
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        ToastUtil.shortShow("订单已超时");
                        businessBuyDetailsActivity.finish();
                    }
                };
                timer.start();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_cancel_order, R.id.tv_re_pay, R.id.iv_popview, R.id.iv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                initPopView();
                break;
            case R.id.tv_cancel_order:
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
                        }).asConfirm("你确定要取消订单吗?", "",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                presenter.ordercancel(buyBusinessResponse.getTradeId());
                            }
                        }, null, false)
                        .show();
                break;
            case R.id.tv_re_pay:
                presenter.manualPay(buyBusinessResponse.getTradeId());
                break;
            case R.id.iv_popview:
                new XPopup.Builder(getContext())
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .asCustom(new CenterErWeiMaPopupView(getContext(), type, GsonUtil.getInstance().objTojson(buyBusinessResponse.getPayee())))
                        .show();
                break;
            case R.id.iv_copy:
                String trim = tvShoukuaiAccount.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    return;
                }
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) businessBuyDetailsActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", trim);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtil.shortShow("已复制到剪贴板");
                break;
        }
    }

    private void initPopView() {
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
                }).asConfirm("你确定要退出购买" + MyApplication.appContext.getResources().getString(R.string.bco) + "吗?", "",
                "取消", "确定",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        businessBuyDetailsActivity.finish();
                    }
                }, null, false)
                .show();
    }

    @Override
    public void ordercancelSuccess(BaseBean baseBean) {
        businessBuyDetailsActivity.finish();
    }

    @Override
    public void manualPaySuccess(BaseBean baseBean) {
        businessBuyDetailsActivity.finish();
    }

    @Override
    public void goOrderMySellComplete() {
        EventBus.getDefault().post(new MessageEvent(3));
        businessBuyDetailsActivity.finish();
    }

    @Override
    public void setPresenter(BusinessBuyDetailsContract.Presenter presenter) {
        this.presenter = (BusinessBuyDetailsPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    public void onKeyDownF() {
        initPopView();
    }

    @Override
    public void onDestroyView() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroyView();
    }
}
