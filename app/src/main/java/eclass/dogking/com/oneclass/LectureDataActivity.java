package eclass.dogking.com.oneclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/4 0004.
 */

public class LectureDataActivity extends AppCompatActivity {


    @BindView(R.id.lecturedata_picture)
    ImageView lecturedataPicture;
    @BindView(R.id.lecturedata1)
    TextView lecturedata1;
    @BindView(R.id.lecturedata1_1)
    TextView lecturedata11;
    @BindView(R.id.lecturedata2)
    TextView lecturedata2;
    @BindView(R.id.lecturedata2_2)
    TextView lecturedata22;
    @BindView(R.id.lecturedata3)
    TextView lecturedata3;
    @BindView(R.id.lecturedata3_3)
    TextView lecturedata33;
    @BindView(R.id.lecturedata4)
    TextView lecturedata4;
    @BindView(R.id.lecturedata4_4)
    TextView lecturedata44;
    @BindView(R.id.lecturedata5)
    TextView lecturedata5;
    @BindView(R.id.lecturedata5_5)
    TextView lecturedata55;
    @BindView(R.id.lecturedata6)
    TextView lecturedata6;
    @BindView(R.id.lecturedata6_6)
    TextView lecturedata66;
    @BindView(R.id.unchose)
    Button unchose;


    private AlertDialog dialog;
    private String name;
    private String tel;
    private List<String> list;//0.课名1.老师2.时间3.地点4.环境5.介绍6.图片url

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.leturedata_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            tel= intent.getStringExtra("tel");
            name = intent.getStringExtra("name");
        }
        getLecture();
    }


    @OnClick({R.id.lecturedata2_2,R.id.unchose})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.lecturedata2_2:

                Intent intent=new Intent(LectureDataActivity.this,TeacherDataActvity.class);
                intent.putExtra("name",list.get(1));
                startActivity(intent);
                //getTeacher();
                break;
            case R.id.unchose:
                createdialog();
                dialog.show();

                break;

        }
    }

    private void  createdialog() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("退课")//设置对话框的标题
                .setMessage("退课将清空你的所有与此课有关的数据，你确定吗？")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unchose();
                        dialog.dismiss();
                    }
                }).create();
    }


    private void  unchose(){

            Observable<HttpDefault<String>> observable= OneclassUtils.createAPI(LecturecsAPI.class).unchose(
                    tel,name
            );
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HttpDefault<String>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull HttpDefault<String> lectureHttpDefault) {

                            if (lectureHttpDefault.getError_code() == -1) {
                                Log.e("message:",lectureHttpDefault.getMessage());
                                Toast.makeText(LectureDataActivity.this, lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(LectureDataActivity.this, lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            //showToast("服务器貌似出问题了(throwable)...", ToastDuration.SHORT);
                            Log.e("tag:", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }

                    });



    }


    private void getLecture(){
        Observable<HttpDefault<List<String>>> observable= OneclassUtils.createAPI(LectureAPI.class).getlecturedata(
                name
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<String>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<String>> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(LectureDataActivity.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureHttpDefault.getError_code() == 0) {
                            list = lectureHttpDefault.getData();
                            lecturedata11.setText(list.get(0));
                            lecturedata22.setText(list.get(1));
                            lecturedata33.setText(list.get(2));
                            lecturedata44.setText(list.get(3));
                            lecturedata55.setText(list.get(4));
                            lecturedata66.setText(list.get(5));
                            Glide.with(LectureDataActivity.this).load(OneclassUtils.getBaseURL()+list.get(6)).fitCenter().into(lecturedataPicture);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //showToast("服务器貌似出问题了(throwable)...", ToastDuration.SHORT);
                        Log.e("tag:", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



}
