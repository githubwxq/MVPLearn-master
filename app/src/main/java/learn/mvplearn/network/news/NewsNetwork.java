package learn.mvplearn.network.news;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class NewsNetwork {
    public static Retrofit mRetrofit;

    public static void setmRetrofit(Retrofit mRetrofit) {
        NewsNetwork.mRetrofit = mRetrofit;
    }

    public static Retrofit getmRetrofit(String baseUrl) {

        if (mRetrofit==null) {
            OkHttpClient.Builder builder=new OkHttpClient().newBuilder();

            if (true) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor); //添加拦截器
            }


            OkHttpClient okHttpClient=builder.build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();


        }

        return  mRetrofit;  //大家都用的工具类

    }
}
