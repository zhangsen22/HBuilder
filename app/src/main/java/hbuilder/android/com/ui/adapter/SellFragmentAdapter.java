package hbuilder.android.com.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.growalong.util.util.GALogger;

import java.text.DecimalFormat;

import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.BuyItem;
import hbuilder.android.com.ui.activity.BusinessSellActivity;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class SellFragmentAdapter extends PowerAdapter<BuyItem> {

    private static final String TAG = SellFragmentAdapter.class.getSimpleName();
    private Context mContext;

    private LayoutInflater inflater;

    public SellFragmentAdapter(Context context) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public PowerHolder<BuyItem> onViewHolderCreate(@NonNull ViewGroup parent, int viewType) {
        return new BuyItemHolder(inflater.inflate(R.layout.buy_item, parent, false));
    }

    @Override
    public void onViewHolderBind(@NonNull PowerHolder<BuyItem> holder, int position) {
        ((BuyItemHolder) holder).onBind(list.get(position), position);
    }

    public class BuyItemHolder extends PowerHolder<BuyItem> {
        TextView tvNameFirst;
        TextView tvName;
        TextView tvTradetimes;
        TextView tvTradesuccrate;
        TextView tvNumber;
        TextView tvMinPrice;
        TextView tvMaxPrice;
        TextView tvSinglePrice;
        TextView tvBuy;

        public BuyItemHolder(View itemView) {
            super(itemView);
            tvNameFirst = itemView.findViewById(R.id.tv_name_first);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTradetimes = itemView.findViewById(R.id.tv_tradetimes);
            tvTradesuccrate = itemView.findViewById(R.id.tv_tradesuccrate);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvMinPrice = itemView.findViewById(R.id.tv_min_price);
            tvMaxPrice = itemView.findViewById(R.id.tv_max_price);
            tvSinglePrice = itemView.findViewById(R.id.tv_single_price);
            tvBuy = itemView.findViewById(R.id.tv_buy);
            tvNameFirst = itemView.findViewById(R.id.tv_name_first);
        }

        @Override
        public void onBind(@NonNull final BuyItem buyItem, int position) {
            if (buyItem != null) {
                GALogger.d(TAG, "buyItem    " + buyItem.toString());
                tvBuy.setText("出售");
                String nickname = buyItem.getNickname();
                if (!TextUtils.isEmpty(nickname)) {
                    tvNameFirst.setText(nickname.subSequence(0, 1));
                    tvName.setText(nickname);
                }

                tvTradetimes.setText(buyItem.getTradeTimes() + "");
                tvTradesuccrate.setText(buyItem.getTradeSuccRate() + "%");
                tvNumber.setText(buyItem.getMaxNum() + "");
                tvMinPrice.setText(MyApplication.appContext.getResources().getString(R.string.rmb)+new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMinNum()));
                tvMaxPrice.setText(MyApplication.appContext.getResources().getString(R.string.rmb)+new DecimalFormat("0.00").format(buyItem.getPrice() * buyItem.getMaxNum()));
                tvSinglePrice.setText(MyApplication.appContext.getResources().getString(R.string.rmb)+buyItem.getPrice());

                tvBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BusinessSellActivity.startThis(mContext,buyItem);
                    }
                });
            }
        }
    }
}
