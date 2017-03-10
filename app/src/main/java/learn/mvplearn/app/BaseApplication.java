package learn.mvplearn.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;



public abstract class BaseApplication extends Application implements Thread.UncaughtExceptionHandler {
    private int count;
    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);//捕获系统异常
        mContext = getApplicationContext();

        // 内存泄漏检测
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);
//        LeakCanary.install(this);
        initResourceAndother();
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (count == 0) {
                    Log.i("wxq", "=前台=");
                }
                count++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    Log.i("wxq", "=后台后台=");
                }


            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    protected abstract void initResourceAndother();

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("wxq", outputError(ex));
        String time = getErrorTime();
        writeAndUpload(mContext, time + outputError(ex).substring(16, 35), outputError(ex));//错误写入到sd卡中
        dealWithException(ex);// 给上层处理异常  可以重启服务发警告等等

        android.os.Process.killProcess(android.os.Process.myPid());

    }

    public abstract void dealWithException(Throwable ex);

    public String outputError(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        if (cause != null) {
            cause.printStackTrace(printWriter);
        }
        return writer.toString();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            System.gc();
        } catch (Exception e) {
        }
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
        try {
            System.gc();
        } catch (Exception e) {
        }
    }
   //存放错误到本地wxqexception文件夹 可以上传到服务器
    public void writeAndUpload(Context context, String key, String value) {
        File cacheDir = getCacheFile(context);
        FileWriter writer = null;
        File cacheFile = new File(cacheDir, key + ".txt");
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer = new FileWriter(cacheFile, true);
            writer.write(value);// 写入缓存
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getCacheFile(Context context) {
        File cacheFile = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //    cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wxq/ErrorException");
            cacheFile = new File(Global.ERROREXCEPTION);
            if (!cacheFile.exists()) {
                cacheFile.mkdir();
            }
        } else {
            cacheFile = context.getCacheDir();
        }
        return cacheFile;
    }

    public String getErrorTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日_HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }


//各种路径
//    Log.i("wxq",mContext.getExternalCacheDir().getAbsolutePath());
//    Log.i("wxq",Environment.getExternalStorageDirectory().getAbsolutePath());
//    Log.i("wxq",mContext.getCacheDir().getAbsolutePath());
//    Log.i("wxq",mContext.getFilesDir().getAbsolutePath());
//    y I/wxq: /storage/emulated/0/Android/data/com.example.wxq.wxqusefullibrary/cache
//    01-18 18:03:00.120 3379-3379/com.example.wxq.wxqusefullibrary I/wxq: /storage/emulated/0
//    01-18 18:03:00.120 3379-3379/com.example.wxq.wxqusefullibrary I/wxq: /data/data/com.example.wxq.wxqusefullibrary/cache
//    01-18 18:03:00.120 3379-3379/com.example.wxq.wxqusefullibrary I/wxq: /data/data/com.example.wxq.wxqusefullibrary/files


}