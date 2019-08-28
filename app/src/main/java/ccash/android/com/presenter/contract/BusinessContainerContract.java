package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BulletinListResponse;

public interface BusinessContainerContract {
    interface Presenter extends IBasePresenter {
        //公告
        void bulletinList();
    }
    interface View extends IBaseView<Presenter> {
        //获取公告成功
        void bulletinListSuccess(BulletinListResponse bulletinListResponse);
    }
}
