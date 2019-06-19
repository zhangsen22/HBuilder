package hbuilder.android.com.ui.adapter;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.DateUtil;
import com.growalong.util.util.GALogger;
import java.text.DecimalFormat;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.modle.MySellinfoItem;
import hbuilder.android.com.modle.SellResponse;
import hbuilder.android.com.ui.activity.BusinessSellDetailsActivity;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class OrderSellDetailsAdapter extends PowerAdapter<MySellinfoItem> {
    private static final String TAG = OrderSellDetailsAdapter.class.getSimpleName();
    private BaseActivity mContext;
    private int childType;
    private LayoutInflater inflater;

    public OrderSellDetailsAdapter(BaseActivity context, int childType,OrderSellClickListenering listenering) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.childType = childType;
        this.listenering = listenering;
    }

    @Override
    public PowerHolder<MySellinfoItem> onViewHolderCreate(@NonNull ViewGroup parent, int viewType) {
        return new OrderSellDetailsHolder(inflater.inflate(R.layout.order_sell_details_item, parent, false));
    }

    @Override
    public void onViewHolderBind(@NonNull PowerHolder<MySellinfoItem> holder, int position) {
        ((OrderSellDetailsHolder) holder).onBind(list.get(position), position);
    }

    private class OrderSellDetailsHolder extends PowerHolder<MySellinfoItem> {
        TextView tvOrderSellTime;
        TextView tvOrderSellStatus;
        TextView tvOrderSellPrice;
        TextView tvOrderSellAmount;
        TextView tvOrderSellPaycode;
        TextView tvOrderSellDjs;
        TextView tvOrderSellSstime;
        TextView tvOrderSellSs;
        TextView tvOrderSellQfb;
        LinearLayout llOrderSellButton;

        public OrderSellDetailsHolder(View itemView) {
            super(itemView);
            tvOrderSellTime = itemView.findViewById(R.id.tv_order_sell_time);
            tvOrderSellStatus = itemView.findViewById(R.id.tv_order_sell_status);
            tvOrderSellPrice = itemView.findViewById(R.id.tv_order_sell_price);
            tvOrderSellAmount = itemView.findViewById(R.id.tv_order_sell_amount);
            tvOrderSellPaycode = itemView.findViewById(R.id.tv_order_sell_paycode);
            tvOrderSellDjs = itemView.findViewById(R.id.tv_order_sell_djs);
            tvOrderSellSstime = itemView.findViewById(R.id.tv_order_sell_sstime);
            tvOrderSellSs = itemView.findViewById(R.id.tv_order_sell_ss);
            tvOrderSellQfb = itemView.findViewById(R.id.tv_order_sell_qfb);
            llOrderSellButton = itemView.findViewById(R.id.ll_order_sell_button);
        }

        @Override
        public void onBind(@NonNull final MySellinfoItem mySellinfoItem, int position) {
            long createTime = mySellinfoItem.getCreateTime();
            final long payTime = mySellinfoItem.getPayTime();
            tvOrderSellTime.setText(DateUtil.getCurrentDateString1(createTime));
            int status = mySellinfoItem.getStatus();
            if(status == 1){
                tvOrderSellStatus.setText("等待付款");
            }else if(status == 2){
                tvOrderSellStatus.setText("等待放币");
            }else if(status == 10){
                tvOrderSellStatus.setText("完成");
            }else if(status == 20){
                tvOrderSellStatus.setText("申诉中");
            }else if(status == 30){
                tvOrderSellStatus.setText("超时取消");
            }else if(status == 40){
                tvOrderSellStatus.setText("已关闭");
            }
            tvOrderSellPrice.setText(new DecimalFormat("0.00").format(mySellinfoItem.getPrice()));
            tvOrderSellAmount.setText(new DecimalFormat("0.00").format(mySellinfoItem.getPrice()*mySellinfoItem.getNum()));
            tvOrderSellPaycode.setText(new DecimalFormat("0.00").format(mySellinfoItem.getNum()));

            if(childType == 1){
                if(status == 1){
                    long currentTime = System.currentTimeMillis();
                    llOrderSellButton.setVisibility(View.GONE);
                    if(currentTime >= createTime + 10*60*1000){
                        tvOrderSellDjs.setVisibility(View.GONE);
                    }else {
                        CountDownTimer timer = new CountDownTimer(createTime + 10*60*1000 - currentTime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                int left = (int) ((millisUntilFinished - 1000) / 1000);
                                GALogger.d(TAG,"left       "+left);
                                if (left > 0) {
                                    tvOrderSellDjs.setVisibility(View.VISIBLE);
                                    tvOrderSellDjs.setText(DateUtil.getCurrentDateString2(millisUntilFinished)+"后取消订单");
                                } else {
                                    tvOrderSellDjs.setVisibility(View.GONE);
                                }
                            }
                            @Override
                            public void onFinish() {
                            }
                        };
                        timer.start();
                    }
                }else if(status == 2){
                    tvOrderSellDjs.setVisibility(View.GONE);
                    llOrderSellButton.setVisibility(View.VISIBLE);
                    long currentTime = System.currentTimeMillis();
                    if(currentTime >= payTime + 10*60*1000){
                        tvOrderSellSstime.setVisibility(View.GONE);
                        tvOrderSellSs.setEnabled(true);
                    }else {
                        CountDownTimer timer = new CountDownTimer(payTime + 10 * 61 * 1000 - currentTime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (mContext != null) {
                                    int left = (int) ((millisUntilFinished - 1000) / 1000);
                                    GALogger.d(TAG, "left       " + left);
                                    if (left > 0) {
                                        tvOrderSellSs.setEnabled(false);
                                        tvOrderSellSstime.setVisibility(View.VISIBLE);
                                        tvOrderSellSstime.setText(DateUtil.getCurrentDateString2(payTime + 10 * 60 * 1000) + "后可申诉");
                                    } else {
                                        tvOrderSellDjs.setVisibility(View.GONE);
                                        tvOrderSellSs.setEnabled(true);
                                    }
                                }
                            }
                            @Override
                            public void onFinish() {
                            }
                        };
                        timer.start();
                    }
                    tvOrderSellQfb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BusinessSellDetailsActivity.startThis(mContext,new SellResponse(mySellinfoItem.getTradeId(),mySellinfoItem.getPayCode(),mySellinfoItem.getCreateTime()),mySellinfoItem.getPrice(),mySellinfoItem.getNum(),AccountManager.getInstance().getNickname());
                        }
                    });
                    tvOrderSellSs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(listenering != null){
                                listenering.orderSellClick(mySellinfoItem.getTradeId());
                            }
                        }
                    });
                }else {
                    tvOrderSellDjs.setVisibility(View.GONE);
                    llOrderSellButton.setVisibility(View.GONE);
                }
            }else if(childType == 2){
                tvOrderSellDjs.setVisibility(View.GONE);
                llOrderSellButton.setVisibility(View.GONE);
            }else if(childType == 3){
                tvOrderSellDjs.setVisibility(View.GONE);
                llOrderSellButton.setVisibility(View.GONE);
            }else if(childType == 4){
                tvOrderSellDjs.setVisibility(View.GONE);
                llOrderSellButton.setVisibility(View.GONE);
            }
        }
    }

    private OrderSellClickListenering listenering;
    public interface OrderSellClickListenering{
        void orderSellClick(String tradeId);
    }
}
