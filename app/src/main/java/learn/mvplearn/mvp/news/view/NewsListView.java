package learn.mvplearn.mvp.news.view;

import learn.mvplearn.mvp.news.model.NewsModel;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public interface NewsListView {
    void showProgress();

    void hideProgress();

    void loadFailer(String msg  );

    void addDate(NewsModel data);


}
