package eclass.dogking.com.oneclass.Database;

import android.app.Application;
import android.content.Context;


import com.liulishuo.filedownloader.FileDownloader;

import eclass.dogking.com.oneclass.utils.GildeImageLoader;
import io.realm.Realm;
import io.realm.RealmConfiguration;



import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.*;

/**
 * Author LYJ
 * Created on 2017/2/9.
 * Time 11:35
 */

public class MyApp extends Application{
    private static Context context = null;
    private static  MyApp myApplication;

    public static MyApp getInstance(){

        if (myApplication==null){
            myApplication = new MyApp();
        }

        return myApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initGalleryFinal();
    }
    private void  initrealm(){
        Realm.init(this);//初始化Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("mineRealm.realm")//设置数据库名称
                .schemaVersion(0)//设置版本号
                .build();

        Realm.setDefaultConfiguration(configuration);//设置配置


    }

    /***************************************
     * 初始化galleryfinal
     */

    private void initGalleryFinal() {

        //配置主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
    //配置imageloader
        ImageLoader imageloader = new GildeImageLoader();
        //设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);

    }
}
