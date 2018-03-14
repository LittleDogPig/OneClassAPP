package eclass.dogking.com.oneclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eclass.dogking.com.oneclass.API.ExamAPI;
import eclass.dogking.com.oneclass.API.ExerciseAPI;
import eclass.dogking.com.oneclass.Adapter.Manager4sAdapter;
import eclass.dogking.com.oneclass.Adapter.Manager5Adapter;
import eclass.dogking.com.oneclass.Adapter.RvListener;
import eclass.dogking.com.oneclass.entiry.Exam;
import eclass.dogking.com.oneclass.entiry.Exercise;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/3/10 0010.
 */

public class AdminExercise extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private String name;
    private LinearLayoutManager mLayoutManager;
    private Manager4sAdapter mrvAdapter;
    private List<Exercise> list;
    private AlertDialog dialog;
    private String Lid;
    private int id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_exercise_son);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            name = intent.getStringExtra("name");
            Lid=intent.getStringExtra("Lid");
        }

        id=Integer.parseInt(Lid);
        getExamList();

    }

    protected void initRV() {
        //RV1
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new Manager4sAdapter(this, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //Toast.makeText(AdminActivity.this, list.get(position).getTel(), Toast.LENGTH_SHORT).show();
                createdialog(position);
                dialog.show();
            }
        });
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(mrvAdapter);


    }

    private void createdialog(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除练习")//设置对话框的标题
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
                        deleteExercise(list.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }


    private void getExamList(){
        Observable<HttpDefault<List<Exercise>>> observable= OneclassUtils.createAPI(ExerciseAPI.class).getexson(
                name
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<Exercise>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<Exercise>> exerciseShowHttpDefault) {

                        if (exerciseShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminExercise.this,exerciseShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseShowHttpDefault.getError_code() == 0) {

                            list= exerciseShowHttpDefault.getData();
                            Log.e("size",list.size()+"");
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

    private void deleteExercise(int id){
        Observable<HttpDefault<String>> observable= OneclassUtils.createAPI(ExerciseAPI.class).deleteExercise(
                id
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> exerciseShowHttpDefault) {

                        if (exerciseShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminExercise.this,exerciseShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseShowHttpDefault.getError_code() == 0) {
                            getExamList();
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
