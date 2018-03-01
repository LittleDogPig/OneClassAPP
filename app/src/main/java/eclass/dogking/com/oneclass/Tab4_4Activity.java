package eclass.dogking.com.oneclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class Tab4_4Activity extends Activity {

    private RecyclerView mRecyclerView;

    private List<LectureShow> lectureShows;
    private LinearLayoutManager mLayoutManager;
    private ExerciseAdapter mrvAdapter;
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab4_4);
        Intent intent = getIntent();
        //取得登录用户的电话
        if (intent != null) {
            tel = intent.getStringExtra("tel");

        }
        getLectureShow();

    }

    private void getLectureShow(){
        Observable<HttpDefault<List<LectureShow>>> observable= OneclassUtils.createAPI(LecturecsAPI.class).getlectureshow(
                tel);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<LectureShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<LectureShow>> lectureShowHttpDefault) {

                        if (lectureShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(Tab4_4Activity.this,lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureShowHttpDefault.getError_code() == 0) {
                            lectureShows=lectureShowHttpDefault.getData();


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
        mRecyclerView=(RecyclerView)findViewById(R.id.tab4_4_rv);
        mLayoutManager = new LinearLayoutManager(this);
        mrvAdapter = new ExerciseAdapter(this,lectureShows,tel);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mrvAdapter);
    }

}
