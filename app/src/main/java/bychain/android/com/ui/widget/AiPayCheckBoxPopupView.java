package bychain.android.com.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import bychain.android.com.R;

/**
 * Description: 支付宝首款设置提示弹框
 */
public class AiPayCheckBoxPopupView extends CenterPopupView {

    private TextView tvOkSao;
    OnConfirmListener confirmListener;

    public AiPayCheckBoxPopupView(@NonNull Context context, OnConfirmListener confirmListener) {
        super(context);
        this.confirmListener = confirmListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.aipay_check_mapopupview;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tvOkSao = findViewById(R.id.tv_ok_sao);
        tvOkSao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmListener!=null)confirmListener.onConfirm();
                dismiss();
            }
        });
    }
}
