package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dog on 2018/1/5 0005.
 */

public class WelcomeActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        final Intent it = new Intent(this, LoginActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行意图
                WelcomeActivity.this.finish();
            }
        };
        timer.schedule(task, 1000 * 3); //3秒后跳转，这里根据自己需要设定时间
    }



}
