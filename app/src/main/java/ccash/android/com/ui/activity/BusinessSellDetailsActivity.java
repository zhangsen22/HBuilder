package ccash.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import ccash.android.com.BaseActivity;
import ccash.android.com.R;
import ccash.android.com.modle.SellResponse;
import ccash.android.com.presenter.BusinessSellDetailsPresenter;
import ccash.android.com.presenter.modle.BusinessSellDetailsModle;
import ccash.android.com.ui.fragment.BusinessSellDetailsFragment;

public class BusinessSellDetailsActivity extends BaseActivity {
    private static final String TAG = ChangePwdActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity, SellResponse sellResponse,double price,double num,String nickname,int requestCode) {
        Intent intent = new Intent(activity, BusinessSellDetailsActivity.class);
        intent.putExtra("sellResponse",sellResponse);
        intent.putExtra("price",price);
        intent.putExtra("num",num);
        intent.putExtra("nickname",nickname);
        activity.startActivityForResult(intent,requestCode);
    }

    public static void startThis(BaseActivity activity, SellResponse sellResponse,double price,double num,String nickname) {
        Intent intent = new Intent(activity, BusinessSellDetailsActivity.class);
        intent.putExtra("sellResponse",sellResponse);
        intent.putExtra("price",price);
        intent.putExtra("num",num);
        intent.putExtra("nickname",nickname);
        activity.startActivity(intent);
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
        SellResponse sellResponse = getIntent().getParcelableExtra("sellResponse");
        double price = getIntent().getDoubleExtra("price",0);
        double num = getIntent().getDoubleExtra("num",0);
        String nickname = getIntent().getStringExtra("nickname");
        BusinessSellDetailsFragment businessSellDetailsFragment = (BusinessSellDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (businessSellDetailsFragment == null) {
            businessSellDetailsFragment = BusinessSellDetailsFragment.newInstance(sellResponse,price,num,nickname);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    businessSellDetailsFragment, R.id.contentFrame);
        }
        //初始化presenter
        new BusinessSellDetailsPresenter(businessSellDetailsFragment, new BusinessSellDetailsModle());
    }
}
