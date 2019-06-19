package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.RewardLogResponse;

public interface TradingAccountContract {
    interface Presenter extends IBasePresenter {
        //获取奖励记录
        void rewardLog();
    }
    interface View extends IBaseView<Presenter> {
        //获取奖励记录成功
        void rewardLogSuccess(RewardLogResponse rewardLogResponse);
    }
}
