package eclass.dogking.com.oneclass.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import eclass.dogking.com.oneclass.API.LectureAPI;

import eclass.dogking.com.oneclass.MainActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.Adapter.MyAdapter;
import eclass.dogking.com.oneclass.entiry.HttpDefault;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//主页
public class Fragment1 extends Fragment {
    @BindView(R.id.tab1_rv)
    RecyclerView  mRecyclerView;

    private Context context;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mrvAdapter;
    private List<LectureShow> lectureShows;
    private List<LectureShow> ls;
    private CustomBanner<String> mBanner;
    private String tel;
    private  List<Integer> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.tab1, container, false);
        ButterKnife.bind(this, view);
        mBanner = (CustomBanner)view.findViewById(R.id.banner);
        ArrayList<String> images = new ArrayList<>();
        images.add(OneclassUtils.getBaseURL()+"file/download?filename=ad1.png&type=0");
        images.add(OneclassUtils.getBaseURL()+"file/download?filename=ad2.png&type=0");
        images.add(OneclassUtils.getBaseURL()+"file/download?filename=ad3.png&type=0");
        setBean(images);
        getLectureShow();

        //System.out.println("名字为："+lectureShows.get(0).getLectureName());

        //创建默认的线性LayoutManager
        return view;
    }

    private void setBean(final ArrayList beans) {
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                //这里返回的是轮播图的项的布局 支持任何的布局
                //position 轮播图的第几个项
                ImageView imageView = new ImageView(context);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String data) {
                //在这里更新轮播图的UI
                //position 轮播图的第几个项
                //view 轮播图当前项的布局 它是createView方法的返回值
                //data 轮播图当前项对应的数据
                Glide.with(context).load(data).centerCrop().placeholder(R.mipmap.loading).error(R.mipmap.error).dontAnimate().into((ImageView) view);
            }
        }, beans).startTurning(5000);

    }

    protected  void  initData(){
        context=this.getContext();
        mLayoutManager = new LinearLayoutManager(context);
        mrvAdapter = new MyAdapter(context,lectureShows,tel);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mrvAdapter);
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
                            Toast.makeText(getContext(),lectureShowHttpDefault.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (lectureShowHttpDefault.getError_code() == 0) {
                            lectureShows=new ArrayList<LectureShow>();
                             ls = lectureShowHttpDefault.getData();
                                Randomlist();//随机课程推荐
                            for(int i=0;i<10;i++)
                            {
                                lectureShows.add(ls.get(list.get(i)));
                            }
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
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void Randomlist(){
        Random  r = new Random();
        list = new ArrayList<Integer>();
        int i;
        while(list.size() < 10){
            i = r.nextInt(ls.size());
            if(!list.contains(i)){
                list.add(i);
            }
        }


    }
    public  void  onAttach(Activity activity){
        super.onAttach(activity);
        tel=((MainActivity) activity).getTel();

    }


}