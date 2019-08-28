package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.RecycleViewLoadingLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.modle.BulletinListItem;
import ccash.android.com.ui.activity.BulletinActivity;
import ccash.android.com.ui.adapter.BulletinAdapter;

public class BulletinFragment extends BaseFragment {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.bulletin_refresh_recycler)
    PullToRefreshRecyclerView bulletinRefreshRecycler;
    RecyclerView mRecyclerView;
    private BulletinActivity bulletinActivity;
    private BulletinAdapter bulletinAdapter;
    private ArrayList<BulletinListItem> bulletinList;

    public static BulletinFragment newInstance(@Nullable ArrayList<BulletinListItem> bulletinList) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("bulletinList", bulletinList);
        BulletinFragment fragment = new BulletinFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bulletinActivity = (BulletinActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.bulletin_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        bulletinList = getArguments().getParcelableArrayList("bulletinList");
        tvTitle.setText("公告");
        bulletinRefreshRecycler.setId(R.id.recycleView);
        bulletinRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = bulletinRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        bulletinAdapter = new BulletinAdapter(MyApplication.appContext);
        bulletinAdapter.attachRecyclerView(mRecyclerView);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        bulletinAdapter.setTotalCount(bulletinList.size());
        bulletinAdapter.setList(bulletinList);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        bulletinActivity.finish();
    }
}
