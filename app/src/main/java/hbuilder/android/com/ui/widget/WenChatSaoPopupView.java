package hbuilder.android.com.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;

import hbuilder.android.com.R;

/**
 * Description: 微信收款设置弹窗
 */
public class WenChatSaoPopupView extends CenterPopupView {


    public WenChatSaoPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.webchat_sao_mapopupview;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
    }
}
