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
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.Adapter.ManageAdapter;
import eclass.dogking.com.oneclass.Adapter.RvListener;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
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

public class AdminSearch extends AppCompatActivity {
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<User> list;
    private LinearLayoutManager mLayoutManager;
    private ManageAdapter mrvAdapter;
    private AlertDialog dialog;
    private String tel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.search_button})
    public void OnClick(View view){
        tel=searchText.getText().toString();

        switch (view.getId()){
            case R.id.search_button:

                getUser();
                break;




        }


    }

    protected void initRV() {
        //RV1
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new ManageAdapter(this, list, new RvListener() {
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
                        deleteuser(list.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }
    private void deleteuser(int id) {
        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(UserAPI.class).deleteuser(
                id);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminSearch.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getUser();
                            Toast.makeText(AdminSearch.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getUser() {
        Observable<HttpDefault<List<User>>> observable = OneclassUtils.createAPI(UserAPI.class).getuser(
                tel
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<User>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<User>> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminSearch.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {

                            list = userHttpDefault.getData();
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
