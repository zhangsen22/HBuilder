package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.activity.HelpAndKefuActivity;

public class HelpAndKefuFragment extends BaseFragment {
    private static final String TAG = HelpAndKefuFragment.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.hejpkefu_pull_refresh_recycler)
    PullToRefreshRecyclerView hejpkefuPullRefreshRecycler;
    @BindView(R.id.ll_kefu_webchat)
    LinearLayout llKefuWebchat;
    @BindView(R.id.ll_kefu_tb)
    LinearLayout llKefuTb;
    private HelpAndKefuActivity helpAndKefuActivity;

    public static HelpAndKefuFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        HelpAndKefuFragment fragment = new HelpAndKefuFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpAndKefuActivity = (HelpAndKefuActivity) getActivity();
    }


    @Override
    protected int getRootView() {
        return R.layout.help_and_kefu_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("帮助与客服");
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
    }

    @OnClick({R.id.ll_kefu_webchat, R.id.ll_kefu_tb,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                helpAndKefuActivity.finish();
                break;
            case R.id.ll_kefu_webchat:
                break;
            case R.id.ll_kefu_tb:
                break;
        }
    }
}
