package learn.mvplearn.network.news;



import learn.mvplearn.mvp.news.model.NewsModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public interface BaoZouService {
    @GET("api/v31/channels/{channels_id}")
    Observable<NewsModel> getNews(@Path("channels_id") int channels_id);

    @GET("api/v31/channels/{channels_id}")
    Observable<NewsModel> getNextNews(@Path("channels_id")int id,@Query("timestamp") int timestamp); //




}
