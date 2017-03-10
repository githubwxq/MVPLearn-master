package learn.mvplearn.mvp.main.presenter;

import learn.mvplearn.mvp.main.view.MainView;
import learn.mvplearn.present.BasePresenterImpl;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class MainParsenterImpl extends BasePresenterImpl<MainView>{

    public MainParsenterImpl(MainView mainView) {
        attachView(mainView);  //activity 对象传过来

    }

    public void switchNavigation(int id) {
        View.SwitchItem(id);    // mainView 的switch方法

    }

}
