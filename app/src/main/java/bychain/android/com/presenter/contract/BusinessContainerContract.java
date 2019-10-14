package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BulletinListResponse;

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
