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
import eclass.dogking.com.oneclass.API.ExerciseAPI;
import eclass.dogking.com.oneclass.API.UserExerciseAPI;
import eclass.dogking.com.oneclass.entiry.Exercise;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.UserExercise;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/11 0011.
 */

public class UserExercisefinishActivity extends AppCompatActivity {
    @BindView(R.id.userexercise_name)
    TextView userexerciseName;
    @BindView(R.id.userexercise)
    TextView userexercise;
    @BindView(R.id.mine1_1)
    TextView mine11;
    @BindView(R.id.mine2_2)
    TextView mine22;
    @BindView(R.id.smmit)
    Button smmit;
    private String tel;
    private String name;
    private String lecturename;
    private Exercise exercise;
    private UserExercise userExercise;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userexercisefinish);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            tel = intent.getStringExtra("tel");
            name = intent.getStringExtra("name");
            lecturename = intent.getStringExtra("lecturename");
        }
        userexerciseName.setText(name + "完成情况");
        getExercise();//找到对应练习更新题目和答案
        getRecord();//找到用户对应练习记录显示得分


    }

    @OnClick({R.id.smmit})
    public void onClikc(View view){
        switch (view.getId()){
            case R.id.smmit:
                UserExercisefinishActivity.this.finish();
            break;
        }

    }

    private void getExercise() {
        Observable<HttpDefault<Exercise>> observable = OneclassUtils.createAPI(ExerciseAPI.class).getexercise(
                lecturename, name);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Exercise>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Exercise> exerciseHttpDefault) {

                        if (exerciseHttpDefault.getError_code() == -1) {
                            Toast.makeText(UserExercisefinishActivity.this, exerciseHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseHttpDefault.getError_code() == 0) {
                            exercise = exerciseHttpDefault.getData();
                            userexercise.setText(exercise.getDescription());
                            String answer;
                            if(exercise.getAnswer().equals("0"))
                            answer="错误" ;
                            else  answer="正确";
                            mine11.setText(answer);
                            //Toast.makeText(UserExerciseActivity.this,"课名为"+lecturename, Toast.LENGTH_SHORT).show();
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


    private void getRecord(){
        Observable<HttpDefault<UserExercise>> observable= OneclassUtils.createAPI(UserExerciseAPI.class).getrecord(
                tel,name,lecturename);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<UserExercise>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<UserExercise> userExerciseHttpDefault) {

                        if (userExerciseHttpDefault.getError_code() == -1) {
                            Toast.makeText(UserExercisefinishActivity.this,userExerciseHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userExerciseHttpDefault.getError_code() == 0) {
                            userExercise=userExerciseHttpDefault.getData();
                            mine22.setText(userExercise.getScore());
                            //Toast.makeText(getContext(),"list为"+lectureShows.get(0).getLectureName(), Toast.LENGTH_SHORT).show();
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
