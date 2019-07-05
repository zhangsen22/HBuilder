package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.Md5Utils;
import com.growalong.util.util.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.presenter.EntrustBuyPresenter;
import hbuilder.android.com.presenter.contract.EntrustBuyContract;
import hbuilder.android.com.presenter.modle.EntrustBuyModle;
import hbuilder.android.com.ui.activity.BalancePassWordActivity;
import hbuilder.android.com.util.ToastUtil;

public class EntrustBuyFragment extends BaseFragment implements EntrustBuyContract.View {
    private static final String TAG = EntrustBuyFragment.class.getSimpleName();
    @BindView(R.id.et_business_buy_price)
    EditText etBusinessBuyPrice;
    @BindView(R.id.et_expect_buy_minnum)
    EditText etExpectBuyMinnum;
    @BindView(R.id.et_expect_buy_maxnum)
    EditText etExpectBuyMaxnum;
    @BindView(R.id.et_monery_buy_password)
    EditText etMoneryBuyPassword;
    @BindView(R.id.tv_forget_buy_password)
    TextView tvForgetBuyPassword;
    @BindView(R.id.tv_buy_publish)
    TextView tvBuyPublish;
    @BindView(R.id.tv_buy_cankaojia)
    TextView tvBuyCanKaoJia;

    private EntrustBuyPresenter entrustBuyPresenter;
    private double hotNum;
    private BaseActivity mContext;

    public static EntrustBuyFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        EntrustBuyFragment fragment = new EntrustBuyFragment();
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
        return R.layout.entrust_buy_fragment;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        //初始化presenter
        new EntrustBuyPresenter(this, new EntrustBuyModle());
        entrustBuyPresenter.getInfo();
    }

    @OnClick({R.id.tv_forget_buy_password, R.id.tv_buy_publish,R.id.tv_buy_cankaojia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_buy_password:
                BalancePassWordActivity.startThis(mContext);
                break;
            case R.id.tv_buy_publish:
                String businessPrice = etBusinessBuyPrice.getText().toString().trim();
                if (TextUtils.isEmpty(businessPrice)) {
                    ToastUtil.shortShow("请输入交易价格");
                    return;
                }
                double d_businessPrice = Double.parseDouble(businessPrice);
                if (d_businessPrice <= 0) {
                    ToastUtil.shortShow("交易价格不能小于零");
                    return;
                }

                String expectMinnum = etExpectBuyMinnum.getText().toString().trim();
                if (TextUtils.isEmpty(expectMinnum)) {
                    ToastUtil.shortShow("请输入您预想的最小售出数量");
                    return;
                }

                double d_expectMinnum = Double.parseDouble(expectMinnum);
                if (d_expectMinnum <= 0) {
                    ToastUtil.shortShow("请输入您预想的最小售出数量大于零");
                    return;
                }

                String expectMaxnum = etExpectBuyMaxnum.getText().toString().trim();
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

                String moneryPassword = etMoneryBuyPassword.getText().toString().trim();
                if (TextUtils.isEmpty(moneryPassword)) {
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }

                long currentTime = System.currentTimeMillis();
                entrustBuyPresenter.putUpBuy(d_businessPrice,d_expectMinnum,d_expectMaxnum,Md5Utils.getMD5(moneryPassword+currentTime),currentTime);
                break;
            case R.id.tv_buy_cankaojia:
                break;
        }
    }

    @Override
    public void onResume() {
        GALogger.d(TAG,"onResume      ");
        super.onResume();
    }

    @Override
    public void putUpBuySuccess(BaseBean baseBean) {
        EventBus.getDefault().post(new MessageEvent(2));
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if (walletResponse != null) {
            hotNum = walletResponse.getHotNum();
        }
    }

    @Override
    public void setPresenter(EntrustBuyContract.Presenter presenter) {
        entrustBuyPresenter = (EntrustBuyPresenter) presenter;
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
