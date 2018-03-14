package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.ExerciseAPI;
import eclass.dogking.com.oneclass.entiry.Exercise;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/3/10 0010.
 */

public class AdminUpdata4 extends AppCompatActivity {
    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.udbutton)
    Button udbutton;
    @BindView(R.id.udbutton2)
    Button udbutton2;
    private String Lid;
    private int id;
    private Exercise exercise;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.adminud4);
        ButterKnife.bind(this);
        //取得课程名称
        Intent intent = getIntent();
        if (intent != null) {
            Lid = intent.getStringExtra("Lid");
        }
        Log.e("tag=========", Lid);
        id = Integer.parseInt(Lid);
        recive();
    }

private void recive(){

        Observable<HttpDefault<Exercise>> observable= OneclassUtils.createAPI(ExerciseAPI.class).getoneexercise(
                id
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Exercise>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Exercise> exerciseShowHttpDefault) {

                        if (exerciseShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminUpdata4.this,exerciseShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseShowHttpDefault.getError_code() == 0) {
                            String answer=null;
                            exercise= exerciseShowHttpDefault.getData();
                            edit1.setText(exercise.getDescription());
                            if(exercise.getDescription().equals("1"))
                                answer="正确";
                            else answer="错误";
                            edit2.setText(answer);
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






    @OnClick({R.id.udbutton,R.id.udbutton2})
    public void onClick(View view){
        String text1=edit1.getText().toString();
        String text2=edit2.getText().toString();




        switch (view.getId()){
            case R.id.udbutton:
                if (!((text1.length() < 1) || (text2.length() < 1) )){
                    //updateExercise(id,text1,text2);
                Toast.makeText(AdminUpdata4.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();}
                else     Toast.makeText(AdminUpdata4.this,"请填写完整信息",Toast.LENGTH_SHORT).show();

                break;
            case R.id.udbutton2:
                finish();

                break;




        }

    }

}
