package eclass.dogking.com.oneclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.Database.UserService;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dog on 2018/1/5 0005.
 */

public class SignActivity extends AppCompatActivity {
    @BindView(R.id.sign_title)
    TextView signTitle;
    @BindView(R.id.signedit1)
    EditText signedit1;
    @BindView(R.id.signedit2)
    EditText signedit2;
    @BindView(R.id.signedit3)
    EditText signedit3;
    @BindView(R.id.signreset)
    TextView signreset;
    @BindView(R.id.signbutton)
    Button signbutton;
    @BindView(R.id.signbutton2)
    Button signbutton2;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private UserService uService;


    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.sign);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.signbutton, R.id.signbutton2, R.id.signreset})
    public void onClick(View v) {

        String phonenum = signedit1.getText().toString();
        String password1 = signedit2.getText().toString();
        String password2 = signedit3.getText().toString();

        switch (v.getId()) {
            case R.id.signbutton:
                if (!((signedit1.length() < 1) || (signedit2.length() < 1) || (signedit3.length() < 1)))//判断有无填写信息
                    if ((phonenum.length() == 11) && (phonenum.charAt(0) == '1'))
                        if ((password1.length() >= 6) && (password1.length() <= 20))
                            if (password1.equals(password2))

                            {
                                User u1 = new User();
                                u1.setPassword(password1);//密码
                                u1.setTel(phonenum);//手机号
                                progressBar.setVisibility(View.VISIBLE);
                                Register(u1);
                            } else {
                                Toast.makeText(SignActivity.this, "前后密码不一致", Toast.LENGTH_SHORT).show();
                                signedit2.setText("");
                                signedit3.setText("");
                            }
                        else {
                            Toast.makeText(SignActivity.this, "密码最少6位", Toast.LENGTH_SHORT).show();
                        }
                    else {
                        Toast.makeText(SignActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }

                else {
                    Toast.makeText(SignActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.signbutton2:
                SignActivity.this.finish();

                break;

            case R.id.signreset:
                signedit1.setText("");
                signedit2.setText("");
                signedit3.setText("");
                break;


        }

    }

    protected void onDestroy() {

        super.onDestroy();
    }

    //注册事件
    private void Register(User user) {

        Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).register(
                user.getTel(),
                user.getPassword()


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
                            Toast.makeText(SignActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            SignActivity.this.finish();
                        } else {
                            signedit1.setText("");
                            Toast.makeText(SignActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignActivity.this, "服务器出问题了", Toast.LENGTH_SHORT).show();
                        Log.e("tag:", e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
