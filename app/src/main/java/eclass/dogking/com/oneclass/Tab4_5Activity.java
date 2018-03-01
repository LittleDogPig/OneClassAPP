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

import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.API.LecturecsAPI;
import eclass.dogking.com.oneclass.Adapter.MyAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.entiry.Lecturecs;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Tab4_5Activity extends Activity {
	private TabHost tabHost;
	private String tel;
	private MyAdapter mrvAdapter;
	private List<LectureShow> lectureShows0;
	private List<LectureShow> lectureShows1;
	private RecyclerView RecyclerView0;
	private RecyclerView RecyclerView1;
	private TextView t0;
	private TextView t1;
	private LinearLayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab4_5);
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
				.setIndicator("未结束课程")
				.setContent(R.id.linearLayout1));//添加第一个标签页
		tabHost.addTab(tabHost.newTabSpec("tab02")
				.setIndicator("已结束课程")
				.setContent(R.id.linearLayout2));//添加第二个标签页
		RecyclerView0=(RecyclerView)findViewById(R.id.leturedata_tabhost1_rv);
		RecyclerView1=(RecyclerView)findViewById(R.id.leturedata_tabhost2_rv);
		t0=(TextView)findViewById(R.id.unfinishlecture);
		t1=(TextView)findViewById(R.id.finishlecture);
	}

	protected  void initrv0(){
		mLayoutManager = new LinearLayoutManager(this);
		mrvAdapter = new MyAdapter(this,lectureShows0,tel);
		RecyclerView0.setLayoutManager(mLayoutManager);
		RecyclerView0.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
		RecyclerView0.setAdapter(mrvAdapter);
	}
	protected  void initrv1(){
		mLayoutManager = new LinearLayoutManager(this);
		mrvAdapter = new MyAdapter(this,lectureShows1,tel);
		RecyclerView1.setLayoutManager(mLayoutManager);
		RecyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
		RecyclerView1.setAdapter(mrvAdapter);
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
							Toast.makeText(Tab4_5Activity.this,lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
						} else if (lectureShowHttpDefault.getError_code() == 0) {

							List<List<LectureShow>>  ls= lectureShowHttpDefault.getData();
							lectureShows0=ls.get(0);
							lectureShows1=ls.get(1);
							String text0=lectureShows0.size()+"";
							Log.e("0:",text0);
							String text1=lectureShows1.size()+"";
							Log.e("0:",text1);
							t0.setText(text0);
							t1.setText(text1);
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
}
