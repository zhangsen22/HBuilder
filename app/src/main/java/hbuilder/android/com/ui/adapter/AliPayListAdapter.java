package hbuilder.android.com.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import java.util.HashMap;
import java.util.Map;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.AliPayPayeeItemModel;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class AliPayListAdapter extends PowerAdapter<AliPayPayeeItemModel> {
    private static final String TAG = MessageCenterAdapter.class.getSimpleName();
    private Map<Integer, Boolean> map = new HashMap<>();
    private boolean onBind;
    private int checkedPosition = -1;
    private Context mContext;
    private LayoutInflater inflater;
    private OnAliPayCheckListener onAliPayCheckListener;

    public AliPayListAdapter(Context context,OnAliPayCheckListener onAliPayCheckListener) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.onAliPayCheckListener = onAliPayCheckListener;
    }

    //得到当前选中的位置
    public int getCheckedPosition() {
        return checkedPosition;
    }

    @Override
    public PowerHolder<AliPayPayeeItemModel> onViewHolderCreate(@NonNull ViewGroup parent, int viewType) {
        return new AliPayListHolder(inflater.inflate(R.layout.ali_pay_list_item, parent, false));
    }

    @Override
    public void onViewHolderBind(@NonNull PowerHolder<AliPayPayeeItemModel> holder, int position) {
        ((AliPayListHolder) holder).onBind(list.get(position), position);
    }

    private class AliPayListHolder extends PowerHolder<AliPayPayeeItemModel> {
        TextView tvAlipayName;
        CheckBox tvAlipayCheck;
        TextView tvAlipayLastmoney;
        TextView tvAlipayLastnum;
        public AliPayListHolder(View itemView) {
            super(itemView);
            tvAlipayName = itemView.findViewById(R.id.tv_alipay_name);
            tvAlipayCheck = itemView.findViewById(R.id.tv_alipay_check);
            tvAlipayLastmoney = itemView.findViewById(R.id.tv_alipay_lastmoney);
            tvAlipayLastnum = itemView.findViewById(R.id.tv_alipay_lastnum);
        }

        @Override
        public void onBind(@NonNull final AliPayPayeeItemModel aliPayPayeeItemModel, final int position) {
            GALogger.d(TAG,"position           "+position);
            tvAlipayName.setText(position+"");
            tvAlipayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    GALogger.d(TAG,"isChecked           "+isChecked);
                    if (isChecked == true) {
                        map.clear();
                        map.put(position, true);
                        checkedPosition = position;
                        if(onAliPayCheckListener != null){
                            onAliPayCheckListener.onAliPayCheck(position,aliPayPayeeItemModel);
                        }
                    } else {
                        map.remove(position);
                        if (map.size() == 0) {
                            checkedPosition = -1; //-1 代表一个都未选择
                        }
                    }
                    if (!onBind) {
                        notifyDataSetChanged();
                    }
                }
            });
            onBind = true;
            if (map != null && map.containsKey(position)) {
                tvAlipayCheck.setChecked(true);
            } else {
                tvAlipayCheck.setChecked(false);
            }
            onBind = false;
        }
    }

    public interface OnAliPayCheckListener{
        void onAliPayCheck(int position,AliPayPayeeItemModel aliPayPayeeItemModel);
    }
}
