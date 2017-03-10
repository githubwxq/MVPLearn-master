package learn.mvplearn.daoimpl;

import learn.mvplearn.dao.TaskDataSource;

public class TaskDataSourceTestImpl implements TaskDataSource {
    @Override
    public String getStringFromRemote() {
        return "Hello ";
    }

    @Override
    public String getStringFromCache() {
        return " world Test ";
    }
}