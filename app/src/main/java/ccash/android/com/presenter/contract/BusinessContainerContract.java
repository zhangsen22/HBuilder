package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BulletinListResponse;
import ccash.android.com.modle.LargeAmountResponse;

public interface BusinessContainerContract {
    interface Presenter extends IBasePresenter {
        //公告
        void bulletinList();
        void getHugeBillinfoRefresh(long minId);
    }
    interface View extends IBaseView<Presenter> {
        //获取公告成功
        void bulletinListSuccess(BulletinListResponse bulletinListResponse);
        void getHugeBillinfoRefreshSuccess(LargeAmountResponse largeAmountResponse);
    }
}
