package hbuilder.android.com.net.retrofit;

import android.content.Intent;
import android.os.Looper;
import com.growalong.util.util.GALogger;

import hbuilder.android.com.MyApplication;
import hbuilder.android.com.app.AppManager;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionBuilder;
import hbuilder.android.com.ui.activity.LoginActivity;
import hbuilder.android.com.util.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 封装 Observer 统一返回方法
 *
 * @param <T>
 */
public abstract class ModelResultObserver<T> implements Observer<T> {
    protected Disposable mDisposable = null;

    protected ModelResultObserver() {
    }


    @Override
    public void onSubscribe(Disposable d) {

        mDisposable = d;
    }

    @Override
    public void onNext(T t) {

        onSuccess(t);
        if (t instanceof BaseBean) {
            BaseBean baseBean = (BaseBean) t;
        }
        //解除注册
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //handle fail
        if (e instanceof ModelException) {
            onFailure((ModelException) e);
        } else {
            ModelException me = ModelExceptionBuilder.build(e);
            onFailure(me);
        }
        //解除注册
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * 成功时候的处理
     *
     * @param result
     * @return 返回 false 会继续处理；返回 true 不会继续处理
     */
    public abstract void onSuccess(T result);

    /**
     * 失败时候的处理
     */
    public void onFailure(ModelException ex) {
        GALogger.d("ModelResult", "onFailure() into, " + ex.toString() + "   mMessage   " + ex.mMessage + "   mCode   " + ex.mCode);
        ToastUtil.longShow(ex.mMessage);
        if (ex.mCode == 1 && Looper.myLooper() == Looper.getMainLooper()) {//"账号在别处已经登录"
            Intent intent = new Intent("android.intent.action.LoginMainActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.appContext.startActivity(intent);
            AppManager.getInstance().finishAllActivity(LoginActivity.class);
        }
    }
}
