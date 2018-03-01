package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.API.LecturesonAPI;
import eclass.dogking.com.oneclass.Adapter.LectureAdapter;
import eclass.dogking.com.oneclass.Adapter.MyAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.entiry.Lectureson;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/4 0004.
 */

public class LectureActivity extends AppCompatActivity {

    @BindView(R.id.lecture_picture)
    ImageView lecturePicture;
    @BindView(R.id.lecture_linerlayou)
    LinearLayout lectureLinerlayou;
    @BindView(R.id.lecture_b1)
    Button lectureB1;
    @BindView(R.id.lecture_b2)
    Button lectureB2;
    @BindView(R.id.lecture_decription)
    TextView lectureDecription;
    @BindView(R.id.lecture_name)
    TextView lecture_name;
    @BindView(R.id.lecture_rv)
    RecyclerView mRecyclerView;

    private LectureAdapter mrvAdapter;
    private List<Lectureson> lecturesons;
    private LinearLayoutManager mLayoutManager;
    private String name;
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lecture_layout);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            name = intent.getStringExtra("name");
            tel=intent.getStringExtra("tel");
        }
        getLecture();
        getLectureson();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.lecture_b1,R.id.lecture_b2})
    public void onClick(View view){
        switch (view.getId()){
            case (R.id.lecture_b1):
              Intent intent=new Intent(LectureActivity.this,LectureDataActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("tel",tel);
                startActivity(intent);
            break;

            case (R.id.lecture_b2):
                ChoseLecture();

            break;


        }

    }

    protected  void  initData(){

        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new LectureAdapter(this,lecturesons,name);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mrvAdapter);
    }

   private void getLecture(){
        Observable<HttpDefault<Lecture>> observable= OneclassUtils.createAPI(LectureAPI.class).findlecturebyname(
                name
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Lecture>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Lecture> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(LectureActivity.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureHttpDefault.getError_code() == 0) {

                            Lecture lecture = lectureHttpDefault.getData();
                            lecture_name.setText(lecture.getName());
                            lectureDecription.setText(lecture.getDescription());
                            Glide.with(LectureActivity.this).load(OneclassUtils.getBaseURL()+lecture.getPictureurl()).centerCrop().into(lecturePicture);
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
    private void getLectureson(){
        Observable<HttpDefault<List<Lectureson>>> observable= OneclassUtils.createAPI(LecturesonAPI.class).getlectureson(
                name
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<Lectureson>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<Lectureson>> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(LectureActivity.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureHttpDefault.getError_code() == 0) {

                           lecturesons = lectureHttpDefault.getData();
                           initData();
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




    private  void ChoseLecture(){
        Observable<HttpDefault<Lecturecs>> observable= OneclassUtils.createAPI(LecturecsAPI.class).choselecture(
                name,tel
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Lecturecs>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Lecturecs> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(LectureActivity.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureHttpDefault.getError_code() == 0) {

                            Lecturecs lecturecs = lectureHttpDefault.getData();
                            Toast.makeText(LectureActivity.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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
