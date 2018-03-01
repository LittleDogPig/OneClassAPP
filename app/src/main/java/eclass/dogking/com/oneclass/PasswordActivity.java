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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/23 0023.
 */

public class PasswordActivity extends AppCompatActivity {

    @BindView(R.id.udsignedit1)
    EditText udsignedit1;
    @BindView(R.id.udsignedit2)
    EditText udsignedit2;
    @BindView(R.id.udsignedit3)
    EditText udsignedit3;
    @BindView(R.id.udbutton)
    Button udbutton;
    @BindView(R.id.udbutton2)
    Button udbutton2;
    private String tel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.password);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            tel = intent.getStringExtra("tel");
        }

    }

    @OnClick({R.id.udbutton,R.id.udbutton2})
    public void onClick(View view){
        String password1=udsignedit1.getText().toString();
        String password2=udsignedit2.getText().toString();
        String password3=udsignedit3.getText().toString();


        switch (view.getId()){
            case R.id.udbutton:
                if(!((password1.length()<1)||(password2.length()<1)||(password3.length()<1)))
                    if((password2.length()>=6)&&(password2.length()<=20))
                        if(password2.equals(password3))
                            udpass(password1,password2);
                    else{
                        Toast.makeText(PasswordActivity.this, "前后密码不一致", Toast.LENGTH_SHORT).show();
                        udsignedit2.setText("");
                        udsignedit3.setText("");
                    }
                else{Toast.makeText(PasswordActivity.this, "密码最少6位", Toast.LENGTH_SHORT).show();}
             else{Toast.makeText(PasswordActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();}



            break;
            case R.id.udbutton2:

                finish();
                break;

        }



    }

    protected  void udpass(String password1,String password2){
        Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).resetPassword(
               tel,password1,password2
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<User> userHttpDefault) {

                        if (userHttpDefault.getError_code() == 0) {
                            Toast.makeText(PasswordActivity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            PasswordActivity.this.finish();
                        } else {
                            udsignedit1.setText("");
                            Toast.makeText(PasswordActivity.this,userHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
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
