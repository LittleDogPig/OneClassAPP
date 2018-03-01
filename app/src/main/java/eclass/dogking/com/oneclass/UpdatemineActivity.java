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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
 * Created by dog on 2018/1/24 0024.
 */

public class UpdatemineActivity extends AppCompatActivity {
    @BindView(R.id.update_title)
    TextView updateTitle;
    @BindView(R.id.udsignedit1)
    EditText udsignedit1;
    @BindView(R.id.udRB1)
    RadioButton udRB1;
    @BindView(R.id.udRB2)
    RadioButton udRB2;
    @BindView(R.id.udrgroup)
    RadioGroup udrgroup;
    @BindView(R.id.udsignedit2)
    EditText udsignedit2;
    @BindView(R.id.udbutton)
    Button udbutton;
    @BindView(R.id.udbutton2)
    Button udbutton2;
    String udsex;
    String udname;
    String uddc;
    String tel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.updateminelayout);
        Intent intent = getIntent();
        //取得登录用户的电话
        if (intent != null) {
            tel = intent.getStringExtra("tel");
        }
        ButterKnife.bind(this);
        receive(tel);

    }

    @OnClick({R.id.udbutton,R.id.udbutton2})
    public void onClick(View view){
        if(udRB1.isChecked()){
            udsex="男";
        }else{udsex="女";}
         udname=udsignedit1.getText().toString();
         uddc=udsignedit2.getText().toString();

        switch (view.getId()){
            case R.id.udbutton:
                if(udsignedit1.getText().equals(""))
                    Toast.makeText(UpdatemineActivity.this,"不设名字我不知道你在修改神魔",Toast.LENGTH_SHORT).show();
                else updatemine();
                break;
            case R.id.udbutton2:
                finish();

            break;




        }

    }
    private void receive(String tel){
        Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).queryUserbyPhone(
                tel

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
                            //Toast.makeText(Tab4_1Activity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            udsignedit1.setText(userHttpDefault.getData().getName());
                            udsignedit2.setText(userHttpDefault.getData().getDescription());
                            //Toast.makeText(Tab4_1Activity.this,"接收成功", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(UpdatemineActivity.this,userHttpDefault.getMessage(),Toast.LENGTH_SHORT).show();
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


private void updatemine() {

    Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).updatemine(
            udname,udsex,uddc,tel);
    observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<HttpDefault<User>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull HttpDefault<User> userHttpDefault) {

                    if (userHttpDefault.getError_code() == -1) {
                        Toast.makeText(UpdatemineActivity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (userHttpDefault.getError_code() == 0) {

                        Toast.makeText(UpdatemineActivity.this,"修改成功", Toast.LENGTH_SHORT).show();
                       UpdatemineActivity.this.finish();
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
