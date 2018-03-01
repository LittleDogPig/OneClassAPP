package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.API.TeacherAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecture;
import eclass.dogking.com.oneclass.entiry.Teacher;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/4 0004.
 */

public class TeacherDataActvity extends AppCompatActivity {
    @BindView(R.id.teacherdata1)
    TextView teacherdata1;
    @BindView(R.id.teacherdata1_1)
    TextView teacherdata11;
    @BindView(R.id.teacherdata2)
    TextView teacherdata2;
    @BindView(R.id.teacherdata2_2)
    TextView teacherdata22;
    @BindView(R.id.teacherdata3)
    TextView teacherdata3;
    @BindView(R.id.teacherdata3_3)
    TextView teacherdata33;
    @BindView(R.id.teacherdata4)
    TextView teacherdata4;
    @BindView(R.id.teacherdata4_4)
    TextView teacherdata44;
    @BindView(R.id.teacherdata5)
    TextView teacherdata5;
    @BindView(R.id.teacherdata5_5)
    TextView teacherdata55;
    private String name;
    private CircleImageView teacherhead;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.teacherdata_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得老师名字名称
        if (intent != null) {
            name = intent.getStringExtra("name");
        }
        teacherhead=(CircleImageView)findViewById(R.id.teacherdata_picture);
    getTeacher();
    }

    private void getTeacher(){
        Observable<HttpDefault<Teacher>> observable= OneclassUtils.createAPI(TeacherAPI.class).findNameByName(
                name
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Teacher>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Teacher> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(TeacherDataActvity.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureHttpDefault.getError_code() == 0) {

                            Teacher teacher = lectureHttpDefault.getData();
                            teacherdata11.setText(teacher.getName());
                            teacherdata22.setText(teacher.getSex());
                            teacherdata33.setText(teacher.getEmail());
                            teacherdata44.setText(teacher.getSchool());
                            teacherdata55.setText(teacher.getDescription());
                            Glide.with(TeacherDataActvity.this).load(OneclassUtils.getBaseURL()+teacher.getPictureurl()).centerCrop().fitCenter().error(R.mipmap.error).into(teacherhead);
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
