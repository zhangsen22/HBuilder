package hbuilder.android.com.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.BulletinListItem;
import hbuilder.android.com.ui.activity.WebViewActivity;
import hbuilder.android.com.ui.adapter.poweradapter.PowerAdapter;
import hbuilder.android.com.ui.adapter.poweradapter.PowerHolder;

public class BulletinAdapter extends PowerAdapter<BulletinListItem> {

    private static final String TAG = BulletinAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater inflater;

    public BulletinAdapter(Context context) {
        super();
        isShowBottom = false;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public PowerHolder<BulletinListItem> onViewHolderCreate(@NonNull ViewGroup parent, int viewType) {
        return new InvitationHolder(inflater.inflate(R.layout.bulletin_item, parent, false));
    }

    @Override
    public void onViewHolderBind(@NonNull PowerHolder<BulletinListItem> holder, int position) {
        ((InvitationHolder) holder).onBind(list.get(position), position);
    }

    private class InvitationHolder extends PowerHolder<BulletinListItem> {
        TextView tvBulletinName;

        public InvitationHolder(View itemView) {
            super(itemView);
            tvBulletinName = itemView.findViewById(R.id.tv_bulletin_name);
        }

        @Override
        public void onBind(@NonNull BulletinListItem bulletinListItem, int position) {
            if(bulletinListItem != null){
                tvBulletinName.setText(bulletinListItem.getTitle());
                tvBulletinName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebViewActivity.launchVerifyCode(MyApplication.appContext, bulletinListItem.getBodyUrl(), true);
                    }
                });
            }
        }
    }
}
