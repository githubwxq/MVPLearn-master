package learn.mvplearn.callback;

import java.security.KeyStore;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class SubscriberCallback<T> extends Subscriber<T>{ //对订阅者的强化Subscriber<T>


    private  NetCallback<T> mNetCallback;

    // 构造的时候传入回调
    public SubscriberCallback(NetCallback<T> mNetCallback) {
        this.mNetCallback = mNetCallback;
    }


    @Override
    public void onCompleted() {
        mNetCallback.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
    e.printStackTrace();
        int code=404;
        String msg="未知的错误";
        if(e instanceof HttpException){
            code = 500;
            msg = "世界最遥远的距离就是没网";
        }
        mNetCallback.onFailer(code, msg);
        mNetCallback.onCompleted();
    }

    @Override
    public void onNext(T t) {
     mNetCallback.onSuccess(t);
    }
}
