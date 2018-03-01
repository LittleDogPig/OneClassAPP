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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.ExerciseAPI;
import eclass.dogking.com.oneclass.API.UserExerciseAPI;
import eclass.dogking.com.oneclass.entiry.Exercise;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.UserExercise;
import eclass.dogking.com.oneclass.entiry.UserExerciseShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/10 0010.
 */

public class UserExerciseActivity extends AppCompatActivity {
    @BindView(R.id.userexercise_name)
    TextView userexerciseName;
    @BindView(R.id.userexercise)
    TextView userexercise;
    @BindView(R.id.RB1)
    RadioButton RB1;
    @BindView(R.id.RB2)
    RadioButton RB2;
    @BindView(R.id.smmit)
    Button smmit;
    private String tel;
    private String name;
    private String lecturename;
    private Exercise exercise;
    private String answer;
    private UserExercise userExercise;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userexercise_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            tel = intent.getStringExtra("tel");
            name = intent.getStringExtra("name");
            lecturename = intent.getStringExtra("lecturename");
        }
        userexerciseName.setText(name);
        getExercise();
    }

    @OnClick({R.id.smmit})
    public void onClick(View view){


        if(RB1.isChecked()){
            answer="1";
        }else{answer="0";}
        switch (view.getId()){
            case R.id.smmit:
                showNormalDialog();

            break;

        }

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(UserExerciseActivity.this);
        normalDialog.setTitle("提交练习");
        normalDialog.setMessage("你已确定好答案了吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Answer();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
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
                            Toast.makeText(UserExerciseActivity.this, exerciseHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseHttpDefault.getError_code() == 0) {
                            exercise = exerciseHttpDefault.getData();
                            userexercise.setText(exercise.getDescription());
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

    private void Answer(){
        Observable<HttpDefault<UserExercise>> observable= OneclassUtils.createAPI(UserExerciseAPI.class).answer(
                tel,name,lecturename,answer);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<UserExercise>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<UserExercise> userExerciseHttpDefault) {

                        if (userExerciseHttpDefault.getError_code() == -1) {
                            Toast.makeText(UserExerciseActivity.this,userExerciseHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userExerciseHttpDefault.getError_code() == 0) {
                            userExercise=userExerciseHttpDefault.getData();
                            UserExerciseActivity.this.finish();
                            Intent intent = new Intent(UserExerciseActivity.this, UserExercisefinishActivity.class);
                            intent.putExtra("lecturename", lecturename);//课程名
                            intent.putExtra("name", name);//练习名
                            intent.putExtra("tel", tel);//传递登录的用户
                            UserExerciseActivity.this.startActivity(intent);
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
