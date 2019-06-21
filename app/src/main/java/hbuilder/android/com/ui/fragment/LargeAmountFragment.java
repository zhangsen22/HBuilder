package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.growalong.util.util.GALogger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.RecycleViewLoadingLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.LargeAmountItem;
import hbuilder.android.com.modle.LargeAmountResponse;
import hbuilder.android.com.presenter.LargeAmountPresenter;
import hbuilder.android.com.presenter.contract.LargeAmountContract;
import hbuilder.android.com.presenter.modle.BuyModle;
import hbuilder.android.com.ui.adapter.LargeAmountAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterLoader;
import hbuilder.android.com.ui.adapter.poweradapter.LoadMoreScrollListener;
import hbuilder.android.com.ui.adapter.poweradapter.OnLoadMoreListener;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class LargeAmountFragment extends BaseFragment implements LargeAmountContract.View, OnLoadMoreListener, PowerAdapter.OnEmptyClickListener, PowerAdapter.OnErrorClickListener, AdapterLoader.OnItemClickListener<LargeAmountItem> {
    private static final String TAG = LargeAmountFragment.class.getSimpleName();
    @BindView(R.id.largeamount_pull_refresh_recycler)
    PullToRefreshRecyclerView largeamountPullRefreshRecycler;
    private LargeAmountPresenter presenter;
    private RecyclerView mRecyclerView;
    private LargeAmountAdapter largeAmountAdapter;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private boolean isRun;
    private static final int DEFAULT_TIME = 0;
    public List<Long> idList;

    public static LargeAmountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        LargeAmountFragment fragment = new LargeAmountFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.large_amount_fragment;
    }

    @Override
    protected void initView(View root) {
        largeamountPullRefreshRecycler.setId(R.id.recycleView);
        largeamountPullRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = largeamountPullRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        largeAmountAdapter = new LargeAmountAdapter(MyApplication.appContext);
        largeAmountAdapter.attachRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener(mRecyclerView));
        largeamountPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
            }
        });
        largeAmountAdapter.setLoadMoreListener(this);
        largeAmountAdapter.setEmptyClickListener(this);
        largeAmountAdapter.setErrorClickListener(this);
        largeAmountAdapter.setOnItemClickListener(this);

        refreshAction = new Runnable() {
            @Override
            public void run() {
                presenter.getHugeBillinfoRefresh(0);
            }
        };
        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                if(idList != null && idList.size() > 0){
                    presenter.getHugeBillinfoLoadMore(idList.get(0));
                }
            }
        };
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        //初始化presenter
        new LargeAmountPresenter(this, new BuyModle());
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
    }

    @Override
    public void getHugeBillinfoRefreshSuccess(LargeAmountResponse largeAmountResponse) {
        List<LargeAmountItem> billInfo = largeAmountResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
//            buyFragmentAdapter.setTotalCount(totalSize);
            largeAmountAdapter.setList(billInfo);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    private void emptyAnderrorView() {
        largeAmountAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
        largeAmountAdapter.showError(true);
    }

    @Override
    public void getHugeBillinfoRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void getHugeBillinfoLoadMoreSuccess(LargeAmountResponse largeAmountResponse) {
        List<LargeAmountItem> billInfo = largeAmountResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            if(idList == null){
                idList = new ArrayList<Long>();
            }
            idList.clear();
            for (LargeAmountItem buyItem: billInfo) {
                idList.add(buyItem.getId());
            }
            Collections.reverse(idList);
//            buyFragmentAdapter.setTotalCount(totalSize);
            largeAmountAdapter.appendList(billInfo);
        }
        isRun = false;
    }

    @Override
    public void getHugeBillinfoLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void setPresenter(LargeAmountContract.Presenter presenter) {
        this.presenter = (LargeAmountPresenter) presenter;
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
    public void onLoadMore() {
        if (isRun) {
            GALogger.d(TAG, "onLoadMore:正在执行，直接返回。。。 ");
            return;
        }
        GALogger.d(TAG, "onLoadMore: ");
        isRun = true;
        mRecyclerView.postDelayed(loadMoreAction, DEFAULT_TIME);
    }

    public void stopPulling() {
        if (largeamountPullRefreshRecycler != null) {
            largeamountPullRefreshRecycler.onRefreshComplete();
        }
    }

    @Override
    public void onEmptyClick(View view) {

    }

    @Override
    public void onErrorClick(View view) {

    }

    @Override
    public void onItemClick(@NonNull PowerHolder<LargeAmountItem> holder, @NonNull View itemView, int position, LargeAmountItem item) {

    }
}
