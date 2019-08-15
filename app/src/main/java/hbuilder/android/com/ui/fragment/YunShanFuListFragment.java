package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.ui.activity.PaySettingActivity;
import hbuilder.android.com.ui.activity.YunShanFuListActivity;
import hbuilder.android.com.util.SharedPreferencesUtils;
import hbuilder.android.com.util.ToastUtil;

public class YunShanFuListFragment extends BaseFragment {
    private static final String TAG = IdCastPayListFragment.class.getSimpleName();
    @BindView(R.id.yunshanfu_pull_refresh_recycler)
    PullToRefreshRecyclerView yunshanfuPullRefreshRecycler;
    private YunShanFuListActivity yunShanFuListActivity;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit_forget_login)
    TextView tvSubmitForgetLogin;

    public static YunShanFuListFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        YunShanFuListFragment fragment = new YunShanFuListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yunShanFuListActivity = (YunShanFuListActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.yunshanfu_list_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("云闪付收款设置");
        yunshanfuPullRefreshRecycler.setId(R.id.recycleView);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
    }

    @OnClick({R.id.iv_back, R.id.tv_submit_forget_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                yunShanFuListActivity.finish();
                break;
            case R.id.tv_submit_forget_login:
                boolean wxPayLock = SharedPreferencesUtils.getBoolean(Constants.WXPAYLOCK, false);
                if (wxPayLock) {
                    ToastUtil.shortShow(MyApplication.appContext.getResources().getString(R.string.text33));
                } else {
                    //id:0                //如果为新加,设为0,如果为修改,此处为修改的收款方式的id
                    PaySettingActivity.startThis(yunShanFuListActivity, 4, null, Constants.REQUESTCODE_17);
                }
                break;
        }
    }
}
