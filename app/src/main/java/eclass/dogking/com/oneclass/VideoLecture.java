package eclass.dogking.com.oneclass;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.List;

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

public class VideoLecture extends AppCompatActivity implements UniversalVideoView.VideoViewCallback {


    @BindView(R.id.title)
    TextView title;

    private String name;
    private String lecturename;
    private View videoLayout;

    private View bottomLayout;
    private View titleLayout;

    private UniversalVideoView videoView;
    private UniversalMediaController mediaController;
    private int seekPosition;//当前进度
    private int cachedHeight;//视频区域大小
    private boolean isFullscreen;//是否全屏
    private String sID;
    private int id;
    private Lectureson lectureson;
    private TextView description;

    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";//用于保存seekPosition的key值


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.videolecture_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            sID = intent.getStringExtra("id");
            lecturename = intent.getStringExtra("lecturename");
        }
        id=Integer.parseInt(sID);
        //Log.e("TAG",id+"");
        title.setText(lecturename);
        getOneson();

        initView();

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
                            Toast.makeText(VideoLecture.this,lectureHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            lectureson=lectureHttpDefault.getData();
                            intiEvent();
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




    private void initView() {

        videoLayout = findViewById(R.id.videoLayout);
        bottomLayout = findViewById(R.id.bottomLayout);
        titleLayout=findViewById(R.id.title_layout);
        videoView = (UniversalVideoView) findViewById(R.id.videoView);
        mediaController = (UniversalMediaController) findViewById(R.id.mediaController);
        description=(TextView)findViewById(R.id.description);
    }
    private void intiEvent() {//把视频控制的按钮设置到播放器里
        videoView.setMediaController(mediaController);//设置置视频区域大小
        setVideoAreaSize();//设置屏幕状态和播放状态的监听
        videoView.setVideoViewCallback(this);

    }




    private void setVideoAreaSize() {

        videoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = videoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);// cachedHeight = (int) (width * 3f / 4f);
                // cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = videoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                videoLayout.setLayoutParams(videoLayoutParams);
                videoView.setVideoPath(OneclassUtils.getBaseURL()+lectureson.getUrl());
                videoView.requestFocus();

            }
        });

    }
    /**

     * 开始按钮

     *

     * @param v

     */

    public void startClick(View v) {

//设置视频开始

        videoView.start();

//设置视频标题

        mediaController.setTitle(lectureson.getName());

    }



//--------------重写下面三个方面是为了保存seekPosition--------------



    @Override

    protected void onPause() {

        super.onPause();

        if (videoView != null && videoView.isPlaying()) {

            seekPosition = videoView.getCurrentPosition();

            Log.i("info", "onPause mSeekPosition=" + seekPosition);

            videoView.pause();

        }

    }



    @Override

    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        Log.i("info", "onSaveInstanceState Position=" + videoView.getCurrentPosition());

        outState.putInt(SEEK_POSITION_KEY, seekPosition);

    }



    @Override

    protected void onRestoreInstanceState(Bundle outState) {

        super.onRestoreInstanceState(outState);

        seekPosition = outState.getInt(SEEK_POSITION_KEY);

        Log.i("info", "onRestoreInstanceState Position=" + seekPosition);

    }







//--------------以下方法都是VideoViewCallback接口的实现方法--------------



    /**

     * 全屏和默认的切换

     *

     * @param isFullscreen

     */

    @Override

    public void onScaleChange(boolean isFullscreen) {

        this.isFullscreen = isFullscreen;

        if (isFullscreen) {

            ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();

            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

            videoLayout.setLayoutParams(layoutParams);

//设置全屏时,无关的View消失,以便为视频控件和控制器控件留出最大化的位置

            bottomLayout.setVisibility(View.GONE);
            titleLayout.setVisibility(View.GONE);



        } else {

            ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();

            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

            layoutParams.height = this.cachedHeight;

            videoLayout.setLayoutParams(layoutParams);

            bottomLayout.setVisibility(View.VISIBLE);
            titleLayout.setVisibility(View.VISIBLE);
        }



        switchTitleBar(!isFullscreen);

    }



    /**

     * 横竖屏切换的时候控制ActionBar的状态

     *

     * @param show

     */

    private void switchTitleBar(boolean show) {

        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {

            if (show) {

                supportActionBar.show();

            } else {

                supportActionBar.hide();

            }

        }

    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {// 视频暂停

        Log.i("info", "onPause");

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {// 视频开始播放或恢复播放

        Log.i("info", "onStart");

    }



    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {// 视频开始缓冲

        Log.i("info", "onBufferingStart");

    }



    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {// 视频结束缓冲

        Log.i("info", "onBufferingEnd");

    }



    @Override
    public void onBackPressed() {

        if (this.isFullscreen) {

            videoView.setFullscreen(false);

        } else {

            super.onBackPressed();

        }

    }


}