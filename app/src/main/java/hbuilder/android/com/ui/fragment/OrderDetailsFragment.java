package hbuilder.android.com.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.growalong.util.util.BitmapUtils;
import com.growalong.util.util.DateUtil;
import com.lxj.xpopup.XPopup;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.OrderDetailsModle;
import hbuilder.android.com.ui.activity.OrderDetailsActivity;
import hbuilder.android.com.ui.widget.CenterErWeiMaPopupView;
import hbuilder.android.com.util.ToastUtil;

public class OrderDetailsFragment extends BaseFragment {
    private static final String TAG = OrderDetailsFragment.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_order_details_code)
    TextView tvOrderDetailsCode;
    @BindView(R.id.tv_order_details_status)
    TextView tvOrderDetailsStatus;
    @BindView(R.id.tv_order_details_price)
    TextView tvOrderDetailsPrice;
    @BindView(R.id.tv_order_details_sell_name)
    TextView tvOrderDetailsSellName;
    @BindView(R.id.tv_order_details_singleprice)
    TextView tvOrderDetailsSingleprice;
    @BindView(R.id.tv_order_details_num)
    TextView tvOrderDetailsNum;
    @BindView(R.id.tv_order_details_time)
    TextView tvOrderDetailsTime;
    @BindView(R.id.tv_order_details_cankaoma)
    TextView tvOrderDetailsCankaoma;
    @BindView(R.id.tv_shoukuai_order_details_name)
    TextView tvShoukuaiOrderDetailsName;
    @BindView(R.id.tv_order_details_shoukuan_type)
    TextView tvOrderDetailsShoukuanType;
    @BindView(R.id.iv_order_details_code_image)
    ImageView ivOrderDetailsCodeImage;
    @BindView(R.id.tv_order_details_account)
    TextView tvOrderDetailsAccount;
    @BindView(R.id.tv_order_details_copy)
    TextView tvOrderDetailsCopy;
    @BindView(R.id.tv_order_details_allprice)
    TextView tvOrderDetailsAllprice;
    private OrderDetailsActivity orderDetailsActivity;
    private OrderDetailsModle orderDetailsModle;
    private Bitmap bitmap;

    public static OrderDetailsFragment newInstance(@Nullable OrderDetailsModle orderDetailsModle) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("orderDetailsModle", orderDetailsModle);
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderDetailsActivity = (OrderDetailsActivity) getActivity();
        orderDetailsModle = getArguments().getParcelable("orderDetailsModle");
    }

    @Override
    protected int getRootView() {
        return R.layout.order_details_fragment;
    }

    @Override
    protected void initView(View root) {
        tvTitle.setText("订单详情");
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        if(orderDetailsModle != null){
            tvOrderDetailsCode.setText(orderDetailsModle.getTradeId());
            int status = orderDetailsModle.getStatus();
            if(status == 1){
                tvOrderDetailsStatus.setText("等待付款");
            }else if(status == 2){
                tvOrderDetailsStatus.setText("等待放币");
            }else if(status == 10){
                tvOrderDetailsStatus.setText("完成");
            }else if(status == 20){
                tvOrderDetailsStatus.setText("申诉中");
            }else if(status == 30){
                tvOrderDetailsStatus.setText("超时取消");
            }else if(status == 40){
                tvOrderDetailsStatus.setText("已关闭");
            }
            tvOrderDetailsPrice.setText(MyApplication.appContext.getResources().getString(R.string.rmb)+new DecimalFormat("0.00").format(orderDetailsModle.getNum()*orderDetailsModle.getPrice()));
            tvOrderDetailsSellName.setText(orderDetailsModle.getName());
            tvOrderDetailsSingleprice.setText(MyApplication.appContext.getResources().getString(R.string.rmb)+new DecimalFormat("0.00").format(orderDetailsModle.getPrice()));
            tvOrderDetailsNum.setText(new DecimalFormat("0.00").format(orderDetailsModle.getNum()));
            tvOrderDetailsTime.setText(DateUtil.getCurrentDateString3(orderDetailsModle.getCreateTime()));
            tvOrderDetailsCankaoma.setText(orderDetailsModle.getPayCode()+"");
            tvShoukuaiOrderDetailsName.setText(orderDetailsModle.getName());
            if(orderDetailsModle.getType() == 1){
                tvOrderDetailsShoukuanType.setText("支付宝");
            }else if(orderDetailsModle.getType() == 2){
                tvOrderDetailsShoukuanType.setText("微信");
            }else if(orderDetailsModle.getType() == 3){
                tvOrderDetailsShoukuanType.setText("银行账户");
            }
            tvOrderDetailsAccount.setText(orderDetailsModle.getAccount());
            tvOrderDetailsAllprice.setText(MyApplication.appContext.getResources().getString(R.string.rmb)+new DecimalFormat("0.00").format(orderDetailsModle.getNum()*orderDetailsModle.getPrice()));
            ivOrderDetailsCodeImage.setImageBitmap(BitmapUtils.base64ToBitmap(orderDetailsModle.getBase64Img()));
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_order_details_code_image, R.id.tv_order_details_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                orderDetailsActivity.finish();
                break;
            case R.id.iv_order_details_code_image:
                bitmap = BitmapUtils.base64ToBitmap(orderDetailsModle.getBase64Img());
                new XPopup.Builder(getContext())
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .asCustom(new CenterErWeiMaPopupView(getContext(),orderDetailsModle.getType(),orderDetailsModle.getAccount(),bitmap))
                        .show();
                break;
            case R.id.tv_order_details_copy:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) orderDetailsActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", tvOrderDetailsAccount.getText().toString().trim());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtil.shortShow("已复制到剪贴板");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        super.onDestroyView();
    }
}
