package hbuilder.android.com.ui.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.DateUtil;
import java.text.DecimalFormat;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.FinanceLogItem;
import hbuilder.android.com.ui.activity.ChongBiActivity;
import hbuilder.android.com.ui.activity.RansferOfFundsActivity;
import hbuilder.android.com.ui.activity.TiBiActivity;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class WalletAccountAdapter extends PowerAdapter<FinanceLogItem> {
    private static final String TAG = WalletAccountAdapter.class.getSimpleName();
    private BaseActivity mContext;
    private LayoutInflater inflater;

    public WalletAccountAdapter(BaseActivity context) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        isAddHeadView = true;
    }

    @Override
    public PowerHolder<FinanceLogItem> onViewHolderCreate(@NonNull ViewGroup parent, int viewType) {
        PowerHolder holder = null;
        switch (viewType) {
            case ITEM_VIEW_HEAD:
                holder = new WalletAccountHeadHolder(inflater.inflate(R.layout.wallet_account_heads, parent, false));
                break;
            case ITEM_VIEW_TYPE_ITEM:
                holder = new WalletAccountContentHolder(inflater.inflate(R.layout.wallet_account_content, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onViewHolderBind(@NonNull PowerHolder<FinanceLogItem> holder, int position) {
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            case ITEM_VIEW_HEAD:
                ((WalletAccountHeadHolder) holder).onBind();
                break;
            case ITEM_VIEW_TYPE_ITEM:
                ((WalletAccountContentHolder) holder).onBind(list.get(position), position);
                break;
        }
    }

    private class WalletAccountHeadHolder extends PowerHolder<FinanceLogItem> {
        private LinearLayout llPropertyCb;
        private LinearLayout llPropertyTb;
        private LinearLayout llPropertyHz;
        public WalletAccountHeadHolder(View itemView) {
            super(itemView);
            llPropertyCb = itemView.findViewById(R.id.ll_property_cb);
            llPropertyTb = itemView.findViewById(R.id.ll_property_tb);
            llPropertyHz = itemView.findViewById(R.id.ll_property_hz);
        }

        public void onBind() {
            llPropertyCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChongBiActivity.startThis(mContext);
                }
            });
            llPropertyTb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TiBiActivity.startThis(mContext);
                }
            });
            llPropertyHz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RansferOfFundsActivity.startThis(mContext,1,Constants.REQUESTCODE_11);
                }
            });
        }
    }

    private class WalletAccountContentHolder extends PowerHolder<FinanceLogItem> {
        ImageView ivImageType;
        TextView tvTextType;
        TextView tvAmountNum;
        TextView tvAmountStatus;
        TextView tvAmountTime;

        public WalletAccountContentHolder(View itemView) {
            super(itemView);
            ivImageType = itemView.findViewById(R.id.iv_image_type);
            tvTextType = itemView.findViewById(R.id.tv_text_type);
            tvAmountNum = itemView.findViewById(R.id.tv_amount_num);
            tvAmountStatus = itemView.findViewById(R.id.tv_amount_status);
            tvAmountTime = itemView.findViewById(R.id.tv_amount_time);
        }

        public void onBind(@NonNull FinanceLogItem financeLogItem, int position) {
            int type = financeLogItem.getType();
            if (type == 1 || type == 2) {
                ivImageType.setImageResource(R.mipmap.v);
            } else if (type == 3 || type == 4) {
                ivImageType.setImageResource(R.mipmap.w);
            }
            double num = financeLogItem.getNum();
            tvAmountNum.setText(new DecimalFormat("0.00").format(num));
            int status = financeLogItem.getStatus();
            if (status == 1) {
                tvAmountStatus.setText("确认中");
            } else if (status == 2) {
                tvAmountStatus.setText("已完成");
            } else if (status == 3) {
                tvAmountStatus.setText("取消");
            }
            if (type == 1) {
                tvTextType.setText("充币");
            } else if (type == 2) {
                tvTextType.setText("提币");
            } else if (type == 3) {
                tvTextType.setText("转入到钱包");
            } else if (type == 4) {
                tvTextType.setText("转出到交易账户");
            }
            long logtime = financeLogItem.getLogtime();
            long succtime = financeLogItem.getSucctime();
            if (succtime > 0) {
                tvAmountTime.setText(DateUtil.getCurrentDateString(succtime));
            } else {
                tvAmountTime.setText(DateUtil.getCurrentDateString(logtime));
            }
        }
    }
}
