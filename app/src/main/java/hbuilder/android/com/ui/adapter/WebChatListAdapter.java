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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.WeChatPayeeItemModel;
import hbuilder.android.com.modle.WeChatPayeeItemModelPayee;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class WebChatListAdapter extends PowerAdapter<WeChatPayeeItemModel> {
    private static final String TAG = MessageCenterAdapter.class.getSimpleName();
    private Map<Integer, Boolean> map = new HashMap<>();
    private boolean onBind;
    private int checkedPosition = -1;
    private Context mContext;
    private LayoutInflater inflater;
    private OnWebChatCheckListener onWebChatCheckListener;
    private long defalutId;

    public WebChatListAdapter(Context context, OnWebChatCheckListener onWebChatCheckListener) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.onWebChatCheckListener = onWebChatCheckListener;
    }

    @Override
    public PowerHolder<WeChatPayeeItemModel> onViewHolderCreate(@NonNull ViewGroup parent, int viewType) {
        return new WebChatListHolder(inflater.inflate(R.layout.web_chat_pay_item_view, parent, false));
    }

    @Override
    public void onViewHolderBind(@NonNull PowerHolder<WeChatPayeeItemModel> holder, int position) {
        ((WebChatListHolder) holder).onBind(list.get(position), position);
    }

    //得到当前选中的位置
    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setDefaultId(long defalut) {
        this.defalutId = defalut;
    }

    private class WebChatListHolder extends PowerHolder<WeChatPayeeItemModel> {
        TextView tvWebchatCode;
        CheckBox tvWebchatCheck;
        TextView tvWebchatLastmoney;
        TextView tvWebchatLastnum;
        TextView tvShuoming;
        TextView tvPayDelete;
        TextView tvWebchatName;

        public WebChatListHolder(View itemView) {
            super(itemView);
            tvWebchatCode = itemView.findViewById(R.id.tv_webchat_code);
            tvWebchatCheck = itemView.findViewById(R.id.tv_webchat_check);
            tvWebchatLastmoney = itemView.findViewById(R.id.tv_webchat_lastmoney);
            tvWebchatLastnum = itemView.findViewById(R.id.tv_webchat_lastnum);
            tvShuoming = itemView.findViewById(R.id.tv_shuoming);
            tvPayDelete = itemView.findViewById(R.id.tv_pay_delete);
            tvWebchatName = itemView.findViewById(R.id.tv_webchat_name);
        }

        @Override
        public void onBind(@NonNull final WeChatPayeeItemModel weChatPayeeItemModel, final int position) {
            GALogger.d(TAG, "position           " + position);
            WeChatPayeeItemModelPayee payee = weChatPayeeItemModel.getPayee();
            if(payee != null){
                tvWebchatCode.setText(payee.getAccount());
                tvWebchatName.setText(payee.getName());
                if (defalutId > 0) {
                    if (defalutId == payee.getId()) {
                        map.clear();
                        map.put(position, true);
                        checkedPosition = position;
                        tvShuoming.setText("默认");
                        tvWebchatCheck.setVisibility(View.GONE);
                        tvShuoming.setTextColor(mContext.getResources().getColor(R.color.color_ff0000));
                    } else {
                        map.remove(position);
                        if (map.size() == 0) {
                            checkedPosition = -1; //-1 代表一个都未选择
                        }
                        tvShuoming.setText("设为默认");
                        tvWebchatCheck.setVisibility(View.VISIBLE);
                        tvShuoming.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                    }
                }
            }
            tvWebchatLastmoney.setText(new DecimalFormat("0.00").format(weChatPayeeItemModel.getLeftMoney()));
            tvWebchatLastnum.setText(weChatPayeeItemModel.getLeftTimes() + "");
            tvPayDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onWebChatCheckListener != null) {
                        onWebChatCheckListener.onWebChatDelete(position, weChatPayeeItemModel);
                    }
                }
            });
            tvWebchatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()) {
                        GALogger.d(TAG, "isChecked           " + isChecked);
                        if (isChecked == true) {
                            map.clear();
                            map.put(position, true);
                            checkedPosition = position;
                            if (onWebChatCheckListener != null) {
                                onWebChatCheckListener.onWebChatCheck(position, weChatPayeeItemModel);
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
                }
            });
            onBind = true;
            if (map != null && map.containsKey(position)) {
                tvWebchatCheck.setChecked(true);
            } else {
                tvWebchatCheck.setChecked(false);
            }
            onBind = false;
        }
    }

    public interface OnWebChatCheckListener {
        void onWebChatCheck(int position, WeChatPayeeItemModel weChatPayeeItemModel);
        void onWebChatDelete(int position, WeChatPayeeItemModel weChatPayeeItemModel);
    }
}
