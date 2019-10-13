package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.RewardDetailResponse;
import ccash.android.com.modle.RewardLogResponse;

public interface RewardDetailContract {

    interface Presenter extends IBasePresenter {
        //获取奖励记录
        void rewardLog();
        void rewardDetailRefresh(int type, long minId);
        void rewardDetailLoadMore(int type, long minId);
    }
    interface View extends IBaseView<Presenter> {
        //获取奖励记录成功
        void rewardLogSuccess(RewardLogResponse rewardLogResponse);
        void rewardDetailRefreshSuccess(RewardDetailResponse rewardDetailResponse);
        void rewardDetailRefreshError();
        void rewardDetailLoadMoreSuccess(RewardDetailResponse rewardDetailResponse);
        void rewardDetailLoadMoreError();
    }
}
