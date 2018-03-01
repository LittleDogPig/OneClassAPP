package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/24 0024.
 */

public class ExamdetailActivity extends AppCompatActivity {
    @BindView(R.id.exam_name)
    TextView examName;
    @BindView(R.id.smmit)
    Button smmit;
    private String tel;
    private String name;
    Lecturecs lecturecs;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.examdetail_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            name = intent.getStringExtra("name");
            tel = intent.getStringExtra("tel");
        }
        examName.setText(name+"考试须知");
    }

    @OnClick({R.id.smmit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.smmit:
                getlecturecs();
                break;
        }
    }
    private  void getlecturecs(){
        Observable<HttpDefault<Lecturecs>> observable= OneclassUtils.createAPI(LecturecsAPI.class).getlecturecs(
                tel,name
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Lecturecs>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Lecturecs> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(ExamdetailActivity.this, lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureHttpDefault.getError_code() == 0) {

                            lecturecs = lectureHttpDefault.getData();
                            if (lecturecs.getExercise().equals("0"))
                                Toast.makeText(ExamdetailActivity.this, "你需要完成练习才能参加考试", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intent = new Intent(ExamdetailActivity.this, ExamActivity.class);
                                intent.putExtra("name", name);//传递课程
                                intent.putExtra("tel", tel);//传递登录的用户
                                ExamdetailActivity.this.startActivity(intent);
                                ExamdetailActivity.this.finish();
                            }
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
