package bychain.android.com.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.growalong.util.util.GALogger;
import com.lxj.xpopup.XPopup;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.MyApplication;
import bychain.android.com.R;
import bychain.android.com.modle.BulletinListItem;
import bychain.android.com.modle.BulletinListResponse;
import bychain.android.com.presenter.BusinessContainerPresenter;
import bychain.android.com.presenter.contract.BusinessContainerContract;
import bychain.android.com.presenter.modle.BusinessContainerModle;
import bychain.android.com.ui.activity.BulletinActivity;
import bychain.android.com.ui.activity.ChongBiActivity;
import bychain.android.com.ui.activity.MainActivity;
import bychain.android.com.ui.activity.WebViewActivity;
import bychain.android.com.ui.widget.KeFuPopupView;

public class HomeFragment extends BaseFragment implements BusinessContainerContract.View {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.ff_business_content)
    LinearLayout ffBusinessContent;
    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.iv_kefu)
    ImageView ivKefu;
    @BindView(R.id.ll_chongbi)
    LinearLayout llChongbi;
    @BindView(R.id.ll_home1)
    LinearLayout llHome1;
    @BindView(R.id.ll_home2)
    LinearLayout llHome2;
    @BindView(R.id.ll_home3)
    LinearLayout llHome3;
    @BindView(R.id.ll_home4)
    LinearLayout llHome4;
    @BindView(R.id.ll_home5)
    LinearLayout llHome5;
    private MainActivity mainActivity;
    private BusinessContainerPresenter presenter;
    private ArrayList<BulletinListItem> bulletinList;

    public static HomeFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GALogger.d(TAG, "onCreate ......");
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "BusinessContainerFragment   is    initView");
        setRootViewPaddingTop(ffBusinessContent);
        initBanner();
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        GALogger.d(TAG, "lazyLoadData ......");
        //初始化presenter
        new BusinessContainerPresenter(this, new BusinessContainerModle());
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                presenter.bulletinList();
            }
        },300);
    }

    @OnClick({R.id.ll_notry_click, R.id.iv_kefu, R.id.ll_chongbi,R.id.ll_home1,R.id.ll_home2,R.id.ll_home3,R.id.ll_home4,R.id.ll_home5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_notry_click:
                if (bulletinList != null && bulletinList.size() > 0) {
                    BulletinActivity.startThis(mainActivity, bulletinList);
                }
                break;
            case R.id.iv_kefu:
                new XPopup.Builder(mainActivity)
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .asCustom(new KeFuPopupView(mainActivity)).show();
                break;
            case R.id.ll_chongbi:
                ChongBiActivity.startThis(mainActivity);
                break;
            case R.id.ll_home1:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, "https://m.bychain.net/#/chainFirst", true);
                break;
            case R.id.ll_home2:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, "https://m.bychain.net/#/chainSecond", true);
                break;
            case R.id.ll_home3:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, "https://m.bychain.net/#/chainThird", true);
                break;
            case R.id.ll_home4:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, "https://m.bychain.net/#/question3", true);
                break;
            case R.id.ll_home5:
                WebViewActivity.launchVerifyCode(MyApplication.appContext, "https://m.bychain.net/#/question4", true);
                break;
        }
    }

    private void initBanner() {
        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.mipmap.banner);
        imgs.add(R.mipmap.banner1);
        imgs.add(R.mipmap.banner2);
        banner.setHorizontalScrollBarEnabled(false);
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {

            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, imgs).setPageIndicator(new int[]{R.drawable.shape_banner_no, R.drawable.shape_banner_yes})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).startTurning(5000)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
    }

    @Override
    public void bulletinListSuccess(BulletinListResponse bulletinListResponse) {
        if (bulletinListResponse != null) {
            if (bulletinList == null) {
                bulletinList = new ArrayList<>();
            }
            bulletinList.clear();
            List<BulletinListItem> list = bulletinListResponse.getList();
            if (list != null && list.size() > 0) {
                bulletinList.addAll(list);
                BulletinListItem bulletinListItem = list.get(0);
                if (bulletinListItem != null) {
                    String title = bulletinListItem.getTitle();
                    if (!TextUtils.isEmpty(title)) {
                        tvGonggao.setText(title);
                    }
                }
            }
        }
    }

    @Override
    public void setPresenter(BusinessContainerContract.Presenter presenter) {
        this.presenter = (BusinessContainerPresenter) presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    @Override
    public void onPause() {
        GALogger.d(TAG, "onPause ......");
        super.onPause();
    }

    @Override
    public void onResume() {
        GALogger.d(TAG, "onResume ......");
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
