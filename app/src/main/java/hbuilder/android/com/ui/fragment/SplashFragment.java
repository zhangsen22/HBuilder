package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.growalong.util.util.GALogger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.AccountInfo;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.presenter.LoginPresenter;
import hbuilder.android.com.presenter.contract.LoginContract;
import hbuilder.android.com.ui.activity.LoginActivity;
import hbuilder.android.com.ui.activity.MainActivity;
import hbuilder.android.com.ui.activity.SplashActivity;

public class SplashFragment extends BaseFragment implements LoginContract.View {
    private static final String TAG = SplashFragment.class.getSimpleName();
    private SplashActivity splashActivity;
    private LoginPresenter splashPresenter;
    private List<String> hostList;
    private ExecutorService pool;

    public static SplashFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.splash_fragment;
    }

    @Override
    protected void initView(View root) {
        if(hostList == null){
            hostList = new ArrayList<>();
        }
        hostList.add("app");
        hostList.add("app.injbank.com");
        hostList.add("injbank");
        hostList.add("www.baidu.com");
        hostList.add("com");
        hostList.add("www.cnblogs.com");
        hostList.add("adpp");
        hostList.add("lanhuapp.com");
        hostList.add("injbdank");
        hostList.add("daohang.qq.com");
        hostList.add("cfom");
        hostList.add("kan.sogou.com");
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
//        for (int i = 0; i < hostList.size(); i++) {
//            int finalI = i;
//            Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        synchronized (SplashFragment.this) {
//                            if (isStratThread) {
//                                InetAddress byName = null;
//                                int resCode = -1;
//                                try {
//                                    byName = InetAddress.getByName(hostList.get(finalI));
//                                    resCode = byName == null ? -1 : 0;
//                                } catch (UnknownHostException e) {
//                                    resCode = -1;
//                                    e.printStackTrace();
//                                }catch (Exception e) {
//                                    resCode = -1;
//                                    e.printStackTrace();
//                                } finally {
//                                    if (resCode == 0) {
//                                        isStratThread = false;
//                                        goApp();
//                                    }
//                                    GALogger.d(TAG, "resCode   " + resCode + "    i     " + finalI + "   isStratThread   " + isStratThread+"   host   "+hostList.get(finalI));
//                                }
//                            }
//                        }
//                    }
//                });
//            thread.setName(i+"");
//            thread.start();
//        }
        if(pool == null) {
            pool = Executors.newFixedThreadPool(hostList.size());
        }
        for (int i = 0; i < hostList.size(); i++) {
            pool.execute(new MyThread(i));
        }
        stopThread();
        goApp();
    }

    class MyThread implements Runnable{
        private int position;

        public MyThread(int i) {
            this.position = i;
        }

      @Override
      public void run() {
              InetAddress byName = null;
              int resCode = -1;
              try {
                  byName = InetAddress.getByName(hostList.get(position));
                  resCode = byName == null ? -1 : 0;
              } catch (UnknownHostException e) {
                  resCode = -1;
                  e.printStackTrace();
              }catch (Exception e) {
                  resCode = -1;
                  e.printStackTrace();
              } finally {
                  if (resCode == 0) {
                      MyApplication.setHostAddress("http://"+hostList.get(position)+":8080/GateServer/");
                  }
                  GALogger.d(TAG, "resCode   " + resCode + "    i     " + position +"   host   "+hostList.get(position));
              }
          }
    }

    private void goApp(){
                if(AccountManager.getInstance().isLogin()){
            /**
             * 掉登录接口  成功了去首页
             */
            long currentTime = System.currentTimeMillis();
            String phoneNumber = AccountManager.getInstance().getPhoneNumber();
            String passWord = AccountManager.getInstance().getPassWord();
            splashPresenter.login(phoneNumber,passWord,currentTime,false);

        }else {
            MyApplication.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    LoginActivity.startThis(splashActivity);
                    splashActivity.finish();
                }
            },3000);
        }
    }

    @Override
    public void loginSuccess(AccountInfo accountInfo) {
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.startThis(splashActivity);
                splashActivity.finish();
            }
        },3000);
    }

    @Override
    public void loginError() {
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                LoginActivity.startThis(splashActivity);
                splashActivity.finish();
            }
        },3000);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        splashPresenter = (LoginPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void onDestroy() {
        stopThread();
        super.onDestroy();
    }

    private void stopThread() {
        if (pool != null && !pool.isShutdown()) {
            try {
                pool.shutdown();
                // (所有的任务都结束的时候，返回TRUE)
                if (!pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                GALogger.d(TAG, "awaitTermination interrupted: " + e);
                pool.shutdownNow();
                e.printStackTrace();
            }
            GALogger.d(TAG, "end");
        }
    }
}
