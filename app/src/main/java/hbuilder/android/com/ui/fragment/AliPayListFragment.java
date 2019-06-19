package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.RecycleViewLoadingLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.modle.AliPayPayeeItemModel;
import hbuilder.android.com.modle.AliPayPayeeModel;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelAliPay;
import hbuilder.android.com.presenter.AliPayListPresenter;
import hbuilder.android.com.presenter.contract.AliPayListContract;
import hbuilder.android.com.ui.activity.AliPayListActivity;
import hbuilder.android.com.ui.activity.PaySettingActivity;
import hbuilder.android.com.ui.adapter.AliPayListAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterLoader;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterSelect;
import hbuilder.android.com.ui.adapter.poweradapter.ISelect;
import hbuilder.android.com.ui.adapter.poweradapter.LoadMoreScrollListener;
import hbuilder.android.com.ui.adapter.poweradapter.OnLoadMoreListener;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class AliPayListFragment extends BaseFragment implements AliPayListContract.View, OnLoadMoreListener, PowerAdapter.OnEmptyClickListener, PowerAdapter.OnErrorClickListener, AdapterLoader.OnItemClickListener<AliPayPayeeItemModel>,AliPayListAdapter.OnAliPayCheckListener {
    private static final String TAG = IdCastPayListFragment.class.getSimpleName();
    private static AliPayListActivity aliPayListActivity;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.alipay_pull_refresh_recycler)
    PullToRefreshRecyclerView alipayPullRefreshRecycler;
    @BindView(R.id.tv_submit_forget_login)
    TextView tvSubmitForgetLogin;
    private AliPayListPresenter presenter;

    private RecyclerView mRecyclerView;
    private AliPayListAdapter aliPayListAdapter;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private boolean isRun;
    private static final int DEFAULT_TIME = 0;
    public List<Long> idList;

    public static AliPayListFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        AliPayListFragment fragment = new AliPayListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aliPayListActivity = (AliPayListActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.ali_pay_list_fragment;
    }

    @Override
    protected void initView(View root) {
        tvTitle.setText("支付宝收款设置");
        alipayPullRefreshRecycler.setId(R.id.recycleView);
        alipayPullRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = alipayPullRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        aliPayListAdapter = new AliPayListAdapter(MyApplication.appContext,this);
        aliPayListAdapter.attachRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener(mRecyclerView));
        alipayPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
            }
        });
        aliPayListAdapter.setLoadMoreListener(this);
        aliPayListAdapter.setEmptyClickListener(this);
        aliPayListAdapter.setErrorClickListener(this);
        aliPayListAdapter.setOnItemClickListener(this);

        refreshAction = new Runnable() {
            @Override
            public void run() {
                if (AccountManager.getInstance().isHaveAliPayee()) {
                    presenter.aliPayListRefresh(3);
                }
            }
        };
        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                if (idList != null && idList.size() > 0) {
//                    presenter.paysetupBankLoadMore(idList.get(0));
                }
            }
        };
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
    }

    @Override
    public void aliPayListRefreshSuccess(PaySetupModelAliPay paySetupModelAliPay) {
        AliPayPayeeModel aliPayeeObj = paySetupModelAliPay.getAliPayeeObj();
        if(aliPayeeObj != null){
            long defalut = aliPayeeObj.getDefalut();
            List<AliPayPayeeItemModel> list = aliPayeeObj.getList();
            if (list != null && list.size() > 0) {
//            buyFragmentAdapter.setTotalCount(totalSize);
                aliPayListAdapter.setList(list);
            } else {
                emptyAnderrorView();
            }
            stopPulling();
        }
    }

    private void emptyAnderrorView() {
        aliPayListAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
        aliPayListAdapter.showError(true);
    }

    @Override
    public void aliPayListRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void aliPayListLoadMoreSuccess(PaySetupModelAliPay paySetupModelAliPay) {
//        List<MessageCenterItem> msg = messageCenterResponse.getMsg();
//        if (msg != null && msg.size() > 0) {
//            if(idList == null){
//                idList = new ArrayList<Long>();
//            }
//            idList.clear();
//            for (MessageCenterItem messageCenterItem: msg) {
//                idList.add(messageCenterItem.getId());
//            }
//            Collections.reverse(idList);
////            buyFragmentAdapter.setTotalCount(totalSize);
//            idCastPayAdapter.appendList(msg);
//        }
//        isRun = false;
    }

    @Override
    public void aliPayListLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void setDefaultPayAliPaySuccess(BaseBean baseBean) {

    }

    @Override
    public void setPresenter(AliPayListContract.Presenter presenter) {
        this.presenter = (AliPayListPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @OnClick({R.id.iv_back, R.id.tv_submit_forget_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                aliPayListActivity.finish();
                break;
            case R.id.tv_submit_forget_login:
                PaySettingActivity.startThis(aliPayListActivity,1);
                break;
        }
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
        if (alipayPullRefreshRecycler != null) {
            alipayPullRefreshRecycler.onRefreshComplete();
        }
    }

    @Override
    public void onEmptyClick(View view) {

    }

    @Override
    public void onErrorClick(View view) {

    }

    @Override
    public void onItemClick(@NonNull PowerHolder<AliPayPayeeItemModel> holder, @NonNull View itemView, int position, AliPayPayeeItemModel item) {
        PaySettingActivity.startThis(aliPayListActivity,1);
    }

    @Override
    public void onAliPayCheck(int position, AliPayPayeeItemModel aliPayPayeeItemModel) {

    }
}
