package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
import com.growalong.util.util.Md5Utils;
import com.growalong.util.util.bean.MessageEvent;
import org.greenrobot.eventbus.EventBus;
import java.text.DecimalFormat;
import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.app.AccountManager;
import ccash.android.com.app.Constants;
import ccash.android.com.modle.BaseBean;
import ccash.android.com.modle.UsdtPriceResponse;
import ccash.android.com.modle.WalletResponse;
import ccash.android.com.presenter.EntrustSalePresenter;
import ccash.android.com.presenter.contract.EntrustSaleContract;
import ccash.android.com.presenter.modle.EntrustSaleModle;
import ccash.android.com.ui.activity.AliPayListActivity;
import ccash.android.com.ui.activity.BalancePassWordActivity;
import ccash.android.com.ui.activity.IdCastPayListActivity;
import ccash.android.com.ui.activity.LaCaraListActivity;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.WebChatListActivity;
import ccash.android.com.ui.activity.YunShanFuListActivity;
import ccash.android.com.util.SharedPreferencesUtils;
import ccash.android.com.util.ToastUtil;

public class EntrustSaleFragment extends BaseFragment implements EntrustSaleContract.View {
    private static final String TAG = EntrustSaleFragment.class.getSimpleName();
    @BindView(R.id.et_business_price)
    EditText etBusinessPrice;
    @BindView(R.id.et_expect_minnum)
    EditText etExpectMinnum;
    @BindView(R.id.et_expect_maxnum)
    EditText etExpectMaxnum;
    @BindView(R.id.tv_user_price)
    TextView tvUserPrice;
    @BindView(R.id.tv_add_alipay)
    TextView tvAddAlipay;
    @BindView(R.id.tv_add_webchat)
    TextView tvAddWebchat;
    @BindView(R.id.tv_add_idcards)
    TextView tvAddIdcards;
    @BindView(R.id.et_monery_password)
    EditText etMoneryPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_sell_publish)
    TextView tvSellPublish;
    @BindView(R.id.tv_sale_cankaojia)
    TextView tvSaleCanKaoJia;
    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.iv_webchat)
    ImageView ivWebchat;
    @BindView(R.id.iv_idcards)
    ImageView ivIdcards;
    @BindView(R.id.iv_yunshanfu)
    ImageView ivYunshanfu;
    @BindView(R.id.tv_add_yunshanfu)
    TextView tvAddYunshanfu;
    @BindView(R.id.iv_lacara)
    ImageView ivLacara;
    @BindView(R.id.tv_add_lacara)
    TextView tvAddLacara;
    private boolean isUseIvAlipay;
    private boolean isUseIvWebchat;
    private boolean isUseIvIdcards;
    private boolean isUseIvCloud;
    private boolean isUseIvLaCara;
    private MainActivity mainActivity;
    private EntrustSalePresenter entrustSalePresenter;
    private double hotNum = 0;
    private double minSellPrice;
    private double maxSellPrice;

    public static EntrustSaleFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        EntrustSaleFragment fragment = new EntrustSaleFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.entrust_sale_fragment;
    }

    @Override
    protected void initView(View root) {
        UsdtPriceResponse usdtPriceResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.USDTPRICE), UsdtPriceResponse.class);
        if (usdtPriceResponse != null) {
            minSellPrice = usdtPriceResponse.getMinSellPrice();
            maxSellPrice = usdtPriceResponse.getMaxSellPrice();
            etBusinessPrice.setHint("交易价格请限于" + new DecimalFormat("0.000").format(minSellPrice) + " ~ " + new DecimalFormat("0.000").format(maxSellPrice));
        }
        GALogger.d(TAG, "EntrustSaleFragment    is    initView");
        GALogger.d(TAG, "mEnableLazyLoad   " + mEnableLazyLoad + "   mIsCreateView   " + mIsCreateView + "  getUserVisibleHint()  " + getUserVisibleHint() + "   mIsLoadData   " + mIsLoadData);
        if (AccountManager.getInstance().isHaveAliPayee()) {
            tvAddAlipay.setText("更改");
        } else {
            tvAddAlipay.setText("添加");
        }
        if (AccountManager.getInstance().isHaveBankPayee()) {
            tvAddIdcards.setText("更改");
        } else {
            tvAddIdcards.setText("添加");
        }
        if (AccountManager.getInstance().isHaveWechatPayee()) {
            tvAddWebchat.setText("更改");
        } else {
            tvAddWebchat.setText("添加");
        }
        if (AccountManager.getInstance().isHaveCloudPayee()) {
            tvAddYunshanfu.setText("更改");
        } else {
            tvAddYunshanfu.setText("添加");
        }
        if (AccountManager.getInstance().isHaveLakalaPayee()) {
            tvAddLacara.setText("更改");
        } else {
            tvAddLacara.setText("添加");
        }
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        GALogger.d(TAG, "EntrustSaleFragment    is    lazyLoadData");
        GALogger.d(TAG, "mEnableLazyLoad   " + mEnableLazyLoad + "   mIsCreateView   " + mIsCreateView + "  getUserVisibleHint()  " + getUserVisibleHint() + "   mIsLoadData   " + mIsLoadData);
        //初始化presenter
        new EntrustSalePresenter(this, new EntrustSaleModle());
        entrustSalePresenter.getInfo();
    }

    @OnClick({R.id.tv_add_alipay, R.id.tv_add_webchat, R.id.tv_add_idcards, R.id.tv_forget_password, R.id.tv_sell_publish, R.id.iv_alipay, R.id.iv_webchat, R.id.iv_idcards, R.id.tv_sale_cankaojia, R.id.iv_yunshanfu, R.id.tv_add_yunshanfu,R.id.tv_add_lacara,R.id.iv_lacara})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_alipay:
                AliPayListActivity.startThis(mainActivity);
                break;
            case R.id.tv_add_webchat:
                WebChatListActivity.startThis(mainActivity);
                break;
            case R.id.tv_add_idcards:
                IdCastPayListActivity.startThis(mainActivity);
                break;
            case R.id.iv_alipay:
                boolean haveAliPayee = AccountManager.getInstance().isHaveAliPayee();
                if (haveAliPayee) {
                    if (isUseIvAlipay) {
                        ivAlipay.setImageResource(R.mipmap.cv);
                    } else {
                        ivAlipay.setImageResource(R.mipmap.cw);
                    }
                    isUseIvAlipay = !isUseIvAlipay;
                } else {
                    ToastUtil.shortShow("请先添加支付宝收款信息");
                }
                break;
            case R.id.iv_webchat:
                boolean haveWechatPayee = AccountManager.getInstance().isHaveWechatPayee();
                if (haveWechatPayee) {
                    if (isUseIvWebchat) {
                        ivWebchat.setImageResource(R.mipmap.cv);
                    } else {
                        ivWebchat.setImageResource(R.mipmap.cw);
                    }
                    isUseIvWebchat = !isUseIvWebchat;
                } else {
                    ToastUtil.shortShow("请先添加微信收款信息");
                }
                break;
            case R.id.iv_idcards:
                boolean haveBankPayee = AccountManager.getInstance().isHaveBankPayee();
                if (haveBankPayee) {
                    if (isUseIvIdcards) {
                        ivIdcards.setImageResource(R.mipmap.cv);
                    } else {
                        ivIdcards.setImageResource(R.mipmap.cw);
                    }
                    isUseIvIdcards = !isUseIvIdcards;
                } else {
                    ToastUtil.shortShow("请先添加银行卡收款信息");
                }
                break;
            case R.id.tv_forget_password:
                BalancePassWordActivity.startThis(mainActivity);
                break;
            case R.id.tv_sell_publish:
                String businessPrice = etBusinessPrice.getText().toString().trim();
                if (TextUtils.isEmpty(businessPrice)) {
                    ToastUtil.shortShow("请输入交易价格");
                    return;
                }
                double d_businessPrice = Double.parseDouble(businessPrice);
                if (d_businessPrice <= 0) {
                    ToastUtil.shortShow("交易价格不能小于零");
                    return;
                }

                if (minSellPrice > 0 && maxSellPrice > 0 && minSellPrice <= maxSellPrice) {
                    if (d_businessPrice < minSellPrice || d_businessPrice > maxSellPrice) {
                        ToastUtil.shortShow("交易价格请限于" + minSellPrice + " - " + maxSellPrice + "之间");
                        return;
                    }
                } else {
                    ToastUtil.shortShow("请获取交易价格区间");
                    return;
                }

                String expectMinnum = etExpectMinnum.getText().toString().trim();
                if (TextUtils.isEmpty(expectMinnum)) {
                    ToastUtil.shortShow("请输入您预想的最小售出数量");
                    return;
                }

                double d_expectMinnum = Double.parseDouble(expectMinnum);
                if (d_expectMinnum <= 0) {
                    ToastUtil.shortShow("请输入您预想的最小售出数量大于零");
                    return;
                }

                String expectMaxnum = etExpectMaxnum.getText().toString().trim();
                if (TextUtils.isEmpty(expectMaxnum)) {
                    ToastUtil.shortShow("请输入您预想的最大售出数量");
                    return;
                }
                double d_expectMaxnum = Double.parseDouble(expectMaxnum);
                if (d_expectMaxnum <= 0) {
                    ToastUtil.shortShow("请输入您预想的最大售出数量大于零");
                    return;
                }

                if (d_expectMaxnum < d_expectMinnum) {
                    ToastUtil.shortShow("您预想的最大售出数量不能小于您预想的最小售出数量");
                    return;
                }

                if (hotNum > 0) {
                    if (d_expectMaxnum > hotNum) {
                        ToastUtil.shortShow("请输入您预想的最大售出数量超出了账户可用余额");
                        return;
                    }
                } else {
                    ToastUtil.shortShow("账户可用余额为零");
                    return;
                }

                if (!isUseIvAlipay && !isUseIvWebchat && !isUseIvIdcards && !isUseIvCloud && !isUseIvLaCara) {
                    ToastUtil.shortShow("请至少添加一种收款方式");
                    return;
                }

                String moneryPassword = etMoneryPassword.getText().toString().trim();
                if (TextUtils.isEmpty(moneryPassword)) {
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }

                long currentTime = System.currentTimeMillis();
                entrustSalePresenter.putUpSell(d_businessPrice, d_expectMinnum, d_expectMaxnum, isUseIvAlipay
                        , isUseIvWebchat, isUseIvIdcards, isUseIvCloud,isUseIvLaCara, Md5Utils.getMD5(moneryPassword + currentTime), currentTime);
                break;
            case R.id.tv_sale_cankaojia:
                break;
            case R.id.iv_yunshanfu:
                boolean haveCloudPayee = AccountManager.getInstance().isHaveCloudPayee();
                if (haveCloudPayee) {
                    if (isUseIvCloud) {
                        ivYunshanfu.setImageResource(R.mipmap.cv);
                    } else {
                        ivYunshanfu.setImageResource(R.mipmap.cw);
                    }
                    isUseIvCloud = !isUseIvCloud;
                } else {
                    ToastUtil.shortShow("请先添加云闪付收款信息");
                }
                break;
            case R.id.iv_lacara:
                boolean haveLakalaPayee = AccountManager.getInstance().isHaveLakalaPayee();
                if (haveLakalaPayee) {
                    if (isUseIvLaCara) {
                        ivLacara.setImageResource(R.mipmap.cv);
                    } else {
                        ivLacara.setImageResource(R.mipmap.cw);
                    }
                    isUseIvLaCara = !isUseIvLaCara;
                } else {
                    ToastUtil.shortShow("请先添加拉卡拉收款信息");
                }
                break;
            case R.id.tv_add_yunshanfu:
                YunShanFuListActivity.startThis(mainActivity);
                break;
            case R.id.tv_add_lacara:
                LaCaraListActivity.startThis(mainActivity);
                break;
        }
    }

    @Override
    public void putUpSellSuccess(BaseBean baseBean) {
        EventBus.getDefault().post(new MessageEvent(1));
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if (walletResponse != null) {
            hotNum = walletResponse.getHotNum();
            GALogger.d(TAG, "hotNum    " + hotNum);
            tvUserPrice.setText(new DecimalFormat("0.000000").format(hotNum) + MyApplication.appContext.getResources().getString(R.string.cas));
        }
    }

    @Override
    public void setPresenter(EntrustSaleContract.Presenter presenter) {
        this.entrustSalePresenter = (EntrustSalePresenter) presenter;
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
    public void onResume() {
        GALogger.d(TAG, "onResume      ");
        super.onResume();
    }
}
