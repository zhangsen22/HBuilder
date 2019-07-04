package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growalong.util.util.GALogger;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.RewardLogResponse;
import hbuilder.android.com.presenter.TradingAccountPresenter;
import hbuilder.android.com.presenter.contract.TradingAccountContract;
import hbuilder.android.com.presenter.modle.TradingAccountModle;
import hbuilder.android.com.ui.activity.RansferOfFundsActivity;
import hbuilder.android.com.ui.activity.RewardDetailActivity;

public class TradingAccountFragment extends BaseFragment implements TradingAccountContract.View {
    private static final String TAG = TradingAccountFragment.class.getSimpleName();
    @BindView(R.id.tv_transfer_of_funds)
    TextView tvTransferOfFunds;
    @BindView(R.id.tv_business_reward)
    TextView tvBusinessReward;
    @BindView(R.id.tv_yesterday_earnings1)
    TextView tvYesterdayEarnings1;
    @BindView(R.id.ll_business_reward)
    LinearLayout llBusinessReward;
    @BindView(R.id.tv_guadan_reward)
    TextView tvGuadanReward;
    @BindView(R.id.tv_yesterday_earnings2)
    TextView tvYesterdayEarnings2;
    @BindView(R.id.ll_guadan_reward)
    LinearLayout llGuadanReward;
    @BindView(R.id.tv_tuiguang_reward)
    TextView tvTuiguangReward;
    @BindView(R.id.tv_yesterday_earnings3)
    TextView tvYesterdayEarnings3;
    @BindView(R.id.ll_tuigunang_reward)
    LinearLayout llTuigunangReward;
    @BindView(R.id.tv_daili_reward)
    TextView tvDailiReward;
    @BindView(R.id.tv_yesterday_earnings4)
    TextView tvYesterdayEarnings4;
    @BindView(R.id.ll_daili_reward)
    LinearLayout llDailiReward;
    private TradingAccountPresenter tradingAccountPresenter;
    private BaseActivity mContext;
    private RewardLogResponse rewardLogResponse;

    public static TradingAccountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        TradingAccountFragment fragment = new TradingAccountFragment();
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
        return R.layout.trading_account_fragment;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG,"TradingAccountFragment   is    lazyLoadData");
        setLoadDataWhenVisible();
        //初始化presenter
        new TradingAccountPresenter(this, new TradingAccountModle());
        tradingAccountPresenter.rewardLog();
    }

    @Override
    public void rewardLogSuccess(RewardLogResponse rewardLogResponse) {
        if(rewardLogResponse != null){
            this.rewardLogResponse = rewardLogResponse;
            tvBusinessReward.setText(new DecimalFormat("0.00").format(rewardLogResponse.getTotTradeReward()));
            tvYesterdayEarnings1.setText(new DecimalFormat("0.00").format(rewardLogResponse.getLastTradeReward()));
            tvGuadanReward.setText(new DecimalFormat("0.00").format(rewardLogResponse.getTotBillReward()));
            tvYesterdayEarnings2.setText(new DecimalFormat("0.00").format(rewardLogResponse.getLastBillReward()));
            tvTuiguangReward.setText(new DecimalFormat("0.00").format(rewardLogResponse.getTotTGReward()));
            tvYesterdayEarnings3.setText(new DecimalFormat("0.00").format(rewardLogResponse.getLastTGReward()));
            tvDailiReward.setText(new DecimalFormat("0.00").format(rewardLogResponse.getTotAgentReward()));
            tvYesterdayEarnings4.setText(new DecimalFormat("0.00").format(rewardLogResponse.getLastAgentReward()));
        }
    }

    @Override
    public void setPresenter(TradingAccountContract.Presenter presenter) {
        this.tradingAccountPresenter = (TradingAccountPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @OnClick({R.id.tv_transfer_of_funds, R.id.ll_business_reward, R.id.ll_guadan_reward, R.id.ll_tuigunang_reward, R.id.ll_daili_reward})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_transfer_of_funds:
                RansferOfFundsActivity.startThis(mContext,2,Constants.REQUESTCODE_11);
                break;
            case R.id.ll_business_reward:
                if(rewardLogResponse != null) {
                    RewardDetailActivity.startThis(mContext, 1,rewardLogResponse);
                }
                break;
            case R.id.ll_guadan_reward:
                if(rewardLogResponse != null) {
                    RewardDetailActivity.startThis(mContext, 4, rewardLogResponse);
                }
                break;
            case R.id.ll_tuigunang_reward:
                if(rewardLogResponse != null) {
                    RewardDetailActivity.startThis(mContext, 2, rewardLogResponse);
                }
                break;
            case R.id.ll_daili_reward:
                if(rewardLogResponse != null) {
                    RewardDetailActivity.startThis(mContext, 3, rewardLogResponse);
                }
                break;
        }
    }
}
