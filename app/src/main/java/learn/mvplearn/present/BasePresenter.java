package learn.mvplearn.present;

import android.view.View;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}
