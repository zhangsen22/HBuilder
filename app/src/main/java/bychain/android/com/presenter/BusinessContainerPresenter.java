package bychain.android.com.presenter;

import bychain.android.com.modle.BulletinListResponse;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.BusinessContainerContract;
import bychain.android.com.presenter.modle.BusinessContainerModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class BusinessContainerPresenter implements BusinessContainerContract.Presenter{
    private BusinessContainerContract.View mView;
    private BusinessContainerModle mModel;

    public BusinessContainerPresenter(BusinessContainerContract.View view, BusinessContainerModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void bulletinList() {
        mModel.bulletinList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BulletinListResponse>() {
                    @Override
                    public void onSuccess(BulletinListResponse bulletinListResponse) {
                        mView.bulletinListSuccess(bulletinListResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
