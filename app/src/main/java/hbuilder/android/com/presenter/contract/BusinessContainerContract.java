package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BulletinListResponse;

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
