package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.ExamAPI;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.entiry.Exam;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/11 0011.
 */

public class ExamFinishActivity extends AppCompatActivity {

    @BindView(R.id.userexercise)
    TextView examtitle;
    @BindView(R.id.mine2_2)
    TextView mine22;
    @BindView(R.id.smmit)
    Button smmit;
    @BindView(R.id.examimg)
    ImageView examimg;
    @BindView(R.id.realanswer)
    TextView realanswer;
    @BindView(R.id.scroll_body)
    View scrollBody;
    private String name;
    private String tel;
    private Lecturecs lecturecs;
    private Exam exam;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.examfinish_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            name = intent.getStringExtra("name");
            tel = intent.getStringExtra("tel");
        }
        scrollBody.setVisibility(View.GONE);
        examtitle.setText(name + "考试");
        getlecturecs();

    }

    @OnClick({R.id.smmit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.smmit:
                scrollBody.setVisibility(View.VISIBLE);
                findexam();
                break;
        }
    }

    private void getlecturecs() {
        Observable<HttpDefault<Lecturecs>> observable = OneclassUtils.createAPI(LecturecsAPI.class).getlecturecs(
                tel, name
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Lecturecs>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Lecturecs> lectureHttpDefault) {
                        lecturecs = lectureHttpDefault.getData();
                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(ExamFinishActivity.this, lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            mine22.setText(lecturecs.getScore() + "");
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
    private void findexam(){
        Observable<HttpDefault<Exam>> observable = OneclassUtils.createAPI(ExamAPI.class).findExam(
                name

        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Exam>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Exam> examHttpDefault) {

                        if (examHttpDefault.getError_code() == 0) {
                            //Toast.makeText(Tab4_1Activity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            exam=examHttpDefault.getData();
                            Log.d("名字------",exam.getName());
                            Log.d("名字------",exam.getAnswer());
                            realanswer.setText("答案："+exam.getAnswer());
                            Glide.with(ExamFinishActivity.this).load(OneclassUtils.getBaseURL()+exam.getUrl())
                                    .fitCenter().error(R.mipmap.error).into(examimg);
                        } else {

                            Toast.makeText(ExamFinishActivity.this,examHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
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
}
