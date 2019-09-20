package ccash.android.com.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.growalong.util.util.DensityUtil;
import com.growalong.util.util.GALogger;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.app.Constants;
import ccash.android.com.modle.BulletinListItem;
import ccash.android.com.modle.BulletinListResponse;
import ccash.android.com.modle.LargeAmountItem;
import ccash.android.com.modle.LargeAmountResponse;
import ccash.android.com.presenter.BusinessContainerPresenter;
import ccash.android.com.presenter.contract.BusinessContainerContract;
import ccash.android.com.presenter.modle.BusinessContainerModle;
import ccash.android.com.ui.activity.BulletinActivity;
import ccash.android.com.ui.activity.GuaDanActivity;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.WebViewActivity;
import ccash.android.com.ui.adapter.BusinessViewPagerAdapter;
import ccash.android.com.util.SharedPreferencesUtils;

public class BusinessContainerFragment extends BaseFragment implements BusinessContainerContract.View {
    private static final String TAG = BusinessContainerFragment.class.getSimpleName();
    @BindView(R.id.business_magicindicator)
    MagicIndicator businessMagicindicator;
    @BindView(R.id.business_viewPager)
    ViewPager businessViewPager;
    @BindView(R.id.ll_notry_click)
    LinearLayout llNotryClick;
    @BindView(R.id.ff_business_content)
    LinearLayout ffBusinessContent;
    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.ll_qiangdan)
    LinearLayout llQiangdan;
    @BindView(R.id.iv_tixing)
    ImageView ivTixing;
    private MainActivity mainActivity;
    private BusinessViewPagerAdapter baseFragmentPagerAdapter;
    private BusinessContainerPresenter presenter;
    private ArrayList<BulletinListItem> bulletinList;
    public HashMap<Long, Long> idMap;
    public List<Long> idList;
    private SoundPool soundPool;
    private boolean isOnCreate = false;

    public static BusinessContainerFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        BusinessContainerFragment fragment = new BusinessContainerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GALogger.d(TAG, "onCreate ......");
        mainActivity = (MainActivity) getActivity();
        isOnCreate = true;
    }

    @Override
    protected int getRootView() {
        return R.layout.business_container_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "BusinessContainerFragment   is    initView");
        setRootViewPaddingTop(ffBusinessContent);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(mainActivity, R.raw.a, 1);
        initBanner();
        final String[] businessTitle = mainActivity.getResources().getStringArray(R.array.business_title);
        businessViewPager.setOffscreenPageLimit(businessTitle.length - 1);
        baseFragmentPagerAdapter = new BusinessViewPagerAdapter(getChildFragmentManager(), businessTitle);
        businessViewPager.setAdapter(baseFragmentPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(mainActivity);
//        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return businessTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(businessTitle[index]);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = businessViewPager.getCurrentItem();
                        if (currentItem != index) {
                            businessViewPager.setCurrentItem(index);
                        } else {

                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(DensityUtil.dip2px(MyApplication.appContext, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setColors(Color.parseColor("#FFCF20"));
                indicator.setYOffset(UIUtil.dip2px(context, 8));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });

        businessMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(businessMagicindicator, businessViewPager);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        GALogger.d(TAG, "lazyLoadData ......");
        initLoaclData();
        //初始化presenter
        new BusinessContainerPresenter(this, new BusinessContainerModle());
        presenter.bulletinList();
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                presenter.getHugeBillinfoRefresh(0);
            }
        }, 500);
        int currentItem = businessViewPager.getCurrentItem();
        if (baseFragmentPagerAdapter != null) {
            BaseFragment currentFragment = baseFragmentPagerAdapter.getCurrentFragment(currentItem);
            if (currentFragment != null && currentFragment.isVisible()) {
                currentFragment.lazyLoadData();
            }
        }
    }

    @OnClick({R.id.ll_notry_click, R.id.ll_qiangdan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_notry_click:
                if (bulletinList != null && bulletinList.size() > 0) {
                    BulletinActivity.startThis(mainActivity, bulletinList);
                }
                break;
            case R.id.ll_qiangdan:
                if(ivTixing.getVisibility() == View.VISIBLE){
                    ivTixing.setVisibility(View.GONE);
                }
                GuaDanActivity.startThis(mainActivity);
                break;
        }
    }

    private void initBanner() {
        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.mipmap.banner);
        imgs.add(R.mipmap.banner1);
        banner.setHorizontalScrollBarEnabled(false);
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {

            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, imgs).setPageIndicator(new int[]{R.drawable.shape_banner_no, R.drawable.shape_banner_yes})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT).startTurning(5000)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 1) {
                            WebViewActivity.launchVerifyCode(MyApplication.appContext, "https://nxotc-sttatic.oss-cn-hongkong.aliyuncs.com/4a09af564b724451a35446dce03899d2.html", true);
                        }
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
    public void getHugeBillinfoRefreshSuccess(LargeAmountResponse largeAmountResponse) {
        List<LargeAmountItem> billInfo = largeAmountResponse.getBillInfo();
        if (billInfo != null && billInfo.size() > 0) {
            for (int i = 0; i < billInfo.size(); i++) {
                LargeAmountItem buyItem = billInfo.get(i);
                if (buyItem != null && buyItem.getId() > 0) {
                    idList.add(buyItem.getId());
                }
            }

            if (idList != null && idList.size() > 0) {
                for (int i = 0; i < idList.size(); i++) {
                    boolean b = idMap.containsKey(idList.get(i));
                    if (!b) {
                        //说明有新单
                        soundPool.play(1, 1, 1, 0, 0, 1);
                        ivTixing.setVisibility(View.VISIBLE);
                        SharedPreferencesUtils.putObject(Constants.DAEQIANGDANLIST, idList);
                    }
                }
            }
        } else {
            SharedPreferencesUtils.remove(Constants.DAEQIANGDANLIST);
        }

        if (idMap != null) {
            idMap.clear();
        }
        if (idList != null) {
            idList.clear();
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
        isOnCreate = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        GALogger.d(TAG, "onResume ......");
        if(!isOnCreate){
            initLoaclData();
            presenter.getHugeBillinfoRefresh(0);
        }
        super.onResume();
    }

    private void initLoaclData() {
        if (idMap == null) {
            idMap = new HashMap<>();
        }
        idMap.clear();
        if (SharedPreferencesUtils.has(Constants.DAEQIANGDANLIST)) {
            List<Long> object = SharedPreferencesUtils.getObject(Constants.DAEQIANGDANLIST, new TypeToken<List<Long>>() {
            }.getType());
            if (object != null && object.size() > 0) {
                for (int i = 0; i < object.size(); i++) {
                    idMap.put(object.get(i), object.get(i));
                }
            }
        }

        if (idList == null) {
            idList = new ArrayList<>();
        }
        idList.clear();
    }
}
