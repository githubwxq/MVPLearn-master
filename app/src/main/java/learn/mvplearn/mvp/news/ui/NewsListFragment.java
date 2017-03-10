package learn.mvplearn.mvp.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import learn.mvplearn.R;
import learn.mvplearn.base.extend.MvpFragment;
import learn.mvplearn.mvp.news.model.NewsModel;
import learn.mvplearn.mvp.news.presenter.NewsListPersenterImpl;
import learn.mvplearn.mvp.news.ui.adapter.NewsAdapter;
import learn.mvplearn.mvp.news.view.NewsListView;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class NewsListFragment extends MvpFragment<NewsListPersenterImpl>  implements NewsListView, SwipeRefreshLayout.OnRefreshListener ,NewsAdapter.LoadMoreListener{
    private int mType = NewsFragment.NEWS_TYPE_FUNY;

    private int pageIndex = 0;
    int channel = 0;
    SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private List<NewsModel> mData;
    int tempTime;

    // 初始化实例
    public static  NewsListFragment newInstance(int type){

        Bundle args=new Bundle();
        NewsListFragment fragment=new NewsListFragment();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_list,null);

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //适配器

        mAdapter = new NewsAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        return  view;




    }
    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getActivity(),"dsafdfasdfadsf",Toast.LENGTH_LONG).show();
//            NewsModel.DataBean item = mAdapter.getItem(position);
//            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//            intent.putExtra("Share_url",item.getShare_url());
//            intent.putExtra("Title",item.getTitle());
//            intent.putExtra("Author_avatar",item.getAuthor_avatar());
//            View transition = view.findViewById(R.id.ivNews);
//            ActivityOptionsCompat options =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                            transition, getString(R.string.transition_news_img));
//            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

        }
    };
    //滑动监听加载更多
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    ) {
                //加载更多
                mvpPresenter.loadNextData(channel, tempTime);
            }

        }
    };

    @Override
    protected NewsListPersenterImpl createPresenter() {
        return new NewsListPersenterImpl(this); // 自动给present赋值了 然后就能用了 fragment中的mvppresenter
    }

    @Override
    protected void initData() {
        switch (mType) {
            case NewsFragment.NEWS_TYPE_FUNY:
                channel = 3;
                break;
            case NewsFragment.NEWS_TYPE_GIRL:
                channel = 4;
                break;
            case NewsFragment.NEWS_TYPE_MOVIE:
                channel = 5;
                break;
            case NewsFragment.NEWS_TYPE_GAME:
                channel = 6;
                break;
            default:
                channel = 3;
                break;
        }
        //加载数据
        mvpPresenter.loadData(channel);

    }

    @Override
    protected void initViews() {

    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
        // 隐藏脚
      //  mAdapter.isShowFooter(false);
    }

    @Override
    public void loadFailer(String msg) {

    }

    @Override
    public void addDate(NewsModel data) {
    //获取数据成功回调
        tempTime= data.getTimestamp();
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);

        if (pageIndex == 0) {
            mAdapter.setmDate(mData);  // 设置数据方法自动刷新
        } else {
            mAdapter.addDate(data); // 下拉加载跟多
            //如果没有更多数据了,则隐藏footer布局
            if (mData == null || mData.size() == 0) {
                mAdapter.isShowFooter(false);
            }else{

            }


        }

        mAdapter.notifyDataSetChanged();
        pageIndex = 2;
        hideProgress();
    }

    @Override
    public void onRefresh() {
        // 刷新
        pageIndex = 0;
        if (mData != null) {
            mData.clear();
            mvpPresenter.loadData(channel);
        }




    }

    @Override
    public void loadMore() {



    //    adfsdf
    }
}
