package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.WebChatListPresenter;
import hbuilder.android.com.presenter.modle.WebChatListModle;
import hbuilder.android.com.ui.fragment.WebChatListFragment;

public class WebChatListActivity extends BaseActivity {
    private static final String TAG = WebChatListActivity.class.getSimpleName();
    private WebChatListFragment webChatListFragment;

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, WebChatListActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_web_chat_list;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
    }

    @Override
    protected void initData() {
        webChatListFragment = (WebChatListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (webChatListFragment == null) {
            webChatListFragment = WebChatListFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    webChatListFragment, R.id.contentFrame);
        }
        //初始化presenter
        new WebChatListPresenter(webChatListFragment, new WebChatListModle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 102){
                webChatListFragment.onActivityResultF();
            }
        }
    }
}
