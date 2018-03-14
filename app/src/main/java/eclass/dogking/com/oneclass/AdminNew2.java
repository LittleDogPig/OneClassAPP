package eclass.dogking.com.oneclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.TeacherAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Teacher;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/3/10 0010.
 */

public class AdminNew2 extends AppCompatActivity {

    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.edit3)
    EditText edit3;
    @BindView(R.id.edit4)
    EditText edit4;
    @BindView(R.id.edit5)
    EditText edit5;
    @BindView(R.id.udbutton)
    Button udbutton;
    @BindView(R.id.udbutton2)
    Button udbutton2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.adminnew2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.udbutton,R.id.udbutton2})
    public void onClick(View view){
        String text1=edit1.getText().toString();
        String text2=edit2.getText().toString();
        String text3=edit3.getText().toString();
        String text4=edit4.getText().toString();
        String text5=edit5.getText().toString();



        switch (view.getId()){
            case R.id.udbutton:
                if (!((text1.length() < 1) || (text2.length() < 1) || (text3.length() < 1)|| (text4.length() < 1)|| (text5.length() < 1)))
                newteacher(text1,text2,text3,text4,text5);
                else
                Toast.makeText(AdminNew2.this,"请输入完整信息",Toast.LENGTH_SHORT).show();

                break;
            case R.id.udbutton2:
                finish();

                break;




        }

    }
    private void newteacher(String name,String email,String school,String sex,String description){
        Observable<HttpDefault<Teacher>> observable = OneclassUtils.createAPI(TeacherAPI.class).newteacher(
                name,sex,email,school,description);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Teacher>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Teacher> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminNew2.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            Toast.makeText(AdminNew2.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            AdminNew2.this.finish();
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
