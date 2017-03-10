package learn.mvplearn.mvp.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import learn.mvplearn.R;
import learn.mvplearn.base.BaseFragment;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class testFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test,null);

        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }
}
