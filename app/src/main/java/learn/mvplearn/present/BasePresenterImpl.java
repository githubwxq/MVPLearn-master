package learn.mvplearn.present;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class BasePresenterImpl<V> implements BasePresenter<V> {
    public V View;

    private CompositeSubscription mCompositeSubscription;


    @Override
    public void attachView(V view) {  //给View赋值
        this.View = view;

    }

    @Override
    public void detachView() {//销毁干的事情
        this.View = null;
        onUnsubscribe();
    }

    //订阅io 发生在io observeon主线程

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())  //订阅操作往往是耗时操作发生在io线程
                .observeOn(AndroidSchedulers.mainThread())  //观察发生在
                .subscribe(subscriber));

    }


    public void onUnsubscribe() {

        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
