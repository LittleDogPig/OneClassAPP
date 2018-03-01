package eclass.dogking.com.oneclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.Adapter.ExamAdapter;
import eclass.dogking.com.oneclass.Adapter.ExamFinishAdapter;
import eclass.dogking.com.oneclass.Adapter.MyAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Tab4_3Activity extends Activity {
	private TabHost tabHost;
	private String tel;
	private ExamAdapter mrvAdapter;
	private ExamFinishAdapter mrvAdapter1;
	private RecyclerView RecyclerView0;
	private RecyclerView RecyclerView1;
	private LinearLayoutManager mLayoutManager;
	private List<LectureShow> lectureShows0;
	private List<LectureShow> lectureShows1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab4_3);
		Intent intent = getIntent();
		//取得登录用户的电话
		if (intent != null) {
			tel = intent.getStringExtra("tel");

		}
		initData();
		getLectureDataShow();
	}

	protected  void  initData(){

		tabHost=(TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();//初始化TabHost组件
		LayoutInflater inflater=LayoutInflater.from(this);//声明并实例化一个LayoutInflater对象
		inflater.inflate(R.layout.lecturedata_tabhost1, tabHost.getTabContentView());
		inflater.inflate(R.layout.lecturedata_tabhost2, tabHost.getTabContentView());
		tabHost.addTab(tabHost.newTabSpec("tab01")
				.setIndicator("未考试")
				.setContent(R.id.linearLayout1));//添加第一个标签页
		tabHost.addTab(tabHost.newTabSpec("tab02")
				.setIndicator("已考试")
				.setContent(R.id.linearLayout2));//添加第二个标签页
		RecyclerView0=(RecyclerView)findViewById(R.id.leturedata_tabhost1_rv);
		RecyclerView1=(RecyclerView)findViewById(R.id.leturedata_tabhost2_rv);

	}

	protected  void initrv0(){
		mLayoutManager = new LinearLayoutManager(this);
		mrvAdapter = new ExamAdapter(this,lectureShows0,tel);
		RecyclerView0.setLayoutManager(mLayoutManager);
		RecyclerView0.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
		RecyclerView0.setAdapter(mrvAdapter);
	}
	protected  void initrv1(){
		mLayoutManager = new LinearLayoutManager(this);
		mrvAdapter1 = new ExamFinishAdapter(this,lectureShows1,tel);
		RecyclerView1.setLayoutManager(mLayoutManager);
		RecyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
		RecyclerView1.setAdapter(mrvAdapter1);
	}

	private void getLectureDataShow(){
		Observable<HttpDefault<List<List<LectureShow>>>> observable= OneclassUtils.createAPI(LecturecsAPI.class).getlecturedatashow(
				tel);

		observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<HttpDefault<List<List<LectureShow>>>>() {
					@Override
					public void onSubscribe(@NonNull Disposable d) {
					}

					@Override
					public void onNext(@NonNull HttpDefault<List<List<LectureShow>>> lectureShowHttpDefault) {

						if (lectureShowHttpDefault.getError_code() == -1) {
							Toast.makeText(Tab4_3Activity.this,lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
						} else if (lectureShowHttpDefault.getError_code() == 0) {

							List<List<LectureShow>>  ls= lectureShowHttpDefault.getData();
							lectureShows0=ls.get(0);
							lectureShows1=ls.get(1);
							initrv0();
							initrv1();
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

	protected void onRestart(){
		getLectureDataShow();
		super.onRestart();

	}

}
