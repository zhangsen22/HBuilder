package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
import com.growalong.util.util.Md5Utils;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.presenter.EntrustSalePresenter;
import hbuilder.android.com.presenter.contract.EntrustSaleContract;
import hbuilder.android.com.presenter.modle.EntrustSaleModle;
import hbuilder.android.com.ui.activity.BalancePassWordActivity;
import hbuilder.android.com.ui.activity.PaySettingActivity;
import hbuilder.android.com.util.SharedPreferencesUtils;
import hbuilder.android.com.util.ToastUtil;

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

    private boolean isUseIvAlipay;
    private boolean isUseIvWebchat;
    private boolean isUseIvIdcards;
    private double minSellPrice;
    private double maxSellPrice;

    private EntrustSalePresenter entrustSalePresenter;
    private double hotNum;
    private BaseActivity mContext;

    public static EntrustSaleFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        EntrustSaleFragment fragment = new EntrustSaleFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (BaseActivity) activity;
    }

    @Override
    protected int getRootView() {
        return R.layout.entrust_sale_fragment;
    }

    @Override
    protected void initView(View root) {
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        //初始化presenter
        new EntrustSalePresenter(this, new EntrustSaleModle());
        UsdtPriceResponse usdtPriceResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.USDTPRICE), UsdtPriceResponse.class);
        if (usdtPriceResponse != null) {
            minSellPrice = usdtPriceResponse.getMinSellPrice();
            maxSellPrice = usdtPriceResponse.getMaxSellPrice();
            etBusinessPrice.setHint("交易价格请限于" + minSellPrice + " ~ " + maxSellPrice);
        }
        WalletResponse walletResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.WALLET_BALANCE), WalletResponse.class);
        if (walletResponse != null) {
            hotNum = walletResponse.getHotNum();
            tvUserPrice.setText(hotNum + "USDT");
        }
        boolean haveAliPayee = AccountManager.getInstance().isHaveAliPayee();
        if (haveAliPayee) {
            tvAddAlipay.setVisibility(View.GONE);
        } else {
            tvAddAlipay.setVisibility(View.VISIBLE);
        }

        boolean haveWechatPayee = AccountManager.getInstance().isHaveWechatPayee();
        if (haveWechatPayee) {
            tvAddWebchat.setVisibility(View.GONE);
        } else {
            tvAddWebchat.setVisibility(View.VISIBLE);
        }

        boolean haveBankPayee = AccountManager.getInstance().isHaveBankPayee();
        if (haveBankPayee) {
            tvAddIdcards.setVisibility(View.GONE);
        } else {
            tvAddIdcards.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_add_alipay, R.id.tv_add_webchat, R.id.tv_add_idcards, R.id.tv_forget_password, R.id.tv_sell_publish,R.id.iv_alipay, R.id.iv_webchat, R.id.iv_idcards,R.id.tv_sale_cankaojia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_alipay:
                PaySettingActivity.startThis(mContext,1);
                break;
            case R.id.tv_add_webchat:
                PaySettingActivity.startThis(mContext,2);
                break;
            case R.id.tv_add_idcards:
                PaySettingActivity.startThis(mContext,3);
                break;
            case R.id.iv_alipay:
                boolean haveAliPayee = AccountManager.getInstance().isHaveAliPayee();
                if (haveAliPayee) {
                    if(isUseIvAlipay){
                        ivAlipay.setImageResource(R.mipmap.o);
                    }else {
                        ivAlipay.setImageResource(R.mipmap.bb);
                    }
                    isUseIvAlipay = !isUseIvAlipay;
                }else {
                    ToastUtil.shortShow("请先添加支付宝收款信息");
                }
                break;
            case R.id.iv_webchat:
                boolean haveWechatPayee = AccountManager.getInstance().isHaveWechatPayee();
                if (haveWechatPayee) {
                    if(isUseIvWebchat){
                        ivWebchat.setImageResource(R.mipmap.o);
                    }else {
                        ivWebchat.setImageResource(R.mipmap.bb);
                    }
                    isUseIvWebchat = !isUseIvWebchat;
                }else {
                    ToastUtil.shortShow("请先添加微信收款信息");
                }
                break;
            case R.id.iv_idcards:
                boolean haveBankPayee = AccountManager.getInstance().isHaveBankPayee();
                if (haveBankPayee) {
                    if(isUseIvIdcards){
                        ivIdcards.setImageResource(R.mipmap.o);
                    }else {
                        ivIdcards.setImageResource(R.mipmap.bb);
                    }
                    isUseIvIdcards = !isUseIvIdcards;
                }else {
                    ToastUtil.shortShow("请先添加银行卡收款信息");
                }
                break;
            case R.id.tv_forget_password:
                BalancePassWordActivity.startThis(mContext);
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

                if(minSellPrice > 0 && maxSellPrice > 0 && minSellPrice <= maxSellPrice){
                    if(d_businessPrice < minSellPrice || d_businessPrice > maxSellPrice){
                        ToastUtil.shortShow("交易价格请限于" + minSellPrice + " - " + maxSellPrice + "之间");
                        return;
                    }
                }else {
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
                }else {
                    ToastUtil.shortShow("账户可用余额为零");
                    return;
                }

                if(!isUseIvAlipay && !isUseIvWebchat && !isUseIvIdcards){
                    ToastUtil.shortShow("请至少添加一种收款方式");
                    return;
                }

                String moneryPassword = etMoneryPassword.getText().toString().trim();
                if (TextUtils.isEmpty(moneryPassword)) {
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }

                long currentTime = System.currentTimeMillis();
                entrustSalePresenter.putUpSell(d_businessPrice,d_expectMinnum,d_expectMaxnum,isUseIvAlipay
                        ,isUseIvWebchat,isUseIvIdcards,Md5Utils.getMD5(moneryPassword+currentTime),currentTime);
                break;
            case R.id.tv_sale_cankaojia:
                etBusinessPrice.setText(maxSellPrice+"");
                break;
        }
    }

    @Override
    public void putUpSellSuccess(BaseBean baseBean) {

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
        GALogger.d(TAG,"onResume      ");
        super.onResume();
    }
}
