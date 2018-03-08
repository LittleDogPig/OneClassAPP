package eclass.dogking.com.oneclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eclass.dogking.com.oneclass.API.PptAPI;
import eclass.dogking.com.oneclass.Adapter.PPtAdapter;
import eclass.dogking.com.oneclass.Adapter.RvListener;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.PptShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import eclass.dogking.com.oneclass.utils.OpenFileUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Tab4_2Activity extends Activity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String tel;
    private PPtAdapter mrvAdapter;
    private List<PptShow> list;
    private LinearLayoutManager mLinearLayoutManager;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab4_2);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //取得课程名称
        if (intent != null) {
            tel = intent.getStringExtra("tel");
        }
        pptshow();
    }

    protected void initRv() {

        mrvAdapter = new PPtAdapter(this, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                File file = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/download/" + list.get(position).getFilename());
                if (!file.exists()) {
                    createdialog(position);
                    dialog.show();
                } else exist(position);
                //Toast.makeText(Tab4_2Activity.this,list.get(position).getName(),Toast.LENGTH_SHORT).show();

            }
        });


        mLinearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(mrvAdapter);
    }

    private void pptshow() {
        Observable<HttpDefault<List<PptShow>>> observable = OneclassUtils.createAPI(PptAPI.class).pptshow(
                tel

        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<PptShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<PptShow>> pptHttpDefault) {

                        if (pptHttpDefault.getError_code() == 0) {
                            //Toast.makeText(Tab4_1Activity.this,userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                            list = pptHttpDefault.getData();
                            initRv();
                        } else {

                            Toast.makeText(Tab4_2Activity.this, pptHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("tag:", e.getLocalizedMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    protected void exist(int position) {
        Tab4_2Activity.this.startActivity(OpenFileUtil.openFile(Environment.getExternalStorageDirectory().getPath()
                + "/download/" + list.get(position).getFilename()));
    }

    protected void unexist(int position) {
        FileDownloader.setup(Tab4_2Activity.this);
        FileDownloader.getImpl().create(OneclassUtils.getBaseURL() + list.get(position).getUrl())
                .setPath(Environment.getExternalStorageDirectory().getPath() + "/download/" + list.get(position).getFilename())
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("tag", "pending");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        Log.e("tag", "connected");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("tag", "progress");
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e("tag", "blockcomplete");
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                        Log.e("tag", "retry");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Toast.makeText(Tab4_2Activity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.e("tag", "completed");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("tag", "paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e("tag", e.getLocalizedMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e("tag", "warn");
                    }
                }).start();
    }

    private void createdialog(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("下载课件")//设置对话框的标题
                .setMessage("你没下载此课件，需要下载吗？")//设置对话框的内容
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
                        progressBar.setVisibility(View.VISIBLE);
                        unexist(position);
                        dialog.dismiss();
                    }
                }).create();
    }
  /*  @OnClick({R.id.start, R.id.stop, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    AndPermission.with(this)
                            .permission(Permission.WRITE_EXTERNAL_STORAGE)
                            .onGranted(new Action() {
                                @Override
                                public void onAction(List<String> permissions) {

                                }
                            }).onDenied(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                            // TODO what to do
                        }
                    }).start();
                } else {
                    FileDownloader.setup(this);
                    FileDownloader.getImpl().create(OneclassUtils.getBaseURL() + DOWNLOAD_URL)
                            .setPath(Environment.getExternalStorageDirectory().getPath() + "/download/" + filename)
                            .setListener(new FileDownloadListener() {
                                @Override
                                protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    Log.e("tag", "pending");
                                }

                                @Override
                                protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                    Log.e("tag", "connected");
                                }

                                @Override
                                protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    Log.e("tag", "progress");
                                }

                                @Override
                                protected void blockComplete(BaseDownloadTask task) {
                                    Log.e("tag", "blockcomplete");
                                }

                                @Override
                                protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                                    Log.e("tag", "retry");
                                }

                                @Override
                                protected void completed(BaseDownloadTask task) {
                                    Log.e("tag", "completed");
                                }

                                @Override
                                protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    Log.e("tag", "paused");
                                }

                                @Override
                                protected void error(BaseDownloadTask task, Throwable e) {
                                    Log.e("tag", "error");
                                }

                                @Override
                                protected void warn(BaseDownloadTask task) {
                                    Log.e("tag", "warn");
                                }
                            }).start();
                }

                break;
            case R.id.stop:
                Tab4_2Activity.this.startActivity(OpenFileUtil.openFile(Environment.getExternalStorageDirectory().getPath() + "/download/" + filename));
                //Aria.download(this).load(DOWNLOAD_URL).pause();
                break;
            case R.id.cancel:
                Aria.download(this).load(DOWNLOAD_URL).cancel();
                break;
        }
    }
*/

}
