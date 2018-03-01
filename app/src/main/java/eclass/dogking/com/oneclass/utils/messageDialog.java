package eclass.dogking.com.oneclass.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import eclass.dogking.com.oneclass.R;

/**
 * Created by dog on 2018/2/21 0021.
 */

public abstract class messageDialog extends Dialog implements View.OnClickListener{

    private Activity activity;
    private EditText editText;
    private Button btn;


    public messageDialog(Activity activity) {
        super(activity,R.style.dialog);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        editText=(EditText) findViewById(R.id.messedit);
        btn= (Button) findViewById(R.id.commit);
        btn.setOnClickListener(this);

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
        public String gettext(){
           return editText.getText().toString();
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit:
                commit();
                this.cancel();
                break;
        }
    }

    public abstract void commit();

}