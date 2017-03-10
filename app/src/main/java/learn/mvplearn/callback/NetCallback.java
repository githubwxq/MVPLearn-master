package learn.mvplearn.callback;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public interface NetCallback<T> {

    void onSuccess(T model); // 泛型适配不同类型对象

    void onFailer(int code, String msg); //数据格式

    void onCompleted();


}
