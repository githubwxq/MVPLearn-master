package learn.mvplearn.mvp.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import learn.mvplearn.R;
import learn.mvplearn.mvp.news.model.NewsModel;
import learn.mvplearn.utils.ImageUitls;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 强化适配强化功能
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<NewsModel.DataBean> mData;

    private boolean mShowFooter = true;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;// 点击事件的回调
    private LoadMoreListener loadMoreListener;// 点击事件的回调

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public interface LoadMoreListener {
        public void loadMore();
    }


    public NewsAdapter(Context context) {
        this.mContext = context;
    }


    public void setmDate(List<NewsModel> data) {
        this.mData = data.get(0).getData();
        this.notifyDataSetChanged();

    }

    public void addDate(NewsModel data) {
        this.mData.addAll(data.getData());
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {

        if (position == mData.size() && mData.size() >= 1) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mTitle.setText(mData.get(position).getTitle());
            ((ItemViewHolder) holder).mDesc.setText(mData.get(position).getAuthor_name());

            ((ItemViewHolder) holder).itemView.setOnClickListener(new MyOnClickListener(position));
            ImageUitls.loadLocal(mContext, mData.get(position).getAuthor_avatar(), ((ItemViewHolder) holder).mNewsImg);
        }else{
            //加载跟多
            loadMoreListener.loadMore();
        }

    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        if (mShowFooter&&mData != null) {
            return mData.size() + 1;
        }

        return mData.size();


    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mDesc = (TextView) v.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) v.findViewById(R.id.ivNews);

        }

//        @Override
//        public void onClick(View view) {
//            if (mOnItemClickListener != null) {
//                mOnItemClickListener.onItemClick(view, this.getPosition());
//            }
//        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        private final int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, position);
            }
        }
    }
}
