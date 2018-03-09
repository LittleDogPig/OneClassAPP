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
        import eclass.dogking.com.oneclass.API.TeacherAPI;
        import eclass.dogking.com.oneclass.API.UserAPI;
        import eclass.dogking.com.oneclass.Adapter.ManageAdapter;
        import eclass.dogking.com.oneclass.Adapter.Manager2Adapter;
        import eclass.dogking.com.oneclass.Adapter.RvListener;
        import eclass.dogking.com.oneclass.entiry.HttpDefault;
        import eclass.dogking.com.oneclass.entiry.Teacher;
        import eclass.dogking.com.oneclass.entiry.User;
        import eclass.dogking.com.oneclass.utils.OneclassUtils;
        import io.reactivex.Observable;
        import io.reactivex.Observer;
        import io.reactivex.android.schedulers.AndroidSchedulers;
        import io.reactivex.disposables.Disposable;
        import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/3/9 0009.
 */

public class AdminSearch2 extends AppCompatActivity {
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<Teacher> list;
    private LinearLayoutManager mLayoutManager;
    private Manager2Adapter mrvAdapter;
    private AlertDialog dialog;
    private String name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search2);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.search_button})
    public void OnClick(View view){
        name=searchText.getText().toString();

        switch (view.getId()){
            case R.id.search_button:

                getTeacher();
                break;
        }


    }

    protected void initRV() {
        //RV1
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new Manager2Adapter(this, list, new RvListener() {
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
                        deleteteacher(list.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }
    private void deleteteacher(int id) {
        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(TeacherAPI.class).deleteteacher(
                id);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminSearch2.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {

                            Toast.makeText(AdminSearch2.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getTeacher() {
        Observable<HttpDefault<List<Teacher>>> observable = OneclassUtils.createAPI(TeacherAPI.class).getTeacher(
                name
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<Teacher>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<Teacher>> teacherHttpDefault) {

                        if (teacherHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminSearch2.this, teacherHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (teacherHttpDefault.getError_code() == 0) {

                            list = teacherHttpDefault.getData();
                            initRV();
                            //Toast.makeText(AdminActivity.this, "list为" + users.get(0).getName(), Toast.LENGTH_SHORT).show();
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
