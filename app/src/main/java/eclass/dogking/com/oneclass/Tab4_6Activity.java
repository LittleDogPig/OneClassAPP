package eclass.dogking.com.oneclass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.MessageAPI;
import eclass.dogking.com.oneclass.Adapter.MessageAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.MessageShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import eclass.dogking.com.oneclass.utils.messageDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Tab4_6Activity extends Activity {
    @BindView(R.id.messagebutton)
    Button messagebutton;

    private String tel;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MessageAdapter mrvAdapter;
    private List<MessageShow> list;
    private View inflate;
    private String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab4_6);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得登录用户的电话
        if (intent != null) {
            tel = intent.getStringExtra("tel");
        }
        getMessageShow();

    }

    @OnClick({R.id.messagebutton})
    public void onClike(View view){
        switch (view.getId()){
            case R.id.messagebutton:
                    dialogshow();
                break;

        }

    }

    protected void initData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new MessageAdapter(this, list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mrvAdapter);

    }

    protected  void dialogshow(){

        new messageDialog(Tab4_6Activity.this){

           public void commit(){
                EditText editText=(EditText)findViewById(R.id.messedit);
                description=editText.getText().toString();
               if(description.equals(""))
                   Toast.makeText(Tab4_6Activity.this,"你在留什么东西",Toast.LENGTH_SHORT).show();
                else save();

           }

       }.show();

    }



    private void getMessageShow() {
        Observable<HttpDefault<List<MessageShow>>> observable = OneclassUtils.createAPI(MessageAPI.class).getmessageshow(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<MessageShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<MessageShow>> messageShowHttpDefault) {

                        if (messageShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(Tab4_6Activity.this, messageShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (messageShowHttpDefault.getError_code() == 0) {
                            list = messageShowHttpDefault.getData();
                            initData();

                            //Toast.makeText(getContext(),"list为"+lectureShows.get(0).getLectureName(), Toast.LENGTH_SHORT).show();
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


    private void save() {
        Observable<HttpDefault<List<MessageShow>>> observable = OneclassUtils.createAPI(MessageAPI.class).save(
        tel,description);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<MessageShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<MessageShow>> messageShowHttpDefault) {

                        if (messageShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(Tab4_6Activity.this, messageShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (messageShowHttpDefault.getError_code() == 0) {
                            getMessageShow();

                            Toast.makeText(Tab4_6Activity.this,messageShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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
