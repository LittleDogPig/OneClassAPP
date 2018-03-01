package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.Adapter.ExerciseAdapter;
import eclass.dogking.com.oneclass.Adapter.MyAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/12 0012.
 */

public class SearcheLectureActivity extends AppCompatActivity {
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String name;
    private String tel;
    private List<LectureShow> lectureShows;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mrvAdapter;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.searchlecture);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            tel=intent.getStringExtra("tel");
        }

    }

    @OnClick({R.id.search_button})
    public void onClick(View view){
        name=searchText.getText().toString();


        switch (view.getId()){
            case R.id.search_button:
                if (name.equals(""))
                    Toast.makeText(this,"请输入课程关键字",Toast.LENGTH_SHORT).show();
                else Search();
                break;




        }



    }


    private void Search(){
        Observable<HttpDefault<List<LectureShow>>> observable= OneclassUtils.createAPI(LectureAPI.class).findlecturelikename(
                name);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<LectureShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<LectureShow>> lectureShowHttpDefault) {

                        if (lectureShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(SearcheLectureActivity.this,lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureShowHttpDefault.getError_code() == 0) {
                            lectureShows=lectureShowHttpDefault.getData();
                            if (lectureShows.size()==0)
                                Toast.makeText(SearcheLectureActivity.this,"找不到此课程", Toast.LENGTH_SHORT).show();
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

    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new MyAdapter(this,lectureShows,tel);
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rv.setAdapter(mrvAdapter);
    }


}
