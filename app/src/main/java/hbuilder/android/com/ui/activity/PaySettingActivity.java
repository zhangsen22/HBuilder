package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.AliPayEditPresenter;
import hbuilder.android.com.presenter.IdCastPresenter;
import hbuilder.android.com.presenter.WebChatEditPresenter;
import hbuilder.android.com.presenter.modle.PaySettingModle;
import hbuilder.android.com.ui.fragment.AliPayEditFragment;
import hbuilder.android.com.ui.fragment.IdCastPayEditFragment;
import hbuilder.android.com.ui.fragment.WebChatEditFragment;

public class PaySettingActivity extends BaseActivity {
    private static final String TAG = PaySettingActivity.class.getSimpleName();
    private WebChatEditFragment webChatEditFragment;
    private AliPayEditFragment aliPayEditFragment;

    public static void startThis(BaseActivity activity,int type,int requestCode) {
        Intent intent = new Intent(activity, PaySettingActivity.class);
        intent.putExtra("type",type);
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_content;
    }

    @Override
    protected void initView(View mRootView) {
    }

    @Override
    protected void initData() {
        int type = getIntent().getIntExtra("type", 1);
        if(type == 1){//1为支付宝，2为微信，3为银行账户
            aliPayEditFragment = (AliPayEditFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.contentFrame);
            if (aliPayEditFragment == null) {
                aliPayEditFragment = AliPayEditFragment.newInstance("");
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        aliPayEditFragment, R.id.contentFrame);
            }
            //初始化presenter
            new AliPayEditPresenter(aliPayEditFragment, new PaySettingModle());
        }else if(type == 2){
            webChatEditFragment = (WebChatEditFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.contentFrame);
            if (webChatEditFragment == null) {
                webChatEditFragment = WebChatEditFragment.newInstance("");
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        webChatEditFragment, R.id.contentFrame);
            }
            //初始化presenter
            new WebChatEditPresenter(webChatEditFragment, new PaySettingModle());
        }else if(type == 3){
            IdCastPayEditFragment idCastPayEditFragment = (IdCastPayEditFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.contentFrame);
            if (idCastPayEditFragment == null) {
                idCastPayEditFragment = IdCastPayEditFragment.newInstance("");
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        idCastPayEditFragment, R.id.contentFrame);
            }
            //初始化presenter
            new IdCastPresenter(idCastPayEditFragment, new PaySettingModle());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    if (data == null) return;
                    webChatEditFragment.onActivityResultF(requestCode,resultCode,data);
                    break;
                case 101:
                    if (data == null) return;
                    aliPayEditFragment.onActivityResultF(requestCode,resultCode,data);
                    break;
                default:
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
