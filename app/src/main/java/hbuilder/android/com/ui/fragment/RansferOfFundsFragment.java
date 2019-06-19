package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.GsonUtil;
import com.growalong.util.util.Md5Utils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.presenter.RansferOfFundsPresenter;
import hbuilder.android.com.presenter.contract.RansferOfFundsContract;
import hbuilder.android.com.presenter.modle.RansferOfFundsModle;
import hbuilder.android.com.ui.activity.RansferOfFundsActivity;
import hbuilder.android.com.util.SharedPreferencesUtils;
import hbuilder.android.com.util.ToastUtil;

/**
 * 1  form  钱包账户
 * 2  from  交易账户
 */

public class RansferOfFundsFragment extends BaseFragment implements RansferOfFundsContract.View {

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
    private RansferOfFundsActivity ransferOfFundsActivity;
    private int fromType;
    private double walletNum;
    private double hotNum;
    private RansferOfFundsPresenter presenter;

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
    }

    @Override
    protected int getRootView() {
        return R.layout.ransfer_offunds_ragment;
    }

    @Override
    protected void initView(View root) {
        tvTitle.setText("资金划转");
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        WalletResponse walletResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.WALLET_BALANCE), WalletResponse.class);
        if (walletResponse != null) {
            walletNum = walletResponse.getWalletNum();
            hotNum = walletResponse.getHotNum();
        }

        if(fromType == 1){
            tvLeft.setText("我的钱包");
            tvRight.setText("交易账户");
            etHuazhuanMore.setText(new DecimalFormat("0.00").format(walletNum));
        }else if(fromType == 2){
            tvLeft.setText("交易账户");
            tvRight.setText("我的钱包");
            etHuazhuanMore.setText(new DecimalFormat("0.00").format(hotNum));
        }
        //初始化presenter
        new RansferOfFundsPresenter(this, new RansferOfFundsModle());
    }

    @OnClick({R.id.iv_back, R.id.tv_all, R.id.tv_publish_zhuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ransferOfFundsActivity.finish();
                break;
            case R.id.tv_all:
                if(fromType == 1){
                    etHuazhuanNum.setText(walletNum+"");
                }else if(fromType == 2){
                    etHuazhuanNum.setText(hotNum+"");
                }
                break;
            case R.id.tv_publish_zhuan:
                String huazhuanNum = etHuazhuanNum.getText().toString().trim();
                if(TextUtils.isEmpty(huazhuanNum)){
                    ToastUtil.shortShow("请输入划转数量");
                    return;
                }
                double d_huazhuanNum = Double.parseDouble(huazhuanNum);
                if(d_huazhuanNum <= 0){
                    if(TextUtils.isEmpty(huazhuanNum)){
                        ToastUtil.shortShow("请输入划转数量");
                        return;
                    }
                }

                if(fromType == 1){
                    if(d_huazhuanNum > walletNum){
                        ToastUtil.shortShow("超出了最大划转数量");
                        return;
                    }
                }else if(fromType == 2){
                    if(d_huazhuanNum > hotNum){
                        ToastUtil.shortShow("超出了最大划转数量");
                        return;
                    }
                }

                String huazhuanPassword = etHuazhuanPassword.getText().toString().trim();
                if(TextUtils.isEmpty(huazhuanPassword)){
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }
                long currentTime = System.currentTimeMillis();
                presenter.transfer(fromType,d_huazhuanNum,Md5Utils.getMD5(huazhuanPassword+currentTime),currentTime);
                break;
        }
    }

    @Override
    public void transferSuccess(WalletResponse walletResponse) {
        if(walletResponse != null){
            SharedPreferencesUtils.putString(Constants.WALLET_BALANCE,GsonUtil.getInstance().objTojson(walletResponse));
        }
        ransferOfFundsActivity.finish();
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
