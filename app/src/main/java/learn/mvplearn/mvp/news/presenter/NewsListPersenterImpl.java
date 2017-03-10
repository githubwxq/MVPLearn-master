package learn.mvplearn.mvp.news.presenter;

import learn.mvplearn.callback.NetCallback;
import learn.mvplearn.callback.SubscriberCallback;
import learn.mvplearn.commmon.Api;
import learn.mvplearn.mvp.news.model.NewsModel;
import learn.mvplearn.mvp.news.view.NewsListView;
import learn.mvplearn.network.news.BaoZouService;
import learn.mvplearn.network.news.NewsNetwork;
import learn.mvplearn.present.BasePresenterImpl;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class NewsListPersenterImpl extends BasePresenterImpl<NewsListView>   {

    //数据层对象
    public BaoZouService mBaozouService = NewsNetwork.getmRetrofit(Api.BAOZOU).create(BaoZouService.class); //接口的实现类获取对应的值


    public NewsListPersenterImpl(NewsListView newsListView) {
        attachView(newsListView); //冲夫人ａｇｍｅｎｔｚｈｏｇｎ传过来的 直接调用 父类代理实现


    }

    //加载
    public void loadData(int channels) {
        View.showProgress(); // 显示精度条

//        addSubscription(mBaozouService.getNews(channels), new Subscriber() { //对订阅者继续封装一成 下次换框架好改
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//
//            }loadData
//        });

        addSubscription(mBaozouService.getNews(channels), new SubscriberCallback(new NetCallback<NewsModel>() {
            @Override
            public void onSuccess(NewsModel model) {  //这个model 来自于 onnext 方法中的那歌参数 而 onnext中的参数 又是网络请求获取到的  订阅后获取对应对象
                View.addDate(model);
            }

            @Override
            public void onFailer(int code, String msg) {
                View.loadFailer(msg);
            }

            @Override
            public void onCompleted() {
                View.hideProgress();
            }
        }));


    }

    /**
     * 加载下一页的数据
     */
    public void loadNextData(int channels,int timestamp){
     View.showProgress(); //这些现实dialog代码就该被封装在网络请求中
        addSubscription(mBaozouService.getNextNews(channels,timestamp),new SubscriberCallback(new NetCallback<NewsModel>() {
            @Override
            public void onSuccess(NewsModel model) { //最终将数据传递到ui成
                View.addDate(model);
            }

            @Override
            public void onFailer(int code, String msg) {
                View.loadFailer(msg);
            }

            @Override
            public void onCompleted() {
                View.hideProgress();
            }
        }));


    }

}

