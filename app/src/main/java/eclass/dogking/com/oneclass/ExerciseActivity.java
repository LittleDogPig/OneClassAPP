package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.API.UserExerciseAPI;
import eclass.dogking.com.oneclass.Adapter.ExerciseAdapter;
import eclass.dogking.com.oneclass.Adapter.UserExerciseAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
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

public class ExerciseActivity extends AppCompatActivity {
    @BindView(R.id.exercise_name)
    TextView exerciseName;
    @BindView(R.id.exerciset1)
    TextView exerciset1;
    @BindView(R.id.exerciset2)
    TextView exerciset2;
    @BindView(R.id.exercise_rv)
    RecyclerView mRecyclerView;

    private String tel;
    private String name;//课程名字
    private List<UserExerciseShow> ls;
    private LinearLayoutManager mLayoutManager;
    private UserExerciseAdapter userExerciseAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exercise_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得登录用户的电话和课程名字
        if (intent != null) {
            tel = intent.getStringExtra("tel");
            name=intent.getStringExtra("name");
        }
        exerciseName.setText(name+"练习");
        getLectureShow();

    }

    private void getLectureShow(){
        Observable<HttpDefault<List<UserExerciseShow>>> observable= OneclassUtils.createAPI(UserExerciseAPI.class).getrecordshow(
                tel,name);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<UserExerciseShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<UserExerciseShow>> lectureShowHttpDefault) {

                        if (lectureShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(ExerciseActivity.this,lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureShowHttpDefault.getError_code() == 0) {
                            ls=lectureShowHttpDefault.getData();
                            String t1=ls.size()+"";
                            String t2;
                            int t=0;
                            for(int i=0;i<ls.size();i++){
                                if (ls.get(i).getFinish().equals("未完成")){
                                     t++;
                                }
                            }
                            t2=t+"";
                            exerciset1.setText(t1);
                            exerciset2.setText(t2);
                            initData();

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
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this);
        userExerciseAdapter = new UserExerciseAdapter(this,ls,tel,name);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(userExerciseAdapter);
    }

    protected void onRestart(){
        getLectureShow();
        super.onRestart();

    }

 }
