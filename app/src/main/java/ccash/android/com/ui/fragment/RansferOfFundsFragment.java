package ccash.android.com.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growalong.util.util.GsonUtil;
import com.growalong.util.util.Md5Utils;
import com.growalong.util.util.TextWatcherUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.app.Constants;
import ccash.android.com.modle.UsdtPriceResponse;
import ccash.android.com.modle.WalletResponse;
import ccash.android.com.presenter.RansferOfFundsPresenter;
import ccash.android.com.presenter.contract.RansferOfFundsContract;
import ccash.android.com.ui.activity.RansferOfFundsActivity;
import ccash.android.com.util.SharedPreferencesUtils;
import ccash.android.com.util.ToastUtil;

/**
 * 1  form  币币账户
 * 2  from  场外账户
 */

public class RansferOfFundsFragment extends BaseFragment implements RansferOfFundsContract.View {
    @BindView(R.id.fl_title_comtent)
    LinearLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_huazhuan_num)
    EditText etHuazhuanNum;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.et_huazhuan_password)
    EditText etHuazhuanPassword;
    @BindView(R.id.et_huazhuan_more)
    TextView etHuazhuanMore;
    @BindView(R.id.tv_publish_zhuan)
    TextView tvPublishZhuan;
    @BindView(R.id.tv_deteils)
    TextView tvDeteils;
    @BindView(R.id.iv_total)
    TextView ivTotal;
    @BindView(R.id.iv_used)
    TextView ivUsed;
    @BindView(R.id.iv_freed)
    TextView ivFreed;
    private RansferOfFundsActivity ransferOfFundsActivity;
    private int fromType;
    private double walletNum;
    private double walletFreezeNum;
    private double hotNum;
    private double hotFreezeNum;
    private RansferOfFundsPresenter presenter;
    private UsdtPriceResponse usdtPriceResponse;

    public static RansferOfFundsFragment newInstance(@Nullable int fromType) {
        Bundle arguments = new Bundle();
        arguments.putInt("fromType", fromType);
        RansferOfFundsFragment fragment = new RansferOfFundsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ransferOfFundsActivity = (RansferOfFundsActivity) getActivity();
        fromType = getArguments().getInt("fromType");
        usdtPriceResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.USDTPRICE), UsdtPriceResponse.class);
    }

    @Override
    protected int getRootView() {
        return R.layout.ransfer_offunds_ragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("资金划转");
        etHuazhuanNum.addTextChangedListener(new TextWatcherUtils() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!TextUtils.isEmpty(s.toString())) {
                    double num = Double.parseDouble(s.toString());
                    if (num <= 0) {
                        ToastUtil.shortShow("划转数量不能小于0");
                        return;
                    }
                    if (fromType == 1) {
                        if (num > walletNum) {
                            ToastUtil.shortShow("超出了最大划转数量");
                            if (usdtPriceResponse != null) {
                                etHuazhuanMore.setText(new DecimalFormat("0.00").format(walletNum) + MyApplication.appContext.getResources().getString(R.string.usdt) + " ≈ " + new DecimalFormat("0.00").format(walletNum * usdtPriceResponse.getMinSellUsdtPrice() / usdtPriceResponse.getApiConvertLowerAmount()) + MyApplication.appContext.getResources().getString(R.string.bco));
                            }
                            return;
                        }

                        if (usdtPriceResponse != null) {
                            etHuazhuanMore.setText(new DecimalFormat("0.00").format(num) + MyApplication.appContext.getResources().getString(R.string.usdt) + " ≈ " + new DecimalFormat("0.00").format(num * usdtPriceResponse.getMinSellUsdtPrice() / usdtPriceResponse.getApiConvertLowerAmount()) + MyApplication.appContext.getResources().getString(R.string.bco));
                        }
                    } else if (fromType == 2) {
                        if (num > hotNum) {
                            ToastUtil.shortShow("超出了最大划转数量");
                            if (usdtPriceResponse != null) {
                                etHuazhuanMore.setText(new DecimalFormat("0.00").format(hotNum) + MyApplication.appContext.getResources().getString(R.string.bco) + " ≈ " + new DecimalFormat("0.00").format(hotNum / usdtPriceResponse.getMinSellUsdtPrice() * usdtPriceResponse.getApiConvertLowerAmount()) + MyApplication.appContext.getResources().getString(R.string.usdt));
                            }
                            return;
                        }

                        if (usdtPriceResponse != null) {
                            etHuazhuanMore.setText(new DecimalFormat("0.00").format(num) + MyApplication.appContext.getResources().getString(R.string.bco) + " ≈ " + new DecimalFormat("0.00").format(num / usdtPriceResponse.getMinSellUsdtPrice() * usdtPriceResponse.getApiConvertLowerAmount()) + MyApplication.appContext.getResources().getString(R.string.usdt));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        presenter.getInfo();
    }

    @OnClick({R.id.iv_back, R.id.tv_all, R.id.tv_publish_zhuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ransferOfFundsActivity.finish();
                break;
            case R.id.tv_all:
                if (fromType == 1) {
                    etHuazhuanNum.setText(new DecimalFormat("0.000000").format(walletNum));
                } else if (fromType == 2) {
                    etHuazhuanNum.setText(new DecimalFormat("0.000000").format(hotNum));
                }
                break;
            case R.id.tv_publish_zhuan:
                String huazhuanNum = etHuazhuanNum.getText().toString().trim();
                if (TextUtils.isEmpty(huazhuanNum)) {
                    ToastUtil.shortShow("请输入划转数量");
                    return;
                }
                double d_huazhuanNum = Double.parseDouble(huazhuanNum);
                if (d_huazhuanNum <= 0) {
                    if (TextUtils.isEmpty(huazhuanNum)) {
                        ToastUtil.shortShow("请输入划转数量");
                        return;
                    }
                }

                if (fromType == 1) {
                    if (d_huazhuanNum > walletNum) {
                        ToastUtil.shortShow("超出了最大划转数量");
                        return;
                    }
                } else if (fromType == 2) {
                    if (d_huazhuanNum > hotNum) {
                        ToastUtil.shortShow("超出了最大划转数量");
                        return;
                    }
                }

                String huazhuanPassword = etHuazhuanPassword.getText().toString().trim();
                if (TextUtils.isEmpty(huazhuanPassword)) {
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }
                long currentTime = System.currentTimeMillis();
                presenter.transfer(fromType, d_huazhuanNum, Md5Utils.getMD5(huazhuanPassword + currentTime), currentTime);
                break;
        }
    }

    @Override
    public void transferSuccess(WalletResponse walletResponse) {
        ransferOfFundsActivity.setResult(Activity.RESULT_OK);
        ransferOfFundsActivity.finish();
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if (walletResponse != null) {
            walletNum = walletResponse.getWalletNum();
            walletFreezeNum = walletResponse.getWalletFreezeNum();
            hotNum = walletResponse.getHotNum();
            hotFreezeNum = walletResponse.getHotFreezeNum();
        }
        if (fromType == 1) {
            tvLeft.setText("我的钱包" + "  (" + MyApplication.appContext.getResources().getString(R.string.usdt) + ")");
            tvRight.setText("交易账户" + "  (" + MyApplication.appContext.getResources().getString(R.string.bco) + ")");
            tvDeteils.setText(MyApplication.appContext.getResources().getString(R.string.text11));
            ivTotal.setText((walletNum+walletFreezeNum)+"");
            ivUsed.setText(walletNum+"");
            ivFreed.setText(walletFreezeNum+"");
            if (usdtPriceResponse != null) {
                etHuazhuanMore.setText(new DecimalFormat("0.00").format(walletNum) + MyApplication.appContext.getResources().getString(R.string.usdt) + " ≈ " + new DecimalFormat("0.00").format(walletNum * usdtPriceResponse.getMinSellUsdtPrice() / usdtPriceResponse.getApiConvertLowerAmount()) + MyApplication.appContext.getResources().getString(R.string.bco));
            }
        } else if (fromType == 2) {
            tvLeft.setText("交易账户" + "  (" + MyApplication.appContext.getResources().getString(R.string.bco) + ")");
            tvRight.setText("我的钱包" + "  (" + MyApplication.appContext.getResources().getString(R.string.usdt) + ")");
            tvDeteils.setText(MyApplication.appContext.getResources().getString(R.string.text12));
            ivTotal.setText((hotNum+hotFreezeNum)+"");
            ivUsed.setText(hotNum+"");
            ivFreed.setText(hotFreezeNum+"");
            if (usdtPriceResponse != null) {
                etHuazhuanMore.setText(new DecimalFormat("0.00").format(hotNum) + MyApplication.appContext.getResources().getString(R.string.bco) + " ≈ " + new DecimalFormat("0.00").format(hotNum / usdtPriceResponse.getMinSellUsdtPrice() * usdtPriceResponse.getApiConvertLowerAmount()) + MyApplication.appContext.getResources().getString(R.string.usdt));
            }
        }
    }

    @Override
    public void setPresenter(RansferOfFundsContract.Presenter presenter) {
        this.presenter = (RansferOfFundsPresenter) presenter;
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
