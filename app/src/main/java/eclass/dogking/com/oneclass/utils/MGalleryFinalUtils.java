package eclass.dogking.com.oneclass.utils;

import android.content.Context;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by dog on 2018/2/25 0025.
 */

public class MGalleryFinalUtils extends FunctionConfig.Builder {

    private static MGalleryFinalUtils galleryFinalUtils;
    private static ThemeConfig theme;
    //    private static ImageLoader imageloader;
    private static GildeImageLoader imageloader;
    private static CoreConfig coreConfig;
    private static FunctionConfig functionConfig_single;
    private static FunctionConfig functionConfig_notsingle;
    private static FunctionConfig.Builder builder;
    private static Context context;
    private static List<PhotoInfo> mphotoList;


    private static GalleryfinalActionListener listener;


    private static final int REQUEST_CODE_OPENCAMERA = 1000;
    private static final int REQUEST_CODE_GALLERY = 1001;
    private static final int REQUEST_CODE_GALLERYSINGLE = 1002;
    private static final int REQUEST_CODE_CROP = 1003;
    private static final int REQUEST_CODE_EDIT = 1004;


    private MGalleryFinalUtils() {
    }

    public static MGalleryFinalUtils getInstance(Context context) {

        MGalleryFinalUtils.context = context;
        mphotoList = new ArrayList<>();

        if (galleryFinalUtils == null) {
            galleryFinalUtils = new MGalleryFinalUtils();
        }
        return galleryFinalUtils;
    }


    /***************************************
     * 初始化galleryfinal
     */
    public void initGalleryFinal(boolean ISsingle, int photonum) {

        //配置主题
        theme = new ThemeConfig.Builder().build();
        //配置功能
//        this = FunctionConfig.Builder;

        builder = new FunctionConfig.Builder();

        functionConfig_single = this
                .setMutiSelect(false)
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnableCamera(true)
                .setEnablePreview(true)
                .setSelected(mphotoList)
                .setMutiSelectMaxSize(1)
                .build();


        functionConfig_notsingle = this
                .setMutiSelect(true)
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnableCamera(true)
                .setEnablePreview(true)
                .setSelected(mphotoList)
                .setMutiSelectMaxSize(photonum)
                .build();

//        functionConfig_notsingle = builder.setEnableCamera(true)
//                .setEnableEdit(true)
//                .setEnableCrop(true)
//                .setEnableRotate(true)
//                .setCropSquare(true)
//                .setEnableCamera(true)
//                .setEnablePreview(true)
//                .setSelected(mphotoList)
//                .setMutiSelectMaxSize(photonum)
//                .build();


        //配置imageloader
        imageloader = new GildeImageLoader();
        //设置核心配置信息
        if (ISsingle) {
            coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                    .setFunctionConfig(functionConfig_single)
                    .build();
        } else {
            coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                    .setFunctionConfig(functionConfig_notsingle)
                    .build();
        }
        GalleryFinal.init(coreConfig);
        initImageloader(context);

    }


    private static void initImageloader(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config.build());

    }

    /*************************************
     * 打开相机
     */
    public void openCamera(GalleryfinalActionListener listener) {
        this.listener = listener;
        GalleryFinal.openCamera(REQUEST_CODE_OPENCAMERA, functionConfig_single, mOnHanlderResultCallback);
    }

    /*************************************
     * 打开相册  单选
     */
    public void openAlbumSingle(GalleryfinalActionListener listener) {
        this.listener = listener;
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERYSINGLE, functionConfig_single, mOnHanlderResultCallback);
    }

    /*************************************
     * 打开相册 多选
     */
    public void openAlbumMore(GalleryfinalActionListener listener) {
        this.listener = listener;
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig_notsingle, mOnHanlderResultCallback);
    }




    private static GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

            mphotoList = new ArrayList<>();
            switch (reqeustCode) {

                case REQUEST_CODE_OPENCAMERA:
                case REQUEST_CODE_GALLERYSINGLE:
                    if (resultList != null) {
                        mphotoList.add(resultList.get(0));
                    }
                    listener.success(mphotoList);
                    break;
                case REQUEST_CODE_GALLERY:
                    if (resultList != null) {
                        mphotoList.addAll(resultList);
                    }
                    listener.success(mphotoList);
                    break;
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            listener.failed(errorMsg);
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();


        }
    };


    public static void clearCache() {
        GalleryFinal.cleanCacheFile();
        Toast.makeText(context, "清理成功", Toast.LENGTH_SHORT).show();
    }


}
