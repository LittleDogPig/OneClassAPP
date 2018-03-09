package eclass.dogking.com.oneclass;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.ExerciseAPI;
import eclass.dogking.com.oneclass.Adapter.Manager3Adapter;
import eclass.dogking.com.oneclass.Adapter.Manager4Adapter;
import eclass.dogking.com.oneclass.Adapter.RvListener;
import eclass.dogking.com.oneclass.entiry.ExerciseShow;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/3/10 0010.
 */

public class AdminSearch4 extends AppCompatActivity {
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String name;
    private LinearLayoutManager mLayoutManager;
    private Manager4Adapter mrvAdapter;
    private List<ExerciseShow> list;
    private AlertDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search4);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.search_button})
    public void OnClick(View view){
        name=searchText.getText().toString();

        switch (view.getId()){
            case R.id.search_button:

                getExerciseShow();
                break;
        }


    }
    protected void initRV() {
        //RV1
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new Manager4Adapter(this, list);
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(mrvAdapter);


    }
    private void createdialog(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除用户")//设置对话框的标题
                .setMessage("你确定删除此用户吗")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //deleteteacher(list.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }

    private void getExerciseShow(){
        Observable<HttpDefault<List<ExerciseShow>>> observable= OneclassUtils.createAPI(ExerciseAPI.class).getExercise(
        name);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<ExerciseShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<ExerciseShow>> exerciseShowHttpDefault) {

                        if (exerciseShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminSearch4.this,exerciseShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseShowHttpDefault.getError_code() == 0) {

                            list= exerciseShowHttpDefault.getData();
                            initRV();

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

}
