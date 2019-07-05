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
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.BuyItem;
import hbuilder.android.com.modle.BuyResponse;
import hbuilder.android.com.presenter.BuyPresenter;
import hbuilder.android.com.presenter.contract.BuyContract;
import hbuilder.android.com.presenter.modle.BuyModle;
import hbuilder.android.com.ui.adapter.BuyFragmentAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterLoader;
import hbuilder.android.com.ui.adapter.poweradapter.LoadMoreScrollListener;
import hbuilder.android.com.ui.adapter.poweradapter.OnLoadMoreListener;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class BuyFragment extends BaseFragment implements OnLoadMoreListener, PowerAdapter.OnEmptyClickListener, PowerAdapter.OnErrorClickListener, AdapterLoader.OnItemClickListener<BuyItem>,BuyContract.View {
    private static final String TAG = BuyFragment.class.getSimpleName();

    @BindView(R.id.buy_pull_refresh_recycler)
    PullToRefreshRecyclerView buyPullRefreshRecycler;
    private RecyclerView mRecyclerView;
    private BuyFragmentAdapter buyFragmentAdapter;
    private BuyPresenter buyPresenter;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private boolean isRun;
    private static final int DEFAULT_TIME = 0;
    public List<Long> idList;

    public static BuyFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        BuyFragment fragment = new BuyFragment();
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
        return R.layout.buy_ragment;
    }

    @Override
    protected void initView(View root) {
        buyPullRefreshRecycler.setId(R.id.recycleView);
        buyPullRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = buyPullRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        buyFragmentAdapter = new BuyFragmentAdapter(MyApplication.appContext);
        buyFragmentAdapter.attachRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener(mRecyclerView));
        buyPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
            }
        });
        buyFragmentAdapter.setLoadMoreListener(this);
        buyFragmentAdapter.setEmptyClickListener(this);
        buyFragmentAdapter.setErrorClickListener(this);
        buyFragmentAdapter.setOnItemClickListener(this);

        refreshAction = new Runnable() {
            @Override
            public void run() {
                buyPresenter.getBuyRefresh(0);
            }
        };
        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                if(idList != null && idList.size() > 0){
                    buyPresenter.getBuyLoadMore(idList.get(0));
                }
            }
        };
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        GALogger.d(TAG,"lazyLoadData   ");
        //初始化presenter
        new BuyPresenter(this, new BuyModle());
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

    @Override
    public void onEmptyClick(View view) {

    }

    @Override
    public void onErrorClick(View view) {

    }

    @Override
    public void onItemClick(@NonNull PowerHolder<BuyItem> holder, @NonNull View itemView, int position, BuyItem item) {

    }

    @Override
    public void getBuyRefreshSuccess(BuyResponse buyResponse) {
        List<BuyItem> billInfo = buyResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            reverseIdList(billInfo);
            if(billInfo.size() <= Constants.RECYCLEVIEW_TOTALCOUNT){
                buyFragmentAdapter.setTotalCount(billInfo.size());
            }else {
                buyFragmentAdapter.setTotalCount(Integer.MAX_VALUE);
            }
            buyFragmentAdapter.setList(billInfo);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    private void emptyAnderrorView() {
        buyFragmentAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
        buyFragmentAdapter.showError(true);
    }

    @Override
    public void getBuyRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void getBuyLoadMoreSuccess(BuyResponse buyResponse) {
        List<BuyItem> billInfo = buyResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            reverseIdList(billInfo);
            buyFragmentAdapter.setTotalCount(Integer.MAX_VALUE);
            buyFragmentAdapter.appendList(billInfo);
        }else {
            GALogger.d(TAG,"LoadMore  is  no");
            reverseIdList(billInfo);
            buyFragmentAdapter.setTotalCount(buyFragmentAdapter.getItemRealCount());
            buyFragmentAdapter.notifyDataSetChanged();
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
    public void getBuyLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void setPresenter(BuyContract.Presenter presenter) {
        buyPresenter = (BuyPresenter) presenter;
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
        if (buyPullRefreshRecycler != null) {
            buyPullRefreshRecycler.onRefreshComplete();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GALogger.d(TAG,"onResume   ");
    }
}
