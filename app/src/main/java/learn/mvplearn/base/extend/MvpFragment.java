package learn.mvplearn.base.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import learn.mvplearn.base.BaseFragment;
import learn.mvplearn.present.BasePresenterImpl;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public abstract class MvpFragment<P extends BasePresenterImpl> extends BaseFragment {

    protected  P mvpPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mvpPresenter= createPresenter();
    }

    protected abstract P createPresenter();

//销毁的封装
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
