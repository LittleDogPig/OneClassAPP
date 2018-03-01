package eclass.dogking.com.oneclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.AdminActivity;


import eclass.dogking.com.oneclass.Database.UserService;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.login_edit1)
    EditText loginEdit1;
    @BindView(R.id.login_edit2)
    EditText loginEdit2;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.sign)
    TextView sign;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.administator)
    TextView administator;

    private UserService uService;
    private long exitTime;

    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

    }



    @OnClick({R.id.login,R.id.sign,R.id.reset,R.id.administator})
        public void onClick(View v){
            String tel=loginEdit1.getText().toString();
            String password=loginEdit2.getText().toString();


        switch (v.getId()){
            case R.id.login:
                Log.e("tag===============","test");
                if (!"".equals(tel) && !"".equals(password))
                {
                    User u1=new User();
                    u1.setTel(tel);
                    u1.setPassword(password);
                    Login(u1);


                }

                else{Toast.makeText(LoginActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.sign:
                Intent intent=new Intent(LoginActivity.this,SignActivity.class);	//创建要显示Activity对应的Intent对象
                startActivity(intent);	//开启一个新的Activity
                break;
            case R.id.reset:
                loginEdit1.setText("");
                loginEdit2.setText("");
                break;
            case R.id.administator:
                View view = getLayoutInflater().inflate(R.layout.dialogview, null);
                final EditText editText = (EditText) view.findViewById(R.id.dialogedit);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        //.setIcon(R.mipmap.icon)//设置标题的图片
                        .setTitle("开发者工具")//设置对话框的标题
                        //.setMessage("我是对话框的内容")//设置对话框的内容
                        .setView(view)//设置edittext
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = editText.getText().toString();
                                if(content.equals("dogking")){
                                    Intent intent=new Intent(LoginActivity.this,AdminActivity.class);	//创建要显示Activity对应的Intent对象
                                    startActivity(intent);	//开启一个新的Activity
                                }
                                else  Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;

        }


    }

    protected void onDestroy() {
        super.onDestroy();
    }
    //登陆事件
    private void Login(User user) {

        Observable<HttpDefault<User>> observable = OneclassUtils.createAPI(UserAPI.class).login(
                user.getTel(),
                user.getPassword());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<User> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(LoginActivity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {

                            User user = userHttpDefault.getData();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("tel",user.getTel());
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(LoginActivity.this,"服务器出问题了", Toast.LENGTH_SHORT).show();
                        Log.e("tag:", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }


                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
