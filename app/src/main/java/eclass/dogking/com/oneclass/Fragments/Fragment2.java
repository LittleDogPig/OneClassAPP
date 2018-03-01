package eclass.dogking.com.oneclass.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eclass.dogking.com.oneclass.API.LectureAPI;
import eclass.dogking.com.oneclass.Adapter.LeftAdapter;
import eclass.dogking.com.oneclass.Adapter.MyAdapter;
import eclass.dogking.com.oneclass.Adapter.RvListener;
import eclass.dogking.com.oneclass.MainActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.SearcheLectureActivity;
import eclass.dogking.com.oneclass.Tab4_1Activity;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//课程
public class Fragment2 extends Fragment {
    @BindView(R.id.rv_sort)
    RecyclerView leftrv;
    @BindView(R.id.rv_right)
    RecyclerView rightrv;
    Unbinder unbinder;
    @BindView(R.id.lecture_search)
    ImageView lectureSearch;
    private Context mContext;
    private List<String> leftlist;
    private LeftAdapter leftAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private MyAdapter rightAdapter;
    private String tel;
    private List<LectureShow> lectureShows;
    private List<LectureShow> ls0;
    private List<LectureShow> ls1;
    private List<LectureShow> ls2;
    private List<LectureShow> ls3;
    private List<LectureShow> ls4;
    private List<LectureShow> ls5;
    private List<LectureShow> ls6;
    private List<LectureShow> ls7;
    private List<List<LectureShow>> mylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.tab2, container, false);

        //对View中控件的操作方法

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.lecture_search)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lecture_search:
                Intent intent=new Intent(getActivity(), SearcheLectureActivity.class);
                intent.putExtra("tel",tel);
                startActivity(intent);
                break;


        }

    }


    protected void initData() {
        rightAdapter = new MyAdapter(mContext, mylist.get(0), tel);
        rightrv.setAdapter(rightAdapter);
        //left
        leftlist = new ArrayList<>();
        leftlist.add("IT");//0
        leftlist.add("办公");//1
        leftlist.add("图像");//2
        leftlist.add("艺术");//3
        leftlist.add("运动");//4
        leftlist.add("语言");//5
        leftlist.add("日常");//6
        leftlist.add("时尚");//7


        leftAdapter = new LeftAdapter(mContext, leftlist, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                changerightrv(position);
                //Toast.makeText(mContext,leftlist.get(position),Toast.LENGTH_SHORT).show();

            }
        });
        leftrv.setAdapter(leftAdapter);
    }

    private void getLectureShow() {
        Observable<HttpDefault<List<LectureShow>>> observable = OneclassUtils.createAPI(LectureAPI.class).getlectureshow(
        );

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpDefault<List<LectureShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HttpDefault<List<LectureShow>> lectureShowHttpDefault) {

                        if (lectureShowHttpDefault.getError_code() == -1) {
                            Toast.makeText(getContext(), lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureShowHttpDefault.getError_code() == 0) {

                            lectureShows = lectureShowHttpDefault.getData();
                            initlist();
                            initleftrv();
                            initrightrv();
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


    private void initleftrv() {
        mContext = this.getContext();
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        leftrv.setLayoutManager(mLinearLayoutManager);

        leftrv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

    }

    private void initrightrv() {
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rightrv.setLayoutManager(mLinearLayoutManager);
        rightrv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }


    private void changerightrv(int position) {
        leftAdapter.setCheckedPosition(position);
        rightAdapter = new MyAdapter(mContext, mylist.get(position), tel);
        rightrv.setAdapter(rightAdapter);

    }

    public void onResume() {
        super.onResume();
        getLectureShow();

    }

    private void initlist() {
        mylist = new ArrayList<>();
        ls0 = new ArrayList<>();
        ls1 = new ArrayList<>();
        ls2 = new ArrayList<>();
        ls3 = new ArrayList<>();
        ls4 = new ArrayList<>();
        ls5 = new ArrayList<>();
        ls6 = new ArrayList<>();
        ls7 = new ArrayList<>();
        for (int i = 0; i < lectureShows.size(); i++) {
            switch (lectureShows.get(i).getType()) {
                case "0":
                    ls0.add(lectureShows.get(i));
                    break;
                case "1":
                    ls1.add(lectureShows.get(i));
                    break;
                case "2":
                    ls2.add(lectureShows.get(i));
                    break;
                case "3":
                    ls3.add(lectureShows.get(i));
                    break;
                case "4":
                    ls4.add(lectureShows.get(i));
                    break;
                case "5":
                    ls5.add(lectureShows.get(i));
                    break;
                case "6":
                    ls6.add(lectureShows.get(i));
                    break;
                case "7":
                    ls7.add(lectureShows.get(i));
                    break;
            }
        }
        mylist.add(ls0);
        mylist.add(ls1);
        mylist.add(ls2);
        mylist.add(ls3);
        mylist.add(ls4);
        mylist.add(ls5);
        mylist.add(ls6);
        mylist.add(ls7);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tel = ((MainActivity) activity).getTel();

    }


}