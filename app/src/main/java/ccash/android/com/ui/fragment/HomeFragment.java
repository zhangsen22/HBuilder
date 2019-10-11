package ccash.android.com.ui.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
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
import com.google.gson.reflect.TypeToken;
import com.growalong.util.util.GALogger;
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
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.WebViewActivity;
import ccash.android.com.util.SharedPreferencesUtils;

public class HomeFragment extends BaseFragment implements BusinessContainerContract.View {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.ff_business_content)
    LinearLayout ffBusinessContent;
    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    private MainActivity mainActivity;
    private BusinessContainerPresenter presenter;
    private ArrayList<BulletinListItem> bulletinList;
    public HashMap<Long, Long> idMap;
    public List<Long> idList;
    private SoundPool soundPool;

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
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(mainActivity, R.raw.a, 1);
        initBanner();
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        GALogger.d(TAG, "lazyLoadData ......");
        //初始化presenter
        new BusinessContainerPresenter(this, new BusinessContainerModle());
        presenter.bulletinList();
        MyApplication.applicationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GALogger.d(TAG, "postDelayed ......");
                initLoaclData();
                presenter.getHugeBillinfoRefresh(0);
                MyApplication.applicationHandler.postDelayed(this, 10000);
            }
        },500);
    }

    @OnClick({R.id.ll_notry_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_notry_click:
                if (bulletinList != null && bulletinList.size() > 0) {
                    BulletinActivity.startThis(mainActivity, bulletinList);
                }
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
        super.onPause();
    }

    @Override
    public void onResume() {
        GALogger.d(TAG, "onResume ......");
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

    @Override
    public void onDestroyView() {
        MyApplication.applicationHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }
}
