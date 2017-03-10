package learn.mvplearn.daoimpl;

import android.app.LoaderManager;


import learn.mvplearn.dao.TaskDataSource;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class TaskDataSourceImpl implements TaskDataSource {
    @Override
    public String getStringFromRemote() {



        return "hellow";
    }

    @Override
    public String getStringFromCache() {



        return "world";
    }

}
