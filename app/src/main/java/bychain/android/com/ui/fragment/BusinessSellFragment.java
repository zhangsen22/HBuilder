package bychain.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.growalong.util.util.Md5Utils;
import com.growalong.util.util.TextWatcherUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.MyApplication;
import bychain.android.com.R;
import bychain.android.com.app.AccountManager;
import bychain.android.com.modle.BuyItem;
import bychain.android.com.modle.SellResponse;
import bychain.android.com.modle.WalletResponse;
import bychain.android.com.presenter.BusinessSellPresenter;
import bychain.android.com.presenter.contract.BusinessSellContract;
import bychain.android.com.ui.activity.AliPayListActivity;
import bychain.android.com.ui.activity.BalancePassWordActivity;
import bychain.android.com.ui.activity.BusinessSellActivity;
import bychain.android.com.ui.activity.BusinessSellDetailsActivity;
import bychain.android.com.ui.activity.IdCastPayListActivity;
import bychain.android.com.ui.activity.WebChatListActivity;
import bychain.android.com.util.ToastUtil;

public class BusinessSellFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, BusinessSellContract.View {
    private static final String TAG = BusinessSellFragment.class.getSimpleName();
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_business_sell_name)
    TextView tvBusinessSellName;
    @BindView(R.id.tv_business_sell_price)
    TextView tvBusinessSellPrice;
    @BindView(R.id.tv_business_sell_more)
    TextView tvBusinessSellMore;
    @BindView(R.id.tv_business_sell_num)
    TextView tvBusinessSellNum;
    @BindView(R.id.et_business_sell_num)
    EditText etBusinessSellNum;
    @BindView(R.id.tv_num_all)
    TextView tvNumAll;
    @BindView(R.id.et_business_sell_price)
    EditText etBusinessSellPrice;
    @BindView(R.id.tv_price_all)
    TextView tvPriceAll;
    @BindView(R.id.cb_alipay)
    CheckBox cbAlipay;
    @BindView(R.id.cb_webchat)
    CheckBox cbWebchat;
    @BindView(R.id.cb_idcast)
    CheckBox cbIdcast;
    @BindView(R.id.et_forget_password)
    EditText etForgetPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_add_alipay)
    ImageView tvAddAlipay;
    @BindView(R.id.tv_add_webchat)
    ImageView tvAddWebchat;
    @BindView(R.id.tv_add_idcards)
    ImageView tvAddIdcards;
    @BindView(R.id.tv_business_sell_more1)
    TextView tvBusinessSellMore1;
    private double hotNum;
    private BusinessSellActivity businessSellActivity;
    private BusinessSellPresenter presenter;
    private BuyItem buyItem;
    private boolean flag = true;//添加标志位，标志是否被编辑
    private int type = 0;//1为支付宝，2为微信，3为银行账户

    public static BusinessSellFragment newInstance(@Nullable BuyItem buyItem) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("buyItem", buyItem);
        BusinessSellFragment fragment = new BusinessSellFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessSellActivity = (BusinessSellActivity) getActivity();
        buyItem = getArguments().getParcelable("buyItem");
    }

    @Override
    protected int getRootView() {
        return R.layout.business_sell_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("售出" + MyApplication.appContext.getResources().getString(R.string.bco));
        etBusinessSellNum.addTextChangedListener(new TextWatcherUtils() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!TextUtils.isEmpty(s.toString())) {
                    if (flag) {
                        flag = false;
                        double num = Double.parseDouble(s.toString());
                        if (num > hotNum) {
                            ToastUtil.shortShow("超出了交易账户可用数量");
                            return;
                        }
                        if (num > buyItem.getMaxNum()) {
                            ToastUtil.shortShow("超出了最大购买数量");
                            return;
                        }
                        etBusinessSellPrice.setText((num * buyItem.getPrice()) + "");
                    } else {
                        flag = true;
                    }
                }
            }
        });

        etBusinessSellPrice.addTextChangedListener(new TextWatcherUtils() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!TextUtils.isEmpty(s.toString())) {
                    if (flag) {
                        flag = false;
                        double price = Double.parseDouble(s.toString());
                        if (price > hotNum * buyItem.getPrice() || price > buyItem.getMaxNum() * buyItem.getPrice()) {
                            ToastUtil.shortShow("交易账户可用余额不足");
                            return;
                        }
                        etBusinessSellNum.setText((price / buyItem.getPrice()) + "");
                    } else {
                        flag = true;
                    }
                }
            }
        });
        cbAlipay.setOnCheckedChangeListener(this);
        cbWebchat.setOnCheckedChangeListener(this);
        cbIdcast.setOnCheckedChangeListener(this);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        presenter.getInfo();
        tvBusinessSellName.setText(buyItem.getNickname());
        tvBusinessSellPrice.setText(buyItem.getPrice() + "");
        tvBusinessSellMore.setText(new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMinNum()) + " - " + new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMaxNum()));
        tvBusinessSellMore1.setText(new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMinNum()) + " - " + new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMaxNum()));
        etBusinessSellPrice.setHintTextColor(MyApplication.appContext.getResources().getColor(R.color.color_999999));
        etBusinessSellPrice.setHint(new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMinNum()) + " - " + new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMaxNum()));
        tvBusinessSellNum.setText(buyItem.getMinNum() + " - " + buyItem.getMaxNum());
    }

    @OnClick({R.id.iv_back, R.id.tv_num_all, R.id.tv_price_all, R.id.tv_forget_password, R.id.tv_order, R.id.tv_add_alipay, R.id.tv_add_webchat, R.id.tv_add_idcards})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                businessSellActivity.finish();
                break;
            case R.id.tv_num_all:
            case R.id.tv_price_all:
                if (hotNum > buyItem.getMaxNum()) {
                    etBusinessSellNum.setText(buyItem.getMaxNum() + "");
                    etBusinessSellPrice.setText(new DecimalFormat("0.00").format(buyItem.getMaxNum() * buyItem.getPrice()));
                } else {
                    etBusinessSellNum.setText(hotNum + "");
                    etBusinessSellPrice.setText(new DecimalFormat("0.00").format(hotNum * buyItem.getPrice()));
                }
                break;
            case R.id.tv_forget_password:
                BalancePassWordActivity.startThis(businessSellActivity);
                break;
            case R.id.tv_order:
                String businessBuyNum = etBusinessSellNum.getText().toString().trim();
                if (TextUtils.isEmpty(businessBuyNum)) {
                    ToastUtil.shortShow("数量为空");
                    return;
                }
                double d_businessBuyNum = Double.parseDouble(businessBuyNum);
                if (d_businessBuyNum <= 0) {
                    ToastUtil.shortShow("数量不能为零");
                    return;
                }
                String businessBuyMoney = etBusinessSellPrice.getText().toString().trim();
                if (TextUtils.isEmpty(businessBuyMoney)) {
                    ToastUtil.shortShow("金额为空");
                    return;
                }
                double d_businessBuyMoney = Double.parseDouble(businessBuyMoney);
                if (d_businessBuyMoney <= 0) {
                    ToastUtil.shortShow("金额不能为零");
                    return;
                }

                if (type == 0) {
                    ToastUtil.shortShow("请选择一种支付方式");
                    return;
                }
                String businessPassword = etForgetPassword.getText().toString().trim();
                if (TextUtils.isEmpty(businessPassword)) {
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }
                long currentTime = System.currentTimeMillis();
                presenter.sell(buyItem.getId(), d_businessBuyNum, type, Md5Utils.getMD5(businessPassword + currentTime), currentTime);
                break;
            case R.id.tv_add_alipay:
                AliPayListActivity.startThis(businessSellActivity);
                break;
            case R.id.tv_add_webchat:
                WebChatListActivity.startThis(businessSellActivity);
                break;
            case R.id.tv_add_idcards:
                IdCastPayListActivity.startThis(businessSellActivity);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_alipay:
                boolean haveAliPayee = AccountManager.getInstance().isHaveAliPayee();
                if (haveAliPayee) {
                    if (isChecked) {
                        cbWebchat.setChecked(false);
                        cbIdcast.setChecked(false);
                        type = 1;
                    } else {
                        type = 0;
                    }
                } else {
                    cbAlipay.setChecked(false);
                    ToastUtil.shortShow("请先添加支付宝收款信息");
                }
                break;
            case R.id.cb_webchat:
                boolean haveWechatPayee = AccountManager.getInstance().isHaveWechatPayee();
                if (haveWechatPayee) {
                    if (isChecked) {
                        cbAlipay.setChecked(false);
                        cbIdcast.setChecked(false);
                        type = 2;
                    } else {
                        type = 0;
                    }
                } else {
                    cbWebchat.setChecked(false);
                    ToastUtil.shortShow("请先添加微信收款信息");
                }
                break;
            case R.id.cb_idcast:
                boolean haveBankPayee = AccountManager.getInstance().isHaveBankPayee();
                if (haveBankPayee) {
                    if (isChecked) {
                        cbAlipay.setChecked(false);
                        cbWebchat.setChecked(false);
                        type = 3;
                    } else {
                        type = 0;
                    }
                } else {
                    cbIdcast.setChecked(false);
                    ToastUtil.shortShow("请先添加银行卡收款信息");
                }
                break;

        }
    }

    @Override
    public void sellSuccess(SellResponse sellResponse) {
        if (sellResponse != null) {
            sellResponse.setCreatTime(System.currentTimeMillis());
            BusinessSellDetailsActivity.startThis(businessSellActivity, sellResponse, buyItem.getPrice(), Double.parseDouble(etBusinessSellNum.getText().toString().trim()), buyItem.getNickname());
            businessSellActivity.finish();
        }
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if (walletResponse != null) {
            this.hotNum = walletResponse.getHotNum();
            etBusinessSellNum.setHint("可用" + hotNum + MyApplication.appContext.getResources().getString(R.string.bco));
            etBusinessSellNum.setHintTextColor(MyApplication.appContext.getResources().getColor(R.color.color_999999));
        }
    }

    @Override
    public void setPresenter(BusinessSellContract.Presenter presenter) {
        this.presenter = (BusinessSellPresenter) presenter;
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
