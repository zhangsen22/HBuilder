package hbuilder.android.com;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import com.growalong.util.util.PackageUtil;
import hbuilder.android.com.crash.CrashHandler;
import hbuilder.android.com.manager.TinkerManager;
import hbuilder.android.com.observer.NetworkChangedReceiver;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    public static volatile Handler applicationHandler;
    public static volatile Context appContext;

    /**
     * 由于在onCreate替换真正的Application,
     * 我们建议在onCreate初始化TinkerPatch,而不是attachBaseContext
     */
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        TinkerManager.getInstance().initTinkerPatch(appContext);

        // 获取当前进程名
        final String processName = PackageUtil.getProcessName(android.os.Process.myPid());
        // 获取当前包名
        final String packageName = appContext.getPackageName();
        applicationHandler = new Handler(appContext.getMainLooper());
        if (processName != null && !processName.equals(packageName)) {
            return;
        }

        /*开启网络广播监听*/
        NetworkChangedReceiver.getReceiverInstance().registerNetworkStateReceiver(appContext);
        //crash崩溃本地捕捉
        CrashHandler.getInstance().init(appContext);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //低内存的时候回收掉
        NetworkChangedReceiver.getReceiverInstance().unRegisterNetworkStateReceiver(appContext);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            applicationHandler.post(runnable);
        } else {
            applicationHandler.postDelayed(runnable, delay);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
