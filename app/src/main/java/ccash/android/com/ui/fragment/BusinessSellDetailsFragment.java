package ccash.android.com.ui.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.DateUtil;
import com.growalong.util.util.GALogger;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.modle.BaseBean;
import ccash.android.com.modle.SellResponse;
import ccash.android.com.presenter.BusinessSellDetailsPresenter;
import ccash.android.com.presenter.contract.BusinessSellDetailsContract;
import ccash.android.com.ui.activity.BusinessSellDetailsActivity;
import ccash.android.com.util.ToastUtil;

public class BusinessSellDetailsFragment extends BaseFragment implements BusinessSellDetailsContract.View {
    private static final String TAG = BusinessSellDetailsFragment.class.getSimpleName();
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
    @BindView(R.id.tv_biusness_price)
    TextView tvBiusnessPrice;
    @BindView(R.id.tv_biusness_num)
    TextView tvBiusnessNum;
    @BindView(R.id.tv_sell_name)
    TextView tvSellName;
    @BindView(R.id.tv_sell_time)
    TextView tvSellTime;
    @BindView(R.id.tv_shoukuai_cankaoma)
    TextView tvShoukuaiCankaoma;
    @BindView(R.id.cb_sell_agree)
    CheckBox cbSellAgree;
    @BindView(R.id.tv_sell_recive_monery)
    TextView tvSellReciveMonery;
    @BindView(R.id.tv_sell_shensu)
    TextView tvSellShensu;
    @BindView(R.id.tv_sell_fangbi)
    TextView tvSellFangbi;
    @BindView(R.id.iv_copy_ordercode)
    ImageView ivCopyOrderCode;
    private BusinessSellDetailsActivity businessSellDetailsActivity;
    private BusinessSellDetailsPresenter presenter;
    private SellResponse sellResponse;
    private double price;
    private double num;
    private String nickname;
    private long createTime = 0;
    private CountDownTimer timer;

    public static BusinessSellDetailsFragment newInstance(@Nullable SellResponse sellResponse, double price, double num, String nickname) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("sellResponse", sellResponse);
        arguments.putDouble("price", price);
        arguments.putDouble("num", num);
        arguments.putString("nickname", nickname);
        BusinessSellDetailsFragment fragment = new BusinessSellDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessSellDetailsActivity = (BusinessSellDetailsActivity) getActivity();
        sellResponse = getArguments().getParcelable("sellResponse");
        createTime = sellResponse.getCreatTime();
        price = getArguments().getDouble("price");
        num = getArguments().getDouble("num");
        nickname = getArguments().getString("nickname");
    }

    @Override
    protected int getRootView() {
        return R.layout.business_sell_details_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("出售"+MyApplication.appContext.getResources().getString(R.string.bco));
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        long currentTime = System.currentTimeMillis();
        if(currentTime >= createTime + 10*60*1000){
            tvSellShensu.setEnabled(true);
        }else {
            timer = new CountDownTimer(createTime + 10*60*1000 - currentTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //如果是Fragment 就判断getActivity() 是否为NULL
                    //如果是Activity 就判断!activity.isFinishing() 是否为NULL
                    if (businessSellDetailsActivity != null) {
                        int left = (int) ((millisUntilFinished - 1000) / 1000);
                        GALogger.d(TAG, "left       " + left);
                        if (left > 0) {
                            tvCountDown.setText(DateUtil.getCurrentDateString2(millisUntilFinished));
                        } else {
                            tvSellShensu.setEnabled(true);
                        }
                    }
                }
                @Override
                public void onFinish() {
                    tvSellShensu.setEnabled(true);
                }
            };
            timer.start();
        }
        tvOrderCode.setText(sellResponse.getTradeId());
        tvPayPrice.setText(new DecimalFormat("0.00").format( price * num));
        tvBiusnessPrice.setText(new DecimalFormat("0.00").format(price));
        tvBiusnessNum.setText(num+"");
        tvSellName.setText(nickname);
        tvSellTime.setText(DateUtil.getCurrentDateString3(System.currentTimeMillis()));
        tvShoukuaiCankaoma.setText(sellResponse.getPayCode()+"");
        tvSellReciveMonery.setText(new DecimalFormat("0.00").format(price * num));
    }

    @OnClick({R.id.iv_back, R.id.tv_sell_shensu, R.id.tv_sell_fangbi,R.id.iv_copy_ordercode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                businessSellDetailsActivity.finish();
                break;
            case R.id.tv_sell_shensu:
                presenter.appeal(sellResponse.getTradeId());
                break;
            case R.id.tv_sell_fangbi:
                if(!cbSellAgree.isChecked()){
                    ToastUtil.shortShow("请确认收到的金额");
                    return;
                }
                presenter.fb_transfer(sellResponse.getTradeId());
                break;
            case R.id.iv_copy_ordercode:
                String trim = tvOrderCode.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    return;
                }
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) businessSellDetailsActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", trim);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtil.shortShow("已复制到剪贴板");
                break;
        }
    }

    @Override
    public void appealSuccess(BaseBean baseBean) {
        businessSellDetailsActivity.setResult(Activity.RESULT_OK);
        businessSellDetailsActivity.finish();
    }

    @Override
    public void fb_transferSuccess(BaseBean baseBean) {
        businessSellDetailsActivity.setResult(Activity.RESULT_OK);
        businessSellDetailsActivity.finish();
    }

    @Override
    public void setPresenter(BusinessSellDetailsContract.Presenter presenter) {
        this.presenter = (BusinessSellDetailsPresenter) presenter;
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
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        super.onDestroyView();
    }
}
