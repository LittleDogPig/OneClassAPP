package eclass.dogking.com.oneclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.CircleTransformation;
import eclass.dogking.com.oneclass.utils.GalleryfinalActionListener;
import eclass.dogking.com.oneclass.utils.MGalleryFinalUtils;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

import eclass.dogking.com.oneclass.utils.choseDialog;
import eclass.dogking.com.oneclass.utils.messageDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Tab4_1Activity extends AppCompatActivity {


     private CircleImageView minehead;
    @BindView(R.id.mine1)
    TextView mine1;
    @BindView(R.id.mine1_1)
    TextView mine11;
    @BindView(R.id.mine2)
    TextView mine2;
    @BindView(R.id.mine2_2)
    TextView mine22;
    @BindView(R.id.mine3)
    TextView mine3;
    @BindView(R.id.mine3_3)
    TextView mine33;
    @BindView(R.id.mine4)
    TextView mine4;
    @BindView(R.id.mine4_4)
    TextView mine44;
    @BindView(R.id.mine5)
    TextView mine5;
    @BindView(R.id.mine5_5)
    TextView mine55;
    @BindView(R.id.mineb1)
    Button mineb1;
    @BindView(R.id.mineb2)
    Button mineb2;
    @BindView(R.id.chose)
    TextView chose;

    String tel;
    private static MGalleryFinalUtils instance = null;
    File file_result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab4_1);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得登录用户的电话
        if (intent != null) {
            tel = intent.getStringExtra("tel");

        }
        initdata();
        //Toast.makeText(this,tel,Toast.LENGTH_SHORT).show();
        //Log.e("个人信息===========",tel);
        receive(tel);
    }
    @OnClick({R.id.mineb1,R.id.mineb2,R.id.chose})
    public void OnClick(View view){
        if (instance == null) {
            instance = MGalleryFinalUtils.getInstance(this);
        }
        String name=mine11.getText().toString();
        String sex=mine33.getText().toString();
        switch (view.getId()){
            case R.id.mineb1:
                this.finish();
                break;
            case R.id.mineb2:
                Intent intent = new Intent(Tab4_1Activity.this, UpdatemineActivity.class);
                intent.putExtra("tel",tel);
                startActivity(intent);
                break;
            case R.id.chose:
                dialogshow();
                break;

        }


    }

    //初始化GalleryFinal动作前的配置
    private DisplayImageOptions initGalleryfinalActionConfig() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_gf_default_photo)
                .showImageForEmptyUri(R.drawable.ic_gf_default_photo)
                .showImageOnLoading(R.drawable.ic_gf_default_photo).build();

        return options;
    }

    protected  void dialogshow(){

        new choseDialog(Tab4_1Activity.this){

            public void camer(){
                //Toast.makeText(Tab4_1Activity.this,"准备照相",Toast.LENGTH_SHORT).show();
                //拍照
                instance.initGalleryFinal(true, 1);
                instance.openCamera(new GalleryfinalActionListener() {
                    @Override
                    public void success(List<PhotoInfo> list) {
                        PhotoInfo photoInfo = list.get(0);
                        DisplayImageOptions options = initGalleryfinalActionConfig();
                        ImageLoader.getInstance().displayImage("file:/" + photoInfo.getPhotoPath(), minehead, options);
                        file_result = new File(photoInfo.getPhotoPath());
                        uploadFile(tel, file_result, "file");
                        receive(tel);
                    }

                    @Override
                    public void failed(String msg) {

                    }
                });
            }

            public  void  photo(){
                //Toast.makeText(Tab4_1Activity.this,"从相册选取",Toast.LENGTH_SHORT).show();
                //打开相册
                instance.initGalleryFinal(true, 1);
                instance.openAlbumSingle(new GalleryfinalActionListener() {
                    @Override
                    public void success(List<PhotoInfo> list) {
                        PhotoInfo photoInfo = list.get(0);
                        DisplayImageOptions options = initGalleryfinalActionConfig();
                        ImageLoader.getInstance().displayImage("file:/" + photoInfo.getPhotoPath(), minehead, options);

                        file_result = new File(photoInfo.getPhotoPath());
                        uploadFile(tel, file_result, "file");
                        receive(tel);
                    }

                    @Override
                    public void failed(String msg) {
                        Log.d("tag", "打开相册Error:" + msg);
                    }
                });
            }

        }.show();

    }





    private void receive(String tel){
        Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).queryUserbyPhone(
                tel

        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<User> userHttpDefault) {

                        if (userHttpDefault.getError_code() == 0) {
                            //Toast.makeText(Tab4_1Activity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("tag:", "============" + "个人信息");
                            mine11.setText(userHttpDefault.getData().getName());
                            mine22.setText(userHttpDefault.getData().getTel());
                            mine33.setText(userHttpDefault.getData().getSex());
                            //Toast.makeText(Tab4_1Activity.this,"接收成功", Toast.LENGTH_SHORT).show();
                            Glide.with(Tab4_1Activity.this).load(OneclassUtils.getBaseURL()+userHttpDefault.getData().getHeadimg())
                                    .centerCrop().fitCenter().error(R.mipmap.error).into(minehead);
                            mine44.setText(refFormatNowDate(userHttpDefault.getData().getCreate_time()));
                            mine55.setText(userHttpDefault.getData().getDescription());
                        } else {

                            Toast.makeText(Tab4_1Activity.this,userHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("tag:", e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    //上传文件到服务器
    private boolean uploadFile(String tel, File file, String paramsname) {

        if (!file.exists()) {
            return false;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(paramsname, file.getName(), requestBody);

        String description_title = "";
        RequestBody description = RequestBody.create(
                MediaType.parse("multipart/form-data"), description_title);


        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(UserAPI.class).updateHeadImg(tel, body);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> stringHttpDefault) {

                        Log.d("tag", "upload status" + stringHttpDefault.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("tag", "upload error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return true;
    }

    private void initdata(){
       minehead=(CircleImageView)findViewById(R.id.minehead);


    }

    public String refFormatNowDate(String time) {
        Date nowTime = new Date(Long.parseLong(time));
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String retStrFormatNowDate = sdFormatter.format(nowTime);

        return retStrFormatNowDate;
    }
}
