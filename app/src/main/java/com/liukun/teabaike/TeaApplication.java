package com.liukun.teabaike;

import android.app.Application;

import com.liukun.teabaike.utils.ImageDownLoader;

public class TeaApplication extends Application{
    private  static  TeaApplication app;
    private ImageDownLoader loader;
    @Override
    public void onCreate() {
        app=this;
        super.onCreate();
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
