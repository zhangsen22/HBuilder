package hbuilder.android.com.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import hbuilder.android.com.R;

/**
 * Description: 在中间的二维码对话框
 */
public class CenterErWeiMaPopupView extends CenterPopupView {

    ImageView ivImageCode;
    TextView tvAccount;
    TextView tvPayTypeName;
    TextView tvDespic;
    private int type;
    private String account;
    private Bitmap base64Img;

    public CenterErWeiMaPopupView(@NonNull Context context, int type, String account, Bitmap base64Img) {
        super(context);
        this.type = type;
        this.account = account;
        this.base64Img = base64Img;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.center_erwei_mapopupview;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        ivImageCode = findViewById(R.id.iv_image_code);
        tvAccount = findViewById(R.id.tv_account);
        tvPayTypeName = findViewById(R.id.tv_pay_type_name);
        tvDespic = findViewById(R.id.tv_despic);
        if(type == 1){ //收款方式,1为支付宝，2为微信，3为银行账户
            tvPayTypeName.setText("请用支付宝扫一扫");
        }else if(type == 2){
            tvPayTypeName.setText("请用微信扫一扫");
        }else if(type == 3){
            tvPayTypeName.setText("请用银联扫一扫");
        }
        if(!TextUtils.isEmpty(account)){
            tvAccount.setVisibility(VISIBLE);
            tvAccount.setText(account);
        }else {
            tvAccount.setVisibility(GONE);
            tvDespic.setText("扫描二维码验证");
        }
        ivImageCode.setImageBitmap(base64Img);
    }
}
