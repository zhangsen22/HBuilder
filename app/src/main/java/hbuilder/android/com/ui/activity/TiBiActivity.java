package hbuilder.android.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.qrcode.Constant;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.TiBiPresenter;
import hbuilder.android.com.presenter.modle.TiBiModle;
import hbuilder.android.com.ui.fragment.TiBiFragment;

public class TiBiActivity extends BaseActivity {
    private static final String TAG = TiBiActivity.class.getSimpleName();
    public static final int REQUEST_PERMISION_CODE_CAMARE = 0;
    private TiBiFragment tiBiFragment;

    public static void startThis(Context context) {
        context.startActivity(new Intent(context, TiBiActivity.class));
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
        tiBiFragment = (TiBiFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (tiBiFragment == null) {
            tiBiFragment = TiBiFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tiBiFragment, R.id.contentFrame);
        }
        //初始化presenter
        new TiBiPresenter(tiBiFragment, new TiBiModle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (data == null) return;
                    tiBiFragment.onActivityResultF(requestCode,resultCode,data);
                    break;
                default:
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
