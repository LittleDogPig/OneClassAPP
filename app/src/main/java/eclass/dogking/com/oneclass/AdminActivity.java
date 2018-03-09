package eclass.dogking.com.oneclass;


import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eclass.dogking.com.oneclass.API.ExamAPI;
import eclass.dogking.com.oneclass.API.ExerciseAPI;
import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.API.MessageAPI;
import eclass.dogking.com.oneclass.API.PptAPI;
import eclass.dogking.com.oneclass.API.TeacherAPI;
import eclass.dogking.com.oneclass.API.UserAPI;
import eclass.dogking.com.oneclass.Adapter.ManageAdapter;
import eclass.dogking.com.oneclass.Adapter.Manager2Adapter;
import eclass.dogking.com.oneclass.Adapter.Manager3Adapter;
import eclass.dogking.com.oneclass.Adapter.Manager4Adapter;
import eclass.dogking.com.oneclass.Adapter.Manager5Adapter;
import eclass.dogking.com.oneclass.Adapter.Manager6Adapter;
import eclass.dogking.com.oneclass.Adapter.Manager7Adapter;
import eclass.dogking.com.oneclass.Adapter.RvListener;
import eclass.dogking.com.oneclass.entiry.Exam;
import eclass.dogking.com.oneclass.entiry.ExerciseShow;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.MessageShow;
import eclass.dogking.com.oneclass.entiry.PptShow;
import eclass.dogking.com.oneclass.entiry.Teacher;
import eclass.dogking.com.oneclass.entiry.User;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dog on 2018/1/23 0023.
 */

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    Button exit;

    FrameLayout tabcontent;
    private Context mcontext;
    private List<User> list;
    private List<Teacher> list2;
    private List<LectureShow> list3;
    private List<ExerciseShow> list4;
    private List<Exam> list5;
    private List<PptShow> list6;
    private List<MessageShow> list7;

    private ManageAdapter mrvAdapter;
    private Manager2Adapter mrvAdapter2;
    private Manager3Adapter mrvAdapter3;
    private Manager4Adapter mrvAdapter4;
    private Manager5Adapter mrvAdapter5;
    private Manager6Adapter mrvAdapter6;
    private Manager7Adapter mrvAdapter7;

    private LinearLayoutManager mLayoutManager;
    private AlertDialog dialog;
    private TabHost tabHost;
    private RecyclerView RecyclerView1;
    private RecyclerView RecyclerView2;
    private RecyclerView RecyclerView3;
    private RecyclerView RecyclerView4;
    private RecyclerView RecyclerView5;
    private RecyclerView RecyclerView6;
    private RecyclerView RecyclerView7;
    private Button searchuser;
    private Button searchteacher;
    private Button searchlecture;
    private Button searchexercise;
    private Button searchexam;
    private Button searchppt;

    private Button addteacher;
    private Button addlecture;
    private Button addexercise;
    private Button addexam;
    private Button addppt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.testweb);

        initTab();
        getUserList();
        getTeacherList();
        getLectureShow();
        getExerciseShow();
        getExamList();
        getPPTShow();
        getMessageShow();
    }

    protected void initTab() {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();//初始化TabHost组件
        LayoutInflater inflater = LayoutInflater.from(this);//声明并实例化一个LayoutInflater对象
        inflater.inflate(R.layout.admin_tab1, tabHost.getTabContentView());
        inflater.inflate(R.layout.admin_tab2, tabHost.getTabContentView());
        inflater.inflate(R.layout.admin_tab3, tabHost.getTabContentView());
        inflater.inflate(R.layout.admin_tab4, tabHost.getTabContentView());
        inflater.inflate(R.layout.admin_tab5, tabHost.getTabContentView());
        inflater.inflate(R.layout.admin_tab6, tabHost.getTabContentView());
        inflater.inflate(R.layout.admin_tab7, tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab01")
                .setIndicator("用户管理")
                .setContent(R.id.linearLayout1));//添加第一个标签页
        tabHost.addTab(tabHost.newTabSpec("tab02")
                .setIndicator("老师管理")
                .setContent(R.id.linearLayout2));//添加第二个标签页
        tabHost.addTab(tabHost.newTabSpec("tab03")
                .setIndicator("课程管理")
                .setContent(R.id.linearLayout3));//添加第二个标签页
        tabHost.addTab(tabHost.newTabSpec("tab04")
                .setIndicator("习题管理")
                .setContent(R.id.linearLayout4));//添加第二个标签页
        tabHost.addTab(tabHost.newTabSpec("tab05")
                .setIndicator("试题管理")
                .setContent(R.id.linearLayout5));//添加第二个标签页
        tabHost.addTab(tabHost.newTabSpec("tab06")
                .setIndicator("课件管理")
                .setContent(R.id.linearLayout6));//添加第二个标签页
        tabHost.addTab(tabHost.newTabSpec("tab07")
                .setIndicator("留言管理")
                .setContent(R.id.linearLayout7));//添加第二个标签页

        RecyclerView1 = (RecyclerView) findViewById(R.id.adminrv1);
        RecyclerView2 = (RecyclerView) findViewById(R.id.adminrv2);
        RecyclerView3 = (RecyclerView) findViewById(R.id.adminrv3);
        RecyclerView4 = (RecyclerView) findViewById(R.id.adminrv4);
        RecyclerView5 = (RecyclerView) findViewById(R.id.adminrv5);
        RecyclerView6 = (RecyclerView) findViewById(R.id.adminrv6);
        RecyclerView7 = (RecyclerView) findViewById(R.id.adminrv7);

        searchuser = (Button) findViewById(R.id.searchuser);
        searchuser.setOnClickListener(this);
        searchteacher=(Button) findViewById(R.id.searchteacher);
        searchteacher.setOnClickListener(this);
        searchlecture = (Button) findViewById(R.id.searchlecture);
        searchlecture.setOnClickListener(this);
        searchexercise = (Button) findViewById(R.id.searchexercise);
        searchexercise.setOnClickListener(this);
        searchexam = (Button) findViewById(R.id.searchexam);
        searchexam.setOnClickListener(this);
        searchppt = (Button) findViewById(R.id.searchppt);
        searchppt.setOnClickListener(this);

        addteacher=(Button) findViewById(R.id.addteacher);
        addteacher.setOnClickListener(this);
        addlecture = (Button) findViewById(R.id.addlecture);
        addlecture.setOnClickListener(this);
        addexercise = (Button) findViewById(R.id.addexercise);
        addexercise.setOnClickListener(this);
        addexam = (Button) findViewById(R.id.addexam);
        addexam.setOnClickListener(this);
        addppt = (Button) findViewById(R.id.addppt);
        addppt.setOnClickListener(this);


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
        RecyclerView1.setLayoutManager(mLayoutManager);
        RecyclerView1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView1.setAdapter(mrvAdapter);


    }

    protected void initRV2() {
        mLayoutManager = new LinearLayoutManager(this);

        //RV2
        mrvAdapter2 = new Manager2Adapter(this, list2, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //Toast.makeText(AdminActivity.this, "test", Toast.LENGTH_SHORT).show();
                createdialog2(position);
                dialog.show();
            }
        });
        RecyclerView2.setLayoutManager(mLayoutManager);
        RecyclerView2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView2.setAdapter(mrvAdapter2);


    }

    protected void initRV3() {
        mLayoutManager = new LinearLayoutManager(this);

        //RV2
        mrvAdapter3 = new Manager3Adapter(this, list3,new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //Toast.makeText(AdminActivity.this, "test", Toast.LENGTH_SHORT).show();
                createdialog3(position);
                dialog.show();
            }
        });
        RecyclerView3.setLayoutManager(mLayoutManager);
        RecyclerView3.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView3.setAdapter(mrvAdapter3);


    }

    protected void initRV4() {
        mLayoutManager = new LinearLayoutManager(this);

        //RV2
        mrvAdapter4 = new Manager4Adapter(this, list4);
        RecyclerView4.setLayoutManager(mLayoutManager);
        RecyclerView4.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView4.setAdapter(mrvAdapter4);


    }

    protected void initRV5() {
        mLayoutManager = new LinearLayoutManager(this);

        //RV2
        mrvAdapter5 = new Manager5Adapter(this, list5,new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //Toast.makeText(AdminActivity.this, "test", Toast.LENGTH_SHORT).show();
                createdialog5(position);
                dialog.show();
            }
        });
        RecyclerView5.setLayoutManager(mLayoutManager);
        RecyclerView5.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView5.setAdapter(mrvAdapter5);


    }
    protected void initRV6() {
        mLayoutManager = new LinearLayoutManager(this);

        //RV2
        mrvAdapter6 = new Manager6Adapter(this, list6,new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //Toast.makeText(AdminActivity.this, "test", Toast.LENGTH_SHORT).show();
                createdialog6(position);
                dialog.show();
            }
        });
        RecyclerView6.setLayoutManager(mLayoutManager);
        RecyclerView6.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView6.setAdapter(mrvAdapter6);


    }

    protected void initRV7() {
        mLayoutManager = new LinearLayoutManager(this);

        //RV2
        mrvAdapter7 = new Manager7Adapter(this, list7,new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //Toast.makeText(AdminActivity.this, "test", Toast.LENGTH_SHORT).show();
                createdialog7(position);
                dialog.show();
            }
        });
        RecyclerView7.setLayoutManager(mLayoutManager);
        RecyclerView7.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView7.setAdapter(mrvAdapter7);


    }




    //用户管理
    private void getUserList() {
        Observable<HttpDefault<List<User>>> observable = OneclassUtils.createAPI(UserAPI.class).userlist(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<User>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<User>> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getUserList();
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getTeacherList() {
        Observable<HttpDefault<List<Teacher>>> observable = OneclassUtils.createAPI(TeacherAPI.class).teacherlist(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<Teacher>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<Teacher>> teacherHttpDefault) {

                        if (teacherHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this, teacherHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (teacherHttpDefault.getError_code() == 0) {

                            list2 = teacherHttpDefault.getData();
                            initRV2();
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
    private void getLectureShow(){
        Observable<HttpDefault<List<LectureShow>>> observable= OneclassUtils.createAPI(LectureAPI.class).getlectureshow(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<LectureShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<LectureShow>> lectureShowHttpDefault) {

                        if (lectureShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this,lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureShowHttpDefault.getError_code() == 0) {

                            list3= lectureShowHttpDefault.getData();
                            initRV3();

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

    private void getPPTShow(){
        Observable<HttpDefault<List<PptShow>>> observable= OneclassUtils.createAPI(PptAPI.class).pptlistShow(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<PptShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<PptShow>> pptShowHttpDefault) {

                        if (pptShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this,pptShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (pptShowHttpDefault.getError_code() == 0) {

                            list6= pptShowHttpDefault.getData();
                            initRV6();

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
                            Toast.makeText(AdminActivity.this, messageShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (messageShowHttpDefault.getError_code() == 0) {

                            list7 = messageShowHttpDefault.getData();
                            initRV7();

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

    private void getExerciseShow(){
        Observable<HttpDefault<List<ExerciseShow>>> observable= OneclassUtils.createAPI(ExerciseAPI.class).exerciseshow(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<ExerciseShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<ExerciseShow>> exerciseShowHttpDefault) {

                        if (exerciseShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this,exerciseShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (exerciseShowHttpDefault.getError_code() == 0) {

                            list4= exerciseShowHttpDefault.getData();
                            initRV4();

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

    private void getExamList(){
        Observable<HttpDefault<List<Exam>>> observable= OneclassUtils.createAPI(ExamAPI.class).list();

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<Exam>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<Exam>> examShowHttpDefault) {

                        if (examShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this,examShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (examShowHttpDefault.getError_code() == 0) {

                            list5= examShowHttpDefault.getData();
                            initRV5();

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
    private void deleteExam(int id) {
        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(ExamAPI.class).deleteExam(
                id);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getExamList();
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void createdialog2(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除老师")//设置对话框的标题
                .setMessage("你确定删除此老师吗")//设置对话框的内容
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
                        deleteteacher(list2.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }

    private void createdialog3(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除课程")//设置对话框的标题
                .setMessage("你确定删除此课程吗")//设置对话框的内容
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
                        deletetlecture(list3.get(position).getLectureName());
                        dialog.dismiss();
                    }
                }).create();
    }


    private void createdialog5(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除考试")//设置对话框的标题
                .setMessage("你确定删除此考试吗")//设置对话框的内容
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
                        deleteExam(list5.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }

    private void createdialog6(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除课件")//设置对话框的标题
                .setMessage("你确定删除此课件吗")//设置对话框的内容
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
                        deletePPT(list6.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }


    private void createdialog7(final int position) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("删除留言")//设置对话框的标题
                .setMessage("你确定删除此留言吗")//设置对话框的内容
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
                        deletetmessage(list7.get(position).getId());
                        dialog.dismiss();
                    }
                }).create();
    }



    private void deletetmessage(int id) {
        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(MessageAPI.class).deletetmessage(
                id);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getMessageShow();
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getTeacherList();
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void deletetlecture(String name) {
        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(LectureAPI.class).deletelecture(
                name);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getLectureShow();
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void deletePPT(int id) {
        Observable<HttpDefault<String>> observable = OneclassUtils.createAPI(PptAPI.class).deletePPT(
                id);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<String> userHttpDefault) {

                        if (userHttpDefault.getError_code() == -1) {
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (userHttpDefault.getError_code() == 0) {
                            getPPTShow();
                            Toast.makeText(AdminActivity.this, userHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchuser:
                Intent intent = new Intent(AdminActivity.this, AdminSearch.class);
                startActivity(intent);
                break;
            case R.id.searchteacher:
                Intent intent2 = new Intent(AdminActivity.this, AdminSearch2.class);
                startActivity(intent2);
                break;
            case R.id.searchlecture:
                Intent intent3 = new Intent(AdminActivity.this, AdminSearch3.class);
                startActivity(intent3);
                break;
            case R.id.searchexercise:
                Intent intent4 = new Intent(AdminActivity.this, AdminSearch4.class);
                startActivity(intent4);
                break;
            case R.id.searchexam:
                Intent intent5 = new Intent(AdminActivity.this, AdminSearch5.class);
                startActivity(intent5);
                break;
            case R.id.searchppt:
                Intent intent6 = new Intent(AdminActivity.this, AdminSearch6.class);
                startActivity(intent6);
                break;
            case R.id.addteacher:
                Intent intent7 = new Intent(AdminActivity.this, AdminNew2.class);
                startActivity(intent7);

                break;
            case R.id.addlecture:
                Intent intent8 = new Intent(AdminActivity.this, AdminNew3.class);
                startActivity(intent8);

                break;
            case R.id.addexercise:
                Intent intent9 = new Intent(AdminActivity.this, AdminNew4.class);
                startActivity(intent9);

                break;
            case R.id.addexam:
                Intent intent10 = new Intent(AdminActivity.this, AdminNew5.class);
                startActivity(intent10);

                break;
            case R.id.addppt:
                Intent intent11 = new Intent(AdminActivity.this, AdminNew6.class);
                startActivity(intent11);

                break;

        }
    }

    protected void onRestart(){
        getUserList();
        getTeacherList();
        getLectureShow();
        getExerciseShow();
        getExamList();
        getPPTShow();
        getMessageShow();
        super.onRestart();

    }
}
