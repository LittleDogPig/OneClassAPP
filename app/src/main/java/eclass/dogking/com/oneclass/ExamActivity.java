package eclass.dogking.com.oneclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.ExamAPI;
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.entiry.Exam;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/11 0011.
 */

public class ExamActivity extends AppCompatActivity {

    @BindView(R.id.exam)
    ImageView examimg;
    @BindView(R.id.answer)
    EditText answer;
    @BindView(R.id.smmit)
    Button smmit;
    @BindView(R.id.exam_name)
    TextView examName;
    @BindView(R.id.tmelater)
    TextView tv_showTime;

    private CountDownTimer time;
    private String name;
    private String tel;
    private Exam exam;
    private String useranswer;
    private AlertDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exam_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            name = intent.getStringExtra("name");
            tel = intent.getStringExtra("tel");
        }
        examName.setText(name + "考试");
        useranswer=answer.getText().toString();
        findexam();
        time = new CountDownTimer(180000, 1000)//
        {

            public void onTick(long millisUntilFinished)
            {
                tv_showTime.setText("剩余时间: " + millisUntilFinished / 1000+"秒");
            }

            public void onFinish()
            {
                tv_showTime.setText("考试结束");
                answer();
            }
        }.start();
    }

    @OnClick({R.id.smmit})
    public void onClick(View view){


        switch(view.getId()){
            case R.id.smmit:
                createdialog();
                    dialog.show();
                break;


        }
    }

    private void  createdialog() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("交卷")//设置对话框的标题
                .setMessage("确定要交卷吗")//设置对话框的内容
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
                        answer();
                        dialog.dismiss();
                    }
                }).create();
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
                            Glide.with(ExamActivity.this).load(OneclassUtils.getBaseURL()+exam.getUrl())
                                    .fitCenter().error(R.mipmap.error).into(examimg);
                        } else {

                            Toast.makeText(ExamActivity.this,examHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
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

    private void answer(){
        Observable<HttpDefault<Lecturecs>> observable = OneclassUtils.createAPI(ExamAPI.class).answer(
              tel,name,useranswer

        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Lecturecs>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Lecturecs> examHttpDefault) {

                        if (examHttpDefault.getError_code() == 0) {
                            //Toast.makeText(Tab4_1Activity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ExamActivity.this,ExamFinishActivity.class);
                            intent.putExtra("name",name);//传递课程
                            intent.putExtra("tel",tel);//传递登录的用户
                            ExamActivity.this.startActivity(intent);
                            ExamActivity.this.finish();
                        } else {

                            Toast.makeText(ExamActivity.this,examHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
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
