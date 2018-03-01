package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import eclass.dogking.com.oneclass.API.LecturesonAPI;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.Lectureson;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/2/15 0015.
 */

public class TextLecture extends AppCompatActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textlecture)
    ImageView textlecture;
    @BindView(R.id.description)
    TextView description;


    private String sID;
    private int id;
    private String name;
    private String lecturename;
    private  Lectureson lectureson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.textlecture_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            sID = intent.getStringExtra("id");
            lecturename = intent.getStringExtra("lecturename");
        }
        id=Integer.parseInt(sID);
        title.setText(name);
        getOneson();
    }

    private void getOneson(){
        Observable<HttpDefault<Lectureson>> observable= OneclassUtils.createAPI(LecturesonAPI.class).getoneson(
                id
        );
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<Lectureson>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<Lectureson> lectureHttpDefault) {

                        if (lectureHttpDefault.getError_code() == -1) {
                            Toast.makeText(TextLecture.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            lectureson=lectureHttpDefault.getData();
                            Glide.with(TextLecture.this).load(OneclassUtils.getBaseURL()+lectureson.getUrl())
                                    .fitCenter().error(R.mipmap.error).into(textlecture);
                            description.setText(lectureson.getDescription());
                            Log.e("TAG",lectureson.getUrl());
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
