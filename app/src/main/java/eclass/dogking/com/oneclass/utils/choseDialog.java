package eclass.dogking.com.oneclass.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import eclass.dogking.com.oneclass.R;

/**
 * Created by dog on 2018/2/26 0026.
 */

public abstract class choseDialog extends Dialog implements View.OnClickListener{

    private Activity activity;
    private Button b1;
    private Button b2;
    private Button b3;


    public choseDialog(Activity activity) {
        super(activity, R.style.dialog);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogphoto);
        b1= (Button) findViewById(R.id.camer);
        b2= (Button) findViewById(R.id.photo);
        b3= (Button) findViewById(R.id.cancel);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camer:
                camer();
                this.cancel();
                break;
            case  R.id.photo:
                photo();
                this.cancel();
                break;
            case R.id.cancel:
                this.cancel();
                break;

        }
    }

    public abstract void camer();

    public abstract void photo();

}