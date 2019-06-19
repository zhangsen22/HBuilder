package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.SharedPreferenceUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.RecycleViewLoadingLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.MyBuyinfoItem;
import hbuilder.android.com.modle.MyBuyinfoResponse;
import hbuilder.android.com.modle.MyEntrustinfoItem;
import hbuilder.android.com.modle.MyEntrustinfoResponse;
import hbuilder.android.com.modle.MySellinfoItem;
import hbuilder.android.com.modle.MySellinfoResponse;
import hbuilder.android.com.modle.OrderDetailsModle;
import hbuilder.android.com.presenter.OrderItemDetailsPresenter;
import hbuilder.android.com.presenter.contract.OrderItemDetailsContract;
import hbuilder.android.com.presenter.modle.OrderItemDetailsModle;
import hbuilder.android.com.ui.activity.OrderDetailsActivity;
import hbuilder.android.com.ui.adapter.OrderBuyDetailsAdapter;
import hbuilder.android.com.ui.adapter.OrderEntrustDetailsAdapter;
import hbuilder.android.com.ui.adapter.OrderSellDetailsAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.AdapterLoader;
import hbuilder.android.com.ui.adapter.poweradapter.LoadMoreScrollListener;
import hbuilder.android.com.ui.adapter.poweradapter.OnLoadMoreListener;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;
import hbuilder.android.com.util.SharedPreferencesUtils;

/**
 * 1:我的卖出    1:未完成  2:已完成  3:申诉中  4:已取消
 * 2:我的买入    1:未完成  2:已完成  3:申诉中  4:已取消
 * 3:我的委托单  1:购买委托  2:出售委托
 */
public class OrderItemDetailsFragment extends BaseFragment implements OnLoadMoreListener, PowerAdapter.OnEmptyClickListener, PowerAdapter.OnErrorClickListener, OrderItemDetailsContract.View, OrderEntrustDetailsAdapter.OrderEntrustClickListenering, OrderSellDetailsAdapter.OrderSellClickListenering, OrderBuyDetailsAdapter.OrderBuyClickListenering {
    private static final String TAG = OrderItemDetailsFragment.class.getSimpleName();
    @BindView(R.id.order_pull_refresh_recycler)
    PullToRefreshRecyclerView orderPullRefreshRecycler;
    private OrderSellDetailsAdapter orderSellDetailsAdapter;
    private OrderBuyDetailsAdapter orderBuyDetailsAdapter;
    private OrderEntrustDetailsAdapter orderEntrustDetailsAdapter;
    private BaseActivity mContext;
    private OrderItemDetailsPresenter presenter;
    private RecyclerView mRecyclerView;
    private int parentType;
    private int childType;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private boolean isRun;
    private static final int DEFAULT_TIME = 0;
    public List<Long> sellIdList;
    public List<Long> buyIdList;
    public List<Long> entrustIdList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (BaseActivity) activity;
    }

    public static OrderItemDetailsFragment newInstance(int parentType,int childType) {
        Bundle arguments = new Bundle();
        arguments.putInt("parentType", parentType);
        arguments.putInt("childType", childType);
        OrderItemDetailsFragment fragment = new OrderItemDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentType = getArguments().getInt("parentType");
        childType = getArguments().getInt("childType");
        GALogger.d(TAG, "parentType    " + parentType+"    childType    "+childType);
    }

    @Override
    protected int getRootView() {
        return R.layout.order_item_details_fragment;
    }

    @Override
    protected void initView(View root) {
        orderPullRefreshRecycler.setId(R.id.recycleView);
        orderPullRefreshRecycler.setHeaderLayout(new RecycleViewLoadingLayout(MyApplication.appContext));
        mRecyclerView = orderPullRefreshRecycler.getRefreshableView();
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.appContext, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        if(parentType == 1) {
            orderSellDetailsAdapter = new OrderSellDetailsAdapter(mContext,childType,this);
            orderSellDetailsAdapter.attachRecyclerView(mRecyclerView);
            orderSellDetailsAdapter.setLoadMoreListener(this);
            orderSellDetailsAdapter.setEmptyClickListener(this);
            orderSellDetailsAdapter.setErrorClickListener(this);
            orderSellDetailsAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<MySellinfoItem>() {
                @Override
                public void onItemClick(@NonNull PowerHolder<MySellinfoItem> holder, @NonNull View itemView, int position, MySellinfoItem item) {
                    if(item != null){
                        int type = item.getType();//收款方式,1为支付宝，2为微信，3为银行账户
                        String name = null;
                        String account = null;
                        String base64Img = null;
                        if(type == 3){
                            name = SharedPreferencesUtils.getString(Constants.bankName);
                            account = SharedPreferencesUtils.getString(Constants.bankAccount);
                            base64Img = "";
                        }else if(type == 1){
                            name = SharedPreferencesUtils.getString(Constants.aliPayName);
                            account = SharedPreferencesUtils.getString(Constants.aliPayAccount);
                            base64Img = SharedPreferencesUtils.getString(Constants.aliPayBase64Img);
                        }else if(type == 2){
                            name = SharedPreferencesUtils.getString(Constants.webChatName);
                            account = SharedPreferencesUtils.getString(Constants.webChatAccount);
                            base64Img = SharedPreferencesUtils.getString(Constants.webChatBase64Img);
                        }
                        OrderDetailsModle orderDetailsModle = new OrderDetailsModle(item.getTradeId(), item.getStatus(), item.getPrice(), item.getNum(), item.getCreateTime(), item.getPayCode(),name,account,base64Img, type);
                        OrderDetailsActivity.startThis(mContext,orderDetailsModle);
                    }
                }
            });
        }else if(parentType == 2){
            orderBuyDetailsAdapter = new OrderBuyDetailsAdapter(mContext,childType,this);
            orderBuyDetailsAdapter.attachRecyclerView(mRecyclerView);
            orderBuyDetailsAdapter.setLoadMoreListener(this);
            orderBuyDetailsAdapter.setEmptyClickListener(this);
            orderBuyDetailsAdapter.setErrorClickListener(this);
            orderBuyDetailsAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<MyBuyinfoItem>() {
                @Override
                public void onItemClick(@NonNull PowerHolder<MyBuyinfoItem> holder, @NonNull View itemView, int position, MyBuyinfoItem item) {
                    if(item != null){
                        int type = item.getType();//收款方式,1为支付宝，2为微信，3为银行账户
                        String name = null;
                        String account = null;
                        String base64Img = null;
                        if(type == 3){
                            name = SharedPreferencesUtils.getString(Constants.bankName);
                            account = SharedPreferencesUtils.getString(Constants.bankAccount);
                            base64Img = "";
                        }else if(type == 1){
                            name = SharedPreferencesUtils.getString(Constants.aliPayName);
                            account = SharedPreferencesUtils.getString(Constants.aliPayAccount);
                            base64Img = SharedPreferencesUtils.getString(Constants.aliPayBase64Img);
                        }else if(type == 2){
                            name = SharedPreferencesUtils.getString(Constants.webChatName);
                            account = SharedPreferencesUtils.getString(Constants.webChatAccount);
                            base64Img = SharedPreferencesUtils.getString(Constants.webChatBase64Img);
                        }
                        OrderDetailsModle orderDetailsModle = new OrderDetailsModle(item.getTradeId(), item.getStatus(), item.getPrice(), item.getNum(), item.getCreateTime(), item.getPayCode(),name,account,base64Img, type);
                        OrderDetailsActivity.startThis(mContext,orderDetailsModle);
                    }
                }
            });
        }else if(parentType == 3){
            orderEntrustDetailsAdapter = new OrderEntrustDetailsAdapter(MyApplication.appContext,childType,this);
            orderEntrustDetailsAdapter.attachRecyclerView(mRecyclerView);
            orderEntrustDetailsAdapter.setLoadMoreListener(this);
            orderEntrustDetailsAdapter.setEmptyClickListener(this);
            orderEntrustDetailsAdapter.setErrorClickListener(this);
        }
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener(mRecyclerView));
        orderPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
            }
        });

        refreshAction = new Runnable() {
            @Override
            public void run() {
                if(parentType == 1) {
                    presenter.mySellinfoRefresh(childType, 0);
                }else if(parentType == 2){
                    presenter.myBuyinfoRefresh(childType, 0);
                }else if(parentType == 3){
                    presenter.myBillInfoRefresh(childType-1, 0);
                }
            }
        };
        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                if(parentType == 1) {
                    if(sellIdList != null && sellIdList.size() > 0){
                        presenter.mySellinfoRefresh(childType, sellIdList.get(0));
                    }
                }else if(parentType == 2){
                    if(buyIdList != null && buyIdList.size() > 0){
                        presenter.myBuyinfoRefresh(childType, buyIdList.get(0));
                    }
                }else if(parentType == 3){
                    if(entrustIdList != null && entrustIdList.size() > 0){
                        presenter.myBillInfoRefresh(childType-1, entrustIdList.get(0));
                    }
                }
            }
        };
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG,"lazyLoadData");
        setLoadDataWhenVisible();
        //初始化presenter
        new OrderItemDetailsPresenter(this, new OrderItemDetailsModle());
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
    public void mySellinfoRefreshSuccess(MySellinfoResponse mySellinfoResponse) {
        List<MySellinfoItem> info = mySellinfoResponse.getInfo();
        GALogger.d(TAG,"info.size()    "+info.size());
        if (info != null && info.size() > 0) {
            GALogger.d(TAG,"info.size()    "+info.get(0).toString());
//            buyFragmentAdapter.setTotalCount(totalSize);
            orderSellDetailsAdapter.setList(info);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    @Override
    public void mySellinfoRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void mySellinfoLoadMoreSuccess(MySellinfoResponse mySellinfoResponse) {
        List<MySellinfoItem> info = mySellinfoResponse.getInfo();
        if (info != null && info.size() > 0) {
            if(sellIdList == null){
                sellIdList = new ArrayList<Long>();
            }
            sellIdList.clear();
            for (MySellinfoItem mySellinfoItem: info) {
                sellIdList.add(mySellinfoItem.getId());
            }
            Collections.reverse(sellIdList);
//            buyFragmentAdapter.setTotalCount(totalSize);
            orderSellDetailsAdapter.appendList(info);
        }
        isRun = false;
    }

    @Override
    public void mySellinfoLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void myBuyinfoRefreshSuccess(MyBuyinfoResponse myBuyinfoResponse) {
        List<MyBuyinfoItem> info = myBuyinfoResponse.getInfo();
        GALogger.d(TAG,"info.size()    "+info.size());
        if (info != null && info.size() > 0) {
//            buyFragmentAdapter.setTotalCount(totalSize);
            orderBuyDetailsAdapter.setList(info);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    @Override
    public void myBuyinfoRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void myBuyinfoLoadMoreSuccess(MyBuyinfoResponse myBuyinfoResponse) {
        List<MyBuyinfoItem> info = myBuyinfoResponse.getInfo();
        if (info != null && info.size() > 0) {
            if(buyIdList == null){
                buyIdList = new ArrayList<Long>();
            }
            buyIdList.clear();
            for (MyBuyinfoItem myBuyinfoItem: info) {
                buyIdList.add(myBuyinfoItem.getId());
            }
            Collections.reverse(buyIdList);
//            buyFragmentAdapter.setTotalCount(totalSize);
            orderBuyDetailsAdapter.appendList(info);
        }
        isRun = false;
    }

    @Override
    public void myBuyinfoLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void myBillInfoRefreshSuccess(MyEntrustinfoResponse myEntrustinfoResponse) {
        List<MyEntrustinfoItem> billInfo = myEntrustinfoResponse.getBillInfo();
        GALogger.d(TAG,"info.size()    "+billInfo.size());
        if (billInfo != null && billInfo.size() > 0) {
//            buyFragmentAdapter.setTotalCount(totalSize);
            orderEntrustDetailsAdapter.setList(billInfo);
        } else {
            emptyAnderrorView();
        }
        stopPulling();
    }

    @Override
    public void myBillInfoRefreshError() {
        stopPulling();
        emptyAnderrorView();
    }

    @Override
    public void myBillInfoLoadMoreSuccess(MyEntrustinfoResponse myEntrustinfoResponse) {
        List<MyEntrustinfoItem> billInfo = myEntrustinfoResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            if(entrustIdList == null){
                entrustIdList = new ArrayList<Long>();
            }
            entrustIdList.clear();
            for (MyEntrustinfoItem myEntrustinfoItem: billInfo) {
                entrustIdList.add(myEntrustinfoItem.getId());
            }
            Collections.reverse(entrustIdList);
//            buyFragmentAdapter.setTotalCount(totalSize);
            orderEntrustDetailsAdapter.appendList(billInfo);
        }
        isRun = false;
    }

    @Override
    public void myBillInfoLoadMoreError() {
        stopPulling();
        isRun = false;
    }

    @Override
    public void cancelSuccess(BaseBean baseBean) {
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
    }

    @Override
    public void appealSuccess(BaseBean baseBean) {
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
    }

    @Override
    public void ordercancelSuccess(BaseBean baseBean) {
        mRecyclerView.postDelayed(refreshAction, DEFAULT_TIME);
    }

    @Override
    public void setPresenter(OrderItemDetailsContract.Presenter presenter) {
        this.presenter = (OrderItemDetailsPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    private void emptyAnderrorView() {
        if(parentType == 1) {
            orderSellDetailsAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
            orderSellDetailsAdapter.showError(true);
        }else if(parentType == 2){
            orderBuyDetailsAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
            orderBuyDetailsAdapter.showError(true);
        }else if(parentType == 3){
            orderEntrustDetailsAdapter.setErrorView(LayoutInflater.from(MyApplication.appContext).inflate(R.layout.no_data_view, mRecyclerView, false));
            orderEntrustDetailsAdapter.showError(true);
        }
    }

    public void stopPulling() {
        if (orderPullRefreshRecycler != null) {
            orderPullRefreshRecycler.onRefreshComplete();
        }
    }

    @Override
    public void vv(final long id, final int type) {
        //带确认和取消按钮的弹窗
        new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
                // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        Log.e("tag", "onShow");
                    }
                    @Override
                    public void onDismiss() {
                        Log.e("tag", "onDismiss");
                    }
                }).asConfirm("撤销", "你确定要撤销吗?",
                "取消", "确定",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        presenter.cancel(id,type);
                    }
                }, null, false)
                .show();
    }

    @Override
    public void orderSellClick(final String tradeId) {
        //带确认和取消按钮的弹窗
        new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
                // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        Log.e("tag", "onShow");
                    }
                    @Override
                    public void onDismiss() {
                        Log.e("tag", "onDismiss");
                    }
                }).asConfirm("申诉", "你确定要申诉吗?",
                "取消", "确定",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        presenter.appeal(tradeId);
                    }
                }, null, false)
                .show();
    }

    @Override
    public void orderBuyClick(int type, final String tradeId) {//1:申诉  2:取消订单
        if(type == 1){
            //带确认和取消按钮的弹窗
            new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
                    // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                    .setPopupCallback(new XPopupCallback() {
                        @Override
                        public void onShow() {
                            Log.e("tag", "onShow");
                        }
                        @Override
                        public void onDismiss() {
                            Log.e("tag", "onDismiss");
                        }
                    }).asConfirm("申诉", "你确定要申诉吗?",
                    "取消", "确定",
                    new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            presenter.appeal(tradeId);
                        }
                    }, null, false)
                    .show();
        }else if(type == 2){
            //带确认和取消按钮的弹窗
            new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
                    // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                    .setPopupCallback(new XPopupCallback() {
                        @Override
                        public void onShow() {
                            Log.e("tag", "onShow");
                        }
                        @Override
                        public void onDismiss() {
                            Log.e("tag", "onDismiss");
                        }
                    }).asConfirm("订单取消", "你确定要取消此订单吗?",
                    "取消", "确定",
                    new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            presenter.ordercancel(tradeId);
                        }
                    }, null, false)
                    .show();
        }
    }
}
