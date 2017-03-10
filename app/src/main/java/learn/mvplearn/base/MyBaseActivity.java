package learn.mvplearn.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



import butterknife.ButterKnife;
import learn.mvplearn.R;
import learn.mvplearn.present.BasePresenterImpl;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public abstract class MyBaseActivity<P extends BasePresenterImpl> extends AppCompatActivity {
    private CompositeSubscription mCompositeSubscription;

    protected P Presente;

    public final static  String TAG=MyBaseActivity.class.getCanonicalName();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        Presente = createPresenter();
        ButterKnife.bind(this);

    }
    //由子类实现
    public abstract P createPresenter();

    public abstract  int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        if(Presente!=null){
            Presente.detachView();
        }
    }


    // 添加订阅关系
    public void addSubscription(Subscription subscription) {
    if(mCompositeSubscription==null)
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);

    }

    //取消订阅
    public void onUnsubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }

    }

}
