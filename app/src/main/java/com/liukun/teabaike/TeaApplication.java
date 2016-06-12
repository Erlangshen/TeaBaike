package com.liukun.teabaike;

import android.app.Application;
import android.widget.Toast;

import com.liukun.teabaike.utils.ImageDownLoader;
import com.liukun.teabaike.utils.NetWorkUtil;

public class TeaApplication extends Application{
    private  static  TeaApplication app;
    private ImageDownLoader loader;
    @Override
    public void onCreate() {
        app=this;
        super.onCreate();
        if (!NetWorkUtil.isNetworkAvailable(this)){
            Toast.makeText(this,"网络不可以用",Toast.LENGTH_SHORT).show();
        }
    }

    public synchronized static TeaApplication getApp(){
        return app;
    }

    public ImageDownLoader getLoaderInstance(){
        if (loader==null){
            loader=new ImageDownLoader(this);
            return loader;
        }else
            return  loader;
    }
}
