package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BaseBean;

public interface IdentityContract {

    interface Presenter extends IBasePresenter {
        //身份认证
        void idCheck(int type,String name,int sex,String birthday,String IDNumber,String IDImageFront,String IDImageBehind,String IDImageWithUser);
    }
    interface View extends IBaseView<Presenter> {
        //身份认证  成功
        void idCheckSuccess(BaseBean baseBean);
    }
}
