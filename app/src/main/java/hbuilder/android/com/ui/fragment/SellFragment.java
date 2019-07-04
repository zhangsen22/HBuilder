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
import hbuilder.android.com.modle.BuyItem;
import hbuilder.android.com.modle.BuyResponse;
import hbuilder.android.com.presenter.SellPresenter;
import hbuilder.android.com.presenter.contract.SellContract;
import hbuilder.android.com.presenter.modle.BuyModle;
import hbuilder.android.com.ui.adapter.SellFragmentAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterLoader;
import hbuilder.android.com.ui.adapter.poweradapter.LoadMoreScrollListener;
import hbuilder.android.com.ui.adapter.poweradapter.OnLoadMoreListener;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class SellFragment extends BaseFragment implements SellContract.View, OnLoadMoreListener, PowerAdapter.OnEmptyClickListener, PowerAdapter.OnErrorClickListener, AdapterLoader.OnItemClickListener<BuyItem> {
    private static final String TAG = SellFragment.class.getSimpleName();
    @BindView(R.id.sell_pull_refresh_recycler)
    PullToRefreshRecyclerView sellPullRefreshRecycler;
    private RecyclerView mRecyclerView;
    private SellFragmentAdapter sellFragmentAdapter;
    private SellPresenter sellPresenter;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private boolean isRun;
    private static final int DEFAULT_TIME = 1000;
    public List<Long> idList;

    public static SellFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        SellFragment fragment = new SellFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GALogger.d(TAG,"onCreate");
    }

    @Override
    protected int getRootView() {
        return R.layout.sell_ragment;
    }

    @Override
    protected void initView(View root) {
        sellPullRefreshRecycler.setId(R.id.recycleView);
        sellPullRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = sellPullRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        sellFragmentAdapter = new SellFragmentAdapter(MyApplication.appContext);
        sellFragmentAdapter.attachRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener(mRecyclerView));
        sellPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
            }
        });
        sellFragmentAdapter.setLoadMoreListener(this);
        sellFragmentAdapter.setEmptyClickListener(this);
        sellFragmentAdapter.setErrorClickListener(this);
        sellFragmentAdapter.setOnItemClickListener(this);

        refreshAction = new Runnable() {
            @Override
            public void run() {
                sellPresenter.getSellRefresh(0);
            }
        };
        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                if(idList != null && idList.size() > 0) {
                    sellPresenter.getSellLoadMore(idList.get(0));
                }
            }
        };
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG,"SellFragment  is  lazyLoadData");
        setLoadDataWhenVisible();
        //初始化presenter
        new SellPresenter(this, new BuyModle());
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
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

    private void emptyAnderrorView() {
        sellFragmentAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
        sellFragmentAdapter.showError(true);
    }

    @Override
    public void setPresenter(SellContract.Presenter presenter) {
        sellPresenter = (SellPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    public void stopPulling() {
        if (sellPullRefreshRecycler != null) {
            sellPullRefreshRecycler.onRefreshComplete();
        }
    }

    @Override
    public void getSellRefreshSuccess(BuyResponse buyResponse) {
        List<BuyItem> billInfo = buyResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            reverseIdList(billInfo);
            sellFragmentAdapter.setTotalCount(Integer.MAX_VALUE);
            sellFragmentAdapter.setList(billInfo);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    @Override
    public void getSellRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void getSellLoadMoreSuccess(BuyResponse buyResponse) {
        List<BuyItem> billInfo = buyResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            reverseIdList(billInfo);
            sellFragmentAdapter.setTotalCount(Integer.MAX_VALUE);
            sellFragmentAdapter.appendList(billInfo);
        }else {
            GALogger.d(TAG,"LoadMore  is  no");
            reverseIdList(billInfo);
            sellFragmentAdapter.setTotalCount(sellFragmentAdapter.getItemRealCount());
            sellFragmentAdapter.notifyDataSetChanged();
        }
        isRun = false;
    }

    public void reverseIdList(List<BuyItem> billInfo){
        if(idList == null){
            idList = new ArrayList<Long>();
        }
        idList.clear();
        if(billInfo == null){
            return;
        }
        for (BuyItem buyItem: billInfo) {
            idList.add(buyItem.getId());
        }
        Collections.reverse(idList);
    }

    @Override
    public void getSellLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void onEmptyClick(View view) {

    }

    @Override
    public void onErrorClick(View view) {

    }

    @Override
    public void onItemClick(@NonNull PowerHolder<BuyItem> holder, @NonNull View itemView, int position, BuyItem item) {

    }
}
