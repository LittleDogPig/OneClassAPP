package eclass.dogking.com.oneclass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dog on 2018/3/10 0010.
 */

public class AdminNew6 extends AppCompatActivity {

    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.edit3)
    EditText edit3;
    @BindView(R.id.edit5)
    EditText edit5;
    @BindView(R.id.udbutton)
    Button udbutton;
    @BindView(R.id.udbutton2)
    Button udbutton2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.adminnew6);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.udbutton,R.id.udbutton2})
    public void onClick(View view){
        String text1=edit1.getText().toString();
        String text2=edit2.getText().toString();
        String text3=edit3.getText().toString();
        String text5=edit5.getText().toString();



        switch (view.getId()){
            case R.id.udbutton:
                if (!((text1.length() < 1) || (text2.length() < 1) || (text3.length() < 1)|| (text5.length() < 1)))
                {
                    Toast.makeText(AdminNew6.this,"上传成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

                else     Toast.makeText(AdminNew6.this,"请填写完整信息",Toast.LENGTH_SHORT).show();

                break;
            case R.id.udbutton2:
                finish();

                break;




        }

    }



}
