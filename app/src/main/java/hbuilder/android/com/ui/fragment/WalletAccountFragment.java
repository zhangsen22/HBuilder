package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.RecycleViewLoadingLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.FinanceLogItem;
import hbuilder.android.com.modle.FinanceLogResponse;
import hbuilder.android.com.presenter.WalletAccountPresenter;
import hbuilder.android.com.presenter.contract.WalletAccountContract;
import hbuilder.android.com.presenter.modle.WalletAccountModle;
import hbuilder.android.com.ui.adapter.WalletAccountAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterLoader;
import hbuilder.android.com.ui.adapter.poweradapter.LoadMoreScrollListener;
import hbuilder.android.com.ui.adapter.poweradapter.OnLoadMoreListener;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class WalletAccountFragment extends BaseFragment implements WalletAccountContract.View, OnLoadMoreListener, PowerAdapter.OnEmptyClickListener, PowerAdapter.OnErrorClickListener, AdapterLoader.OnItemClickListener<FinanceLogItem> {

    private static final String TAG = WalletAccountFragment.class.getSimpleName();
    @BindView(R.id.walletaccount_pull_refresh_recycler)
    PullToRefreshRecyclerView walletaccountPullRefreshRecycler;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private WalletAccountPresenter walletAccountPresenter;
    private RecyclerView mRecyclerView;
    private WalletAccountAdapter walletAccountAdapter;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private boolean isRun;
    private static final int DEFAULT_TIME = 0;
    public List<Long> idList;

    public static WalletAccountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        WalletAccountFragment fragment = new WalletAccountFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.wallet_account_fragment;
    }

    @Override
    protected void initView(View root) {
        walletaccountPullRefreshRecycler.setId(R.id.recycleView);
        walletaccountPullRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = walletaccountPullRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        walletAccountAdapter = new WalletAccountAdapter(MyApplication.appContext);
        walletAccountAdapter.attachRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener(mRecyclerView));
        walletaccountPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
            }
        });
        walletAccountAdapter.setLoadMoreListener(this);
        walletAccountAdapter.setEmptyClickListener(this);
        walletAccountAdapter.setErrorClickListener(this);
        walletAccountAdapter.setOnItemClickListener(this);

        refreshAction = new Runnable() {
            @Override
            public void run() {
                walletAccountPresenter.financeLogRefresh(0);
            }
        };
        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                if (idList != null && idList.size() > 0) {
                    walletAccountPresenter.financeLogLoadMore(idList.get(0));
                }
            }
        };
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        //初始化presenter
        new WalletAccountPresenter(this, new WalletAccountModle());
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
    }

    @Override
    public void financeLogRefreshSuccess(FinanceLogResponse financeLogResponse) {
        List<FinanceLogItem> financeLog = financeLogResponse.getFinanceLog();
        if (financeLog != null && financeLog.size() > 0) {
            tvNoData.setVisibility(View.GONE);
            reverseIdList(financeLog);
            walletAccountAdapter.setTotalCount(Integer.MAX_VALUE);
            walletAccountAdapter.setList(financeLog);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    @Override
    public void financeLogRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void financeLogLoadMoreSuccess(FinanceLogResponse financeLogResponse) {
        List<FinanceLogItem> financeLog = financeLogResponse.getFinanceLog();
        if (financeLog != null && financeLog.size() > 0) {
            reverseIdList(financeLog);
            walletAccountAdapter.setTotalCount(Integer.MAX_VALUE);
            walletAccountAdapter.appendList(financeLog);
        }
        isRun = false;
    }

    public void reverseIdList(List<FinanceLogItem> billInfo){
        if(idList == null){
            idList = new ArrayList<Long>();
        }
        idList.clear();
        for (FinanceLogItem buyItem: billInfo) {
            idList.add(buyItem.getId());
        }
        Collections.reverse(idList);
    }

    @Override
    public void financeLogLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void setPresenter(WalletAccountContract.Presenter presenter) {
        walletAccountPresenter = (WalletAccountPresenter) presenter;
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

    @Override
    public void onEmptyClick(View view) {

    }

    @Override
    public void onErrorClick(View view) {

    }

    @Override
    public void onItemClick(@NonNull PowerHolder<FinanceLogItem> holder, @NonNull View itemView, int position, FinanceLogItem item) {

    }

    private void emptyAnderrorView() {
        tvNoData.setVisibility(View.VISIBLE);
    }

    public void stopPulling() {
        if (walletaccountPullRefreshRecycler != null) {
            walletaccountPullRefreshRecycler.onRefreshComplete();
        }
    }
}
